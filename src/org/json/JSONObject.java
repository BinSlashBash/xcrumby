package org.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

public class JSONObject
{
  public static final Object NULL = new Null(null);
  private final Map map = new HashMap();
  
  public JSONObject() {}
  
  public JSONObject(Object paramObject)
  {
    this();
    populateMap(paramObject);
  }
  
  public JSONObject(Object paramObject, String[] paramArrayOfString)
  {
    this();
    Class localClass = paramObject.getClass();
    int i = 0;
    for (;;)
    {
      String str;
      if (i < paramArrayOfString.length) {
        str = paramArrayOfString[i];
      }
      try
      {
        putOpt(str, localClass.getField(str).get(paramObject));
        i += 1;
        continue;
        return;
      }
      catch (Exception localException)
      {
        for (;;) {}
      }
    }
  }
  
  public JSONObject(String paramString)
    throws JSONException
  {
    this(new JSONTokener(paramString));
  }
  
  public JSONObject(String paramString, Locale paramLocale)
    throws JSONException
  {
    this();
    ResourceBundle localResourceBundle = ResourceBundle.getBundle(paramString, paramLocale, Thread.currentThread().getContextClassLoader());
    Enumeration localEnumeration = localResourceBundle.getKeys();
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = localEnumeration.nextElement();
      if ((localObject instanceof String))
      {
        String[] arrayOfString = ((String)localObject).split("\\.");
        int j = arrayOfString.length - 1;
        paramLocale = this;
        int i = 0;
        while (i < j)
        {
          String str = arrayOfString[i];
          JSONObject localJSONObject = paramLocale.optJSONObject(str);
          paramString = localJSONObject;
          if (localJSONObject == null)
          {
            paramString = new JSONObject();
            paramLocale.put(str, paramString);
          }
          i += 1;
          paramLocale = paramString;
        }
        paramLocale.put(arrayOfString[j], localResourceBundle.getString((String)localObject));
      }
    }
  }
  
  public JSONObject(Map paramMap)
  {
    if (paramMap != null)
    {
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMap.next();
        Object localObject = localEntry.getValue();
        if (localObject != null) {
          this.map.put(localEntry.getKey(), wrap(localObject));
        }
      }
    }
  }
  
  public JSONObject(JSONObject paramJSONObject, String[] paramArrayOfString)
  {
    this();
    int i = 0;
    for (;;)
    {
      if (i < paramArrayOfString.length) {}
      try
      {
        putOnce(paramArrayOfString[i], paramJSONObject.opt(paramArrayOfString[i]));
        i += 1;
        continue;
        return;
      }
      catch (Exception localException)
      {
        for (;;) {}
      }
    }
  }
  
  public JSONObject(JSONTokener paramJSONTokener)
    throws JSONException
  {
    this();
    if (paramJSONTokener.nextClean() != '{') {
      throw paramJSONTokener.syntaxError("A JSONObject text must begin with '{'");
    }
    do
    {
      paramJSONTokener.back();
      String str;
      switch (paramJSONTokener.nextClean())
      {
      default: 
        paramJSONTokener.back();
        str = paramJSONTokener.nextValue().toString();
        if (paramJSONTokener.nextClean() != ':') {
          throw paramJSONTokener.syntaxError("Expected a ':' after a key");
        }
      case '\000': 
        throw paramJSONTokener.syntaxError("A JSONObject text must end with '}'");
        putOnce(str, paramJSONTokener.nextValue());
        switch (paramJSONTokener.nextClean())
        {
        default: 
          throw paramJSONTokener.syntaxError("Expected a ',' or '}'");
        }
        break;
      }
    } while (paramJSONTokener.nextClean() != '}');
  }
  
  public static String doubleToString(double paramDouble)
  {
    Object localObject;
    if ((Double.isInfinite(paramDouble)) || (Double.isNaN(paramDouble))) {
      localObject = "null";
    }
    String str;
    do
    {
      do
      {
        do
        {
          do
          {
            return (String)localObject;
            str = Double.toString(paramDouble);
            localObject = str;
          } while (str.indexOf('.') <= 0);
          localObject = str;
        } while (str.indexOf('e') >= 0);
        localObject = str;
      } while (str.indexOf('E') >= 0);
      while (str.endsWith("0")) {
        str = str.substring(0, str.length() - 1);
      }
      localObject = str;
    } while (!str.endsWith("."));
    return str.substring(0, str.length() - 1);
  }
  
  public static String[] getNames(Object paramObject)
  {
    String[] arrayOfString = null;
    if (paramObject == null) {
      paramObject = arrayOfString;
    }
    Field[] arrayOfField;
    int j;
    do
    {
      return (String[])paramObject;
      arrayOfField = paramObject.getClass().getFields();
      j = arrayOfField.length;
      paramObject = arrayOfString;
    } while (j == 0);
    arrayOfString = new String[j];
    int i = 0;
    for (;;)
    {
      paramObject = arrayOfString;
      if (i >= j) {
        break;
      }
      arrayOfString[i] = arrayOfField[i].getName();
      i += 1;
    }
  }
  
  public static String[] getNames(JSONObject paramJSONObject)
  {
    int i = paramJSONObject.length();
    if (i == 0)
    {
      paramJSONObject = null;
      return paramJSONObject;
    }
    Iterator localIterator = paramJSONObject.keys();
    String[] arrayOfString = new String[i];
    i = 0;
    for (;;)
    {
      paramJSONObject = arrayOfString;
      if (!localIterator.hasNext()) {
        break;
      }
      arrayOfString[i] = ((String)localIterator.next());
      i += 1;
    }
  }
  
  static final void indent(Writer paramWriter, int paramInt)
    throws IOException
  {
    int i = 0;
    while (i < paramInt)
    {
      paramWriter.write(32);
      i += 1;
    }
  }
  
  public static String numberToString(Number paramNumber)
    throws JSONException
  {
    if (paramNumber == null) {
      throw new JSONException("Null pointer");
    }
    testValidity(paramNumber);
    String str = paramNumber.toString();
    paramNumber = str;
    if (str.indexOf('.') > 0)
    {
      paramNumber = str;
      if (str.indexOf('e') < 0)
      {
        paramNumber = str;
        if (str.indexOf('E') < 0)
        {
          while (str.endsWith("0")) {
            str = str.substring(0, str.length() - 1);
          }
          paramNumber = str;
          if (str.endsWith(".")) {
            paramNumber = str.substring(0, str.length() - 1);
          }
        }
      }
    }
    return paramNumber;
  }
  
  private void populateMap(Object paramObject)
  {
    int i = 0;
    Object localObject1 = paramObject.getClass();
    if (((Class)localObject1).getClassLoader() != null) {
      i = 1;
    }
    Method[] arrayOfMethod;
    label28:
    Method localMethod;
    if (i != 0)
    {
      arrayOfMethod = ((Class)localObject1).getMethods();
      i = 0;
      if (i >= arrayOfMethod.length) {
        break label267;
      }
      localMethod = arrayOfMethod[i];
    }
    try
    {
      if (!Modifier.isPublic(localMethod.getModifiers())) {
        break label171;
      }
      localObject2 = localMethod.getName();
      localObject1 = "";
      if (!((String)localObject2).startsWith("get")) {
        break label197;
      }
      if ("getClass".equals(localObject2)) {
        break label272;
      }
      if (!"getDeclaringClass".equals(localObject2)) {
        break label187;
      }
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Object localObject2;
        label99:
        continue;
        String str = "";
      }
    }
    if ((((String)localObject1).length() > 0) && (Character.isUpperCase(((String)localObject1).charAt(0))) && (localMethod.getParameterTypes().length == 0))
    {
      if (((String)localObject1).length() != 1) {
        break label218;
      }
      localObject2 = ((String)localObject1).toLowerCase();
    }
    for (;;)
    {
      localObject1 = localMethod.invoke(paramObject, (Object[])null);
      if (localObject1 != null) {
        this.map.put(localObject2, wrap(localObject1));
      }
      label171:
      i += 1;
      break label28;
      arrayOfMethod = ((Class)localObject1).getDeclaredMethods();
      break;
      label187:
      localObject1 = ((String)localObject2).substring(3);
      break label99;
      label197:
      if (!((String)localObject2).startsWith("is")) {
        break label99;
      }
      localObject1 = ((String)localObject2).substring(2);
      break label99;
      label218:
      localObject2 = localObject1;
      if (!Character.isUpperCase(((String)localObject1).charAt(1))) {
        localObject2 = ((String)localObject1).substring(0, 1).toLowerCase() + ((String)localObject1).substring(1);
      }
    }
    label267:
  }
  
  public static Writer quote(String paramString, Writer paramWriter)
    throws IOException
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      paramWriter.write("\"\"");
      return paramWriter;
    }
    int i = 0;
    int m = paramString.length();
    paramWriter.write(34);
    int j = 0;
    int k = i;
    if (j < m)
    {
      i = paramString.charAt(j);
      switch (i)
      {
      default: 
        if ((i < 32) || ((i >= 128) && (i < 160)) || ((i >= 8192) && (i < 8448)))
        {
          paramWriter.write("\\u");
          String str = Integer.toHexString(i);
          paramWriter.write("0000", 0, 4 - str.length());
          paramWriter.write(str);
        }
        break;
      }
      for (;;)
      {
        j += 1;
        break;
        paramWriter.write(92);
        paramWriter.write(i);
        continue;
        if (k == 60) {
          paramWriter.write(92);
        }
        paramWriter.write(i);
        continue;
        paramWriter.write("\\b");
        continue;
        paramWriter.write("\\t");
        continue;
        paramWriter.write("\\n");
        continue;
        paramWriter.write("\\f");
        continue;
        paramWriter.write("\\r");
        continue;
        paramWriter.write(i);
      }
    }
    paramWriter.write(34);
    return paramWriter;
  }
  
  public static String quote(String paramString)
  {
    StringWriter localStringWriter = new StringWriter();
    synchronized (localStringWriter.getBuffer())
    {
      try
      {
        paramString = quote(paramString, localStringWriter).toString();
        return paramString;
      }
      catch (IOException paramString)
      {
        return "";
      }
    }
  }
  
  public static Object stringToValue(String paramString)
  {
    if (paramString.equals("")) {}
    for (;;)
    {
      return paramString;
      if (paramString.equalsIgnoreCase("true")) {
        return Boolean.TRUE;
      }
      if (paramString.equalsIgnoreCase("false")) {
        return Boolean.FALSE;
      }
      if (paramString.equalsIgnoreCase("null")) {
        return NULL;
      }
      int i = paramString.charAt(0);
      if (((i >= 48) && (i <= 57)) || (i == 45)) {
        try
        {
          Object localObject;
          if ((paramString.indexOf('.') > -1) || (paramString.indexOf('e') > -1) || (paramString.indexOf('E') > -1))
          {
            localObject = Double.valueOf(paramString);
            if ((!((Double)localObject).isInfinite()) && (!((Double)localObject).isNaN())) {
              return localObject;
            }
          }
          else
          {
            localObject = new Long(paramString);
            if (paramString.equals(((Long)localObject).toString()))
            {
              if (((Long)localObject).longValue() == ((Long)localObject).intValue())
              {
                localObject = new Integer(((Long)localObject).intValue());
                return localObject;
              }
              return localObject;
            }
          }
        }
        catch (Exception localException) {}
      }
    }
    return paramString;
  }
  
  public static void testValidity(Object paramObject)
    throws JSONException
  {
    if (paramObject != null) {
      if ((paramObject instanceof Double))
      {
        if ((((Double)paramObject).isInfinite()) || (((Double)paramObject).isNaN())) {
          throw new JSONException("JSON does not allow non-finite numbers.");
        }
      }
      else if (((paramObject instanceof Float)) && ((((Float)paramObject).isInfinite()) || (((Float)paramObject).isNaN()))) {
        throw new JSONException("JSON does not allow non-finite numbers.");
      }
    }
  }
  
  public static String valueToString(Object paramObject)
    throws JSONException
  {
    if ((paramObject == null) || (paramObject.equals(null))) {
      return "null";
    }
    if ((paramObject instanceof JSONString))
    {
      try
      {
        paramObject = ((JSONString)paramObject).toJSONString();
        if ((paramObject instanceof String)) {
          return (String)paramObject;
        }
      }
      catch (Exception paramObject)
      {
        throw new JSONException((Throwable)paramObject);
      }
      throw new JSONException("Bad value from toJSONString: " + paramObject);
    }
    if ((paramObject instanceof Number)) {
      return numberToString((Number)paramObject);
    }
    if (((paramObject instanceof Boolean)) || ((paramObject instanceof JSONObject)) || ((paramObject instanceof JSONArray))) {
      return paramObject.toString();
    }
    if ((paramObject instanceof Map)) {
      return new JSONObject((Map)paramObject).toString();
    }
    if ((paramObject instanceof Collection)) {
      return new JSONArray((Collection)paramObject).toString();
    }
    if (paramObject.getClass().isArray()) {
      return new JSONArray(paramObject).toString();
    }
    return quote(paramObject.toString());
  }
  
  public static Object wrap(Object paramObject)
  {
    if (paramObject == null) {}
    try
    {
      return NULL;
    }
    catch (Exception paramObject)
    {
      localObject = null;
    }
    Object localObject = paramObject;
    if (!(paramObject instanceof JSONObject))
    {
      localObject = paramObject;
      if (!(paramObject instanceof JSONArray))
      {
        localObject = paramObject;
        if (!NULL.equals(paramObject))
        {
          localObject = paramObject;
          if (!(paramObject instanceof JSONString))
          {
            localObject = paramObject;
            if (!(paramObject instanceof Byte))
            {
              localObject = paramObject;
              if (!(paramObject instanceof Character))
              {
                localObject = paramObject;
                if (!(paramObject instanceof Short))
                {
                  localObject = paramObject;
                  if (!(paramObject instanceof Integer))
                  {
                    localObject = paramObject;
                    if (!(paramObject instanceof Long))
                    {
                      localObject = paramObject;
                      if (!(paramObject instanceof Boolean))
                      {
                        localObject = paramObject;
                        if (!(paramObject instanceof Float))
                        {
                          localObject = paramObject;
                          if (!(paramObject instanceof Double))
                          {
                            localObject = paramObject;
                            if (!(paramObject instanceof String))
                            {
                              if ((paramObject instanceof Collection)) {
                                return new JSONArray((Collection)paramObject);
                              }
                              if (paramObject.getClass().isArray()) {
                                return new JSONArray(paramObject);
                              }
                              if ((paramObject instanceof Map)) {
                                return new JSONObject((Map)paramObject);
                              }
                              localObject = paramObject.getClass().getPackage();
                              if (localObject == null) {
                                break label253;
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    label253:
    for (localObject = ((Package)localObject).getName();; localObject = "")
    {
      if ((((String)localObject).startsWith("java.")) || (((String)localObject).startsWith("javax.")) || (paramObject.getClass().getClassLoader() == null)) {
        return paramObject.toString();
      }
      paramObject = new JSONObject(paramObject);
      return paramObject;
      return localObject;
    }
  }
  
  static final Writer writeValue(Writer paramWriter, Object paramObject, int paramInt1, int paramInt2)
    throws JSONException, IOException
  {
    if ((paramObject == null) || (paramObject.equals(null)))
    {
      paramWriter.write("null");
      return paramWriter;
    }
    if ((paramObject instanceof JSONObject))
    {
      ((JSONObject)paramObject).write(paramWriter, paramInt1, paramInt2);
      return paramWriter;
    }
    if ((paramObject instanceof JSONArray))
    {
      ((JSONArray)paramObject).write(paramWriter, paramInt1, paramInt2);
      return paramWriter;
    }
    if ((paramObject instanceof Map))
    {
      new JSONObject((Map)paramObject).write(paramWriter, paramInt1, paramInt2);
      return paramWriter;
    }
    if ((paramObject instanceof Collection))
    {
      new JSONArray((Collection)paramObject).write(paramWriter, paramInt1, paramInt2);
      return paramWriter;
    }
    if (paramObject.getClass().isArray())
    {
      new JSONArray(paramObject).write(paramWriter, paramInt1, paramInt2);
      return paramWriter;
    }
    if ((paramObject instanceof Number))
    {
      paramWriter.write(numberToString((Number)paramObject));
      return paramWriter;
    }
    if ((paramObject instanceof Boolean))
    {
      paramWriter.write(paramObject.toString());
      return paramWriter;
    }
    if ((paramObject instanceof JSONString)) {
      for (;;)
      {
        try
        {
          String str = ((JSONString)paramObject).toJSONString();
          if (str != null)
          {
            paramObject = str.toString();
            paramWriter.write((String)paramObject);
            return paramWriter;
          }
        }
        catch (Exception paramWriter)
        {
          throw new JSONException(paramWriter);
        }
        paramObject = quote(paramObject.toString());
      }
    }
    quote(paramObject.toString(), paramWriter);
    return paramWriter;
  }
  
  public JSONObject accumulate(String paramString, Object paramObject)
    throws JSONException
  {
    testValidity(paramObject);
    Object localObject = opt(paramString);
    if (localObject == null)
    {
      localObject = paramObject;
      if ((paramObject instanceof JSONArray)) {
        localObject = new JSONArray().put(paramObject);
      }
      put(paramString, localObject);
      return this;
    }
    if ((localObject instanceof JSONArray))
    {
      ((JSONArray)localObject).put(paramObject);
      return this;
    }
    put(paramString, new JSONArray().put(localObject).put(paramObject));
    return this;
  }
  
  public JSONObject append(String paramString, Object paramObject)
    throws JSONException
  {
    testValidity(paramObject);
    Object localObject = opt(paramString);
    if (localObject == null)
    {
      put(paramString, new JSONArray().put(paramObject));
      return this;
    }
    if ((localObject instanceof JSONArray))
    {
      put(paramString, ((JSONArray)localObject).put(paramObject));
      return this;
    }
    throw new JSONException("JSONObject[" + paramString + "] is not a JSONArray.");
  }
  
  public Object get(String paramString)
    throws JSONException
  {
    if (paramString == null) {
      throw new JSONException("Null key.");
    }
    Object localObject = opt(paramString);
    if (localObject == null) {
      throw new JSONException("JSONObject[" + quote(paramString) + "] not found.");
    }
    return localObject;
  }
  
  public boolean getBoolean(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if ((localObject.equals(Boolean.FALSE)) || (((localObject instanceof String)) && (((String)localObject).equalsIgnoreCase("false")))) {
      return false;
    }
    if ((localObject.equals(Boolean.TRUE)) || (((localObject instanceof String)) && (((String)localObject).equalsIgnoreCase("true")))) {
      return true;
    }
    throw new JSONException("JSONObject[" + quote(paramString) + "] is not a Boolean.");
  }
  
  public double getDouble(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    try
    {
      if ((localObject instanceof Number)) {
        return ((Number)localObject).doubleValue();
      }
      double d = Double.parseDouble((String)localObject);
      return d;
    }
    catch (Exception localException)
    {
      throw new JSONException("JSONObject[" + quote(paramString) + "] is not a number.");
    }
  }
  
  public int getInt(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    try
    {
      if ((localObject instanceof Number)) {
        return ((Number)localObject).intValue();
      }
      int i = Integer.parseInt((String)localObject);
      return i;
    }
    catch (Exception localException)
    {
      throw new JSONException("JSONObject[" + quote(paramString) + "] is not an int.");
    }
  }
  
  public JSONArray getJSONArray(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if ((localObject instanceof JSONArray)) {
      return (JSONArray)localObject;
    }
    throw new JSONException("JSONObject[" + quote(paramString) + "] is not a JSONArray.");
  }
  
  public JSONObject getJSONObject(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if ((localObject instanceof JSONObject)) {
      return (JSONObject)localObject;
    }
    throw new JSONException("JSONObject[" + quote(paramString) + "] is not a JSONObject.");
  }
  
  public long getLong(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    try
    {
      if ((localObject instanceof Number)) {
        return ((Number)localObject).longValue();
      }
      long l = Long.parseLong((String)localObject);
      return l;
    }
    catch (Exception localException)
    {
      throw new JSONException("JSONObject[" + quote(paramString) + "] is not a long.");
    }
  }
  
  public String getString(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if ((localObject instanceof String)) {
      return (String)localObject;
    }
    throw new JSONException("JSONObject[" + quote(paramString) + "] not a string.");
  }
  
  public boolean has(String paramString)
  {
    return this.map.containsKey(paramString);
  }
  
  public JSONObject increment(String paramString)
    throws JSONException
  {
    Object localObject = opt(paramString);
    if (localObject == null)
    {
      put(paramString, 1);
      return this;
    }
    if ((localObject instanceof Integer))
    {
      put(paramString, ((Integer)localObject).intValue() + 1);
      return this;
    }
    if ((localObject instanceof Long))
    {
      put(paramString, ((Long)localObject).longValue() + 1L);
      return this;
    }
    if ((localObject instanceof Double))
    {
      put(paramString, ((Double)localObject).doubleValue() + 1.0D);
      return this;
    }
    if ((localObject instanceof Float))
    {
      put(paramString, ((Float)localObject).floatValue() + 1.0F);
      return this;
    }
    throw new JSONException("Unable to increment [" + quote(paramString) + "].");
  }
  
  public boolean isNull(String paramString)
  {
    return NULL.equals(opt(paramString));
  }
  
  public Set keySet()
  {
    return this.map.keySet();
  }
  
  public Iterator keys()
  {
    return keySet().iterator();
  }
  
  public int length()
  {
    return this.map.size();
  }
  
  public JSONArray names()
  {
    JSONArray localJSONArray = new JSONArray();
    Object localObject = keys();
    while (((Iterator)localObject).hasNext()) {
      localJSONArray.put(((Iterator)localObject).next());
    }
    localObject = localJSONArray;
    if (localJSONArray.length() == 0) {
      localObject = null;
    }
    return (JSONArray)localObject;
  }
  
  public Object opt(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return this.map.get(paramString);
  }
  
  public boolean optBoolean(String paramString)
  {
    return optBoolean(paramString, false);
  }
  
  public boolean optBoolean(String paramString, boolean paramBoolean)
  {
    try
    {
      boolean bool = getBoolean(paramString);
      return bool;
    }
    catch (Exception paramString) {}
    return paramBoolean;
  }
  
  public double optDouble(String paramString)
  {
    return optDouble(paramString, NaN.0D);
  }
  
  public double optDouble(String paramString, double paramDouble)
  {
    try
    {
      double d = getDouble(paramString);
      return d;
    }
    catch (Exception paramString) {}
    return paramDouble;
  }
  
  public int optInt(String paramString)
  {
    return optInt(paramString, 0);
  }
  
  public int optInt(String paramString, int paramInt)
  {
    try
    {
      int i = getInt(paramString);
      return i;
    }
    catch (Exception paramString) {}
    return paramInt;
  }
  
  public JSONArray optJSONArray(String paramString)
  {
    paramString = opt(paramString);
    if ((paramString instanceof JSONArray)) {
      return (JSONArray)paramString;
    }
    return null;
  }
  
  public JSONObject optJSONObject(String paramString)
  {
    paramString = opt(paramString);
    if ((paramString instanceof JSONObject)) {
      return (JSONObject)paramString;
    }
    return null;
  }
  
  public long optLong(String paramString)
  {
    return optLong(paramString, 0L);
  }
  
  public long optLong(String paramString, long paramLong)
  {
    try
    {
      long l = getLong(paramString);
      return l;
    }
    catch (Exception paramString) {}
    return paramLong;
  }
  
  public String optString(String paramString)
  {
    return optString(paramString, "");
  }
  
  public String optString(String paramString1, String paramString2)
  {
    paramString1 = opt(paramString1);
    if (NULL.equals(paramString1)) {
      return paramString2;
    }
    return paramString1.toString();
  }
  
  public JSONObject put(String paramString, double paramDouble)
    throws JSONException
  {
    put(paramString, new Double(paramDouble));
    return this;
  }
  
  public JSONObject put(String paramString, int paramInt)
    throws JSONException
  {
    put(paramString, new Integer(paramInt));
    return this;
  }
  
  public JSONObject put(String paramString, long paramLong)
    throws JSONException
  {
    put(paramString, new Long(paramLong));
    return this;
  }
  
  public JSONObject put(String paramString, Object paramObject)
    throws JSONException
  {
    if (paramString == null) {
      throw new NullPointerException("Null key.");
    }
    if (paramObject != null)
    {
      testValidity(paramObject);
      this.map.put(paramString, paramObject);
      return this;
    }
    remove(paramString);
    return this;
  }
  
  public JSONObject put(String paramString, Collection paramCollection)
    throws JSONException
  {
    put(paramString, new JSONArray(paramCollection));
    return this;
  }
  
  public JSONObject put(String paramString, Map paramMap)
    throws JSONException
  {
    put(paramString, new JSONObject(paramMap));
    return this;
  }
  
  public JSONObject put(String paramString, boolean paramBoolean)
    throws JSONException
  {
    if (paramBoolean) {}
    for (Boolean localBoolean = Boolean.TRUE;; localBoolean = Boolean.FALSE)
    {
      put(paramString, localBoolean);
      return this;
    }
  }
  
  public JSONObject putOnce(String paramString, Object paramObject)
    throws JSONException
  {
    if ((paramString != null) && (paramObject != null))
    {
      if (opt(paramString) != null) {
        throw new JSONException("Duplicate key \"" + paramString + "\"");
      }
      put(paramString, paramObject);
    }
    return this;
  }
  
  public JSONObject putOpt(String paramString, Object paramObject)
    throws JSONException
  {
    if ((paramString != null) && (paramObject != null)) {
      put(paramString, paramObject);
    }
    return this;
  }
  
  public Object remove(String paramString)
  {
    return this.map.remove(paramString);
  }
  
  public JSONArray toJSONArray(JSONArray paramJSONArray)
    throws JSONException
  {
    Object localObject;
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0))
    {
      localObject = null;
      return (JSONArray)localObject;
    }
    JSONArray localJSONArray = new JSONArray();
    int i = 0;
    for (;;)
    {
      localObject = localJSONArray;
      if (i >= paramJSONArray.length()) {
        break;
      }
      localJSONArray.put(opt(paramJSONArray.getString(i)));
      i += 1;
    }
  }
  
  public String toString()
  {
    try
    {
      String str = toString(0);
      return str;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public String toString(int paramInt)
    throws JSONException
  {
    Object localObject1 = new StringWriter();
    synchronized (((StringWriter)localObject1).getBuffer())
    {
      localObject1 = write((Writer)localObject1, paramInt, 0).toString();
      return (String)localObject1;
    }
  }
  
  public Writer write(Writer paramWriter)
    throws JSONException
  {
    return write(paramWriter, 0, 0);
  }
  
  Writer write(Writer paramWriter, int paramInt1, int paramInt2)
    throws JSONException
  {
    int i = 0;
    for (;;)
    {
      int j;
      try
      {
        j = length();
        localObject1 = keys();
        paramWriter.write(123);
        if (j != 1) {
          break label217;
        }
        localObject1 = ((Iterator)localObject1).next();
        paramWriter.write(quote(localObject1.toString()));
        paramWriter.write(58);
        if (paramInt1 > 0) {
          paramWriter.write(32);
        }
        writeValue(paramWriter, this.map.get(localObject1), paramInt1, paramInt2);
        paramWriter.write(125);
        return paramWriter;
      }
      catch (IOException paramWriter)
      {
        Object localObject1;
        Object localObject2;
        throw new JSONException(paramWriter);
      }
      if (((Iterator)localObject1).hasNext())
      {
        localObject2 = ((Iterator)localObject1).next();
        if (i != 0) {
          paramWriter.write(44);
        }
        if (paramInt1 > 0) {
          paramWriter.write(10);
        }
        indent(paramWriter, j);
        paramWriter.write(quote(localObject2.toString()));
        paramWriter.write(58);
        if (paramInt1 > 0) {
          paramWriter.write(32);
        }
        writeValue(paramWriter, this.map.get(localObject2), paramInt1, j);
        i = 1;
      }
      else
      {
        if (paramInt1 > 0) {
          paramWriter.write(10);
        }
        indent(paramWriter, paramInt2);
        continue;
        label217:
        if (j != 0) {
          j = paramInt2 + paramInt1;
        }
      }
    }
  }
  
  private static final class Null
  {
    private Null() {}
    
    Null(JSONObject.1 param1)
    {
      this();
    }
    
    protected final Object clone()
    {
      return this;
    }
    
    public boolean equals(Object paramObject)
    {
      return (paramObject == null) || (paramObject == this);
    }
    
    public String toString()
    {
      return "null";
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/JSONObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */