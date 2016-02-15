package com.google.android.gms.internal;

import android.content.Context;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import uk.co.senab.photoview.IPhotoView;

public final class du extends C0359do {
    private final String lh;
    private final Context mContext;
    private final String ro;

    public du(Context context, String str, String str2) {
        this.mContext = context;
        this.lh = str;
        this.ro = str2;
    }

    public void aY() {
        HttpURLConnection httpURLConnection;
        try {
            dw.m822y("Pinging URL: " + this.ro);
            httpURLConnection = (HttpURLConnection) new URL(this.ro).openConnection();
            dq.m780a(this.mContext, this.lh, true, httpURLConnection);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode < IPhotoView.DEFAULT_ZOOM_DURATION || responseCode >= 300) {
                dw.m823z("Received non-success response code " + responseCode + " from pinging URL: " + this.ro);
            }
            httpURLConnection.disconnect();
        } catch (IndexOutOfBoundsException e) {
            dw.m823z("Error while parsing ping URL: " + this.ro + ". " + e.getMessage());
        } catch (IOException e2) {
            dw.m823z("Error while pinging URL: " + this.ro + ". " + e2.getMessage());
        } catch (Throwable th) {
            httpURLConnection.disconnect();
        }
    }

    public void onStop() {
    }
}
