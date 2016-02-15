package com.google.android.gms.games.internal.notification;

import com.google.android.gms.common.data.C0251b;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.fo;
import com.google.android.gms.plus.PlusShare;

public final class GameNotificationRef extends C0251b implements GameNotification {
    GameNotificationRef(DataHolder holder, int dataRow) {
        super(holder, dataRow);
    }

    public long getId() {
        return getLong("_id");
    }

    public String getText() {
        return getString("text");
    }

    public String getTitle() {
        return getString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE);
    }

    public int getType() {
        return getInteger("type");
    }

    public String hp() {
        return getString("notification_id");
    }

    public String hq() {
        return getString("ticker");
    }

    public String hr() {
        return getString("coalesced_text");
    }

    public boolean hs() {
        return getInteger("acknowledged") > 0;
    }

    public boolean ht() {
        return getInteger("alert_level") == 0;
    }

    public String toString() {
        return fo.m976e(this).m975a("Id", Long.valueOf(getId())).m975a("NotificationId", hp()).m975a("Type", Integer.valueOf(getType())).m975a("Title", getTitle()).m975a("Ticker", hq()).m975a("Text", getText()).m975a("CoalescedText", hr()).m975a("isAcknowledged", Boolean.valueOf(hs())).m975a("isSilent", Boolean.valueOf(ht())).toString();
    }
}
