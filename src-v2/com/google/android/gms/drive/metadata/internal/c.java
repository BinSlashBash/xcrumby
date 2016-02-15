/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package com.google.android.gms.drive.metadata.internal;

import android.graphics.Bitmap;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.b;
import com.google.android.gms.internal.gs;
import com.google.android.gms.internal.gt;
import com.google.android.gms.internal.gv;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class c {
    private static Map<String, MetadataField<?>> FP = new HashMap();

    static {
        c.b(gs.FR);
        c.b(gs.Go);
        c.b(gs.Gh);
        c.b(gs.Gm);
        c.b(gs.Gp);
        c.b(gs.Gb);
        c.b(gs.Gc);
        c.b(gs.FZ);
        c.b(gs.Ge);
        c.b(gs.Gk);
        c.b(gs.FS);
        c.b(gs.Gj);
        c.b(gs.FT);
        c.b(gs.Ga);
        c.b(gs.FU);
        c.b(gs.FV);
        c.b(gs.FW);
        c.b(gs.Gg);
        c.b(gs.Gd);
        c.b(gs.Gi);
        c.b(gs.Gl);
        c.b(gs.Gq);
        c.b(gs.Gr);
        c.b(gs.FY);
        c.b(gs.FX);
        c.b(gs.Gn);
        c.b(gs.Gf);
        c.b(gt.Gs);
        c.b(gt.Gu);
        c.b(gt.Gv);
        c.b(gt.Gw);
        c.b(gt.Gt);
        c.b(gv.Gy);
        c.b(gv.Gz);
    }

    public static MetadataField<?> ax(String string2) {
        return FP.get(string2);
    }

    private static void b(MetadataField<?> metadataField) {
        if (FP.containsKey(metadataField.getName())) {
            throw new IllegalArgumentException("Duplicate field name registered: " + metadataField.getName());
        }
        FP.put(metadataField.getName(), metadataField);
    }

    public static Collection<MetadataField<?>> fS() {
        return Collections.unmodifiableCollection(FP.values());
    }
}

