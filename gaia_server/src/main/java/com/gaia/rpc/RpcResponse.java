package com.gaia.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RpcResponse {
    private Object result;
    private String err;
}
