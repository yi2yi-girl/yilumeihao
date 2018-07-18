package com.springxiaobudian.spring;

import com.springxiaobudian.demo.controller.UserController;
import com.springxiaobudian.spring.anotation.Autowired;
import com.springxiaobudian.spring.anotation.Controller;
import com.springxiaobudian.spring.anotation.Service;
import sun.plugin.com.BeanClass;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yiye on 2018/7/15.
 * user.name xiaobudian
 * user.emal 3080712145@qq.com
 *
 */
public class MyDispatcherServlet extends HttpServlet {
    private Properties contextConfig = new Properties();

    private List<String> classNames = new ArrayList<String>();
    //存储beanDefinition对象的IOC容器
    private Map<String,Object> beanMap = new ConcurrentHashMap<String,Object>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("调用dopost()方法");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //定位
        doResourceConfig( config.getInitParameter("contextConfigLocation"));
        System.out.println("定位资源result:"+contextConfig);
        doScanBeanDefinition(contextConfig.getProperty("scanPackage"));
        System.out.println("加载资源名称beanClass:"+classNames);
        doRegister();
        System.out.println("beanDefinitionsMap:"+beanMap.entrySet());
        doAutowired();
        UserController userController = (UserController) beanMap.get("userController");

        userController.query(null,null,"jack");

    }

    private void doRegister() {
        if(classNames.isEmpty()){
            return;
        }
        for (String className:classNames){
            try {
                Class<?> clazz = Class.forName(className);

                if(clazz.isAnnotationPresent(Controller.class)){
                    String beanName = lowerFirstCase(clazz.getSimpleName());
                    beanMap.put(beanName,clazz.newInstance());

                }else if(clazz.isAnnotationPresent(Service.class)){
                    Service service = clazz.getAnnotation(Service.class);
                    String beanName = service.value();
                    if("".equals(beanName.trim())){
                        beanName = lowerFirstCase(clazz.getSimpleName());
                    }
                    beanMap.put(beanName,clazz.newInstance());
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> i:interfaces){
                        beanMap.put(i.getName(),clazz.newInstance());
                    }
                }else{
                    continue;
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }
    }

    private String lowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private void doAutowired() {
        if(beanMap.isEmpty()){
            return;
        }
        for(Map.Entry<String, Object> entry :beanMap.entrySet()){
            Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
            for (Field field:declaredFields){
                if(!field.isAnnotationPresent(Autowired.class)){
                    continue;
                }
                Autowired autowired = field.getAnnotation(Autowired.class);
                String beanName = autowired.value().trim();
                if("".equals(beanName)){
                    beanName= field.getType().getName();
                }
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(),beanMap.get(beanName));
                    System.out.println("filed:"+entry.getValue() + ">>>>>>>>>>"+beanMap.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void doScanBeanDefinition(String packageName) {
        URL url = this.getClass().getClassLoader().getResource("/" +packageName.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());
        for(File file:classDir.listFiles()){
            if(file.isDirectory()){
                doScanBeanDefinition(packageName + "."+file.getName());
            }else{
                classNames.add(packageName + "."+file.getName().replace(".class",""));
            }

        }


    }

    private void doResourceConfig(String contextConfigName) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigName.replace("classpath:", ""));
        try {
            contextConfig.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
    }

    public static boolean compTime(String s1,String s2){
        try {
            if (s1.indexOf(":")<0||s1.indexOf(":")<0) {
                System.out.println("格式不正确");
            }else{
                String[]array1 = s1.split(":");
                int total1 = Integer.valueOf(array1[0])*3600+Integer.valueOf(array1[1])*60+Integer.valueOf(array1[2]);
                String[]array2 = s2.split(":");
                int total2 = Integer.valueOf(array2[0])*3600+Integer.valueOf(array2[1])*60+Integer.valueOf(array2[2]);
                return total1-total2>0?true:false;
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            return true;
        }
        return false;


    }
}
