package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Student;
import org.example.service.HelloService;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.function.Function;

@RestController("/hello")
@Slf4j
public class HelloController {

    @Autowired
    private HelloService helloService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @RequestMapping("test1/{id}")
    public String test1(HttpServletRequest request, @PathVariable("id") String id) throws Exception {
        log.info(request.getRequestURL().toString());
        /*if ("sh".equals(id)) {
            helloService.sendToOneMessage();
        } else if ("cd".equals(id)) {
            helloService.sendToOneMessageCD();
        }*/

        String s = helloService.testRemoteCRM(id);

        log.info(String.format("请求结束: %s", System.currentTimeMillis()));
        //String s = helloService.restTest();
        return s;
    }

    @RequestMapping("test2")
    public String test2(@RequestParam HashMap<String, Objects> argMap) {
        if (argMap.size() == 0) {
            return null;
        }

        Function<Object, Object> function = new Function<>() {
            @Override
            public Object apply(Object o) {
                return o.toString();
            }
        };

        return  "arg: " + argMap;
    }

    public static void from(Function<Object, Object> function) {
        Object apply = function.apply(new ArrayList<String>());
        System.out.println("apply+" + apply.toString());
    }

    @RequestMapping("test3")
    public String test3(@RequestParam HashMap<String, Objects> argMap) throws IOException {
        ArrayList<Future<String>> list = new ArrayList<>();

        return "";
    }

}
