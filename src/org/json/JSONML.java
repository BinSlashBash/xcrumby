package org.json;

import java.util.Iterator;

public class JSONML
{
  private static Object parse(XMLTokener paramXMLTokener, boolean paramBoolean, JSONArray paramJSONArray)
    throws JSONException
  {
    Object localObject1;
    for (;;)
    {
      if (!paramXMLTokener.more()) {
        throw paramXMLTokener.syntaxError("Bad XML");
      }
      localObject2 = paramXMLTokener.nextContent();
      if (localObject2 != XML.LT) {
        break label725;
      }
      localObject1 = paramXMLTokener.nextToken();
      if (!(localObject1 instanceof Character)) {
        break label294;
      }
      if (localObject1 == XML.SLASH)
      {
        paramJSONArray = paramXMLTokener.nextToken();
        if (!(paramJSONArray instanceof String)) {
          throw new JSONException("Expected a closing name instead of '" + paramJSONArray + "'.");
        }
        if (paramXMLTokener.nextToken() == XML.GT) {
          break label602;
        }
        throw paramXMLTokener.syntaxError("Misshaped close tag");
      }
      if (localObject1 == XML.BANG)
      {
        int i = paramXMLTokener.next();
        if (i == 45)
        {
          if (paramXMLTokener.next() == '-') {
            paramXMLTokener.skipPast("-->");
          } else {
            paramXMLTokener.back();
          }
        }
        else if (i == 91)
        {
          if ((paramXMLTokener.nextToken().equals("CDATA")) && (paramXMLTokener.next() == '['))
          {
            if (paramJSONArray != null) {
              paramJSONArray.put(paramXMLTokener.nextCDATA());
            }
          }
          else {
            throw paramXMLTokener.syntaxError("Expected 'CDATA['");
          }
        }
        else
        {
          int j = 1;
          label209:
          localObject1 = paramXMLTokener.nextMeta();
          if (localObject1 == null) {
            throw paramXMLTokener.syntaxError("Missing '>' after '<!'.");
          }
          if (localObject1 == XML.LT) {
            i = j + 1;
          }
          for (;;)
          {
            j = i;
            if (i > 0) {
              break label209;
            }
            break;
            i = j;
            if (localObject1 == XML.GT) {
              i = j - 1;
            }
          }
        }
      }
      else
      {
        if (localObject1 != XML.QUEST) {
          break;
        }
        paramXMLTokener.skipPast("?>");
      }
    }
    throw paramXMLTokener.syntaxError("Misshaped tag");
    label294:
    if (!(localObject1 instanceof String)) {
      throw paramXMLTokener.syntaxError("Bad tagName '" + localObject1 + "'.");
    }
    String str1 = (String)localObject1;
    Object localObject2 = new JSONArray();
    JSONObject localJSONObject = new JSONObject();
    if (paramBoolean)
    {
      ((JSONArray)localObject2).put(str1);
      if (paramJSONArray != null) {
        paramJSONArray.put(localObject2);
      }
      label380:
      localObject1 = null;
      label383:
      if (localObject1 != null) {
        break label761;
      }
      localObject1 = paramXMLTokener.nextToken();
    }
    label602:
    label607:
    label725:
    label761:
    for (;;)
    {
      if (localObject1 == null)
      {
        throw paramXMLTokener.syntaxError("Misshaped tag");
        localJSONObject.put("tagName", str1);
        if (paramJSONArray == null) {
          break label380;
        }
        paramJSONArray.put(localJSONObject);
        break label380;
      }
      if (!(localObject1 instanceof String))
      {
        if ((paramBoolean) && (localJSONObject.length() > 0)) {
          ((JSONArray)localObject2).put(localJSONObject);
        }
        if (localObject1 != XML.SLASH) {
          break label607;
        }
        if (paramXMLTokener.nextToken() != XML.GT) {
          throw paramXMLTokener.syntaxError("Misshaped tag");
        }
      }
      else
      {
        String str2 = (String)localObject1;
        if ((!paramBoolean) && (("tagName".equals(str2)) || ("childNode".equals(str2)))) {
          throw paramXMLTokener.syntaxError("Reserved attribute.");
        }
        localObject1 = paramXMLTokener.nextToken();
        if (localObject1 == XML.EQ)
        {
          localObject1 = paramXMLTokener.nextToken();
          if (!(localObject1 instanceof String)) {
            throw paramXMLTokener.syntaxError("Missing value");
          }
          localJSONObject.accumulate(str2, XML.stringToValue((String)localObject1));
          localObject1 = null;
          break label383;
        }
        localJSONObject.accumulate(str2, "");
        break label383;
      }
      if (paramJSONArray != null) {
        break;
      }
      if (paramBoolean)
      {
        paramJSONArray = (JSONArray)localObject2;
        return paramJSONArray;
      }
      return localJSONObject;
      if (localObject1 != XML.GT) {
        throw paramXMLTokener.syntaxError("Misshaped tag");
      }
      localObject1 = (String)parse(paramXMLTokener, paramBoolean, (JSONArray)localObject2);
      if (localObject1 == null) {
        break;
      }
      if (!((String)localObject1).equals(str1)) {
        throw paramXMLTokener.syntaxError("Mismatched '" + str1 + "' and '" + (String)localObject1 + "'");
      }
      if ((!paramBoolean) && (((JSONArray)localObject2).length() > 0)) {
        localJSONObject.put("childNodes", localObject2);
      }
      if (paramJSONArray != null) {
        break;
      }
      if (paramBoolean) {
        return localObject2;
      }
      return localJSONObject;
      if (paramJSONArray == null) {
        break;
      }
      localObject1 = localObject2;
      if ((localObject2 instanceof String)) {
        localObject1 = XML.stringToValue((String)localObject2);
      }
      paramJSONArray.put(localObject1);
      break;
    }
  }
  
