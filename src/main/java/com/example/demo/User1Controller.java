package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shixin
 * @date 2021/2/26
 */
@Slf4j
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class User1Controller {
    final User1Service user1Service;

    /**
     * 方式1：会有重复提交问题
     *
     * @param user
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/way1")
    public String way1(@RequestBody User1 user) throws InterruptedException {
        user1Service.way1(user);
        return "注册成功";
    }

    /**
     * 方式2：加入了逻辑判断，但是还是会有重复提交问题
     *
     * @param user
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/way2")
    public String way2(@RequestBody User1 user) throws InterruptedException {
        user1Service.way2(user);
        return "注册成功";
    }

    /**
     * 方式3：使用本地缓存记录标识，不会有重复提交问题
     *
     * @param param
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/way3")
    public String way3(@RequestBody UserParam param) throws InterruptedException {
        user1Service.way3(param);
        return "注册成功";
    }

    /**
     * 方式4：使用用户+请求路径加锁，不会有重复提交问题
     *
     * @param user
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/way4")
    public String way4(@RequestBody User1 user,
                       HttpServletRequest request) throws InterruptedException {
        String remoteAddr = request.getRemoteAddr();
        log.info("用户地址：{}", remoteAddr);
        user1Service.way4(user, remoteAddr+"/user/way4");
        return "注册成功";
    }

    /**
     * 方式5：使用Redis分布式锁记录标识，不会有重复提交问题
     *
     * @param param
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/way5")
    public String way5(@RequestBody UserParam param) throws InterruptedException {
        user1Service.way5(param);
        return "注册成功";
    }
}