/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.view.View
 */
package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.api.b;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface GoogleApiClient {
    public <C extends Api.a> C a(Api.c<C> var1);

    public <A extends Api.a, T extends a.b<? extends Result, A>> T a(T var1);

    public <A extends Api.a, T extends a.b<? extends Result, A>> T b(T var1);

    public ConnectionResult blockingConnect(long var1, TimeUnit var3);

    public void connect();

    public void disconnect();

    public Looper getLooper();

    public boolean isConnected();

    public boolean isConnecting();

    public boolean isConnectionCallbacksRegistered(ConnectionCallbacks var1);

    public boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener var1);

    public void reconnect();

    public void registerConnectionCallbacks(ConnectionCallbacks var1);

    public void registerConnectionFailedListener(OnConnectionFailedListener var1);

    public void unregisterConnectionCallbacks(ConnectionCallbacks var1);

    public void unregisterConnectionFailedListener(OnConnectionFailedListener var1);

    public static final class Builder {
        private Looper AS;
        private final Set<String> AT = new HashSet<String>();
        private int AU;
        private View AV;
        private String AW;
        private final Map<Api<?>, Api.ApiOptions> AX = new HashMap();
        private final Set<ConnectionCallbacks> AY = new HashSet<ConnectionCallbacks>();
        private final Set<OnConnectionFailedListener> AZ = new HashSet<OnConnectionFailedListener>();
        private final Context mContext;
        private String wG;

        public Builder(Context context) {
            this.mContext = context;
            this.AS = context.getMainLooper();
            this.AW = context.getPackageName();
        }

        public Builder(Context context, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            this(context);
            fq.b(connectionCallbacks, (Object)"Must provide a connected listener");
            this.AY.add(connectionCallbacks);
            fq.b(onConnectionFailedListener, (Object)"Must provide a connection failed listener");
            this.AZ.add(onConnectionFailedListener);
        }

        public Builder addApi(Api<? extends Api.ApiOptions.NotRequiredOptions> object) {
            this.AX.put(object, null);
            object = object.dZ();
            int n2 = object.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                this.AT.add(((Scope)object.get(i2)).en());
            }
            return this;
        }

        public <O extends Api.ApiOptions.HasOptions> Builder addApi(Api<O> object, O o2) {
            fq.b(o2, (Object)"Null options are not permitted for this Api");
            this.AX.put(object, (Api.ApiOptions)o2);
            object = object.dZ();
            int n2 = object.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                this.AT.add(((Scope)object.get(i2)).en());
            }
            return this;
        }

        public Builder addConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
            this.AY.add(connectionCallbacks);
            return this;
        }

        public Builder addOnConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
            this.AZ.add(onConnectionFailedListener);
            return this;
        }

        public Builder addScope(Scope scope) {
            this.AT.add(scope.en());
            return this;
        }

        public GoogleApiClient build() {
            return new b(this.mContext, this.AS, this.eh(), this.AX, this.AY, this.AZ);
        }

        public fc eh() {
            return new fc(this.wG, this.AT, this.AU, this.AV, this.AW);
        }

        public Builder setAccountName(String string2) {
            this.wG = string2;
            return this;
        }

        public Builder setGravityForPopups(int n2) {
            this.AU = n2;
            return this;
        }

        public Builder setHandler(Handler handler) {
            fq.b(handler, (Object)"Handler must not be null");
            this.AS = handler.getLooper();
            return this;
        }

        public Builder setViewForPopups(View view) {
            this.AV = view;
            return this;
        }

        public Builder useDefaultAccount() {
            return this.setAccountName("<<default account>>");
        }
    }

    public static interface ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        public void onConnected(Bundle var1);

        public void onConnectionSuspended(int var1);
    }

    public static interface OnConnectionFailedListener
    extends GooglePlayServicesClient.OnConnectionFailedListener {
        @Override
        public void onConnectionFailed(ConnectionResult var1);
    }

}