  public static JSONArray toJSONArray(String paramString)
    throws JSONException
  {
    return toJSONArray(new XMLTokener(paramString));
  }
  
  public static JSONArray toJSONArray(XMLTokener paramXMLTokener)
    throws JSONException
  {
    return (JSONArray)parse(paramXMLTokener, true, null);
  }
  
  public static JSONObject toJSONObject(String paramString)
    throws JSONException
  {
    return toJSONObject(new XMLTokener(paramString));
  }
  
  public static JSONObject toJSONObject(XMLTokener paramXMLTokener)
    throws JSONException
  {
    return (JSONObject)parse(paramXMLTokener, false, null);
  }
  
  public static String toString(JSONArray paramJSONArray)
    throws JSONException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str1 = paramJSONArray.getString(0);
    XML.noSpace(str1);
    str1 = XML.escape(str1);
    localStringBuffer.append('<');
    localStringBuffer.append(str1);
    Object localObject = paramJSONArray.opt(1);
    if ((localObject instanceof JSONObject))
    {
      j = 2;
      localObject = (JSONObject)localObject;
      Iterator localIterator = ((JSONObject)localObject).keys();
      for (;;)
      {
        i = j;
        if (!localIterator.hasNext()) {
          break;
        }
        String str2 = localIterator.next().toString();
        XML.noSpace(str2);
        String str3 = ((JSONObject)localObject).optString(str2);
        if (str3 != null)
        {
          localStringBuffer.append(' ');
          localStringBuffer.append(XML.escape(str2));
          localStringBuffer.append('=');
          localStringBuffer.append('"');
          localStringBuffer.append(XML.escape(str3));
          localStringBuffer.append('"');
        }
      }
    }
    int i = 1;
    int k = paramJSONArray.length();
    if (i >= k)
    {
      localStringBuffer.append('/');
      localStringBuffer.append('>');
      return localStringBuffer.toString();
    }
    localStringBuffer.append('>');
    label217:
    localObject = paramJSONArray.get(i);
    int j = i + 1;
    if (localObject != null)
    {
      if (!(localObject instanceof String)) {
        break label297;
      }
      localStringBuffer.append(XML.escape(localObject.toString()));
    }
    for (;;)
    {
      i = j;
      if (j < k) {
        break label217;
      }
      localStringBuffer.append('<');
      localStringBuffer.append('/');
      localStringBuffer.append(str1);
      localStringBuffer.append('>');
      break;
      label297:
      if ((localObject instanceof JSONObject)) {
        localStringBuffer.append(toString((JSONObject)localObject));
      } else if ((localObject instanceof JSONArray)) {
        localStringBuffer.append(toString((JSONArray)localObject));
      }
    }
  }
  
  public static String toString(JSONObject paramJSONObject)
    throws JSONException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str1 = paramJSONObject.optString("tagName");
    if (str1 == null) {
      return XML.escape(paramJSONObject.toString());
    }
    XML.noSpace(str1);
    str1 = XML.escape(str1);
    localStringBuffer.append('<');
    localStringBuffer.append(str1);
    Object localObject = paramJSONObject.keys();
    while (((Iterator)localObject).hasNext())
    {
      String str2 = ((Iterator)localObject).next().toString();
      if ((!"tagName".equals(str2)) && (!"childNodes".equals(str2)))
      {
        XML.noSpace(str2);
        String str3 = paramJSONObject.optString(str2);
        if (str3 != null)
        {
          localStringBuffer.append(' ');
          localStringBuffer.append(XML.escape(str2));
          localStringBuffer.append('=');
          localStringBuffer.append('"');
          localStringBuffer.append(XML.escape(str3));
          localStringBuffer.append('"');
        }
      }
    }
    paramJSONObject = paramJSONObject.optJSONArray("childNodes");
    if (paramJSONObject == null)
    {
      localStringBuffer.append('/');
      localStringBuffer.append('>');
    }
    for (;;)
    {
      return localStringBuffer.toString();
      localStringBuffer.append('>');
      int j = paramJSONObject.length();
      int i = 0;
      if (i < j)
      {
        localObject = paramJSONObject.get(i);
        if (localObject != null)
        {
          if (!(localObject instanceof String)) {
            break label261;
          }
          localStringBuffer.append(XML.escape(localObject.toString()));
        }
        for (;;)
        {
          i += 1;
          break;
          label261:
          if ((localObject instanceof JSONObject)) {
            localStringBuffer.append(toString((JSONObject)localObject));
          } else if ((localObject instanceof JSONArray)) {
            localStringBuffer.append(toString((JSONArray)localObject));
          } else {
            localStringBuffer.append(localObject.toString());
          }
        }
      }
      localStringBuffer.append('<');
      localStringBuffer.append('/');
      localStringBuffer.append(str1);
      localStringBuffer.append('>');
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/JSONML.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */