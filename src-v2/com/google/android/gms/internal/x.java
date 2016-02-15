/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package com.google.android.gms.internal;

import android.os.Handler;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.dv;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.v;
import java.lang.ref.WeakReference;

public final class x {
    private final a kV;
    private final Runnable kW;
    private ah kX;
    private boolean kY = false;
    private boolean kZ = false;
    private long la = 0;

    public x(v v2) {
        this(v2, new a(dv.rp));
    }

    x(final v v2, a a2) {
        this.kV = a2;
        this.kW = new Runnable(){
            private final WeakReference<v> lb;

            @Override
            public void run() {
                x.this.kY = false;
                v v22 = this.lb.get();
                if (v22 != null) {
                    v22.b(x.this.kX);
                }
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a(ah ah2, long l2) {
        if (this.kY) {
            dw.z("An ad refresh is already scheduled.");
            return;
        } else {
            this.kX = ah2;
            this.kY = true;
            this.la = l2;
            if (this.kZ) return;
            {
                dw.x("Scheduling ad refresh " + l2 + " milliseconds from now.");
                this.kV.postDelayed(this.kW, l2);
                return;
            }
        }
    }

    public void cancel() {
        this.kY = false;
        this.kV.removeCallbacks(this.kW);
    }

    public void d(ah ah2) {
        this.a(ah2, 60000);
    }

    public void pause() {
        this.kZ = true;
        if (this.kY) {
            this.kV.removeCallbacks(this.kW);
        }
    }

    public void resume() {
        this.kZ = false;
        if (this.kY) {
            this.kY = false;
            this.a(this.kX, this.la);
        }
    }

    public static class a {
        private final Handler mHandler;

        public a(Handler handler) {
            this.mHandler = handler;
        }

        public boolean postDelayed(Runnable runnable, long l2) {
            return this.mHandler.postDelayed(runnable, l2);
        }

        public void removeCallbacks(Runnable runnable) {
            this.mHandler.removeCallbacks(runnable);
        }
    }

}

