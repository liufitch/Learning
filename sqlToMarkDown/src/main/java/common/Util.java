package common;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Util {

    public static Object getMethodInvoke(Class clazz){
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor("field", clazz);
            Method getMethod = propertyDescriptor.getReadMethod();
            return getMethod.invoke(clazz);
        }catch (IntrospectionException | InvocationTargetException | IllegalAccessException e){
            e.printStackTrace();
        }
        return null;

    }

    public static void nullToEmpty(Object obj){
       for (Field field : obj.getClass().getDeclaredFields()){
           field.setAccessible(true);
           try {
               if(field.get(obj) == null){
                field.set(obj, "");
               }
           }catch (IllegalAccessException e){
                e.printStackTrace();
           }

       }

    }

}
