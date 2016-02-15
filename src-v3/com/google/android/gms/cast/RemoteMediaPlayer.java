package com.google.android.gms.cast;

import com.google.android.gms.cast.Cast.C1471a;
import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.en;
import com.google.android.gms.internal.es;
import com.google.android.gms.internal.et;
import com.google.android.gms.internal.eu;
import java.io.IOException;
import org.json.JSONObject;

public class RemoteMediaPlayer implements MessageReceivedCallback {
    public static final int RESUME_STATE_PAUSE = 2;
    public static final int RESUME_STATE_PLAY = 1;
    public static final int RESUME_STATE_UNCHANGED = 0;
    public static final int STATUS_CANCELED = 2;
    public static final int STATUS_FAILED = 1;
    public static final int STATUS_REPLACED = 4;
    public static final int STATUS_SUCCEEDED = 0;
    public static final int STATUS_TIMED_OUT = 3;
    private final Object li;
    private final es yE;
    private final C0786a yF;
    private OnMetadataUpdatedListener yG;
    private OnStatusUpdatedListener yH;

    public interface OnMetadataUpdatedListener {
        void onMetadataUpdated();
    }

    public interface OnStatusUpdatedListener {
        void onStatusUpdated();
    }

    public interface MediaChannelResult extends Result {
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.a */
    private class C0786a implements et {
        final /* synthetic */ RemoteMediaPlayer yI;
        private GoogleApiClient yS;
        private long yT;

        /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.a.a */
        private final class C0785a implements ResultCallback<Status> {
            private final long yU;
            final /* synthetic */ C0786a yV;

            C0785a(C0786a c0786a, long j) {
                this.yV = c0786a;
                this.yU = j;
            }

            public void m1644i(Status status) {
                if (!status.isSuccess()) {
                    this.yV.yI.yE.m2144a(this.yU, status.getStatusCode());
                }
            }

            public /* synthetic */ void onResult(Result x0) {
                m1644i((Status) x0);
            }
        }

        public C0786a(RemoteMediaPlayer remoteMediaPlayer) {
            this.yI = remoteMediaPlayer;
            this.yT = 0;
        }

        public void m1645a(String str, String str2, long j, String str3) throws IOException {
            if (this.yS == null) {
                throw new IOException("No GoogleApiClient available");
            }
            Cast.CastApi.sendMessage(this.yS, str, str2).setResultCallback(new C0785a(this, j));
        }

        public void m1646b(GoogleApiClient googleApiClient) {
            this.yS = googleApiClient;
        }

