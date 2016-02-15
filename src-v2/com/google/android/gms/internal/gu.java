/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.h;
import java.util.Arrays;
import java.util.Collection;

public class gu
extends h<DriveId> {
    public static final gu Gx = new gu();

    private gu() {
        super("driveId", Arrays.asList("sqlId", "resourceId"), 4100000);
    }

    @Override
    protected /* synthetic */ Object b(DataHolder dataHolder, int n2, int n3) {
        return this.j(dataHolder, n2, n3);
    }

    protected DriveId j(DataHolder dataHolder, int n2, int n3) {
        String string2;
        long l2 = dataHolder.getMetadata().getLong("dbInstanceId");
        String string3 = string2 = dataHolder.getString("resourceId", n2, n3);
        if (string2 != null) {
            string3 = string2;
            if (string2.startsWith("generated-android-")) {
                string3 = null;
            }
        }
        return new DriveId(string3, dataHolder.getLong("sqlId", n2, n3), l2);
    }
}

