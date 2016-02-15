package org.junit.internal.runners.statements;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class InvokeMethod
  extends Statement
{
  private Object fTarget;
  private final FrameworkMethod fTestMethod;
  
  public InvokeMethod(FrameworkMethod paramFrameworkMethod, Object paramObject)
  {
    this.fTestMethod = paramFrameworkMethod;
    this.fTarget = paramObject;
  }
  
  public void evaluate()
    throws Throwable
  {
    this.fTestMethod.invokeExplosively(this.fTarget, new Object[0]);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/statements/InvokeMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */