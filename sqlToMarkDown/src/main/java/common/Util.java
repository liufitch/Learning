package common;

import constants.Constants;
import entity.TableEntity;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

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
}
