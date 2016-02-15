/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.drive.events;

import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.DriveEvent;

public interface ResourceEvent
extends DriveEvent {
    @Override
    public DriveId getDriveId();
}

