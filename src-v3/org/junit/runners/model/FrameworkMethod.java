package org.junit.runners.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import org.junit.internal.runners.model.ReflectiveCallable;

public class FrameworkMethod extends FrameworkMember<FrameworkMethod> {
    final Method fMethod;

    /* renamed from: org.junit.runners.model.FrameworkMethod.1 */
    class C12711 extends ReflectiveCallable {
        final /* synthetic */ Object[] val$params;
        final /* synthetic */ Object val$target;

        C12711(Object obj, Object[] objArr) {
            this.val$target = obj;
            this.val$params = objArr;
        }

        protected Object runReflectiveCall() throws Throwable {
            return FrameworkMethod.this.fMethod.invoke(this.val$target, this.val$params);
        }
    }

    public FrameworkMethod(Method method) {
        this.fMethod = method;
    }

    public Method getMethod() {
        return this.fMethod;
    }

    public Object invokeExplosively(Object target, Object... params) throws Throwable {
        return new C12711(target, params).run();
    }

    public String getName() {
        return this.fMethod.getName();
    }

    public void validatePublicVoidNoArg(boolean isStatic, List<Throwable> errors) {
        validatePublicVoid(isStatic, errors);
        if (this.fMethod.getParameterTypes().length != 0) {
            errors.add(new Exception("Method " + this.fMethod.getName() + " should have no parameters"));
        }
    }

    public void validatePublicVoid(boolean isStatic, List<Throwable> errors) {
        if (Modifier.isStatic(this.fMethod.getModifiers()) != isStatic) {
            errors.add(new Exception("Method " + this.fMethod.getName() + "() " + (isStatic ? "should" : "should not") + " be static"));
        }
        if (!Modifier.isPublic(this.fMethod.getDeclaringClass().getModifiers())) {
            errors.add(new Exception("Class " + this.fMethod.getDeclaringClass().getName() + " should be public"));
        }
        if (!Modifier.isPublic(this.fMethod.getModifiers())) {
            errors.add(new Exception("Method " + this.fMethod.getName() + "() should be public"));
        }
        if (this.fMethod.getReturnType() != Void.TYPE) {
            errors.add(new Exception("Method " + this.fMethod.getName() + "() should be void"));
        }
    }

    public boolean isStatic() {
        return Modifier.isStatic(this.fMethod.getModifiers());
    }

    public boolean isPublic() {
        return Modifier.isPublic(this.fMethod.getModifiers());
    }

    public Class<?> getReturnType() {
        return this.fMethod.getReturnType();
    }

    public Class<?> getType() {
        return getReturnType();
    }

    public void validateNoTypeParametersOnArgs(List<Throwable> errors) {
        new NoGenericTypeParametersValidator(this.fMethod).validate(errors);
    }

    public boolean isShadowedBy(FrameworkMethod other) {
        if (!other.getName().equals(getName()) || other.getParameterTypes().length != getParameterTypes().length) {
            return false;
        }
        for (int i = 0; i < other.getParameterTypes().length; i++) {
            if (!other.getParameterTypes()[i].equals(getParameterTypes()[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (FrameworkMethod.class.isInstance(obj)) {
            return ((FrameworkMethod) obj).fMethod.equals(this.fMethod);
        }
        return false;
    }

    public int hashCode() {
        return this.fMethod.hashCode();
    }

    @Deprecated
    public boolean producesType(Type type) {
        return getParameterTypes().length == 0 && (type instanceof Class) && ((Class) type).isAssignableFrom(this.fMethod.getReturnType());
    }

    private Class<?>[] getParameterTypes() {
        return this.fMethod.getParameterTypes();
    }

    public Annotation[] getAnnotations() {
        return this.fMethod.getAnnotations();
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        return this.fMethod.getAnnotation(annotationType);
    }
}
