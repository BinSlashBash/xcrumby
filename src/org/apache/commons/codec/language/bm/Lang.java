package org.apache.commons.codec.language.bm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lang
{
  private static final String LANGUAGE_RULES_RN = "org/apache/commons/codec/language/bm/lang.txt";
  private static final Map<NameType, Lang> Langs = new EnumMap(NameType.class);
  private final Languages languages;
  private final List<LangRule> rules;
  
  static
  {
    NameType[] arrayOfNameType = NameType.values();
    int j = arrayOfNameType.length;
    int i = 0;
    while (i < j)
    {
      NameType localNameType = arrayOfNameType[i];
      Langs.put(localNameType, loadFromResource("org/apache/commons/codec/language/bm/lang.txt", Languages.getInstance(localNameType)));
      i += 1;
    }
  }
  
  private Lang(List<LangRule> paramList, Languages paramLanguages)
  {
    this.rules = Collections.unmodifiableList(paramList);
    this.languages = paramLanguages;
  }
  
  public static Lang instance(NameType paramNameType)
  {
    return (Lang)Langs.get(paramNameType);
  }
  
  public static Lang loadFromResource(String paramString, Languages paramLanguages)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject1 = Lang.class.getClassLoader().getResourceAsStream(paramString);
    if (localObject1 == null) {
      throw new IllegalStateException("Unable to resolve required resource:org/apache/commons/codec/language/bm/lang.txt");
    }
    Scanner localScanner = new Scanner((InputStream)localObject1, "UTF-8");
    int i = 0;
    for (;;)
    {
      try
      {
        if (!localScanner.hasNextLine()) {
          break;
        }
        localObject3 = localScanner.nextLine();
        localObject1 = localObject3;
        if (i != 0)
        {
          if (!((String)localObject1).endsWith("*/")) {
            continue;
          }
          i = 0;
          continue;
        }
        if (((String)localObject1).startsWith("/*"))
        {
          i = 1;
          continue;
        }
        int j = ((String)localObject1).indexOf("//");
        localObject2 = localObject1;
        if (j >= 0) {
          localObject2 = ((String)localObject1).substring(0, j);
        }
        localObject1 = ((String)localObject2).trim();
        if (((String)localObject1).length() == 0) {
          continue;
        }
        localObject1 = ((String)localObject1).split("\\s+");
        if (localObject1.length != 3) {
          throw new IllegalArgumentException("Malformed line '" + (String)localObject3 + "' in language resource '" + paramString + "'");
        }
      }
      finally
      {
        localScanner.close();
      }
      Object localObject2 = Pattern.compile(localObject1[0]);
      Object localObject3 = localObject1[1].split("\\+");
      boolean bool = localObject1[2].equals("true");
      localArrayList.add(new LangRule((Pattern)localObject2, new HashSet(Arrays.asList((Object[])localObject3)), bool, null));
    }
    localScanner.close();
    return new Lang(localArrayList, paramLanguages);
  }
  
  public String guessLanguage(String paramString)
  {
    paramString = guessLanguages(paramString);
    if (paramString.isSingleton()) {
      return paramString.getAny();
    }
    return "any";
  }
  
  public Languages.LanguageSet guessLanguages(String paramString)
  {
    paramString = paramString.toLowerCase(Locale.ENGLISH);
    Object localObject = new HashSet(this.languages.getLanguages());
    Iterator localIterator = this.rules.iterator();
    while (localIterator.hasNext())
    {
      LangRule localLangRule = (LangRule)localIterator.next();
      if (localLangRule.matches(paramString)) {
        if (localLangRule.acceptOnMatch) {
          ((Set)localObject).retainAll(localLangRule.languages);
        } else {
          ((Set)localObject).removeAll(localLangRule.languages);
        }
      }
    }
    localObject = Languages.LanguageSet.from((Set)localObject);
    paramString = (String)localObject;
    if (localObject.equals(Languages.NO_LANGUAGES)) {
      paramString = Languages.ANY_LANGUAGE;
    }
    return paramString;
  }
  
  private static final class LangRule
  {
    private final boolean acceptOnMatch;
    private final Set<String> languages;
    private final Pattern pattern;
    
    private LangRule(Pattern paramPattern, Set<String> paramSet, boolean paramBoolean)
    {
      this.pattern = paramPattern;
      this.languages = paramSet;
      this.acceptOnMatch = paramBoolean;
    }
    
    public boolean matches(String paramString)
    {
      return this.pattern.matcher(paramString).find();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/language/bm/Lang.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */