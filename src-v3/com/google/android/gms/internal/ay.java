package com.google.android.gms.internal;

import java.util.Map;

public final class ay implements bb {
    private final az mF;

    public ay(az azVar) {
        this.mF = azVar;
    }

    public void m2026b(dz dzVar, Map<String, String> map) {
        String str = (String) map.get("name");
        if (str == null) {
            dw.m823z("App event with no name parameter.");
        } else {
            this.mF.onAppEvent(str, (String) map.get("info"));
        }
    }
}
