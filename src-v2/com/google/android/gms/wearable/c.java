/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.wearable;

import android.net.Uri;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.wearable.d;
import java.util.Map;
import java.util.Set;

public interface c
extends Freezable<c> {
    public byte[] getData();

    public Uri getUri();

    public Map<String, d> ma();

    @Deprecated
    public Set<String> mb();
}

