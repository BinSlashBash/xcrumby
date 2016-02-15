/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.drive;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.gs;
import com.google.android.gms.internal.gt;
import java.util.Date;

public final class MetadataChangeSet {
    private final MetadataBundle EP;

    private MetadataChangeSet(MetadataBundle metadataBundle) {
        this.EP = MetadataBundle.a(metadataBundle);
    }

    public MetadataBundle fD() {
        return this.EP;
    }

    public String getDescription() {
        return this.EP.a(gs.FT);
    }

    public String getIndexableText() {
        return this.EP.a(gs.FY);
    }

    public Date getLastViewedByMeDate() {
        return (Date)this.EP.a(gt.Gt);
    }

    public String getMimeType() {
        return (String)this.EP.a(gs.Gh);
    }

    public String getTitle() {
        return (String)this.EP.a(gs.Go);
    }

    public Boolean isPinned() {
        return (Boolean)this.EP.a(gs.Gc);
    }

    public Boolean isStarred() {
        return (Boolean)this.EP.a(gs.Gm);
    }

    public Boolean isViewed() {
        return this.EP.a(gs.Gg);
    }

    public static class Builder {
        private final MetadataBundle EP = MetadataBundle.fT();

        public MetadataChangeSet build() {
            return new MetadataChangeSet(this.EP);
        }

        public Builder setDescription(String string2) {
            this.EP.b(gs.FT, string2);
            return this;
        }

        public Builder setIndexableText(String string2) {
            this.EP.b(gs.FY, string2);
            return this;
        }

        public Builder setLastViewedByMeDate(Date date) {
            this.EP.b(gt.Gt, date);
            return this;
        }

        public Builder setMimeType(String string2) {
            this.EP.b(gs.Gh, string2);
            return this;
        }

        public Builder setPinned(boolean bl2) {
            this.EP.b(gs.Gc, bl2);
            return this;
        }

        public Builder setStarred(boolean bl2) {
            this.EP.b(gs.Gm, bl2);
            return this;
        }

        public Builder setTitle(String string2) {
            this.EP.b(gs.Go, string2);
            return this;
        }

        public Builder setViewed(boolean bl2) {
            this.EP.b(gs.Gg, bl2);
            return this;
        }
    }

}

