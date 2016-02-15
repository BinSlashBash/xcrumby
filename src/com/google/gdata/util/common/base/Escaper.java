package com.google.gdata.util.common.base;

public abstract interface Escaper
{
  public abstract Appendable escape(Appendable paramAppendable);
  
  public abstract String escape(String paramString);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/google/gdata/util/common/base/Escaper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */