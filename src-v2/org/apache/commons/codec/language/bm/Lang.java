/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language.bm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.language.bm.Languages;
import org.apache.commons.codec.language.bm.NameType;

public class Lang {
    private static final String LANGUAGE_RULES_RN = "org/apache/commons/codec/language/bm/lang.txt";
    private static final Map<NameType, Lang> Langs = new EnumMap<NameType, Lang>(NameType.class);
    private final Languages languages;
    private final List<LangRule> rules;

    static {
        for (NameType nameType : NameType.values()) {
            Langs.put(nameType, Lang.loadFromResource("org/apache/commons/codec/language/bm/lang.txt", Languages.getInstance(nameType)));
        }
    }

    private Lang(List<LangRule> list, Languages languages) {
        this.rules = Collections.unmodifiableList(list);
        this.languages = languages;
    }

    public static Lang instance(NameType nameType) {
        return Langs.get((Object)nameType);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Lang loadFromResource(String string2, Languages languages) {
        ArrayList<LangRule> arrayList = new ArrayList<LangRule>();
        Object object = Lang.class.getClassLoader().getResourceAsStream(string2);
        if (object == null) {
            throw new IllegalStateException("Unable to resolve required resource:org/apache/commons/codec/language/bm/lang.txt");
        }
        Scanner scanner = new Scanner((InputStream)object, "UTF-8");
        boolean bl2 = false;
        try {
            while (scanner.hasNextLine()) {
                String[] arrstring;
                object = arrstring = scanner.nextLine();
                if (bl2) {
                    if (!object.endsWith("*/")) continue;
                    bl2 = false;
                    continue;
                }
                if (object.startsWith("/*")) {
                    bl2 = true;
                    continue;
                }
                int n2 = object.indexOf("//");
                Object object2 = object;
                if (n2 >= 0) {
                    object2 = object.substring(0, n2);
                }
                if ((object = object2.trim()).length() == 0) continue;
                if ((object = object.split("\\s+")).length != 3) {
                    throw new IllegalArgumentException("Malformed line '" + (String)arrstring + "' in language resource '" + string2 + "'");
                }
                object2 = Pattern.compile(object[0]);
                arrstring = object[1].split("\\+");
                boolean bl3 = object[2].equals("true");
                arrayList.add(new LangRule((Pattern)object2, new HashSet<String>(Arrays.asList(arrstring)), bl3));
            }
            return new Lang(arrayList, languages);
        }
        finally {
            scanner.close();
        }
    }

    public String guessLanguage(String object) {
        if ((object = this.guessLanguages((String)object)).isSingleton()) {
            return object.getAny();
        }
        return "any";
    }

    public Languages.LanguageSet guessLanguages(String object) {
        object = object.toLowerCase(Locale.ENGLISH);
        Object object2 = new HashSet<String>(this.languages.getLanguages());
        for (LangRule langRule : this.rules) {
            if (!langRule.matches((String)object)) continue;
            if (langRule.acceptOnMatch) {
                object2.retainAll(langRule.languages);
                continue;
            }
            object2.removeAll(langRule.languages);
        }
        object = object2 = Languages.LanguageSet.from(object2);
        if (object2.equals(Languages.NO_LANGUAGES)) {
            object = Languages.ANY_LANGUAGE;
        }
        return object;
    }

    private static final class LangRule {
        private final boolean acceptOnMatch;
        private final Set<String> languages;
        private final Pattern pattern;

        private LangRule(Pattern pattern, Set<String> set, boolean bl2) {
            this.pattern = pattern;
            this.languages = set;
            this.acceptOnMatch = bl2;
        }

        public boolean matches(String string2) {
            return this.pattern.matcher(string2).find();
        }
    }

}

