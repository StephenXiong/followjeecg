package com.xiong.webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 *@author xiongyf
 *@date 2017年11月5日 下午3:59:46
 *
 */
public class Processor extends Thread {
	
	private Socket socket;
	private InputStream in;
	private PrintStream out;
	public static final String WEB_ROOT="E:\\selfLearn\\";
	
	public Processor(Socket socket) {
		this.socket = socket;
		try {
			in = socket.getInputStream();
			out = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String fileName = parse(in);
		sendFile(fileName);
	}
	
	public String parse(InputStream in){
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String resourcePath = null;
		try {
			String content = br.readLine();
			String[] array = content.split(" ");
			if(array.length != 3) {
				sendErrorMessage(400, "query Error!");
			}
			System.out.println(String.format("method: %s, resourcePath: %s, Http-version: %s", array[0],array[1],array[2]));
			resourcePath = array[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resourcePath;
	}
	
	public void sendErrorMessage(int code, String errorMessage){
		out.println("HTTP/1.1 " + code + " "+ errorMessage);
		out.println("content-type: text/html");
		out.println();
		out.println("<html>");
		out.println("<title>Error Message</title>");
		out.println("<body><h1>ErrorCode: "+code+", message: "+errorMessage+" </h1></body>");
		out.println("</html>");
		out.flush();
		out.close();
	}
	
	public void sendFile(String fileName) {
		File file = new File(WEB_ROOT + fileName);
		if(!file.exists()){
			sendErrorMessage(404, "not found");
		}
		try {
			InputStream in = new FileInputStream(file);
			byte content[] = new byte[(int)file.length()];
			in.read(content);
			out.println("HTTP/1.1 200 queryFile");
			out.println("content-length:"+ content.length);
			out.println();
			out.write(content);
			out.flush();
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
