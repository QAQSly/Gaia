package com.gaia.nettyHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;



import com.gaia.rpc.RpcRequest;
import com.gaia.rpc.RpcResponse;
import com.gaia.serializer.SerializerImp;
import com.gaia.service.ServiceRegistry;


@Slf4j
public class RpcServerHandler extends ChannelInboundHandlerAdapter {
    private final ServiceRegistry serviceRegistry;
    private RpcRequest request;
    private final SerializerImp serializerImp = new SerializerImp();

    public RpcServerHandler(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object arg1) throws Exception {

        if (arg1 instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) arg1;

            String message = byteBuf.toString(io.netty.util.CharsetUtil.UTF_8);
            log.info("接收到消息：{}", message);

            try {

        
                request = serializerImp.deserializerJson(message, RpcRequest.class);

                if (request != null) {
                    log.info("反序列化成功" +
                    "类名: " + 
                    request.getClassName() + 
                    "方法名: " +
                    request.getMethodName());
                }

            } catch (Exception e) {
                log.info("反序列化失败");
            } finally {
                byteBuf.release();
            }
           
            
        }

     
        Object service = serviceRegistry.getRegister(request.getClassName());

        if (service == null) {
            String message = serializerImp.serializerJson(new RpcResponse(null, "服务不存在"));
            ByteBuf buf = Unpooled.copiedBuffer(message, io.netty.util.CharsetUtil.UTF_8);
            ctx.writeAndFlush(buf);
            return;
        }

        Object result = service.getClass().getMethod(request.getMethodName(), request.getParamTypes()).invoke(service, request.getParameters());
        String message = serializerImp.serializerJson(new RpcResponse(result, null));
        log.info("响应序列化" + message);
        ByteBuf buf = Unpooled.copiedBuffer(message, io.netty.util.CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);
    }

     @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("发生异常: ", cause);
        ctx.close();
    }
    
}
