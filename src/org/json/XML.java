package org.json;

import java.util.Iterator;

public class XML
{
  public static final Character AMP = new Character('&');
  public static final Character APOS = new Character('\'');
  public static final Character BANG = new Character('!');
  public static final Character EQ = new Character('=');
  public static final Character GT = new Character('>');
  public static final Character LT = new Character('<');
  public static final Character QUEST = new Character('?');
  public static final Character QUOT = new Character('"');
  public static final Character SLASH = new Character('/');
  
  public static String escape(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    int j = paramString.length();
    if (i < j)
    {
      char c = paramString.charAt(i);
      switch (c)
      {
      default: 
        localStringBuffer.append(c);
      }
      for (;;)
      {
        i += 1;
        break;
        localStringBuffer.append("&amp;");
        continue;
        localStringBuffer.append("&lt;");
        continue;
        localStringBuffer.append("&gt;");
        continue;
        localStringBuffer.append("&quot;");
        continue;
        localStringBuffer.append("&apos;");
      }
    }
    return localStringBuffer.toString();
  }
  
  public static void noSpace(String paramString)
    throws JSONException
  {
    int j = paramString.length();
    if (j == 0) {
      throw new JSONException("Empty string.");
    }
    int i = 0;
    while (i < j)
    {
      if (Character.isWhitespace(paramString.charAt(i))) {
        throw new JSONException("'" + paramString + "' contains a space character.");
      }
      i += 1;
    }
  }
  
  private static boolean parse(XMLTokener paramXMLTokener, JSONObject paramJSONObject, String paramString)
    throws JSONException
  {
    Object localObject = paramXMLTokener.nextToken();
    if (localObject == BANG)
    {
      int i = paramXMLTokener.next();
      if (i == 45) {
        if (paramXMLTokener.next() == '-') {
          paramXMLTokener.skipPast("-->");
        }
      }
      int j;
      do
      {
        return false;
        paramXMLTokener.back();
        do
        {
          j = 1;
          paramJSONObject = paramXMLTokener.nextMeta();
          if (paramJSONObject != null) {
            break;
          }
          throw paramXMLTokener.syntaxError("Missing '>' after '<!'.");
        } while (i != 91);
        if ((!"CDATA".equals(paramXMLTokener.nextToken())) || (paramXMLTokener.next() != '[')) {
          break;
        }
        paramXMLTokener = paramXMLTokener.nextCDATA();
      } while (paramXMLTokener.length() <= 0);
      paramJSONObject.accumulate("content", paramXMLTokener);
      return false;
      throw paramXMLTokener.syntaxError("Expected 'CDATA['");
      if (paramJSONObject == LT) {
        i = j + 1;
      }
      for (;;)
      {
        j = i;
        if (i > 0) {
          break;
        }
        return false;
        i = j;
        if (paramJSONObject == GT) {
          i = j - 1;
        }
      }
    }
    if (localObject == QUEST)
    {
      paramXMLTokener.skipPast("?>");
      return false;
    }
    if (localObject == SLASH)
    {
      paramJSONObject = paramXMLTokener.nextToken();
      if (paramString == null) {
        throw paramXMLTokener.syntaxError("Mismatched close tag " + paramJSONObject);
      }
      if (!paramJSONObject.equals(paramString)) {
        throw paramXMLTokener.syntaxError("Mismatched " + paramString + " and " + paramJSONObject);
      }
      if (paramXMLTokener.nextToken() != GT) {
        throw paramXMLTokener.syntaxError("Misshaped close tag");
      }
      return true;
    }
    if ((localObject instanceof Character)) {
      throw paramXMLTokener.syntaxError("Misshaped tag");
    }
    localObject = (String)localObject;
    paramString = null;
    JSONObject localJSONObject = new JSONObject();
    label312:
    if (paramString == null) {
      paramString = paramXMLTokener.nextToken();
    }
    for (;;)
    {
      if ((paramString instanceof String))
      {
        String str = (String)paramString;
        paramString = paramXMLTokener.nextToken();
        if (paramString == EQ)
        {
          paramString = paramXMLTokener.nextToken();
          if (!(paramString instanceof String)) {
            throw paramXMLTokener.syntaxError("Missing value");
          }
          localJSONObject.accumulate(str, stringToValue((String)paramString));
          paramString = null;
          break label312;
        }
        localJSONObject.accumulate(str, "");
        break label312;
      }
      if (paramString == SLASH)
      {
        if (paramXMLTokener.nextToken() != GT) {
          throw paramXMLTokener.syntaxError("Misshaped tag");
        }
        if (localJSONObject.length() > 0)
        {
          paramJSONObject.accumulate((String)localObject, localJSONObject);
          return false;
        }
        paramJSONObject.accumulate((String)localObject, "");
        return false;
      }
      if (paramString == GT)
      {
        label532:
        do
        {
          for (;;)
          {
            paramString = paramXMLTokener.nextContent();
            if (paramString == null)
            {
              if (localObject == null) {
                break;
              }
              throw paramXMLTokener.syntaxError("Unclosed tag " + (String)localObject);
            }
            if (!(paramString instanceof String)) {
              break label532;
            }
            paramString = (String)paramString;
            if (paramString.length() > 0) {
              localJSONObject.accumulate("content", stringToValue(paramString));
            }
          }
        } while ((paramString != LT) || (!parse(paramXMLTokener, localJSONObject, (String)localObject)));
        if (localJSONObject.length() == 0)
        {
          paramJSONObject.accumulate((String)localObject, "");
          return false;
        }
        if ((localJSONObject.length() == 1) && (localJSONObject.opt("content") != null))
        {
          paramJSONObject.accumulate((String)localObject, localJSONObject.opt("content"));
          return false;
        }
        paramJSONObject.accumulate((String)localObject, localJSONObject);
        return false;
      }
      throw paramXMLTokener.syntaxError("Misshaped tag");
    }
  }
  
