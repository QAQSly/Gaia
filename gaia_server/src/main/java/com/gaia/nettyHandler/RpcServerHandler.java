package com.gaia.nettyHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import com.gaia.rpc.RpcRequest;
import com.gaia.rpc.RpcResponse;
import com.gaia.service.ServiceRegistry;

@SuppressWarnings("rawtypes")
@Slf4j
public class RpcServerHandler extends SimpleChannelInboundHandler {
    private final ServiceRegistry serviceRegistry;
    private RpcRequest request;

    public RpcServerHandler(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object arg1) throws Exception {

        if (arg1 instanceof RpcRequest) {
            request = (RpcRequest) arg1;
        }
        Object service = serviceRegistry.getRegister(request.getClassName());

        if (service == null) {
            ctx.writeAndFlush(new RpcResponse(null, "服务不存在"));
            return;
        }

        Object result = service.getClass().getMethod(request.getClassName(), request.getParamTypes()).invoke(service, request.getParameters());
        ctx.writeAndFlush(new RpcResponse(result, null));
    }
    
}
