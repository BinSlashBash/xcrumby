package org.junit.internal.runners.statements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

public class RunAfters
  extends Statement
{
  private final List<FrameworkMethod> fAfters;
  private final Statement fNext;
  private final Object fTarget;
  
  public RunAfters(Statement paramStatement, List<FrameworkMethod> paramList, Object paramObject)
  {
    this.fNext = paramStatement;
    this.fAfters = paramList;
    this.fTarget = paramObject;
  }
  
  public void evaluate()
    throws Throwable
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      this.fNext.evaluate();
      Iterator localIterator1 = this.fAfters.iterator();
      while (localIterator1.hasNext())
      {
        FrameworkMethod localFrameworkMethod1 = (FrameworkMethod)localIterator1.next();
        try
        {
          localFrameworkMethod1.invokeExplosively(this.fTarget, new Object[0]);
        }
        catch (Throwable localThrowable2)
        {
          localArrayList.add(localThrowable2);
        }
      }
      Iterator localIterator2;
      FrameworkMethod localFrameworkMethod2;
      Iterator localIterator3;
      FrameworkMethod localFrameworkMethod3;
      MultipleFailureException.assertEmpty(localArrayList);
    }
    catch (Throwable localThrowable1)
    {
      localArrayList.add(localThrowable1);
    }
    finally
    {
      localIterator3 = this.fAfters.iterator();
      while (localIterator3.hasNext())
      {
        localFrameworkMethod3 = (FrameworkMethod)localIterator3.next();
        try
        {
          localFrameworkMethod3.invokeExplosively(this.fTarget, new Object[0]);
        }
        catch (Throwable localThrowable4)
        {
          localArrayList.add(localThrowable4);
        }
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/statements/RunAfters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */