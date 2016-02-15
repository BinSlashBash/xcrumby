/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.fc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class Api<O extends ApiOptions> {
    private final ArrayList<Scope> AA;
    private final b<?, O> Ay;
    private final c<?> Az;

    public /* varargs */ <C extends a> Api(b<C, O> b2, c<C> c2, Scope ... arrscope) {
        this.Ay = b2;
        this.Az = c2;
        this.AA = new ArrayList<Scope>(Arrays.asList(arrscope));
    }

    public b<?, O> dY() {
        return this.Ay;
    }

    public List<Scope> dZ() {
        return this.AA;
    }

    public c<?> ea() {
        return this.Az;
    }

    public static interface ApiOptions {

        public static interface HasOptions
        extends ApiOptions {
        }

        public static final class NoOptions
        implements NotRequiredOptions {
            private NoOptions() {
            }
        }

        public static interface NotRequiredOptions
        extends ApiOptions {
        }

        public static interface Optional
        extends HasOptions,
        NotRequiredOptions {
        }

    }

    public static interface a {
        public void connect();

        public void disconnect();

        public Looper getLooper();

        public boolean isConnected();
    }

    public static interface b<T extends a, O> {
        public T a(Context var1, Looper var2, fc var3, O var4, GoogleApiClient.ConnectionCallbacks var5, GoogleApiClient.OnConnectionFailedListener var6);

        public int getPriority();
    }

    public static final class c<C extends a> {
    }

}

