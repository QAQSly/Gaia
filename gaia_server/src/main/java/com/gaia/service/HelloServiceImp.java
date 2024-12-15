package com.gaia.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloServiceImp implements HelloService {

    @Override
    public String sayHello(String name) {
        log.info("=========HelloServiceImp=========");
        return "Hello " + name;   
    }
    
}
