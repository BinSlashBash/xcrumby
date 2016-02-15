package org.junit.rules;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

@Deprecated
public class TestWatchman
  implements MethodRule
{
  public Statement apply(final Statement paramStatement, final FrameworkMethod paramFrameworkMethod, Object paramObject)
  {
    new Statement()
    {
      /* Error */
      public void evaluate()
        throws Throwable
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 19	org/junit/rules/TestWatchman$1:this$0	Lorg/junit/rules/TestWatchman;
        //   4: aload_0
        //   5: getfield 21	org/junit/rules/TestWatchman$1:val$method	Lorg/junit/runners/model/FrameworkMethod;
        //   8: invokevirtual 36	org/junit/rules/TestWatchman:starting	(Lorg/junit/runners/model/FrameworkMethod;)V
        //   11: aload_0
        //   12: getfield 23	org/junit/rules/TestWatchman$1:val$base	Lorg/junit/runners/model/Statement;
        //   15: invokevirtual 38	org/junit/runners/model/Statement:evaluate	()V
        //   18: aload_0
        //   19: getfield 19	org/junit/rules/TestWatchman$1:this$0	Lorg/junit/rules/TestWatchman;
        //   22: aload_0
        //   23: getfield 21	org/junit/rules/TestWatchman$1:val$method	Lorg/junit/runners/model/FrameworkMethod;
        //   26: invokevirtual 41	org/junit/rules/TestWatchman:succeeded	(Lorg/junit/runners/model/FrameworkMethod;)V
        //   29: aload_0
        //   30: getfield 19	org/junit/rules/TestWatchman$1:this$0	Lorg/junit/rules/TestWatchman;
        //   33: aload_0
        //   34: getfield 21	org/junit/rules/TestWatchman$1:val$method	Lorg/junit/runners/model/FrameworkMethod;
        //   37: invokevirtual 44	org/junit/rules/TestWatchman:finished	(Lorg/junit/runners/model/FrameworkMethod;)V
        //   40: return
        //   41: astore_1
        //   42: aload_1
        //   43: athrow
        //   44: astore_1
        //   45: aload_0
        //   46: getfield 19	org/junit/rules/TestWatchman$1:this$0	Lorg/junit/rules/TestWatchman;
        //   49: aload_0
        //   50: getfield 21	org/junit/rules/TestWatchman$1:val$method	Lorg/junit/runners/model/FrameworkMethod;
        //   53: invokevirtual 44	org/junit/rules/TestWatchman:finished	(Lorg/junit/runners/model/FrameworkMethod;)V
        //   56: aload_1
        //   57: athrow
        //   58: astore_1
        //   59: aload_0
        //   60: getfield 19	org/junit/rules/TestWatchman$1:this$0	Lorg/junit/rules/TestWatchman;
        //   63: aload_1
        //   64: aload_0
        //   65: getfield 21	org/junit/rules/TestWatchman$1:val$method	Lorg/junit/runners/model/FrameworkMethod;
        //   68: invokevirtual 48	org/junit/rules/TestWatchman:failed	(Ljava/lang/Throwable;Lorg/junit/runners/model/FrameworkMethod;)V
        //   71: aload_1
        //   72: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	73	0	this	1
        //   41	2	1	localAssumptionViolatedException	org.junit.internal.AssumptionViolatedException
        //   44	13	1	localObject	Object
        //   58	14	1	localThrowable	Throwable
        // Exception table:
        //   from	to	target	type
        //   11	29	41	org/junit/internal/AssumptionViolatedException
        //   11	29	44	finally
        //   42	44	44	finally
        //   59	73	44	finally
        //   11	29	58	java/lang/Throwable
      }
    };
  }
  
  public void failed(Throwable paramThrowable, FrameworkMethod paramFrameworkMethod) {}
  
  public void finished(FrameworkMethod paramFrameworkMethod) {}
  
  public void starting(FrameworkMethod paramFrameworkMethod) {}
  
  public void succeeded(FrameworkMethod paramFrameworkMethod) {}
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/rules/TestWatchman.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */