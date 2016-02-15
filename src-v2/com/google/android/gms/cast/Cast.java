/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Looper
 *  android.os.RemoteException
 *  android.text.TextUtils
 */
package com.google.android.gms.cast;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.en;
import com.google.android.gms.internal.fc;
import com.google.android.gms.internal.fq;
import java.io.IOException;

public final class Cast {
    public static final Api<CastOptions> API;
    public static final CastApi CastApi;
    public static final String EXTRA_APP_NO_LONGER_RUNNING = "com.google.android.gms.cast.EXTRA_APP_NO_LONGER_RUNNING";
    public static final int MAX_MESSAGE_LENGTH = 65536;
    public static final int MAX_NAMESPACE_LENGTH = 128;
    static final Api.c<en> wx;
    private static final Api.b<en, CastOptions> wy;

    static {
        wx = new Api.c();
        wy = new Api.b<en, CastOptions>(){

            @Override
            public en a(Context context, Looper looper, fc fc2, CastOptions castOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                fq.b(castOptions, (Object)"Setting the API options is required.");
                return new en(context, looper, castOptions.xT, castOptions.xV, castOptions.xU, connectionCallbacks, onConnectionFailedListener);
            }

            @Override
            public int getPriority() {
                return Integer.MAX_VALUE;
            }
        };
        API = new Api<CastOptions>(wy, wx, new Scope[0]);
        CastApi = new CastApi.a();
    }

    private Cast() {
    }

    public static interface ApplicationConnectionResult
    extends Result {
        public ApplicationMetadata getApplicationMetadata();

        public String getApplicationStatus();

        public String getSessionId();

        public boolean getWasLaunched();
    }

    public static interface CastApi {
        public ApplicationMetadata getApplicationMetadata(GoogleApiClient var1) throws IllegalStateException;

        public String getApplicationStatus(GoogleApiClient var1) throws IllegalStateException;

        public double getVolume(GoogleApiClient var1) throws IllegalStateException;

        public boolean isMute(GoogleApiClient var1) throws IllegalStateException;

        public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient var1);

        public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient var1, String var2);

        public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient var1, String var2, String var3);

        public PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient var1, String var2);

        public PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient var1, String var2, boolean var3);

        public PendingResult<Status> leaveApplication(GoogleApiClient var1);

        public void removeMessageReceivedCallbacks(GoogleApiClient var1, String var2) throws IOException, IllegalArgumentException;

        public void requestStatus(GoogleApiClient var1) throws IOException, IllegalStateException;

        public PendingResult<Status> sendMessage(GoogleApiClient var1, String var2, String var3);

        public void setMessageReceivedCallbacks(GoogleApiClient var1, String var2, MessageReceivedCallback var3) throws IOException, IllegalStateException;

        public void setMute(GoogleApiClient var1, boolean var2) throws IOException, IllegalStateException;

        public void setVolume(GoogleApiClient var1, double var2) throws IOException, IllegalArgumentException, IllegalStateException;

        public PendingResult<Status> stopApplication(GoogleApiClient var1);

        public PendingResult<Status> stopApplication(GoogleApiClient var1, String var2);

        public static final class a
        implements CastApi {
            @Override
            public ApplicationMetadata getApplicationMetadata(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((en)((Object)googleApiClient.a(Cast.wx))).getApplicationMetadata();
            }

            @Override
            public String getApplicationStatus(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((en)((Object)googleApiClient.a(Cast.wx))).getApplicationStatus();
            }

            @Override
            public double getVolume(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((en)((Object)googleApiClient.a(Cast.wx))).dI();
            }

            @Override
            public boolean isMute(GoogleApiClient googleApiClient) throws IllegalStateException {
                return ((en)((Object)googleApiClient.a(Cast.wx))).isMute();
            }

            @Override
            public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient) {
                return googleApiClient.b(new c(){

                    @Override
                    protected void a(en en2) throws RemoteException {
                        try {
                            en2.b(null, null, this);
                            return;
                        }
                        catch (IllegalStateException var1_2) {
                            this.x(2001);
                            return;
                        }
                    }
                });
            }

            @Override
            public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, final String string2) {
                return googleApiClient.b(new c(){

                    @Override
                    protected void a(en en2) throws RemoteException {
                        try {
                            en2.b(string2, null, this);
                            return;
                        }
                        catch (IllegalStateException var1_2) {
                            this.x(2001);
                            return;
                        }
                    }
                });
            }

            @Override
            public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, final String string2, final String string3) {
                return googleApiClient.b(new c(){

                    @Override
                    protected void a(en en2) throws RemoteException {
                        try {
                            en2.b(string2, string3, this);
                            return;
                        }
                        catch (IllegalStateException var1_2) {
                            this.x(2001);
                            return;
                        }
                    }
                });
            }

            @Override
            public PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, final String string2) {
                return googleApiClient.b(new c(){

                    @Override
                    protected void a(en en2) throws RemoteException {
                        try {
                            en2.a(string2, false, this);
                            return;
                        }
                        catch (IllegalStateException var1_2) {
                            this.x(2001);
                            return;
                        }
                    }
                });
            }

            @Override
            public PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, final String string2, final boolean bl2) {
                return googleApiClient.b(new c(){

                    @Override
                    protected void a(en en2) throws RemoteException {
                        try {
                            en2.a(string2, bl2, this);
                            return;
                        }
                        catch (IllegalStateException var1_2) {
                            this.x(2001);
                            return;
                        }
                    }
                });
            }

            @Override
            public PendingResult<Status> leaveApplication(GoogleApiClient googleApiClient) {
                return googleApiClient.b(new b(){

                    @Override
                    protected void a(en en2) throws RemoteException {
                        try {
                            en2.e(this);
                            return;
                        }
                        catch (IllegalStateException var1_2) {
                            this.x(2001);
                            return;
                        }
                    }
                });
            }

            @Override
            public void removeMessageReceivedCallbacks(GoogleApiClient googleApiClient, String string2) throws IOException, IllegalArgumentException {
                try {
                    ((en)((Object)googleApiClient.a(Cast.wx))).V(string2);
                    return;
                }
                catch (RemoteException var1_2) {
                    throw new IOException("service error");
                }
            }

            @Override
            public void requestStatus(GoogleApiClient googleApiClient) throws IOException, IllegalStateException {
                try {
                    ((en)((Object)googleApiClient.a(Cast.wx))).dH();
                    return;
                }
                catch (RemoteException var1_2) {
                    throw new IOException("service error");
                }
            }

            @Override
            public PendingResult<Status> sendMessage(GoogleApiClient googleApiClient, final String string2, final String string3) {
                return googleApiClient.b(new b(){

                    @Override
                    protected void a(en en2) throws RemoteException {
                        try {
                            en2.a(string2, string3, this);
                            return;
                        }
                        catch (IllegalArgumentException var1_2) {
                            this.x(2001);
                            return;
                        }
                        catch (IllegalStateException var1_3) {
                            this.x(2001);
                            return;
                        }
                    }
                });
            }

            @Override
            public void setMessageReceivedCallbacks(GoogleApiClient googleApiClient, String string2, MessageReceivedCallback messageReceivedCallback) throws IOException, IllegalStateException {
                try {
                    ((en)((Object)googleApiClient.a(Cast.wx))).a(string2, messageReceivedCallback);
                    return;
                }
                catch (RemoteException var1_2) {
                    throw new IOException("service error");
                }
            }

            @Override
            public void setMute(GoogleApiClient googleApiClient, boolean bl2) throws IOException, IllegalStateException {
                try {
                    ((en)((Object)googleApiClient.a(Cast.wx))).v(bl2);
                    return;
                }
                catch (RemoteException var1_2) {
                    throw new IOException("service error");
                }
            }

            @Override
            public void setVolume(GoogleApiClient googleApiClient, double d2) throws IOException, IllegalArgumentException, IllegalStateException {
                try {
                    ((en)((Object)googleApiClient.a(Cast.wx))).a(d2);
                    return;
                }
                catch (RemoteException var1_2) {
                    throw new IOException("service error");
                }
            }

            @Override
            public PendingResult<Status> stopApplication(GoogleApiClient googleApiClient) {
                return googleApiClient.b(new b(){

                    @Override
                    protected void a(en en2) throws RemoteException {
                        try {
                            en2.a("", this);
                            return;
                        }
                        catch (IllegalStateException var1_2) {
                            this.x(2001);
                            return;
                        }
                    }
                });
            }

            @Override
            public PendingResult<Status> stopApplication(GoogleApiClient googleApiClient, final String string2) {
                return googleApiClient.b(new b(){

                    @Override
                    protected void a(en en2) throws RemoteException {
                        if (TextUtils.isEmpty((CharSequence)string2)) {
                            this.c(2001, "IllegalArgument: sessionId cannot be null or empty");
                            return;
                        }
                        try {
                            en2.a(string2, this);
                            return;
                        }
                        catch (IllegalStateException var1_2) {
                            this.x(2001);
                            return;
                        }
                    }
                });
            }

        }

    }

    public static final class CastOptions
    implements Api.ApiOptions.HasOptions {
        final CastDevice xT;
        final Listener xU;
        private final int xV;

        private CastOptions(Builder builder) {
            this.xT = builder.xW;
            this.xU = builder.xX;
            this.xV = builder.xY;
        }

        public static Builder builder(CastDevice castDevice, Listener listener) {
            return new Builder(castDevice, listener);
        }

        public static final class Builder {
            CastDevice xW;
            Listener xX;
            private int xY;

            private Builder(CastDevice castDevice, Listener listener) {
                fq.b(castDevice, (Object)"CastDevice parameter cannot be null");
                fq.b(listener, (Object)"CastListener parameter cannot be null");
                this.xW = castDevice;
                this.xX = listener;
                this.xY = 0;
            }

            public CastOptions build() {
                return new CastOptions(this);
            }

            public Builder setVerboseLoggingEnabled(boolean bl2) {
                if (bl2) {
                    this.xY |= 1;
                    return this;
                }
                this.xY &= -2;
                return this;
            }
        }

    }

    public static abstract class Listener {
        public void onApplicationDisconnected(int n2) {
        }

        public void onApplicationStatusChanged() {
        }

        public void onVolumeChanged() {
        }
    }

    public static interface MessageReceivedCallback {
        public void onMessageReceived(CastDevice var1, String var2, String var3);
    }

    protected static abstract class a<R extends Result>
    extends a.b<R, en> {
        public a() {
            super(Cast.wx);
        }

        public void c(int n2, String string2) {
            this.a(this.d(new Status(n2, string2, null)));
        }

        public void x(int n2) {
            this.a(this.d(new Status(n2)));
        }
    }

    private static abstract class b
    extends a<Status> {
        private b() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.f(status);
        }

        public Status f(Status status) {
            return status;
        }
    }

    private static abstract class c
    extends a<ApplicationConnectionResult> {
        private c() {
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.h(status);
        }

        public ApplicationConnectionResult h(final Status status) {
            return new ApplicationConnectionResult(){

                @Override
                public ApplicationMetadata getApplicationMetadata() {
                    return null;
                }

                @Override
                public String getApplicationStatus() {
                    return null;
                }

                @Override
                public String getSessionId() {
                    return null;
                }

                @Override
                public Status getStatus() {
                    return status;
                }

                @Override
                public boolean getWasLaunched() {
                    return false;
                }
            };
        }

    }

}

