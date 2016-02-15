package com.google.android.gms.tagmanager;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

class as extends Thread implements ar {
    private static as Ya;
    private final LinkedBlockingQueue<Runnable> XZ;
    private volatile at Yb;
    private volatile boolean mClosed;
    private final Context mContext;
    private volatile boolean tx;

    /* renamed from: com.google.android.gms.tagmanager.as.1 */
    class C04951 implements Runnable {
        final /* synthetic */ ar Yc;
        final /* synthetic */ long Yd;
        final /* synthetic */ String Ye;
        final /* synthetic */ as Yf;

        C04951(as asVar, ar arVar, long j, String str) {
            this.Yf = asVar;
            this.Yc = arVar;
            this.Yd = j;
            this.Ye = str;
        }

        public void run() {
            if (this.Yf.Yb == null) {
                cx lG = cx.lG();
                lG.m2513a(this.Yf.mContext, this.Yc);
                this.Yf.Yb = lG.lH();
            }
            this.Yf.Yb.m1370e(this.Yd, this.Ye);
        }
    }

    private as(Context context) {
        super("GAThread");
        this.XZ = new LinkedBlockingQueue();
        this.tx = false;
        this.mClosed = false;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        start();
    }

    static as m2450H(Context context) {
        if (Ya == null) {
            Ya = new as(context);
        }
        return Ya;
    }

    private String m2453a(Throwable th) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        th.printStackTrace(printStream);
        printStream.flush();
        return new String(byteArrayOutputStream.toByteArray());
    }

    public void m2455a(Runnable runnable) {
        this.XZ.add(runnable);
    }

    void m2456b(String str, long j) {
        m2455a(new C04951(this, this, j, str));
    }

    public void bC(String str) {
        m2456b(str, System.currentTimeMillis());
    }

    public void run() {
        while (!this.mClosed) {
            try {
                Runnable runnable = (Runnable) this.XZ.take();
                if (!this.tx) {
                    runnable.run();
                }
            } catch (InterruptedException e) {
                bh.m1385x(e.toString());
            } catch (Throwable th) {
                bh.m1384w("Error on GAThread: " + m2453a(th));
                bh.m1384w("Google Analytics is shutting down.");
                this.tx = true;
            }
        }
    }
}
