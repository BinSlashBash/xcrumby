package com.google.android.gms.drive.events;

import com.google.android.gms.drive.DriveId;

/* renamed from: com.google.android.gms.drive.events.c */
public class C0270c {
    public static boolean m247a(int i, DriveId driveId) {
        return (driveId == null && (4 & ((long) (1 << i))) == 0) ? false : true;
    }
}
