package com.xy.util;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by Administrator on 2019/9/10.
 */
public class BeanUtil {
    //传入className字符串作为参数，只是想利用反射来实现这个功能
    //也可以传入Object obj一个对象，就看自己的设计了
    public BeanUtil(){}
    public  Object getBean(HttpServletRequest request, String className) {
        try {
            //className为JavaBean路径，获取Class

            Class c = Class.forName(className);
            //利用反射读取构造，创建bean对象
            Object obj = c.getConstructor().newInstance();
            //利用request获取所有表单项name，同时规范要求bean的属性名和表单项名必须一致。
            Enumeration<String> enu = request.getParameterNames();
            while (enu.hasMoreElements()) {
                String fieldName = enu.nextElement();
                //利用属性名获取set/get方法名
                String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                //获取无参的get方法
                Field f;
                String mMethodName=fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try{
                        f = c.getDeclaredField(mMethodName);
                    if(f.getName()!=null){
                        Method getMethod = c.getMethod(getMethodName, null);
                        //利用无参有返回值的get方法获得对应的set方法（get方法返回类型与set方法参数录入类型一致）
                        Method setMethod = c.getMethod(setMethodName, getMethod.getReturnType());
                        //调用录入具体的参数值，保存到bean对象中。
                        String value = request.getParameter(fieldName);
                        //因为set方法中的参数类型不一样，因此要做相应的转换
                        switch (getMethod.getReturnType().getName()) {
                            case "int":
                                setMethod.invoke(obj, Integer.parseInt(value));
                                break;
                            case "float":
                                setMethod.invoke(obj, Float.parseFloat(value));
                                break;
                            case "double":
                                setMethod.invoke(obj, Double.parseDouble(value));
                                break;
                            case "long":
                                setMethod.invoke(obj, Long.parseLong(value));
                                break;
                            default:
                                setMethod.invoke(obj, value);
                        }
                    }
                }catch (Exception e){

                }

            }
            return obj;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }
}
