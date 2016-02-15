package org.junit.internal.runners.model;

import java.util.List;

@Deprecated
public class MultipleFailureException
  extends org.junit.runners.model.MultipleFailureException
{
  private static final long serialVersionUID = 1L;
  
  public MultipleFailureException(List<Throwable> paramList)
  {
    super(paramList);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/model/MultipleFailureException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */