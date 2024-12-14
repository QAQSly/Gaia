package com.gaia.nettyHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

@Slf4j
public class ConnectionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端连接： {}", ctx.channel().remoteAddress());
        String message = "你才是大傻逼";
        ctx.writeAndFlush(Unpooled.copiedBuffer(message, io.netty.util.CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;

            String message = buf.toString(io.netty.util.CharsetUtil.UTF_8);

            log.info("客户端消息: {}", message);
            buf.release();
        } else {
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("发生异常: ", cause);
        ctx.close();
    }
}