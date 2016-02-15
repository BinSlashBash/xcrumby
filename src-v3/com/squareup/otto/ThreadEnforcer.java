package com.squareup.otto;

import android.os.Looper;

public interface ThreadEnforcer {
    public static final ThreadEnforcer ANY;
    public static final ThreadEnforcer MAIN;

    /* renamed from: com.squareup.otto.ThreadEnforcer.1 */
    static class C11641 implements ThreadEnforcer {
        C11641() {
        }

        public void enforce(Bus bus) {
        }
    }

    /* renamed from: com.squareup.otto.ThreadEnforcer.2 */
    static class C11652 implements ThreadEnforcer {
        C11652() {
        }

        public void enforce(Bus bus) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                throw new IllegalStateException("Event bus " + bus + " accessed from non-main thread " + Looper.myLooper());
            }
        }
    }

    void enforce(Bus bus);

    static {
        ANY = new C11641();
        MAIN = new C11652();
    }
}
