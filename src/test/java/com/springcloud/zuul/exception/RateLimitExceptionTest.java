package com.springcloud.zuul.exception;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * @Author: ZQ
 * @Description:
 * @Date created in 18:23 2019/12/20
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RateLimitExceptionTest {

    @Test
    public void test() {
        //每秒生成一个令牌，返回SmoothBursty实现类
        RateLimiter rateLimiter = RateLimiter.create(1D);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService executor = Executors.newCachedThreadPool();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 10; i++) {
            Runnable runnable = () -> {
                try {
                    countDownLatch.await();
                    //尝试获取令牌桶中一个令牌，失败立即返回
                    if (rateLimiter.tryAcquire()) {
                        System.out.println(Thread.currentThread().getName() + ": " + sdf.format(new Date()) + "获取到令牌");
                    } else System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            executor.submit(runnable);
        }
        countDownLatch.countDown();
        executor.shutdown();
    }

    @Test
    public void test2() {
        //每秒生成一个令牌，返回SmoothBursty实现类
        RateLimiter rateLimiter = RateLimiter.create(0.1D);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            Runnable runnable = () -> {
                try {
                    countDownLatch.await();
                    //尝试获取令牌桶中一个令牌，最多等待60秒
                    if (rateLimiter.tryAcquire(60, TimeUnit.SECONDS)) {
                        System.out.println(Thread.currentThread().getName() + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "获取到令牌");
                    } else {
                        System.out.println(Thread.currentThread().getName() + ": byebye");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            executor.submit(runnable);
        }
        countDownLatch.countDown();
        executor.shutdown();

    }

}