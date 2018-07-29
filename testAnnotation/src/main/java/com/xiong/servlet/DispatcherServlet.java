package com.xiong.servlet;

import com.xiong.annotation.Autowired;
import com.xiong.annotation.Controller;
import com.xiong.annotation.RequestMapping;
import com.xiong.annotation.Service;
import com.xiong.controller.MyController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiongyufeng
 * @version 2018/6/27
 */
public class DispatcherServlet extends HttpServlet {

    private List<String> packageNames = new ArrayList<String>();
    private Map<String, Object> instanceMap = new HashMap<String, Object>();
    private Map<String, Object> handlerMap = new HashMap<String, Object>();

    public DispatcherServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) {

        scanPackage("com.xiong");

        try {
            filterAndInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        handlerMap();

        ioc();
    }

    private void ioc() {
        if (instanceMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String value = autowired.value();
                    try {
                        field.set(entry.getValue(), instanceMap.get(value));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                field.setAccessible(false);
            }
        }
    }

    private void handlerMap() {
        if (instanceMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
            if (entry.getValue().getClass().isAnnotationPresent(Controller.class)) {
                Controller controller = entry.getValue().getClass().getAnnotation(Controller.class);
                String ctValue = controller.value();
                Method[] methods = entry.getValue().getClass().getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        String rmValue = requestMapping.value();
                        handlerMap.put("/" + ctValue + "/" + rmValue, method);
                    }
                }
            }
        }
    }

    private void filterAndInstance() throws Exception {
        if (packageNames.size() <= 0) {
            return;
        }

        for (String className : packageNames) {
            Class clazz = Class.forName(className);
            if (clazz.isAnnotationPresent(Controller.class)) {
                Object instance = clazz.newInstance();
                Controller controller = (Controller) clazz.getAnnotation(Controller.class);
                String key = controller.value();
                instanceMap.put(key, instance);
            } else if (clazz.isAnnotationPresent(Service.class)) {
                Object instance = clazz.newInstance();
                Service service = (Service) clazz.getAnnotation(Service.class);
                String key = service.value();
                instanceMap.put(key, instance);
            }
        }
    }

    private void scanPackage(String packages) {
        URL url = this.getClass().getClassLoader().getResource("/" + replaceTo(packages));
        String pathFile = url.getFile();
        File file = new File(pathFile);
        String[] fileList = file.list();
        for (String path : fileList) {
            System.out.println("=====" + path);
            File eachFile = new File(pathFile + path);
            if (eachFile.isDirectory()) {
                scanPackage(packages + "." + eachFile.getName());
            } else {
                packageNames.add(packages + "." + eachFile.getName().substring(0, eachFile.getName().indexOf(".")));
            }
        }
    }

    private String replaceTo(String path) {
        return path.replaceAll("\\.", "/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String context = req.getContextPath();
        String path = url.replace(context, "");
        Method method = (Method) handlerMap.get(path);
        MyController controller = (MyController) instanceMap.get(path.split("/")[1]);
        try {
            method.invoke(controller, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
