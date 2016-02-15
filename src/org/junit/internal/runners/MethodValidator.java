package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@Deprecated
public class MethodValidator
{
  private final List<Throwable> fErrors = new ArrayList();
  private TestClass fTestClass;
  
  public MethodValidator(TestClass paramTestClass)
  {
    this.fTestClass = paramTestClass;
  }
  
  private void validateTestMethods(Class<? extends Annotation> paramClass, boolean paramBoolean)
  {
    Iterator localIterator = this.fTestClass.getAnnotatedMethods(paramClass).iterator();
    if (localIterator.hasNext())
    {
      Method localMethod = (Method)localIterator.next();
      if (Modifier.isStatic(localMethod.getModifiers()) != paramBoolean) {
        if (!paramBoolean) {
          break label338;
        }
      }
      label338:
      for (paramClass = "should";; paramClass = "should not")
      {
        this.fErrors.add(new Exception("Method " + localMethod.getName() + "() " + paramClass + " be static"));
        if (!Modifier.isPublic(localMethod.getDeclaringClass().getModifiers())) {
          this.fErrors.add(new Exception("Class " + localMethod.getDeclaringClass().getName() + " should be public"));
        }
        if (!Modifier.isPublic(localMethod.getModifiers())) {
          this.fErrors.add(new Exception("Method " + localMethod.getName() + " should be public"));
        }
        if (localMethod.getReturnType() != Void.TYPE) {
          this.fErrors.add(new Exception("Method " + localMethod.getName() + " should be void"));
        }
        if (localMethod.getParameterTypes().length == 0) {
          break;
        }
        this.fErrors.add(new Exception("Method " + localMethod.getName() + " should have no parameters"));
        break;
      }
    }
  }
  
  public void assertValid()
    throws InitializationError
  {
    if (!this.fErrors.isEmpty()) {
      throw new InitializationError(this.fErrors);
    }
  }
  
  public void validateInstanceMethods()
  {
    validateTestMethods(After.class, false);
    validateTestMethods(Before.class, false);
    validateTestMethods(Test.class, false);
    if (this.fTestClass.getAnnotatedMethods(Test.class).size() == 0) {
      this.fErrors.add(new Exception("No runnable methods"));
    }
  }
  
  public List<Throwable> validateMethodsForDefaultRunner()
  {
    validateNoArgConstructor();
    validateStaticMethods();
    validateInstanceMethods();
    return this.fErrors;
  }
  
  public void validateNoArgConstructor()
  {
    try
    {
      this.fTestClass.getConstructor();
      return;
    }
    catch (Exception localException)
    {
      this.fErrors.add(new Exception("Test class should have public zero-argument constructor", localException));
    }
  }
  
  public void validateStaticMethods()
  {
    validateTestMethods(BeforeClass.class, true);
    validateTestMethods(AfterClass.class, true);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/MethodValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */