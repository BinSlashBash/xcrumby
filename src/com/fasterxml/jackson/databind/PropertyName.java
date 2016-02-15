package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.util.InternCache;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import java.io.Serializable;

public class PropertyName
  implements Serializable
{
  public static final PropertyName NO_NAME = new PropertyName(new String(""), null);
  public static final PropertyName USE_DEFAULT = new PropertyName("", null);
  private static final String _NO_NAME = "";
  private static final String _USE_DEFAULT = "";
  private static final long serialVersionUID = 7930806520033045126L;
  protected SerializableString _encodedSimple;
  protected final String _namespace;
  protected final String _simpleName;
  
  public PropertyName(String paramString)
  {
    this(paramString, null);
  }
  
  public PropertyName(String paramString1, String paramString2)
  {
    String str = paramString1;
    if (paramString1 == null) {
      str = "";
    }
    this._simpleName = str;
    this._namespace = paramString2;
  }
  
  public static PropertyName construct(String paramString1, String paramString2)
  {
    String str = paramString1;
    if (paramString1 == null) {
      str = "";
    }
    if ((paramString2 == null) && (str.length() == 0)) {
      return USE_DEFAULT;
    }
    return new PropertyName(str, paramString2);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = true;
    boolean bool3 = false;
    boolean bool1;
    if (paramObject == this) {
      bool1 = true;
    }
    do
    {
      do
      {
        do
        {
          return bool1;
          bool1 = bool3;
        } while (paramObject == null);
        bool1 = bool3;
      } while (paramObject.getClass() != getClass());
      paramObject = (PropertyName)paramObject;
      if (this._simpleName != null) {
        break;
      }
      bool1 = bool3;
    } while (((PropertyName)paramObject)._simpleName != null);
    if (this._namespace == null)
    {
      if (((PropertyName)paramObject)._namespace == null) {}
      for (bool1 = bool2;; bool1 = false)
      {
        return bool1;
        if (this._simpleName.equals(((PropertyName)paramObject)._simpleName)) {
          break;
        }
        return false;
      }
    }
    return this._namespace.equals(((PropertyName)paramObject)._namespace);
  }
  
  public String getNamespace()
  {
    return this._namespace;
  }
  
  public String getSimpleName()
  {
    return this._simpleName;
  }
  
  public boolean hasNamespace()
  {
    return this._namespace != null;
  }
  
  public boolean hasSimpleName()
  {
    return this._simpleName.length() > 0;
  }
  
  public boolean hasSimpleName(String paramString)
  {
    if (paramString == null) {
      return this._simpleName == null;
    }
    return paramString.equals(this._simpleName);
  }
  
  public int hashCode()
  {
    if (this._namespace == null) {
      return this._simpleName.hashCode();
    }
    return this._namespace.hashCode() ^ this._simpleName.hashCode();
  }
  
  public PropertyName internSimpleName()
  {
    if (this._simpleName.length() == 0) {}
    String str;
    do
    {
      return this;
      str = InternCache.instance.intern(this._simpleName);
    } while (str == this._simpleName);
    return new PropertyName(str, this._namespace);
  }
  
  public boolean isEmpty()
  {
    return (this._namespace == null) && (this._simpleName.isEmpty());
  }
  
  protected Object readResolve()
  {
    PropertyName localPropertyName;
    if ((this._simpleName == null) || ("".equals(this._simpleName))) {
      localPropertyName = USE_DEFAULT;
    }
    do
    {
      do
      {
        return localPropertyName;
        localPropertyName = this;
      } while (!this._simpleName.equals(""));
      localPropertyName = this;
    } while (this._namespace != null);
    return NO_NAME;
  }
  
  public SerializableString simpleAsEncoded(MapperConfig<?> paramMapperConfig)
  {
    SerializableString localSerializableString2 = this._encodedSimple;
    SerializableString localSerializableString1 = localSerializableString2;
    if (localSerializableString2 == null)
    {
      localSerializableString1 = paramMapperConfig.compileString(this._simpleName);
      this._encodedSimple = localSerializableString1;
    }
    return localSerializableString1;
  }
  
  public String toString()
  {
    if (this._namespace == null) {
      return this._simpleName;
    }
    return "{" + this._namespace + "}" + this._simpleName;
  }
  
  public PropertyName withNamespace(String paramString)
  {
    if (paramString == null)
    {
      if (this._namespace != null) {}
    }
    else {
      while (paramString.equals(this._namespace)) {
        return this;
      }
    }
    return new PropertyName(this._simpleName, paramString);
  }
  
  public PropertyName withSimpleName(String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    if (str.equals(this._simpleName)) {
      return this;
    }
    return new PropertyName(str, this._namespace);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/PropertyName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */