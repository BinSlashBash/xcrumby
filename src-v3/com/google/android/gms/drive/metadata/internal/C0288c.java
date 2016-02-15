package com.google.android.gms.drive.metadata.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.gs;
import com.google.android.gms.internal.gt;
import com.google.android.gms.internal.gv;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.google.android.gms.drive.metadata.internal.c */
public final class C0288c {
    private static Map<String, MetadataField<?>> FP;

    static {
        FP = new HashMap();
        C0288c.m330b(gs.FR);
        C0288c.m330b(gs.Go);
        C0288c.m330b(gs.Gh);
        C0288c.m330b(gs.Gm);
        C0288c.m330b(gs.Gp);
        C0288c.m330b(gs.Gb);
        C0288c.m330b(gs.Gc);
        C0288c.m330b(gs.FZ);
        C0288c.m330b(gs.Ge);
        C0288c.m330b(gs.Gk);
        C0288c.m330b(gs.FS);
        C0288c.m330b(gs.Gj);
        C0288c.m330b(gs.FT);
        C0288c.m330b(gs.Ga);
        C0288c.m330b(gs.FU);
        C0288c.m330b(gs.FV);
        C0288c.m330b(gs.FW);
        C0288c.m330b(gs.Gg);
        C0288c.m330b(gs.Gd);
        C0288c.m330b(gs.Gi);
        C0288c.m330b(gs.Gl);
        C0288c.m330b(gs.Gq);
        C0288c.m330b(gs.Gr);
        C0288c.m330b(gs.FY);
        C0288c.m330b(gs.FX);
        C0288c.m330b(gs.Gn);
        C0288c.m330b(gs.Gf);
        C0288c.m330b(gt.Gs);
        C0288c.m330b(gt.Gu);
        C0288c.m330b(gt.Gv);
        C0288c.m330b(gt.Gw);
        C0288c.m330b(gt.Gt);
        C0288c.m330b(gv.Gy);
        C0288c.m330b(gv.Gz);
    }

    public static MetadataField<?> ax(String str) {
        return (MetadataField) FP.get(str);
    }

    private static void m330b(MetadataField<?> metadataField) {
        if (FP.containsKey(metadataField.getName())) {
            throw new IllegalArgumentException("Duplicate field name registered: " + metadataField.getName());
        }
        FP.put(metadataField.getName(), metadataField);
    }

    public static Collection<MetadataField<?>> fS() {
        return Collections.unmodifiableCollection(FP.values());
    }
}
