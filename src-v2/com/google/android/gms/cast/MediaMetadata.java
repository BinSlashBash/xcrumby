/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.text.TextUtils
 */
package com.google.android.gms.cast;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.internal.ew;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class MediaMetadata {
    public static final String KEY_ALBUM_ARTIST = "com.google.android.gms.cast.metadata.ALBUM_ARTIST";
    public static final String KEY_ALBUM_TITLE = "com.google.android.gms.cast.metadata.ALBUM_TITLE";
    public static final String KEY_ARTIST = "com.google.android.gms.cast.metadata.ARTIST";
    public static final String KEY_BROADCAST_DATE = "com.google.android.gms.cast.metadata.BROADCAST_DATE";
    public static final String KEY_COMPOSER = "com.google.android.gms.cast.metadata.COMPOSER";
    public static final String KEY_CREATION_DATE = "com.google.android.gms.cast.metadata.CREATION_DATE";
    public static final String KEY_DISC_NUMBER = "com.google.android.gms.cast.metadata.DISC_NUMBER";
    public static final String KEY_EPISODE_NUMBER = "com.google.android.gms.cast.metadata.EPISODE_NUMBER";
    public static final String KEY_HEIGHT = "com.google.android.gms.cast.metadata.HEIGHT";
    public static final String KEY_LOCATION_LATITUDE = "com.google.android.gms.cast.metadata.LOCATION_LATITUDE";
    public static final String KEY_LOCATION_LONGITUDE = "com.google.android.gms.cast.metadata.LOCATION_LONGITUDE";
    public static final String KEY_LOCATION_NAME = "com.google.android.gms.cast.metadata.LOCATION_NAME";
    public static final String KEY_RELEASE_DATE = "com.google.android.gms.cast.metadata.RELEASE_DATE";
    public static final String KEY_SEASON_NUMBER = "com.google.android.gms.cast.metadata.SEASON_NUMBER";
    public static final String KEY_SERIES_TITLE = "com.google.android.gms.cast.metadata.SERIES_TITLE";
    public static final String KEY_STUDIO = "com.google.android.gms.cast.metadata.STUDIO";
    public static final String KEY_SUBTITLE = "com.google.android.gms.cast.metadata.SUBTITLE";
    public static final String KEY_TITLE = "com.google.android.gms.cast.metadata.TITLE";
    public static final String KEY_TRACK_NUMBER = "com.google.android.gms.cast.metadata.TRACK_NUMBER";
    public static final String KEY_WIDTH = "com.google.android.gms.cast.metadata.WIDTH";
    public static final int MEDIA_TYPE_GENERIC = 0;
    public static final int MEDIA_TYPE_MOVIE = 1;
    public static final int MEDIA_TYPE_MUSIC_TRACK = 3;
    public static final int MEDIA_TYPE_PHOTO = 4;
    public static final int MEDIA_TYPE_TV_SHOW = 2;
    public static final int MEDIA_TYPE_USER = 100;
    private static final String[] yp = new String[]{null, "String", "int", "double", "ISO-8601 date String"};
    private static final a yq = new a().a("com.google.android.gms.cast.metadata.CREATION_DATE", "creationDateTime", 4).a("com.google.android.gms.cast.metadata.RELEASE_DATE", "releaseDate", 4).a("com.google.android.gms.cast.metadata.BROADCAST_DATE", "originalAirdate", 4).a("com.google.android.gms.cast.metadata.TITLE", "title", 1).a("com.google.android.gms.cast.metadata.SUBTITLE", "subtitle", 1).a("com.google.android.gms.cast.metadata.ARTIST", "artist", 1).a("com.google.android.gms.cast.metadata.ALBUM_ARTIST", "albumArtist", 1).a("com.google.android.gms.cast.metadata.ALBUM_TITLE", "albumName", 1).a("com.google.android.gms.cast.metadata.COMPOSER", "composer", 1).a("com.google.android.gms.cast.metadata.DISC_NUMBER", "discNumber", 2).a("com.google.android.gms.cast.metadata.TRACK_NUMBER", "trackNumber", 2).a("com.google.android.gms.cast.metadata.SEASON_NUMBER", "season", 2).a("com.google.android.gms.cast.metadata.EPISODE_NUMBER", "episode", 2).a("com.google.android.gms.cast.metadata.SERIES_TITLE", "seriesTitle", 1).a("com.google.android.gms.cast.metadata.STUDIO", "studio", 1).a("com.google.android.gms.cast.metadata.WIDTH", "width", 2).a("com.google.android.gms.cast.metadata.HEIGHT", "height", 2).a("com.google.android.gms.cast.metadata.LOCATION_NAME", "location", 1).a("com.google.android.gms.cast.metadata.LOCATION_LATITUDE", "latitude", 3).a("com.google.android.gms.cast.metadata.LOCATION_LONGITUDE", "longitude", 3);
    private final List<WebImage> xJ = new ArrayList<WebImage>();
    private final Bundle yr = new Bundle();
    private int ys;

    public MediaMetadata() {
        this(0);
    }

    public MediaMetadata(int n2) {
        this.ys = n2;
    }

    /*
     * Exception decompiling
     */
    private /* varargs */ void a(JSONObject var1_1, String ... var2_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
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

    private boolean a(Bundle bundle, Bundle bundle2) {
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (String string2 : bundle.keySet()) {
            Object object = bundle.get(string2);
            Object object2 = bundle2.get(string2);
            if (object instanceof Bundle && object2 instanceof Bundle && !this.a((Bundle)object, (Bundle)object2)) {
                return false;
            }
            if (!(object == null ? object2 != null || !bundle2.containsKey(string2) : !object.equals(object2))) continue;
            return false;
        }
        return true;
    }

    /*
     * Exception decompiling
     */
    private /* varargs */ void b(JSONObject var1_1, String ... var2_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:486)
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

    private void d(String string2, int n2) throws IllegalArgumentException {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("null and empty keys are not allowed");
        }
        int n3 = yq.T(string2);
        if (n3 != n2 && n3 != 0) {
            throw new IllegalArgumentException("Value for " + string2 + " must be a " + yp[n2]);
        }
    }

    public void addImage(WebImage webImage) {
        this.xJ.add(webImage);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void c(JSONObject jSONObject) {
        this.clear();
        this.ys = 0;
        try {
            this.ys = jSONObject.getInt("metadataType");
        }
        catch (JSONException var2_2) {}
        ew.a(this.xJ, jSONObject);
        switch (this.ys) {
            default: {
                this.b(jSONObject, new String[0]);
                return;
            }
            case 0: {
                this.b(jSONObject, "com.google.android.gms.cast.metadata.TITLE", "com.google.android.gms.cast.metadata.ARTIST", "com.google.android.gms.cast.metadata.SUBTITLE", "com.google.android.gms.cast.metadata.RELEASE_DATE");
                return;
            }
            case 1: {
                this.b(jSONObject, "com.google.android.gms.cast.metadata.TITLE", "com.google.android.gms.cast.metadata.STUDIO", "com.google.android.gms.cast.metadata.SUBTITLE", "com.google.android.gms.cast.metadata.RELEASE_DATE");
                return;
            }
            case 2: {
                this.b(jSONObject, "com.google.android.gms.cast.metadata.TITLE", "com.google.android.gms.cast.metadata.SERIES_TITLE", "com.google.android.gms.cast.metadata.SEASON_NUMBER", "com.google.android.gms.cast.metadata.EPISODE_NUMBER", "com.google.android.gms.cast.metadata.BROADCAST_DATE");
                return;
            }
            case 3: {
                this.b(jSONObject, "com.google.android.gms.cast.metadata.TITLE", "com.google.android.gms.cast.metadata.ALBUM_TITLE", "com.google.android.gms.cast.metadata.ARTIST", "com.google.android.gms.cast.metadata.ALBUM_ARTIST", "com.google.android.gms.cast.metadata.COMPOSER", "com.google.android.gms.cast.metadata.TRACK_NUMBER", "com.google.android.gms.cast.metadata.DISC_NUMBER", "com.google.android.gms.cast.metadata.RELEASE_DATE");
                return;
            }
            case 4: 
        }
        this.b(jSONObject, "com.google.android.gms.cast.metadata.TITLE", "com.google.android.gms.cast.metadata.ARTIST", "com.google.android.gms.cast.metadata.LOCATION_NAME", "com.google.android.gms.cast.metadata.LOCATION_LATITUDE", "com.google.android.gms.cast.metadata.LOCATION_LONGITUDE", "com.google.android.gms.cast.metadata.WIDTH", "com.google.android.gms.cast.metadata.HEIGHT", "com.google.android.gms.cast.metadata.CREATION_DATE");
    }

    public void clear() {
        this.yr.clear();
        this.xJ.clear();
    }

    public void clearImages() {
        this.xJ.clear();
    }

    public boolean containsKey(String string2) {
        return this.yr.containsKey(string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JSONObject dB() {
        JSONObject jSONObject;
        jSONObject = new JSONObject();
        try {
            jSONObject.put("metadataType", this.ys);
        }
        catch (JSONException var2_2) {}
        ew.a(jSONObject, this.xJ);
        switch (this.ys) {
            default: {
                this.a(jSONObject, new String[0]);
                return jSONObject;
            }
            case 0: {
                this.a(jSONObject, "com.google.android.gms.cast.metadata.TITLE", "com.google.android.gms.cast.metadata.ARTIST", "com.google.android.gms.cast.metadata.SUBTITLE", "com.google.android.gms.cast.metadata.RELEASE_DATE");
                return jSONObject;
            }
            case 1: {
                this.a(jSONObject, "com.google.android.gms.cast.metadata.TITLE", "com.google.android.gms.cast.metadata.STUDIO", "com.google.android.gms.cast.metadata.SUBTITLE", "com.google.android.gms.cast.metadata.RELEASE_DATE");
                return jSONObject;
            }
            case 2: {
                this.a(jSONObject, "com.google.android.gms.cast.metadata.TITLE", "com.google.android.gms.cast.metadata.SERIES_TITLE", "com.google.android.gms.cast.metadata.SEASON_NUMBER", "com.google.android.gms.cast.metadata.EPISODE_NUMBER", "com.google.android.gms.cast.metadata.BROADCAST_DATE");
                return jSONObject;
            }
            case 3: {
                this.a(jSONObject, "com.google.android.gms.cast.metadata.TITLE", "com.google.android.gms.cast.metadata.ARTIST", "com.google.android.gms.cast.metadata.ALBUM_TITLE", "com.google.android.gms.cast.metadata.ALBUM_ARTIST", "com.google.android.gms.cast.metadata.COMPOSER", "com.google.android.gms.cast.metadata.TRACK_NUMBER", "com.google.android.gms.cast.metadata.DISC_NUMBER", "com.google.android.gms.cast.metadata.RELEASE_DATE");
                return jSONObject;
            }
            case 4: 
        }
        this.a(jSONObject, "com.google.android.gms.cast.metadata.TITLE", "com.google.android.gms.cast.metadata.ARTIST", "com.google.android.gms.cast.metadata.LOCATION_NAME", "com.google.android.gms.cast.metadata.LOCATION_LATITUDE", "com.google.android.gms.cast.metadata.LOCATION_LONGITUDE", "com.google.android.gms.cast.metadata.WIDTH", "com.google.android.gms.cast.metadata.HEIGHT", "com.google.android.gms.cast.metadata.CREATION_DATE");
        return jSONObject;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MediaMetadata)) {
            return false;
        }
        object = (MediaMetadata)object;
        if (!this.a(this.yr, object.yr)) return false;
        if (this.xJ.equals(object.xJ)) return true;
        return false;
    }

    public Calendar getDate(String string2) {
        this.d(string2, 4);
        string2 = this.yr.getString(string2);
        if (string2 != null) {
            return ew.ac(string2);
        }
        return null;
    }

    public String getDateAsString(String string2) {
        this.d(string2, 4);
        return this.yr.getString(string2);
    }

    public double getDouble(String string2) {
        this.d(string2, 3);
        return this.yr.getDouble(string2);
    }

    public List<WebImage> getImages() {
        return this.xJ;
    }

    public int getInt(String string2) {
        this.d(string2, 2);
        return this.yr.getInt(string2);
    }

    public int getMediaType() {
        return this.ys;
    }

    public String getString(String string2) {
        this.d(string2, 1);
        return this.yr.getString(string2);
    }

    public boolean hasImages() {
        if (this.xJ != null && !this.xJ.isEmpty()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        Iterator iterator = this.yr.keySet().iterator();
        int n2 = 17;
        while (iterator.hasNext()) {
            String string2 = (String)iterator.next();
            n2 = this.yr.get(string2).hashCode() + n2 * 31;
        }
        return n2 * 31 + this.xJ.hashCode();
    }

    public Set<String> keySet() {
        return this.yr.keySet();
    }

    public void putDate(String string2, Calendar calendar) {
        this.d(string2, 4);
        this.yr.putString(string2, ew.a(calendar));
    }

    public void putDouble(String string2, double d2) {
        this.d(string2, 3);
        this.yr.putDouble(string2, d2);
    }

    public void putInt(String string2, int n2) {
        this.d(string2, 2);
        this.yr.putInt(string2, n2);
    }

    public void putString(String string2, String string3) {
        this.d(string2, 1);
        this.yr.putString(string2, string3);
    }

    private static class a {
        private final Map<String, String> yt = new HashMap<String, String>();
        private final Map<String, String> yu = new HashMap<String, String>();
        private final Map<String, Integer> yv = new HashMap<String, Integer>();

        public String R(String string2) {
            return this.yt.get(string2);
        }

        public String S(String string2) {
            return this.yu.get(string2);
        }

        public int T(String object) {
            if ((object = this.yv.get(object)) != null) {
                return object.intValue();
            }
            return 0;
        }

        public a a(String string2, String string3, int n2) {
            this.yt.put(string2, string3);
            this.yu.put(string3, string2);
            this.yv.put(string2, n2);
            return this;
        }
    }

}

