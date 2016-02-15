/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.fq;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class fo {
    public static a e(Object object) {
        return new a(object);
    }

    public static boolean equal(Object object, Object object2) {
        if (object == object2 || object != null && object.equals(object2)) {
            return true;
        }
        return false;
    }

    public static /* varargs */ int hashCode(Object ... arrobject) {
        return Arrays.hashCode(arrobject);
    }

    public static final class a {
        private final List<String> DI;
        private final Object DJ;

        private a(Object object) {
            this.DJ = fq.f(object);
            this.DI = new ArrayList<String>();
        }

        public a a(String string2, Object object) {
            this.DI.add(fq.f(string2) + "=" + String.valueOf(object));
            return this;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(100).append(this.DJ.getClass().getSimpleName()).append('{');
            int n2 = this.DI.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                stringBuilder.append(this.DI.get(i2));
                if (i2 >= n2 - 1) continue;
                stringBuilder.append(", ");
            }
            return stringBuilder.append('}').toString();
        }
    }

}

