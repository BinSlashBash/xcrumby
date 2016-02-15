package org.jsoup.safety;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

public class Whitelist
{
  private Map<TagName, Set<AttributeKey>> attributes = new HashMap();
  private Map<TagName, Map<AttributeKey, AttributeValue>> enforcedAttributes = new HashMap();
  private boolean preserveRelativeLinks = false;
  private Map<TagName, Map<AttributeKey, Set<Protocol>>> protocols = new HashMap();
  private Set<TagName> tagNames = new HashSet();
  
  public static Whitelist basic()
  {
    return new Whitelist().addTags(new String[] { "a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em", "i", "li", "ol", "p", "pre", "q", "small", "strike", "strong", "sub", "sup", "u", "ul" }).addAttributes("a", new String[] { "href" }).addAttributes("blockquote", new String[] { "cite" }).addAttributes("q", new String[] { "cite" }).addProtocols("a", "href", new String[] { "ftp", "http", "https", "mailto" }).addProtocols("blockquote", "cite", new String[] { "http", "https" }).addProtocols("cite", "cite", new String[] { "http", "https" }).addEnforcedAttribute("a", "rel", "nofollow");
  }
  
  public static Whitelist basicWithImages()
  {
    return basic().addTags(new String[] { "img" }).addAttributes("img", new String[] { "align", "alt", "height", "src", "title", "width" }).addProtocols("img", "src", new String[] { "http", "https" });
  }
  
  public static Whitelist none()
  {
    return new Whitelist();
  }
  
  public static Whitelist relaxed()
  {
    return new Whitelist().addTags(new String[] { "a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img", "li", "ol", "p", "pre", "q", "small", "strike", "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u", "ul" }).addAttributes("a", new String[] { "href", "title" }).addAttributes("blockquote", new String[] { "cite" }).addAttributes("col", new String[] { "span", "width" }).addAttributes("colgroup", new String[] { "span", "width" }).addAttributes("img", new String[] { "align", "alt", "height", "src", "title", "width" }).addAttributes("ol", new String[] { "start", "type" }).addAttributes("q", new String[] { "cite" }).addAttributes("table", new String[] { "summary", "width" }).addAttributes("td", new String[] { "abbr", "axis", "colspan", "rowspan", "width" }).addAttributes("th", new String[] { "abbr", "axis", "colspan", "rowspan", "scope", "width" }).addAttributes("ul", new String[] { "type" }).addProtocols("a", "href", new String[] { "ftp", "http", "https", "mailto" }).addProtocols("blockquote", "cite", new String[] { "http", "https" }).addProtocols("cite", "cite", new String[] { "http", "https" }).addProtocols("img", "src", new String[] { "http", "https" }).addProtocols("q", "cite", new String[] { "http", "https" });
  }
  
  public static Whitelist simpleText()
  {
    return new Whitelist().addTags(new String[] { "b", "em", "i", "strong", "u" });
  }
  
  private boolean testValidProtocol(Element paramElement, Attribute paramAttribute, Set<Protocol> paramSet)
  {
    String str = paramElement.absUrl(paramAttribute.getKey());
    paramElement = str;
    if (str.length() == 0) {
      paramElement = paramAttribute.getValue();
    }
    if (!this.preserveRelativeLinks) {
      paramAttribute.setValue(paramElement);
    }
    paramAttribute = paramSet.iterator();
    while (paramAttribute.hasNext())
    {
      paramSet = (Protocol)paramAttribute.next();
      paramSet = paramSet.toString() + ":";
      if (paramElement.toLowerCase().startsWith(paramSet)) {
        return true;
      }
    }
    return false;
  }
  
  public Whitelist addAttributes(String paramString, String... paramVarArgs)
  {
    Validate.notEmpty(paramString);
    Validate.notNull(paramVarArgs);
    if (paramVarArgs.length > 0) {}
    HashSet localHashSet;
    for (boolean bool = true;; bool = false)
    {
      Validate.isTrue(bool, "No attributes supplied.");
      paramString = TagName.valueOf(paramString);
      if (!this.tagNames.contains(paramString)) {
        this.tagNames.add(paramString);
      }
      localHashSet = new HashSet();
      int j = paramVarArgs.length;
      int i = 0;
      while (i < j)
      {
        String str = paramVarArgs[i];
        Validate.notEmpty(str);
        localHashSet.add(AttributeKey.valueOf(str));
        i += 1;
      }
    }
    if (this.attributes.containsKey(paramString))
    {
      ((Set)this.attributes.get(paramString)).addAll(localHashSet);
      return this;
    }
    this.attributes.put(paramString, localHashSet);
    return this;
  }
  
