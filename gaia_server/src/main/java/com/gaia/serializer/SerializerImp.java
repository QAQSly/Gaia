package com.gaia.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializerImp implements Serializer {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serializer(Object obj) {
        try {
            
            objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("序列化失败", e);
        }
        return null;
    }

    @Override
    public <T> T deserializer(byte[] bytes, Class<T> clazz) {
        try {
            objectMapper.readValue(bytes, clazz);
        } catch (Exception e) {
            throw new RuntimeException("反序列化失败", e);
        }
        return null;
    }

}
