/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.SystemClock
 */
package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.internal.em;
import com.google.android.gms.internal.eo;
import com.google.android.gms.internal.er;
import com.google.android.gms.internal.eu;
import com.google.android.gms.internal.ev;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class es
extends em {
    private static final String NAMESPACE = eo.X("com.google.cast.media");
    private static final long zG = TimeUnit.HOURS.toMillis(24);
    private static final long zH = TimeUnit.HOURS.toMillis(24);
    private static final long zI = TimeUnit.HOURS.toMillis(24);
    private static final long zJ = TimeUnit.SECONDS.toMillis(1);
    private final Handler mHandler = new Handler(Looper.getMainLooper());
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

    public es() {
        this(null);
    }

    public es(String string2) {
        super(NAMESPACE, "MediaControlChannel", string2);
        this.zU = new a();
        this.zM = new ev(zH);
        this.zN = new ev(zG);
        this.zO = new ev(zG);
        this.zP = new ev(zG);
        this.zQ = new ev(zI);
        this.zR = new ev(zG);
        this.zS = new ev(zG);
        this.zT = new ev(zG);
        this.dS();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(long l2, JSONObject jSONObject) throws JSONException {
        int n2;
        boolean bl2;
        int n3;
        int n4;
        block8 : {
            n3 = 1;
            bl2 = this.zM.n(l2);
            n4 = this.zQ.dU() && !this.zQ.n(l2) ? 1 : 0;
            if (this.zR.dU()) {
                n2 = n3;
                if (!this.zR.n(l2)) break block8;
            }
            n2 = this.zS.dU() && !this.zS.n(l2) ? n3 : 0;
        }
        n4 = n4 != 0 ? 2 : 0;
        n3 = n4;
        if (n2 != 0) {
            n3 = n4 | 1;
        }
        if (bl2 || this.zL == null) {
            this.zL = new MediaStatus(jSONObject);
            this.zK = SystemClock.elapsedRealtime();
            n4 = 7;
        } else {
            n4 = this.zL.a(jSONObject, n3);
        }
        if ((n4 & 1) != 0) {
            this.zK = SystemClock.elapsedRealtime();
            this.onStatusUpdated();
        }
        if ((n4 & 2) != 0) {
            this.zK = SystemClock.elapsedRealtime();
            this.onStatusUpdated();
        }
        if ((n4 & 4) != 0) {
            this.onMetadataUpdated();
        }
        this.zM.c(l2, 0);
        this.zN.c(l2, 0);
        this.zO.c(l2, 0);
        this.zP.c(l2, 0);
        this.zQ.c(l2, 0);
        this.zR.c(l2, 0);
        this.zS.c(l2, 0);
        this.zT.c(l2, 0);
    }

    private void dS() {
        this.w(false);
        this.zK = 0;
        this.zL = null;
        this.zM.clear();
        this.zQ.clear();
        this.zR.clear();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void w(boolean bl2) {
        if (this.zV == bl2) return;
        this.zV = bl2;
        if (bl2) {
            this.mHandler.postDelayed(this.zU, zJ);
            return;
        }
        this.mHandler.removeCallbacks(this.zU);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void U(String string2) {
        long l2;
        String string3;
        Object object;
        this.yY.b("message received: %s", string2);
        try {
            object = new JSONObject(string2);
            string3 = object.getString("type");
            l2 = object.optLong("requestId", -1);
            if (string3.equals("MEDIA_STATUS")) {
                if ((object = object.getJSONArray("status")).length() > 0) {
                    this.a(l2, object.getJSONObject(0));
                    return;
                }
                this.zL = null;
                this.onStatusUpdated();
                this.onMetadataUpdated();
                this.zT.c(l2, 0);
                return;
            }
            if (string3.equals("INVALID_PLAYER_STATE")) {
                this.yY.d("received unexpected error: Invalid Player State.", new Object[0]);
                object = object.optJSONObject("customData");
                this.zM.b(l2, 1, (JSONObject)object);
                this.zN.b(l2, 1, (JSONObject)object);
                this.zO.b(l2, 1, (JSONObject)object);
                this.zP.b(l2, 1, (JSONObject)object);
                this.zQ.b(l2, 1, (JSONObject)object);
                this.zR.b(l2, 1, (JSONObject)object);
                this.zS.b(l2, 1, (JSONObject)object);
                this.zT.b(l2, 1, (JSONObject)object);
                return;
            }
        }
        catch (JSONException var4_3) {
            this.yY.d("Message is malformed (%s); ignoring: %s", var4_3.getMessage(), string2);
            return;
        }
        if (string3.equals("LOAD_FAILED")) {
            object = object.optJSONObject("customData");
            this.zM.b(l2, 1, (JSONObject)object);
            return;
        }
        if (string3.equals("LOAD_CANCELLED")) {
            object = object.optJSONObject("customData");
            this.zM.b(l2, 2, (JSONObject)object);
            return;
        }
        if (string3.equals("INVALID_REQUEST")) {
            this.yY.d("received unexpected error: Invalid Request.", new Object[0]);
            object = object.optJSONObject("customData");
            this.zM.b(l2, 1, (JSONObject)object);
            this.zN.b(l2, 1, (JSONObject)object);
            this.zO.b(l2, 1, (JSONObject)object);
            this.zP.b(l2, 1, (JSONObject)object);
            this.zQ.b(l2, 1, (JSONObject)object);
            this.zR.b(l2, 1, (JSONObject)object);
            this.zS.b(l2, 1, (JSONObject)object);
            this.zT.b(l2, 1, (JSONObject)object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long a(eu eu2) throws IOException {
        long l2;
        JSONObject jSONObject;
        jSONObject = new JSONObject();
        l2 = this.dE();
        this.zT.a(l2, eu2);
        this.w(true);
        try {
            jSONObject.put("requestId", l2);
            jSONObject.put("type", "GET_STATUS");
            if (this.zL != null) {
                jSONObject.put("mediaSessionId", this.zL.dC());
            }
        }
        catch (JSONException var1_2) {}
        this.a(jSONObject.toString(), l2, null);
        return l2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long a(eu object, double d2, JSONObject jSONObject) throws IOException, IllegalStateException, IllegalArgumentException {
        JSONObject jSONObject2;
        long l2;
        if (Double.isInfinite(d2) || Double.isNaN(d2)) {
            throw new IllegalArgumentException("Volume cannot be " + d2);
        }
        jSONObject2 = new JSONObject();
        l2 = this.dE();
        this.zR.a(l2, (eu)object);
        this.w(true);
        try {
            jSONObject2.put("requestId", l2);
            jSONObject2.put("type", "SET_VOLUME");
            jSONObject2.put("mediaSessionId", this.dC());
            object = new JSONObject();
            object.put("level", d2);
            jSONObject2.put("volume", object);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        }
        catch (JSONException var1_2) {}
        this.a(jSONObject2.toString(), l2, null);
        return l2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long a(eu eu2, long l2, int n2, JSONObject jSONObject) throws IOException, IllegalStateException {
        long l3;
        JSONObject jSONObject2;
        jSONObject2 = new JSONObject();
        l3 = this.dE();
        this.zQ.a(l3, eu2);
        this.w(true);
        try {
            jSONObject2.put("requestId", l3);
            jSONObject2.put("type", "SEEK");
            jSONObject2.put("mediaSessionId", this.dC());
            jSONObject2.put("currentTime", eo.m(l2));
            if (n2 == 1) {
                jSONObject2.put("resumeState", "PLAYBACK_START");
            } else if (n2 == 2) {
                jSONObject2.put("resumeState", "PLAYBACK_PAUSE");
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        }
        catch (JSONException var1_2) {}
        this.a(jSONObject2.toString(), l3, null);
        return l3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long a(eu eu2, MediaInfo mediaInfo, boolean bl2, long l2, JSONObject jSONObject) throws IOException {
        JSONObject jSONObject2;
        long l3;
        jSONObject2 = new JSONObject();
        l3 = this.dE();
        this.zM.a(l3, eu2);
        this.w(true);
        try {
            jSONObject2.put("requestId", l3);
            jSONObject2.put("type", "LOAD");
            jSONObject2.put("media", mediaInfo.dB());
            jSONObject2.put("autoplay", bl2);
            jSONObject2.put("currentTime", eo.m(l2));
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        }
        catch (JSONException var1_2) {}
        this.a(jSONObject2.toString(), l3, null);
        return l3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long a(eu eu2, JSONObject jSONObject) throws IOException {
        long l2;
        JSONObject jSONObject2;
        jSONObject2 = new JSONObject();
        l2 = this.dE();
        this.zN.a(l2, eu2);
        this.w(true);
        try {
            jSONObject2.put("requestId", l2);
            jSONObject2.put("type", "PAUSE");
            jSONObject2.put("mediaSessionId", this.dC());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        }
        catch (JSONException var1_2) {}
        this.a(jSONObject2.toString(), l2, null);
        return l2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long a(eu object, boolean bl2, JSONObject jSONObject) throws IOException, IllegalStateException {
        JSONObject jSONObject2;
        long l2;
        jSONObject2 = new JSONObject();
        l2 = this.dE();
        this.zS.a(l2, (eu)object);
        this.w(true);
        try {
            jSONObject2.put("requestId", l2);
            jSONObject2.put("type", "SET_VOLUME");
            jSONObject2.put("mediaSessionId", this.dC());
            object = new JSONObject();
            object.put("muted", bl2);
            jSONObject2.put("volume", object);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        }
        catch (JSONException var1_2) {}
        this.a(jSONObject2.toString(), l2, null);
        return l2;
    }

    @Override
    public void a(long l2, int n2) {
        this.zM.c(l2, n2);
        this.zN.c(l2, n2);
        this.zO.c(l2, n2);
        this.zP.c(l2, n2);
        this.zQ.c(l2, n2);
        this.zR.c(l2, n2);
        this.zS.c(l2, n2);
        this.zT.c(l2, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long b(eu eu2, JSONObject jSONObject) throws IOException {
        long l2;
        JSONObject jSONObject2;
        jSONObject2 = new JSONObject();
        l2 = this.dE();
        this.zP.a(l2, eu2);
        this.w(true);
        try {
            jSONObject2.put("requestId", l2);
            jSONObject2.put("type", "STOP");
            jSONObject2.put("mediaSessionId", this.dC());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        }
        catch (JSONException var1_2) {}
        this.a(jSONObject2.toString(), l2, null);
        return l2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long c(eu eu2, JSONObject jSONObject) throws IOException, IllegalStateException {
        long l2;
        JSONObject jSONObject2;
        jSONObject2 = new JSONObject();
        l2 = this.dE();
        this.zO.a(l2, eu2);
        this.w(true);
        try {
            jSONObject2.put("requestId", l2);
            jSONObject2.put("type", "PLAY");
            jSONObject2.put("mediaSessionId", this.dC());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        }
        catch (JSONException var1_2) {}
        this.a(jSONObject2.toString(), l2, null);
        return l2;
    }

    public long dC() throws IllegalStateException {
        if (this.zL == null) {
            throw new IllegalStateException("No current media session");
        }
        return this.zL.dC();
    }

    @Override
    public void dF() {
        this.dS();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public long getApproximateStreamPosition() {
        MediaInfo mediaInfo = this.getMediaInfo();
        if (mediaInfo == null) {
            return 0;
        }
        if (this.zK == 0) return 0;
        double d2 = this.zL.getPlaybackRate();
        long l2 = this.zL.getStreamPosition();
        int n2 = this.zL.getPlayerState();
        if (d2 == 0.0) return l2;
        if (n2 != 2) {
            return l2;
        }
        long l3 = SystemClock.elapsedRealtime() - this.zK;
        if (l3 < 0) {
            return l2;
        }
        if (l3 == 0) {
            return l2;
        }
        long l4 = mediaInfo.getStreamDuration();
        if ((l3 = l2 + (long)((double)l3 * d2)) > l4) {
            return l4;
        }
        if (l3 >= 0) return l3;
        return 0;
    }

    public MediaInfo getMediaInfo() {
        if (this.zL == null) {
            return null;
        }
        return this.zL.getMediaInfo();
    }

    public MediaStatus getMediaStatus() {
        return this.zL;
    }

    public long getStreamDuration() {
        MediaInfo mediaInfo = this.getMediaInfo();
        if (mediaInfo != null) {
            return mediaInfo.getStreamDuration();
        }
        return 0;
    }

    protected void onMetadataUpdated() {
    }

    protected void onStatusUpdated() {
    }

    private class a
    implements Runnable {
        private a() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            boolean bl2 = false;
            es.this.zV = false;
            long l2 = SystemClock.elapsedRealtime();
            es.this.zM.d(l2, 3);
            es.this.zN.d(l2, 3);
            es.this.zO.d(l2, 3);
            es.this.zP.d(l2, 3);
            es.this.zQ.d(l2, 3);
            es.this.zR.d(l2, 3);
            es.this.zS.d(l2, 3);
            es.this.zT.d(l2, 3);
            Object object = ev.Ab;
            synchronized (object) {
                if (es.this.zM.dU() || es.this.zQ.dU() || es.this.zR.dU() || es.this.zS.dU() || es.this.zT.dU()) {
                    bl2 = true;
                }
            }
            es.this.w(bl2);
        }
    }

}

