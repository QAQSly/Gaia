package com.gaia.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SerializerImp implements Serializer {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serializer(Object obj) {
        try {
            
            return objectMapper.writeValueAsString(obj).getBytes();
        } catch (Exception e) {
            throw new RuntimeException("序列化失败", e);
        }
    }

    @Override
    public <T> T deserializer(byte[] bytes, Class<T> clazz) {
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (Exception e) {
            throw new RuntimeException("反序列化失败", e);
        }
    }

    @Override
    public <T> T deserializerJson(String json, Class<T> clazz) {
        try {
           return objectMapper.readValue(json, clazz); 
        } catch (Exception e) {
            log.info("反序列化失败", e);
            throw new RuntimeException("反序列化失败", e);
        }
    }

    @Override
    public String serializerJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("序列化失败", e);
        }
    }

}
