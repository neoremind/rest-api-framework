package com.hulu.ap.apiframework.client;

import java.lang.reflect.Field;

/**
 * Reflection utility
 *
 * @author xu.zhang
 */
public class ReflectionUtil {

    /**
     * Get field
     *
     * @param clazz     class
     * @param fieldName field name
     * @return field
     */
    static Field getField(Class<?> clazz, String fieldName) {
        for (Class<?> itr = clazz; hasSuperClass(itr); ) {
            Field[] fields = itr.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    return field;
                }
            }

            itr = itr.getSuperclass();
        }

        return null;
    }

    /**
     * has super class
     *
     * @param clazz class
     * @return if has super class
     */
    public static boolean hasSuperClass(Class<?> clazz) {
        return (clazz != null) && !clazz.equals(Object.class);
    }

}
