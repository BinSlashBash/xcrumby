package in.uncod.android.bypass;

import java.util.HashMap;
import java.util.Map;
import org.json.zip.JSONzip;

public class Element {
    Map<String, String> attributes;
    Element[] children;
    int nestLevel;
    Element parent;
    String text;
    Type type;

    public enum Type {
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
            TypeValues = values();
        }

        private Type(int value) {
            this.value = value;
        }

        public static Type fromInteger(int x) {
            for (Type type : TypeValues) {
                if (type.value == x) {
                    return type;
                }
            }
            return null;
        }
    }

    public Element(String text, int type) {
        this.attributes = new HashMap();
        this.nestLevel = 0;
        this.text = text;
        this.type = Type.fromInteger(type);
    }

    public void setParent(Element element) {
        this.parent = element;
    }

    public void setChildren(Element[] children) {
        this.children = children;
    }

    public void addAttribute(String name, String value) {
        this.attributes.put(name, value);
    }

    public String getAttribute(String name) {
        return (String) this.attributes.get(name);
    }

    public Element getParent() {
        return this.parent;
    }

    public String getText() {
        return this.text;
    }

    public int size() {
        if (this.children != null) {
            return this.children.length;
        }
        return 0;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isBlockElement() {
        return (this.type.value & JSONzip.end) == 0;
    }

    public boolean isSpanElement() {
        return (this.type.value & JSONzip.end) == JSONzip.end;
    }
}
