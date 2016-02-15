/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 */
package com.google.android.gms.games.internal.game;

import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.internal.game.GameBadge;
import com.google.android.gms.games.internal.game.GameBadgeEntity;

public final class GameBadgeRef
extends b
implements GameBadge {
    GameBadgeRef(DataHolder dataHolder, int n2) {
        super(dataHolder, n2);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        return GameBadgeEntity.a(this, object);
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.hh();
    }

    @Override
    public String getDescription() {
        return this.getString("badge_description");
    }

    @Override
    public Uri getIconImageUri() {
        return this.ah("badge_icon_image_uri");
    }

    @Override
    public String getTitle() {
        return this.getString("badge_title");
    }

    @Override
    public int getType() {
        return this.getInteger("badge_type");
    }

    @Override
    public int hashCode() {
        return GameBadgeEntity.a(this);
    }

    public GameBadge hh() {
        return new GameBadgeEntity(this);
    }

    public String toString() {
        return GameBadgeEntity.b(this);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ((GameBadgeEntity)this.hh()).writeToParcel(parcel, n2);
    }
}

