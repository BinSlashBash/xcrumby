package org.json;

public class CDL
{
  private static String getValue(JSONTokener paramJSONTokener)
    throws JSONException
  {
    char c1;
    do
    {
      c1 = paramJSONTokener.next();
    } while ((c1 == ' ') || (c1 == '\t'));
    switch (c1)
    {
    default: 
      paramJSONTokener.back();
      return paramJSONTokener.nextTo(',');
    case '\000': 
      return null;
    case '"': 
    case '\'': 
      StringBuffer localStringBuffer = new StringBuffer();
      for (;;)
      {
        char c2 = paramJSONTokener.next();
        if (c2 == c1) {
          return localStringBuffer.toString();
        }
        if ((c2 == 0) || (c2 == '\n') || (c2 == '\r')) {
          throw paramJSONTokener.syntaxError("Missing close quote '" + c1 + "'.");
        }
        localStringBuffer.append(c2);
      }
    }
    paramJSONTokener.back();
    return "";
  }
  
  public static JSONArray rowToJSONArray(JSONTokener paramJSONTokener)
    throws JSONException
  {
    JSONArray localJSONArray = new JSONArray();
    label130:
    for (;;)
    {
      Object localObject = getValue(paramJSONTokener);
      char c = paramJSONTokener.next();
      if ((localObject == null) || ((localJSONArray.length() == 0) && (((String)localObject).length() == 0) && (c != ',')))
      {
        localObject = null;
        return (JSONArray)localObject;
      }
      localJSONArray.put(localObject);
      for (;;)
      {
        if (c == ',') {
          break label130;
        }
        if (c != ' ')
        {
          localObject = localJSONArray;
          if (c == '\n') {
            break;
          }
          localObject = localJSONArray;
          if (c == '\r') {
            break;
          }
          localObject = localJSONArray;
          if (c == 0) {
            break;
          }
          throw paramJSONTokener.syntaxError("Bad character '" + c + "' (" + c + ").");
        }
        c = paramJSONTokener.next();
      }
    }
  }
  
  public static JSONObject rowToJSONObject(JSONArray paramJSONArray, JSONTokener paramJSONTokener)
    throws JSONException
  {
    paramJSONTokener = rowToJSONArray(paramJSONTokener);
    if (paramJSONTokener != null) {
      return paramJSONTokener.toJSONObject(paramJSONArray);
    }
    return null;
  }
  
  public static String rowToString(JSONArray paramJSONArray)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (i < paramJSONArray.length())
    {
      if (i > 0) {
        localStringBuffer.append(',');
      }
      Object localObject = paramJSONArray.opt(i);
      if (localObject != null)
      {
        localObject = localObject.toString();
        if ((((String)localObject).length() <= 0) || ((((String)localObject).indexOf(',') < 0) && (((String)localObject).indexOf('\n') < 0) && (((String)localObject).indexOf('\r') < 0) && (((String)localObject).indexOf(0) < 0) && (((String)localObject).charAt(0) != '"'))) {
          break label179;
        }
        localStringBuffer.append('"');
        int k = ((String)localObject).length();
        int j = 0;
        while (j < k)
        {
          char c = ((String)localObject).charAt(j);
          if ((c >= ' ') && (c != '"')) {
            localStringBuffer.append(c);
          }
          j += 1;
        }
        localStringBuffer.append('"');
      }
      for (;;)
      {
        i += 1;
        break;
        label179:
        localStringBuffer.append((String)localObject);
      }
    }
    localStringBuffer.append('\n');
    return localStringBuffer.toString();
  }
  
  public static JSONArray toJSONArray(String paramString)
    throws JSONException
  {
    return toJSONArray(new JSONTokener(paramString));
  }
  
  public static JSONArray toJSONArray(JSONArray paramJSONArray, String paramString)
    throws JSONException
  {
    return toJSONArray(paramJSONArray, new JSONTokener(paramString));
  }
  
  public static JSONArray toJSONArray(JSONArray paramJSONArray, JSONTokener paramJSONTokener)
    throws JSONException
  {
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0))
    {
      paramJSONArray = null;
      return paramJSONArray;
    }
    JSONArray localJSONArray = new JSONArray();
    for (;;)
    {
      JSONObject localJSONObject = rowToJSONObject(paramJSONArray, paramJSONTokener);
      if (localJSONObject == null)
      {
        paramJSONArray = localJSONArray;
        if (localJSONArray.length() != 0) {
          break;
        }
        return null;
      }
      localJSONArray.put(localJSONObject);
    }
  }
  
  public static JSONArray toJSONArray(JSONTokener paramJSONTokener)
    throws JSONException
  {
    return toJSONArray(rowToJSONArray(paramJSONTokener), paramJSONTokener);
  }
  
  public static String toString(JSONArray paramJSONArray)
    throws JSONException
  {
    Object localObject = paramJSONArray.optJSONObject(0);
    if (localObject != null)
    {
      localObject = ((JSONObject)localObject).names();
      if (localObject != null) {
        return rowToString((JSONArray)localObject) + toString((JSONArray)localObject, paramJSONArray);
      }
    }
    return null;
  }
  
  public static String toString(JSONArray paramJSONArray1, JSONArray paramJSONArray2)
    throws JSONException
  {
    if ((paramJSONArray1 == null) || (paramJSONArray1.length() == 0)) {
      return null;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    while (i < paramJSONArray2.length())
    {
      JSONObject localJSONObject = paramJSONArray2.optJSONObject(i);
      if (localJSONObject != null) {
        localStringBuffer.append(rowToString(localJSONObject.toJSONArray(paramJSONArray1)));
      }
      i += 1;
    }
    return localStringBuffer.toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/CDL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */