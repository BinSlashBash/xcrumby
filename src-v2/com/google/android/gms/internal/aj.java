/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.location.Location
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.search.SearchAdRequest;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.as;
import com.google.android.gms.internal.av;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class aj {
    public static final aj lR = new aj();

    private aj() {
    }

    public static aj az() {
        return lR;
    }

    /*
     * Enabled aggressive block sorting
     */
    public ah a(Context object, as as2) {
        Set<String> set = as2.getBirthday();
        long l2 = set != null ? set.getTime() : -1;
        String string2 = as2.getContentUrl();
        int n2 = as2.getGender();
        set = as2.getKeywords();
        set = !set.isEmpty() ? Collections.unmodifiableList(new ArrayList(set)) : null;
        boolean bl2 = as2.isTestDevice((Context)object);
        int n3 = as2.aE();
        Location location = as2.getLocation();
        Bundle bundle = as2.getNetworkExtrasBundle(AdMobAdapter.class);
        boolean bl3 = as2.getManualImpressionsEnabled();
        String string3 = as2.getPublisherProvidedId();
        object = as2.aB();
        if (object != null) {
            object = new av((SearchAdRequest)object);
            return new ah(3, l2, bundle, n2, (List<String>)((Object)set), bl2, n3, bl3, string3, (av)object, location, string2);
        }
        object = null;
        return new ah(3, l2, bundle, n2, (List<String>)((Object)set), bl2, n3, bl3, string3, (av)object, location, string2);
    }
}

