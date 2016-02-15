package com.google.android.gms.drive.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.events.DriveEvent.Listener;
import com.google.android.gms.drive.internal.C0285w.C0809a;
import com.google.android.gms.internal.fq;

/* renamed from: com.google.android.gms.drive.internal.s */
public class C1314s<C extends DriveEvent> extends C0809a {
    private final int ES;
    private final C0281a<C> FA;
    private final Listener<C> Fz;

    /* renamed from: com.google.android.gms.drive.internal.s.a */
    private static class C0281a<E extends DriveEvent> extends Handler {
        private C0281a(Looper looper) {
            super(looper);
        }

        public void m291a(Listener<E> listener, E e) {
            sendMessage(obtainMessage(1, new Pair(listener, e)));
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Std.STD_FILE /*1*/:
                    Pair pair = (Pair) msg.obj;
                    ((Listener) pair.first).onEvent((DriveEvent) pair.second);
                default:
                    Log.wtf("EventCallback", "Don't know how to handle this event");
            }
        }
    }

    public C1314s(Looper looper, int i, Listener<C> listener) {
        this.ES = i;
        this.Fz = listener;
        this.FA = new C0281a(null);
    }

    public void m2670a(OnEventResponse onEventResponse) throws RemoteException {
        fq.m986x(this.ES == onEventResponse.getEventType());
        switch (onEventResponse.getEventType()) {
            case Std.STD_FILE /*1*/:
                this.FA.m291a(this.Fz, onEventResponse.fL());
            case Std.STD_URL /*2*/:
                this.FA.m291a(this.Fz, onEventResponse.fM());
            default:
                Log.w("EventCallback", "Unexpected event type:" + onEventResponse.getEventType());
        }
    }
}
