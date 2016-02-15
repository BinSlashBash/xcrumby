/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

public class MethodSorter {
    public static Comparator<Method> DEFAULT = new Comparator<Method>(){

        @Override
        public int compare(Method method, Method method2) {
            int n2;
            int n3 = method.getName().hashCode();
            if (n3 != (n2 = method2.getName().hashCode())) {
                if (n3 < n2) {
                    return -1;
                }
                return 1;
            }
            return MethodSorter.NAME_ASCENDING.compare(method, method2);
        }
    };
    public static Comparator<Method> NAME_ASCENDING = new Comparator<Method>(){

        @Override
        public int compare(Method method, Method method2) {
            int n2 = method.getName().compareTo(method2.getName());
            if (n2 != 0) {
                return n2;
            }
            return method.toString().compareTo(method2.toString());
        }
    };

    private MethodSorter() {
    }

    public static Method[] getDeclaredMethods(Class<?> arrmethod) {
        Comparator<Method> comparator = MethodSorter.getSorter((FixMethodOrder)arrmethod.getAnnotation(FixMethodOrder.class));
        arrmethod = arrmethod.getDeclaredMethods();
        if (comparator != null) {
            Arrays.sort(arrmethod, comparator);
        }
        return arrmethod;
    }

    private static Comparator<Method> getSorter(FixMethodOrder fixMethodOrder) {
        if (fixMethodOrder == null) {
            return DEFAULT;
        }
        return fixMethodOrder.value().getComparator();
    }

}

