package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class DummyUserService implements UserService {
    GreetingService greetingService;

    public DummyUserService(GreetingService greetingService) {
        this.greetingService=greetingService;
    }

    @Override
    public void greetAll() {

    }

}
