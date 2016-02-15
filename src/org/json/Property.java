package org.json;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

public class Property
{
  public static JSONObject toJSONObject(Properties paramProperties)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    if ((paramProperties != null) && (!paramProperties.isEmpty()))
    {
      Enumeration localEnumeration = paramProperties.propertyNames();
      while (localEnumeration.hasMoreElements())
      {
        String str = (String)localEnumeration.nextElement();
        localJSONObject.put(str, paramProperties.getProperty(str));
      }
    }
    return localJSONObject;
  }
  
  public static Properties toProperties(JSONObject paramJSONObject)
    throws JSONException
  {
    Properties localProperties = new Properties();
    if (paramJSONObject != null)
    {
      Iterator localIterator = paramJSONObject.keys();
      while (localIterator.hasNext())
      {
        String str = localIterator.next().toString();
        localProperties.put(str, paramJSONObject.getString(str));
      }
    }
    return localProperties;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/Property.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */