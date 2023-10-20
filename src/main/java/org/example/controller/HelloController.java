package org.example.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Student;
import org.example.service.HelloService;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.Function;

@RestController("/hello")
@Slf4j
public class HelloController {

    @Autowired
    private HelloService helloService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private StudentService studentService;

    @RequestMapping("test1")
    public List<Student> test1(HttpServletRequest request) throws Exception {
        log.info(request.getRequestURL().toString());
        List<Student> all = studentService.findAll();


        log.info(String.format("请求结束: %s", System.currentTimeMillis()));
        //String s = helloService.restTest();
        return all;
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

        return "arg: " + argMap;
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
