package com.example.demo;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Random;


@SpringBootTest
@Rollback(false)
class DemoApplicationTests {
    @Autowired
    UserService userService;

    @Test
    void testWay1() throws Throwable {
        TestRunnable[] testRunnables = new TestRunnable[10];
        for (int i = 0; i < 10; i++) {
            testRunnables[i] = new TestRunnable() {
                @Override
                public void runTest() throws Throwable {
                    User1 user = new User1();
                    user.setTel("17639600474");
                    user.setPassword("123456");
                    userService.way1(user);
                }
            };
        }
        MultiThreadedTestRunner multiThreadedTestRunner = new MultiThreadedTestRunner(testRunnables);
        multiThreadedTestRunner.runTestRunnables();
    }

    @Test
    void testWay2() throws Throwable {
        TestRunnable[] testRunnables = new TestRunnable[10];
        for (int i = 0; i < 10; i++) {
            testRunnables[i] = new TestRunnable() {
                @Override
                public void runTest() throws Throwable {
                    User1 user = new User1();
                    user.setTel("17621027129");
                    user.setPassword("123456");
                    userService.way2(user);
                }
            };
        }
        MultiThreadedTestRunner multiThreadedTestRunner = new MultiThreadedTestRunner(testRunnables);
        multiThreadedTestRunner.runTestRunnables();
    }

    @Test
    void testWay3() throws Throwable {
        String random = String.valueOf(new Random().nextInt());
        TestRunnable[] testRunnables = new TestRunnable[10];
        for (int i = 0; i < 10; i++) {
            testRunnables[i] = new TestRunnable() {
                @Override
                public void runTest() throws Throwable {
                    UserParam param = new UserParam();
                    param.setTel(random);
                    param.setPassword("123456");
                    param.setMarkId(random);
                    userService.way3(param);
                }
            };
        }
        MultiThreadedTestRunner multiThreadedTestRunner = new MultiThreadedTestRunner(testRunnables);
        multiThreadedTestRunner.runTestRunnables();
    }

    @Test
    void testWay4() throws Throwable {
        TestRunnable[] testRunnables = new TestRunnable[10];
        for (int i = 0; i < 10; i++) {
            testRunnables[i] = new TestRunnable() {
                @Override
                public void runTest() throws Throwable {
                    User1 user = new User1();
                    user.setTel("17521027129");
                    user.setPassword("123456");
                    userService.way4(user, ("127.0.0.1/way4"));
                }
            };
        }
        MultiThreadedTestRunner multiThreadedTestRunner = new MultiThreadedTestRunner(testRunnables);
        multiThreadedTestRunner.runTestRunnables();
    }

    @Test
    void testWay5() throws Throwable {
        String random = String.valueOf(new Random().nextInt());
        TestRunnable[] testRunnables = new TestRunnable[10];
        for (int i = 0; i < 10; i++) {
            testRunnables[i] = new TestRunnable() {
                @Override
                public void runTest() throws Throwable {
                    UserParam param = new UserParam();
                    param.setTel(random);
                    param.setPassword("123456");
                    param.setMarkId(random);
                    userService.way5(param);
                }
            };
        }
        MultiThreadedTestRunner multiThreadedTestRunner = new MultiThreadedTestRunner(testRunnables);
        multiThreadedTestRunner.runTestRunnables();
    }

    @Test
    void testWay6() throws Throwable {
        TestRunnable[] testRunnables = new TestRunnable[10];
        for (int i = 0; i < 10; i++) {
            testRunnables[i] = new TestRunnable() {
                @Override
                public void runTest() throws Throwable {
                    User2 user = new User2();
                    user.setTel("1231231");
                    user.setPassword("123456");
                    userService.way6(user);
                }
            };
        }
        MultiThreadedTestRunner multiThreadedTestRunner = new MultiThreadedTestRunner(testRunnables);
        multiThreadedTestRunner.runTestRunnables();
    }
}