        public long dD() {
            long j = this.yT + 1;
            this.yT = j;
            return j;
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.1 */
    class C12961 extends es {
        final /* synthetic */ RemoteMediaPlayer yI;

        C12961(RemoteMediaPlayer remoteMediaPlayer) {
            this.yI = remoteMediaPlayer;
        }

        protected void onMetadataUpdated() {
            this.yI.onMetadataUpdated();
        }

        protected void onStatusUpdated() {
            this.yI.onStatusUpdated();
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.c */
    private static final class C1298c implements MediaChannelResult {
        private final Status wJ;
        private final JSONObject yn;

        C1298c(Status status, JSONObject jSONObject) {
            this.wJ = status;
            this.yn = jSONObject;
        }

        public Status getStatus() {
            return this.wJ;
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.b */
    private static abstract class C1518b extends C1471a<MediaChannelResult> {
        eu yW;

        /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.b.1 */
        class C07871 implements eu {
            final /* synthetic */ C1518b yX;

            C07871(C1518b c1518b) {
                this.yX = c1518b;
            }

            public void m1647a(long j, int i, JSONObject jSONObject) {
                this.yX.m1659a(new C1298c(new Status(i), jSONObject));
            }

            public void m1648l(long j) {
                this.yX.m1659a(this.yX.m3459j(new Status(RemoteMediaPlayer.STATUS_REPLACED)));
            }
        }

        /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.b.2 */
        class C12972 implements MediaChannelResult {
            final /* synthetic */ Status wz;
            final /* synthetic */ C1518b yX;

            C12972(C1518b c1518b, Status status) {
                this.yX = c1518b;
                this.wz = status;
            }

            public Status getStatus() {
                return this.wz;
            }
        }

        C1518b() {
            this.yW = new C07871(this);
        }

        public /* synthetic */ Result m3458d(Status status) {
            return m3459j(status);
        }

        public MediaChannelResult m3459j(Status status) {
            return new C12972(this, status);
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.2 */
    class C15692 extends C1518b {
        final /* synthetic */ RemoteMediaPlayer yI;
        final /* synthetic */ GoogleApiClient yJ;
        final /* synthetic */ MediaInfo yK;
        final /* synthetic */ boolean yL;
        final /* synthetic */ long yM;
        final /* synthetic */ JSONObject yN;

        C15692(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, MediaInfo mediaInfo, boolean z, long j, JSONObject jSONObject) {
            this.yI = remoteMediaPlayer;
            this.yJ = googleApiClient;
            this.yK = mediaInfo;
            this.yL = z;
            this.yM = j;
            this.yN = jSONObject;
        }

        protected void m3621a(en enVar) {
            synchronized (this.yI.li) {
                this.yI.yF.m1646b(this.yJ);
                try {
                    this.yI.yE.m2141a(this.yW, this.yK, this.yL, this.yM, this.yN);
                    this.yI.yF.m1646b(null);
                } catch (IOException e) {
                    m1659a(m3459j(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.yI.yF.m1646b(null);
                } catch (Throwable th) {
                    this.yI.yF.m1646b(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.3 */
    class C15703 extends C1518b {
        final /* synthetic */ RemoteMediaPlayer yI;
        final /* synthetic */ GoogleApiClient yJ;
        final /* synthetic */ JSONObject yN;

        C15703(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, JSONObject jSONObject) {
            this.yI = remoteMediaPlayer;
            this.yJ = googleApiClient;
            this.yN = jSONObject;
        }

        protected void m3623a(en enVar) {
            synchronized (this.yI.li) {
                this.yI.yF.m1646b(this.yJ);
                try {
                    this.yI.yE.m2142a(this.yW, this.yN);
                    this.yI.yF.m1646b(null);
                } catch (IOException e) {
                    m1659a(m3459j(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.yI.yF.m1646b(null);
                } catch (Throwable th) {
                    this.yI.yF.m1646b(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.4 */
    class C15714 extends C1518b {
        final /* synthetic */ RemoteMediaPlayer yI;
        final /* synthetic */ GoogleApiClient yJ;
        final /* synthetic */ JSONObject yN;

        C15714(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, JSONObject jSONObject) {
            this.yI = remoteMediaPlayer;
            this.yJ = googleApiClient;
            this.yN = jSONObject;
        }

        protected void m3625a(en enVar) {
            synchronized (this.yI.li) {
                this.yI.yF.m1646b(this.yJ);
                try {
                    this.yI.yE.m2145b(this.yW, this.yN);
                    this.yI.yF.m1646b(null);
                } catch (IOException e) {
                    m1659a(m3459j(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.yI.yF.m1646b(null);
                } catch (Throwable th) {
                    this.yI.yF.m1646b(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.5 */
    class C15725 extends C1518b {
        final /* synthetic */ RemoteMediaPlayer yI;
        final /* synthetic */ GoogleApiClient yJ;
        final /* synthetic */ JSONObject yN;

        C15725(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, JSONObject jSONObject) {
            this.yI = remoteMediaPlayer;
            this.yJ = googleApiClient;
            this.yN = jSONObject;
        }

        protected void m3627a(en enVar) {
            synchronized (this.yI.li) {
                this.yI.yF.m1646b(this.yJ);
                try {
                    this.yI.yE.m2146c(this.yW, this.yN);
                    this.yI.yF.m1646b(null);
                } catch (IOException e) {
                    m1659a(m3459j(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.yI.yF.m1646b(null);
                } catch (Throwable th) {
                    this.yI.yF.m1646b(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.6 */
    class C15736 extends C1518b {
        final /* synthetic */ RemoteMediaPlayer yI;
        final /* synthetic */ GoogleApiClient yJ;
        final /* synthetic */ JSONObject yN;
        final /* synthetic */ long yO;
        final /* synthetic */ int yP;

        C15736(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, long j, int i, JSONObject jSONObject) {
            this.yI = remoteMediaPlayer;
            this.yJ = googleApiClient;
            this.yO = j;
            this.yP = i;
            this.yN = jSONObject;
        }

        protected void m3629a(en enVar) {
            synchronized (this.yI.li) {
                this.yI.yF.m1646b(this.yJ);
                try {
                    this.yI.yE.m2140a(this.yW, this.yO, this.yP, this.yN);
                    this.yI.yF.m1646b(null);
                } catch (IOException e) {
                    m1659a(m3459j(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.yI.yF.m1646b(null);
                } catch (Throwable th) {
                    this.yI.yF.m1646b(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.7 */
    class C15747 extends C1518b {
        final /* synthetic */ RemoteMediaPlayer yI;
        final /* synthetic */ GoogleApiClient yJ;
        final /* synthetic */ JSONObject yN;
        final /* synthetic */ double yQ;

        C15747(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, double d, JSONObject jSONObject) {
            this.yI = remoteMediaPlayer;
            this.yJ = googleApiClient;
            this.yQ = d;
            this.yN = jSONObject;
        }

        protected void m3631a(en enVar) {
            synchronized (this.yI.li) {
                this.yI.yF.m1646b(this.yJ);
                try {
                    this.yI.yE.m2139a(this.yW, this.yQ, this.yN);
                    this.yI.yF.m1646b(null);
                } catch (IllegalStateException e) {
                    m1659a(m3459j(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.yI.yF.m1646b(null);
                } catch (IllegalArgumentException e2) {
                    m1659a(m3459j(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.yI.yF.m1646b(null);
                } catch (IOException e3) {
                    m1659a(m3459j(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.yI.yF.m1646b(null);
                } catch (Throwable th) {
                    this.yI.yF.m1646b(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.8 */
    class C15758 extends C1518b {
        final /* synthetic */ RemoteMediaPlayer yI;
        final /* synthetic */ GoogleApiClient yJ;
        final /* synthetic */ JSONObject yN;
        final /* synthetic */ boolean yR;

        C15758(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, boolean z, JSONObject jSONObject) {
            this.yI = remoteMediaPlayer;
            this.yJ = googleApiClient;
            this.yR = z;
            this.yN = jSONObject;
        }

        protected void m3633a(en enVar) {
            synchronized (this.yI.li) {
                this.yI.yF.m1646b(this.yJ);
                try {
                    this.yI.yE.m2143a(this.yW, this.yR, this.yN);
                    this.yI.yF.m1646b(null);
                } catch (IllegalStateException e) {
                    m1659a(m3459j(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.yI.yF.m1646b(null);
                } catch (IOException e2) {
                    m1659a(m3459j(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.yI.yF.m1646b(null);
                } catch (Throwable th) {
                    this.yI.yF.m1646b(null);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer.9 */
    class C15769 extends C1518b {
        final /* synthetic */ RemoteMediaPlayer yI;
        final /* synthetic */ GoogleApiClient yJ;

        C15769(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient) {
            this.yI = remoteMediaPlayer;
            this.yJ = googleApiClient;
        }

        protected void m3635a(en enVar) {
            synchronized (this.yI.li) {
                this.yI.yF.m1646b(this.yJ);
                try {
                    this.yI.yE.m2138a(this.yW);
                    this.yI.yF.m1646b(null);
                } catch (IOException e) {
                    m1659a(m3459j(new Status(RemoteMediaPlayer.STATUS_FAILED)));
                    this.yI.yF.m1646b(null);
                } catch (Throwable th) {
                    this.yI.yF.m1646b(null);
                }
            }
        }
    }

    public RemoteMediaPlayer() {
        this.li = new Object();
        this.yF = new C0786a(this);
        this.yE = new C12961(this);
        this.yE.m870a(this.yF);
    }

    private void onMetadataUpdated() {
        if (this.yG != null) {
            this.yG.onMetadataUpdated();
        }
    }

    private void onStatusUpdated() {
        if (this.yH != null) {
            this.yH.onStatusUpdated();
        }
    }

    public long getApproximateStreamPosition() {
        long approximateStreamPosition;
        synchronized (this.li) {
            approximateStreamPosition = this.yE.getApproximateStreamPosition();
        }
        return approximateStreamPosition;
    }

    public MediaInfo getMediaInfo() {
        MediaInfo mediaInfo;
        synchronized (this.li) {
            mediaInfo = this.yE.getMediaInfo();
        }
        return mediaInfo;
    }

    public MediaStatus getMediaStatus() {
        MediaStatus mediaStatus;
        synchronized (this.li) {
            mediaStatus = this.yE.getMediaStatus();
        }
        return mediaStatus;
    }

    public String getNamespace() {
        return this.yE.getNamespace();
    }

    public long getStreamDuration() {
        long streamDuration;
        synchronized (this.li) {
            streamDuration = this.yE.getStreamDuration();
        }
        return streamDuration;
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo) {
        return load(apiClient, mediaInfo, true, 0, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo, boolean autoplay) {
        return load(apiClient, mediaInfo, autoplay, 0, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo, boolean autoplay, long playPosition) {
        return load(apiClient, mediaInfo, autoplay, playPosition, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient apiClient, MediaInfo mediaInfo, boolean autoplay, long playPosition, JSONObject customData) {
        return apiClient.m125b(new C15692(this, apiClient, mediaInfo, autoplay, playPosition, customData));
    }

    public void onMessageReceived(CastDevice castDevice, String namespace, String message) {
        this.yE.m2137U(message);
    }

    public PendingResult<MediaChannelResult> pause(GoogleApiClient apiClient) {
        return pause(apiClient, null);
    }

    public PendingResult<MediaChannelResult> pause(GoogleApiClient apiClient, JSONObject customData) {
        return apiClient.m125b(new C15703(this, apiClient, customData));
    }

    public PendingResult<MediaChannelResult> play(GoogleApiClient apiClient) {
        return play(apiClient, null);
    }

    public PendingResult<MediaChannelResult> play(GoogleApiClient apiClient, JSONObject customData) {
        return apiClient.m125b(new C15725(this, apiClient, customData));
    }

    public PendingResult<MediaChannelResult> requestStatus(GoogleApiClient apiClient) {
        return apiClient.m125b(new C15769(this, apiClient));
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient apiClient, long position) {
        return seek(apiClient, position, STATUS_SUCCEEDED, null);
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient apiClient, long position, int resumeState) {
        return seek(apiClient, position, resumeState, null);
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient apiClient, long position, int resumeState, JSONObject customData) {
        return apiClient.m125b(new C15736(this, apiClient, position, resumeState, customData));
    }

    public void setOnMetadataUpdatedListener(OnMetadataUpdatedListener listener) {
        this.yG = listener;
    }

    public void setOnStatusUpdatedListener(OnStatusUpdatedListener listener) {
        this.yH = listener;
    }

    public PendingResult<MediaChannelResult> setStreamMute(GoogleApiClient apiClient, boolean muteState) {
        return setStreamMute(apiClient, muteState, null);
    }

    public PendingResult<MediaChannelResult> setStreamMute(GoogleApiClient apiClient, boolean muteState, JSONObject customData) {
        return apiClient.m125b(new C15758(this, apiClient, muteState, customData));
    }

    public PendingResult<MediaChannelResult> setStreamVolume(GoogleApiClient apiClient, double volume) throws IllegalArgumentException {
        return setStreamVolume(apiClient, volume, null);
    }

    public PendingResult<MediaChannelResult> setStreamVolume(GoogleApiClient apiClient, double volume, JSONObject customData) throws IllegalArgumentException {
        if (!Double.isInfinite(volume) && !Double.isNaN(volume)) {
            return apiClient.m125b(new C15747(this, apiClient, volume, customData));
        }
        throw new IllegalArgumentException("Volume cannot be " + volume);
    }

    public PendingResult<MediaChannelResult> stop(GoogleApiClient apiClient) {
        return stop(apiClient, null);
    }

    public PendingResult<MediaChannelResult> stop(GoogleApiClient apiClient, JSONObject customData) {
        return apiClient.m125b(new C15714(this, apiClient, customData));
    }
}
