/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.safety;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

public class Whitelist {
    private Map<TagName, Set<AttributeKey>> attributes = new HashMap<TagName, Set<AttributeKey>>();
    private Map<TagName, Map<AttributeKey, AttributeValue>> enforcedAttributes = new HashMap<TagName, Map<AttributeKey, AttributeValue>>();
    private boolean preserveRelativeLinks = false;
    private Map<TagName, Map<AttributeKey, Set<Protocol>>> protocols = new HashMap<TagName, Map<AttributeKey, Set<Protocol>>>();
    private Set<TagName> tagNames = new HashSet<TagName>();

    public static Whitelist basic() {
        return new Whitelist().addTags("a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em", "i", "li", "ol", "p", "pre", "q", "small", "strike", "strong", "sub", "sup", "u", "ul").addAttributes("a", "href").addAttributes("blockquote", "cite").addAttributes("q", "cite").addProtocols("a", "href", "ftp", "http", "https", "mailto").addProtocols("blockquote", "cite", "http", "https").addProtocols("cite", "cite", "http", "https").addEnforcedAttribute("a", "rel", "nofollow");
    }

    public static Whitelist basicWithImages() {
        return Whitelist.basic().addTags("img").addAttributes("img", "align", "alt", "height", "src", "title", "width").addProtocols("img", "src", "http", "https");
    }

    public static Whitelist none() {
        return new Whitelist();
    }

