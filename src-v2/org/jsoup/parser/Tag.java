/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.parser;

import java.util.HashMap;
import java.util.Map;
import org.jsoup.helper.Validate;

public class Tag {
    private static final String[] blockTags;
    private static final String[] emptyTags;
    private static final String[] formListedTags;
    private static final String[] formSubmitTags;
    private static final String[] formatAsInlineTags;
    private static final String[] inlineTags;
    private static final String[] preserveWhitespaceTags;
    private static final Map<String, Tag> tags;
    private boolean canContainBlock = true;
    private boolean canContainInline = true;
    private boolean empty = false;
    private boolean formList = false;
    private boolean formSubmit = false;
    private boolean formatAsBlock = true;
    private boolean isBlock = true;
    private boolean preserveWhitespace = false;
    private boolean selfClosing = false;
    private String tagName;

    static {
        int n2;
        tags = new HashMap<String, Tag>();
        blockTags = new String[]{"html", "head", "body", "frameset", "script", "noscript", "style", "meta", "link", "title", "frame", "noframes", "section", "nav", "aside", "hgroup", "header", "footer", "p", "h1", "h2", "h3", "h4", "h5", "h6", "ul", "ol", "pre", "div", "blockquote", "hr", "address", "figure", "figcaption", "form", "fieldset", "ins", "del", "s", "dl", "dt", "dd", "li", "table", "caption", "thead", "tfoot", "tbody", "colgroup", "col", "tr", "th", "td", "video", "audio", "canvas", "details", "menu", "plaintext"};
        inlineTags = new String[]{"object", "base", "font", "tt", "i", "b", "u", "big", "small", "em", "strong", "dfn", "code", "samp", "kbd", "var", "cite", "abbr", "time", "acronym", "mark", "ruby", "rt", "rp", "a", "img", "br", "wbr", "map", "q", "sub", "sup", "bdo", "iframe", "embed", "span", "input", "select", "textarea", "label", "button", "optgroup", "option", "legend", "datalist", "keygen", "output", "progress", "meter", "area", "param", "source", "track", "summary", "command", "device"};
        emptyTags = new String[]{"meta", "link", "base", "frame", "img", "br", "wbr", "embed", "hr", "input", "keygen", "col", "command", "device"};
        formatAsInlineTags = new String[]{"title", "a", "p", "h1", "h2", "h3", "h4", "h5", "h6", "pre", "address", "li", "th", "td", "script", "style", "ins", "del", "s"};
        preserveWhitespaceTags = new String[]{"pre", "plaintext", "title", "textarea"};
        formListedTags = new String[]{"button", "fieldset", "input", "keygen", "object", "output", "select", "textarea"};
        formSubmitTags = new String[]{"input", "keygen", "object", "select", "textarea"};
        String[] arrstring = blockTags;
        int n3 = arrstring.length;
        for (n2 = 0; n2 < n3; ++n2) {
            Tag.register(new Tag(arrstring[n2]));
        }
        arrstring = inlineTags;
        n3 = arrstring.length;
        for (n2 = 0; n2 < n3; ++n2) {
            Tag object2222 = new Tag(arrstring[n2]);
            object2222.isBlock = false;
            object2222.canContainBlock = false;
            object2222.formatAsBlock = false;
            Tag.register(object2222);
        }
        for (String string2 : emptyTags) {
            Tag tag = tags.get(string2);
            Validate.notNull(tag);
            tag.canContainBlock = false;
            tag.canContainInline = false;
            tag.empty = true;
        }
        for (String string3 : formatAsInlineTags) {
            Tag tag = tags.get(string3);
            Validate.notNull(tag);
            tag.formatAsBlock = false;
        }
        for (String string4 : preserveWhitespaceTags) {
            Tag tag = tags.get(string4);
            Validate.notNull(tag);
            tag.preserveWhitespace = true;
        }
        for (String string5 : formListedTags) {
            Tag tag = tags.get(string5);
            Validate.notNull(tag);
            tag.formList = true;
        }
        for (String string6 : formSubmitTags) {
            Tag tag = tags.get(string6);
            Validate.notNull(tag);
            tag.formSubmit = true;
        }
    }

    private Tag(String string2) {
        this.tagName = string2.toLowerCase();
    }

    public static boolean isKnownTag(String string2) {
        return tags.containsKey(string2);
    }

    private static void register(Tag tag) {
        tags.put(tag.tagName, tag);
    }

    public static Tag valueOf(String object) {
        Object object2;
        Validate.notNull(object);
        Object object3 = object2 = tags.get(object);
        if (object2 == null) {
            object2 = object.trim().toLowerCase();
            Validate.notEmpty((String)object2);
            object3 = object = tags.get(object2);
            if (object == null) {
                object3 = new Tag((String)object2);
                object3.isBlock = false;
                object3.canContainBlock = true;
            }
        }
        return object3;
    }

    public boolean canContainBlock() {
        return this.canContainBlock;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Tag)) {
            return false;
        }
        object = (Tag)object;
        if (this.canContainBlock != object.canContainBlock) {
            return false;
        }
        if (this.canContainInline != object.canContainInline) {
            return false;
        }
        if (this.empty != object.empty) {
            return false;
        }
        if (this.formatAsBlock != object.formatAsBlock) {
            return false;
        }
        if (this.isBlock != object.isBlock) {
            return false;
        }
        if (this.preserveWhitespace != object.preserveWhitespace) {
            return false;
        }
        if (this.selfClosing != object.selfClosing) {
            return false;
        }
        if (this.formList != object.formList) {
            return false;
        }
        if (this.formSubmit != object.formSubmit) {
            return false;
        }
        if (this.tagName.equals(object.tagName)) return true;
        return false;
    }

    public boolean formatAsBlock() {
        return this.formatAsBlock;
    }

    public String getName() {
        return this.tagName;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n2 = 1;
        int n3 = this.tagName.hashCode();
        int n4 = this.isBlock ? 1 : 0;
        int n5 = this.formatAsBlock ? 1 : 0;
        int n6 = this.canContainBlock ? 1 : 0;
        int n7 = this.canContainInline ? 1 : 0;
        int n8 = this.empty ? 1 : 0;
        int n9 = this.selfClosing ? 1 : 0;
        int n10 = this.preserveWhitespace ? 1 : 0;
        int n11 = this.formList ? 1 : 0;
        if (this.formSubmit) {
            return ((((((((n3 * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31 + n11) * 31 + n2;
        }
        n2 = 0;
        return ((((((((n3 * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31 + n11) * 31 + n2;
    }

    public boolean isBlock() {
        return this.isBlock;
    }

    public boolean isData() {
        if (!this.canContainInline && !this.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public boolean isFormListed() {
        return this.formList;
    }

    public boolean isFormSubmittable() {
        return this.formSubmit;
    }

    public boolean isInline() {
        if (!this.isBlock) {
            return true;
        }
        return false;
    }

    public boolean isKnownTag() {
        return tags.containsKey(this.tagName);
    }

    public boolean isSelfClosing() {
        if (this.empty || this.selfClosing) {
            return true;
        }
        return false;
    }

    public boolean preserveWhitespace() {
        return this.preserveWhitespace;
    }

    Tag setSelfClosing() {
        this.selfClosing = true;
        return this;
    }

    public String toString() {
        return this.tagName;
    }
}

