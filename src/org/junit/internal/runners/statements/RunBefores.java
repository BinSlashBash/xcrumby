package org.junit.internal.runners.statements;

import java.util.Iterator;
import java.util.List;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class RunBefores
  extends Statement
{
  private final List<FrameworkMethod> fBefores;
  private final Statement fNext;
  private final Object fTarget;
  
  public RunBefores(Statement paramStatement, List<FrameworkMethod> paramList, Object paramObject)
  {
    this.fNext = paramStatement;
    this.fBefores = paramList;
    this.fTarget = paramObject;
  }
  
  public void evaluate()
    throws Throwable
  {
    Iterator localIterator = this.fBefores.iterator();
    while (localIterator.hasNext()) {
      ((FrameworkMethod)localIterator.next()).invokeExplosively(this.fTarget, new Object[0]);
    }
    this.fNext.evaluate();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/statements/RunBefores.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */