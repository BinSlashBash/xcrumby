package com.google.android.gms.games.internal;

import com.google.android.gms.internal.fj;

public final class GamesLog {
    private static final fj JH;

    static {
        JH = new fj("Games");
    }

    private GamesLog() {
    }

    public static void m364a(String str, String str2, Throwable th) {
        JH.m941a(str, str2, th);
    }

    public static void m365f(String str, String str2) {
        JH.m942f(str, str2);
    }

    public static void m366g(String str, String str2) {
        JH.m943g(str, str2);
    }

    public static void m367h(String str, String str2) {
        JH.m944h(str, str2);
    }
}
