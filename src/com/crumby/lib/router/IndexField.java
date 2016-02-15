package com.crumby.lib.router;

public class IndexField<T>
{
  public final T defaultValue;
  public final String key;
  public final String name;
  
  public IndexField(String paramString1, String paramString2, T paramT)
  {
    this.key = paramString1;
    this.name = paramString2;
    this.defaultValue = paramT;
  }
  
  public IndexField<T> differentDefaultValue(T paramT)
  {
    return new IndexField(this.key, this.name, paramT);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/router/IndexField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */