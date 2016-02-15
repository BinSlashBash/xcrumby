package org.apache.commons.codec.language.bm;

import java.io.InputStream;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class Languages
{
  public static final String ANY = "any";
  public static final LanguageSet ANY_LANGUAGE = new LanguageSet()
  {
    public boolean contains(String paramAnonymousString)
    {
      return true;
    }
    
    public String getAny()
    {
      throw new NoSuchElementException("Can't fetch any language from the any language set.");
    }
    
    public boolean isEmpty()
    {
      return false;
    }
    
    public boolean isSingleton()
    {
      return false;
    }
    
    public Languages.LanguageSet restrictTo(Languages.LanguageSet paramAnonymousLanguageSet)
    {
      return paramAnonymousLanguageSet;
    }
    
    public String toString()
    {
      return "ANY_LANGUAGE";
    }
  };
  private static final Map<NameType, Languages> LANGUAGES = new EnumMap(NameType.class);
  public static final LanguageSet NO_LANGUAGES;
  private final Set<String> languages;
  
  static
  {
    NameType[] arrayOfNameType = NameType.values();
    int j = arrayOfNameType.length;
    int i = 0;
    while (i < j)
    {
      NameType localNameType = arrayOfNameType[i];
      LANGUAGES.put(localNameType, getInstance(langResourceName(localNameType)));
      i += 1;
    }
    NO_LANGUAGES = new LanguageSet()
    {
      public boolean contains(String paramAnonymousString)
      {
        return false;
      }
      
      public String getAny()
      {
        throw new NoSuchElementException("Can't fetch any language from the empty language set.");
      }
      
      public boolean isEmpty()
      {
        return true;
      }
      
      public boolean isSingleton()
      {
        return false;
      }
      
      public Languages.LanguageSet restrictTo(Languages.LanguageSet paramAnonymousLanguageSet)
      {
        return this;
      }
      
      public String toString()
      {
        return "NO_LANGUAGES";
      }
    };
  }
  
  private Languages(Set<String> paramSet)
  {
    this.languages = paramSet;
  }
  
  public static Languages getInstance(String paramString)
  {
    HashSet localHashSet = new HashSet();
    Object localObject = Languages.class.getClassLoader().getResourceAsStream(paramString);
    if (localObject == null) {
      throw new IllegalArgumentException("Unable to resolve required resource: " + paramString);
    }
    paramString = new Scanner((InputStream)localObject, "UTF-8");
    int i = 0;
    try
    {
      while (paramString.hasNextLine())
      {
        localObject = paramString.nextLine().trim();
        if (i != 0)
        {
          if (((String)localObject).endsWith("*/")) {
            i = 0;
          }
        }
        else if (((String)localObject).startsWith("/*")) {
          i = 1;
        } else if (((String)localObject).length() > 0) {
          localHashSet.add(localObject);
        }
      }
    }
    finally
    {
      paramString.close();
    }
    return new Languages(Collections.unmodifiableSet(localSet));
  }
  
  public static Languages getInstance(NameType paramNameType)
  {
    return (Languages)LANGUAGES.get(paramNameType);
  }
  
  private static String langResourceName(NameType paramNameType)
  {
    return String.format("org/apache/commons/codec/language/bm/%s_languages.txt", new Object[] { paramNameType.getName() });
  }
  
  public Set<String> getLanguages()
  {
    return this.languages;
  }
  
  public static abstract class LanguageSet
  {
    public static LanguageSet from(Set<String> paramSet)
    {
      if (paramSet.isEmpty()) {
        return Languages.NO_LANGUAGES;
      }
      return new Languages.SomeLanguages(paramSet, null);
    }
    
    public abstract boolean contains(String paramString);
    
    public abstract String getAny();
    
    public abstract boolean isEmpty();
    
    public abstract boolean isSingleton();
    
    public abstract LanguageSet restrictTo(LanguageSet paramLanguageSet);
  }
  
  public static final class SomeLanguages
    extends Languages.LanguageSet
  {
    private final Set<String> languages;
    
    private SomeLanguages(Set<String> paramSet)
    {
      this.languages = Collections.unmodifiableSet(paramSet);
    }
    
    public boolean contains(String paramString)
    {
      return this.languages.contains(paramString);
    }
    
    public String getAny()
    {
      return (String)this.languages.iterator().next();
    }
    
    public Set<String> getLanguages()
    {
      return this.languages;
    }
    
    public boolean isEmpty()
    {
      return this.languages.isEmpty();
    }
    
    public boolean isSingleton()
    {
      return this.languages.size() == 1;
    }
    
    public Languages.LanguageSet restrictTo(Languages.LanguageSet paramLanguageSet)
    {
      if (paramLanguageSet == Languages.NO_LANGUAGES) {
        return paramLanguageSet;
      }
      if (paramLanguageSet == Languages.ANY_LANGUAGE) {
        return this;
      }
      paramLanguageSet = (SomeLanguages)paramLanguageSet;
      HashSet localHashSet = new HashSet(Math.min(this.languages.size(), paramLanguageSet.languages.size()));
      Iterator localIterator = this.languages.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (paramLanguageSet.languages.contains(str)) {
          localHashSet.add(str);
        }
      }
      return from(localHashSet);
    }
    
    public String toString()
    {
      return "Languages(" + this.languages.toString() + ")";
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/language/bm/Languages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */