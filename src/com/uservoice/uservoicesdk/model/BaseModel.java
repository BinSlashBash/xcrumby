package com.uservoice.uservoicesdk.model;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Html;
import com.uservoice.uservoicesdk.Config;
import com.uservoice.uservoicesdk.Session;
import com.uservoice.uservoicesdk.rest.RestMethod;
import com.uservoice.uservoicesdk.rest.RestTask;
import com.uservoice.uservoicesdk.rest.RestTaskCallback;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseModel
{
  protected int id;
  
  protected static String apiPath(String paramString, Object... paramVarArgs)
  {
    return "/api/v1" + String.format(paramString, paramVarArgs);
  }
  
  protected static List<BaseModel> deserializeHeterogenousList(JSONObject paramJSONObject, String paramString)
    throws JSONException
  {
    if (!paramJSONObject.has(paramString)) {
      paramJSONObject = null;
    }
    JSONArray localJSONArray;
    int i;
    do
    {
      return paramJSONObject;
      localJSONArray = paramJSONObject.getJSONArray(paramString);
      paramString = new ArrayList(localJSONArray.length());
      i = 0;
      paramJSONObject = paramString;
    } while (i >= localJSONArray.length());
    JSONObject localJSONObject = localJSONArray.getJSONObject(i);
    paramJSONObject = localJSONObject.getString("type");
    if (paramJSONObject.equals("suggestion")) {}
    for (paramJSONObject = new Suggestion();; paramJSONObject = new Article())
    {
      paramJSONObject.load(localJSONObject);
      paramString.add(paramJSONObject);
      do
      {
        i += 1;
        break;
      } while (!paramJSONObject.equals("article"));
    }
  }
  
  /* Error */
  protected static <T extends BaseModel> List<T> deserializeList(JSONObject paramJSONObject, String paramString, Class<T> paramClass)
    throws JSONException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 41	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   5: ifne +7 -> 12
    //   8: aconst_null
    //   9: astore_0
    //   10: aload_0
    //   11: areturn
    //   12: aload_0
    //   13: aload_1
    //   14: invokevirtual 45	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   17: astore 4
    //   19: new 47	java/util/ArrayList
    //   22: dup
    //   23: aload 4
    //   25: invokevirtual 53	org/json/JSONArray:length	()I
    //   28: invokespecial 56	java/util/ArrayList:<init>	(I)V
    //   31: astore_1
    //   32: iconst_0
    //   33: istore_3
    //   34: aload_1
    //   35: astore_0
    //   36: iload_3
    //   37: aload 4
    //   39: invokevirtual 53	org/json/JSONArray:length	()I
    //   42: if_icmpge -32 -> 10
    //   45: aload_2
    //   46: invokevirtual 106	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   49: checkcast 2	com/uservoice/uservoicesdk/model/BaseModel
    //   52: astore_0
    //   53: aload_0
    //   54: aload 4
    //   56: iload_3
    //   57: invokevirtual 60	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   60: invokevirtual 79	com/uservoice/uservoicesdk/model/BaseModel:load	(Lorg/json/JSONObject;)V
    //   63: aload_1
    //   64: aload_0
    //   65: invokeinterface 84 2 0
    //   70: pop
    //   71: iload_3
    //   72: iconst_1
    //   73: iadd
    //   74: istore_3
    //   75: goto -41 -> 34
    //   78: astore_0
    //   79: new 35	org/json/JSONException
    //   82: dup
    //   83: new 15	java/lang/StringBuilder
    //   86: dup
    //   87: invokespecial 16	java/lang/StringBuilder:<init>	()V
    //   90: ldc 108
    //   92: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: aload_2
    //   96: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   99: ldc 113
    //   101: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   104: aload_0
    //   105: invokevirtual 116	java/lang/IllegalArgumentException:getMessage	()Ljava/lang/String;
    //   108: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: invokevirtual 31	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   114: invokespecial 119	org/json/JSONException:<init>	(Ljava/lang/String;)V
    //   117: athrow
    //   118: astore_0
    //   119: new 35	org/json/JSONException
    //   122: dup
    //   123: new 15	java/lang/StringBuilder
    //   126: dup
    //   127: invokespecial 16	java/lang/StringBuilder:<init>	()V
    //   130: ldc 108
    //   132: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: aload_2
    //   136: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   139: ldc 113
    //   141: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   144: aload_0
    //   145: invokevirtual 120	java/lang/IllegalAccessException:getMessage	()Ljava/lang/String;
    //   148: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   151: invokevirtual 31	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   154: invokespecial 119	org/json/JSONException:<init>	(Ljava/lang/String;)V
    //   157: athrow
    //   158: astore_0
    //   159: new 35	org/json/JSONException
    //   162: dup
    //   163: new 15	java/lang/StringBuilder
    //   166: dup
    //   167: invokespecial 16	java/lang/StringBuilder:<init>	()V
    //   170: ldc 122
    //   172: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   175: aload_2
    //   176: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   179: ldc 113
    //   181: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   184: aload_0
    //   185: invokevirtual 123	java/lang/InstantiationException:getMessage	()Ljava/lang/String;
    //   188: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   191: invokevirtual 31	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   194: invokespecial 119	org/json/JSONException:<init>	(Ljava/lang/String;)V
    //   197: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	198	0	paramJSONObject	JSONObject
    //   0	198	1	paramString	String
    //   0	198	2	paramClass	Class<T>
    //   33	42	3	i	int
    //   17	38	4	localJSONArray	JSONArray
    // Exception table:
    //   from	to	target	type
    //   12	32	78	java/lang/IllegalArgumentException
    //   36	71	78	java/lang/IllegalArgumentException
    //   12	32	118	java/lang/IllegalAccessException
    //   36	71	118	java/lang/IllegalAccessException
    //   12	32	158	java/lang/InstantiationException
    //   36	71	158	java/lang/InstantiationException
  }
  
  protected static <T extends BaseModel> T deserializeObject(JSONObject paramJSONObject, String paramString, Class<T> paramClass)
    throws JSONException
  {
    if (!paramJSONObject.has(paramString)) {
      return null;
    }
    try
    {
      paramString = paramJSONObject.getJSONObject(paramString);
      BaseModel localBaseModel = (BaseModel)paramClass.newInstance();
      localBaseModel.load(paramString);
      paramString = (BaseModel)paramClass.cast(localBaseModel);
      return paramString;
    }
    catch (JSONException paramString)
    {
      throw new JSONException("JSON deserialization failure for " + paramClass + " " + paramString.getMessage() + " JSON: " + paramJSONObject.toString());
    }
    catch (IllegalArgumentException paramJSONObject)
    {
      throw new JSONException("Reflection failed trying to call load on " + paramClass + " " + paramJSONObject.getMessage());
    }
    catch (IllegalAccessException paramJSONObject)
    {
      throw new JSONException("Reflection failed trying to call load on " + paramClass + " " + paramJSONObject.getMessage());
    }
    catch (InstantiationException paramJSONObject)
    {
      throw new JSONException("Reflection failed trying to instantiate " + paramClass + " " + paramJSONObject.getMessage());
    }
  }
  
  protected static RestTask doDelete(String paramString, RestTaskCallback paramRestTaskCallback)
  {
    return doDelete(paramString, null, paramRestTaskCallback);
  }
  
  protected static RestTask doDelete(String paramString, Map<String, String> paramMap, RestTaskCallback paramRestTaskCallback)
  {
    paramString = new RestTask(RestMethod.DELETE, paramString, paramMap, paramRestTaskCallback);
    paramString.execute(new String[0]);
    return paramString;
  }
  
  protected static RestTask doGet(String paramString, RestTaskCallback paramRestTaskCallback)
  {
    return doGet(paramString, null, paramRestTaskCallback);
  }
  
  protected static RestTask doGet(String paramString, Map<String, String> paramMap, RestTaskCallback paramRestTaskCallback)
  {
    paramString = new RestTask(RestMethod.GET, paramString, paramMap, paramRestTaskCallback);
    paramString.execute(new String[0]);
    return paramString;
  }
  
  protected static RestTask doPost(String paramString, RestTaskCallback paramRestTaskCallback)
  {
    return doPost(paramString, null, paramRestTaskCallback);
  }
  
  protected static RestTask doPost(String paramString, Map<String, String> paramMap, RestTaskCallback paramRestTaskCallback)
  {
    paramString = new RestTask(RestMethod.POST, paramString, paramMap, paramRestTaskCallback);
    paramString.execute(new String[0]);
    return paramString;
  }
  
  protected static RestTask doPut(String paramString, RestTaskCallback paramRestTaskCallback)
  {
    return doPut(paramString, null, paramRestTaskCallback);
  }
  
  protected static RestTask doPut(String paramString, Map<String, String> paramMap, RestTaskCallback paramRestTaskCallback)
  {
    paramString = new RestTask(RestMethod.PUT, paramString, paramMap, paramRestTaskCallback);
    paramString.execute(new String[0]);
    return paramString;
  }
  
  protected static ClientConfig getClientConfig()
  {
    return getSession().getClientConfig();
  }
  
  protected static Config getConfig()
  {
    return getSession().getConfig();
  }
  
  protected static Session getSession()
  {
    return Session.getInstance();
  }
  
  public static <T extends BaseModel> T load(SharedPreferences paramSharedPreferences, String paramString1, String paramString2, Class<T> paramClass)
  {
    try
    {
      paramSharedPreferences = deserializeObject(new JSONObject(paramSharedPreferences.getString(paramString1, "{}")), paramString2, paramClass);
      return paramSharedPreferences;
    }
    catch (JSONException paramSharedPreferences) {}
    return null;
  }
  
  @SuppressLint({"SimpleDateFormat"})
  protected Date getDate(JSONObject paramJSONObject, String paramString)
    throws JSONException
  {
    paramJSONObject = getString(paramJSONObject, paramString);
    paramString = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
    try
    {
      paramString = paramString.parse(paramJSONObject);
      return paramString;
    }
    catch (ParseException paramString)
    {
      throw new JSONException("Could not parse date: " + paramJSONObject);
    }
  }
  
  protected String getHtml(JSONObject paramJSONObject, String paramString)
    throws JSONException
  {
    if (paramJSONObject.isNull(paramString)) {
      return null;
    }
    return paramJSONObject.getString(paramString);
  }
  
  public int getId()
  {
    return this.id;
  }
  
  protected String getString(JSONObject paramJSONObject, String paramString)
    throws JSONException
  {
    if (paramJSONObject.isNull(paramString)) {
      return null;
    }
    return Html.fromHtml(paramJSONObject.getString(paramString)).toString().trim();
  }
  
  public void load(JSONObject paramJSONObject)
    throws JSONException
  {
    this.id = paramJSONObject.getInt("id");
  }
  
  public boolean persist(SharedPreferences paramSharedPreferences, String paramString1, String paramString2)
  {
    JSONObject localJSONObject2 = new JSONObject();
    JSONObject localJSONObject1 = new JSONObject();
    try
    {
      save(localJSONObject2);
      localJSONObject1.put(paramString2, localJSONObject2);
      paramSharedPreferences = paramSharedPreferences.edit();
      paramSharedPreferences.putString(paramString1, localJSONObject1.toString());
      return paramSharedPreferences.commit();
    }
    catch (JSONException paramSharedPreferences) {}
    return false;
  }
  
  public void save(JSONObject paramJSONObject)
    throws JSONException
  {
    paramJSONObject.put("id", this.id);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/uservoice/uservoicesdk/model/BaseModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */