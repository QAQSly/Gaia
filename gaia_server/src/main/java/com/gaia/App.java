package com.gaia;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.gaia.server.NettyServer;

/**
 * Gaia
 *
 */
public class App 
{
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main( String[] args )
    {
        logger.info("========开启服务============");
        // new NettyServer().startServer();
    }

}
