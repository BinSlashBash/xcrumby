package com.google.android.gms.wearable;

import android.net.Uri;
import com.google.android.gms.common.data.Freezable;
import java.util.Map;
import java.util.Set;

/* renamed from: com.google.android.gms.wearable.c */
public interface C1102c extends Freezable<C1102c> {
    byte[] getData();

    Uri getUri();

    Map<String, C1103d> ma();

    @Deprecated
    Set<String> mb();
}
