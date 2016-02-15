/*
 * Decompiled with CFR 0_110.
 */
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
import org.apache.commons.codec.language.bm.NameType;

public class Languages {
    public static final String ANY = "any";
    public static final LanguageSet ANY_LANGUAGE;
    private static final Map<NameType, Languages> LANGUAGES;
    public static final LanguageSet NO_LANGUAGES;
    private final Set<String> languages;

    static {
        LANGUAGES = new EnumMap<NameType, Languages>(NameType.class);
        for (NameType nameType : NameType.values()) {
            LANGUAGES.put(nameType, Languages.getInstance(Languages.langResourceName(nameType)));
        }
        NO_LANGUAGES = new LanguageSet(){

            @Override
            public boolean contains(String string2) {
                return false;
            }

            @Override
            public String getAny() {
                throw new NoSuchElementException("Can't fetch any language from the empty language set.");
            }

            @Override
            public boolean isEmpty() {
                return true;
            }

            @Override
            public boolean isSingleton() {
                return false;
            }

            @Override
            public LanguageSet restrictTo(LanguageSet languageSet) {
                return this;
            }

            public String toString() {
                return "NO_LANGUAGES";
            }
        };
        ANY_LANGUAGE = new LanguageSet(){

            @Override
            public boolean contains(String string2) {
                return true;
            }

            @Override
            public String getAny() {
                throw new NoSuchElementException("Can't fetch any language from the any language set.");
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean isSingleton() {
                return false;
            }

            @Override
            public LanguageSet restrictTo(LanguageSet languageSet) {
                return languageSet;
            }

            public String toString() {
                return "ANY_LANGUAGE";
            }
        };
    }

    private Languages(Set<String> set) {
        this.languages = set;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Languages getInstance(String object) {
        HashSet<Object> hashSet = new HashSet<Object>();
        Object object2 = Languages.class.getClassLoader().getResourceAsStream((String)object);
        if (object2 == null) {
            throw new IllegalArgumentException("Unable to resolve required resource: " + (String)object);
        }
        object = new Scanner((InputStream)object2, "UTF-8");
        boolean bl2 = false;
        try {
            while (object.hasNextLine()) {
                object2 = object.nextLine().trim();
                if (bl2) {
                    if (!object2.endsWith("*/")) continue;
                    bl2 = false;
                    continue;
                }
                if (object2.startsWith("/*")) {
                    bl2 = true;
                    continue;
                }
                if (object2.length() <= 0) continue;
                hashSet.add(object2);
            }
            return new Languages(Collections.unmodifiableSet(hashSet));
        }
        finally {
            object.close();
        }
    }

    public static Languages getInstance(NameType nameType) {
        return LANGUAGES.get((Object)nameType);
    }

    private static String langResourceName(NameType nameType) {
        return String.format("org/apache/commons/codec/language/bm/%s_languages.txt", nameType.getName());
    }

    public Set<String> getLanguages() {
        return this.languages;
    }

    public static abstract class LanguageSet {
        public static LanguageSet from(Set<String> set) {
            if (set.isEmpty()) {
                return Languages.NO_LANGUAGES;
            }
            return new SomeLanguages(set);
        }

        public abstract boolean contains(String var1);

        public abstract String getAny();

        public abstract boolean isEmpty();

        public abstract boolean isSingleton();

        public abstract LanguageSet restrictTo(LanguageSet var1);
    }

    public static final class SomeLanguages
    extends LanguageSet {
        private final Set<String> languages;

        private SomeLanguages(Set<String> set) {
            this.languages = Collections.unmodifiableSet(set);
        }

        @Override
        public boolean contains(String string2) {
            return this.languages.contains(string2);
        }

        @Override
        public String getAny() {
            return this.languages.iterator().next();
        }

        public Set<String> getLanguages() {
            return this.languages;
        }

        @Override
        public boolean isEmpty() {
            return this.languages.isEmpty();
        }

        @Override
        public boolean isSingleton() {
            if (this.languages.size() == 1) {
                return true;
            }
            return false;
        }

        @Override
        public LanguageSet restrictTo(LanguageSet languageSet) {
            if (languageSet == Languages.NO_LANGUAGES) {
                return languageSet;
            }
            if (languageSet == Languages.ANY_LANGUAGE) {
                return this;
            }
            languageSet = (SomeLanguages)languageSet;
            HashSet<String> hashSet = new HashSet<String>(Math.min(this.languages.size(), languageSet.languages.size()));
            for (String string2 : this.languages) {
                if (!languageSet.languages.contains(string2)) continue;
                hashSet.add(string2);
            }
            return SomeLanguages.from(hashSet);
        }

        public String toString() {
            return "Languages(" + this.languages.toString() + ")";
        }
    }

}

