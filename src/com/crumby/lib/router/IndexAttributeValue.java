package com.crumby.lib.router;

import com.google.gson.JsonPrimitive;

public class IndexAttributeValue<T>
{
  T value;
  
  public IndexAttributeValue(T paramT)
  {
    this.value = paramT;
  }
  
  public IndexAttributeValue copy()
  {
    return new IndexAttributeValue(this.value);
  }
  
  public boolean getAsBoolean()
  {
    if (!(this.value instanceof Boolean)) {
      return false;
    }
    return ((Boolean)this.value).booleanValue();
  }
  
  public JsonPrimitive getAsJsonPrimitive()
  {
    JsonPrimitive localJsonPrimitive = null;
    if ((this.value instanceof Number)) {
      localJsonPrimitive = new JsonPrimitive((Number)this.value);
    }
    do
    {
      return localJsonPrimitive;
      if ((this.value instanceof Boolean)) {
        return new JsonPrimitive((Boolean)this.value);
      }
      if ((this.value instanceof String)) {
        return new JsonPrimitive((String)this.value);
      }
    } while (!(this.value instanceof Character));
    return new JsonPrimitive((Character)this.value);
  }
  
  public String getAsString()
  {
    if (!(this.value instanceof String)) {
      return null;
    }
    return (String)this.value;
  }
  
  public T getObject()
  {
    return (T)this.value;
  }
  
  public void setValue(T paramT)
  {
    this.value = paramT;
  }
  
  public void setValueFromJson(JsonPrimitive paramJsonPrimitive)
  {
    if ((this.value instanceof Number)) {
      this.value = paramJsonPrimitive.getAsNumber();
    }
    do
    {
      return;
      if ((this.value instanceof Boolean))
      {
        this.value = Boolean.valueOf(paramJsonPrimitive.getAsBoolean());
        return;
      }
      if ((this.value instanceof String))
      {
        this.value = paramJsonPrimitive.getAsString();
        return;
      }
    } while (!(this.value instanceof Character));
    this.value = Character.valueOf(paramJsonPrimitive.getAsCharacter());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/router/IndexAttributeValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */