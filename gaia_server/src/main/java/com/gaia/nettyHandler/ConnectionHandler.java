package com.gaia.nettyHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ConnectionHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger(ConnectionHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端连接： {}", ctx.channel().remoteAddress());
        String message = "你才是大傻逼";
        ctx.writeAndFlush(Unpooled.copiedBuffer(message, io.netty.util.CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;

            String message = buf.toString(io.netty.util.CharsetUtil.UTF_8);

            logger.info("客户端消息: {}", message);
            buf.release();
        } else {
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("发生异常: ", cause);
        ctx.close();
    }
}