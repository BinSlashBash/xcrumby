/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.MethodSorter;

@Deprecated
public class TestClass {
    private final Class<?> fClass;

    public TestClass(Class<?> class_) {
        this.fClass = class_;
    }

    private List<Class<?>> getSuperClasses(Class<?> class_) {
        ArrayList arrayList = new ArrayList();
        while (class_ != null) {
            arrayList.add(class_);
            class_ = class_.getSuperclass();
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean isShadowed(Method method, Method method2) {
        if (!method2.getName().equals(method.getName())) {
            return false;
        }
        if (method2.getParameterTypes().length != method.getParameterTypes().length) return false;
        int n2 = 0;
        while (n2 < method2.getParameterTypes().length) {
            if (!method2.getParameterTypes()[n2].equals(method.getParameterTypes()[n2])) return false;
            ++n2;
        }
        return true;
    }

    private boolean isShadowed(Method method, List<Method> object) {
        object = object.iterator();
        while (object.hasNext()) {
            if (!this.isShadowed(method, (Method)object.next())) continue;
            return true;
        }
        return false;
    }

    private boolean runsTopToBottom(Class<? extends Annotation> class_) {
        if (class_.equals((Object)Before.class) || class_.equals((Object)BeforeClass.class)) {
            return true;
        }
        return false;
    }

    List<Method> getAfters() {
        return this.getAnnotatedMethods(AfterClass.class);
    }

    public List<Method> getAnnotatedMethods(Class<? extends Annotation> class_) {
        ArrayList<Method> arrayList = new ArrayList<Method>();
        Iterator iterator = this.getSuperClasses(this.fClass).iterator();
        while (iterator.hasNext()) {
            for (Method method : MethodSorter.getDeclaredMethods(iterator.next())) {
                if (method.getAnnotation(class_) == null || this.isShadowed(method, arrayList)) continue;
                arrayList.add(method);
            }
        }
        if (this.runsTopToBottom(class_)) {
            Collections.reverse(arrayList);
        }
        return arrayList;
    }

    List<Method> getBefores() {
        return this.getAnnotatedMethods(BeforeClass.class);
    }

    public Constructor<?> getConstructor() throws SecurityException, NoSuchMethodException {
        return this.fClass.getConstructor(new Class[0]);
    }

    public Class<?> getJavaClass() {
        return this.fClass;
    }

    public String getName() {
        return this.fClass.getName();
    }

    public List<Method> getTestMethods() {
        return this.getAnnotatedMethods(Test.class);
    }
}

