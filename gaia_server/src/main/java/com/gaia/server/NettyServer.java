package com.gaia.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NettyServer {
     private static final Logger logger = LogManager.getLogger(NettyServer.class);
    public void startServer() {
        EventLoopGroup bossGrop = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGrop, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new BusinessHandler());
                }
            });

            ChannelFuture cf = sb.bind(8080).sync();
            cf.channel().closeFuture().sync();

        } catch (Exception err) {
            logger.info(err.toString());
        } finally {
            workerGroup.shutdownGracefully();
            bossGrop.shutdownGracefully();
        }
    }
}