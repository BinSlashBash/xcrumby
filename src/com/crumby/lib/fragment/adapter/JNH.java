package com.crumby.lib.fragment.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JNH
{
  public static final int getAttributeInt(JsonObject paramJsonObject, String paramString)
  {
    paramJsonObject = paramJsonObject.get(paramString);
    if ((paramJsonObject == null) || (paramJsonObject.isJsonNull())) {
      return 0;
    }
    return paramJsonObject.getAsInt();
  }
  
  public static final String getAttributeString(JsonObject paramJsonObject, String paramString)
  {
    paramJsonObject = paramJsonObject.get(paramString);
    if ((paramJsonObject == null) || (paramJsonObject.isJsonNull())) {
      return null;
    }
    return paramJsonObject.getAsString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/adapter/JNH.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */