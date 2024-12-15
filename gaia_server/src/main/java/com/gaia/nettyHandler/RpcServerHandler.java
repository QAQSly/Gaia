package com.gaia.nettyHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCounted;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

import com.gaia.rpc.RpcRequest;
import com.gaia.rpc.RpcResponse;
import com.gaia.serializer.SerializerImp;
import com.gaia.service.ServiceRegistry;

@SuppressWarnings("rawtypes")
@Slf4j
public class RpcServerHandler extends SimpleChannelInboundHandler {
    private final ServiceRegistry serviceRegistry;
    private RpcRequest request;
    private final SerializerImp serializerImp = new SerializerImp();

    public RpcServerHandler(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object arg1) throws Exception {

        if (arg1 instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) arg1;

            String message = byteBuf.toString(io.netty.util.CharsetUtil.UTF_8);
            log.info("接收到消息：{}", message);

            try {

        
                request = serializerImp.deserializerJson(message, RpcRequest.class);

            } catch (Exception e) {
                log.info("反序列化失败");
            } finally {
                byteBuf.release();
            }
           
            
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
