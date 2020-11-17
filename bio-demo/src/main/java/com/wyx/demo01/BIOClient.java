package com.wyx.demo01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Description : 客户端连接
 * @author : Just wyx
 * @Date : 2020/11/17
 */
public class BIOClient {
	public static void main(String[] args) throws IOException {
		// 创建连接
		Socket socket = new Socket("127.0.0.1", 9999);
		// 从连接中获取输出流并发送消息
		OutputStream outputStream = socket.getOutputStream();
		System.out.println("请输入：");
		Scanner scanner = new Scanner(System.in);
		String message = scanner.nextLine();
		// 向服务器发送消息
		outputStream.write(message.getBytes());

		// 从连接中取出输入流并接收回话
		InputStream inputStream = socket.getInputStream();
		byte[] bytes = new byte[1024];
		inputStream.read(bytes);
		message = new String(bytes);
		System.out.println("服务端返回：" + message);
		socket.close();
	}
}
