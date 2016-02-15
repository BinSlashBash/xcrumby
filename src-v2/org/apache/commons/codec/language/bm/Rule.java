/*
 * Decompiled with CFR 0_110.
 */
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
import org.apache.commons.codec.language.bm.Languages;
import org.apache.commons.codec.language.bm.NameType;
import org.apache.commons.codec.language.bm.RuleType;

public class Rule {
    public static final String ALL = "ALL";
    public static final RPattern ALL_STRINGS_RMATCHER = new RPattern(){

        @Override
        public boolean isMatch(CharSequence charSequence) {
            return true;
        }
    };
    private static final String DOUBLE_QUOTE = "\"";
    private static final String HASH_INCLUDE = "#include";
    private static final Map<NameType, Map<RuleType, Map<String, Map<String, List<Rule>>>>> RULES = new EnumMap<NameType, Map<RuleType, Map<String, Map<String, List<Rule>>>>>(NameType.class);
    private final RPattern lContext;
    private final String pattern;
    private final PhonemeExpr phoneme;
    private final RPattern rContext;

    static {
        for (NameType nameType : NameType.values()) {
            EnumMap enumMap = new EnumMap(RuleType.class);
            for (RuleType ruleType : RuleType.values()) {
                HashMap<String, Map<String, List<Rule>>> hashMap = new HashMap<String, Map<String, List<Rule>>>();
                for (String string2 : Languages.getInstance(nameType).getLanguages()) {
                    try {
                        hashMap.put(string2, Rule.parseRules(Rule.createScanner(nameType, ruleType, string2), Rule.createResourceName(nameType, ruleType, string2)));
                        continue;
                    }
                    catch (IllegalStateException var7_1) {
                        throw new IllegalStateException("Problem processing " + Rule.createResourceName(nameType, ruleType, string2), var7_1);
                    }
                }
                if (!ruleType.equals((Object)RuleType.RULES)) {
                    hashMap.put("common", Rule.parseRules(Rule.createScanner(nameType, ruleType, "common"), Rule.createResourceName(nameType, ruleType, "common")));
                }
                enumMap.put(ruleType, Collections.unmodifiableMap(hashMap));
            }
            RULES.put(nameType, Collections.unmodifiableMap(enumMap));
        }
    }

    public Rule(String string2, String string3, String string4, PhonemeExpr phonemeExpr) {
        this.pattern = string2;
        this.lContext = Rule.pattern(string3 + "$");
        this.rContext = Rule.pattern("^" + string4);
        this.phoneme = phonemeExpr;
    }

    private static boolean contains(CharSequence charSequence, char c2) {
        for (int i2 = 0; i2 < charSequence.length(); ++i2) {
            if (charSequence.charAt(i2) != c2) continue;
            return true;
        }
        return false;
    }

    private static String createResourceName(NameType nameType, RuleType ruleType, String string2) {
        return String.format("org/apache/commons/codec/language/bm/%s_%s_%s.txt", nameType.getName(), ruleType.getName(), string2);
    }

    private static Scanner createScanner(String string2) {
        string2 = String.format("org/apache/commons/codec/language/bm/%s.txt", string2);
        InputStream inputStream = Languages.class.getClassLoader().getResourceAsStream(string2);
        if (inputStream == null) {
            throw new IllegalArgumentException("Unable to load resource: " + string2);
        }
        return new Scanner(inputStream, "UTF-8");
    }

    private static Scanner createScanner(NameType object, RuleType object2, String string2) {
        object = Rule.createResourceName((NameType)((Object)object), (RuleType)((Object)object2), string2);
        object2 = Languages.class.getClassLoader().getResourceAsStream((String)object);
        if (object2 == null) {
            throw new IllegalArgumentException("Unable to load resource: " + (String)object);
        }
        return new Scanner((InputStream)object2, "UTF-8");
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean endsWith(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence2.length() <= charSequence.length()) {
            int n2 = charSequence.length() - 1;
            int n3 = charSequence2.length() - 1;
            do {
                if (n3 < 0) {
                    return true;
                }
                if (charSequence.charAt(n2) != charSequence2.charAt(n3)) break;
                --n2;
                --n3;
            } while (true);
        }
        return false;
    }

    public static List<Rule> getInstance(NameType nameType, RuleType ruleType, String string2) {
        return Rule.getInstance(nameType, ruleType, Languages.LanguageSet.from(new HashSet<String>(Arrays.asList(string2))));
    }

    public static List<Rule> getInstance(NameType object, RuleType iterator, Languages.LanguageSet languageSet) {
        iterator = Rule.getInstanceMap((NameType)((Object)object), (RuleType)((Object)iterator), languageSet);
        object = new ArrayList();
        iterator = iterator.values().iterator();
        while (iterator.hasNext()) {
            object.addAll((List)iterator.next());
        }
        return object;
    }

    public static Map<String, List<Rule>> getInstanceMap(NameType nameType, RuleType ruleType, String string2) {
        Map<String, List<Rule>> map = RULES.get((Object)nameType).get((Object)ruleType).get(string2);
        if (map == null) {
            throw new IllegalArgumentException(String.format("No rules found for %s, %s, %s.", nameType.getName(), ruleType.getName(), string2));
        }
        return map;
    }

    public static Map<String, List<Rule>> getInstanceMap(NameType nameType, RuleType ruleType, Languages.LanguageSet languageSet) {
        if (languageSet.isSingleton()) {
            return Rule.getInstanceMap(nameType, ruleType, languageSet.getAny());
        }
        return Rule.getInstanceMap(nameType, ruleType, "any");
    }

    private static Phoneme parsePhoneme(String string2) {
        int n2 = string2.indexOf("[");
        if (n2 >= 0) {
            if (!string2.endsWith("]")) {
                throw new IllegalArgumentException("Phoneme expression contains a '[' but does not end in ']'");
            }
            return new Phoneme(string2.substring(0, n2), Languages.LanguageSet.from(new HashSet<String>(Arrays.asList(string2.substring(n2 + 1, string2.length() - 1).split("[+]")))));
        }
        return new Phoneme(string2, Languages.ANY_LANGUAGE);
    }

