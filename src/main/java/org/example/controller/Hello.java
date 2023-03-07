package org.example.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.service.HelloService;
import org.example.utils.SplitListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController("/hello")
@Slf4j
public class Hello {

    @Autowired
    private HelloService helloService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @RequestMapping("test1")
    public String test1(HttpServletRequest request) {
        log.info(request.getRequestURL().toString());
        Teacher person = new Teacher();
        person.run();
        person.setName("jack");
        Gson gson = new Gson();


        log.info(String.format("请求结束: %s", System.currentTimeMillis()));
        String s = helloService.restTest();
        return s;
    }

    @RequestMapping("test2")
    public String test2(@RequestParam HashMap<String, Objects> argMap) {
        if (argMap.size() == 0) {
            return null;
        }
        return "arg: " + argMap;
    }

    @RequestMapping("test3")
    public String test3(@RequestParam HashMap<String, Objects> argMap) {
        ArrayList<Future<String>> list = new ArrayList<>();
        for (int i = 0; i < 18000; i++) {
            //list.add(i + "");
            int finalI = i;
            Future<String> submit = taskExecutor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + finalI);
                    return finalI + "";
                }
            });

            list.add(submit);
        }


        System.out.println(list.size());
        int maximumPoolSize = taskExecutor.getThreadPoolExecutor().getMaximumPoolSize();
        System.out.println("maximumPoolSize: " + maximumPoolSize);
        BlockingQueue<Runnable> queue = taskExecutor.getThreadPoolExecutor().getQueue();
        System.out.println("queue size: "+queue.size());

        /*List<List<String>> split = SplitListUtils.split(list, 100);
        List<Future<String>> result = new ArrayList<>();
        for (List<String> subList : split) {
            Future<String> submit = taskExecutor.submit(new ExeTask(subList));
            result.add(submit);
        }

        StringBuilder res = new StringBuilder();
        for (Future<String> future : result) {
            try {
                String s = future.get();
                res.append(s);
                taskExecutor.submit(() -> {
                    char[] chars = s.toCharArray();
                    for (char aChar : chars) {
                        //System.out.println(Thread.currentThread().getName() + ": " + aChar);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }*/
        return "arg: "+list.size();
    }

    static class ExeTask implements Callable<String> {
        private List<String> list;

        ExeTask(List<String> list) {
            this.list = list;
        }

        @Override
        public String call() throws Exception {
            int cnt = 0;
            long tmpTime = 0;
            String msg = null;
            List<String> results = new ArrayList<String>();
            if (!CollectionUtils.isEmpty(list)) {
                Thread.sleep(1000);
                cnt = list.size();
                //System.out.println("ExeTask~~~" + Thread.currentThread().getName());
            }
            return cnt + "";
        }
    }

}
