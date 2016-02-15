package com.google.android.gms.cast;

import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.C0240b;
import com.google.android.gms.common.api.Api.C0241c;
import com.google.android.gms.common.api.C0245a.C0244d;
import com.google.android.gms.common.api.C0245a.C1299b;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.GamesStatusCodes;
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
    static final C0241c<en> wx;
    private static final C0240b<en, CastOptions> wy;

    public interface CastApi {

        /* renamed from: com.google.android.gms.cast.Cast.CastApi.a */
        public static final class C0784a implements CastApi {

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.a.1 */
            class C15601 extends C1516b {
                final /* synthetic */ String xN;
                final /* synthetic */ String xO;
                final /* synthetic */ C0784a xP;

                C15601(C0784a c0784a, String str, String str2) {
                    this.xP = c0784a;
                    this.xN = str;
                    this.xO = str2;
                    super();
                }

                protected void m3603a(en enVar) throws RemoteException {
                    try {
                        enVar.m3064a(this.xN, this.xO, (C0244d) this);
                    } catch (IllegalArgumentException e) {
                        m3257x(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    } catch (IllegalStateException e2) {
                        m3257x(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.a.2 */
            class C15612 extends C1517c {
                final /* synthetic */ C0784a xP;
                final /* synthetic */ String xQ;

                C15612(C0784a c0784a, String str) {
                    this.xP = c0784a;
                    this.xQ = str;
                    super();
                }

                protected void m3605a(en enVar) throws RemoteException {
                    try {
                        enVar.m3065a(this.xQ, false, (C0244d) this);
                    } catch (IllegalStateException e) {
                        m3257x(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.a.3 */
            class C15623 extends C1517c {
                final /* synthetic */ C0784a xP;
                final /* synthetic */ String xQ;
                final /* synthetic */ boolean xR;

                C15623(C0784a c0784a, String str, boolean z) {
                    this.xP = c0784a;
                    this.xQ = str;
                    this.xR = z;
                    super();
                }

                protected void m3607a(en enVar) throws RemoteException {
                    try {
                        enVar.m3065a(this.xQ, this.xR, (C0244d) this);
                    } catch (IllegalStateException e) {
                        m3257x(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.a.4 */
            class C15634 extends C1517c {
                final /* synthetic */ C0784a xP;
                final /* synthetic */ String xQ;
                final /* synthetic */ String xS;

                C15634(C0784a c0784a, String str, String str2) {
                    this.xP = c0784a;
                    this.xQ = str;
                    this.xS = str2;
                    super();
                }

                protected void m3609a(en enVar) throws RemoteException {
                    try {
                        enVar.m3066b(this.xQ, this.xS, this);
                    } catch (IllegalStateException e) {
                        m3257x(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.a.5 */
            class C15645 extends C1517c {
                final /* synthetic */ C0784a xP;
                final /* synthetic */ String xQ;

                C15645(C0784a c0784a, String str) {
                    this.xP = c0784a;
                    this.xQ = str;
                    super();
                }

                protected void m3611a(en enVar) throws RemoteException {
                    try {
                        enVar.m3066b(this.xQ, null, this);
                    } catch (IllegalStateException e) {
                        m3257x(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.a.6 */
            class C15656 extends C1517c {
                final /* synthetic */ C0784a xP;

                C15656(C0784a c0784a) {
                    this.xP = c0784a;
                    super();
                }

                protected void m3613a(en enVar) throws RemoteException {
                    try {
                        enVar.m3066b(null, null, this);
                    } catch (IllegalStateException e) {
                        m3257x(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.a.7 */
            class C15667 extends C1516b {
                final /* synthetic */ C0784a xP;

                C15667(C0784a c0784a) {
                    this.xP = c0784a;
                    super();
                }

                protected void m3615a(en enVar) throws RemoteException {
                    try {
                        enVar.m3067e((C0244d) this);
                    } catch (IllegalStateException e) {
                        m3257x(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.a.8 */
            class C15678 extends C1516b {
                final /* synthetic */ C0784a xP;

                C15678(C0784a c0784a) {
                    this.xP = c0784a;
                    super();
                }

                protected void m3617a(en enVar) throws RemoteException {
                    try {
                        enVar.m3063a(UnsupportedUrlFragment.DISPLAY_NAME, (C0244d) this);
                    } catch (IllegalStateException e) {
                        m3257x(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            /* renamed from: com.google.android.gms.cast.Cast.CastApi.a.9 */
            class C15689 extends C1516b {
                final /* synthetic */ C0784a xP;
                final /* synthetic */ String xS;

                C15689(C0784a c0784a, String str) {
                    this.xP = c0784a;
                    this.xS = str;
                    super();
                }

                protected void m3619a(en enVar) throws RemoteException {
                    if (TextUtils.isEmpty(this.xS)) {
                        m3256c(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE, "IllegalArgument: sessionId cannot be null or empty");
                        return;
                    }
                    try {
                        enVar.m3063a(this.xS, (C0244d) this);
                    } catch (IllegalStateException e) {
                        m3257x(GamesStatusCodes.STATUS_REQUEST_UPDATE_TOTAL_FAILURE);
                    }
                }
            }

            public ApplicationMetadata getApplicationMetadata(GoogleApiClient client) throws IllegalStateException {
                return ((en) client.m123a(Cast.wx)).getApplicationMetadata();
            }

            public String getApplicationStatus(GoogleApiClient client) throws IllegalStateException {
                return ((en) client.m123a(Cast.wx)).getApplicationStatus();
            }

            public double getVolume(GoogleApiClient client) throws IllegalStateException {
                return ((en) client.m123a(Cast.wx)).dI();
            }

            public boolean isMute(GoogleApiClient client) throws IllegalStateException {
                return ((en) client.m123a(Cast.wx)).isMute();
            }

            public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient client) {
                return client.m125b(new C15656(this));
            }

            public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient client, String applicationId) {
                return client.m125b(new C15645(this, applicationId));
            }

            public PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient client, String applicationId, String sessionId) {
                return client.m125b(new C15634(this, applicationId, sessionId));
            }

            public PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient client, String applicationId) {
                return client.m125b(new C15612(this, applicationId));
            }

            public PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient client, String applicationId, boolean relaunchIfRunning) {
                return client.m125b(new C15623(this, applicationId, relaunchIfRunning));
            }

            public PendingResult<Status> leaveApplication(GoogleApiClient client) {
                return client.m125b(new C15667(this));
            }

            public void removeMessageReceivedCallbacks(GoogleApiClient client, String namespace) throws IOException, IllegalArgumentException {
                try {
                    ((en) client.m123a(Cast.wx)).m3058V(namespace);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public void requestStatus(GoogleApiClient client) throws IOException, IllegalStateException {
                try {
                    ((en) client.m123a(Cast.wx)).dH();
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public PendingResult<Status> sendMessage(GoogleApiClient client, String namespace, String message) {
                return client.m125b(new C15601(this, namespace, message));
            }

            public void setMessageReceivedCallbacks(GoogleApiClient client, String namespace, MessageReceivedCallback callbacks) throws IOException, IllegalStateException {
                try {
                    ((en) client.m123a(Cast.wx)).m3062a(namespace, callbacks);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public void setMute(GoogleApiClient client, boolean mute) throws IOException, IllegalStateException {
                try {
                    ((en) client.m123a(Cast.wx)).m3069v(mute);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public void setVolume(GoogleApiClient client, double volume) throws IOException, IllegalArgumentException, IllegalStateException {
                try {
                    ((en) client.m123a(Cast.wx)).m3059a(volume);
                } catch (RemoteException e) {
                    throw new IOException("service error");
                }
            }

            public PendingResult<Status> stopApplication(GoogleApiClient client) {
                return client.m125b(new C15678(this));
            }

            public PendingResult<Status> stopApplication(GoogleApiClient client, String sessionId) {
                return client.m125b(new C15689(this, sessionId));
            }
        }

        ApplicationMetadata getApplicationMetadata(GoogleApiClient googleApiClient) throws IllegalStateException;

        String getApplicationStatus(GoogleApiClient googleApiClient) throws IllegalStateException;

        double getVolume(GoogleApiClient googleApiClient) throws IllegalStateException;

        boolean isMute(GoogleApiClient googleApiClient) throws IllegalStateException;

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient);

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str);

        PendingResult<ApplicationConnectionResult> joinApplication(GoogleApiClient googleApiClient, String str, String str2);

        PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str);

        PendingResult<ApplicationConnectionResult> launchApplication(GoogleApiClient googleApiClient, String str, boolean z);

        PendingResult<Status> leaveApplication(GoogleApiClient googleApiClient);

        void removeMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str) throws IOException, IllegalArgumentException;

        void requestStatus(GoogleApiClient googleApiClient) throws IOException, IllegalStateException;

        PendingResult<Status> sendMessage(GoogleApiClient googleApiClient, String str, String str2);

        void setMessageReceivedCallbacks(GoogleApiClient googleApiClient, String str, MessageReceivedCallback messageReceivedCallback) throws IOException, IllegalStateException;

        void setMute(GoogleApiClient googleApiClient, boolean z) throws IOException, IllegalStateException;

        void setVolume(GoogleApiClient googleApiClient, double d) throws IOException, IllegalArgumentException, IllegalStateException;

        PendingResult<Status> stopApplication(GoogleApiClient googleApiClient);

        PendingResult<Status> stopApplication(GoogleApiClient googleApiClient, String str);
    }

    public static abstract class Listener {
        public void onApplicationDisconnected(int statusCode) {
        }

        public void onApplicationStatusChanged() {
        }

        public void onVolumeChanged() {
        }
    }

    public interface MessageReceivedCallback {
        void onMessageReceived(CastDevice castDevice, String str, String str2);
    }

    /* renamed from: com.google.android.gms.cast.Cast.1 */
    static class C07831 implements C0240b<en, CastOptions> {
        C07831() {
        }

        public en m1643a(Context context, Looper looper, fc fcVar, CastOptions castOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            fq.m982b((Object) castOptions, (Object) "Setting the API options is required.");
            return new en(context, looper, castOptions.xT, (long) castOptions.xV, castOptions.xU, connectionCallbacks, onConnectionFailedListener);
        }

        public int getPriority() {
            return Integer.MAX_VALUE;
        }
    }

    public interface ApplicationConnectionResult extends Result {
        ApplicationMetadata getApplicationMetadata();

        String getApplicationStatus();

        String getSessionId();

        boolean getWasLaunched();
    }

    public static final class CastOptions implements HasOptions {
        final CastDevice xT;
        final Listener xU;
        private final int xV;

        public static final class Builder {
            CastDevice xW;
            Listener xX;
            private int xY;

            private Builder(CastDevice castDevice, Listener castListener) {
                fq.m982b((Object) castDevice, (Object) "CastDevice parameter cannot be null");
                fq.m982b((Object) castListener, (Object) "CastListener parameter cannot be null");
                this.xW = castDevice;
                this.xX = castListener;
                this.xY = 0;
            }

            public CastOptions build() {
                return new CastOptions();
            }

            public Builder setVerboseLoggingEnabled(boolean enabled) {
                if (enabled) {
                    this.xY |= 1;
                } else {
                    this.xY &= -2;
                }
                return this;
            }
        }

        private CastOptions(Builder builder) {
            this.xT = builder.xW;
            this.xU = builder.xX;
            this.xV = builder.xY;
        }

        public static Builder builder(CastDevice castDevice, Listener castListener) {
            return new Builder(castListener, null);
        }
    }

    /* renamed from: com.google.android.gms.cast.Cast.a */
    protected static abstract class C1471a<R extends Result> extends C1299b<R, en> {
        public C1471a() {
            super(Cast.wx);
        }

        public void m3256c(int i, String str) {
            m1659a(m1663d(new Status(i, str, null)));
        }

        public void m3257x(int i) {
            m1659a(m1663d(new Status(i)));
        }
    }

    /* renamed from: com.google.android.gms.cast.Cast.b */
    private static abstract class C1516b extends C1471a<Status> {
        private C1516b() {
        }

        public /* synthetic */ Result m3454d(Status status) {
            return m3455f(status);
        }

        public Status m3455f(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.cast.Cast.c */
    private static abstract class C1517c extends C1471a<ApplicationConnectionResult> {

        /* renamed from: com.google.android.gms.cast.Cast.c.1 */
        class C12951 implements ApplicationConnectionResult {
            final /* synthetic */ Status wz;
            final /* synthetic */ C1517c xZ;

            C12951(C1517c c1517c, Status status) {
                this.xZ = c1517c;
                this.wz = status;
            }

            public ApplicationMetadata getApplicationMetadata() {
                return null;
            }

            public String getApplicationStatus() {
                return null;
            }

            public String getSessionId() {
                return null;
            }

            public Status getStatus() {
                return this.wz;
            }

            public boolean getWasLaunched() {
                return false;
            }
        }

        private C1517c() {
        }

        public /* synthetic */ Result m3456d(Status status) {
            return m3457h(status);
        }

        public ApplicationConnectionResult m3457h(Status status) {
            return new C12951(this, status);
        }
    }

    static {
        wx = new C0241c();
        wy = new C07831();
        API = new Api(wy, wx, new Scope[0]);
        CastApi = new C0784a();
    }

    private Cast() {
    }
}
