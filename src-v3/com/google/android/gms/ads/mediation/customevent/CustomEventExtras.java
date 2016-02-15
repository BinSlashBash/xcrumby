package com.google.android.gms.ads.mediation.customevent;

import com.google.ads.mediation.NetworkExtras;
import java.util.HashMap;

public final class CustomEventExtras implements NetworkExtras {
    private final HashMap<String, Object> rQ;

    public CustomEventExtras() {
        this.rQ = new HashMap();
    }

    public Object getExtra(String label) {
        return this.rQ.get(label);
    }

    public void setExtra(String label, Object value) {
        this.rQ.put(label, value);
    }
}
