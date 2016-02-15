/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runners.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.runners.model.FrameworkMember;
import org.junit.runners.model.NoGenericTypeParametersValidator;

public class FrameworkMethod
extends FrameworkMember<FrameworkMethod> {
    final Method fMethod;

    public FrameworkMethod(Method method) {
        this.fMethod = method;
    }

    private Class<?>[] getParameterTypes() {
        return this.fMethod.getParameterTypes();
    }

    public boolean equals(Object object) {
        if (!FrameworkMethod.class.isInstance(object)) {
            return false;
        }
        return ((FrameworkMethod)object).fMethod.equals(this.fMethod);
    }

    public <T extends Annotation> T getAnnotation(Class<T> class_) {
        return this.fMethod.getAnnotation(class_);
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.fMethod.getAnnotations();
    }

    public Method getMethod() {
        return this.fMethod;
    }

    @Override
    public String getName() {
        return this.fMethod.getName();
    }

    public Class<?> getReturnType() {
        return this.fMethod.getReturnType();
    }

    @Override
    public Class<?> getType() {
        return this.getReturnType();
    }

    public int hashCode() {
        return this.fMethod.hashCode();
    }

    public /* varargs */ Object invokeExplosively(final Object object, final Object ... arrobject) throws Throwable {
        return new ReflectiveCallable(){

            @Override
            protected Object runReflectiveCall() throws Throwable {
                return FrameworkMethod.this.fMethod.invoke(object, arrobject);
            }
        }.run();
    }

    @Override
    public boolean isPublic() {
        return Modifier.isPublic(this.fMethod.getModifiers());
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean isShadowedBy(FrameworkMethod frameworkMethod) {
        if (!frameworkMethod.getName().equals(this.getName())) {
            return false;
        }
        if (frameworkMethod.getParameterTypes().length != this.getParameterTypes().length) return false;
        int n2 = 0;
        while (n2 < frameworkMethod.getParameterTypes().length) {
            if (!frameworkMethod.getParameterTypes()[n2].equals(this.getParameterTypes()[n2])) return false;
            ++n2;
        }
        return true;
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(this.fMethod.getModifiers());
    }

    @Deprecated
    public boolean producesType(Type type) {
        if (this.getParameterTypes().length == 0 && type instanceof Class && ((Class)type).isAssignableFrom(this.fMethod.getReturnType())) {
            return true;
        }
        return false;
    }

    public void validateNoTypeParametersOnArgs(List<Throwable> list) {
        new NoGenericTypeParametersValidator(this.fMethod).validate(list);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void validatePublicVoid(boolean bl2, List<Throwable> list) {
        if (Modifier.isStatic(this.fMethod.getModifiers()) != bl2) {
            String string2 = bl2 ? "should" : "should not";
            list.add(new Exception("Method " + this.fMethod.getName() + "() " + string2 + " be static"));
        }
        if (!Modifier.isPublic(this.fMethod.getDeclaringClass().getModifiers())) {
            list.add(new Exception("Class " + this.fMethod.getDeclaringClass().getName() + " should be public"));
        }
        if (!Modifier.isPublic(this.fMethod.getModifiers())) {
            list.add(new Exception("Method " + this.fMethod.getName() + "() should be public"));
        }
        if (this.fMethod.getReturnType() != Void.TYPE) {
            list.add(new Exception("Method " + this.fMethod.getName() + "() should be void"));
        }
    }

    public void validatePublicVoidNoArg(boolean bl2, List<Throwable> list) {
        this.validatePublicVoid(bl2, list);
        if (this.fMethod.getParameterTypes().length != 0) {
            list.add(new Exception("Method " + this.fMethod.getName() + " should have no parameters"));
        }
    }

}

