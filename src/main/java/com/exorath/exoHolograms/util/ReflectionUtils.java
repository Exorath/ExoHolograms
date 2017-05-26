/*
 * Copyright 2017 Exorath
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.exorath.exoHolograms.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by toonsev on 5/26/2017.
 */
public class ReflectionUtils {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void putInPrivateStaticMap(Class<?> clazz, String fieldName, Object key, Object value) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Map map = (Map) field.get(null);
        map.put(key, value);
    }

    public static void setPrivateField(Class<?> clazz, Object handle, String fieldName, Object value) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(handle, value);
    }

    public static Object getPrivateField(Class<?> clazz, Object handle, String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(handle);
    }

    private static Method getStackTraceElementMethod;
    private static Method getStackTraceDepthMethod;

    private static boolean stackTraceErrorPrinted;

    /**
     * If you only need one stack trace element this is faster than Throwable.getStackTrace()[element],
     * it doesn't generate the full stack trace.
     */
    public static StackTraceElement getStackTraceElement(int index) {
        try {
            if (getStackTraceElementMethod == null) {
                getStackTraceElementMethod = Throwable.class.getDeclaredMethod("getStackTraceElement", int.class);
                getStackTraceElementMethod.setAccessible(true);
            }
            if (getStackTraceDepthMethod == null) {
                getStackTraceDepthMethod = Throwable.class.getDeclaredMethod("getStackTraceDepth");
                getStackTraceDepthMethod.setAccessible(true);
            }

            Throwable dummyThrowable = new Throwable();
            int depth = (Integer) getStackTraceDepthMethod.invoke(dummyThrowable);

            if (index < depth) {
                return (StackTraceElement) getStackTraceElementMethod.invoke(new Throwable(), index);
            } else {
                return null;
            }
        } catch (Throwable t) {
            if (!stackTraceErrorPrinted) {
                t.printStackTrace();
                stackTraceErrorPrinted = true;
            }
            return null;
        }
    }
}
