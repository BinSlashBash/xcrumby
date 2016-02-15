package com.crumby.lib.router;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class IndexSetting
{
  private Map<String, IndexAttributeValue> attributes;
  private final FragmentIndex index;
  private boolean showInSearch;
  
  public IndexSetting(FragmentIndex paramFragmentIndex)
  {
    this.index = paramFragmentIndex;
    this.showInSearch = true;
    buildAttributes();
  }
  
  public IndexSetting(IndexSetting paramIndexSetting)
  {
    this.showInSearch = paramIndexSetting.showInSearch;
    this.index = paramIndexSetting.index;
    buildAttributes();
    paramIndexSetting = paramIndexSetting.attributes.entrySet().iterator();
    while (paramIndexSetting.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramIndexSetting.next();
      this.attributes.put(localEntry.getKey(), ((IndexAttributeValue)localEntry.getValue()).copy());
    }
  }
  
  private void buildAttributes()
  {
    this.attributes = new HashMap();
    IndexField[] arrayOfIndexField = this.index.getSettingAttributes().indexFields;
    int j = arrayOfIndexField.length;
    int i = 0;
    if (i < j)
    {
      IndexField localIndexField = arrayOfIndexField[i];
      IndexAttributeValue localIndexAttributeValue = null;
      if ((localIndexField.defaultValue instanceof Number)) {
        localIndexAttributeValue = new IndexAttributeValue((Number)localIndexField.defaultValue);
      }
      for (;;)
      {
        this.attributes.put(localIndexField.key, localIndexAttributeValue);
        i += 1;
        break;
        if ((localIndexField.defaultValue instanceof Boolean)) {
          localIndexAttributeValue = new IndexAttributeValue((Boolean)localIndexField.defaultValue);
        } else if ((localIndexField.defaultValue instanceof String)) {
          localIndexAttributeValue = new IndexAttributeValue((String)localIndexField.defaultValue);
        } else if ((localIndexField.defaultValue instanceof Character)) {
          localIndexAttributeValue = new IndexAttributeValue((Character)localIndexField.defaultValue);
        }
      }
    }
  }
  
  public JsonObject getAsJson()
    throws Exception
  {
    JsonObject localJsonObject = new JsonObject();
    Iterator localIterator = this.attributes.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localJsonObject.add((String)localEntry.getKey(), ((IndexAttributeValue)localEntry.getValue()).getAsJsonPrimitive());
    }
    return localJsonObject;
  }
  
  public boolean getAttributeBoolean(String paramString)
  {
    if (this.attributes.get(paramString) == null) {
      return false;
    }
    return ((IndexAttributeValue)this.attributes.get(paramString)).getAsBoolean();
  }
  
  public IndexAttributeValue getAttributeValue(String paramString)
  {
    return (IndexAttributeValue)this.attributes.get(paramString);
  }
  
  public String getBaseUrl()
  {
    return this.index.getBaseUrl();
  }
  
  public String getDisplayName()
  {
    return this.index.getDisplayName();
  }
  
  public String getFaviconUrl()
  {
    return this.index.getFaviconUrl();
  }
  
  public IndexField[] getFields()
  {
    return this.index.getSettingAttributes().indexFields;
  }
  
  public String getFragmentClassName()
  {
    return this.index.getFragmentClassName();
  }
  
  public FragmentIndex getIndex()
  {
    return this.index;
  }
  
  public void restoreFromJson(JsonObject paramJsonObject)
    throws Exception
  {
    paramJsonObject = paramJsonObject.entrySet().iterator();
    while (paramJsonObject.hasNext())
    {
      Object localObject = (Map.Entry)paramJsonObject.next();
      String str = (String)((Map.Entry)localObject).getKey();
      localObject = (JsonElement)((Map.Entry)localObject).getValue();
      IndexAttributeValue localIndexAttributeValue = (IndexAttributeValue)this.attributes.get(str);
      if (localIndexAttributeValue != null)
      {
        localIndexAttributeValue.setValueFromJson(((JsonElement)localObject).getAsJsonPrimitive());
        this.attributes.put(str, localIndexAttributeValue);
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/router/IndexSetting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */