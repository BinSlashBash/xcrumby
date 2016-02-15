/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.en;
import com.google.android.gms.internal.es;
import com.google.android.gms.internal.et;
import com.google.android.gms.internal.eu;
import java.io.IOException;
import org.json.JSONObject;

public class RemoteMediaPlayer
implements Cast.MessageReceivedCallback {
    public static final int RESUME_STATE_PAUSE = 2;
    public static final int RESUME_STATE_PLAY = 1;
    public static final int RESUME_STATE_UNCHANGED = 0;
    public static final int STATUS_CANCELED = 2;
    public static final int STATUS_FAILED = 1;
    public static final int STATUS_REPLACED = 4;
    public static final int STATUS_SUCCEEDED = 0;
    public static final int STATUS_TIMED_OUT = 3;
    private final Object li = new Object();
    private final es yE;
    private final a yF;
    private OnMetadataUpdatedListener yG;
    private OnStatusUpdatedListener yH;

    public RemoteMediaPlayer() {
        this.yF = new a();
        this.yE = new es(){

            @Override
            protected void onMetadataUpdated() {
                RemoteMediaPlayer.this.onMetadataUpdated();
            }

            @Override
            protected void onStatusUpdated() {
                RemoteMediaPlayer.this.onStatusUpdated();
            }
        };
        this.yE.a(this.yF);
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getApproximateStreamPosition() {
        Object object = this.li;
        synchronized (object) {
            return this.yE.getApproximateStreamPosition();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public MediaInfo getMediaInfo() {
        Object object = this.li;
        synchronized (object) {
            return this.yE.getMediaInfo();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public MediaStatus getMediaStatus() {
        Object object = this.li;
        synchronized (object) {
            return this.yE.getMediaStatus();
        }
    }

    public String getNamespace() {
        return this.yE.getNamespace();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getStreamDuration() {
        Object object = this.li;
        synchronized (object) {
            return this.yE.getStreamDuration();
        }
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient googleApiClient, MediaInfo mediaInfo) {
        return this.load(googleApiClient, mediaInfo, true, 0, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient googleApiClient, MediaInfo mediaInfo, boolean bl2) {
        return this.load(googleApiClient, mediaInfo, bl2, 0, null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient googleApiClient, MediaInfo mediaInfo, boolean bl2, long l2) {
        return this.load(googleApiClient, mediaInfo, bl2, l2, null);
    }

    public PendingResult<MediaChannelResult> load(final GoogleApiClient googleApiClient, final MediaInfo mediaInfo, final boolean bl2, final long l2, final JSONObject jSONObject) {
        return googleApiClient.b(new b(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            protected void a(en object) {
                object = RemoteMediaPlayer.this.li;
                synchronized (object) {
                    RemoteMediaPlayer.this.yF.b(googleApiClient);
                    try {
                        RemoteMediaPlayer.this.yE.a(this.yW, mediaInfo, bl2, l2, jSONObject);
                    }
                    catch (IOException var2_2) {
                        this.a(this.j(new Status(1)));
                    }
                    finally {
                        RemoteMediaPlayer.this.yF.b(null);
                    }
                    return;
                }
            }
        });
    }

    @Override
    public void onMessageReceived(CastDevice castDevice, String string2, String string3) {
        this.yE.U(string3);
    }

    public PendingResult<MediaChannelResult> pause(GoogleApiClient googleApiClient) {
        return this.pause(googleApiClient, null);
    }

    public PendingResult<MediaChannelResult> pause(final GoogleApiClient googleApiClient, final JSONObject jSONObject) {
        return googleApiClient.b(new b(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            protected void a(en object) {
                object = RemoteMediaPlayer.this.li;
                synchronized (object) {
                    RemoteMediaPlayer.this.yF.b(googleApiClient);
                    try {
                        RemoteMediaPlayer.this.yE.a(this.yW, jSONObject);
                    }
                    catch (IOException var2_2) {
                        this.a(this.j(new Status(1)));
                    }
                    finally {
                        RemoteMediaPlayer.this.yF.b(null);
                    }
                    return;
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> play(GoogleApiClient googleApiClient) {
        return this.play(googleApiClient, null);
    }

    public PendingResult<MediaChannelResult> play(final GoogleApiClient googleApiClient, final JSONObject jSONObject) {
        return googleApiClient.b(new b(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            protected void a(en object) {
                object = RemoteMediaPlayer.this.li;
                synchronized (object) {
                    RemoteMediaPlayer.this.yF.b(googleApiClient);
                    try {
                        RemoteMediaPlayer.this.yE.c(this.yW, jSONObject);
                    }
                    catch (IOException var2_2) {
                        this.a(this.j(new Status(1)));
                    }
                    finally {
                        RemoteMediaPlayer.this.yF.b(null);
                    }
                    return;
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> requestStatus(final GoogleApiClient googleApiClient) {
        return googleApiClient.b(new b(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            protected void a(en object) {
                object = RemoteMediaPlayer.this.li;
                synchronized (object) {
                    RemoteMediaPlayer.this.yF.b(googleApiClient);
                    try {
                        RemoteMediaPlayer.this.yE.a(this.yW);
                    }
                    catch (IOException var2_2) {
                        this.a(this.j(new Status(1)));
                    }
                    finally {
                        RemoteMediaPlayer.this.yF.b(null);
                    }
                    return;
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient googleApiClient, long l2) {
        return this.seek(googleApiClient, l2, 0, null);
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient googleApiClient, long l2, int n2) {
        return this.seek(googleApiClient, l2, n2, null);
    }

    public PendingResult<MediaChannelResult> seek(final GoogleApiClient googleApiClient, final long l2, final int n2, final JSONObject jSONObject) {
        return googleApiClient.b(new b(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            protected void a(en object) {
                object = RemoteMediaPlayer.this.li;
                synchronized (object) {
                    RemoteMediaPlayer.this.yF.b(googleApiClient);
                    try {
                        RemoteMediaPlayer.this.yE.a(this.yW, l2, n2, jSONObject);
                    }
                    catch (IOException var2_2) {
                        this.a(this.j(new Status(1)));
                    }
                    finally {
                        RemoteMediaPlayer.this.yF.b(null);
                    }
                    return;
                }
            }
        });
    }

    public void setOnMetadataUpdatedListener(OnMetadataUpdatedListener onMetadataUpdatedListener) {
        this.yG = onMetadataUpdatedListener;
    }

    public void setOnStatusUpdatedListener(OnStatusUpdatedListener onStatusUpdatedListener) {
        this.yH = onStatusUpdatedListener;
    }

    public PendingResult<MediaChannelResult> setStreamMute(GoogleApiClient googleApiClient, boolean bl2) {
        return this.setStreamMute(googleApiClient, bl2, null);
    }

    public PendingResult<MediaChannelResult> setStreamMute(final GoogleApiClient googleApiClient, final boolean bl2, final JSONObject jSONObject) {
        return googleApiClient.b(new b(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            protected void a(en object) {
                object = RemoteMediaPlayer.this.li;
                synchronized (object) {
                    RemoteMediaPlayer.this.yF.b(googleApiClient);
                    try {
                        RemoteMediaPlayer.this.yE.a(this.yW, bl2, jSONObject);
                    }
                    catch (IllegalStateException var2_2) {
                        this.a(this.j(new Status(1)));
                    }
                    catch (IOException var2_3) {
                        this.a(this.j(new Status(1)));
                    }
                    finally {
                        RemoteMediaPlayer.this.yF.b(null);
                    }
                    return;
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> setStreamVolume(GoogleApiClient googleApiClient, double d2) throws IllegalArgumentException {
        return this.setStreamVolume(googleApiClient, d2, null);
    }

    public PendingResult<MediaChannelResult> setStreamVolume(final GoogleApiClient googleApiClient, final double d2, final JSONObject jSONObject) throws IllegalArgumentException {
        if (Double.isInfinite(d2) || Double.isNaN(d2)) {
            throw new IllegalArgumentException("Volume cannot be " + d2);
        }
        return googleApiClient.b(new b(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            protected void a(en object) {
                object = RemoteMediaPlayer.this.li;
                synchronized (object) {
                    RemoteMediaPlayer.this.yF.b(googleApiClient);
                    try {
                        RemoteMediaPlayer.this.yE.a(this.yW, d2, jSONObject);
                    }
                    catch (IllegalStateException var2_2) {
                        this.a(this.j(new Status(1)));
                    }
                    catch (IllegalArgumentException var2_3) {
                        this.a(this.j(new Status(1)));
                    }
                    catch (IOException var2_4) {
                        this.a(this.j(new Status(1)));
                    }
                    finally {
                        RemoteMediaPlayer.this.yF.b(null);
                    }
                    return;
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> stop(GoogleApiClient googleApiClient) {
        return this.stop(googleApiClient, null);
    }

    public PendingResult<MediaChannelResult> stop(final GoogleApiClient googleApiClient, final JSONObject jSONObject) {
        return googleApiClient.b(new b(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            protected void a(en object) {
                object = RemoteMediaPlayer.this.li;
                synchronized (object) {
                    RemoteMediaPlayer.this.yF.b(googleApiClient);
                    try {
                        RemoteMediaPlayer.this.yE.b(this.yW, jSONObject);
                    }
                    catch (IOException var2_2) {
                        this.a(this.j(new Status(1)));
                    }
                    finally {
                        RemoteMediaPlayer.this.yF.b(null);
                    }
                    return;
                }
            }
        });
    }

    public static interface MediaChannelResult
    extends Result {
    }

    public static interface OnMetadataUpdatedListener {
        public void onMetadataUpdated();
    }

    public static interface OnStatusUpdatedListener {
        public void onStatusUpdated();
    }

    private class com.google.android.gms.cast.RemoteMediaPlayer$a
    implements et {
        private GoogleApiClient yS;
        private long yT;

        public com.google.android.gms.cast.RemoteMediaPlayer$a() {
            this.yT = 0;
        }

        @Override
        public void a(String string2, String string3, long l2, String string4) throws IOException {
            if (this.yS == null) {
                throw new IOException("No GoogleApiClient available");
            }
            Cast.CastApi.sendMessage(this.yS, string2, string3).setResultCallback(new a(l2));
        }

        public void b(GoogleApiClient googleApiClient) {
            this.yS = googleApiClient;
        }

        @Override
        public long dD() {
            long l2;
            this.yT = l2 = this.yT + 1;
            return l2;
        }

        private final class a
        implements ResultCallback<Status> {
            private final long yU;

            a(long l2) {
                this.yU = l2;
            }

            public void i(Status status) {
                if (!status.isSuccess()) {
                    RemoteMediaPlayer.this.yE.a(this.yU, status.getStatusCode());
                }
            }

            @Override
            public /* synthetic */ void onResult(Result result) {
                this.i((Status)result);
            }
        }

    }

    private static abstract class b
    extends Cast.a<MediaChannelResult> {
        eu yW;

        b() {
            this.yW = new eu(){

                @Override
                public void a(long l2, int n2, JSONObject jSONObject) {
                    b.this.a(new c(new Status(n2), jSONObject));
                }

                @Override
                public void l(long l2) {
                    b.this.a(b.this.j(new Status(4)));
                }
            };
        }

        @Override
        public /* synthetic */ Result d(Status status) {
            return this.j(status);
        }

        public MediaChannelResult j(final Status status) {
            return new MediaChannelResult(){

                @Override
                public Status getStatus() {
                    return status;
                }
            };
        }

    }

    private static final class c
    implements MediaChannelResult {
        private final Status wJ;
        private final JSONObject yn;

        c(Status status, JSONObject jSONObject) {
            this.wJ = status;
            this.yn = jSONObject;
        }

        @Override
        public Status getStatus() {
            return this.wJ;
        }
    }

}

