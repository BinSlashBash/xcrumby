package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class fo {

    /* renamed from: com.google.android.gms.internal.fo.a */
    public static final class C0398a {
        private final List<String> DI;
        private final Object DJ;

        private C0398a(Object obj) {
            this.DJ = fq.m985f(obj);
            this.DI = new ArrayList();
        }

        public C0398a m975a(String str, Object obj) {
            this.DI.add(((String) fq.m985f(str)) + "=" + String.valueOf(obj));
            return this;
        }

        public String toString() {
            StringBuilder append = new StringBuilder(100).append(this.DJ.getClass().getSimpleName()).append('{');
            int size = this.DI.size();
            for (int i = 0; i < size; i++) {
                append.append((String) this.DI.get(i));
                if (i < size - 1) {
                    append.append(", ");
                }
            }
            return append.append('}').toString();
        }
    }

    public static C0398a m976e(Object obj) {
        return new C0398a(null);
    }

    public static boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public static int hashCode(Object... objects) {
        return Arrays.hashCode(objects);
    }
}
