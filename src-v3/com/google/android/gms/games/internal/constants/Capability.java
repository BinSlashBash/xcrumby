package com.google.android.gms.games.internal.constants;

import java.util.ArrayList;

public class Capability {
    public static final ArrayList<String> Lp;

    static {
        Lp = new ArrayList();
        Lp.add("ibb");
        Lp.add("rtp");
        Lp.add("unreliable_ping");
    }
}