    private static PhonemeExpr parsePhonemeExpr(String string2) {
        if (string2.startsWith("(")) {
            if (!string2.endsWith(")")) {
                throw new IllegalArgumentException("Phoneme starts with '(' so must end with ')'");
            }
            ArrayList<Phoneme> arrayList = new ArrayList<Phoneme>();
            string2 = string2.substring(1, string2.length() - 1);
            String[] arrstring = string2.split("[|]");
            int n2 = arrstring.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                arrayList.add(Rule.parsePhoneme(arrstring[i2]));
            }
            if (string2.startsWith("|") || string2.endsWith("|")) {
                arrayList.add(new Phoneme("", Languages.ANY_LANGUAGE));
            }
            return new PhonemeList(arrayList);
        }
        return Rule.parsePhoneme(string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Map<String, List<Rule>> parseRules(Scanner scanner, final String string2) {
        HashMap<String, List<Rule>> hashMap = new HashMap<String, List<Rule>>();
        int n2 = 0;
        boolean bl2 = false;
        while (scanner.hasNextLine()) {
            Object object;
            final int n3 = n2 + 1;
            Object object2 = object = scanner.nextLine();
            if (bl2) {
                n2 = n3;
                if (!object2.endsWith("*/")) continue;
                bl2 = false;
                n2 = n3;
                continue;
            }
            if (object2.startsWith("/*")) {
                bl2 = true;
                n2 = n3;
                continue;
            }
            n2 = object2.indexOf("//");
            Object object3 = object2;
            if (n2 >= 0) {
                object3 = object2.substring(0, n2);
            }
            object2 = object3.trim();
            n2 = n3;
            if (object2.length() == 0) continue;
            if (object2.startsWith("#include")) {
                if ((object2 = object2.substring("#include".length()).trim()).contains(" ")) {
                    throw new IllegalArgumentException("Malformed import statement '" + (String)object + "' in " + string2);
                }
                hashMap.putAll(Rule.parseRules(Rule.createScanner((String)object2), string2 + "->" + (String)object2));
                n2 = n3;
                continue;
            }
            if ((object2 = object2.split("\\s+")).length != 4) {
                throw new IllegalArgumentException("Malformed rule statement split into " + object2.length + " parts: " + (String)object + " in " + string2);
            }
            try {
                object = new Rule(Rule.stripQuotes((String)object2[0]), Rule.stripQuotes((String)object2[1]), Rule.stripQuotes((String)object2[2]), Rule.parsePhonemeExpr(Rule.stripQuotes((String)object2[3]))){
                    private final String loc;
                    private final int myLine;

                    public String toString() {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Rule");
                        stringBuilder.append("{line=").append(this.myLine);
                        stringBuilder.append(", loc='").append(this.loc).append('\'');
                        stringBuilder.append('}');
                        return stringBuilder.toString();
                    }
                };
                String string3 = object.pattern.substring(0, 1);
                object2 = object3 = hashMap.get(string3);
                if (object3 == null) {
                    object2 = new ArrayList<Object>();
                    hashMap.put(string3, (List<Rule>)object2);
                }
                object2.add(object);
                n2 = n3;
                continue;
            }
            catch (IllegalArgumentException var0_1) {}
            throw new IllegalStateException("Problem parsing line '" + n3 + "' in " + string2, var0_1);
        }
        return hashMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static RPattern pattern(final String string2) {
        final boolean bl2 = true;
        boolean bl3 = string2.startsWith("^");
        boolean bl4 = string2.endsWith("$");
        int n2 = bl3 ? 1 : 0;
        int n3 = bl4 ? string2.length() - 1 : string2.length();
        final String string3 = string2.substring(n2, n3);
        if (!string3.contains("[")) {
            if (bl3 && bl4) {
                if (string3.length() != 0) return new RPattern(){

                    @Override
                    public boolean isMatch(CharSequence charSequence) {
                        return charSequence.equals(string3);
                    }
                };
                return new RPattern(){

                    @Override
                    public boolean isMatch(CharSequence charSequence) {
                        if (charSequence.length() == 0) {
                            return true;
                        }
                        return false;
                    }
                };
            }
            if ((bl3 || bl4) && string3.length() == 0) {
                return ALL_STRINGS_RMATCHER;
            }
            if (bl3) {
                return new RPattern(){

                    @Override
                    public boolean isMatch(CharSequence charSequence) {
                        return Rule.startsWith(charSequence, string3);
                    }
                };
            }
            if (!bl4) return new RPattern(){
                Pattern pattern;

                @Override
                public boolean isMatch(CharSequence charSequence) {
                    return this.pattern.matcher(charSequence).find();
                }
            };
            return new RPattern(){

                @Override
                public boolean isMatch(CharSequence charSequence) {
                    return Rule.endsWith(charSequence, string3);
                }
            };
        } else {
            String string4;
            boolean bl5 = string3.startsWith("[");
            boolean bl6 = string3.endsWith("]");
            if (!bl5 || !bl6 || (string4 = string3.substring(1, string3.length() - 1)).contains("[")) return new ;
            {
                bl5 = string4.startsWith("^");
                string3 = string4;
                if (bl5) {
                    string3 = string4.substring(1);
                }
                if (bl5) {
                    bl2 = false;
                }
                if (bl3 && bl4) {
                    return new RPattern(){

                        @Override
                        public boolean isMatch(CharSequence charSequence) {
                            if (charSequence.length() == 1 && Rule.contains(string3, charSequence.charAt(0)) == bl2) {
                                return true;
                            }
                            return false;
                        }
                    };
                }
                if (bl3) {
                    return new RPattern(){

                        @Override
                        public boolean isMatch(CharSequence charSequence) {
                            boolean bl22;
                            boolean bl3 = bl22 = false;
                            if (charSequence.length() > 0) {
                                bl3 = bl22;
                                if (Rule.contains(string3, charSequence.charAt(0)) == bl2) {
                                    bl3 = true;
                                }
                            }
                            return bl3;
                        }
                    };
                }
                if (!bl4) return new ;
                return new RPattern(){

                    @Override
                    public boolean isMatch(CharSequence charSequence) {
                        if (charSequence.length() > 0 && Rule.contains(string3, charSequence.charAt(charSequence.length() - 1)) == bl2) {
                            return true;
                        }
                        return false;
                    }
                };
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static boolean startsWith(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence2.length() > charSequence.length()) {
            return false;
        }
        int n2 = 0;
        while (n2 < charSequence2.length()) {
            if (charSequence.charAt(n2) != charSequence2.charAt(n2)) return false;
            ++n2;
        }
        return true;
    }

    private static String stripQuotes(String string2) {
        String string3 = string2;
        if (string2.startsWith("\"")) {
            string3 = string2.substring(1);
        }
        string2 = string3;
        if (string3.endsWith("\"")) {
            string2 = string3.substring(0, string3.length() - 1);
        }
        return string2;
    }

    public RPattern getLContext() {
        return this.lContext;
    }

    public String getPattern() {
        return this.pattern;
    }

    public PhonemeExpr getPhoneme() {
        return this.phoneme;
    }

    public RPattern getRContext() {
        return this.rContext;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean patternAndContextMatches(CharSequence charSequence, int n2) {
        if (n2 < 0) {
            throw new IndexOutOfBoundsException("Can not match pattern at negative indexes");
        }
        int n3 = n2 + this.pattern.length();
        if (n3 > charSequence.length() || !charSequence.subSequence(n2, n3).equals(this.pattern) || !this.rContext.isMatch(charSequence.subSequence(n3, charSequence.length()))) {
            return false;
        }
        return this.lContext.isMatch(charSequence.subSequence(0, n2));
    }

    public static final class Phoneme
    implements PhonemeExpr {
        public static final Comparator<Phoneme> COMPARATOR = new Comparator<Phoneme>(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public int compare(Phoneme phoneme, Phoneme phoneme2) {
                for (int i2 = 0; i2 < phoneme.phonemeText.length(); ++i2) {
                    int n2;
                    if (i2 >= phoneme2.phonemeText.length()) {
                        return 1;
                    }
                    int n3 = n2 = phoneme.phonemeText.charAt(i2) - phoneme2.phonemeText.charAt(i2);
                    if (n2 != 0) return n3;
                }
                if (phoneme.phonemeText.length() >= phoneme2.phonemeText.length()) return 0;
                return -1;
            }
        };
        private final Languages.LanguageSet languages;
        private final StringBuilder phonemeText;

        public Phoneme(CharSequence charSequence, Languages.LanguageSet languageSet) {
            this.phonemeText = new StringBuilder(charSequence);
            this.languages = languageSet;
        }

        public Phoneme(Phoneme phoneme, Phoneme phoneme2) {
            this(phoneme.phonemeText, phoneme.languages);
            this.phonemeText.append(phoneme2.phonemeText);
        }

        public Phoneme(Phoneme phoneme, Phoneme phoneme2, Languages.LanguageSet languageSet) {
            this(phoneme.phonemeText, languageSet);
            this.phonemeText.append(phoneme2.phonemeText);
        }

        public Phoneme append(CharSequence charSequence) {
            this.phonemeText.append(charSequence);
            return this;
        }

        public Languages.LanguageSet getLanguages() {
            return this.languages;
        }

        public CharSequence getPhonemeText() {
            return this.phonemeText;
        }

        @Override
        public Iterable<Phoneme> getPhonemes() {
            return Collections.singleton(this);
        }

        @Deprecated
        public Phoneme join(Phoneme phoneme) {
            return new Phoneme(this.phonemeText.toString() + phoneme.phonemeText.toString(), this.languages.restrictTo(phoneme.languages));
        }

    }

    public static interface PhonemeExpr {
        public Iterable<Phoneme> getPhonemes();
    }

    public static final class PhonemeList
    implements PhonemeExpr {
        private final List<Phoneme> phonemes;

        public PhonemeList(List<Phoneme> list) {
            this.phonemes = list;
        }

        public List<Phoneme> getPhonemes() {
            return this.phonemes;
        }
    }

    public static interface RPattern {
        public boolean isMatch(CharSequence var1);
    }

}

