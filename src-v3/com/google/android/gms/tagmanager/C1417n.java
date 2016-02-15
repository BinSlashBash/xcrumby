package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tagmanager.ContainerHolder.ContainerAvailableListener;

/* renamed from: com.google.android.gms.tagmanager.n */
class C1417n implements ContainerHolder {
    private final Looper AS;
    private Container WR;
    private Container WS;
    private C0527b WT;
    private C0526a WU;
    private boolean WV;
    private TagManager WW;
    private Status wJ;

    /* renamed from: com.google.android.gms.tagmanager.n.a */
    public interface C0526a {
        void br(String str);

        String ke();

        void kg();
    }

    /* renamed from: com.google.android.gms.tagmanager.n.b */
    private class C0527b extends Handler {
        private final ContainerAvailableListener WX;
        final /* synthetic */ C1417n WY;

        public C0527b(C1417n c1417n, ContainerAvailableListener containerAvailableListener, Looper looper) {
            this.WY = c1417n;
            super(looper);
            this.WX = containerAvailableListener;
        }

        public void bs(String str) {
            sendMessage(obtainMessage(1, str));
        }

        protected void bt(String str) {
            this.WX.onContainerAvailable(this.WY, str);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Std.STD_FILE /*1*/:
                    bt((String) msg.obj);
                default:
                    bh.m1384w("Don't know how to handle this message.");
            }
        }
    }

    public C1417n(Status status) {
        this.wJ = status;
        this.AS = null;
    }

    public C1417n(TagManager tagManager, Looper looper, Container container, C0526a c0526a) {
        this.WW = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.AS = looper;
        this.WR = container;
        this.WU = c0526a;
        this.wJ = Status.Bv;
        tagManager.m1353a(this);
    }

    private void kf() {
        if (this.WT != null) {
            this.WT.bs(this.WS.kc());
        }
    }

    public synchronized void m3227a(Container container) {
        if (!this.WV) {
            if (container == null) {
                bh.m1384w("Unexpected null container.");
            } else {
                this.WS = container;
                kf();
            }
        }
    }

    public synchronized void bp(String str) {
        if (!this.WV) {
            this.WR.bp(str);
        }
    }

    void br(String str) {
        if (this.WV) {
            bh.m1384w("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        } else {
            this.WU.br(str);
        }
    }

    public synchronized Container getContainer() {
        Container container = null;
        synchronized (this) {
            if (this.WV) {
                bh.m1384w("ContainerHolder is released.");
            } else {
                if (this.WS != null) {
                    this.WR = this.WS;
                    this.WS = null;
                }
                container = this.WR;
            }
        }
        return container;
    }

    String getContainerId() {
        if (!this.WV) {
            return this.WR.getContainerId();
        }
        bh.m1384w("getContainerId called on a released ContainerHolder.");
        return UnsupportedUrlFragment.DISPLAY_NAME;
    }

    public Status getStatus() {
        return this.wJ;
    }

    String ke() {
        if (!this.WV) {
            return this.WU.ke();
        }
        bh.m1384w("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        return UnsupportedUrlFragment.DISPLAY_NAME;
    }

    public synchronized void refresh() {
        if (this.WV) {
            bh.m1384w("Refreshing a released ContainerHolder.");
        } else {
            this.WU.kg();
        }
    }

    public synchronized void release() {
        if (this.WV) {
            bh.m1384w("Releasing a released ContainerHolder.");
        } else {
            this.WV = true;
            this.WW.m1354b(this);
            this.WR.release();
            this.WR = null;
            this.WS = null;
            this.WU = null;
            this.WT = null;
        }
    }

    public synchronized void setContainerAvailableListener(ContainerAvailableListener listener) {
        if (this.WV) {
            bh.m1384w("ContainerHolder is released.");
        } else if (listener == null) {
            this.WT = null;
        } else {
            this.WT = new C0527b(this, listener, this.AS);
            if (this.WS != null) {
                kf();
            }
        }
    }
}
