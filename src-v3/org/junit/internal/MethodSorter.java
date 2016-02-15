package org.junit.internal;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import org.junit.FixMethodOrder;

public class MethodSorter {
    public static Comparator<Method> DEFAULT;
    public static Comparator<Method> NAME_ASCENDING;

    /* renamed from: org.junit.internal.MethodSorter.1 */
    static class C06951 implements Comparator<Method> {
        C06951() {
        }

        public int compare(Method m1, Method m2) {
            int i1 = m1.getName().hashCode();
            int i2 = m2.getName().hashCode();
            if (i1 != i2) {
                return i1 < i2 ? -1 : 1;
            } else {
                return MethodSorter.NAME_ASCENDING.compare(m1, m2);
            }
        }
    }

    /* renamed from: org.junit.internal.MethodSorter.2 */
    static class C06962 implements Comparator<Method> {
        C06962() {
        }

        public int compare(Method m1, Method m2) {
            int comparison = m1.getName().compareTo(m2.getName());
            return comparison != 0 ? comparison : m1.toString().compareTo(m2.toString());
        }
    }

    static {
        DEFAULT = new C06951();
        NAME_ASCENDING = new C06962();
    }

    public static Method[] getDeclaredMethods(Class<?> clazz) {
        Comparator<Method> comparator = getSorter((FixMethodOrder) clazz.getAnnotation(FixMethodOrder.class));
        Method[] methods = clazz.getDeclaredMethods();
        if (comparator != null) {
            Arrays.sort(methods, comparator);
        }
        return methods;
    }

    private MethodSorter() {
    }

    private static Comparator<Method> getSorter(FixMethodOrder fixMethodOrder) {
        if (fixMethodOrder == null) {
            return DEFAULT;
        }
        return fixMethodOrder.value().getComparator();
    }
}
