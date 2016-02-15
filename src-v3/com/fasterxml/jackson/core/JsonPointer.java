package com.fasterxml.jackson.core;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.core.io.NumberInput;

public class JsonPointer {
    protected static final JsonPointer EMPTY;
    protected final String _asString;
    protected final int _matchingElementIndex;
    protected final String _matchingPropertyName;
    protected final JsonPointer _nextSegment;

    static {
        EMPTY = new JsonPointer();
    }

    protected JsonPointer() {
        this._nextSegment = null;
        this._matchingPropertyName = UnsupportedUrlFragment.DISPLAY_NAME;
        this._matchingElementIndex = -1;
        this._asString = UnsupportedUrlFragment.DISPLAY_NAME;
    }

    protected JsonPointer(String fullString, String segment, JsonPointer next) {
        this._asString = fullString;
        this._nextSegment = next;
        this._matchingPropertyName = segment;
        this._matchingElementIndex = _parseIndex(segment);
    }

    public static JsonPointer compile(String input) throws IllegalArgumentException {
        if (input == null || input.length() == 0) {
            return EMPTY;
        }
        if (input.charAt(0) == '/') {
            return _parseTail(input);
        }
        throw new IllegalArgumentException("Invalid input: JSON Pointer expression must start with '/': \"" + input + "\"");
    }

    public static JsonPointer valueOf(String input) {
        return compile(input);
    }

    public boolean matches() {
        return this._nextSegment == null;
    }

    public String getMatchingProperty() {
        return this._matchingPropertyName;
    }

    public int getMatchingIndex() {
        return this._matchingElementIndex;
    }

    public boolean mayMatchProperty() {
        return this._matchingPropertyName != null;
    }

    public boolean mayMatchElement() {
        return this._matchingElementIndex >= 0;
    }

    public JsonPointer matchProperty(String name) {
        if (this._nextSegment == null || !this._matchingPropertyName.equals(name)) {
            return null;
        }
        return this._nextSegment;
    }

    public JsonPointer matchElement(int index) {
        if (index != this._matchingElementIndex || index < 0) {
            return null;
        }
        return this._nextSegment;
    }

    public JsonPointer tail() {
        return this._nextSegment;
    }

    public String toString() {
        return this._asString;
    }

    public int hashCode() {
        return this._asString.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof JsonPointer)) {
            return false;
        }
        return this._asString.equals(((JsonPointer) o)._asString);
    }

    private static final int _parseIndex(String str) {
        int len = str.length();
        if (len == 0 || len > 10) {
            return -1;
        }
        int i = 0;
        while (i < len) {
            int i2 = i + 1;
            char c = str.charAt(i);
            if (c > '9' || c < '0') {
                return -1;
            }
            i = i2 + 1;
        }
        if (len != 10 || NumberInput.parseLong(str) <= 2147483647L) {
            return NumberInput.parseInt(str);
        }
        return -1;
    }

    protected static JsonPointer _parseTail(String input) {
        int end = input.length();
        int i = 1;
        while (i < end) {
            char c = input.charAt(i);
            if (c == '/') {
                return new JsonPointer(input, input.substring(1, i), _parseTail(input.substring(i)));
            }
            i++;
            if (c == '~' && i < end) {
                return _parseQuotedTail(input, i);
            }
        }
        return new JsonPointer(input, input.substring(1), EMPTY);
    }

    protected static JsonPointer _parseQuotedTail(String input, int i) {
        int end = input.length();
        StringBuilder sb = new StringBuilder(Math.max(16, end));
        if (i > 2) {
            sb.append(input, 1, i - 1);
        }
        int i2 = i + 1;
        _appendEscape(sb, input.charAt(i));
        i = i2;
        while (i < end) {
            char c = input.charAt(i);
            if (c == '/') {
                return new JsonPointer(input, sb.toString(), _parseTail(input.substring(i)));
            }
            i++;
            if (c != '~' || i >= end) {
                sb.append(c);
            } else {
                i2 = i + 1;
                _appendEscape(sb, input.charAt(i));
                i = i2;
            }
        }
        return new JsonPointer(input, sb.toString(), EMPTY);
    }

    private static void _appendEscape(StringBuilder sb, char c) {
        if (c == '0') {
            c = '~';
        } else if (c == '1') {
            c = '/';
        } else {
            sb.append('~');
        }
        sb.append(c);
    }
}
