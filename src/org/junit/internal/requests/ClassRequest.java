package org.junit.internal.requests;

import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.Request;
import org.junit.runner.Runner;

public class ClassRequest
  extends Request
{
  private final boolean fCanUseSuiteMethod;
  private Runner fRunner;
  private final Object fRunnerLock = new Object();
  private final Class<?> fTestClass;
  
  public ClassRequest(Class<?> paramClass)
  {
    this(paramClass, true);
  }
  
  public ClassRequest(Class<?> paramClass, boolean paramBoolean)
  {
    this.fTestClass = paramClass;
    this.fCanUseSuiteMethod = paramBoolean;
  }
  
  public Runner getRunner()
  {
    synchronized (this.fRunnerLock)
    {
      if (this.fRunner == null) {
        this.fRunner = new AllDefaultPossibilitiesBuilder(this.fCanUseSuiteMethod).safeRunnerForClass(this.fTestClass);
      }
      Runner localRunner = this.fRunner;
      return localRunner;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/requests/ClassRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */