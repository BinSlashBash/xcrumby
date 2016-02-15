/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.cast;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.internal.eo;
import org.json.JSONException;
import org.json.JSONObject;

public final class MediaStatus {
    public static final long COMMAND_PAUSE = 1;
    public static final long COMMAND_SEEK = 2;
    public static final long COMMAND_SET_VOLUME = 4;
    public static final long COMMAND_SKIP_BACKWARD = 32;
    public static final long COMMAND_SKIP_FORWARD = 16;
    public static final long COMMAND_TOGGLE_MUTE = 8;
    public static final int IDLE_REASON_CANCELED = 2;
    public static final int IDLE_REASON_ERROR = 4;
    public static final int IDLE_REASON_FINISHED = 1;
    public static final int IDLE_REASON_INTERRUPTED = 3;
    public static final int IDLE_REASON_NONE = 0;
    public static final int PLAYER_STATE_BUFFERING = 4;
    public static final int PLAYER_STATE_IDLE = 1;
    public static final int PLAYER_STATE_PAUSED = 3;
    public static final int PLAYER_STATE_PLAYING = 2;
    public static final int PLAYER_STATE_UNKNOWN = 0;
    private long yA;
    private long yB;
    private double yC;
    private boolean yD;
    private JSONObject yn;
    private MediaInfo yo;
    private long yw;
    private double yx;
    private int yy;
    private int yz;

    public MediaStatus(JSONObject jSONObject) throws JSONException {
        this.a(jSONObject, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int a(JSONObject jSONObject, int n2) throws JSONException {
        double d2;
        int n3;
        int n4;
        Object object;
        int n5 = 2;
        long l2 = jSONObject.getLong("mediaSessionId");
        if (l2 != this.yw) {
            this.yw = l2;
            n4 = 1;
        } else {
            n4 = 0;
        }
        int n6 = n4;
        if (jSONObject.has("playerState")) {
            object = jSONObject.getString("playerState");
            n3 = object.equals("IDLE") ? 1 : (object.equals("PLAYING") ? 2 : (object.equals("PAUSED") ? 3 : (object.equals("BUFFERING") ? 4 : 0)));
            int n7 = n4;
            if (n3 != this.yy) {
                this.yy = n3;
                n7 = n4 | 2;
            }
            n6 = n7;
            if (n3 == 1) {
                n6 = n7;
                if (jSONObject.has("idleReason")) {
                    object = jSONObject.getString("idleReason");
                    n3 = object.equals("CANCELLED") ? n5 : (object.equals("INTERRUPTED") ? 3 : (object.equals("FINISHED") ? 1 : (object.equals("ERROR") ? 4 : 0)));
                    n6 = n7;
                    if (n3 != this.yz) {
                        this.yz = n3;
                        n6 = n7 | 2;
                    }
                }
            }
        }
        n3 = n6;
        if (jSONObject.has("playbackRate")) {
            d2 = jSONObject.getDouble("playbackRate");
            n3 = n6;
            if (this.yx != d2) {
                this.yx = d2;
                n3 = n6 | 2;
            }
        }
        n4 = n3;
        if (jSONObject.has("currentTime")) {
            n4 = n3;
            if ((n2 & 2) == 0) {
                l2 = eo.b(jSONObject.getDouble("currentTime"));
                n4 = n3;
                if (l2 != this.yA) {
                    this.yA = l2;
                    n4 = n3 | 2;
                }
            }
        }
        n3 = n4;
        if (jSONObject.has("supportedMediaCommands")) {
            l2 = jSONObject.getLong("supportedMediaCommands");
            n3 = n4;
            if (l2 != this.yB) {
                this.yB = l2;
                n3 = n4 | 2;
            }
        }
        n4 = n3;
        if (jSONObject.has("volume")) {
            n4 = n3;
            if ((n2 & 1) == 0) {
                object = jSONObject.getJSONObject("volume");
                d2 = object.getDouble("level");
                n2 = n3;
                if (d2 != this.yC) {
                    this.yC = d2;
                    n2 = n3 | 2;
                }
                boolean bl2 = object.getBoolean("muted");
                n4 = n2;
                if (bl2 != this.yD) {
                    this.yD = bl2;
                    n4 = n2 | 2;
                }
            }
        }
        n2 = n4;
        if (jSONObject.has("customData")) {
            this.yn = jSONObject.getJSONObject("customData");
            n2 = n4 | 2;
        }
        n3 = n2;
        if (!jSONObject.has("media")) return n3;
        jSONObject = jSONObject.getJSONObject("media");
        this.yo = new MediaInfo(jSONObject);
        n3 = n2 |= 2;
        if (!jSONObject.has("metadata")) return n3;
        return n2 | 4;
    }

    public long dC() {
        return this.yw;
    }

    public JSONObject getCustomData() {
        return this.yn;
    }

    public int getIdleReason() {
        return this.yz;
    }

    public MediaInfo getMediaInfo() {
        return this.yo;
    }

    public double getPlaybackRate() {
        return this.yx;
    }

    public int getPlayerState() {
        return this.yy;
    }

    public long getStreamPosition() {
        return this.yA;
    }

    public double getStreamVolume() {
        return this.yC;
    }

    public boolean isMediaCommandSupported(long l2) {
        if ((this.yB & l2) != 0) {
            return true;
        }
        return false;
    }

    public boolean isMute() {
        return this.yD;
    }
}

