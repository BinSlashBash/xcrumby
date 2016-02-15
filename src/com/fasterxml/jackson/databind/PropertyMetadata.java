package com.fasterxml.jackson.databind;

import java.io.Serializable;

public class PropertyMetadata
  implements Serializable
{
  public static final PropertyMetadata STD_OPTIONAL = new PropertyMetadata(Boolean.FALSE, null, null);
  public static final PropertyMetadata STD_REQUIRED = new PropertyMetadata(Boolean.TRUE, null, null);
  public static final PropertyMetadata STD_REQUIRED_OR_OPTIONAL = new PropertyMetadata(null, null, null);
  private static final long serialVersionUID = -1L;
  protected final String _description;
  protected final Integer _index;
  protected final Boolean _required;
  
  @Deprecated
  protected PropertyMetadata(Boolean paramBoolean, String paramString)
  {
    this(paramBoolean, paramString, null);
  }
  
  protected PropertyMetadata(Boolean paramBoolean, String paramString, Integer paramInteger)
  {
    this._required = paramBoolean;
    this._description = paramString;
    this._index = paramInteger;
  }
  
  @Deprecated
  public static PropertyMetadata construct(boolean paramBoolean, String paramString)
  {
    return construct(paramBoolean, paramString, null);
  }
  
  public static PropertyMetadata construct(boolean paramBoolean, String paramString, Integer paramInteger)
  {
    if (paramBoolean) {}
    for (PropertyMetadata localPropertyMetadata2 = STD_REQUIRED;; localPropertyMetadata2 = STD_OPTIONAL)
    {
      PropertyMetadata localPropertyMetadata1 = localPropertyMetadata2;
      if (paramString != null) {
        localPropertyMetadata1 = localPropertyMetadata2.withDescription(paramString);
      }
      paramString = localPropertyMetadata1;
      if (paramInteger != null) {
        paramString = localPropertyMetadata1.withIndex(paramInteger);
      }
      return paramString;
    }
  }
  
  public String getDescription()
  {
    return this._description;
  }
  
  public Integer getIndex()
  {
    return this._index;
  }
  
  public Boolean getRequired()
  {
    return this._required;
  }
  
  public boolean hasIndex()
  {
    return this._index != null;
  }
  
  public boolean isRequired()
  {
    return (this._required != null) && (this._required.booleanValue());
  }
  
  protected Object readResolve()
  {
    if ((this._description == null) && (this._index == null))
    {
      if (this._required == null) {
        return STD_REQUIRED_OR_OPTIONAL;
      }
      if (this._required.booleanValue()) {
        return STD_REQUIRED;
      }
      return STD_OPTIONAL;
    }
    return this;
  }
  
  public PropertyMetadata withDescription(String paramString)
  {
    return new PropertyMetadata(this._required, paramString, this._index);
  }
  
  public PropertyMetadata withIndex(Integer paramInteger)
  {
    return new PropertyMetadata(this._required, this._description, paramInteger);
  }
  
  public PropertyMetadata withRequired(Boolean paramBoolean)
  {
    if (paramBoolean == null)
    {
      if (this._required != null) {}
    }
    else {
      while ((this._required != null) && (this._required.booleanValue() == paramBoolean.booleanValue())) {
        return this;
      }
    }
    return new PropertyMetadata(paramBoolean, this._description, this._index);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/PropertyMetadata.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */