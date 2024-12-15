package com.gaia.rpc;

import lombok.Data;

@Data
public class RpcRequest  {
    private String methodName;
    private String ClassName;
    private Object[] parameters;
    private Class<?>[] paramTypes; 
}
