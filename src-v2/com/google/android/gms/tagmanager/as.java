/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.tagmanager.ar;
import com.google.android.gms.tagmanager.at;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.cx;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

class as
extends Thread
implements ar {
    private static as Ya;
    private final LinkedBlockingQueue<Runnable> XZ = new LinkedBlockingQueue();
    private volatile at Yb;
    private volatile boolean mClosed = false;
    private final Context mContext;
    private volatile boolean tx = false;

    /*
     * Enabled aggressive block sorting
     */
    private as(Context context) {
        super("GAThread");
        this.mContext = context != null ? context.getApplicationContext() : context;
        this.start();
    }

    static as H(Context context) {
        if (Ya == null) {
            Ya = new as(context);
        }
        return Ya;
    }

    private String a(Throwable throwable) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        throwable.printStackTrace(printStream);
        printStream.flush();
        return new String(byteArrayOutputStream.toByteArray());
    }

    @Override
    public void a(Runnable runnable) {
        this.XZ.add(runnable);
    }

    void b(final String string2, final long l2) {
        this.a(new Runnable(){

            @Override
            public void run() {
                if (as.this.Yb == null) {
                    cx cx2 = cx.lG();
                    cx2.a(as.this.mContext, this);
                    as.this.Yb = cx2.lH();
                }
                as.this.Yb.e(l2, string2);
            }
        });
    }

    @Override
    public void bC(String string2) {
        this.b(string2, System.currentTimeMillis());
    }

    @Override
    public void run() {
        while (!this.mClosed) {
            try {
                Runnable runnable = this.XZ.take();
                if (this.tx) continue;
                runnable.run();
            }
            catch (InterruptedException var1_2) {
                try {
                    bh.x(var1_2.toString());
                    continue;
                }
                catch (Throwable var1_3) {
                    bh.w("Error on GAThread: " + this.a(var1_3));
                    bh.w("Google Analytics is shutting down.");
                    this.tx = true;
                }
            }
        }
    }

}

