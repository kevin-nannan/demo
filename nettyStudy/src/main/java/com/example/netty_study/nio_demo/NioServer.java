package com.example.netty_study.nio_demo;

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
 * @date 2022/4/9 16:27
 */
public class NioServer {
    public static void main(String[] args) throws IOException {
        test();
    }
    public static void test() throws IOException {
        ServerSocketChannel ssc=ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress("127.0.0.1",8899));
        Selector selector= Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            System.out.println("waiting for event happend");
            selector.select();

            Iterator<SelectionKey> iterator=selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey key=iterator.next();

                if(key.isAcceptable()){
                    System.out.println("有客户端连接，客户端连接事件发生了");
                    ServerSocketChannel inner=(ServerSocketChannel) key.channel();
                    SocketChannel clientSocketChannel=inner.accept();
                    clientSocketChannel.configureBlocking(false);
                    clientSocketChannel.register(selector,SelectionKey.OP_READ);

                }else if(key.isReadable()){
                    System.out.println("有客户端向服务端发送数据了。");
                    SocketChannel clientSocketChannel=(SocketChannel)key.channel();
                    ByteBuffer buffer=ByteBuffer.allocate(20);
                    StringBuffer sb=new StringBuffer();
                    while(clientSocketChannel.read(buffer)>0){
                        buffer.flip();
                        sb.append(new String(buffer.array(),0,(buffer.limit()-buffer.position())));
                    }
                    System.out.println("客户端发来的消息:"+sb);
                    ByteBuffer bufferToWrite=ByteBuffer.wrap("helloclient, I'm server".getBytes());
                    clientSocketChannel.write(bufferToWrite);
                    clientSocketChannel.register(selector,SelectionKey.OP_WRITE);

                }else if(key.isWritable()){
                    System.out.println("write event");
                    key.interestOps(SelectionKey.OP_READ);
                }
                iterator.remove();
            }
        }
    }
}
