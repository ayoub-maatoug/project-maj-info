package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(OutputCaptureExtension.class) // 1
@ExtendWith(SpringExtension.class) // 1
public class DummyUserServiceTest {

    @Configuration // 2
    @ComponentScan("com.emse.spring.faircorp.hello")
    public static class DummyUserServiceTestConfig{}

    @Autowired // 3
    public DummyUserService dummyUserService;


    @Test
    public void testGreetingAll(CapturedOutput output) {
        dummyUserService.greetAll();
        Assertions.assertThat(output.getAll()).contains("Hello, Elodie!", "Hello, Elodie!");

    }
}
