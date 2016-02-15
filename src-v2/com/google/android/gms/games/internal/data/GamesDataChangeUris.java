/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.net.Uri$Builder
 */
package com.google.android.gms.games.internal.data;

import android.net.Uri;

public final class GamesDataChangeUris {
    private static final Uri Lq = Uri.parse((String)"content://com.google.android.gms.games/").buildUpon().appendPath("data_change").build();
    public static final Uri Lr = Lq.buildUpon().appendPath("invitations").build();
    public static final Uri Ls = Lq.buildUpon().appendEncodedPath("players").build();
}

