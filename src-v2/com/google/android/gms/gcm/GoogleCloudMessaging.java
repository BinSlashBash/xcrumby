/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcelable
 */
package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class GoogleCloudMessaging {
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String MESSAGE_TYPE_DELETED = "deleted_messages";
    public static final String MESSAGE_TYPE_MESSAGE = "gcm";
    public static final String MESSAGE_TYPE_SEND_ERROR = "send_error";
    static GoogleCloudMessaging Nq;
    private PendingIntent Nr;
    final BlockingQueue<Intent> Ns = new LinkedBlockingQueue<Intent>();
    private Handler Nt;
    private Messenger Nu;
    private Context kI;

    public GoogleCloudMessaging() {
        this.Nt = new Handler(Looper.getMainLooper()){

            public void handleMessage(Message message) {
                message = (Intent)message.obj;
                GoogleCloudMessaging.this.Ns.add((Intent)message);
            }
        };
        this.Nu = new Messenger(this.Nt);
    }

    private void a(String string2, String string3, long l2, int n2, Bundle bundle) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        if (string2 == null) {
            throw new IllegalArgumentException("Missing 'to'");
        }
        Intent intent = new Intent("com.google.android.gcm.intent.SEND");
        intent.putExtras(bundle);
        this.c(intent);
        intent.setPackage("com.google.android.gms");
        intent.putExtra("google.to", string2);
        intent.putExtra("google.message_id", string3);
        intent.putExtra("google.ttl", Long.toString(l2));
        intent.putExtra("google.delay", Integer.toString(n2));
        this.kI.sendOrderedBroadcast(intent, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
    }

    private /* varargs */ void c(String ... object) {
        object = this.d((String[])object);
        Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
        intent.setPackage("com.google.android.gms");
        intent.putExtra("google.messenger", (Parcelable)this.Nu);
        this.c(intent);
        intent.putExtra("sender", (String)object);
        this.kI.startService(intent);
    }

    public static GoogleCloudMessaging getInstance(Context object) {
        synchronized (GoogleCloudMessaging.class) {
            if (Nq == null) {
                Nq = new GoogleCloudMessaging();
                GoogleCloudMessaging.Nq.kI = object;
            }
            object = Nq;
            return object;
        }
    }

    private void hL() {
        Intent intent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
        intent.setPackage("com.google.android.gms");
        this.Ns.clear();
        intent.putExtra("google.messenger", (Parcelable)this.Nu);
        this.c(intent);
        this.kI.startService(intent);
    }

    void c(Intent intent) {
        synchronized (this) {
            if (this.Nr == null) {
                Intent intent2 = new Intent();
                intent2.setPackage("com.google.example.invalidpackage");
                this.Nr = PendingIntent.getBroadcast((Context)this.kI, (int)0, (Intent)intent2, (int)0);
            }
            intent.putExtra("app", (Parcelable)this.Nr);
            return;
        }
    }

    public void close() {
        this.hM();
    }

    /* varargs */ String d(String ... arrstring) {
        if (arrstring == null || arrstring.length == 0) {
            throw new IllegalArgumentException("No senderIds");
        }
        StringBuilder stringBuilder = new StringBuilder(arrstring[0]);
        for (int i2 = 1; i2 < arrstring.length; ++i2) {
            stringBuilder.append(',').append(arrstring[i2]);
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getMessageType(Intent object) {
        String string2;
        if (!"com.google.android.c2dm.intent.RECEIVE".equals(object.getAction())) {
            return null;
        }
        object = string2 = object.getStringExtra("message_type");
        if (string2 != null) return object;
        return "gcm";
    }

    void hM() {
        synchronized (this) {
            if (this.Nr != null) {
                this.Nr.cancel();
                this.Nr = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* varargs */ String register(String ... object) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        this.Ns.clear();
        this.c((String[])object);
        try {
            object = this.Ns.poll(5000, TimeUnit.MILLISECONDS);
            if (object == null) {
                throw new IOException("SERVICE_NOT_AVAILABLE");
            }
            String string2 = object.getStringExtra("registration_id");
            if (string2 != null) {
                return string2;
            }
        }
        catch (InterruptedException var1_2) {
            throw new IOException(var1_2.getMessage());
        }
        object.getStringExtra("error");
        object = object.getStringExtra("error");
        if (object != null) {
            throw new IOException((String)object);
        }
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    public void send(String string2, String string3, long l2, Bundle bundle) throws IOException {
        this.a(string2, string3, l2, -1, bundle);
    }

    public void send(String string2, String string3, Bundle bundle) throws IOException {
        this.send(string2, string3, -1, bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregister() throws IOException {
        Object object;
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        this.hL();
        try {
            object = this.Ns.poll(5000, TimeUnit.MILLISECONDS);
            if (object == null) {
                throw new IOException("SERVICE_NOT_AVAILABLE");
            }
            if (object.getStringExtra("unregistered") != null) {
                return;
            }
        }
        catch (InterruptedException var1_2) {
            throw new IOException(var1_2.getMessage());
        }
        if ((object = object.getStringExtra("error")) != null) {
            throw new IOException((String)object);
        }
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

}

