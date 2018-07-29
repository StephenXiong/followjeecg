package com.xiong.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author xiongyf
 * @date 2017年11月5日 下午3:59:00
 *
 */
public class WebServer {

	private ServerSocket serverSocket = null;

	public WebServer(int port) {
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void webStart() {
		Socket socket = null;
		try {
			while (true) {
				socket = serverSocket.accept();
				new Processor(socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		int port = args.length > 0 ? Integer.valueOf(args[0]) : 80;
		new WebServer(port).webStart();
	}
}
