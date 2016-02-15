package org.json;

import java.io.StringWriter;

public class JSONStringer
  extends JSONWriter
{
  public JSONStringer()
  {
    super(new StringWriter());
  }
  
  public String toString()
  {
    if (this.mode == 'd') {
      return this.writer.toString();
    }
    return null;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/JSONStringer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */