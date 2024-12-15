package com.gaia.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import com.gaia.nettyHandler.ConnectionHandler;
import com.gaia.nettyHandler.RpcServerHandler;
import com.gaia.service.ServiceRegistry;

@Slf4j
@Data
public class NettyServer {
    private final int port;
    private final ServiceRegistry serviceRegistry;

    public NettyServer(int port, ServiceRegistry serviceRegistry) {
        this.port = port;
        this.serviceRegistry = serviceRegistry;
    }
    public  void startServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    // ch.pipeline().addLast(new ConnectionHandler());
                    ch.pipeline().addLast(new RpcServerHandler(serviceRegistry));
                }
            });

            ChannelFuture cf = sb.bind(this.port).sync();
            if (cf != null) {
                log.info("=====start Server=========");
            }
            cf.channel().closeFuture().sync();

        } catch (InterruptedException err) {
             log.error("Error occurred while starting the server", err);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
