package com.gaia.service;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceRegistry {
    private final Map<String, Object> services = new HashMap<>();

    public void register(String className, Object serviceImp) {
        services.put(className, serviceImp);
        log.info("注册服务" + className);
    }

    public Object getRegister(String className) {
        log.info("获取服务" + className);
        return services.get(className);
    }
}