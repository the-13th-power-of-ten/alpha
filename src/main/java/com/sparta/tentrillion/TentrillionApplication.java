package com.sparta.tentrillion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class TentrillionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TentrillionApplication.class, args);
    }

}