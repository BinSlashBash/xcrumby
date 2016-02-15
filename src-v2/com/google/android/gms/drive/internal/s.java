/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 *  android.util.Pair
 */
package com.google.android.gms.drive.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ConflictEvent;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.internal.OnEventResponse;
import com.google.android.gms.drive.internal.w;
import com.google.android.gms.internal.fq;

public class s<C extends DriveEvent>
extends w.a {
    private final int ES;
    private final a<C> FA;
    private final DriveEvent.Listener<C> Fz;

    public s(Looper looper, int n2, DriveEvent.Listener<C> listener) {
        this.ES = n2;
        this.Fz = listener;
        this.FA = new a(looper);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a(OnEventResponse onEventResponse) throws RemoteException {
        boolean bl2 = this.ES == onEventResponse.getEventType();
        fq.x(bl2);
        switch (onEventResponse.getEventType()) {
            default: {
                Log.w((String)"EventCallback", (String)("Unexpected event type:" + onEventResponse.getEventType()));
                return;
            }
            case 1: {
                this.FA.a(this.Fz, (ChangeEvent)onEventResponse.fL());
                return;
            }
            case 2: 
        }
        this.FA.a(this.Fz, (ConflictEvent)onEventResponse.fM());
    }

    private static class a<E extends DriveEvent>
    extends Handler {
        private a(Looper looper) {
            super(looper);
        }

        public void a(DriveEvent.Listener<E> listener, E e2) {
            this.sendMessage(this.obtainMessage(1, (Object)new Pair(listener, e2)));
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    Log.wtf((String)"EventCallback", (String)"Don't know how to handle this event");
                    return;
                }
                case 1: 
            }
            message = (Pair)message.obj;
            ((DriveEvent.Listener)message.first).onEvent((DriveEvent)message.second);
        }
    }

}

