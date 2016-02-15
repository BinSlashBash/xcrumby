/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.c;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.dh;
import java.util.Map;

class ai {
    private static void a(DataLayer dataLayer, c.d arra) {
        arra = arra.eS;
        int n2 = arra.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            dataLayer.bv(dh.j(arra[i2]));
        }
    }

    public static void a(DataLayer dataLayer, c.i i2) {
        if (i2.fI == null) {
            bh.z("supplemental missing experimentSupplemental");
            return;
        }
        ai.a(dataLayer, i2.fI);
        ai.b(dataLayer, i2.fI);
        ai.c(dataLayer, i2.fI);
    }

    private static void b(DataLayer dataLayer, c.d arra) {
        arra = arra.eR;
        int n2 = arra.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            Map<String, Object> map = ai.c(arra[i2]);
            if (map == null) continue;
            dataLayer.push(map);
        }
    }

    private static Map<String, Object> c(d.a object) {
        if (!((object = dh.o((d.a)object)) instanceof Map)) {
            bh.z("value: " + object + " is not a map value, ignored.");
            return null;
        }
        return (Map)object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void c(DataLayer var0, c.d var1_1) {
        var9_2 = var1_1.eT;
        var3_3 = var9_2.length;
        var2_4 = 0;
        while (var2_4 < var3_3) {
            var10_8 = var9_2[var2_4];
            if (var10_8.eM != null) ** GOTO lbl9
            bh.z("GaExperimentRandom: No key");
            ** GOTO lbl30
lbl9: // 1 sources:
            var8_7 = var0.get(var10_8.eM);
            var1_1 = var8_7 instanceof Number == false ? null : Long.valueOf(((Number)var8_7).longValue());
            var4_5 = var10_8.eN;
            var6_6 = var10_8.eO;
            if (var10_8.eP && var1_1 != null && var1_1.longValue() >= var4_5 && var1_1.longValue() <= var6_6) ** GOTO lbl16
            if (var4_5 > var6_6) ** GOTO lbl22
            var8_7 = Math.round(Math.random() * (double)(var6_6 - var4_5) + (double)var4_5);
lbl16: // 2 sources:
            var0.bv(var10_8.eM);
            var1_1 = var0.c(var10_8.eM, var8_7);
            if (var10_8.eQ <= 0) ** GOTO lbl29
            if (var1_1.containsKey("gtm")) ** GOTO lbl24
            var1_1.put("gtm", DataLayer.mapOf(new Object[]{"lifetime", var10_8.eQ}));
            ** GOTO lbl29
lbl22: // 1 sources:
            bh.z("GaExperimentRandom: random range invalid");
            ** GOTO lbl30
lbl24: // 1 sources:
            var8_7 = var1_1.get("gtm");
            if (var8_7 instanceof Map) {
                ((Map)var8_7).put("lifetime", var10_8.eQ);
            } else {
                bh.z("GaExperimentRandom: gtm not a map");
            }
lbl29: // 4 sources:
            var0.push(var1_1);
lbl30: // 3 sources:
            ++var2_4;
        }
    }
}

