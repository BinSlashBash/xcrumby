package com.google.android.gms.cast;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.internal.ew;
import com.google.android.gms.plus.PlusShare;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
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
    private static final String[] yp;
    private static final C0235a yq;
    private final List<WebImage> xJ;
    private final Bundle yr;
    private int ys;

    /* renamed from: com.google.android.gms.cast.MediaMetadata.a */
    private static class C0235a {
        private final Map<String, String> yt;
        private final Map<String, String> yu;
        private final Map<String, Integer> yv;

        public C0235a() {
            this.yt = new HashMap();
            this.yu = new HashMap();
            this.yv = new HashMap();
        }

        public String m87R(String str) {
            return (String) this.yt.get(str);
        }

        public String m88S(String str) {
            return (String) this.yu.get(str);
        }

        public int m89T(String str) {
            Integer num = (Integer) this.yv.get(str);
            return num != null ? num.intValue() : MediaMetadata.MEDIA_TYPE_GENERIC;
        }

        public C0235a m90a(String str, String str2, int i) {
            this.yt.put(str, str2);
            this.yu.put(str2, str);
            this.yv.put(str, Integer.valueOf(i));
            return this;
        }
    }

    static {
        yp = new String[]{null, "String", "int", "double", "ISO-8601 date String"};
        yq = new C0235a().m90a(KEY_CREATION_DATE, "creationDateTime", MEDIA_TYPE_PHOTO).m90a(KEY_RELEASE_DATE, "releaseDate", MEDIA_TYPE_PHOTO).m90a(KEY_BROADCAST_DATE, "originalAirdate", MEDIA_TYPE_PHOTO).m90a(KEY_TITLE, PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE, MEDIA_TYPE_MOVIE).m90a(KEY_SUBTITLE, "subtitle", MEDIA_TYPE_MOVIE).m90a(KEY_ARTIST, "artist", MEDIA_TYPE_MOVIE).m90a(KEY_ALBUM_ARTIST, "albumArtist", MEDIA_TYPE_MOVIE).m90a(KEY_ALBUM_TITLE, "albumName", MEDIA_TYPE_MOVIE).m90a(KEY_COMPOSER, "composer", MEDIA_TYPE_MOVIE).m90a(KEY_DISC_NUMBER, "discNumber", MEDIA_TYPE_TV_SHOW).m90a(KEY_TRACK_NUMBER, "trackNumber", MEDIA_TYPE_TV_SHOW).m90a(KEY_SEASON_NUMBER, "season", MEDIA_TYPE_TV_SHOW).m90a(KEY_EPISODE_NUMBER, "episode", MEDIA_TYPE_TV_SHOW).m90a(KEY_SERIES_TITLE, "seriesTitle", MEDIA_TYPE_MOVIE).m90a(KEY_STUDIO, "studio", MEDIA_TYPE_MOVIE).m90a(KEY_WIDTH, "width", MEDIA_TYPE_TV_SHOW).m90a(KEY_HEIGHT, "height", MEDIA_TYPE_TV_SHOW).m90a(KEY_LOCATION_NAME, "location", MEDIA_TYPE_MOVIE).m90a(KEY_LOCATION_LATITUDE, "latitude", MEDIA_TYPE_MUSIC_TRACK).m90a(KEY_LOCATION_LONGITUDE, "longitude", MEDIA_TYPE_MUSIC_TRACK);
    }

    public MediaMetadata() {
        this(MEDIA_TYPE_GENERIC);
    }

    public MediaMetadata(int mediaType) {
        this.xJ = new ArrayList();
        this.yr = new Bundle();
        this.ys = mediaType;
    }

    private void m91a(JSONObject jSONObject, String... strArr) {
        try {
            int length = strArr.length;
            for (int i = MEDIA_TYPE_GENERIC; i < length; i += MEDIA_TYPE_MOVIE) {
                String str = strArr[i];
                if (this.yr.containsKey(str)) {
                    switch (yq.m89T(str)) {
                        case MEDIA_TYPE_MOVIE /*1*/:
                        case MEDIA_TYPE_PHOTO /*4*/:
                            jSONObject.put(yq.m87R(str), this.yr.getString(str));
                            break;
                        case MEDIA_TYPE_TV_SHOW /*2*/:
                            jSONObject.put(yq.m87R(str), this.yr.getInt(str));
                            break;
                        case MEDIA_TYPE_MUSIC_TRACK /*3*/:
                            jSONObject.put(yq.m87R(str), this.yr.getDouble(str));
                            break;
                        default:
                            break;
                    }
                }
            }
            for (String str2 : this.yr.keySet()) {
                if (!str2.startsWith("com.google.")) {
                    Object obj = this.yr.get(str2);
                    if (obj instanceof String) {
                        jSONObject.put(str2, obj);
                    } else if (obj instanceof Integer) {
                        jSONObject.put(str2, obj);
                    } else if (obj instanceof Double) {
                        jSONObject.put(str2, obj);
                    }
                }
            }
        } catch (JSONException e) {
        }
    }

    private boolean m92a(Bundle bundle, Bundle bundle2) {
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            Object obj2 = bundle2.get(str);
            if ((obj instanceof Bundle) && (obj2 instanceof Bundle) && !m92a((Bundle) obj, (Bundle) obj2)) {
                return false;
            }
            if (obj == null) {
                if (obj2 != null || !bundle2.containsKey(str)) {
                    return false;
                }
            } else if (!obj.equals(obj2)) {
                return false;
            }
        }
        return true;
    }

    private void m93b(JSONObject jSONObject, String... strArr) {
        Set hashSet = new HashSet(Arrays.asList(strArr));
        try {
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (!"metadataType".equals(str)) {
                    String S = yq.m88S(str);
                    if (S == null) {
                        Object obj = jSONObject.get(str);
                        if (obj instanceof String) {
                            this.yr.putString(str, (String) obj);
                        } else if (obj instanceof Integer) {
                            this.yr.putInt(str, ((Integer) obj).intValue());
                        } else if (obj instanceof Double) {
                            this.yr.putDouble(str, ((Double) obj).doubleValue());
                        }
                    } else if (hashSet.contains(S)) {
                        try {
                            Object obj2 = jSONObject.get(str);
                            if (obj2 != null) {
                                switch (yq.m89T(S)) {
                                    case MEDIA_TYPE_MOVIE /*1*/:
                                        if (!(obj2 instanceof String)) {
                                            break;
                                        }
                                        this.yr.putString(S, (String) obj2);
                                        break;
                                    case MEDIA_TYPE_TV_SHOW /*2*/:
                                        if (!(obj2 instanceof Integer)) {
                                            break;
                                        }
                                        this.yr.putInt(S, ((Integer) obj2).intValue());
                                        break;
                                    case MEDIA_TYPE_MUSIC_TRACK /*3*/:
                                        if (!(obj2 instanceof Double)) {
                                            break;
                                        }
                                        this.yr.putDouble(S, ((Double) obj2).doubleValue());
                                        break;
                                    case MEDIA_TYPE_PHOTO /*4*/:
                                        if (!(obj2 instanceof String)) {
                                            break;
                                        }
                                        if (ew.ac((String) obj2) == null) {
                                            break;
                                        }
                                        this.yr.putString(S, (String) obj2);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } catch (JSONException e) {
                        }
                    }
                }
            }
        } catch (JSONException e2) {
        }
    }

    private void m94d(String str, int i) throws IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("null and empty keys are not allowed");
        }
        int T = yq.m89T(str);
        if (T != i && T != 0) {
            throw new IllegalArgumentException("Value for " + str + " must be a " + yp[i]);
        }
    }

    public void addImage(WebImage image) {
        this.xJ.add(image);
    }

    public void m95c(JSONObject jSONObject) {
        clear();
        this.ys = MEDIA_TYPE_GENERIC;
        try {
            this.ys = jSONObject.getInt("metadataType");
        } catch (JSONException e) {
        }
        ew.m910a(this.xJ, jSONObject);
        String[] strArr;
        switch (this.ys) {
            case MEDIA_TYPE_GENERIC /*0*/:
                strArr = new String[MEDIA_TYPE_PHOTO];
                strArr[MEDIA_TYPE_GENERIC] = KEY_TITLE;
                strArr[MEDIA_TYPE_MOVIE] = KEY_ARTIST;
                strArr[MEDIA_TYPE_TV_SHOW] = KEY_SUBTITLE;
                strArr[MEDIA_TYPE_MUSIC_TRACK] = KEY_RELEASE_DATE;
                m93b(jSONObject, strArr);
            case MEDIA_TYPE_MOVIE /*1*/:
                strArr = new String[MEDIA_TYPE_PHOTO];
                strArr[MEDIA_TYPE_GENERIC] = KEY_TITLE;
                strArr[MEDIA_TYPE_MOVIE] = KEY_STUDIO;
                strArr[MEDIA_TYPE_TV_SHOW] = KEY_SUBTITLE;
                strArr[MEDIA_TYPE_MUSIC_TRACK] = KEY_RELEASE_DATE;
                m93b(jSONObject, strArr);
            case MEDIA_TYPE_TV_SHOW /*2*/:
                m93b(jSONObject, KEY_TITLE, KEY_SERIES_TITLE, KEY_SEASON_NUMBER, KEY_EPISODE_NUMBER, KEY_BROADCAST_DATE);
            case MEDIA_TYPE_MUSIC_TRACK /*3*/:
                m93b(jSONObject, KEY_TITLE, KEY_ALBUM_TITLE, KEY_ARTIST, KEY_ALBUM_ARTIST, KEY_COMPOSER, KEY_TRACK_NUMBER, KEY_DISC_NUMBER, KEY_RELEASE_DATE);
            case MEDIA_TYPE_PHOTO /*4*/:
                m93b(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_LOCATION_NAME, KEY_LOCATION_LATITUDE, KEY_LOCATION_LONGITUDE, KEY_WIDTH, KEY_HEIGHT, KEY_CREATION_DATE);
            default:
                m93b(jSONObject, new String[MEDIA_TYPE_GENERIC]);
        }
    }

    public void clear() {
        this.yr.clear();
        this.xJ.clear();
    }

    public void clearImages() {
        this.xJ.clear();
    }

    public boolean containsKey(String key) {
        return this.yr.containsKey(key);
    }

    public JSONObject dB() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("metadataType", this.ys);
        } catch (JSONException e) {
        }
        ew.m911a(jSONObject, this.xJ);
        String[] strArr;
        switch (this.ys) {
            case MEDIA_TYPE_GENERIC /*0*/:
                strArr = new String[MEDIA_TYPE_PHOTO];
                strArr[MEDIA_TYPE_GENERIC] = KEY_TITLE;
                strArr[MEDIA_TYPE_MOVIE] = KEY_ARTIST;
                strArr[MEDIA_TYPE_TV_SHOW] = KEY_SUBTITLE;
                strArr[MEDIA_TYPE_MUSIC_TRACK] = KEY_RELEASE_DATE;
                m91a(jSONObject, strArr);
                break;
            case MEDIA_TYPE_MOVIE /*1*/:
                strArr = new String[MEDIA_TYPE_PHOTO];
                strArr[MEDIA_TYPE_GENERIC] = KEY_TITLE;
                strArr[MEDIA_TYPE_MOVIE] = KEY_STUDIO;
                strArr[MEDIA_TYPE_TV_SHOW] = KEY_SUBTITLE;
                strArr[MEDIA_TYPE_MUSIC_TRACK] = KEY_RELEASE_DATE;
                m91a(jSONObject, strArr);
                break;
            case MEDIA_TYPE_TV_SHOW /*2*/:
                m91a(jSONObject, KEY_TITLE, KEY_SERIES_TITLE, KEY_SEASON_NUMBER, KEY_EPISODE_NUMBER, KEY_BROADCAST_DATE);
                break;
            case MEDIA_TYPE_MUSIC_TRACK /*3*/:
                m91a(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_ALBUM_TITLE, KEY_ALBUM_ARTIST, KEY_COMPOSER, KEY_TRACK_NUMBER, KEY_DISC_NUMBER, KEY_RELEASE_DATE);
                break;
            case MEDIA_TYPE_PHOTO /*4*/:
                m91a(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_LOCATION_NAME, KEY_LOCATION_LATITUDE, KEY_LOCATION_LONGITUDE, KEY_WIDTH, KEY_HEIGHT, KEY_CREATION_DATE);
                break;
            default:
                m91a(jSONObject, new String[MEDIA_TYPE_GENERIC]);
                break;
        }
        return jSONObject;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MediaMetadata)) {
            return false;
        }
        MediaMetadata mediaMetadata = (MediaMetadata) other;
        return m92a(this.yr, mediaMetadata.yr) && this.xJ.equals(mediaMetadata.xJ);
    }

    public Calendar getDate(String key) {
        m94d(key, MEDIA_TYPE_PHOTO);
        String string = this.yr.getString(key);
        return string != null ? ew.ac(string) : null;
    }

    public String getDateAsString(String key) {
        m94d(key, MEDIA_TYPE_PHOTO);
        return this.yr.getString(key);
    }

    public double getDouble(String key) {
        m94d(key, MEDIA_TYPE_MUSIC_TRACK);
        return this.yr.getDouble(key);
    }

    public List<WebImage> getImages() {
        return this.xJ;
    }

    public int getInt(String key) {
        m94d(key, MEDIA_TYPE_TV_SHOW);
        return this.yr.getInt(key);
    }

    public int getMediaType() {
        return this.ys;
    }

    public String getString(String key) {
        m94d(key, MEDIA_TYPE_MOVIE);
        return this.yr.getString(key);
    }

    public boolean hasImages() {
        return (this.xJ == null || this.xJ.isEmpty()) ? false : true;
    }

    public int hashCode() {
        int i = 17;
        for (String str : this.yr.keySet()) {
            i *= 31;
            i = this.yr.get(str).hashCode() + i;
        }
        return (i * 31) + this.xJ.hashCode();
    }

    public Set<String> keySet() {
        return this.yr.keySet();
    }

    public void putDate(String key, Calendar value) {
        m94d(key, MEDIA_TYPE_PHOTO);
        this.yr.putString(key, ew.m909a(value));
    }

    public void putDouble(String key, double value) {
        m94d(key, MEDIA_TYPE_MUSIC_TRACK);
        this.yr.putDouble(key, value);
    }

    public void putInt(String key, int value) {
        m94d(key, MEDIA_TYPE_TV_SHOW);
        this.yr.putInt(key, value);
    }

    public void putString(String key, String value) {
        m94d(key, MEDIA_TYPE_MOVIE);
        this.yr.putString(key, value);
    }
}
