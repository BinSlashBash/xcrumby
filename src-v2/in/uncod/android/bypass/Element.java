/*
 * Decompiled with CFR 0_110.
 */
package in.uncod.android.bypass;

import java.util.HashMap;
import java.util.Map;

public class Element {
    Map<String, String> attributes = new HashMap<String, String>();
    Element[] children;
    int nestLevel = 0;
    Element parent;
    String text;
    Type type;

    public Element(String string2, int n2) {
        this.text = string2;
        this.type = Type.fromInteger(n2);
    }

    public void addAttribute(String string2, String string3) {
        this.attributes.put(string2, string3);
    }

    public String getAttribute(String string2) {
        return this.attributes.get(string2);
    }

    public Element getParent() {
        return this.parent;
    }

    public String getText() {
        return this.text;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isBlockElement() {
        if ((this.type.value & 256) == 0) {
            return true;
        }
        return false;
    }

    public boolean isSpanElement() {
        if ((this.type.value & 256) == 256) {
            return true;
        }
        return false;
    }

    public void setChildren(Element[] arrelement) {
        this.children = arrelement;
    }

    public void setParent(Element element) {
        this.parent = element;
    }

    public int size() {
        if (this.children != null) {
            return this.children.length;
        }
        return 0;
    }

    public static enum Type {
        BLOCK_CODE(0),
        BLOCK_QUOTE(1),
        BLOCK_HTML(2),
        HEADER(3),
        HRULE(4),
        LIST(5),
        LIST_ITEM(6),
        PARAGRAPH(7),
        TABLE(8),
        TABLE_CELL(9),
        TABLE_ROW(10),
        AUTOLINK(267),
        CODE_SPAN(268),
        DOUBLE_EMPHASIS(269),
        EMPHASIS(270),
        IMAGE(271),
        LINEBREAK(272),
        LINK(273),
        RAW_HTML_TAG(274),
        TRIPLE_EMPHASIS(275),
        TEXT(276);
        
        private static final Type[] TypeValues;
        private final int value;

        static {
            TypeValues = Type.values();
        }

        private Type(int n3) {
            this.value = n3;
        }

        public static Type fromInteger(int n2) {
            for (Type type : TypeValues) {
                if (type.value != n2) continue;
                return type;
            }
            return null;
        }
    }

}

