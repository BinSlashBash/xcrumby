package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaStatus;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class es extends em {
    private static final String NAMESPACE;
    private static final long zG;
    private static final long zH;
    private static final long zI;
    private static final long zJ;
    private final Handler mHandler;
    private long zK;
    private MediaStatus zL;
    private final ev zM;
    private final ev zN;
    private final ev zO;
    private final ev zP;
    private final ev zQ;
    private final ev zR;
    private final ev zS;
    private final ev zT;
    private final Runnable zU;
    private boolean zV;

    /* renamed from: com.google.android.gms.internal.es.a */
    private class C0381a implements Runnable {
        final /* synthetic */ es zW;

        private C0381a(es esVar) {
            this.zW = esVar;
        }

        public void run() {
            boolean z = false;
            this.zW.zV = false;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.zW.zM.m907d(elapsedRealtime, 3);
            this.zW.zN.m907d(elapsedRealtime, 3);
            this.zW.zO.m907d(elapsedRealtime, 3);
            this.zW.zP.m907d(elapsedRealtime, 3);
            this.zW.zQ.m907d(elapsedRealtime, 3);
            this.zW.zR.m907d(elapsedRealtime, 3);
            this.zW.zS.m907d(elapsedRealtime, 3);
            this.zW.zT.m907d(elapsedRealtime, 3);
            synchronized (ev.Ab) {
                if (this.zW.zM.dU() || this.zW.zQ.dU() || this.zW.zR.dU() || this.zW.zS.dU() || this.zW.zT.dU()) {
                    z = true;
                }
            }
            this.zW.m2136w(z);
        }
    }

    static {
        NAMESPACE = eo.m873X("com.google.cast.media");
        zG = TimeUnit.HOURS.toMillis(24);
        zH = TimeUnit.HOURS.toMillis(24);
        zI = TimeUnit.HOURS.toMillis(24);
        zJ = TimeUnit.SECONDS.toMillis(1);
    }

    public es() {
        this(null);
    }

    public es(String str) {
        super(NAMESPACE, "MediaControlChannel", str);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.zU = new C0381a();
        this.zM = new ev(zH);
        this.zN = new ev(zG);
        this.zO = new ev(zG);
        this.zP = new ev(zG);
        this.zQ = new ev(zI);
        this.zR = new ev(zG);
        this.zS = new ev(zG);
        this.zT = new ev(zG);
        dS();
    }

    private void m2126a(long j, JSONObject jSONObject) throws JSONException {
        int i = 1;
        boolean n = this.zM.m908n(j);
        int i2 = (!this.zQ.dU() || this.zQ.m908n(j)) ? 0 : 1;
        if ((!this.zR.dU() || this.zR.m908n(j)) && (!this.zS.dU() || this.zS.m908n(j))) {
            i = 0;
        }
        i2 = i2 != 0 ? 2 : 0;
        if (i != 0) {
            i2 |= 1;
        }
        if (n || this.zL == null) {
            this.zL = new MediaStatus(jSONObject);
            this.zK = SystemClock.elapsedRealtime();
            i2 = 7;
        } else {
            i2 = this.zL.m96a(jSONObject, i2);
        }
        if ((i2 & 1) != 0) {
            this.zK = SystemClock.elapsedRealtime();
            onStatusUpdated();
        }
        if ((i2 & 2) != 0) {
            this.zK = SystemClock.elapsedRealtime();
            onStatusUpdated();
        }
        if ((i2 & 4) != 0) {
            onMetadataUpdated();
        }
        this.zM.m906c(j, 0);
        this.zN.m906c(j, 0);
        this.zO.m906c(j, 0);
        this.zP.m906c(j, 0);
        this.zQ.m906c(j, 0);
        this.zR.m906c(j, 0);
        this.zS.m906c(j, 0);
        this.zT.m906c(j, 0);
    }

    private void dS() {
        m2136w(false);
        this.zK = 0;
        this.zL = null;
        this.zM.clear();
        this.zQ.clear();
        this.zR.clear();
    }

    private void m2136w(boolean z) {
        if (this.zV != z) {
            this.zV = z;
            if (z) {
                this.mHandler.postDelayed(this.zU, zJ);
            } else {
                this.mHandler.removeCallbacks(this.zU);
            }
        }
    }

    public final void m2137U(String str) {
        this.yY.m898b("message received: %s", str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("type");
            long optLong = jSONObject.optLong("requestId", -1);
            if (string.equals("MEDIA_STATUS")) {
                JSONArray jSONArray = jSONObject.getJSONArray("status");
                if (jSONArray.length() > 0) {
                    m2126a(optLong, jSONArray.getJSONObject(0));
                    return;
                }
                this.zL = null;
                onStatusUpdated();
                onMetadataUpdated();
                this.zT.m906c(optLong, 0);
            } else if (string.equals("INVALID_PLAYER_STATE")) {
                this.yY.m900d("received unexpected error: Invalid Player State.", new Object[0]);
                jSONObject = jSONObject.optJSONObject("customData");
                this.zM.m905b(optLong, 1, jSONObject);
                this.zN.m905b(optLong, 1, jSONObject);
                this.zO.m905b(optLong, 1, jSONObject);
                this.zP.m905b(optLong, 1, jSONObject);
                this.zQ.m905b(optLong, 1, jSONObject);
                this.zR.m905b(optLong, 1, jSONObject);
                this.zS.m905b(optLong, 1, jSONObject);
                this.zT.m905b(optLong, 1, jSONObject);
            } else if (string.equals("LOAD_FAILED")) {
                this.zM.m905b(optLong, 1, jSONObject.optJSONObject("customData"));
            } else if (string.equals("LOAD_CANCELLED")) {
                this.zM.m905b(optLong, 2, jSONObject.optJSONObject("customData"));
            } else if (string.equals("INVALID_REQUEST")) {
                this.yY.m900d("received unexpected error: Invalid Request.", new Object[0]);
                jSONObject = jSONObject.optJSONObject("customData");
                this.zM.m905b(optLong, 1, jSONObject);
                this.zN.m905b(optLong, 1, jSONObject);
                this.zO.m905b(optLong, 1, jSONObject);
                this.zP.m905b(optLong, 1, jSONObject);
                this.zQ.m905b(optLong, 1, jSONObject);
                this.zR.m905b(optLong, 1, jSONObject);
                this.zS.m905b(optLong, 1, jSONObject);
                this.zT.m905b(optLong, 1, jSONObject);
            }
        } catch (JSONException e) {
            this.yY.m900d("Message is malformed (%s); ignoring: %s", e.getMessage(), str);
        }
    }

    public long m2138a(eu euVar) throws IOException {
        JSONObject jSONObject = new JSONObject();
        long dE = dE();
        this.zT.m904a(dE, euVar);
        m2136w(true);
        try {
            jSONObject.put("requestId", dE);
            jSONObject.put("type", (Object) "GET_STATUS");
            if (this.zL != null) {
                jSONObject.put("mediaSessionId", this.zL.dC());
            }
        } catch (JSONException e) {
        }
        m871a(jSONObject.toString(), dE, null);
        return dE;
    }

    public long m2139a(eu euVar, double d, JSONObject jSONObject) throws IOException, IllegalStateException, IllegalArgumentException {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Volume cannot be " + d);
        }
        JSONObject jSONObject2 = new JSONObject();
        long dE = dE();
        this.zR.m904a(dE, euVar);
        m2136w(true);
        try {
            jSONObject2.put("requestId", dE);
            jSONObject2.put("type", (Object) "SET_VOLUME");
            jSONObject2.put("mediaSessionId", dC());
            Object jSONObject3 = new JSONObject();
            jSONObject3.put("level", d);
            jSONObject2.put("volume", jSONObject3);
            if (jSONObject != null) {
                jSONObject2.put("customData", (Object) jSONObject);
            }
        } catch (JSONException e) {
        }
        m871a(jSONObject2.toString(), dE, null);
        return dE;
    }

    public long m2140a(eu euVar, long j, int i, JSONObject jSONObject) throws IOException, IllegalStateException {
        JSONObject jSONObject2 = new JSONObject();
        long dE = dE();
        this.zQ.m904a(dE, euVar);
        m2136w(true);
        try {
            jSONObject2.put("requestId", dE);
            jSONObject2.put("type", (Object) "SEEK");
            jSONObject2.put("mediaSessionId", dC());
            jSONObject2.put("currentTime", eo.m876m(j));
            if (i == 1) {
                jSONObject2.put("resumeState", (Object) "PLAYBACK_START");
            } else if (i == 2) {
                jSONObject2.put("resumeState", (Object) "PLAYBACK_PAUSE");
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", (Object) jSONObject);
            }
        } catch (JSONException e) {
        }
        m871a(jSONObject2.toString(), dE, null);
        return dE;
    }

    public long m2141a(eu euVar, MediaInfo mediaInfo, boolean z, long j, JSONObject jSONObject) throws IOException {
        JSONObject jSONObject2 = new JSONObject();
        long dE = dE();
        this.zM.m904a(dE, euVar);
        m2136w(true);
        try {
            jSONObject2.put("requestId", dE);
            jSONObject2.put("type", (Object) "LOAD");
            jSONObject2.put("media", mediaInfo.dB());
            jSONObject2.put("autoplay", z);
            jSONObject2.put("currentTime", eo.m876m(j));
            if (jSONObject != null) {
                jSONObject2.put("customData", (Object) jSONObject);
            }
        } catch (JSONException e) {
        }
        m871a(jSONObject2.toString(), dE, null);
        return dE;
    }

    public long m2142a(eu euVar, JSONObject jSONObject) throws IOException {
        JSONObject jSONObject2 = new JSONObject();
        long dE = dE();
        this.zN.m904a(dE, euVar);
        m2136w(true);
        try {
            jSONObject2.put("requestId", dE);
            jSONObject2.put("type", (Object) "PAUSE");
            jSONObject2.put("mediaSessionId", dC());
            if (jSONObject != null) {
                jSONObject2.put("customData", (Object) jSONObject);
            }
        } catch (JSONException e) {
        }
        m871a(jSONObject2.toString(), dE, null);
        return dE;
    }

    public long m2143a(eu euVar, boolean z, JSONObject jSONObject) throws IOException, IllegalStateException {
        JSONObject jSONObject2 = new JSONObject();
        long dE = dE();
        this.zS.m904a(dE, euVar);
        m2136w(true);
        try {
            jSONObject2.put("requestId", dE);
            jSONObject2.put("type", (Object) "SET_VOLUME");
            jSONObject2.put("mediaSessionId", dC());
            Object jSONObject3 = new JSONObject();
            jSONObject3.put("muted", z);
            jSONObject2.put("volume", jSONObject3);
            if (jSONObject != null) {
                jSONObject2.put("customData", (Object) jSONObject);
            }
        } catch (JSONException e) {
        }
        m871a(jSONObject2.toString(), dE, null);
        return dE;
    }

    public void m2144a(long j, int i) {
        this.zM.m906c(j, i);
        this.zN.m906c(j, i);
        this.zO.m906c(j, i);
        this.zP.m906c(j, i);
        this.zQ.m906c(j, i);
        this.zR.m906c(j, i);
        this.zS.m906c(j, i);
        this.zT.m906c(j, i);
    }

    public long m2145b(eu euVar, JSONObject jSONObject) throws IOException {
        JSONObject jSONObject2 = new JSONObject();
        long dE = dE();
        this.zP.m904a(dE, euVar);
        m2136w(true);
        try {
            jSONObject2.put("requestId", dE);
            jSONObject2.put("type", (Object) "STOP");
            jSONObject2.put("mediaSessionId", dC());
            if (jSONObject != null) {
                jSONObject2.put("customData", (Object) jSONObject);
            }
        } catch (JSONException e) {
        }
        m871a(jSONObject2.toString(), dE, null);
        return dE;
    }

    public long m2146c(eu euVar, JSONObject jSONObject) throws IOException, IllegalStateException {
        JSONObject jSONObject2 = new JSONObject();
        long dE = dE();
        this.zO.m904a(dE, euVar);
        m2136w(true);
        try {
            jSONObject2.put("requestId", dE);
            jSONObject2.put("type", (Object) "PLAY");
            jSONObject2.put("mediaSessionId", dC());
            if (jSONObject != null) {
                jSONObject2.put("customData", (Object) jSONObject);
            }
        } catch (JSONException e) {
        }
        m871a(jSONObject2.toString(), dE, null);
        return dE;
    }

    public long dC() throws IllegalStateException {
        if (this.zL != null) {
            return this.zL.dC();
        }
        throw new IllegalStateException("No current media session");
    }

    public void dF() {
        dS();
    }

    public long getApproximateStreamPosition() {
        MediaInfo mediaInfo = getMediaInfo();
        if (mediaInfo == null || this.zK == 0) {
            return 0;
        }
        double playbackRate = this.zL.getPlaybackRate();
        long streamPosition = this.zL.getStreamPosition();
        int playerState = this.zL.getPlayerState();
        if (playbackRate == 0.0d || playerState != 2) {
            return streamPosition;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.zK;
        long j = elapsedRealtime < 0 ? 0 : elapsedRealtime;
        if (j == 0) {
            return streamPosition;
        }
        elapsedRealtime = mediaInfo.getStreamDuration();
        streamPosition += (long) (((double) j) * playbackRate);
        if (streamPosition <= elapsedRealtime) {
            elapsedRealtime = streamPosition < 0 ? 0 : streamPosition;
        }
        return elapsedRealtime;
    }

    public MediaInfo getMediaInfo() {
        return this.zL == null ? null : this.zL.getMediaInfo();
    }

    public MediaStatus getMediaStatus() {
        return this.zL;
    }

    public long getStreamDuration() {
        MediaInfo mediaInfo = getMediaInfo();
        return mediaInfo != null ? mediaInfo.getStreamDuration() : 0;
    }

    protected void onMetadataUpdated() {
    }

    protected void onStatusUpdated() {
    }
}
