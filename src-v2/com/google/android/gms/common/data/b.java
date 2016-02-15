/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.net.Uri
 */
package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fq;

public abstract class b {
    protected final DataHolder BB;
    protected final int BD;
    private final int BE;

    /*
     * Enabled aggressive block sorting
     */
    public b(DataHolder dataHolder, int n2) {
        this.BB = fq.f(dataHolder);
        boolean bl2 = n2 >= 0 && n2 < dataHolder.getCount();
        fq.x(bl2);
        this.BD = n2;
        this.BE = dataHolder.G(this.BD);
    }

    protected void a(String string2, CharArrayBuffer charArrayBuffer) {
        this.BB.copyToBuffer(string2, this.BD, this.BE, charArrayBuffer);
    }

    protected Uri ah(String string2) {
        return this.BB.parseUri(string2, this.BD, this.BE);
    }

    protected boolean ai(String string2) {
        return this.BB.hasNull(string2, this.BD, this.BE);
    }

    public boolean equals(Object object) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (object instanceof b) {
            object = (b)object;
            bl3 = bl2;
            if (fo.equal(object.BD, this.BD)) {
                bl3 = bl2;
                if (fo.equal(object.BE, this.BE)) {
                    bl3 = bl2;
                    if (object.BB == this.BB) {
                        bl3 = true;
                    }
                }
            }
        }
        return bl3;
    }

    protected boolean getBoolean(String string2) {
        return this.BB.getBoolean(string2, this.BD, this.BE);
    }

    protected byte[] getByteArray(String string2) {
        return this.BB.getByteArray(string2, this.BD, this.BE);
    }

    protected int getInteger(String string2) {
        return this.BB.getInteger(string2, this.BD, this.BE);
    }

    protected long getLong(String string2) {
        return this.BB.getLong(string2, this.BD, this.BE);
    }

    protected String getString(String string2) {
        return this.BB.getString(string2, this.BD, this.BE);
    }

    public boolean hasColumn(String string2) {
        return this.BB.hasColumn(string2);
    }

    public int hashCode() {
        return fo.hashCode(this.BD, this.BE, this.BB);
    }

    public boolean isDataValid() {
        if (!this.BB.isClosed()) {
            return true;
        }
        return false;
    }
}

