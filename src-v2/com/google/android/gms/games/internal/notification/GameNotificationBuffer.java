/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.notification;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.notification.GameNotification;
import com.google.android.gms.games.internal.notification.GameNotificationRef;

public final class GameNotificationBuffer
extends DataBuffer<GameNotification> {
    public GameNotification bj(int n2) {
        return new GameNotificationRef(this.BB, n2);
    }

    @Override
    public /* synthetic */ Object get(int n2) {
        return this.bj(n2);
    }
}

