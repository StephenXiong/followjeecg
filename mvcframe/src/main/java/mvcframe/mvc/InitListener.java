package mvcframe.mvc;

import java.net.URL;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import mvcframe.base.ConfigBean;
import mvcframe.util.XmlUtils;

public class InitListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("================系統結束");
	}

	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("================系統初始化");
		ServletContext context = arg0.getServletContext();
		String filePath = context.getInitParameter("struts");

		Map<String, ConfigBean> map = XmlUtils.parseConfig(getRealPath(filePath));
		context.setAttribute("xml", map);
	}

	private String getRealPath(String configFile) {
		// 以URL形式获取工程的资源文件 classpath 路径, 得到以file:/为开头的URL
		// 例如返回: file:/D:/workspace/myproject01/WEB-INF/classes/
		URL classPath = Thread.currentThread().getContextClassLoader().getResource("");
		String proFilePath = classPath.toString();

		// 移除开通的file:/六个字符
		proFilePath = proFilePath.substring(5);

		// 如果为window系统下,则把路径中的路径分隔符替换为window系统的文件路径分隔符
		proFilePath = proFilePath.replace("/", java.io.File.separator);

		// 兼容处理最后一个字符是否为 window系统的文件路径分隔符,同时建立 properties 文件路径
		// 例如返回: D:\workspace\myproject01\WEB-INF\classes\config.properties
		if (!proFilePath.endsWith(java.io.File.separator)) {
			proFilePath = proFilePath + java.io.File.separator + configFile.split("\\:")[1];
		} else {
			proFilePath = proFilePath + configFile.split("\\:")[1];
		}
		return proFilePath;
	}

}
