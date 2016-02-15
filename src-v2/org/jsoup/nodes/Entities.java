/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.nodes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

public class Entities {
    private static final Map<String, Character> base;
    private static final Map<Character, String> baseByVal;
    private static final Map<String, Character> full;
    private static final Map<Character, String> fullByVal;
    private static final Pattern strictUnescapePattern;
    private static final Pattern unescapePattern;
    private static final Object[][] xhtmlArray;
    private static final Map<Character, String> xhtmlByVal;

    static {
        unescapePattern = Pattern.compile("&(#(x|X)?([0-9a-fA-F]+)|[a-zA-Z]+\\d*);?");
        strictUnescapePattern = Pattern.compile("&(#(x|X)?([0-9a-fA-F]+)|[a-zA-Z]+\\d*);");
        Object[] arrobject = new Object[]{"quot", 34};
        Object[] object2 = new Object[]{"amp", 38};
        Object[] arrobject2 = new Object[]{"gt", 62};
        xhtmlArray = new Object[][]{arrobject, object2, {"apos", 39}, {"lt", 60}, arrobject2};
        xhtmlByVal = new HashMap<Character, String>();
        base = Entities.loadEntities("entities-base.properties");
        baseByVal = Entities.toCharacterKey(base);
        full = Entities.loadEntities("entities-full.properties");
        fullByVal = Entities.toCharacterKey(full);
        for (Object[] arrobject3 : xhtmlArray) {
            char c2 = (char)((Integer)arrobject3[1]).intValue();
            xhtmlByVal.put(Character.valueOf(c2), (String)arrobject3[0]);
        }
    }

    private Entities() {
    }

    static /* synthetic */ Map access$000() {
        return xhtmlByVal;
    }

    static /* synthetic */ Map access$100() {
        return baseByVal;
    }

    static /* synthetic */ Map access$200() {
        return fullByVal;
    }

    /*
     * Enabled aggressive block sorting
     */
    static String escape(String string2, CharsetEncoder charsetEncoder, EscapeMode map) {
        StringBuilder stringBuilder = new StringBuilder(string2.length() * 2);
        map = map.getMap();
        int n2 = string2.length();
        int n3 = 0;
        while (n3 < n2) {
            int n4 = string2.codePointAt(n3);
            if (n4 < 65536) {
                char c2 = (char)n4;
                if (map.containsKey(Character.valueOf(c2))) {
                    stringBuilder.append('&').append(map.get(Character.valueOf(c2))).append(';');
                } else if (charsetEncoder.canEncode(c2)) {
                    stringBuilder.append(c2);
                } else {
                    stringBuilder.append("&#x").append(Integer.toHexString(n4)).append(';');
                }
            } else {
                String string3 = new String(Character.toChars(n4));
                if (charsetEncoder.canEncode(string3)) {
                    stringBuilder.append(string3);
                } else {
                    stringBuilder.append("&#x").append(Integer.toHexString(n4)).append(';');
                }
            }
            n3 += Character.charCount(n4);
        }
        return stringBuilder.toString();
    }

    static String escape(String string2, Document.OutputSettings outputSettings) {
        return Entities.escape(string2, outputSettings.encoder(), outputSettings.escapeMode());
    }

    public static Character getCharacterByName(String string2) {
        return full.get(string2);
    }

    public static boolean isBaseNamedEntity(String string2) {
        return base.containsKey(string2);
    }

    public static boolean isNamedEntity(String string2) {
        return full.containsKey(string2);
    }

    private static Map<String, Character> loadEntities(String object) {
        Properties object22 = new Properties();
        HashMap<String, Character> hashMap = new HashMap<String, Character>();
        try {
            InputStream inputStream = Entities.class.getResourceAsStream((String)((Object)object));
            object22.load(inputStream);
            inputStream.close();
        }
        catch (IOException var2_5) {
            throw new MissingResourceException("Error loading entities resource: " + var2_5.getMessage(), "Entities", (String)((Object)object));
        }
        for (Map.Entry entry : object22.entrySet()) {
            char c2 = (char)Integer.parseInt((String)entry.getValue(), 16);
            hashMap.put((String)entry.getKey(), Character.valueOf(c2));
        }
        return hashMap;
    }

    private static Map<Character, String> toCharacterKey(Map<String, Character> object) {
        HashMap<Character, String> hashMap = new HashMap<Character, String>();
        object = object.entrySet().iterator();
        while (object.hasNext()) {
            Object object2 = (Map.Entry)object.next();
            Character c2 = (Character)object2.getValue();
            object2 = (String)object2.getKey();
            if (hashMap.containsKey(c2)) {
                if (!object2.toLowerCase().equals(object2)) continue;
                hashMap.put(c2, (String)object2);
                continue;
            }
            hashMap.put(c2, (String)object2);
        }
        return hashMap;
    }

    static String unescape(String string2) {
        return Entities.unescape(string2, false);
    }

    static String unescape(String string2, boolean bl2) {
        return Parser.unescapeEntities(string2, bl2);
    }

    public static enum EscapeMode {
        xhtml(Entities.access$000()),
        base(Entities.access$100()),
        extended(Entities.access$200());
        
        private Map<Character, String> map;

        private EscapeMode(Map<Character, String> map) {
            this.map = map;
        }

        public Map<Character, String> getMap() {
            return this.map;
        }
    }

}

