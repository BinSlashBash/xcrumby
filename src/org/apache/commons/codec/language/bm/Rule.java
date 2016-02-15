package org.apache.commons.codec.language.bm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule
{
  public static final String ALL = "ALL";
  public static final RPattern ALL_STRINGS_RMATCHER = new RPattern()
  {
    public boolean isMatch(CharSequence paramAnonymousCharSequence)
    {
      return true;
    }
  };
  private static final String DOUBLE_QUOTE = "\"";
  private static final String HASH_INCLUDE = "#include";
  private static final Map<NameType, Map<RuleType, Map<String, Map<String, List<Rule>>>>> RULES = new EnumMap(NameType.class);
  private final RPattern lContext;
  private final String pattern;
  private final PhonemeExpr phoneme;
  private final RPattern rContext;
  
  static
  {
    NameType[] arrayOfNameType = NameType.values();
    int k = arrayOfNameType.length;
    int i = 0;
    while (i < k)
    {
      NameType localNameType = arrayOfNameType[i];
      EnumMap localEnumMap = new EnumMap(RuleType.class);
      RuleType[] arrayOfRuleType = RuleType.values();
      int m = arrayOfRuleType.length;
      int j = 0;
      while (j < m)
      {
        RuleType localRuleType = arrayOfRuleType[j];
        HashMap localHashMap = new HashMap();
        Iterator localIterator = Languages.getInstance(localNameType).getLanguages().iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          try
          {
            localHashMap.put(str, parseRules(createScanner(localNameType, localRuleType, str), createResourceName(localNameType, localRuleType, str)));
          }
          catch (IllegalStateException localIllegalStateException)
          {
            throw new IllegalStateException("Problem processing " + createResourceName(localNameType, localRuleType, str), localIllegalStateException);
          }
        }
        if (!localRuleType.equals(RuleType.RULES)) {
          localHashMap.put("common", parseRules(createScanner(localNameType, localRuleType, "common"), createResourceName(localNameType, localRuleType, "common")));
        }
        localEnumMap.put(localRuleType, Collections.unmodifiableMap(localHashMap));
        j += 1;
      }
      RULES.put(localNameType, Collections.unmodifiableMap(localEnumMap));
      i += 1;
    }
  }
  
  public Rule(String paramString1, String paramString2, String paramString3, PhonemeExpr paramPhonemeExpr)
  {
    this.pattern = paramString1;
    this.lContext = pattern(paramString2 + "$");
    this.rContext = pattern("^" + paramString3);
    this.phoneme = paramPhonemeExpr;
  }
  
  private static boolean contains(CharSequence paramCharSequence, char paramChar)
  {
    int i = 0;
    while (i < paramCharSequence.length())
    {
      if (paramCharSequence.charAt(i) == paramChar) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  private static String createResourceName(NameType paramNameType, RuleType paramRuleType, String paramString)
  {
    return String.format("org/apache/commons/codec/language/bm/%s_%s_%s.txt", new Object[] { paramNameType.getName(), paramRuleType.getName(), paramString });
  }
  
  private static Scanner createScanner(String paramString)
  {
    paramString = String.format("org/apache/commons/codec/language/bm/%s.txt", new Object[] { paramString });
    InputStream localInputStream = Languages.class.getClassLoader().getResourceAsStream(paramString);
    if (localInputStream == null) {
      throw new IllegalArgumentException("Unable to load resource: " + paramString);
    }
    return new Scanner(localInputStream, "UTF-8");
  }
  
  private static Scanner createScanner(NameType paramNameType, RuleType paramRuleType, String paramString)
  {
    paramNameType = createResourceName(paramNameType, paramRuleType, paramString);
    paramRuleType = Languages.class.getClassLoader().getResourceAsStream(paramNameType);
    if (paramRuleType == null) {
      throw new IllegalArgumentException("Unable to load resource: " + paramNameType);
    }
    return new Scanner(paramRuleType, "UTF-8");
  }
  
  private static boolean endsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    if (paramCharSequence2.length() > paramCharSequence1.length()) {
      return false;
    }
    int j = paramCharSequence1.length() - 1;
    int i = paramCharSequence2.length() - 1;
    for (;;)
    {
      if (i < 0) {
        break label67;
      }
      if (paramCharSequence1.charAt(j) != paramCharSequence2.charAt(i)) {
        break;
      }
      j -= 1;
      i -= 1;
    }
    label67:
    return true;
  }
  
  public static List<Rule> getInstance(NameType paramNameType, RuleType paramRuleType, String paramString)
  {
    return getInstance(paramNameType, paramRuleType, Languages.LanguageSet.from(new HashSet(Arrays.asList(new String[] { paramString }))));
  }
  
  public static List<Rule> getInstance(NameType paramNameType, RuleType paramRuleType, Languages.LanguageSet paramLanguageSet)
  {
    paramRuleType = getInstanceMap(paramNameType, paramRuleType, paramLanguageSet);
    paramNameType = new ArrayList();
    paramRuleType = paramRuleType.values().iterator();
    while (paramRuleType.hasNext()) {
      paramNameType.addAll((List)paramRuleType.next());
    }
    return paramNameType;
  }
  
  public static Map<String, List<Rule>> getInstanceMap(NameType paramNameType, RuleType paramRuleType, String paramString)
  {
    Map localMap = (Map)((Map)((Map)RULES.get(paramNameType)).get(paramRuleType)).get(paramString);
    if (localMap == null) {
      throw new IllegalArgumentException(String.format("No rules found for %s, %s, %s.", new Object[] { paramNameType.getName(), paramRuleType.getName(), paramString }));
    }
    return localMap;
  }
  
  public static Map<String, List<Rule>> getInstanceMap(NameType paramNameType, RuleType paramRuleType, Languages.LanguageSet paramLanguageSet)
  {
    if (paramLanguageSet.isSingleton()) {
      return getInstanceMap(paramNameType, paramRuleType, paramLanguageSet.getAny());
    }
    return getInstanceMap(paramNameType, paramRuleType, "any");
  }
  
  private static Phoneme parsePhoneme(String paramString)
  {
    int i = paramString.indexOf("[");
    if (i >= 0)
    {
      if (!paramString.endsWith("]")) {
        throw new IllegalArgumentException("Phoneme expression contains a '[' but does not end in ']'");
      }
      return new Phoneme(paramString.substring(0, i), Languages.LanguageSet.from(new HashSet(Arrays.asList(paramString.substring(i + 1, paramString.length() - 1).split("[+]")))));
    }
    return new Phoneme(paramString, Languages.ANY_LANGUAGE);
  }
  
  private static PhonemeExpr parsePhonemeExpr(String paramString)
  {
    if (paramString.startsWith("("))
    {
      if (!paramString.endsWith(")")) {
        throw new IllegalArgumentException("Phoneme starts with '(' so must end with ')'");
      }
      ArrayList localArrayList = new ArrayList();
      paramString = paramString.substring(1, paramString.length() - 1);
      String[] arrayOfString = paramString.split("[|]");
      int j = arrayOfString.length;
      int i = 0;
      while (i < j)
      {
        localArrayList.add(parsePhoneme(arrayOfString[i]));
        i += 1;
      }
      if ((paramString.startsWith("|")) || (paramString.endsWith("|"))) {
        localArrayList.add(new Phoneme("", Languages.ANY_LANGUAGE));
      }
      return new PhonemeList(localArrayList);
    }
    return parsePhoneme(paramString);
  }
  
  private static Map<String, List<Rule>> parseRules(Scanner paramScanner, final String paramString)
  {
    HashMap localHashMap = new HashMap();
    int j = 0;
    int i = 0;
    while (paramScanner.hasNextLine())
    {
      final int k = j + 1;
      Object localObject3 = paramScanner.nextLine();
      Object localObject1 = localObject3;
      if (i != 0)
      {
        j = k;
        if (((String)localObject1).endsWith("*/"))
        {
          i = 0;
          j = k;
        }
      }
      else if (((String)localObject1).startsWith("/*"))
      {
        i = 1;
        j = k;
      }
      else
      {
        j = ((String)localObject1).indexOf("//");
        Object localObject2 = localObject1;
        if (j >= 0) {
          localObject2 = ((String)localObject1).substring(0, j);
        }
        localObject1 = ((String)localObject2).trim();
        j = k;
        if (((String)localObject1).length() != 0) {
          if (((String)localObject1).startsWith("#include"))
          {
            localObject1 = ((String)localObject1).substring("#include".length()).trim();
            if (((String)localObject1).contains(" ")) {
              throw new IllegalArgumentException("Malformed import statement '" + (String)localObject3 + "' in " + paramString);
            }
            localHashMap.putAll(parseRules(createScanner((String)localObject1), paramString + "->" + (String)localObject1));
            j = k;
          }
          else
          {
            localObject1 = ((String)localObject1).split("\\s+");
            if (localObject1.length != 4) {
              throw new IllegalArgumentException("Malformed rule statement split into " + localObject1.length + " parts: " + (String)localObject3 + " in " + paramString);
            }
            try
            {
              localObject3 = new Rule(stripQuotes(localObject1[0]), stripQuotes(localObject1[1]), stripQuotes(localObject1[2]), parsePhonemeExpr(stripQuotes(localObject1[3])))
              {
                private final String loc = paramString;
                private final int myLine = k;
                
                public String toString()
                {
                  StringBuilder localStringBuilder = new StringBuilder();
                  localStringBuilder.append("Rule");
                  localStringBuilder.append("{line=").append(this.myLine);
                  localStringBuilder.append(", loc='").append(this.loc).append('\'');
                  localStringBuilder.append('}');
                  return localStringBuilder.toString();
                }
              };
              String str = ((Rule)localObject3).pattern.substring(0, 1);
              localObject2 = (List)localHashMap.get(str);
              localObject1 = localObject2;
              if (localObject2 == null)
              {
                localObject1 = new ArrayList();
                localHashMap.put(str, localObject1);
              }
              ((List)localObject1).add(localObject3);
              j = k;
            }
            catch (IllegalArgumentException paramScanner)
            {
              throw new IllegalStateException("Problem parsing line '" + k + "' in " + paramString, paramScanner);
            }
          }
        }
      }
    }
    return localHashMap;
  }
  
  private static RPattern pattern(String paramString)
  {
    final boolean bool1 = true;
    boolean bool2 = paramString.startsWith("^");
    boolean bool3 = paramString.endsWith("$");
    int i;
    if (bool2)
    {
      i = 1;
      if (!bool3) {
        break label87;
      }
    }
    Object localObject;
    label87:
    for (int j = paramString.length() - 1;; j = paramString.length())
    {
      localObject = paramString.substring(i, j);
      if (((String)localObject).contains("[")) {
        break label157;
      }
      if ((!bool2) || (!bool3)) {
        break label105;
      }
      if (((String)localObject).length() != 0) {
        break label95;
      }
      new RPattern()
      {
        public boolean isMatch(CharSequence paramAnonymousCharSequence)
        {
          return paramAnonymousCharSequence.length() == 0;
        }
      };
      i = 0;
      break;
    }
    label95:
    new RPattern()
    {
      public boolean isMatch(CharSequence paramAnonymousCharSequence)
      {
        return paramAnonymousCharSequence.equals(this.val$content);
      }
    };
    label105:
    if (((bool2) || (bool3)) && (((String)localObject).length() == 0)) {
      return ALL_STRINGS_RMATCHER;
    }
    if (bool2) {
      new RPattern()
      {
        public boolean isMatch(CharSequence paramAnonymousCharSequence)
        {
          return Rule.startsWith(paramAnonymousCharSequence, this.val$content);
        }
      };
    }
    if (bool3)
    {
      new RPattern()
      {
        public boolean isMatch(CharSequence paramAnonymousCharSequence)
        {
          return Rule.endsWith(paramAnonymousCharSequence, this.val$content);
        }
      };
      label157:
      boolean bool4 = ((String)localObject).startsWith("[");
      boolean bool5 = ((String)localObject).endsWith("]");
      if ((bool4) && (bool5))
      {
        String str = ((String)localObject).substring(1, ((String)localObject).length() - 1);
        if (!str.contains("["))
        {
          bool4 = str.startsWith("^");
          localObject = str;
          if (bool4) {
            localObject = str.substring(1);
          }
          if (!bool4) {}
          while ((bool2) && (bool3))
          {
            new RPattern()
            {
              public boolean isMatch(CharSequence paramAnonymousCharSequence)
              {
                return (paramAnonymousCharSequence.length() == 1) && (Rule.contains(this.val$bContent, paramAnonymousCharSequence.charAt(0)) == bool1);
              }
            };
            bool1 = false;
          }
          if (bool2) {
            new RPattern()
            {
              public boolean isMatch(CharSequence paramAnonymousCharSequence)
              {
                boolean bool2 = false;
                boolean bool1 = bool2;
                if (paramAnonymousCharSequence.length() > 0)
                {
                  bool1 = bool2;
                  if (Rule.contains(this.val$bContent, paramAnonymousCharSequence.charAt(0)) == bool1) {
                    bool1 = true;
                  }
                }
                return bool1;
              }
            };
          }
          if (bool3) {
            new RPattern()
            {
              public boolean isMatch(CharSequence paramAnonymousCharSequence)
              {
                return (paramAnonymousCharSequence.length() > 0) && (Rule.contains(this.val$bContent, paramAnonymousCharSequence.charAt(paramAnonymousCharSequence.length() - 1)) == bool1);
              }
            };
          }
        }
      }
    }
    new RPattern()
    {
      Pattern pattern = Pattern.compile(this.val$regex);
      
      public boolean isMatch(CharSequence paramAnonymousCharSequence)
      {
        return this.pattern.matcher(paramAnonymousCharSequence).find();
      }
    };
  }
  
  private static boolean startsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    if (paramCharSequence2.length() > paramCharSequence1.length()) {
      return false;
    }
    int i = 0;
    for (;;)
    {
      if (i >= paramCharSequence2.length()) {
        break label53;
      }
      if (paramCharSequence1.charAt(i) != paramCharSequence2.charAt(i)) {
        break;
      }
      i += 1;
    }
    label53:
    return true;
  }
  
  private static String stripQuotes(String paramString)
  {
    String str = paramString;
    if (paramString.startsWith("\"")) {
      str = paramString.substring(1);
    }
    paramString = str;
    if (str.endsWith("\"")) {
      paramString = str.substring(0, str.length() - 1);
    }
    return paramString;
  }
  
  public RPattern getLContext()
  {
    return this.lContext;
  }
  
  public String getPattern()
  {
    return this.pattern;
  }
  
  public PhonemeExpr getPhoneme()
  {
    return this.phoneme;
  }
  
  public RPattern getRContext()
  {
    return this.rContext;
  }
  
  public boolean patternAndContextMatches(CharSequence paramCharSequence, int paramInt)
  {
    if (paramInt < 0) {
      throw new IndexOutOfBoundsException("Can not match pattern at negative indexes");
    }
    int i = paramInt + this.pattern.length();
    if (i > paramCharSequence.length()) {}
    while ((!paramCharSequence.subSequence(paramInt, i).equals(this.pattern)) || (!this.rContext.isMatch(paramCharSequence.subSequence(i, paramCharSequence.length())))) {
      return false;
    }
    return this.lContext.isMatch(paramCharSequence.subSequence(0, paramInt));
  }
  
  public static final class Phoneme
    implements Rule.PhonemeExpr
  {
    public static final Comparator<Phoneme> COMPARATOR = new Comparator()
    {
      public int compare(Rule.Phoneme paramAnonymousPhoneme1, Rule.Phoneme paramAnonymousPhoneme2)
      {
        int i = 0;
        while (i < paramAnonymousPhoneme1.phonemeText.length())
        {
          int j;
          if (i >= paramAnonymousPhoneme2.phonemeText.length()) {
            j = 1;
          }
          int k;
          do
          {
            return j;
            k = paramAnonymousPhoneme1.phonemeText.charAt(i) - paramAnonymousPhoneme2.phonemeText.charAt(i);
            j = k;
          } while (k != 0);
          i += 1;
        }
        if (paramAnonymousPhoneme1.phonemeText.length() < paramAnonymousPhoneme2.phonemeText.length()) {
          return -1;
        }
        return 0;
      }
    };
    private final Languages.LanguageSet languages;
    private final StringBuilder phonemeText;
    
    public Phoneme(CharSequence paramCharSequence, Languages.LanguageSet paramLanguageSet)
    {
      this.phonemeText = new StringBuilder(paramCharSequence);
      this.languages = paramLanguageSet;
    }
    
    public Phoneme(Phoneme paramPhoneme1, Phoneme paramPhoneme2)
    {
      this(paramPhoneme1.phonemeText, paramPhoneme1.languages);
      this.phonemeText.append(paramPhoneme2.phonemeText);
    }
    
    public Phoneme(Phoneme paramPhoneme1, Phoneme paramPhoneme2, Languages.LanguageSet paramLanguageSet)
    {
      this(paramPhoneme1.phonemeText, paramLanguageSet);
      this.phonemeText.append(paramPhoneme2.phonemeText);
    }
    
    public Phoneme append(CharSequence paramCharSequence)
    {
      this.phonemeText.append(paramCharSequence);
      return this;
    }
    
    public Languages.LanguageSet getLanguages()
    {
      return this.languages;
    }
    
    public CharSequence getPhonemeText()
    {
      return this.phonemeText;
    }
    
    public Iterable<Phoneme> getPhonemes()
    {
      return Collections.singleton(this);
    }
    
    @Deprecated
    public Phoneme join(Phoneme paramPhoneme)
    {
      return new Phoneme(this.phonemeText.toString() + paramPhoneme.phonemeText.toString(), this.languages.restrictTo(paramPhoneme.languages));
    }
  }
  
  public static abstract interface PhonemeExpr
  {
    public abstract Iterable<Rule.Phoneme> getPhonemes();
  }
  
  public static final class PhonemeList
    implements Rule.PhonemeExpr
  {
    private final List<Rule.Phoneme> phonemes;
    
    public PhonemeList(List<Rule.Phoneme> paramList)
    {
      this.phonemes = paramList;
    }
    
    public List<Rule.Phoneme> getPhonemes()
    {
      return this.phonemes;
    }
  }
  
  public static abstract interface RPattern
  {
    public abstract boolean isMatch(CharSequence paramCharSequence);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/language/bm/Rule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */