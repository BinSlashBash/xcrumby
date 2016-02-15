package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class MinimalClassNameIdResolver
  extends ClassNameIdResolver
{
  protected final String _basePackageName;
  protected final String _basePackagePrefix;
  
  protected MinimalClassNameIdResolver(JavaType paramJavaType, TypeFactory paramTypeFactory)
  {
    super(paramJavaType, paramTypeFactory);
    paramJavaType = paramJavaType.getRawClass().getName();
    int i = paramJavaType.lastIndexOf('.');
    if (i < 0)
    {
      this._basePackageName = "";
      this._basePackagePrefix = ".";
      return;
    }
    this._basePackagePrefix = paramJavaType.substring(0, i + 1);
    this._basePackageName = paramJavaType.substring(0, i);
  }
  
  protected JavaType _typeFromId(String paramString, TypeFactory paramTypeFactory)
  {
    Object localObject = paramString;
    if (paramString.startsWith("."))
    {
      localObject = new StringBuilder(paramString.length() + this._basePackageName.length());
      if (this._basePackageName.length() != 0) {
        break label63;
      }
      ((StringBuilder)localObject).append(paramString.substring(1));
    }
    for (;;)
    {
      localObject = ((StringBuilder)localObject).toString();
      return super._typeFromId((String)localObject, paramTypeFactory);
      label63:
      ((StringBuilder)localObject).append(this._basePackageName).append(paramString);
    }
  }
  
  public JsonTypeInfo.Id getMechanism()
  {
    return JsonTypeInfo.Id.MINIMAL_CLASS;
  }
  
  public String idFromValue(Object paramObject)
  {
    String str = paramObject.getClass().getName();
    paramObject = str;
    if (str.startsWith(this._basePackagePrefix)) {
      paramObject = str.substring(this._basePackagePrefix.length() - 1);
    }
    return (String)paramObject;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/jsontype/impl/MinimalClassNameIdResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */