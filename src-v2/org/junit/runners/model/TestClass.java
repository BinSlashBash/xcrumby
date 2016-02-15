/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runners.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.internal.MethodSorter;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMember;
import org.junit.runners.model.FrameworkMethod;

public class TestClass {
    private final Class<?> fClass;
    private Map<Class<?>, List<FrameworkField>> fFieldsForAnnotations = new HashMap();
    private Map<Class<?>, List<FrameworkMethod>> fMethodsForAnnotations = new HashMap();

    public TestClass(Class<?> object) {
        this.fClass = object;
        if (object != null && object.getConstructors().length > 1) {
            throw new IllegalArgumentException("Test class can only have one constructor");
        }
        object = this.getSuperClasses(this.fClass).iterator();
        while (object.hasNext()) {
            int n2;
            Field[] arrfield = (Field[])object.next();
            Method[] arrmethod = MethodSorter.getDeclaredMethods(arrfield);
            int n3 = arrmethod.length;
            for (n2 = 0; n2 < n3; ++n2) {
                this.addToAnnotationLists(new FrameworkMethod(arrmethod[n2]), this.fMethodsForAnnotations);
            }
            arrfield = arrfield.getDeclaredFields();
            n3 = arrfield.length;
            for (n2 = 0; n2 < n3; ++n2) {
                this.addToAnnotationLists(new FrameworkField(arrfield[n2]), this.fFieldsForAnnotations);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private <T extends FrameworkMember<T>> void addToAnnotationLists(T list, Map<Class<?>, List<T>> map) {
        Annotation[] arrannotation = list.getAnnotations();
        int n2 = arrannotation.length;
        int n3 = 0;
        while (n3 < n2) {
            Class<? extends Annotation> class_ = arrannotation[n3].annotationType();
            List<T> list2 = this.getAnnotatedMembers(map, class_);
            if (list.isShadowedBy((List<List<List<T>>>)list2)) {
                return;
            }
            if (this.runsTopToBottom(class_)) {
                list2.add(0, list);
            } else {
                list2.add(list);
            }
            ++n3;
        }
    }

    private <T> List<T> getAnnotatedMembers(Map<Class<?>, List<T>> map, Class<? extends Annotation> class_) {
        if (!map.containsKey(class_)) {
            map.put(class_, new ArrayList());
        }
        return map.get(class_);
    }

    private List<Class<?>> getSuperClasses(Class<?> class_) {
        ArrayList arrayList = new ArrayList();
        while (class_ != null) {
            arrayList.add(class_);
            class_ = class_.getSuperclass();
        }
        return arrayList;
    }

    private boolean runsTopToBottom(Class<? extends Annotation> class_) {
        if (class_.equals((Object)Before.class) || class_.equals((Object)BeforeClass.class)) {
            return true;
        }
        return false;
    }

    public <T> List<T> getAnnotatedFieldValues(Object object, Class<? extends Annotation> object2, Class<T> class_) {
        ArrayList<T> arrayList = new ArrayList<T>();
        for (FrameworkField frameworkField : this.getAnnotatedFields((Class<? extends Annotation>)((Object)object2))) {
            try {
                Object object3 = frameworkField.get(object);
                if (!class_.isInstance(object3)) continue;
                arrayList.add(class_.cast(object3));
                continue;
            }
            catch (IllegalAccessException var1_2) {
                throw new RuntimeException("How did getFields return a field we couldn't access?", var1_2);
            }
        }
        return arrayList;
    }

    public List<FrameworkField> getAnnotatedFields(Class<? extends Annotation> class_) {
        return this.getAnnotatedMembers(this.fFieldsForAnnotations, class_);
    }

    public <T> List<T> getAnnotatedMethodValues(Object object, Class<? extends Annotation> object22, Class<T> class_) {
        ArrayList arrayList = new ArrayList();
        for (FrameworkMethod frameworkMethod : this.getAnnotatedMethods((Class<? extends Annotation>)object22)) {
            try {
                void var3_6;
                Object object2 = frameworkMethod.invokeExplosively(object, new Object[0]);
                if (!var3_6.isInstance(object2)) continue;
                arrayList.add(var3_6.cast(object2));
                continue;
            }
            catch (Throwable var1_2) {
                throw new RuntimeException("Exception in " + frameworkMethod.getName(), var1_2);
            }
        }
        return arrayList;
    }

    public List<FrameworkMethod> getAnnotatedMethods(Class<? extends Annotation> class_) {
        return this.getAnnotatedMembers(this.fMethodsForAnnotations, class_);
    }

    public Annotation[] getAnnotations() {
        if (this.fClass == null) {
            return new Annotation[0];
        }
        return this.fClass.getAnnotations();
    }

    public Class<?> getJavaClass() {
        return this.fClass;
    }

    public String getName() {
        if (this.fClass == null) {
            return "null";
        }
        return this.fClass.getName();
    }

    public Constructor<?> getOnlyConstructor() {
        Constructor<?>[] arrconstructor = this.fClass.getConstructors();
        Assert.assertEquals(1, arrconstructor.length);
        return arrconstructor[0];
    }

    public boolean isANonStaticInnerClass() {
        if (this.fClass.isMemberClass() && !Modifier.isStatic(this.fClass.getModifiers())) {
            return true;
        }
        return false;
    }
}

