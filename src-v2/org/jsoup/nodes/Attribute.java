/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.nodes;

import java.util.Map;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;

public class Attribute
implements Map.Entry<String, String>,
Cloneable {
    private String key;
    private String value;

    public Attribute(String string2, String string3) {
        Validate.notEmpty(string2);
        Validate.notNull(string3);
        this.key = string2.trim().toLowerCase();
        this.value = string3;
    }

    public static Attribute createFromEncoded(String string2, String string3) {
        return new Attribute(string2, Entities.unescape(string3, true));
    }

    public Attribute clone() {
        try {
            Attribute attribute = (Attribute)super.clone();
            return attribute;
        }
        catch (CloneNotSupportedException var1_2) {
            throw new RuntimeException(var1_2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Attribute)) {
            return false;
        }
        object = (Attribute)object;
        if (this.key != null) {
            if (!this.key.equals(object.key)) {
                return false;
            }
        } else if (object.key != null) return false;
        if (this.value != null) {
            if (this.value.equals(object.value)) return true;
            return false;
        }
        if (object.value == null) return true;
        return false;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int hashCode() {
        int n2 = 0;
        int n3 = this.key != null ? this.key.hashCode() : 0;
        if (this.value != null) {
            n2 = this.value.hashCode();
        }
        return n3 * 31 + n2;
    }

    public String html() {
        return this.key + "=\"" + Entities.escape(this.value, new Document("").outputSettings()) + "\"";
    }

    protected void html(StringBuilder stringBuilder, Document.OutputSettings outputSettings) {
        stringBuilder.append(this.key).append("=\"").append(Entities.escape(this.value, outputSettings)).append("\"");
    }

    protected boolean isDataAttribute() {
        if (this.key.startsWith("data-") && this.key.length() > "data-".length()) {
            return true;
        }
        return false;
    }

    public void setKey(String string2) {
        Validate.notEmpty(string2);
        this.key = string2.trim().toLowerCase();
    }

    @Override
    public String setValue(String string2) {
        Validate.notNull(string2);
        String string3 = this.value;
        this.value = string2;
        return string3;
    }

    public String toString() {
        return this.html();
    }
}