  public Whitelist addEnforcedAttribute(String paramString1, String paramString2, String paramString3)
  {
    Validate.notEmpty(paramString1);
    Validate.notEmpty(paramString2);
    Validate.notEmpty(paramString3);
    paramString1 = TagName.valueOf(paramString1);
    if (!this.tagNames.contains(paramString1)) {
      this.tagNames.add(paramString1);
    }
    paramString2 = AttributeKey.valueOf(paramString2);
    paramString3 = AttributeValue.valueOf(paramString3);
    if (this.enforcedAttributes.containsKey(paramString1))
    {
      ((Map)this.enforcedAttributes.get(paramString1)).put(paramString2, paramString3);
      return this;
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put(paramString2, paramString3);
    this.enforcedAttributes.put(paramString1, localHashMap);
    return this;
  }
  
  public Whitelist addProtocols(String paramString1, String paramString2, String... paramVarArgs)
  {
    Validate.notEmpty(paramString1);
    Validate.notEmpty(paramString2);
    Validate.notNull(paramVarArgs);
    TagName localTagName = TagName.valueOf(paramString1);
    AttributeKey localAttributeKey = AttributeKey.valueOf(paramString2);
    if (this.protocols.containsKey(localTagName))
    {
      paramString1 = (Map)this.protocols.get(localTagName);
      if (!paramString1.containsKey(localAttributeKey)) {
        break label143;
      }
    }
    for (paramString1 = (Set)paramString1.get(localAttributeKey);; paramString1 = paramString2)
    {
      int j = paramVarArgs.length;
      int i = 0;
      while (i < j)
      {
        paramString2 = paramVarArgs[i];
        Validate.notEmpty(paramString2);
        paramString1.add(Protocol.valueOf(paramString2));
        i += 1;
      }
      paramString1 = new HashMap();
      this.protocols.put(localTagName, paramString1);
      break;
      label143:
      paramString2 = new HashSet();
      paramString1.put(localAttributeKey, paramString2);
    }
    return this;
  }
  
  public Whitelist addTags(String... paramVarArgs)
  {
    Validate.notNull(paramVarArgs);
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      String str = paramVarArgs[i];
      Validate.notEmpty(str);
      this.tagNames.add(TagName.valueOf(str));
      i += 1;
    }
    return this;
  }
  
  Attributes getEnforcedAttributes(String paramString)
  {
    Attributes localAttributes = new Attributes();
    paramString = TagName.valueOf(paramString);
    if (this.enforcedAttributes.containsKey(paramString))
    {
      paramString = ((Map)this.enforcedAttributes.get(paramString)).entrySet().iterator();
      while (paramString.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramString.next();
        localAttributes.put(((AttributeKey)localEntry.getKey()).toString(), ((AttributeValue)localEntry.getValue()).toString());
      }
    }
    return localAttributes;
  }
  
  protected boolean isSafeAttribute(String paramString, Element paramElement, Attribute paramAttribute)
  {
    boolean bool = true;
    TagName localTagName = TagName.valueOf(paramString);
    AttributeKey localAttributeKey = AttributeKey.valueOf(paramAttribute.getKey());
    if ((this.attributes.containsKey(localTagName)) && (((Set)this.attributes.get(localTagName)).contains(localAttributeKey))) {
      if (this.protocols.containsKey(localTagName))
      {
        paramString = (Map)this.protocols.get(localTagName);
        if ((paramString.containsKey(localAttributeKey)) && (!testValidProtocol(paramElement, paramAttribute, (Set)paramString.get(localAttributeKey)))) {
          break label122;
        }
        bool = true;
      }
    }
    label122:
    while ((!paramString.equals(":all")) && (isSafeAttribute(":all", paramElement, paramAttribute))) {
      for (;;)
      {
        return bool;
        bool = false;
      }
    }
    return false;
  }
  
  protected boolean isSafeTag(String paramString)
  {
    return this.tagNames.contains(TagName.valueOf(paramString));
  }
  
  public Whitelist preserveRelativeLinks(boolean paramBoolean)
  {
    this.preserveRelativeLinks = paramBoolean;
    return this;
  }
  
  static class AttributeKey
    extends Whitelist.TypedValue
  {
    AttributeKey(String paramString)
    {
      super();
    }
    
    static AttributeKey valueOf(String paramString)
    {
      return new AttributeKey(paramString);
    }
  }
  
  static class AttributeValue
    extends Whitelist.TypedValue
  {
    AttributeValue(String paramString)
    {
      super();
    }
    
    static AttributeValue valueOf(String paramString)
    {
      return new AttributeValue(paramString);
    }
  }
  
  static class Protocol
    extends Whitelist.TypedValue
  {
    Protocol(String paramString)
    {
      super();
    }
    
    static Protocol valueOf(String paramString)
    {
      return new Protocol(paramString);
    }
  }
  
  static class TagName
    extends Whitelist.TypedValue
  {
    TagName(String paramString)
    {
      super();
    }
    
    static TagName valueOf(String paramString)
    {
      return new TagName(paramString);
    }
  }
  
  static abstract class TypedValue
  {
    private String value;
    
    TypedValue(String paramString)
    {
      Validate.notNull(paramString);
      this.value = paramString;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      do
      {
        do
        {
          return true;
          if (paramObject == null) {
            return false;
          }
          if (getClass() != paramObject.getClass()) {
            return false;
          }
          paramObject = (TypedValue)paramObject;
          if (this.value != null) {
            break;
          }
        } while (((TypedValue)paramObject).value == null);
        return false;
      } while (this.value.equals(((TypedValue)paramObject).value));
      return false;
    }
    
    public int hashCode()
    {
      if (this.value == null) {}
      for (int i = 0;; i = this.value.hashCode()) {
        return i + 31;
      }
    }
    
    public String toString()
    {
      return this.value;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/safety/Whitelist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */