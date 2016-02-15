/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ServiceConnection
 *  android.os.IBinder
 */
package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class a
implements ServiceConnection {
    boolean Ae = false;
    private final BlockingQueue<IBinder> Af = new LinkedBlockingQueue<IBinder>();

    public IBinder dV() throws InterruptedException {
        if (this.Ae) {
            throw new IllegalStateException();
        }
        this.Ae = true;
        return this.Af.take();
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            this.Af.put(iBinder);
            return;
        }
        catch (InterruptedException var1_2) {
            return;
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
    }
}

