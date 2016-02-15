package com.crumby;

import java.util.concurrent.atomic.AtomicInteger;

public enum ViewIdGenerator
{
  INSTANCE;
  
  private final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
  
  private ViewIdGenerator() {}
  
  public int generateViewId()
  {
    int k;
    int i;
    do
    {
      k = this.sNextGeneratedId.get();
      int j = k + 1;
      i = j;
      if (j > 16777215) {
        i = 1;
      }
    } while (!this.sNextGeneratedId.compareAndSet(k, i));
    return k;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/ViewIdGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */