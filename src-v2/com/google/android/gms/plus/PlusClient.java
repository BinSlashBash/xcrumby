/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 */
package com.google.android.gms.plus;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.fk;
import com.google.android.gms.plus.Moments;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.internal.e;
import com.google.android.gms.plus.internal.h;
import com.google.android.gms.plus.internal.i;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.Collection;

@Deprecated
public class PlusClient
implements GooglePlayServicesClient {
    final e TL;

    PlusClient(e e2) {
        this.TL = e2;
    }

    @Deprecated
    public void clearDefaultAccount() {
        this.TL.clearDefaultAccount();
    }

    @Deprecated
    @Override
    public void connect() {
        this.TL.connect();
    }

    @Deprecated
    @Override
    public void disconnect() {
        this.TL.disconnect();
    }

    @Deprecated
    public String getAccountName() {
        return this.TL.getAccountName();
    }

    @Deprecated
    public Person getCurrentPerson() {
        return this.TL.getCurrentPerson();
    }

    e iI() {
        return this.TL;
    }

    @Deprecated
    @Override
    public boolean isConnected() {
        return this.TL.isConnected();
    }

    @Deprecated
    @Override
    public boolean isConnecting() {
        return this.TL.isConnecting();
    }

    @Deprecated
    @Override
    public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        return this.TL.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    @Deprecated
    @Override
    public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.TL.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    @Deprecated
    public void loadMoments(final OnMomentsLoadedListener onMomentsLoadedListener) {
        this.TL.l(new a.d<Moments.LoadMomentsResult>(){

            public void a(Moments.LoadMomentsResult loadMomentsResult) {
                onMomentsLoadedListener.onMomentsLoaded(loadMomentsResult.getStatus().eq(), loadMomentsResult.getMomentBuffer(), loadMomentsResult.getNextPageToken(), loadMomentsResult.getUpdated());
            }

            @Override
            public /* synthetic */ void b(Object object) {
                this.a((Moments.LoadMomentsResult)object);
            }
        });
    }

    @Deprecated
    public void loadMoments(final OnMomentsLoadedListener onMomentsLoadedListener, int n2, String string2, Uri uri, String string3, String string4) {
        this.TL.a(new a.d<Moments.LoadMomentsResult>(){

            public void a(Moments.LoadMomentsResult loadMomentsResult) {
                onMomentsLoadedListener.onMomentsLoaded(loadMomentsResult.getStatus().eq(), loadMomentsResult.getMomentBuffer(), loadMomentsResult.getNextPageToken(), loadMomentsResult.getUpdated());
            }

            @Override
            public /* synthetic */ void b(Object object) {
                this.a((Moments.LoadMomentsResult)object);
            }
        }, n2, string2, uri, string3, string4);
    }

    @Deprecated
    public void loadPeople(final OnPeopleLoadedListener onPeopleLoadedListener, Collection<String> collection) {
        this.TL.a(new a.d<People.LoadPeopleResult>(){

            public void a(People.LoadPeopleResult loadPeopleResult) {
                onPeopleLoadedListener.onPeopleLoaded(loadPeopleResult.getStatus().eq(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
            }

            @Override
            public /* synthetic */ void b(Object object) {
                this.a((People.LoadPeopleResult)object);
            }
        }, collection);
    }

    @Deprecated
    public /* varargs */ void loadPeople(final OnPeopleLoadedListener onPeopleLoadedListener, String ... arrstring) {
        this.TL.d(new a.d<People.LoadPeopleResult>(){

            public void a(People.LoadPeopleResult loadPeopleResult) {
                onPeopleLoadedListener.onPeopleLoaded(loadPeopleResult.getStatus().eq(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
            }

            @Override
            public /* synthetic */ void b(Object object) {
                this.a((People.LoadPeopleResult)object);
            }
        }, arrstring);
    }

    @Deprecated
    public void loadVisiblePeople(final OnPeopleLoadedListener onPeopleLoadedListener, int n2, String string2) {
        this.TL.a(new a.d<People.LoadPeopleResult>(){

            public void a(People.LoadPeopleResult loadPeopleResult) {
                onPeopleLoadedListener.onPeopleLoaded(loadPeopleResult.getStatus().eq(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
            }

            @Override
            public /* synthetic */ void b(Object object) {
                this.a((People.LoadPeopleResult)object);
            }
        }, n2, string2);
    }

    @Deprecated
    public void loadVisiblePeople(final OnPeopleLoadedListener onPeopleLoadedListener, String string2) {
        this.TL.o(new a.d<People.LoadPeopleResult>(){

            public void a(People.LoadPeopleResult loadPeopleResult) {
                onPeopleLoadedListener.onPeopleLoaded(loadPeopleResult.getStatus().eq(), loadPeopleResult.getPersonBuffer(), loadPeopleResult.getNextPageToken());
            }

            @Override
            public /* synthetic */ void b(Object object) {
                this.a((People.LoadPeopleResult)object);
            }
        }, string2);
    }

    @Deprecated
    @Override
    public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.TL.registerConnectionCallbacks(connectionCallbacks);
    }

    @Deprecated
    @Override
    public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.TL.registerConnectionFailedListener(onConnectionFailedListener);
    }

    @Deprecated
    public void removeMoment(String string2) {
        this.TL.removeMoment(string2);
    }

    @Deprecated
    public void revokeAccessAndDisconnect(final OnAccessRevokedListener onAccessRevokedListener) {
        this.TL.n(new a.d<Status>(){

            public void Y(Status status) {
                onAccessRevokedListener.onAccessRevoked(status.getStatus().eq());
            }

            @Override
            public /* synthetic */ void b(Object object) {
                this.Y((Status)object);
            }
        });
    }

    @Deprecated
    @Override
    public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks) {
        this.TL.unregisterConnectionCallbacks(connectionCallbacks);
    }

    @Deprecated
    @Override
    public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.TL.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @Deprecated
    public void writeMoment(Moment moment) {
        this.TL.a(null, moment);
    }

    @Deprecated
    public static class Builder {
        private final GooglePlayServicesClient.ConnectionCallbacks TQ;
        private final GooglePlayServicesClient.OnConnectionFailedListener TR;
        private final i TS;
        private final Context mContext;

        public Builder(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.mContext = context;
            this.TQ = connectionCallbacks;
            this.TR = onConnectionFailedListener;
            this.TS = new i(this.mContext);
        }

        public PlusClient build() {
            return new PlusClient(new e(this.mContext, this.TQ, this.TR, this.TS.iZ()));
        }

        public Builder clearScopes() {
            this.TS.iY();
            return this;
        }

        public Builder setAccountName(String string2) {
            this.TS.bh(string2);
            return this;
        }

        public /* varargs */ Builder setActions(String ... arrstring) {
            this.TS.f(arrstring);
            return this;
        }

        public /* varargs */ Builder setScopes(String ... arrstring) {
            this.TS.e(arrstring);
            return this;
        }
    }

    @Deprecated
    public static interface OnAccessRevokedListener {
        public void onAccessRevoked(ConnectionResult var1);
    }

    @Deprecated
    public static interface OnMomentsLoadedListener {
        @Deprecated
        public void onMomentsLoaded(ConnectionResult var1, MomentBuffer var2, String var3, String var4);
    }

    @Deprecated
    public static interface OnPeopleLoadedListener {
        public void onPeopleLoaded(ConnectionResult var1, PersonBuffer var2, String var3);
    }

    @Deprecated
    public static interface OrderBy {
        @Deprecated
        public static final int ALPHABETICAL = 0;
        @Deprecated
        public static final int BEST = 1;
    }

}

