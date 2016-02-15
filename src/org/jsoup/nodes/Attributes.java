package org.jsoup.nodes;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.jsoup.helper.Validate;

public class Attributes
  implements Iterable<Attribute>, Cloneable
{
  protected static final String dataPrefix = "data-";
  private LinkedHashMap<String, Attribute> attributes = null;
  
  private static String dataKey(String paramString)
  {
    return "data-" + paramString;
  }
  
  public void addAll(Attributes paramAttributes)
  {
    if (paramAttributes.size() == 0) {
      return;
    }
    if (this.attributes == null) {
      this.attributes = new LinkedHashMap(paramAttributes.size());
    }
    this.attributes.putAll(paramAttributes.attributes);
  }
  
  public List<Attribute> asList()
  {
    if (this.attributes == null) {
      return Collections.emptyList();
    }
    ArrayList localArrayList = new ArrayList(this.attributes.size());
    Iterator localIterator = this.attributes.entrySet().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(((Map.Entry)localIterator.next()).getValue());
    }
    return Collections.unmodifiableList(localArrayList);
  }
  
  /* Error */
  public Attributes clone()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 33	org/jsoup/nodes/Attributes:attributes	Ljava/util/LinkedHashMap;
    //   4: ifnonnull +13 -> 17
    //   7: new 2	org/jsoup/nodes/Attributes
    //   10: dup
    //   11: invokespecial 125	org/jsoup/nodes/Attributes:<init>	()V
    //   14: astore_1
    //   15: aload_1
    //   16: areturn
    //   17: aload_0
    //   18: invokespecial 127	java/lang/Object:clone	()Ljava/lang/Object;
    //   21: checkcast 2	org/jsoup/nodes/Attributes
    //   24: astore_2
    //   25: aload_2
    //   26: new 62	java/util/LinkedHashMap
    //   29: dup
    //   30: aload_0
    //   31: getfield 33	org/jsoup/nodes/Attributes:attributes	Ljava/util/LinkedHashMap;
    //   34: invokevirtual 79	java/util/LinkedHashMap:size	()I
    //   37: invokespecial 65	java/util/LinkedHashMap:<init>	(I)V
    //   40: putfield 33	org/jsoup/nodes/Attributes:attributes	Ljava/util/LinkedHashMap;
    //   43: aload_0
    //   44: invokevirtual 128	org/jsoup/nodes/Attributes:iterator	()Ljava/util/Iterator;
    //   47: astore_3
    //   48: aload_2
    //   49: astore_1
    //   50: aload_3
    //   51: invokeinterface 96 1 0
    //   56: ifeq -41 -> 15
    //   59: aload_3
    //   60: invokeinterface 100 1 0
    //   65: checkcast 130	org/jsoup/nodes/Attribute
    //   68: astore_1
    //   69: aload_2
    //   70: getfield 33	org/jsoup/nodes/Attributes:attributes	Ljava/util/LinkedHashMap;
    //   73: aload_1
    //   74: invokevirtual 133	org/jsoup/nodes/Attribute:getKey	()Ljava/lang/String;
    //   77: aload_1
    //   78: invokevirtual 136	org/jsoup/nodes/Attribute:clone	()Lorg/jsoup/nodes/Attribute;
    //   81: invokevirtual 140	java/util/LinkedHashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   84: pop
    //   85: goto -37 -> 48
    //   88: astore_1
    //   89: new 142	java/lang/RuntimeException
    //   92: dup
    //   93: aload_1
    //   94: invokespecial 145	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   97: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	98	0	this	Attributes
    //   14	64	1	localObject	Object
    //   88	6	1	localCloneNotSupportedException	CloneNotSupportedException
    //   24	46	2	localAttributes	Attributes
    //   47	13	3	localIterator	Iterator
    // Exception table:
    //   from	to	target	type
    //   17	25	88	java/lang/CloneNotSupportedException
  }
  
  public Map<String, String> dataset()
  {
    return new Dataset(null);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (!(paramObject instanceof Attributes)) {
        return false;
      }
      paramObject = (Attributes)paramObject;
      if (this.attributes == null) {
        break;
      }
    } while (this.attributes.equals(((Attributes)paramObject).attributes));
    for (;;)
    {
      return false;
      if (((Attributes)paramObject).attributes == null) {
        break;
      }
    }
  }
  
  public String get(String paramString)
  {
    Validate.notEmpty(paramString);
    if (this.attributes == null) {
      return "";
    }
    paramString = (Attribute)this.attributes.get(paramString.toLowerCase());
    if (paramString != null) {
      return paramString.getValue();
    }
    return "";
  }
  
  public boolean hasKey(String paramString)
  {
    return (this.attributes != null) && (this.attributes.containsKey(paramString.toLowerCase()));
  }
  
  public int hashCode()
  {
    if (this.attributes != null) {
      return this.attributes.hashCode();
    }
    return 0;
  }
  
  public String html()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    html(localStringBuilder, new Document("").outputSettings());
    return localStringBuilder.toString();
  }
  
  void html(StringBuilder paramStringBuilder, Document.OutputSettings paramOutputSettings)
  {
    if (this.attributes == null) {}
    for (;;)
    {
      return;
      Iterator localIterator = this.attributes.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Attribute localAttribute = (Attribute)((Map.Entry)localIterator.next()).getValue();
        paramStringBuilder.append(" ");
        localAttribute.html(paramStringBuilder, paramOutputSettings);
      }
    }
  }
  
  public Iterator<Attribute> iterator()
  {
    return asList().iterator();
  }
  
  public void put(String paramString1, String paramString2)
  {
    put(new Attribute(paramString1, paramString2));
  }
  
  public void put(Attribute paramAttribute)
  {
    Validate.notNull(paramAttribute);
    if (this.attributes == null) {
      this.attributes = new LinkedHashMap(2);
    }
    this.attributes.put(paramAttribute.getKey(), paramAttribute);
  }
  
  public void remove(String paramString)
  {
    Validate.notEmpty(paramString);
    if (this.attributes == null) {
      return;
    }
    this.attributes.remove(paramString.toLowerCase());
  }
  
  public int size()
  {
    if (this.attributes == null) {
      return 0;
    }
    return this.attributes.size();
  }
  
  public String toString()
  {
    return html();
  }
  
  private class Dataset
    extends AbstractMap<String, String>
  {
    private Dataset()
    {
      if (Attributes.this.attributes == null) {
        Attributes.access$102(Attributes.this, new LinkedHashMap(2));
      }
    }
    
    public Set<Map.Entry<String, String>> entrySet()
    {
      return new EntrySet(null);
    }
    
    public String put(String paramString1, String paramString2)
    {
      String str = Attributes.dataKey(paramString1);
      if (Attributes.this.hasKey(str)) {}
      for (paramString1 = ((Attribute)Attributes.this.attributes.get(str)).getValue();; paramString1 = null)
      {
        paramString2 = new Attribute(str, paramString2);
        Attributes.this.attributes.put(str, paramString2);
        return paramString1;
      }
    }
    
    private class DatasetIterator
      implements Iterator<Map.Entry<String, String>>
    {
      private Attribute attr;
      private Iterator<Attribute> attrIter = Attributes.this.attributes.values().iterator();
      
      private DatasetIterator() {}
      
      public boolean hasNext()
      {
        while (this.attrIter.hasNext())
        {
          this.attr = ((Attribute)this.attrIter.next());
          if (this.attr.isDataAttribute()) {
            return true;
          }
        }
        return false;
      }
      
      public Map.Entry<String, String> next()
      {
        return new Attribute(this.attr.getKey().substring("data-".length()), this.attr.getValue());
      }
      
      public void remove()
      {
        Attributes.this.attributes.remove(this.attr.getKey());
      }
    }
    
    private class EntrySet
      extends AbstractSet<Map.Entry<String, String>>
    {
      private EntrySet() {}
      
      public Iterator<Map.Entry<String, String>> iterator()
      {
        return new Attributes.Dataset.DatasetIterator(Attributes.Dataset.this, null);
      }
      
      public int size()
      {
        int i = 0;
        Attributes.Dataset.DatasetIterator localDatasetIterator = new Attributes.Dataset.DatasetIterator(Attributes.Dataset.this, null);
        while (localDatasetIterator.hasNext()) {
          i += 1;
        }
        return i;
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/nodes/Attributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */