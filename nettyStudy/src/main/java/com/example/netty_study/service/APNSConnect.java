package com.example.netty_study.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class APNSConnect {

    private static final Logger logger = LoggerFactory.getLogger(APNSConnect.class);

    private static ApnsClient apnsClient = null;

    public static ApnsClient getAPNSConnect() { 

        if (apnsClient == null) {  
            try {
                EventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);
                apnsClient = new ApnsClientBuilder().setApnsServer(ApnsClientBuilder.DEVELOPMENT_APNS_HOST)
                        .setClientCredentials(new File("C:\\Users\\Administrator\\Desktop\\PushCertificate-HualiSmartWay.p12"), "#edcvfr4%t")
                        .setConcurrentConnections(4).setEventLoopGroup(eventLoopGroup).build();
            } catch (Exception e) {
                logger.error("ios get pushy apns client failed!"); 
                e.printStackTrace();
            } 
        }
        return apnsClient;

    }
}
