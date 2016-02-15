/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.drive.events;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.DriveId;

public interface DriveEvent
extends SafeParcelable {
    public static final int TYPE_CHANGE = 1;

    public DriveId getDriveId();

    public int getType();

    public static interface Listener<E extends DriveEvent> {
        public void onEvent(E var1);
    }

}

