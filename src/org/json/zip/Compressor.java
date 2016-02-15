package org.json.zip;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.Kim;

public class Compressor
  extends JSONzip
{
  final BitWriter bitwriter;
  
  public Compressor(BitWriter paramBitWriter)
  {
    this.bitwriter = paramBitWriter;
  }
  
  private static int bcd(char paramChar)
  {
    if ((paramChar >= '0') && (paramChar <= '9')) {
      return paramChar - '0';
    }
    switch (paramChar)
    {
    case ',': 
    default: 
      return 13;
    case '.': 
      return 10;
    case '-': 
      return 11;
    }
    return 12;
  }
  
  private void one()
    throws JSONException
  {
    write(1, 1);
  }
  
  private void write(int paramInt1, int paramInt2)
    throws JSONException
  {
    try
    {
      this.bitwriter.write(paramInt1, paramInt2);
      return;
    }
    catch (Throwable localThrowable)
    {
      throw new JSONException(localThrowable);
    }
  }
  
  private void write(int paramInt, Huff paramHuff)
    throws JSONException
  {
    paramHuff.write(paramInt, this.bitwriter);
  }
  
  private void write(Kim paramKim, int paramInt1, int paramInt2, Huff paramHuff)
    throws JSONException
  {
    while (paramInt1 < paramInt2)
    {
      write(paramKim.get(paramInt1), paramHuff);
      paramInt1 += 1;
    }
  }
  
  private void write(Kim paramKim, Huff paramHuff)
    throws JSONException
  {
    write(paramKim, 0, paramKim.length, paramHuff);
  }
  
  private void writeAndTick(int paramInt, Keep paramKeep)
    throws JSONException
  {
    int i = paramKeep.bitsize();
    paramKeep.tick(paramInt);
    write(paramInt, i);
  }
  
  private void writeArray(JSONArray paramJSONArray)
    throws JSONException
  {
    int k = 0;
    int j = paramJSONArray.length();
    if (j == 0)
    {
      write(1, 3);
      return;
    }
    Object localObject2 = paramJSONArray.get(0);
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = JSONObject.NULL;
    }
    int i;
    if ((localObject1 instanceof String))
    {
      k = 1;
      write(6, 3);
      writeString((String)localObject1);
      i = 1;
      label69:
      if (i >= j) {
        break label162;
      }
      localObject2 = paramJSONArray.get(i);
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = JSONObject.NULL;
      }
      if (localObject1 instanceof String != k) {
        zero();
      }
      one();
      if (!(localObject1 instanceof String)) {
        break label153;
      }
      writeString((String)localObject1);
    }
    for (;;)
    {
      i += 1;
      break label69;
      write(7, 3);
      writeValue(localObject1);
      break;
      label153:
      writeValue(localObject1);
    }
    label162:
    zero();
    zero();
  }
  
  private void writeJSON(Object paramObject)
    throws JSONException
  {
    if (JSONObject.NULL.equals(paramObject))
    {
      write(4, 3);
      return;
    }
    if (Boolean.FALSE.equals(paramObject))
    {
      write(3, 3);
      return;
    }
    if (Boolean.TRUE.equals(paramObject))
    {
      write(2, 3);
      return;
    }
    Object localObject;
    if ((paramObject instanceof Map)) {
      localObject = new JSONObject((Map)paramObject);
    }
    while ((localObject instanceof JSONObject))
    {
      writeObject((JSONObject)localObject);
      return;
      if ((paramObject instanceof Collection))
      {
        localObject = new JSONArray((Collection)paramObject);
      }
      else
      {
        localObject = paramObject;
        if (paramObject.getClass().isArray()) {
          localObject = new JSONArray(paramObject);
        }
      }
    }
    if ((localObject instanceof JSONArray))
    {
      writeArray((JSONArray)localObject);
      return;
    }
    throw new JSONException("Unrecognized object");
  }
  
  private void writeName(String paramString)
    throws JSONException
  {
    paramString = new Kim(paramString);
    int i = this.namekeep.find(paramString);
    if (i != -1)
    {
      one();
      writeAndTick(i, this.namekeep);
      return;
    }
    zero();
    write(paramString, this.namehuff);
    write(256, this.namehuff);
    this.namekeep.register(paramString);
  }
  
  private void writeObject(JSONObject paramJSONObject)
    throws JSONException
  {
    int i = 1;
    Iterator localIterator = paramJSONObject.keys();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof String))
      {
        if (i != 0)
        {
          i = 0;
          write(5, 3);
        }
        for (;;)
        {
          writeName((String)localObject);
          localObject = paramJSONObject.get((String)localObject);
          if (!(localObject instanceof String)) {
            break label95;
          }
          zero();
          writeString((String)localObject);
          break;
          one();
        }
        label95:
        one();
        writeValue(localObject);
      }
    }
    if (i != 0)
    {
      write(0, 3);
      return;
    }
    zero();
  }
  
  private void writeString(String paramString)
    throws JSONException
  {
    if (paramString.length() == 0)
    {
      zero();
      zero();
      write(256, this.substringhuff);
      zero();
      return;
    }
    paramString = new Kim(paramString);
    int i = this.stringkeep.find(paramString);
    if (i != -1)
    {
      one();
      writeAndTick(i, this.stringkeep);
      return;
    }
    writeSubstring(paramString);
    this.stringkeep.register(paramString);
  }
  
  private void writeSubstring(Kim paramKim)
    throws JSONException
  {
    this.substringkeep.reserve();
    zero();
    int i = 0;
    int i2 = paramKim.length;
    int j = -1;
    for (int n = 0;; n = i + 1)
    {
      int m = -1;
      int k = i;
      for (;;)
      {
        if (k <= i2 - 3)
        {
          m = this.substringkeep.match(paramKim, k, i2);
          if (m == -1) {}
        }
        else
        {
          if (m != -1) {
            break;
          }
          zero();
          if (i < i2)
          {
            write(paramKim, i, i2, this.substringhuff);
            if (j != -1) {
              this.substringkeep.registerOne(paramKim, j, n);
            }
          }
          write(256, this.substringhuff);
          zero();
          this.substringkeep.registerMany(paramKim);
          return;
        }
        k += 1;
      }
      int i1 = j;
      if (i != k)
      {
        zero();
        write(paramKim, i, k, this.substringhuff);
        write(256, this.substringhuff);
        i1 = j;
        if (j != -1)
        {
          this.substringkeep.registerOne(paramKim, j, n);
          i1 = -1;
        }
      }
      one();
      writeAndTick(m, this.substringkeep);
      i = k + this.substringkeep.length(m);
      if (i1 != -1) {
        this.substringkeep.registerOne(paramKim, i1, n);
      }
      j = k;
    }
  }
  
  private void writeValue(Object paramObject)
    throws JSONException
  {
    if ((paramObject instanceof Number))
    {
      String str = JSONObject.numberToString((Number)paramObject);
      int i = this.values.find(str);
      if (i != -1)
      {
        write(2, 2);
        writeAndTick(i, this.values);
        return;
      }
      if (((paramObject instanceof Integer)) || ((paramObject instanceof Long)))
      {
        long l = ((Number)paramObject).longValue();
        if ((l >= 0L) && (l < 16384L))
        {
          write(0, 2);
          if (l < 16L)
          {
            zero();
            write((int)l, 4);
            return;
          }
          one();
          if (l < 128L)
          {
            zero();
            write((int)l, 7);
            return;
          }
          one();
          write((int)l, 14);
          return;
        }
      }
      write(1, 2);
      i = 0;
      while (i < str.length())
      {
        write(bcd(str.charAt(i)), 4);
        i += 1;
      }
      write(endOfNumber, 4);
      this.values.register(str);
      return;
    }
    write(3, 2);
    writeJSON(paramObject);
  }
  
  private void zero()
    throws JSONException
  {
    write(0, 1);
  }
  
  public void flush()
    throws JSONException
  {
    pad(8);
  }
  
  public void pad(int paramInt)
    throws JSONException
  {
    try
    {
      this.bitwriter.pad(paramInt);
      return;
    }
    catch (Throwable localThrowable)
    {
      throw new JSONException(localThrowable);
    }
  }
  
  public void zip(JSONArray paramJSONArray)
    throws JSONException
  {
    begin();
    writeJSON(paramJSONArray);
  }
  
  public void zip(JSONObject paramJSONObject)
    throws JSONException
  {
    begin();
    writeJSON(paramJSONObject);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/zip/Compressor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */