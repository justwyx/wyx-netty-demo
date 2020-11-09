package com.wyx.niosocketdemo.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

/**
 * @author : Just wyx
 * @Description : TODO 2020/11/9
 * @Date : 2020/11/9
 */
public class NioServer {
	public static void main(String[] args) throws IOException {
		// 创建一个服务端channel
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		// 指定channel采用的为阻塞模式
		serverChannel.configureBlocking(false);
		// 指定要监听端口
		serverChannel.bind(new InetSocketAddress(8888));
		// 创建一个多路复用selector
		Selector selector = Selector.open();
		// 将channel注册到selector，并告诉selector让其监听"接收client 连接事件"
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			if (selector.select(1000) == 0) {
				System.out.println("没有就绪的连接");
				continue;
			}
			// 获取所有就绪的channel的key
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			for (SelectionKey key : selectionKeys) {
				// 若当前key为OP_ACCEPT,则说明当前channel是可以接收客户端连接的
				// 那么这里的代码就是用于接收客户端连接的
				if (key.isAcceptable()) {
					System.out.println("接收client 连接");
					// 获取连接到server的客户端channel，其是客户端channel在server端的代表(驻京办)
					SocketChannel clientChannel = serverChannel.accept();
					clientChannel.configureBlocking(false);
					// 将客户端channel注册到selector，并告诉selector让其监听这个channel中是否发生了读事件
					clientChannel.register(selector, SelectionKey.OP_READ);
				}

				// 获当前key为OP_READ，则说明当前channel中有客户端发送来的数据
				// 那么这里的代码就是用于读取channel中的数据的
				if (key.isReadable()) {
					try {
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						// 根据key获取其对应的channel
						SocketChannel clientChannel = (SocketChannel) key.channel();
						// 把channel中的数据读取到buffer
						clientChannel.read(buffer);
					} catch (IOException e) {
						key.cancel();
					}
				}
				// 删除重复的key，避免重复处理
				selectionKeys.remove(key);
			} // end for
		}
	}
}
