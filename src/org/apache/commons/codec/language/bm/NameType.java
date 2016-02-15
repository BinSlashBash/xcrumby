package org.apache.commons.codec.language.bm;

public enum NameType
{
  ASHKENAZI("ash"),  GENERIC("gen"),  SEPHARDIC("sep");
  
  private final String name;
  
  private NameType(String paramString)
  {
    this.name = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/language/bm/NameType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */