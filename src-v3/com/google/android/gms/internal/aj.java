package com.google.android.gms.internal;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.search.SearchAdRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class aj {
    public static final aj lR;

    static {
        lR = new aj();
    }

    private aj() {
    }

    public static aj az() {
        return lR;
    }

    public ah m613a(Context context, as asVar) {
        Date birthday = asVar.getBirthday();
        long time = birthday != null ? birthday.getTime() : -1;
        String contentUrl = asVar.getContentUrl();
        int gender = asVar.getGender();
        Collection keywords = asVar.getKeywords();
        List unmodifiableList = !keywords.isEmpty() ? Collections.unmodifiableList(new ArrayList(keywords)) : null;
        boolean isTestDevice = asVar.isTestDevice(context);
        int aE = asVar.aE();
        Location location = asVar.getLocation();
        Bundle networkExtrasBundle = asVar.getNetworkExtrasBundle(AdMobAdapter.class);
        boolean manualImpressionsEnabled = asVar.getManualImpressionsEnabled();
        String publisherProvidedId = asVar.getPublisherProvidedId();
        SearchAdRequest aB = asVar.aB();
        return new ah(3, time, networkExtrasBundle, gender, unmodifiableList, isTestDevice, aE, manualImpressionsEnabled, publisherProvidedId, aB != null ? new av(aB) : null, location, contentUrl);
    }
}
