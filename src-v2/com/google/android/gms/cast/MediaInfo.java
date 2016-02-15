/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.cast;

import android.text.TextUtils;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.internal.eo;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.gp;
import org.json.JSONException;
import org.json.JSONObject;

public final class MediaInfo {
    public static final int STREAM_TYPE_BUFFERED = 1;
    public static final int STREAM_TYPE_INVALID = -1;
    public static final int STREAM_TYPE_LIVE = 2;
    public static final int STREAM_TYPE_NONE = 0;
    private final String yi;
    private int yj;
    private String yk;
    private MediaMetadata yl;
    private long ym;
    private JSONObject yn;

    MediaInfo(String string2) throws IllegalArgumentException {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("content ID cannot be null or empty");
        }
        this.yi = string2;
        this.yj = -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    MediaInfo(JSONObject jSONObject) throws JSONException {
        this.yi = jSONObject.getString("contentId");
        Object object = jSONObject.getString("streamType");
        this.yj = "NONE".equals(object) ? 0 : ("BUFFERED".equals(object) ? 1 : ("LIVE".equals(object) ? 2 : -1));
        this.yk = jSONObject.getString("contentType");
        if (jSONObject.has("metadata")) {
            object = jSONObject.getJSONObject("metadata");
            this.yl = new MediaMetadata(object.getInt("metadataType"));
            this.yl.c((JSONObject)object);
        }
        this.ym = eo.b(jSONObject.optDouble("duration", 0.0));
        this.yn = jSONObject.optJSONObject("customData");
    }

    void a(MediaMetadata mediaMetadata) {
        this.yl = mediaMetadata;
    }

    void b(JSONObject jSONObject) {
        this.yn = jSONObject;
    }

    void dA() throws IllegalArgumentException {
        if (TextUtils.isEmpty((CharSequence)this.yi)) {
            throw new IllegalArgumentException("content ID cannot be null or empty");
        }
        if (TextUtils.isEmpty((CharSequence)this.yk)) {
            throw new IllegalArgumentException("content type cannot be null or empty");
        }
        if (this.yj == -1) {
            throw new IllegalArgumentException("a valid stream type must be specified");
        }
    }

    /*
     * Exception decompiling
     */
    public JSONObject dB() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:366)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:422)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // org.benf.cfr.reader.Main.main(Main.java:178)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl2 = true;
        boolean bl3 = false;
        if (this == object) {
            return true;
        }
        boolean bl4 = bl3;
        if (!(object instanceof MediaInfo)) return bl4;
        object = (MediaInfo)object;
        boolean bl5 = this.yn == null;
        boolean bl6 = object.yn == null;
        bl4 = bl3;
        if (bl5 != bl6) return bl4;
        if (this.yn != null && object.yn != null) {
            bl4 = bl3;
            if (!gp.d(this.yn, object.yn)) return bl4;
        }
        if (!eo.a(this.yi, object.yi)) return false;
        if (this.yj != object.yj) return false;
        if (!eo.a(this.yk, object.yk)) return false;
        if (!eo.a(this.yl, object.yl)) return false;
        if (this.ym != object.ym) return false;
        return bl2;
    }

    public String getContentId() {
        return this.yi;
    }

    public String getContentType() {
        return this.yk;
    }

    public JSONObject getCustomData() {
        return this.yn;
    }

    public MediaMetadata getMetadata() {
        return this.yl;
    }

    public long getStreamDuration() {
        return this.ym;
    }

    public int getStreamType() {
        return this.yj;
    }

    public int hashCode() {
        return fo.hashCode(this.yi, this.yj, this.yk, this.yl, this.ym, String.valueOf(this.yn));
    }

    void k(long l2) throws IllegalArgumentException {
        if (l2 < 0) {
            throw new IllegalArgumentException("Stream duration cannot be negative");
        }
        this.ym = l2;
    }

    void setContentType(String string2) throws IllegalArgumentException {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("content type cannot be null or empty");
        }
        this.yk = string2;
    }

    void setStreamType(int n2) throws IllegalArgumentException {
        if (n2 < -1 || n2 > 2) {
            throw new IllegalArgumentException("invalid stream type");
        }
        this.yj = n2;
    }

    public static class Builder {
        private final MediaInfo yo;

        public Builder(String string2) throws IllegalArgumentException {
            if (TextUtils.isEmpty((CharSequence)string2)) {
                throw new IllegalArgumentException("Content ID cannot be empty");
            }
            this.yo = new MediaInfo(string2);
        }

        public MediaInfo build() throws IllegalArgumentException {
            this.yo.dA();
            return this.yo;
        }

        public Builder setContentType(String string2) throws IllegalArgumentException {
            this.yo.setContentType(string2);
            return this;
        }

        public Builder setCustomData(JSONObject jSONObject) {
            this.yo.b(jSONObject);
            return this;
        }

        public Builder setMetadata(MediaMetadata mediaMetadata) {
            this.yo.a(mediaMetadata);
            return this;
        }

        public Builder setStreamDuration(long l2) throws IllegalArgumentException {
            this.yo.k(l2);
            return this;
        }

        public Builder setStreamType(int n2) throws IllegalArgumentException {
            this.yo.setStreamType(n2);
            return this;
        }
    }

}

