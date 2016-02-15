package com.google.android.gms.drive;

import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.gs;
import com.google.android.gms.internal.gt;
import java.util.Date;

public final class MetadataChangeSet {
    private final MetadataBundle EP;

    public static class Builder {
        private final MetadataBundle EP;

        public Builder() {
            this.EP = MetadataBundle.fT();
        }

        public MetadataChangeSet build() {
            return new MetadataChangeSet(null);
        }

        public Builder setDescription(String description) {
            this.EP.m1745b(gs.FT, description);
            return this;
        }

        public Builder setIndexableText(String text) {
            this.EP.m1745b(gs.FY, text);
            return this;
        }

        public Builder setLastViewedByMeDate(Date date) {
            this.EP.m1745b(gt.Gt, date);
            return this;
        }

        public Builder setMimeType(String mimeType) {
            this.EP.m1745b(gs.Gh, mimeType);
            return this;
        }

        public Builder setPinned(boolean pinned) {
            this.EP.m1745b(gs.Gc, Boolean.valueOf(pinned));
            return this;
        }

        public Builder setStarred(boolean starred) {
            this.EP.m1745b(gs.Gm, Boolean.valueOf(starred));
            return this;
        }

        public Builder setTitle(String title) {
            this.EP.m1745b(gs.Go, title);
            return this;
        }

        public Builder setViewed(boolean viewed) {
            this.EP.m1745b(gs.Gg, Boolean.valueOf(viewed));
            return this;
        }
    }

    private MetadataChangeSet(MetadataBundle bag) {
        this.EP = MetadataBundle.m1743a(bag);
    }

    public MetadataBundle fD() {
        return this.EP;
    }

    public String getDescription() {
        return (String) this.EP.m1744a(gs.FT);
    }

    public String getIndexableText() {
        return (String) this.EP.m1744a(gs.FY);
    }

    public Date getLastViewedByMeDate() {
        return (Date) this.EP.m1744a(gt.Gt);
    }

    public String getMimeType() {
        return (String) this.EP.m1744a(gs.Gh);
    }

    public String getTitle() {
        return (String) this.EP.m1744a(gs.Go);
    }

    public Boolean isPinned() {
        return (Boolean) this.EP.m1744a(gs.Gc);
    }

    public Boolean isStarred() {
        return (Boolean) this.EP.m1744a(gs.Gm);
    }

    public Boolean isViewed() {
        return (Boolean) this.EP.m1744a(gs.Gg);
    }
}
