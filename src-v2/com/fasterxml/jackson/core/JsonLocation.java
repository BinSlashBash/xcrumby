/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core;

import java.io.Serializable;

public class JsonLocation
implements Serializable {
    public static final JsonLocation NA = new JsonLocation("N/A", -1, -1, -1, -1);
    private static final long serialVersionUID = 1;
    final int _columnNr;
    final int _lineNr;
    final transient Object _sourceRef;
    final long _totalBytes;
    final long _totalChars;

    public JsonLocation(Object object, long l2, int n2, int n3) {
        this(object, -1, l2, n2, n3);
    }

    public JsonLocation(Object object, long l2, long l3, int n2, int n3) {
        this._sourceRef = object;
        this._totalBytes = l2;
        this._totalChars = l3;
        this._lineNr = n2;
        this._columnNr = n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl2 = true;
        boolean bl3 = false;
        if (object == this) {
            return true;
        }
        boolean bl4 = bl3;
        if (object == null) return bl4;
        bl4 = bl3;
        if (!(object instanceof JsonLocation)) return bl4;
        object = (JsonLocation)object;
        if (this._sourceRef == null) {
            bl4 = bl3;
            if (object._sourceRef != null) return bl4;
        } else if (!this._sourceRef.equals(object._sourceRef)) {
            return false;
        }
        if (this._lineNr != object._lineNr) return false;
        if (this._columnNr != object._columnNr) return false;
        if (this._totalChars != object._totalChars) return false;
        if (this.getByteOffset() != object.getByteOffset()) return false;
        return bl2;
    }

    public long getByteOffset() {
        return this._totalBytes;
    }

    public long getCharOffset() {
        return this._totalChars;
    }

    public int getColumnNr() {
        return this._columnNr;
    }

    public int getLineNr() {
        return this._lineNr;
    }

    public Object getSourceRef() {
        return this._sourceRef;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n2;
        if (this._sourceRef == null) {
            n2 = 1;
            do {
                return ((n2 ^ this._lineNr) + this._columnNr ^ (int)this._totalChars) + (int)this._totalBytes;
                break;
            } while (true);
        }
        n2 = this._sourceRef.hashCode();
        return ((n2 ^ this._lineNr) + this._columnNr ^ (int)this._totalChars) + (int)this._totalBytes;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(80);
        stringBuilder.append("[Source: ");
        if (this._sourceRef == null) {
            stringBuilder.append("UNKNOWN");
        } else {
            stringBuilder.append(this._sourceRef.toString());
        }
        stringBuilder.append("; line: ");
        stringBuilder.append(this._lineNr);
        stringBuilder.append(", column: ");
        stringBuilder.append(this._columnNr);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

