package com.example.netty_study.nio_demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhangchengkai
 * @date 2022/4/9 16:15
 */
public class NioClient {
    public static void main(String[] args) throws IOException {
        test();
    }

    public static void test() throws IOException {
        SocketChannel clientSocketChannel = SocketChannel.open();
        clientSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        clientSocketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));
        clientSocketChannel.register(selector, SelectionKey.OP_CONNECT);

        while (true) {
            int select = selector.select();
            System.out.println(select);
            Set<SelectionKey> set = selector.selectedKeys();
            System.out.println("created event times: ===>" + set.size());
            Iterator<SelectionKey> iterable = set.iterator();
            while (iterable.hasNext()) {
                SelectionKey key = iterable.next();
                SocketChannel socketChannel = (SocketChannel) key.channel();
                if (key.isConnectable()) {
                    if (socketChannel.isConnectionPending()) {
                        socketChannel.finishConnect();
                    }
                    socketChannel.write(ByteBuffer.wrap("i have been connected server.".getBytes()));
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(30);
                    int len;
                    while ((len = socketChannel.read(byteBuffer)) > 0) {
                        System.out.println(new String(byteBuffer.array(), 0, len));
                        byteBuffer.flip();
                    }
                }
                iterable.remove();

            }
        }
    }
}
