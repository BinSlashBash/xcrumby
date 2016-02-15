package org.junit.runners.model;

import java.util.Arrays;
import java.util.List;

public class InitializationError
  extends Exception
{
  private static final long serialVersionUID = 1L;
  private final List<Throwable> fErrors;
  
  public InitializationError(String paramString)
  {
    this(new Exception(paramString));
  }
  
  public InitializationError(Throwable paramThrowable)
  {
    this(Arrays.asList(new Throwable[] { paramThrowable }));
  }
  
  public InitializationError(List<Throwable> paramList)
  {
    this.fErrors = paramList;
  }
  
  public List<Throwable> getCauses()
  {
    return this.fErrors;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runners/model/InitializationError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */