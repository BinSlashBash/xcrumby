/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language.bm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.codec.language.bm.Lang;
import org.apache.commons.codec.language.bm.Languages;
import org.apache.commons.codec.language.bm.NameType;
import org.apache.commons.codec.language.bm.Rule;
import org.apache.commons.codec.language.bm.RuleType;

public class PhoneticEngine {
    private static final int DEFAULT_MAX_PHONEMES = 20;
    private static final Map<NameType, Set<String>> NAME_PREFIXES = new EnumMap<NameType, Set<String>>(NameType.class);
    private final boolean concat;
    private final Lang lang;
    private final int maxPhonemes;
    private final NameType nameType;
    private final RuleType ruleType;

    static {
        NAME_PREFIXES.put(NameType.ASHKENAZI, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("bar", "ben", "da", "de", "van", "von"))));
        NAME_PREFIXES.put(NameType.SEPHARDIC, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("al", "el", "da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von"))));
        NAME_PREFIXES.put(NameType.GENERIC, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von"))));
    }

    public PhoneticEngine(NameType nameType, RuleType ruleType, boolean bl2) {
        this(nameType, ruleType, bl2, 20);
    }

    public PhoneticEngine(NameType nameType, RuleType ruleType, boolean bl2, int n2) {
        if (ruleType == RuleType.RULES) {
            throw new IllegalArgumentException("ruleType must not be " + (Object)((Object)RuleType.RULES));
        }
        this.nameType = nameType;
        this.ruleType = ruleType;
        this.concat = bl2;
        this.lang = Lang.instance(nameType);
        this.maxPhonemes = n2;
    }

    private PhonemeBuilder applyFinalRules(PhonemeBuilder phonemeBuilder, Map<String, List<Rule>> map) {
        if (map == null) {
            throw new NullPointerException("finalRules can not be null");
        }
        if (map.isEmpty()) {
            return phonemeBuilder;
        }
        TreeSet<Rule.Phoneme> treeSet = new TreeSet<Rule.Phoneme>(Rule.Phoneme.COMPARATOR);
        Iterator<Rule.Phoneme> iterator = phonemeBuilder.getPhonemes().iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            phonemeBuilder = PhonemeBuilder.empty(object.getLanguages());
            object = object.getPhonemeText().toString();
            int n2 = 0;
            while (n2 < object.length()) {
                RulesApplication rulesApplication = new RulesApplication(map, (CharSequence)object, phonemeBuilder, n2, this.maxPhonemes).invoke();
                boolean bl2 = rulesApplication.isFound();
                phonemeBuilder = rulesApplication.getPhonemeBuilder();
                if (!bl2) {
                    phonemeBuilder.append(object.subSequence(n2, n2 + 1));
                }
                n2 = rulesApplication.getI();
            }
            treeSet.addAll(phonemeBuilder.getPhonemes());
        }
        return new PhonemeBuilder(treeSet);
    }

    private static String join(Iterable<String> object, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        if ((object = object.iterator()).hasNext()) {
            stringBuilder.append((String)object.next());
        }
        while (object.hasNext()) {
            stringBuilder.append(string2).append((String)object.next());
        }
        return stringBuilder.toString();
    }

    public String encode(String string2) {
        return this.encode(string2, this.lang.guessLanguages(string2));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public String encode(String var1_1, Languages.LanguageSet var2_2) {
        var4_3 = Rule.getInstanceMap(this.nameType, RuleType.RULES, (Languages.LanguageSet)var2_2);
        var5_4 = Rule.getInstanceMap(this.nameType, this.ruleType, "common");
        var6_5 = Rule.getInstanceMap(this.nameType, this.ruleType, (Languages.LanguageSet)var2_2);
        var7_6 = var1_1.toLowerCase(Locale.ENGLISH).replace('-', ' ').trim();
        if (this.nameType == NameType.GENERIC) {
            if (var7_6.length() >= 2 && var7_6.substring(0, 2).equals("d'")) {
                var1_1 = var7_6.substring(2);
                var2_2 = "d" + (String)var1_1;
                return "(" + this.encode((String)var1_1) + ")-(" + this.encode((String)var2_2) + ")";
            }
            var8_7 = PhoneticEngine.NAME_PREFIXES.get((Object)this.nameType).iterator();
            while (var8_7.hasNext()) {
                var1_1 = var8_7.next();
                if (!var7_6.startsWith((String)var1_1 + " ")) continue;
                var2_2 = var7_6.substring(var1_1.length() + 1);
                var1_1 = var1_1 + (String)var2_2;
                return "(" + this.encode((String)var2_2) + ")-(" + this.encode((String)var1_1) + ")";
            }
        }
        var7_6 = Arrays.asList(var7_6.split("\\s+"));
        var1_1 = new ArrayList<E>();
        switch (.$SwitchMap$org$apache$commons$codec$language$bm$NameType[this.nameType.ordinal()]) {
            default: {
                throw new IllegalStateException("Unreachable case: " + (Object)this.nameType);
            }
            case 1: {
                var8_7 = var7_6.iterator();
                while (var8_7.hasNext()) {
                    var9_8 = ((String)var8_7.next()).split("'");
                    var1_1.add((String)var9_8[var9_8.length - 1]);
                }
                var1_1.removeAll((Collection)PhoneticEngine.NAME_PREFIXES.get((Object)this.nameType));
                break;
            }
            case 2: {
                var1_1.addAll(var7_6);
                var1_1.removeAll((Collection)PhoneticEngine.NAME_PREFIXES.get((Object)this.nameType));
                break;
            }
            case 3: {
                var1_1.addAll(var7_6);
            }
        }
        if (!this.concat) ** GOTO lbl39
        var1_1 = PhoneticEngine.join(var1_1, " ");
        ** GOTO lbl41
lbl39: // 1 sources:
        if (var1_1.size() == 1) {
            var1_1 = (String)var7_6.iterator().next();
lbl41: // 2 sources:
            var2_2 = PhonemeBuilder.empty((Languages.LanguageSet)var2_2);
            var3_9 = 0;
            while (var3_9 < var1_1.length()) {
                var2_2 = new RulesApplication((Map<String, List<Rule>>)var4_3, (CharSequence)var1_1, (PhonemeBuilder)var2_2, var3_9, this.maxPhonemes).invoke();
                var3_9 = var2_2.getI();
                var2_2 = var2_2.getPhonemeBuilder();
            }
            return this.applyFinalRules(this.applyFinalRules((PhonemeBuilder)var2_2, var5_4), var6_5).makeString();
        }
        var2_2 = new StringBuilder();
        var1_1 = var1_1.iterator();
        while (var1_1.hasNext() != false) {
            var4_3 = (String)var1_1.next();
            var2_2.append("-").append(this.encode((String)var4_3));
        }
        return var2_2.substring(1);
    }

    public Lang getLang() {
        return this.lang;
    }

    public int getMaxPhonemes() {
        return this.maxPhonemes;
    }

    public NameType getNameType() {
        return this.nameType;
    }

    public RuleType getRuleType() {
        return this.ruleType;
    }

    public boolean isConcat() {
        return this.concat;
    }

    static final class PhonemeBuilder {
        private final Set<Rule.Phoneme> phonemes;

        private PhonemeBuilder(Set<Rule.Phoneme> set) {
            this.phonemes = set;
        }

        private PhonemeBuilder(Rule.Phoneme phoneme) {
            this.phonemes = new LinkedHashSet<Rule.Phoneme>();
            this.phonemes.add(phoneme);
        }

        public static PhonemeBuilder empty(Languages.LanguageSet languageSet) {
            return new PhonemeBuilder(new Rule.Phoneme("", languageSet));
        }

        public void append(CharSequence charSequence) {
            Iterator<Rule.Phoneme> iterator = this.phonemes.iterator();
            while (iterator.hasNext()) {
                iterator.next().append(charSequence);
            }
        }

        public void apply(Rule.PhonemeExpr phonemeExpr, int n2) {
            LinkedHashSet<Rule.Phoneme> linkedHashSet = new LinkedHashSet<Rule.Phoneme>(n2);
            for (Rule.Phoneme phoneme : this.phonemes) {
                Iterator<Rule.Phoneme> iterator = phonemeExpr.getPhonemes().iterator();
                while (iterator.hasNext()) {
                    Rule.Phoneme phoneme2 = iterator.next();
                    Languages.LanguageSet languageSet = phoneme.getLanguages().restrictTo(phoneme2.getLanguages());
                    if (languageSet.isEmpty()) continue;
                    phoneme2 = new Rule.Phoneme(phoneme, phoneme2, languageSet);
                    if (linkedHashSet.size() >= n2) continue;
                    linkedHashSet.add(phoneme2);
                    if (linkedHashSet.size() < n2) continue;
                }
            }
            this.phonemes.clear();
            this.phonemes.addAll(linkedHashSet);
        }

        public Set<Rule.Phoneme> getPhonemes() {
            return this.phonemes;
        }

        public String makeString() {
            StringBuilder stringBuilder = new StringBuilder();
            for (Rule.Phoneme phoneme : this.phonemes) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append("|");
                }
                stringBuilder.append(phoneme.getPhonemeText());
            }
            return stringBuilder.toString();
        }
    }

    private static final class RulesApplication {
        private final Map<String, List<Rule>> finalRules;
        private boolean found;
        private int i;
        private final CharSequence input;
        private final int maxPhonemes;
        private PhonemeBuilder phonemeBuilder;

        public RulesApplication(Map<String, List<Rule>> map, CharSequence charSequence, PhonemeBuilder phonemeBuilder, int n2, int n3) {
            if (map == null) {
                throw new NullPointerException("The finalRules argument must not be null");
            }
            this.finalRules = map;
            this.phonemeBuilder = phonemeBuilder;
            this.input = charSequence;
            this.i = n2;
            this.maxPhonemes = n3;
        }

        public int getI() {
            return this.i;
        }

        public PhonemeBuilder getPhonemeBuilder() {
            return this.phonemeBuilder;
        }

        public RulesApplication invoke() {
            this.found = false;
            int n2 = 1;
            int n3 = 1;
            List<Rule> list = this.finalRules.get(this.input.subSequence(this.i, this.i + 1));
            if (list != null) {
                list = list.iterator();
                n2 = n3;
                while (list.hasNext()) {
                    Rule rule = (Rule)list.next();
                    n2 = n3 = rule.getPattern().length();
                    if (!rule.patternAndContextMatches(this.input, this.i)) continue;
                    this.phonemeBuilder.apply(rule.getPhoneme(), this.maxPhonemes);
                    this.found = true;
                    n2 = n3;
                    break;
                }
            }
            if (!this.found) {
                n2 = 1;
            }
            this.i += n2;
            return this;
        }

        public boolean isFound() {
            return this.found;
        }
    }

}

