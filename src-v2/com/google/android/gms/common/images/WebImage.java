/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.b;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fo;
import org.json.JSONException;
import org.json.JSONObject;

public final class WebImage
implements SafeParcelable {
    public static final Parcelable.Creator<WebImage> CREATOR = new b();
    private final Uri Cu;
    private final int kr;
    private final int ks;
    private final int xH;

    WebImage(int n2, Uri uri, int n3, int n4) {
        this.xH = n2;
        this.Cu = uri;
        this.kr = n3;
        this.ks = n4;
    }

    public WebImage(Uri uri) throws IllegalArgumentException {
        this(uri, 0, 0);
    }

    public WebImage(Uri uri, int n2, int n3) throws IllegalArgumentException {
        this(1, uri, n2, n3);
        if (uri == null) {
            throw new IllegalArgumentException("url cannot be null");
        }
        if (n2 < 0 || n3 < 0) {
            throw new IllegalArgumentException("width and height must not be negative");
        }
    }

    public WebImage(JSONObject jSONObject) throws IllegalArgumentException {
        this(WebImage.d(jSONObject), jSONObject.optInt("width", 0), jSONObject.optInt("height", 0));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Uri d(JSONObject jSONObject) {
        Uri uri = null;
        if (!jSONObject.has("url")) return uri;
        try {
            return Uri.parse((String)jSONObject.getString("url"));
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    public JSONObject dB() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", this.Cu.toString());
            jSONObject.put("width", this.kr);
            jSONObject.put("height", this.ks);
            return jSONObject;
        }
        catch (JSONException var2_2) {
            return jSONObject;
        }
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (!(object instanceof WebImage)) {
            return false;
        }
        object = (WebImage)object;
        if (!fo.equal((Object)this.Cu, (Object)object.Cu)) return false;
        if (this.kr != object.kr) return false;
        if (this.ks == object.ks) return true;
        return false;
    }

    public int getHeight() {
        return this.ks;
    }

    public Uri getUrl() {
        return this.Cu;
    }

    int getVersionCode() {
        return this.xH;
    }

    public int getWidth() {
        return this.kr;
    }

    public int hashCode() {
        return fo.hashCode(new Object[]{this.Cu, this.kr, this.ks});
    }

    public String toString() {
        return String.format("Image %dx%d %s", this.kr, this.ks, this.Cu.toString());
    }

    public void writeToParcel(Parcel parcel, int n2) {
        b.a(this, parcel, n2);
    }
}

