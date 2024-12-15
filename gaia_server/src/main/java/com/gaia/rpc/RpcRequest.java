package com.gaia.rpc;

import lombok.Data;

@Data
public class RpcRequest  {
    private String methodName;
    private String className;
    private Object[] parameters;
    private Class<?>[] paramTypes; 
}
