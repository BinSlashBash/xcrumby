/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.drive.events;

import com.google.android.gms.drive.DriveId;

public class c {
    public static boolean a(int n2, DriveId driveId) {
        if (driveId != null || (4 & (long)(1 << n2)) != 0) {
            return true;
        }
        return false;
    }
}

