package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.ApiOptions.NotRequiredOptions;
import com.google.android.gms.common.api.Api.C0239a;
import com.google.android.gms.common.api.Api.C0241c;
import com.google.android.gms.common.api.C0245a.C1299b;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface GoogleApiClient {

    public static final class Builder {
        private Looper AS;
        private final Set<String> AT;
        private int AU;
        private View AV;
        private String AW;
        private final Map<Api<?>, ApiOptions> AX;
        private final Set<ConnectionCallbacks> AY;
        private final Set<OnConnectionFailedListener> AZ;
        private final Context mContext;
        private String wG;

        public Builder(Context context) {
            this.AT = new HashSet();
            this.AX = new HashMap();
            this.AY = new HashSet();
            this.AZ = new HashSet();
            this.mContext = context;
            this.AS = context.getMainLooper();
            this.AW = context.getPackageName();
        }

        public Builder(Context context, ConnectionCallbacks connectedListener, OnConnectionFailedListener connectionFailedListener) {
            this(context);
            fq.m982b((Object) connectedListener, (Object) "Must provide a connected listener");
            this.AY.add(connectedListener);
            fq.m982b((Object) connectionFailedListener, (Object) "Must provide a connection failed listener");
            this.AZ.add(connectionFailedListener);
        }

        public Builder addApi(Api<? extends NotRequiredOptions> api) {
            this.AX.put(api, null);
            List dZ = api.dZ();
            int size = dZ.size();
            for (int i = 0; i < size; i++) {
                this.AT.add(((Scope) dZ.get(i)).en());
            }
            return this;
        }

        public <O extends HasOptions> Builder addApi(Api<O> api, O options) {
            fq.m982b((Object) options, (Object) "Null options are not permitted for this Api");
            this.AX.put(api, options);
            List dZ = api.dZ();
            int size = dZ.size();
            for (int i = 0; i < size; i++) {
                this.AT.add(((Scope) dZ.get(i)).en());
            }
            return this;
        }

        public Builder addConnectionCallbacks(ConnectionCallbacks listener) {
            this.AY.add(listener);
            return this;
        }

        public Builder addOnConnectionFailedListener(OnConnectionFailedListener listener) {
            this.AZ.add(listener);
            return this;
        }

        public Builder addScope(Scope scope) {
            this.AT.add(scope.en());
            return this;
        }

        public GoogleApiClient build() {
            return new C0793b(this.mContext, this.AS, eh(), this.AX, this.AY, this.AZ);
        }

        public fc eh() {
            return new fc(this.wG, this.AT, this.AU, this.AV, this.AW);
        }

        public Builder setAccountName(String accountName) {
            this.wG = accountName;
            return this;
        }

        public Builder setGravityForPopups(int gravityForPopups) {
            this.AU = gravityForPopups;
            return this;
        }

        public Builder setHandler(Handler handler) {
            fq.m982b((Object) handler, (Object) "Handler must not be null");
            this.AS = handler.getLooper();
            return this;
        }

        public Builder setViewForPopups(View viewForPopups) {
            this.AV = viewForPopups;
            return this;
        }

        public Builder useDefaultAccount() {
            return setAccountName("<<default account>>");
        }
    }

    public interface ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        void onConnected(Bundle bundle);

        void onConnectionSuspended(int i);
    }

    public interface OnConnectionFailedListener extends com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener {
        void onConnectionFailed(ConnectionResult connectionResult);
    }

    <C extends C0239a> C m123a(C0241c<C> c0241c);

    <A extends C0239a, T extends C1299b<? extends Result, A>> T m124a(T t);

    <A extends C0239a, T extends C1299b<? extends Result, A>> T m125b(T t);

    ConnectionResult blockingConnect(long j, TimeUnit timeUnit);

    void connect();

    void disconnect();

    Looper getLooper();

    boolean isConnected();

    boolean isConnecting();

    boolean isConnectionCallbacksRegistered(ConnectionCallbacks connectionCallbacks);

    boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener onConnectionFailedListener);

    void reconnect();

    void registerConnectionCallbacks(ConnectionCallbacks connectionCallbacks);

    void registerConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener);

    void unregisterConnectionCallbacks(ConnectionCallbacks connectionCallbacks);

    void unregisterConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener);
}
