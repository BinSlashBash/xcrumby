package com.google.android.gms.drive;

import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.gs;
import com.google.android.gms.internal.gt;
import com.google.android.gms.internal.gv;
import java.util.Date;

public abstract class Metadata implements Freezable<Metadata> {
    public static final int CONTENT_AVAILABLE_LOCALLY = 1;
    public static final int CONTENT_NOT_AVAILABLE_LOCALLY = 0;

    protected abstract <T> T m1703a(MetadataField<T> metadataField);

    public String getAlternateLink() {
        return (String) m1703a(gs.FS);
    }

    public int getContentAvailability() {
        Integer num = (Integer) m1703a(gv.Gy);
        return num == null ? 0 : num.intValue();
    }

    public Date getCreatedDate() {
        return (Date) m1703a(gt.Gs);
    }

    public String getDescription() {
        return (String) m1703a(gs.FT);
    }

    public DriveId getDriveId() {
        return (DriveId) m1703a(gs.FR);
    }

    public String getEmbedLink() {
        return (String) m1703a(gs.FU);
    }

    public String getFileExtension() {
        return (String) m1703a(gs.FV);
    }

    public long getFileSize() {
        return ((Long) m1703a(gs.FW)).longValue();
    }

    public Date getLastViewedByMeDate() {
        return (Date) m1703a(gt.Gt);
    }

    public String getMimeType() {
        return (String) m1703a(gs.Gh);
    }

    public Date getModifiedByMeDate() {
        return (Date) m1703a(gt.Gv);
    }

    public Date getModifiedDate() {
        return (Date) m1703a(gt.Gu);
    }

    public String getOriginalFilename() {
        return (String) m1703a(gs.Gi);
    }

    public long getQuotaBytesUsed() {
        return ((Long) m1703a(gs.Gl)).longValue();
    }

    public Date getSharedWithMeDate() {
        return (Date) m1703a(gt.Gw);
    }

    public String getTitle() {
        return (String) m1703a(gs.Go);
    }

    public String getWebContentLink() {
        return (String) m1703a(gs.Gq);
    }

    public String getWebViewLink() {
        return (String) m1703a(gs.Gr);
    }

    public boolean isEditable() {
        Boolean bool = (Boolean) m1703a(gs.Gb);
        return bool == null ? false : bool.booleanValue();
    }

    public boolean isFolder() {
        return DriveFolder.MIME_TYPE.equals(getMimeType());
    }

    public boolean isInAppFolder() {
        Boolean bool = (Boolean) m1703a(gs.FZ);
        return bool == null ? false : bool.booleanValue();
    }

    public boolean isPinnable() {
        Boolean bool = (Boolean) m1703a(gv.Gz);
        return bool == null ? false : bool.booleanValue();
    }

    public boolean isPinned() {
        Boolean bool = (Boolean) m1703a(gs.Gc);
        return bool == null ? false : bool.booleanValue();
    }

    public boolean isRestricted() {
        Boolean bool = (Boolean) m1703a(gs.Gd);
        return bool == null ? false : bool.booleanValue();
    }

    public boolean isShared() {
        Boolean bool = (Boolean) m1703a(gs.Ge);
        return bool == null ? false : bool.booleanValue();
    }

    public boolean isStarred() {
        Boolean bool = (Boolean) m1703a(gs.Gm);
        return bool == null ? false : bool.booleanValue();
    }

    public boolean isTrashed() {
        Boolean bool = (Boolean) m1703a(gs.Gp);
        return bool == null ? false : bool.booleanValue();
    }

    public boolean isViewed() {
        Boolean bool = (Boolean) m1703a(gs.Gg);
        return bool == null ? false : bool.booleanValue();
    }
}
