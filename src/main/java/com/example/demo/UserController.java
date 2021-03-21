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
public class UserController {
    final UserService userService;

    /**
     * 方式1：会有重复提交问题
     *
     * @param user
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/register1")
    public String register1(@RequestBody User1 user) throws InterruptedException {
        userService.register1(user);
        return "注册成功";
    }

    /**
     * 方式2：加入了逻辑判断，但是还是会有重复提交问题
     *
     * @param user
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/register2")
    public String register2(@RequestBody User1 user) throws InterruptedException {
        userService.register2(user);
        return "注册成功";
    }

    /**
     * 方式3：使用本地缓存记录标识，不会有重复提交问题
     *
     * @param param
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/register3")
    public String register3(@RequestBody UserParam param) throws InterruptedException {
        userService.register3(param);
        return "注册成功";
    }

    /**
     * 方式4：使用用户+请求路径加锁，不会有重复提交问题
     *
     * @param user
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/register4")
    public String register4(@RequestBody User1 user,
                       HttpServletRequest request) throws InterruptedException {
        String remoteAddr = request.getRemoteAddr();
        log.info("用户地址：{}", remoteAddr);
        userService.register4(user, remoteAddr+"/user/way4");
        return "注册成功";
    }

    /**
     * 方式5：使用Redis分布式锁记录标识，不会有重复提交问题
     *
     * @param param
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/register5")
    public String register5(@RequestBody UserParam param) throws InterruptedException {
        userService.register5(param);
        return "注册成功";
    }

    /**
     * 方式6：使用数据库唯一约束，
     *
     * @param user
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/register6")
    public String register6(@RequestBody User2 user) throws InterruptedException {
        userService.register6(user);
        return "注册成功";
    }

    /**
     * 使用注解
     */
    @PostMapping("/register7")
    @RepeatLimit(key = "#user.tel")
    public String register7(@RequestBody User1 user) throws InterruptedException {
        userService.register7(user);
        return "注册成功";
    }
}
