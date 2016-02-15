package org.junit.runners.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import org.junit.internal.runners.model.ReflectiveCallable;

public class FrameworkMethod
  extends FrameworkMember<FrameworkMethod>
{
  final Method fMethod;
  
  public FrameworkMethod(Method paramMethod)
  {
    this.fMethod = paramMethod;
  }
  
  private Class<?>[] getParameterTypes()
  {
    return this.fMethod.getParameterTypes();
  }
  
  public boolean equals(Object paramObject)
  {
    if (!FrameworkMethod.class.isInstance(paramObject)) {
      return false;
    }
    return ((FrameworkMethod)paramObject).fMethod.equals(this.fMethod);
  }
  
  public <T extends Annotation> T getAnnotation(Class<T> paramClass)
  {
    return this.fMethod.getAnnotation(paramClass);
  }
  
  public Annotation[] getAnnotations()
  {
    return this.fMethod.getAnnotations();
  }
  
  public Method getMethod()
  {
    return this.fMethod;
  }
  
  public String getName()
  {
    return this.fMethod.getName();
  }
  
  public Class<?> getReturnType()
  {
    return this.fMethod.getReturnType();
  }
  
  public Class<?> getType()
  {
    return getReturnType();
  }
  
  public int hashCode()
  {
    return this.fMethod.hashCode();
  }
  
  public Object invokeExplosively(final Object paramObject, final Object... paramVarArgs)
    throws Throwable
  {
    new ReflectiveCallable()
    {
      protected Object runReflectiveCall()
        throws Throwable
      {
        return FrameworkMethod.this.fMethod.invoke(paramObject, paramVarArgs);
      }
    }.run();
  }
  
  public boolean isPublic()
  {
    return Modifier.isPublic(this.fMethod.getModifiers());
  }
  
  public boolean isShadowedBy(FrameworkMethod paramFrameworkMethod)
  {
    if (!paramFrameworkMethod.getName().equals(getName())) {}
    while (paramFrameworkMethod.getParameterTypes().length != getParameterTypes().length) {
      return false;
    }
    int i = 0;
    for (;;)
    {
      if (i >= paramFrameworkMethod.getParameterTypes().length) {
        break label65;
      }
      if (!paramFrameworkMethod.getParameterTypes()[i].equals(getParameterTypes()[i])) {
        break;
      }
      i += 1;
    }
    label65:
    return true;
  }
  
  public boolean isStatic()
  {
    return Modifier.isStatic(this.fMethod.getModifiers());
  }
  
  @Deprecated
  public boolean producesType(Type paramType)
  {
    return (getParameterTypes().length == 0) && ((paramType instanceof Class)) && (((Class)paramType).isAssignableFrom(this.fMethod.getReturnType()));
  }
  
  public void validateNoTypeParametersOnArgs(List<Throwable> paramList)
  {
    new NoGenericTypeParametersValidator(this.fMethod).validate(paramList);
  }
  
  public void validatePublicVoid(boolean paramBoolean, List<Throwable> paramList)
  {
    if (Modifier.isStatic(this.fMethod.getModifiers()) != paramBoolean) {
      if (!paramBoolean) {
        break label252;
      }
    }
    label252:
    for (String str = "should";; str = "should not")
    {
      paramList.add(new Exception("Method " + this.fMethod.getName() + "() " + str + " be static"));
      if (!Modifier.isPublic(this.fMethod.getDeclaringClass().getModifiers())) {
        paramList.add(new Exception("Class " + this.fMethod.getDeclaringClass().getName() + " should be public"));
      }
      if (!Modifier.isPublic(this.fMethod.getModifiers())) {
        paramList.add(new Exception("Method " + this.fMethod.getName() + "() should be public"));
      }
      if (this.fMethod.getReturnType() != Void.TYPE) {
        paramList.add(new Exception("Method " + this.fMethod.getName() + "() should be void"));
      }
      return;
    }
  }
  
  public void validatePublicVoidNoArg(boolean paramBoolean, List<Throwable> paramList)
  {
    validatePublicVoid(paramBoolean, paramList);
    if (this.fMethod.getParameterTypes().length != 0) {
      paramList.add(new Exception("Method " + this.fMethod.getName() + " should have no parameters"));
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runners/model/FrameworkMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */