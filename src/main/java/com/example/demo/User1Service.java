package com.example.demo;

import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shixin
 * @date 2021/3/13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class User1Service {
    final User1Repository user1Repository;
    final StringRedisTemplate redisTemplate;

    static final CopyOnWriteArraySet<String> MARK_SET = new CopyOnWriteArraySet<>();

    static final ConcurrentHashMap<String, Object> LOCK_MAP = new ConcurrentHashMap<>();

    private void commonStart() {
        log.info("Thread:{} 开始注册用户", Thread.currentThread().getName());
    }

    private void commonEnd() {
        log.info("Thread:{} 注册用户成功", Thread.currentThread().getName());
    }

    private void businessJudge(String tel) {
        User1 example = new User1();
        example.setTel(tel);
        long count = user1Repository.count(Example.of(example));
        if (count > 0) {
            log.info("Thread:{} 业务判断重复提交", Thread.currentThread().getName());
            throw new RepeatException("请勿重复注册！");
        } else {
            log.info("Thread:{} Tel: {} 可以注册", Thread.currentThread().getName(), tel);
        }
    }


    public void way1(User1 user) throws InterruptedException {
        commonStart();

        // 模拟耗时业务处理过程
        Thread.sleep(2000);

        user1Repository.save(user);

        commonEnd();
    }

    public void way2(User1 user) throws InterruptedException {
        commonStart();

        businessJudge(user.getTel());

        // 模拟耗时业务处理过程
        Thread.sleep(2000);

        user1Repository.save(user);

        commonEnd();
    }

    public void way3(UserParam param) throws InterruptedException {
        commonStart();

        // 在MARK_SET判断是否有该标识
        if (!MARK_SET.add(param.getMarkId())) {
            log.info("线程: {} 唯一标识判断重复提交", Thread.currentThread().getName());
            throw new RepeatException("请勿重复注册！");
        }

        businessJudge(param.getTel());

        // 模拟耗时业务处理过程
        Thread.sleep(2000);

        User1 user = new User1();
        user.setTel(param.tel);
        user.setPassword(param.password);
        user1Repository.save(user);

        // 注册成功后删除该markId
        MARK_SET.remove(param.getMarkId());

        commonEnd();
    }

    public void way4(User1 user, String key) throws InterruptedException {
        commonStart();

        log.info("线程: {} 执行", Thread.currentThread().getName());
        if (LOCK_MAP.containsKey(key)) {
            log.info("线程: {} 请勿重复注册！", Thread.currentThread().getName());
            throw new RepeatException("请勿重复注册！");
        }

        LOCK_MAP.putIfAbsent(key, new Object());

        businessJudge(user.getTel());

        // 模拟耗时业务处理过程
        Thread.sleep(5000);

        synchronized (LOCK_MAP.get(key)) {
            log.info("线程: {} 进入代码块, key锁对象：{}", Thread.currentThread().getName(), key);

            user1Repository.save(user);

            LOCK_MAP.remove(key);

            commonEnd();
        }
    }

    public void way5(UserParam param) throws InterruptedException {
        commonStart();

        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(param.getMarkId(), param.getTel(), Duration.ofMillis(10_000));

        // 在Redis中判断是否有该标识
        if (Objects.nonNull(success) && !success) {
            log.info("线程: {} Redis唯一标识判断重复提交", Thread.currentThread().getName());
            throw new RepeatException("请勿重复注册！");
        }

        businessJudge(param.getTel());

        // 模拟耗时业务处理过程
        Thread.sleep(2000);

        User1 user = new User1();
        user.setTel(param.tel);
        user.setPassword(param.password);
        user1Repository.save(user);

        // 注册成功后删除该markId
        redisTemplate.delete(param.getMarkId());

        commonEnd();
    }
}
