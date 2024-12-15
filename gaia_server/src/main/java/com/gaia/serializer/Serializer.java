package com.gaia.serializer;

public interface Serializer {
    
    byte[] serializer(Object obj); // 序列化
    <T> T deserializer(byte[] bytes, Class<T> clazz); // 反序列化
    <T> T deserializerJson(String json, Class<T> clazz); // 反序列化(json)

    
}
