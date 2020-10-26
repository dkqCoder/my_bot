package com.tty.data.common.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rxu
 * @create 2017-02-09 16:42:58
 *
 * 添加新的copy方法支持 {@link BeanUtilsBean#copyProperties(Object, Object)} 值为null导致抛出异常的问题
 **/
public class BeanCopyUtil extends BeanUtilsBean {
    private static final Logger log = LoggerFactory.getLogger(BeanCopyUtil.class);


    private static class SingletonFactory{
        private static BeanCopyUtil instance = new BeanCopyUtil();
    }
    public static BeanCopyUtil getInstance(){
        return SingletonFactory.instance;
    }

    /**
     *
     * @param dest 目标对象
     * @param orig 源对象
     * @param ignoreNullFlag 是否忽略null值
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public void copyProperties(Object dest, Object orig,boolean ignoreNullFlag)
            throws IllegalAccessException, InvocationTargetException {


        // Validate existence of the specified beans
        if (dest == null) {
            throw new IllegalArgumentException
                    ("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }
        if (log.isDebugEnabled()) {
            log.debug("BeanUtils.copyProperties(" + dest + ", " +
                    orig + "," + ignoreNullFlag + ")");
        }


        // Copy the properties, converting as necessary
        if (orig instanceof DynaBean) {
            DynaProperty[] origDescriptors =
                    ((DynaBean) orig).getDynaClass().getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                // Need to check isReadable() for WrapDynaBean
                // (see Jira issue# BEANUTILS-61)
                if (getPropertyUtils().isReadable(orig, name) &&
                        getPropertyUtils().isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    if(ignoreNullFlag){
                        if(value!=null){
                            copyProperty(dest, name, value);
                        }
                    }else{
                        copyProperty(dest, name, value);
                    }
                }
            }
        } else if (orig instanceof Map) {
            @SuppressWarnings("unchecked")
            // Map properties are always of type <String, Object>
                    Map<String, Object> propMap = (Map<String, Object>) orig;
            for (Map.Entry<String, Object> entry : propMap.entrySet()) {
                String name = entry.getKey();
                Object value=entry.getValue();
                if (getPropertyUtils().isWriteable(dest, name)) {
                    if(ignoreNullFlag){
                        if(value!=null){
                            copyProperty(dest, name, value);
                        }
                    }else{
                        copyProperty(dest, name, value);
                    }
                }
            }
        } else /* if (orig is a standard JavaBean) */ {
            PropertyDescriptor[] origDescriptors =
                    getPropertyUtils().getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                if (getPropertyUtils().isReadable(orig, name) &&
                        getPropertyUtils().isWriteable(dest, name)) {
                    try {
                        Object value =
                                getPropertyUtils().getSimpleProperty(orig, name);
                        if(ignoreNullFlag){
                            if(value!=null){
                                copyProperty(dest, name, value);
                            }
                        }else{
                            copyProperty(dest, name, value);
                        }
                    } catch (NoSuchMethodException e) {
                        // Should not happen
                    }
                }
            }
        }
    }
}
