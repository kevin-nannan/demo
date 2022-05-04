package com.example.netty_study.nio_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author zhangchengkai
 * @Description
 * @createTime 2022-04-10 14:55
 * @other
 */

public class Start {
    private static final Logger log= LoggerFactory.getLogger(Start.class);
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("the nioServer is start");
                    NioServer.test();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("the nioClient is start");
                    NioClient.test();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
