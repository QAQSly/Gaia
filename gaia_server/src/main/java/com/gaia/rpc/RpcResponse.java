package com.gaia.rpc;

import lombok.Data;

@Data
public class RpcResponse {
    private Object result;
    private String err;
}
