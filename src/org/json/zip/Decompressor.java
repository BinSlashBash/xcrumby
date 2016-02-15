package org.json.zip;

import java.io.UnsupportedEncodingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.Kim;

public class Decompressor
  extends JSONzip
{
  BitReader bitreader;
  
  public Decompressor(BitReader paramBitReader)
  {
    this.bitreader = paramBitReader;
  }
  
  private boolean bit()
    throws JSONException
  {
    try
    {
      boolean bool = this.bitreader.bit();
      return bool;
    }
    catch (Throwable localThrowable)
    {
      throw new JSONException(localThrowable);
    }
  }
  
  private Object getAndTick(Keep paramKeep, BitReader paramBitReader)
    throws JSONException
  {
    int i;
    try
    {
      i = paramBitReader.read(paramKeep.bitsize());
      paramBitReader = paramKeep.value(i);
      if (i >= paramKeep.length) {
        throw new JSONException("Deep error.");
      }
    }
    catch (Throwable paramKeep)
    {
      throw new JSONException(paramKeep);
    }
    paramKeep.tick(i);
    return paramBitReader;
  }
  
  private int read(int paramInt)
    throws JSONException
  {
    try
    {
      paramInt = this.bitreader.read(paramInt);
      return paramInt;
    }
    catch (Throwable localThrowable)
    {
      throw new JSONException(localThrowable);
    }
  }
  
  private JSONArray readArray(boolean paramBoolean)
    throws JSONException
  {
    JSONArray localJSONArray = new JSONArray();
    if (paramBoolean) {}
    for (Object localObject = readString();; localObject = readValue())
    {
      localJSONArray.put(localObject);
      if (bit()) {
        break label73;
      }
      if (bit()) {
        break;
      }
      return localJSONArray;
    }
    if (paramBoolean) {}
    for (localObject = readValue();; localObject = readString())
    {
      localJSONArray.put(localObject);
      break;
    }
    label73:
    if (paramBoolean) {}
    for (localObject = readString();; localObject = readValue())
    {
      localJSONArray.put(localObject);
      break;
    }
  }
  
  private Object readJSON()
    throws JSONException
  {
    switch (read(3))
    {
    case 4: 
    default: 
      return JSONObject.NULL;
    case 5: 
      return readObject();
    case 6: 
      return readArray(true);
    case 7: 
      return readArray(false);
    case 0: 
      return new JSONObject();
    case 1: 
      return new JSONArray();
    case 2: 
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }
  
  private String readName()
    throws JSONException
  {
    Object localObject = new byte[65536];
    int i = 0;
    if (!bit())
    {
      for (;;)
      {
        int j = this.namehuff.read(this.bitreader);
        if (j == 256)
        {
          if (i != 0) {
            break;
          }
          return "";
        }
        localObject[i] = ((byte)j);
        i += 1;
      }
      localObject = new Kim((byte[])localObject, i);
      this.namekeep.register(localObject);
      return ((Kim)localObject).toString();
    }
    return getAndTick(this.namekeep, this.bitreader).toString();
  }
  
  private JSONObject readObject()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    String str = readName();
    if (!bit()) {}
    for (Object localObject = readString();; localObject = readValue())
    {
      localJSONObject.put(str, localObject);
      if (bit()) {
        break;
      }
      return localJSONObject;
    }
  }
  
  private String readString()
    throws JSONException
  {
    int i = 0;
    int j = -1;
    int k = 0;
    if (bit()) {
      return getAndTick(this.stringkeep, this.bitreader).toString();
    }
    Object localObject = new byte[65536];
    boolean bool = bit();
    this.substringkeep.reserve();
    for (;;)
    {
      int m;
      if (bool)
      {
        m = ((Kim)getAndTick(this.substringkeep, this.bitreader)).copy((byte[])localObject, i);
        if (j != -1) {
          this.substringkeep.registerOne(new Kim((byte[])localObject, j, k + 1));
        }
        k = m;
        bool = bit();
        j = i;
        i = m;
      }
      else
      {
        for (;;)
        {
          m = this.substringhuff.read(this.bitreader);
          if (m == 256)
          {
            if (bit()) {
              break;
            }
            if (i != 0) {
              break label207;
            }
            return "";
          }
          localObject[i] = ((byte)m);
          m = i + 1;
          i = m;
          if (j != -1)
          {
            this.substringkeep.registerOne(new Kim((byte[])localObject, j, k + 1));
            j = -1;
            i = m;
          }
        }
        bool = true;
      }
    }
    label207:
    localObject = new Kim((byte[])localObject, i);
    this.stringkeep.register(localObject);
    this.substringkeep.registerMany((Kim)localObject);
    return ((Kim)localObject).toString();
  }
  
  private Object readValue()
    throws JSONException
  {
    int i = 4;
    switch (read(2))
    {
    default: 
      throw new JSONException("Impossible.");
    case 0: 
      if (!bit()) {}
      for (;;)
      {
        return new Integer(read(i));
        if (!bit()) {
          i = 7;
        } else {
          i = 14;
        }
      }
    case 1: 
      Object localObject = new byte['Ā'];
      i = 0;
      for (;;)
      {
        int j = read(4);
        if (j == endOfNumber) {}
        try
        {
          localObject = JSONObject.stringToValue(new String((byte[])localObject, 0, i, "US-ASCII"));
          this.values.register(localObject);
          return localObject;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          throw new JSONException(localUnsupportedEncodingException);
        }
        localObject[i] = bcd[j];
        i += 1;
      }
    case 2: 
      return getAndTick(this.values, this.bitreader);
    }
    return readJSON();
  }
  
  public boolean pad(int paramInt)
    throws JSONException
  {
    try
    {
      boolean bool = this.bitreader.pad(paramInt);
      return bool;
    }
    catch (Throwable localThrowable)
    {
      throw new JSONException(localThrowable);
    }
  }
  
  public Object unzip()
    throws JSONException
  {
    begin();
    return readJSON();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/zip/Decompressor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */