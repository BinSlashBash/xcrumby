package org.apache.commons.codec.language.bm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PhoneticEngine
{
  private static final int DEFAULT_MAX_PHONEMES = 20;
  private static final Map<NameType, Set<String>> NAME_PREFIXES = new EnumMap(NameType.class);
  private final boolean concat;
  private final Lang lang;
  private final int maxPhonemes;
  private final NameType nameType;
  private final RuleType ruleType;
  
  static
  {
    NAME_PREFIXES.put(NameType.ASHKENAZI, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { "bar", "ben", "da", "de", "van", "von" }))));
    NAME_PREFIXES.put(NameType.SEPHARDIC, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { "al", "el", "da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von" }))));
    NAME_PREFIXES.put(NameType.GENERIC, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[] { "da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von" }))));
  }
  
  public PhoneticEngine(NameType paramNameType, RuleType paramRuleType, boolean paramBoolean)
  {
    this(paramNameType, paramRuleType, paramBoolean, 20);
  }
  
  public PhoneticEngine(NameType paramNameType, RuleType paramRuleType, boolean paramBoolean, int paramInt)
  {
    if (paramRuleType == RuleType.RULES) {
      throw new IllegalArgumentException("ruleType must not be " + RuleType.RULES);
    }
    this.nameType = paramNameType;
    this.ruleType = paramRuleType;
    this.concat = paramBoolean;
    this.lang = Lang.instance(paramNameType);
    this.maxPhonemes = paramInt;
  }
  
  private PhonemeBuilder applyFinalRules(PhonemeBuilder paramPhonemeBuilder, Map<String, List<Rule>> paramMap)
  {
    if (paramMap == null) {
      throw new NullPointerException("finalRules can not be null");
    }
    if (paramMap.isEmpty()) {
      return paramPhonemeBuilder;
    }
    TreeSet localTreeSet = new TreeSet(Rule.Phoneme.COMPARATOR);
    Iterator localIterator = paramPhonemeBuilder.getPhonemes().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (Rule.Phoneme)localIterator.next();
      paramPhonemeBuilder = PhonemeBuilder.empty(((Rule.Phoneme)localObject).getLanguages());
      localObject = ((Rule.Phoneme)localObject).getPhonemeText().toString();
      RulesApplication localRulesApplication;
      for (int i = 0; i < ((String)localObject).length(); i = localRulesApplication.getI())
      {
        localRulesApplication = new RulesApplication(paramMap, (CharSequence)localObject, paramPhonemeBuilder, i, this.maxPhonemes).invoke();
        boolean bool = localRulesApplication.isFound();
        paramPhonemeBuilder = localRulesApplication.getPhonemeBuilder();
        if (!bool) {
          paramPhonemeBuilder.append(((String)localObject).subSequence(i, i + 1));
        }
      }
      localTreeSet.addAll(paramPhonemeBuilder.getPhonemes());
    }
    return new PhonemeBuilder(localTreeSet, null);
  }
  
  private static String join(Iterable<String> paramIterable, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    paramIterable = paramIterable.iterator();
    if (paramIterable.hasNext()) {
      localStringBuilder.append((String)paramIterable.next());
    }
    while (paramIterable.hasNext()) {
      localStringBuilder.append(paramString).append((String)paramIterable.next());
    }
    return localStringBuilder.toString();
  }
  
  public String encode(String paramString)
  {
    return encode(paramString, this.lang.guessLanguages(paramString));
  }
  
  public String encode(String paramString, Languages.LanguageSet paramLanguageSet)
  {
    Object localObject1 = Rule.getInstanceMap(this.nameType, RuleType.RULES, paramLanguageSet);
    Map localMap1 = Rule.getInstanceMap(this.nameType, this.ruleType, "common");
    Map localMap2 = Rule.getInstanceMap(this.nameType, this.ruleType, paramLanguageSet);
    Object localObject2 = paramString.toLowerCase(Locale.ENGLISH).replace('-', ' ').trim();
    Iterator localIterator;
    if (this.nameType == NameType.GENERIC)
    {
      if ((((String)localObject2).length() >= 2) && (((String)localObject2).substring(0, 2).equals("d'")))
      {
        paramString = ((String)localObject2).substring(2);
        paramLanguageSet = "d" + paramString;
        return "(" + encode(paramString) + ")-(" + encode(paramLanguageSet) + ")";
      }
      localIterator = ((Set)NAME_PREFIXES.get(this.nameType)).iterator();
      while (localIterator.hasNext())
      {
        paramString = (String)localIterator.next();
        if (((String)localObject2).startsWith(paramString + " "))
        {
          paramLanguageSet = ((String)localObject2).substring(paramString.length() + 1);
          paramString = paramString + paramLanguageSet;
          return "(" + encode(paramLanguageSet) + ")-(" + encode(paramString) + ")";
        }
      }
    }
    localObject2 = Arrays.asList(((String)localObject2).split("\\s+"));
    paramString = new ArrayList();
    switch (this.nameType)
    {
    default: 
      throw new IllegalStateException("Unreachable case: " + this.nameType);
    case ???: 
      localIterator = ((List)localObject2).iterator();
      while (localIterator.hasNext())
      {
        String[] arrayOfString = ((String)localIterator.next()).split("'");
        paramString.add(arrayOfString[(arrayOfString.length - 1)]);
      }
      paramString.removeAll((Collection)NAME_PREFIXES.get(this.nameType));
      if (!this.concat) {
        break;
      }
    }
    for (paramString = join(paramString, " ");; paramString = (String)((List)localObject2).iterator().next())
    {
      paramLanguageSet = PhonemeBuilder.empty(paramLanguageSet);
      int i = 0;
      while (i < paramString.length())
      {
        paramLanguageSet = new RulesApplication((Map)localObject1, paramString, paramLanguageSet, i, this.maxPhonemes).invoke();
        i = paramLanguageSet.getI();
        paramLanguageSet = paramLanguageSet.getPhonemeBuilder();
      }
      paramString.addAll((Collection)localObject2);
      paramString.removeAll((Collection)NAME_PREFIXES.get(this.nameType));
      break;
      paramString.addAll((Collection)localObject2);
      break;
      if (paramString.size() != 1) {
        break label622;
      }
    }
    label622:
    paramLanguageSet = new StringBuilder();
    paramString = paramString.iterator();
    while (paramString.hasNext())
    {
      localObject1 = (String)paramString.next();
      paramLanguageSet.append("-").append(encode((String)localObject1));
    }
    return paramLanguageSet.substring(1);
    return applyFinalRules(applyFinalRules(paramLanguageSet, localMap1), localMap2).makeString();
  }
  
  public Lang getLang()
  {
    return this.lang;
  }
  
  public int getMaxPhonemes()
  {
    return this.maxPhonemes;
  }
  
  public NameType getNameType()
  {
    return this.nameType;
  }
  
  public RuleType getRuleType()
  {
    return this.ruleType;
  }
  
  public boolean isConcat()
  {
    return this.concat;
  }
  
  static final class PhonemeBuilder
  {
    private final Set<Rule.Phoneme> phonemes;
    
    private PhonemeBuilder(Set<Rule.Phoneme> paramSet)
    {
      this.phonemes = paramSet;
    }
    
    private PhonemeBuilder(Rule.Phoneme paramPhoneme)
    {
      this.phonemes = new LinkedHashSet();
      this.phonemes.add(paramPhoneme);
    }
    
    public static PhonemeBuilder empty(Languages.LanguageSet paramLanguageSet)
    {
      return new PhonemeBuilder(new Rule.Phoneme("", paramLanguageSet));
    }
    
    public void append(CharSequence paramCharSequence)
    {
      Iterator localIterator = this.phonemes.iterator();
      while (localIterator.hasNext()) {
        ((Rule.Phoneme)localIterator.next()).append(paramCharSequence);
      }
    }
    
    public void apply(Rule.PhonemeExpr paramPhonemeExpr, int paramInt)
    {
      LinkedHashSet localLinkedHashSet = new LinkedHashSet(paramInt);
      Iterator localIterator1 = this.phonemes.iterator();
      label55:
      do
      {
        Rule.Phoneme localPhoneme2;
        do
        {
          Rule.Phoneme localPhoneme1;
          Languages.LanguageSet localLanguageSet;
          do
          {
            break label55;
            Iterator localIterator2;
            while (!localIterator2.hasNext())
            {
              if (!localIterator1.hasNext()) {
                break;
              }
              localPhoneme1 = (Rule.Phoneme)localIterator1.next();
              localIterator2 = paramPhonemeExpr.getPhonemes().iterator();
            }
            localPhoneme2 = (Rule.Phoneme)localIterator2.next();
            localLanguageSet = localPhoneme1.getLanguages().restrictTo(localPhoneme2.getLanguages());
          } while (localLanguageSet.isEmpty());
          localPhoneme2 = new Rule.Phoneme(localPhoneme1, localPhoneme2, localLanguageSet);
        } while (localLinkedHashSet.size() >= paramInt);
        localLinkedHashSet.add(localPhoneme2);
      } while (localLinkedHashSet.size() < paramInt);
      this.phonemes.clear();
      this.phonemes.addAll(localLinkedHashSet);
    }
    
    public Set<Rule.Phoneme> getPhonemes()
    {
      return this.phonemes;
    }
    
    public String makeString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      Iterator localIterator = this.phonemes.iterator();
      while (localIterator.hasNext())
      {
        Rule.Phoneme localPhoneme = (Rule.Phoneme)localIterator.next();
        if (localStringBuilder.length() > 0) {
          localStringBuilder.append("|");
        }
        localStringBuilder.append(localPhoneme.getPhonemeText());
      }
      return localStringBuilder.toString();
    }
  }
  
  private static final class RulesApplication
  {
    private final Map<String, List<Rule>> finalRules;
    private boolean found;
    private int i;
    private final CharSequence input;
    private final int maxPhonemes;
    private PhoneticEngine.PhonemeBuilder phonemeBuilder;
    
    public RulesApplication(Map<String, List<Rule>> paramMap, CharSequence paramCharSequence, PhoneticEngine.PhonemeBuilder paramPhonemeBuilder, int paramInt1, int paramInt2)
    {
      if (paramMap == null) {
        throw new NullPointerException("The finalRules argument must not be null");
      }
      this.finalRules = paramMap;
      this.phonemeBuilder = paramPhonemeBuilder;
      this.input = paramCharSequence;
      this.i = paramInt1;
      this.maxPhonemes = paramInt2;
    }
    
    public int getI()
    {
      return this.i;
    }
    
    public PhoneticEngine.PhonemeBuilder getPhonemeBuilder()
    {
      return this.phonemeBuilder;
    }
    
    public RulesApplication invoke()
    {
      this.found = false;
      int j = 1;
      int k = 1;
      Object localObject = (List)this.finalRules.get(this.input.subSequence(this.i, this.i + 1));
      if (localObject != null)
      {
        localObject = ((List)localObject).iterator();
        j = k;
        while (((Iterator)localObject).hasNext())
        {
          Rule localRule = (Rule)((Iterator)localObject).next();
          k = localRule.getPattern().length();
          j = k;
          if (localRule.patternAndContextMatches(this.input, this.i))
          {
            this.phonemeBuilder.apply(localRule.getPhoneme(), this.maxPhonemes);
            this.found = true;
            j = k;
          }
        }
      }
      if (!this.found) {
        j = 1;
      }
      this.i += j;
      return this;
    }
    
    public boolean isFound()
    {
      return this.found;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/language/bm/PhoneticEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */