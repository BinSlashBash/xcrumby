/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.theories;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParameterSignature {
    private final Annotation[] annotations;
    private final Class<?> type;

    private ParameterSignature(Class<?> class_, Annotation[] arrannotation) {
        this.type = class_;
        this.annotations = arrannotation;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private <T extends Annotation> T findDeepAnnotation(Annotation[] arrannotation, Class<T> class_, int n2) {
        if (n2 == 0) {
            return null;
        }
        int n3 = arrannotation.length;
        int n4 = 0;
        while (n4 < n3) {
            Annotation annotation = arrannotation[n4];
            if (class_.isInstance(annotation)) {
                return (T)((Annotation)class_.cast(annotation));
            }
            T t2 = this.findDeepAnnotation(annotation.annotationType().getAnnotations(), class_, n2 - 1);
            if (t2 != null) {
                return (T)((Annotation)class_.cast(t2));
            }
            ++n4;
        }
        return null;
    }

    public static ArrayList<ParameterSignature> signatures(Method method) {
        return ParameterSignature.signatures(method.getParameterTypes(), method.getParameterAnnotations());
    }

    private static ArrayList<ParameterSignature> signatures(Class<?>[] arrclass, Annotation[][] arrannotation) {
        ArrayList<ParameterSignature> arrayList = new ArrayList<ParameterSignature>();
        for (int i2 = 0; i2 < arrclass.length; ++i2) {
            arrayList.add(new ParameterSignature(arrclass[i2], arrannotation[i2]));
        }
        return arrayList;
    }

    public static List<ParameterSignature> signatures(Constructor<?> constructor) {
        return ParameterSignature.signatures(constructor.getParameterTypes(), constructor.getParameterAnnotations());
    }

    public boolean canAcceptArrayType(Class<?> class_) {
        if (class_.isArray() && this.canAcceptType(class_.getComponentType())) {
            return true;
        }
        return false;
    }

    public boolean canAcceptType(Class<?> class_) {
        return this.type.isAssignableFrom(class_);
    }

    public <T extends Annotation> T findDeepAnnotation(Class<T> class_) {
        return this.findDeepAnnotation(this.annotations, class_, 3);
    }

    public <T extends Annotation> T getAnnotation(Class<T> class_) {
        for (Annotation annotation : this.getAnnotations()) {
            if (!class_.isInstance(annotation)) continue;
            return (T)((Annotation)class_.cast(annotation));
        }
        return null;
    }

    public List<Annotation> getAnnotations() {
        return Arrays.asList(this.annotations);
    }

    public Class<?> getType() {
        return this.type;
    }

    public boolean hasAnnotation(Class<? extends Annotation> class_) {
        if (this.getAnnotation(class_) != null) {
            return true;
        }
        return false;
    }
}

