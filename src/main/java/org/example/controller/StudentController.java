package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.example.entity.Student;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

@RestController("/student")
@Slf4j
public class StudentController {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;


    @RequestMapping("getAll")
    public List<Student> test1(HttpServletRequest request) throws Exception {
        log.info(request.getRequestURL().toString());
        List<Student> all = null;


        log.info(String.format("请求结束: %s", System.currentTimeMillis()));
        //String s = helloService.restTest();
        return all;
    }

    @RequestMapping("saveStudent")
    public String test2(@RequestParam HashMap<String, String> argMap) throws Exception {
        if (argMap.isEmpty()) {
            return null;
        }

        String name = argMap.get("name");
        Integer age = Integer.valueOf(argMap.get("age"));
        String birth = argMap.get("birth");
        Date birthdate = DateUtils.parseDate(birth, "yyyy-MM-dd");



        return "arg: ";
    }

    public static void from(Function<Object, Object> function) {
        Object apply = function.apply(new ArrayList<String>());
        System.out.println("apply+" + apply.toString());
    }

    @RequestMapping("modifyStudent")
    public Integer modifyStudent(@RequestParam HashMap<String, String> argMap) {
        if (argMap.isEmpty()) {
            return null;
        }

        String name = argMap.get("name");
        Integer id = Integer.valueOf(argMap.get("id"));

        Integer modified = 1;

        System.out.println(modified);

        return modified;
    }

    @RequestMapping("findById/{id}")
    public Integer findById(@PathVariable("id") Integer id) {

        System.out.println(id);

        return id;
    }
}
