package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.system.CapturedOutput;

public class ConsoleGreetingServiceTest {


    @Test
    public void testGreeting( CapturedOutput output) {
        ConsoleGreetingService greetingService = new ConsoleGreetingService(); // 1
        greetingService.greet("Spring");

        Assertions.assertThat(output.getAll()).contains("Hello, Spring!");
    }
}