    public static Whitelist relaxed() {
        return new Whitelist().addTags("a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img", "li", "ol", "p", "pre", "q", "small", "strike", "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u", "ul").addAttributes("a", "href", "title").addAttributes("blockquote", "cite").addAttributes("col", "span", "width").addAttributes("colgroup", "span", "width").addAttributes("img", "align", "alt", "height", "src", "title", "width").addAttributes("ol", "start", "type").addAttributes("q", "cite").addAttributes("table", "summary", "width").addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width").addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope", "width").addAttributes("ul", "type").addProtocols("a", "href", "ftp", "http", "https", "mailto").addProtocols("blockquote", "cite", "http", "https").addProtocols("cite", "cite", "http", "https").addProtocols("img", "src", "http", "https").addProtocols("q", "cite", "http", "https");
    }

    public static Whitelist simpleText() {
        return new Whitelist().addTags("b", "em", "i", "strong", "u");
    }

    private boolean testValidProtocol(Element object, Attribute object2, Set<Protocol> object3) {
        String string2;
        object = string2 = object.absUrl((String)object2.getKey());
        if (string2.length() == 0) {
            object = object2.getValue();
        }
        if (!this.preserveRelativeLinks) {
            object2.setValue((String)object);
        }
        object2 = object3.iterator();
        while (object2.hasNext()) {
            object3 = (Protocol)object2.next();
            object3 = object3.toString() + ":";
            if (!object.toLowerCase().startsWith((String)object3)) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public /* varargs */ Whitelist addAttributes(String object, String ... arrstring) {
        Validate.notEmpty((String)object);
        Validate.notNull(arrstring);
        boolean bl2 = arrstring.length > 0;
        Validate.isTrue(bl2, "No attributes supplied.");
        object = TagName.valueOf((String)object);
        if (!this.tagNames.contains(object)) {
            this.tagNames.add((TagName)object);
        }
        HashSet<AttributeKey> hashSet = new HashSet<AttributeKey>();
        for (String string2 : arrstring) {
            Validate.notEmpty(string2);
            hashSet.add(AttributeKey.valueOf(string2));
        }
        if (this.attributes.containsKey(object)) {
            this.attributes.get(object).addAll(hashSet);
            return this;
        }
        this.attributes.put((TagName)object, (Set<AttributeKey>)hashSet);
        return this;
    }

    public Whitelist addEnforcedAttribute(String object, String object2, String object3) {
        Validate.notEmpty((String)object);
        Validate.notEmpty((String)object2);
        Validate.notEmpty((String)object3);
        object = TagName.valueOf((String)object);
        if (!this.tagNames.contains(object)) {
            this.tagNames.add((TagName)object);
        }
        object2 = AttributeKey.valueOf((String)object2);
        object3 = AttributeValue.valueOf((String)object3);
        if (this.enforcedAttributes.containsKey(object)) {
            this.enforcedAttributes.get(object).put((AttributeKey)object2, (AttributeValue)object3);
            return this;
        }
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        hashMap.put(object2, object3);
        this.enforcedAttributes.put((TagName)object, (Map<AttributeKey, AttributeValue>)hashMap);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public /* varargs */ Whitelist addProtocols(String hashSet, String object, String ... arrstring) {
        void var3_7;
        Validate.notEmpty(hashSet);
        Validate.notEmpty((String)object);
        Validate.notNull(var3_7);
        TagName tagName = TagName.valueOf(hashSet);
        AttributeKey attributeKey = AttributeKey.valueOf((String)object);
        if (this.protocols.containsKey(tagName)) {
            hashSet = this.protocols.get(tagName);
        } else {
            hashSet = new HashMap();
            this.protocols.put(tagName, (Map<AttributeKey, Set<Protocol>>)((Object)hashSet));
        }
        if (hashSet.containsKey(attributeKey)) {
            hashSet = (Set)hashSet.get(attributeKey);
        } else {
            HashSet<Protocol> hashSet2 = new HashSet<Protocol>();
            hashSet.put(attributeKey, hashSet2);
            hashSet = hashSet2;
        }
        int n2 = var3_7.length;
        int n3 = 0;
        while (n3 < n2) {
            void var2_5 = var3_7[n3];
            Validate.notEmpty((String)var2_5);
            hashSet.add(Protocol.valueOf((String)var2_5));
            ++n3;
        }
        return this;
    }

    public /* varargs */ Whitelist addTags(String ... arrstring) {
        Validate.notNull(arrstring);
        for (String string2 : arrstring) {
            Validate.notEmpty(string2);
            this.tagNames.add(TagName.valueOf(string2));
        }
        return this;
    }

    Attributes getEnforcedAttributes(String iterator) {
        Attributes attributes = new Attributes();
        if (this.enforcedAttributes.containsKey(iterator = TagName.valueOf((String)((Object)iterator)))) {
            for (Map.Entry<AttributeKey, AttributeValue> entry : this.enforcedAttributes.get(iterator).entrySet()) {
                attributes.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return attributes;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected boolean isSafeAttribute(String map, Element element, Attribute attribute) {
        boolean bl2 = true;
        TagName tagName = TagName.valueOf(map);
        AttributeKey attributeKey = AttributeKey.valueOf((String)attribute.getKey());
        if (this.attributes.containsKey(tagName) && this.attributes.get(tagName).contains(attributeKey)) {
            if (!this.protocols.containsKey(tagName)) return bl2;
            map = this.protocols.get(tagName);
            if (!map.containsKey(attributeKey)) return true;
            if (!this.testValidProtocol(element, attribute, map.get(attributeKey))) return false;
            return true;
        }
        if (map.equals((Object)":all")) return false;
        if (this.isSafeAttribute(":all", element, attribute)) return bl2;
        return false;
    }

    protected boolean isSafeTag(String string2) {
        return this.tagNames.contains(TagName.valueOf(string2));
    }

    public Whitelist preserveRelativeLinks(boolean bl2) {
        this.preserveRelativeLinks = bl2;
        return this;
    }

    static class AttributeKey
    extends TypedValue {
        AttributeKey(String string2) {
            super(string2);
        }

        static AttributeKey valueOf(String string2) {
            return new AttributeKey(string2);
        }
    }

    static class AttributeValue
    extends TypedValue {
        AttributeValue(String string2) {
            super(string2);
        }

        static AttributeValue valueOf(String string2) {
            return new AttributeValue(string2);
        }
    }

    static class Protocol
    extends TypedValue {
        Protocol(String string2) {
            super(string2);
        }

        static Protocol valueOf(String string2) {
            return new Protocol(string2);
        }
    }

    static class TagName
    extends TypedValue {
        TagName(String string2) {
            super(string2);
        }

        static TagName valueOf(String string2) {
            return new TagName(string2);
        }
    }

    static abstract class TypedValue {
        private String value;

        TypedValue(String string2) {
            Validate.notNull(string2);
            this.value = string2;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (TypedValue)object;
            if (this.value == null) {
                if (object.value == null) return true;
                return false;
            }
            if (!this.value.equals(object.value)) return false;
            return true;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int hashCode() {
            int n2;
            if (this.value == null) {
                n2 = 0;
                do {
                    return n2 + 31;
                    break;
                } while (true);
            }
            n2 = this.value.hashCode();
            return n2 + 31;
        }

        public String toString() {
            return this.value;
        }
    }

}

