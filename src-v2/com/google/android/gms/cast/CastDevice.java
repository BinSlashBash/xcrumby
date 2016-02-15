/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.cast;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.cast.b;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.eo;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CastDevice
implements SafeParcelable {
    public static final Parcelable.Creator<CastDevice> CREATOR = new b();
    private final int xH;
    private String ya;
    String yb;
    private Inet4Address yc;
    private String yd;
    private String ye;
    private String yf;
    private int yg;
    private List<WebImage> yh;

    private CastDevice() {
        this(1, null, null, null, null, null, -1, new ArrayList<WebImage>());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    CastDevice(int n2, String object, String string2, String string3, String string4, String string5, int n3, List<WebImage> list) {
        this.xH = n2;
        this.ya = object;
        this.yb = string2;
        if (this.yb != null) {
            try {
                object = InetAddress.getByName(this.yb);
                if (object instanceof Inet4Address) {
                    this.yc = (Inet4Address)object;
                }
            }
            catch (UnknownHostException var2_3) {
                this.yc = null;
            }
        }
        this.yd = string3;
        this.ye = string4;
        this.yf = string5;
        this.yg = n3;
        this.yh = list;
    }

    public static CastDevice getFromBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        bundle.setClassLoader(CastDevice.class.getClassLoader());
        return (CastDevice)bundle.getParcelable("com.google.android.gms.cast.EXTRA_CAST_DEVICE");
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof CastDevice)) {
            return false;
        }
        object = (CastDevice)object;
        if (this.getDeviceId() == null) {
            if (object.getDeviceId() == null) return true;
            return false;
        }
        if (!eo.a(this.ya, object.ya) || !eo.a(this.yc, object.yc) || !eo.a(this.ye, object.ye) || !eo.a(this.yd, object.yd) || !eo.a(this.yf, object.yf) || this.yg != object.yg || !eo.a(this.yh, object.yh)) return false;
        return true;
    }

    public String getDeviceId() {
        return this.ya;
    }

    public String getDeviceVersion() {
        return this.yf;
    }

    public String getFriendlyName() {
        return this.yd;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public WebImage getIcon(int n2, int n3) {
        WebImage webImage = null;
        if (this.yh.isEmpty()) {
            return null;
        }
        if (n2 <= 0) return this.yh.get(0);
        if (n3 <= 0) {
            return this.yh.get(0);
        }
        Iterator<WebImage> iterator = this.yh.iterator();
        WebImage webImage2 = null;
        while (iterator.hasNext()) {
            WebImage webImage3 = iterator.next();
            int n4 = webImage3.getWidth();
            int n5 = webImage3.getHeight();
            if (n4 >= n2 && n5 >= n3) {
                if (webImage2 != null && (webImage2.getWidth() <= n4 || webImage2.getHeight() <= n5)) continue;
                webImage2 = webImage3;
                continue;
            }
            if (n4 >= n2 || n5 >= n3 || webImage != null && (webImage.getWidth() >= n4 || webImage.getHeight() >= n5)) continue;
            webImage = webImage3;
        }
        if (webImage == null) return this.yh.get(0);
        return webImage;
    }

    public List<WebImage> getIcons() {
        return Collections.unmodifiableList(this.yh);
    }

    public Inet4Address getIpAddress() {
        return this.yc;
    }

    public String getModelName() {
        return this.ye;
    }

    public int getServicePort() {
        return this.yg;
    }

    int getVersionCode() {
        return this.xH;
    }

    public boolean hasIcons() {
        if (!this.yh.isEmpty()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.ya == null) {
            return 0;
        }
        return this.ya.hashCode();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean isSameDevice(CastDevice castDevice) {
        if (castDevice == null) {
            return false;
        }
        if (this.getDeviceId() != null) return eo.a(this.getDeviceId(), castDevice.getDeviceId());
        if (castDevice.getDeviceId() != null) return false;
        return true;
    }

    public void putInBundle(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        bundle.putParcelable("com.google.android.gms.cast.EXTRA_CAST_DEVICE", (Parcelable)this);
    }

    public String toString() {
        return String.format("\"%s\" (%s)", this.yd, this.ya);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        b.a(this, parcel, n2);
    }
}

