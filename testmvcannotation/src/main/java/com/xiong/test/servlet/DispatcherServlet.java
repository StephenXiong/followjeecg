package com.xiong.test.servlet;

import com.xiong.test.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.*;

/**
 * @author xiongyufeng
 * @version 2018/7/4
 */
public class DispatcherServlet extends HttpServlet {

    private List<String> packageNames = new ArrayList<>();
    private Map<String, Object> instanceMap = new HashMap<>();
    private Map<String, Object> handlerMap = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();
        String ctValue = path.split("/")[1];
        Object object = instanceMap.get(ctValue);
        Method method = (Method) handlerMap.get(path);
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(RequestParam.class)) {
                RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
                String rpValue = requestParam.value();
                String value = req.getParameter(rpValue);
                Class clazz = parameters[i].getType();
                if ("int".equals(clazz.getName())) {
                    args[i] = Integer.valueOf(value);
                } else if ("java.lang.String".equals(clazz.getName())) {
                    args[i] = value;
                }

            }
        }
        try {
            Object obj = null;
            if (parameters.length > 0) {
                obj = method.invoke(object, args);
            } else {
                obj = method.invoke(object, null);
            }
            resp.getWriter().print(obj.toString());
        } catch (Exception e) {
            resp.getWriter().print(e.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void init(ServletConfig arg0) {
        scanPackages("com.xiong.test");
        try {
            filterAndInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler();
        ioc();
    }

    private void ioc() {
        if (instanceMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String awValue = autowired.value();
                    try {
                        field.set(entry.getValue(), instanceMap.get(awValue));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    field.setAccessible(false);
                }
            }
        }
    }

    private void handler() {
        if (instanceMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
            Class clazz = entry.getValue().getClass();
            if (clazz.isAnnotationPresent(Controller.class)) {
                Controller controller = (Controller) clazz.getAnnotation(Controller.class);
                String ctValue = controller.value();
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        String reValue = requestMapping.value();
                        handlerMap.put("/" + ctValue + "/" + reValue, method);
                    }
                }
            }
        }
    }

    private void filterAndInstance() throws Exception {
        if (packageNames.size() < 0) {
            return;
        }
        for (String packageName : packageNames) {
            Class clazz = Class.forName(packageName);
            Object instance = null;
            if (clazz.isAnnotationPresent(Controller.class)) {
                instance = clazz.newInstance();
                Controller controller = (Controller) clazz.getAnnotation(Controller.class);
                String ctValue = controller.value();
                instanceMap.put(ctValue, instance);
            } else if (clazz.isAnnotationPresent(Service.class)) {
                instance = clazz.newInstance();
                Service service = (Service) clazz.getAnnotation(Service.class);
                String srValue = service.value();
                instanceMap.put(srValue, instance);
            }
        }
    }

    private void scanPackages(String packageName) {
        URL url = this.getClass().getClassLoader().getResource("/" + replaceTo(packageName));
        String fileName = url.getFile();
        File file = new File(fileName);
        String[] packageFileList = file.list();
        for (String packageFile : packageFileList) {
            File eachFile = new File(fileName + packageFile);
            if (eachFile.isDirectory()) {
                scanPackages(packageName + "." + eachFile.getName());
            } else {
                packageNames.add(packageName + "." + eachFile.getName().substring(0, eachFile.getName().indexOf(".")));
            }
        }
    }

    private String replaceTo(String packageName) {
        return packageName.replaceAll("\\.", "/");
    }
}
