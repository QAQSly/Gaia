package com.gaia;

import com.gaia.server.NettyServer;
import com.gaia.service.HelloService;
import com.gaia.service.HelloServiceImp;
import com.gaia.service.ServiceRegistry;

import lombok.extern.slf4j.Slf4j;

/**
 * Gaia
 *
 */
@Slf4j
public class App 
{

    public static void main( String[] args )
    {
        log.info("========开启服务============");
        ServiceRegistry serviceRegistry = new ServiceRegistry();
        serviceRegistry.register(HelloService.class.getName(), new HelloServiceImp());
        NettyServer server = new NettyServer(8080, serviceRegistry);
        server.startServer();
        // NettyServer.startServer();
    }

}
