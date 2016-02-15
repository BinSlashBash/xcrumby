package org.json;

import java.util.Iterator;

public class CookieList
{
  public static JSONObject toJSONObject(String paramString)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    paramString = new JSONTokener(paramString);
    while (paramString.more())
    {
      String str = Cookie.unescape(paramString.nextTo('='));
      paramString.next('=');
      localJSONObject.put(str, Cookie.unescape(paramString.nextTo(';')));
      paramString.next();
    }
    return localJSONObject;
  }
  
  public static String toString(JSONObject paramJSONObject)
    throws JSONException
  {
    int i = 0;
    Iterator localIterator = paramJSONObject.keys();
    StringBuffer localStringBuffer = new StringBuffer();
    while (localIterator.hasNext())
    {
      String str = localIterator.next().toString();
      if (!paramJSONObject.isNull(str))
      {
        if (i != 0) {
          localStringBuffer.append(';');
        }
        localStringBuffer.append(Cookie.escape(str));
        localStringBuffer.append("=");
        localStringBuffer.append(Cookie.escape(paramJSONObject.getString(str)));
        i = 1;
      }
    }
    return localStringBuffer.toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/CookieList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */