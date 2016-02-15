/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;

public final class Challenge {
    private final String realm;
    private final String scheme;

    public Challenge(String string2, String string3) {
        this.scheme = string2;
        this.realm = string3;
    }

    public boolean equals(Object object) {
        if (object instanceof Challenge && Util.equal(this.scheme, ((Challenge)object).scheme) && Util.equal(this.realm, ((Challenge)object).realm)) {
            return true;
        }
        return false;
    }

    public String getRealm() {
        return this.realm;
    }

    public String getScheme() {
        return this.scheme;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n2 = 0;
        int n3 = this.realm != null ? this.realm.hashCode() : 0;
        if (this.scheme != null) {
            n2 = this.scheme.hashCode();
        }
        return (n3 + 899) * 31 + n2;
    }

    public String toString() {
        return this.scheme + " realm=\"" + this.realm + "\"";
    }
}

