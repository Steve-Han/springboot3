package org.example;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

//未指定@Configuration注解，需要在启动类加@EnableConfigurationProperties注解
@SpringBootApplication
@ServletComponentScan
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
        //System.exit(SpringApplication.exit(SpringApplication.run(MyApplication.class, args)));
        //
    }

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        System.out.println("quit");
        return () -> 42;
    }
}