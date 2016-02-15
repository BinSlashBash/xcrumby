/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.drive;

import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.gs;
import com.google.android.gms.internal.gt;
import com.google.android.gms.internal.gv;
import java.util.Date;

public abstract class Metadata
implements Freezable<Metadata> {
    public static final int CONTENT_AVAILABLE_LOCALLY = 1;
    public static final int CONTENT_NOT_AVAILABLE_LOCALLY = 0;

    protected abstract <T> T a(MetadataField<T> var1);

    public String getAlternateLink() {
        return this.a(gs.FS);
    }

    public int getContentAvailability() {
        Integer n2 = this.a(gv.Gy);
        if (n2 == null) {
            return 0;
        }
        return n2;
    }

    public Date getCreatedDate() {
        return (Date)this.a(gt.Gs);
    }

    public String getDescription() {
        return this.a(gs.FT);
    }

    public DriveId getDriveId() {
        return this.a(gs.FR);
    }

    public String getEmbedLink() {
        return this.a(gs.FU);
    }

    public String getFileExtension() {
        return this.a(gs.FV);
    }

    public long getFileSize() {
        return this.a(gs.FW);
    }

    public Date getLastViewedByMeDate() {
        return (Date)this.a(gt.Gt);
    }

    public String getMimeType() {
        return (String)this.a(gs.Gh);
    }

    public Date getModifiedByMeDate() {
        return (Date)this.a(gt.Gv);
    }

    public Date getModifiedDate() {
        return (Date)this.a(gt.Gu);
    }

    public String getOriginalFilename() {
        return this.a(gs.Gi);
    }

    public long getQuotaBytesUsed() {
        return (Long)this.a(gs.Gl);
    }

    public Date getSharedWithMeDate() {
        return (Date)this.a(gt.Gw);
    }

    public String getTitle() {
        return (String)this.a(gs.Go);
    }

    public String getWebContentLink() {
        return this.a(gs.Gq);
    }

    public String getWebViewLink() {
        return this.a(gs.Gr);
    }

    public boolean isEditable() {
        Boolean bl2 = this.a(gs.Gb);
        if (bl2 == null) {
            return false;
        }
        return bl2;
    }

    public boolean isFolder() {
        return "application/vnd.google-apps.folder".equals(this.getMimeType());
    }

    public boolean isInAppFolder() {
        Boolean bl2 = this.a(gs.FZ);
        if (bl2 == null) {
            return false;
        }
        return bl2;
    }

    public boolean isPinnable() {
        Boolean bl2 = this.a(gv.Gz);
        if (bl2 == null) {
            return false;
        }
        return bl2;
    }

    public boolean isPinned() {
        Boolean bl2 = (Boolean)this.a(gs.Gc);
        if (bl2 == null) {
            return false;
        }
        return bl2;
    }

    public boolean isRestricted() {
        Boolean bl2 = this.a(gs.Gd);
        if (bl2 == null) {
            return false;
        }
        return bl2;
    }

    public boolean isShared() {
        Boolean bl2 = this.a(gs.Ge);
        if (bl2 == null) {
            return false;
        }
        return bl2;
    }

    public boolean isStarred() {
        Boolean bl2 = (Boolean)this.a(gs.Gm);
        if (bl2 == null) {
            return false;
        }
        return bl2;
    }

    public boolean isTrashed() {
        Boolean bl2 = (Boolean)this.a(gs.Gp);
        if (bl2 == null) {
            return false;
        }
        return bl2;
    }

    public boolean isViewed() {
        Boolean bl2 = this.a(gs.Gg);
        if (bl2 == null) {
            return false;
        }
        return bl2;
    }
}

