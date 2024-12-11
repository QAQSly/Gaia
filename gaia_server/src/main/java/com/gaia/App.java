package com.gaia;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.gaia.server.NettyServer;

import lombok.extern.slf4j.Slf4j;

/**
 * Gaia
 *
 */
@Slf4j
public class App 
{
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main( String[] args )
    {
        log.info("========开启服务============");
        NettyServer.startServer();
    }

}
