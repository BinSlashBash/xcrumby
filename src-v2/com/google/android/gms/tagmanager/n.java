/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;
import com.google.android.gms.tagmanager.bh;

class n
implements ContainerHolder {
    private final Looper AS;
    private Container WR;
    private Container WS;
    private b WT;
    private a WU;
    private boolean WV;
    private TagManager WW;
    private Status wJ;

    public n(Status status) {
        this.wJ = status;
        this.AS = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public n(TagManager tagManager, Looper looper, Container container, a a2) {
        this.WW = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.AS = looper;
        this.WR = container;
        this.WU = a2;
        this.wJ = Status.Bv;
        tagManager.a(this);
    }

    private void kf() {
        if (this.WT != null) {
            this.WT.bs(this.WS.kc());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(Container container) {
        synchronized (this) {
            boolean bl2 = this.WV;
            if (!bl2) {
                if (container == null) {
                    bh.w("Unexpected null container.");
                } else {
                    this.WS = container;
                    this.kf();
                }
            }
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void bp(String string2) {
        synchronized (this) {
            block6 : {
                boolean bl2 = this.WV;
                if (!bl2) break block6;
                do {
                    return;
                    break;
                } while (true);
            }
            this.WR.bp(string2);
            return;
        }
    }

    void br(String string2) {
        if (this.WV) {
            bh.w("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
            return;
        }
        this.WU.br(string2);
    }

    @Override
    public Container getContainer() {
        Container container = null;
        synchronized (this) {
            if (this.WV) {
                bh.w("ContainerHolder is released.");
            }
            if (this.WS != null) {
                this.WR = this.WS;
                this.WS = null;
            }
            container = this.WR;
        }
    }

    String getContainerId() {
        if (this.WV) {
            bh.w("getContainerId called on a released ContainerHolder.");
            return "";
        }
        return this.WR.getContainerId();
    }

    @Override
    public Status getStatus() {
        return this.wJ;
    }

    String ke() {
        if (this.WV) {
            bh.w("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
            return "";
        }
        return this.WU.ke();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void refresh() {
        synchronized (this) {
            if (this.WV) {
                bh.w("Refreshing a released ContainerHolder.");
            } else {
                this.WU.kg();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void release() {
        synchronized (this) {
            if (this.WV) {
                bh.w("Releasing a released ContainerHolder.");
            } else {
                this.WV = true;
                this.WW.b(this);
                this.WR.release();
                this.WR = null;
                this.WS = null;
                this.WU = null;
                this.WT = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setContainerAvailableListener(ContainerHolder.ContainerAvailableListener containerAvailableListener) {
        synchronized (this) {
            if (this.WV) {
                bh.w("ContainerHolder is released.");
            } else if (containerAvailableListener == null) {
                this.WT = null;
            } else {
                this.WT = new b(containerAvailableListener, this.AS);
                if (this.WS != null) {
                    this.kf();
                }
            }
            return;
        }
    }

    public static interface a {
        public void br(String var1);

        public String ke();

        public void kg();
    }

    private class b
    extends Handler {
        private final ContainerHolder.ContainerAvailableListener WX;

        public b(ContainerHolder.ContainerAvailableListener containerAvailableListener, Looper looper) {
            super(looper);
            this.WX = containerAvailableListener;
        }

        public void bs(String string2) {
            this.sendMessage(this.obtainMessage(1, (Object)string2));
        }

        protected void bt(String string2) {
            this.WX.onContainerAvailable(n.this, string2);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    bh.w("Don't know how to handle this message.");
                    return;
                }
                case 1: 
            }
            this.bt((String)message.obj);
        }
    }

}

