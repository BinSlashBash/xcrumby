package com.google.android.gms.cast;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.eo;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CastDevice implements SafeParcelable {
    public static final Creator<CastDevice> CREATOR;
    private final int xH;
    private String ya;
    String yb;
    private Inet4Address yc;
    private String yd;
    private String ye;
    private String yf;
    private int yg;
    private List<WebImage> yh;

    static {
        CREATOR = new C0237b();
    }

    private CastDevice() {
        this(1, null, null, null, null, null, -1, new ArrayList());
    }

    CastDevice(int versionCode, String deviceId, String hostAddress, String friendlyName, String modelName, String deviceVersion, int servicePort, List<WebImage> icons) {
        this.xH = versionCode;
        this.ya = deviceId;
        this.yb = hostAddress;
        if (this.yb != null) {
            try {
                InetAddress byName = InetAddress.getByName(this.yb);
                if (byName instanceof Inet4Address) {
                    this.yc = (Inet4Address) byName;
                }
            } catch (UnknownHostException e) {
                this.yc = null;
            }
        }
        this.yd = friendlyName;
        this.ye = modelName;
        this.yf = deviceVersion;
        this.yg = servicePort;
        this.yh = icons;
    }

    public static CastDevice getFromBundle(Bundle extras) {
        if (extras == null) {
            return null;
        }
        extras.setClassLoader(CastDevice.class.getClassLoader());
        return (CastDevice) extras.getParcelable("com.google.android.gms.cast.EXTRA_CAST_DEVICE");
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CastDevice)) {
            return false;
        }
        CastDevice castDevice = (CastDevice) obj;
        return getDeviceId() == null ? castDevice.getDeviceId() == null : eo.m874a(this.ya, castDevice.ya) && eo.m874a(this.yc, castDevice.yc) && eo.m874a(this.ye, castDevice.ye) && eo.m874a(this.yd, castDevice.yd) && eo.m874a(this.yf, castDevice.yf) && this.yg == castDevice.yg && eo.m874a(this.yh, castDevice.yh);
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

    public WebImage getIcon(int preferredWidth, int preferredHeight) {
        WebImage webImage = null;
        if (this.yh.isEmpty()) {
            return null;
        }
        if (preferredWidth <= 0 || preferredHeight <= 0) {
            return (WebImage) this.yh.get(0);
        }
        WebImage webImage2 = null;
        for (WebImage webImage3 : this.yh) {
            WebImage webImage32;
            int width = webImage32.getWidth();
            int height = webImage32.getHeight();
            if (width < preferredWidth || height < preferredHeight) {
                if (width < preferredWidth && height < preferredHeight && (webImage == null || (webImage.getWidth() < width && webImage.getHeight() < height))) {
                    webImage = webImage2;
                }
                webImage32 = webImage;
                webImage = webImage2;
            } else {
                if (webImage2 == null || (webImage2.getWidth() > width && webImage2.getHeight() > height)) {
                    WebImage webImage4 = webImage;
                    webImage = webImage32;
                    webImage32 = webImage4;
                }
                webImage32 = webImage;
                webImage = webImage2;
            }
            webImage2 = webImage;
            webImage = webImage32;
        }
        if (webImage2 == null) {
            webImage2 = webImage != null ? webImage : (WebImage) this.yh.get(0);
        }
        return webImage2;
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
        return !this.yh.isEmpty();
    }

    public int hashCode() {
        return this.ya == null ? 0 : this.ya.hashCode();
    }

    public boolean isSameDevice(CastDevice castDevice) {
        if (castDevice == null) {
            return false;
        }
        if (getDeviceId() == null) {
            return castDevice.getDeviceId() == null;
        } else {
            return eo.m874a(getDeviceId(), castDevice.getDeviceId());
        }
    }

    public void putInBundle(Bundle bundle) {
        if (bundle != null) {
            bundle.putParcelable("com.google.android.gms.cast.EXTRA_CAST_DEVICE", this);
        }
    }

    public String toString() {
        return String.format("\"%s\" (%s)", new Object[]{this.yd, this.ya});
    }

    public void writeToParcel(Parcel out, int flags) {
        C0237b.m100a(this, out, flags);
    }
}
