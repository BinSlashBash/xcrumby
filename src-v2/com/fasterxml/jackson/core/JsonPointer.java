/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.NumberInput;

public class JsonPointer {
    protected static final JsonPointer EMPTY = new JsonPointer();
    protected final String _asString;
    protected final int _matchingElementIndex;
    protected final String _matchingPropertyName;
    protected final JsonPointer _nextSegment;

    protected JsonPointer() {
        this._nextSegment = null;
        this._matchingPropertyName = "";
        this._matchingElementIndex = -1;
        this._asString = "";
    }

    protected JsonPointer(String string2, String string3, JsonPointer jsonPointer) {
        this._asString = string2;
        this._nextSegment = jsonPointer;
        this._matchingPropertyName = string3;
        this._matchingElementIndex = JsonPointer._parseIndex(string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void _appendEscape(StringBuilder stringBuilder, char c2) {
        if (c2 == '0') {
            c2 = (char)126;
        } else if (c2 == '1') {
            c2 = (char)47;
        } else {
            stringBuilder.append('~');
        }
        stringBuilder.append(c2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final int _parseIndex(String string2) {
        int n2 = string2.length();
        if (n2 == 0 || n2 > 10) return -1;
        int n3 = 0;
        while (n3 < n2) {
            char c2 = string2.charAt(n3);
            if (c2 > '9' || c2 < '0') return -1;
            {
                n3 = n3 + 1 + 1;
                continue;
            }
        }
        if (n2 != 10 || NumberInput.parseLong(string2) <= Integer.MAX_VALUE) return NumberInput.parseInt(string2);
        return -1;
    }

    protected static JsonPointer _parseQuotedTail(String string2, int n2) {
        int n3 = string2.length();
        StringBuilder stringBuilder = new StringBuilder(Math.max(16, n3));
        if (n2 > 2) {
            stringBuilder.append(string2, 1, n2 - 1);
        }
        JsonPointer._appendEscape(stringBuilder, string2.charAt(n2));
        ++n2;
        while (n2 < n3) {
            char c2 = string2.charAt(n2);
            if (c2 == '/') {
                return new JsonPointer(string2, stringBuilder.toString(), JsonPointer._parseTail(string2.substring(n2)));
            }
            if (c2 == '~' && ++n2 < n3) {
                JsonPointer._appendEscape(stringBuilder, string2.charAt(n2));
                ++n2;
                continue;
            }
            stringBuilder.append(c2);
        }
        return new JsonPointer(string2, stringBuilder.toString(), EMPTY);
    }

    protected static JsonPointer _parseTail(String string2) {
        int n2 = string2.length();
        int n3 = 1;
        while (n3 < n2) {
            int n4;
            char c2 = string2.charAt(n3);
            if (c2 == '/') {
                return new JsonPointer(string2, string2.substring(1, n3), JsonPointer._parseTail(string2.substring(n3)));
            }
            n3 = n4 = n3 + 1;
            if (c2 != '~') continue;
            n3 = n4;
            if (n4 >= n2) continue;
            return JsonPointer._parseQuotedTail(string2, n4);
        }
        return new JsonPointer(string2, string2.substring(1), EMPTY);
    }

    public static JsonPointer compile(String string2) throws IllegalArgumentException {
        if (string2 == null || string2.length() == 0) {
            return EMPTY;
        }
        if (string2.charAt(0) != '/') {
            throw new IllegalArgumentException("Invalid input: JSON Pointer expression must start with '/': \"" + string2 + "\"");
        }
        return JsonPointer._parseTail(string2);
    }

    public static JsonPointer valueOf(String string2) {
        return JsonPointer.compile(string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object == this) {
            return true;
        }
        boolean bl3 = bl2;
        if (object == null) return bl3;
        bl3 = bl2;
        if (!(object instanceof JsonPointer)) return bl3;
        return this._asString.equals(((JsonPointer)object)._asString);
    }

    public int getMatchingIndex() {
        return this._matchingElementIndex;
    }

    public String getMatchingProperty() {
        return this._matchingPropertyName;
    }

    public int hashCode() {
        return this._asString.hashCode();
    }

    public JsonPointer matchElement(int n2) {
        if (n2 != this._matchingElementIndex || n2 < 0) {
            return null;
        }
        return this._nextSegment;
    }

    public JsonPointer matchProperty(String string2) {
        if (this._nextSegment == null || !this._matchingPropertyName.equals(string2)) {
            return null;
        }
        return this._nextSegment;
    }

    public boolean matches() {
        if (this._nextSegment == null) {
            return true;
        }
        return false;
    }

    public boolean mayMatchElement() {
        if (this._matchingElementIndex >= 0) {
            return true;
        }
        return false;
    }

    public boolean mayMatchProperty() {
        if (this._matchingPropertyName != null) {
            return true;
        }
        return false;
    }

    public JsonPointer tail() {
        return this._nextSegment;
    }

    public String toString() {
        return this._asString;
    }
}

