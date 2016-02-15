package com.google.android.gms.internal;

/* renamed from: com.google.android.gms.internal.do */
public abstract class C0359do {
    private final Runnable kW;
    private volatile Thread qY;

    /* renamed from: com.google.android.gms.internal.do.1 */
    class C03581 implements Runnable {
        final /* synthetic */ C0359do qZ;

        C03581(C0359do c0359do) {
            this.qZ = c0359do;
        }

        public final void run() {
            this.qZ.qY = Thread.currentThread();
            this.qZ.aY();
        }
    }

    public C0359do() {
        this.kW = new C03581(this);
    }

    public abstract void aY();

    public final void cancel() {
        onStop();
        if (this.qY != null) {
            this.qY.interrupt();
        }
    }

    public abstract void onStop();

    public final void start() {
        dp.execute(this.kW);
    }
}