  public static Object stringToValue(String paramString)
  {
    Object localObject;
    if ("true".equalsIgnoreCase(paramString)) {
      localObject = Boolean.TRUE;
    }
    for (;;)
    {
      return localObject;
      if ("false".equalsIgnoreCase(paramString)) {
        return Boolean.FALSE;
      }
      if ("null".equalsIgnoreCase(paramString)) {
        return JSONObject.NULL;
      }
      try
      {
        int i = paramString.charAt(0);
        if ((i == 45) || ((i >= 48) && (i <= 57)))
        {
          localObject = new Long(paramString);
          bool = ((Long)localObject).toString().equals(paramString);
          if (bool) {
            continue;
          }
        }
      }
      catch (Exception localException1)
      {
        for (;;)
        {
          try
          {
            Double localDouble = new Double(paramString);
            boolean bool = localDouble.toString().equals(paramString);
            if (bool) {
              return localDouble;
            }
          }
          catch (Exception localException2) {}
        }
      }
    }
    return paramString;
  }
  
  public static JSONObject toJSONObject(String paramString)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    paramString = new XMLTokener(paramString);
    while ((paramString.more()) && (paramString.skipPast("<"))) {
      parse(paramString, localJSONObject, null);
    }
    return localJSONObject;
  }
  
  public static String toString(Object paramObject)
    throws JSONException
  {
    return toString(paramObject, null);
  }
  
  public static String toString(Object paramObject, String paramString)
    throws JSONException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Object localObject2;
    int j;
    int i;
    if ((paramObject instanceof JSONObject))
    {
      if (paramString != null)
      {
        localStringBuffer.append('<');
        localStringBuffer.append(paramString);
        localStringBuffer.append('>');
      }
      localObject2 = (JSONObject)paramObject;
      Iterator localIterator = ((JSONObject)localObject2).keys();
      while (localIterator.hasNext())
      {
        String str = localIterator.next().toString();
        localObject1 = ((JSONObject)localObject2).opt(str);
        paramObject = localObject1;
        if (localObject1 == null) {
          paramObject = "";
        }
        if ((paramObject instanceof String)) {
          localObject1 = (String)paramObject;
        }
        for (;;)
        {
          if ("content".equals(str))
          {
            if ((paramObject instanceof JSONArray))
            {
              paramObject = (JSONArray)paramObject;
              j = ((JSONArray)paramObject).length();
              i = 0;
              while (i < j)
              {
                if (i > 0) {
                  localStringBuffer.append('\n');
                }
                localStringBuffer.append(escape(((JSONArray)paramObject).get(i).toString()));
                i += 1;
              }
              break;
              continue;
            }
            localStringBuffer.append(escape(paramObject.toString()));
            break;
          }
        }
        if ((paramObject instanceof JSONArray))
        {
          paramObject = (JSONArray)paramObject;
          j = ((JSONArray)paramObject).length();
          i = 0;
          label219:
          if (i < j)
          {
            localObject1 = ((JSONArray)paramObject).get(i);
            if (!(localObject1 instanceof JSONArray)) {
              break label305;
            }
            localStringBuffer.append('<');
            localStringBuffer.append(str);
            localStringBuffer.append('>');
            localStringBuffer.append(toString(localObject1));
            localStringBuffer.append("</");
            localStringBuffer.append(str);
            localStringBuffer.append('>');
          }
          for (;;)
          {
            i += 1;
            break label219;
            break;
            label305:
            localStringBuffer.append(toString(localObject1, str));
          }
        }
        if ("".equals(paramObject))
        {
          localStringBuffer.append('<');
          localStringBuffer.append(str);
          localStringBuffer.append("/>");
        }
        else
        {
          localStringBuffer.append(toString(paramObject, str));
        }
      }
      if (paramString != null)
      {
        localStringBuffer.append("</");
        localStringBuffer.append(paramString);
        localStringBuffer.append('>');
      }
      return localStringBuffer.toString();
    }
    Object localObject1 = paramObject;
    if (paramObject.getClass().isArray()) {
      localObject1 = new JSONArray(paramObject);
    }
    if ((localObject1 instanceof JSONArray))
    {
      localObject1 = (JSONArray)localObject1;
      j = ((JSONArray)localObject1).length();
      i = 0;
      if (i < j)
      {
        localObject2 = ((JSONArray)localObject1).opt(i);
        if (paramString == null) {}
        for (paramObject = "array";; paramObject = paramString)
        {
          localStringBuffer.append(toString(localObject2, (String)paramObject));
          i += 1;
          break;
        }
      }
      return localStringBuffer.toString();
    }
    if (localObject1 == null) {}
    for (paramObject = "null"; paramString == null; paramObject = escape(localObject1.toString())) {
      return "\"" + (String)paramObject + "\"";
    }
    if (((String)paramObject).length() == 0) {
      return "<" + paramString + "/>";
    }
    return "<" + paramString + ">" + (String)paramObject + "</" + paramString + ">";
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/XML.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */