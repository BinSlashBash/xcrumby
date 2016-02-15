/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.notification;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.internal.notification.GameNotification;
import com.google.android.gms.internal.fo;

public final class GameNotificationRef
extends b
implements GameNotification {
    GameNotificationRef(DataHolder dataHolder, int n2) {
        super(dataHolder, n2);
    }

    public long getId() {
        return this.getLong("_id");
    }

    public String getText() {
        return this.getString("text");
    }

    public String getTitle() {
        return this.getString("title");
    }

    public int getType() {
        return this.getInteger("type");
    }

    public String hp() {
        return this.getString("notification_id");
    }

    public String hq() {
        return this.getString("ticker");
    }

    public String hr() {
        return this.getString("coalesced_text");
    }

    public boolean hs() {
        if (this.getInteger("acknowledged") > 0) {
            return true;
        }
        return false;
    }

    public boolean ht() {
        if (this.getInteger("alert_level") == 0) {
            return true;
        }
        return false;
    }

    public String toString() {
        return fo.e(this).a("Id", this.getId()).a("NotificationId", this.hp()).a("Type", this.getType()).a("Title", this.getTitle()).a("Ticker", this.hq()).a("Text", this.getText()).a("CoalescedText", this.hr()).a("isAcknowledged", this.hs()).a("isSilent", this.ht()).toString();
    }
}

