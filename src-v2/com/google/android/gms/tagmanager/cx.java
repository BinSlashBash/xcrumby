/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.tagmanager.ar;
import com.google.android.gms.tagmanager.at;
import com.google.android.gms.tagmanager.au;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.bn;
import com.google.android.gms.tagmanager.ca;
import com.google.android.gms.tagmanager.cw;

class cx
extends cw {
    private static cx aam;
    private static final Object sF;
    private Context aac;
    private at aad;
    private volatile ar aae;
    private int aaf = 1800000;
    private boolean aag = true;
    private boolean aah = false;
    private boolean aai = true;
    private au aaj;
    private bn aak;
    private boolean aal;
    private boolean connected = true;
    private Handler handler;

    static {
        sF = new Object();
    }

    private cx() {
        this.aaj = new au(){

            @Override
            public void r(boolean bl2) {
                cx.this.a(bl2, cx.this.connected);
            }
        };
        this.aal = false;
    }

    private void cj() {
        this.aak = new bn(this);
        this.aak.o(this.aac);
    }

    private void ck() {
        this.handler = new Handler(this.aac.getMainLooper(), new Handler.Callback(){

            public boolean handleMessage(Message message) {
                if (1 == message.what && sF.equals(message.obj)) {
                    cx.this.bW();
                    if (cx.this.aaf > 0 && !cx.this.aal) {
                        cx.this.handler.sendMessageDelayed(cx.this.handler.obtainMessage(1, sF), (long)cx.this.aaf);
                    }
                }
                return true;
            }
        });
        if (this.aaf > 0) {
            this.handler.sendMessageDelayed(this.handler.obtainMessage(1, sF), (long)this.aaf);
        }
    }

    public static cx lG() {
        if (aam == null) {
            aam = new cx();
        }
        return aam;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void a(Context context, ar ar2) {
        synchronized (this) {
            Context context2 = this.aac;
            if (context2 == null) {
                this.aac = context.getApplicationContext();
                if (this.aae == null) {
                    void var2_2;
                    this.aae = var2_2;
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void a(boolean bl2, boolean bl3) {
        synchronized (this) {
            boolean bl4;
            if (this.aal != bl2 || (bl4 = this.connected) != bl3) {
                if ((bl2 || !bl3) && this.aaf > 0) {
                    this.handler.removeMessages(1, sF);
                }
                if (!bl2 && bl3 && this.aaf > 0) {
                    this.handler.sendMessageDelayed(this.handler.obtainMessage(1, sF), (long)this.aaf);
                }
                StringBuilder stringBuilder = new StringBuilder().append("PowerSaveMode ");
                String string2 = !bl2 && bl3 ? "terminated." : "initiated.";
                bh.y(stringBuilder.append(string2).toString());
                this.aal = bl2;
                this.connected = bl3;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void bW() {
        synchronized (this) {
            if (!this.aah) {
                bh.y("Dispatch call queued. Dispatch will run once initialization is complete.");
                this.aag = true;
            } else {
                this.aae.a(new Runnable(){

                    @Override
                    public void run() {
                        cx.this.aad.bW();
                    }
                });
            }
            return;
        }
    }

    @Override
    void cm() {
        synchronized (this) {
            if (!this.aal && this.connected && this.aaf > 0) {
                this.handler.removeMessages(1, sF);
                this.handler.sendMessage(this.handler.obtainMessage(1, sF));
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    at lH() {
        synchronized (this) {
            if (this.aad == null) {
                if (this.aac == null) {
                    throw new IllegalStateException("Cant get a store unless we have a context");
                }
                this.aad = new ca(this.aaj, this.aac);
            }
            if (this.handler == null) {
                this.ck();
            }
            this.aah = true;
            if (this.aag) {
                this.bW();
                this.aag = false;
            }
            if (this.aak != null) return this.aad;
            if (!this.aai) return this.aad;
            this.cj();
            return this.aad;
        }
    }

    @Override
    void s(boolean bl2) {
        synchronized (this) {
            this.a(this.aal, bl2);
            return;
        }
    }

}

