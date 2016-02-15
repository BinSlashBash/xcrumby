/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package com.google.android.gms.internal;

import android.graphics.Bitmap;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.SortableMetadataField;
import com.google.android.gms.drive.metadata.internal.h;
import com.google.android.gms.drive.metadata.internal.i;
import com.google.android.gms.drive.metadata.internal.j;
import com.google.android.gms.internal.gu;

public class gs {
    public static final MetadataField<DriveId> FR = gu.Gx;
    public static final MetadataField<String> FS = new j("alternateLink", 4300000);
    public static final MetadataField<String> FT = new j("description", 4300000);
    public static final MetadataField<String> FU = new j("embedLink", 4300000);
    public static final MetadataField<String> FV = new j("fileExtension", 4300000);
    public static final MetadataField<Long> FW = new com.google.android.gms.drive.metadata.internal.e("fileSize", 4300000);
    public static final MetadataField<Boolean> FX = new com.google.android.gms.drive.metadata.internal.a("hasThumbnail", 4300000);
    public static final MetadataField<String> FY = new j("indexableText", 4300000);
    public static final MetadataField<Boolean> FZ = new com.google.android.gms.drive.metadata.internal.a("isAppData", 4300000);
    public static final MetadataField<Boolean> Ga = new com.google.android.gms.drive.metadata.internal.a("isCopyable", 4300000);
    public static final MetadataField<Boolean> Gb = new com.google.android.gms.drive.metadata.internal.a("isEditable", 4100000);
    public static final a Gc = new a("isPinned", 4100000);
    public static final MetadataField<Boolean> Gd = new com.google.android.gms.drive.metadata.internal.a("isRestricted", 4300000);
    public static final MetadataField<Boolean> Ge = new com.google.android.gms.drive.metadata.internal.a("isShared", 4300000);
    public static final MetadataField<Boolean> Gf = new com.google.android.gms.drive.metadata.internal.a("isTrashable", 4400000);
    public static final MetadataField<Boolean> Gg = new com.google.android.gms.drive.metadata.internal.a("isViewed", 4300000);
    public static final b Gh = new b("mimeType", 4100000);
    public static final MetadataField<String> Gi = new j("originalFilename", 4300000);
    public static final com.google.android.gms.drive.metadata.b<String> Gj = new i("ownerNames", 4300000);
    public static final c Gk = new c("parents", 4100000);
    public static final d Gl = new d("quotaBytesUsed", 4300000);
    public static final e Gm = new e("starred", 4100000);
    public static final MetadataField<Bitmap> Gn = new h<Bitmap>("thumbnail", 4400000){

        @Override
        protected /* synthetic */ Object b(DataHolder dataHolder, int n2, int n3) {
            return this.i(dataHolder, n2, n3);
        }

        protected Bitmap i(DataHolder dataHolder, int n2, int n3) {
            throw new IllegalStateException("Thumbnail field is write only");
        }
    };
    public static final f Go = new f("title", 4100000);
    public static final g Gp = new g("trashed", 4100000);
    public static final MetadataField<String> Gq = new j("webContentLink", 4300000);
    public static final MetadataField<String> Gr = new j("webViewLink", 4300000);

    public static class a
    extends com.google.android.gms.drive.metadata.internal.a
    implements SearchableMetadataField<Boolean> {
        public a(String string2, int n2) {
            super(string2, n2);
        }
    }

    public static class b
    extends j
    implements SearchableMetadataField<String> {
        public b(String string2, int n2) {
            super(string2, n2);
        }
    }

    public static class c
    extends com.google.android.gms.drive.metadata.internal.g<DriveId>
    implements SearchableCollectionMetadataField<DriveId> {
        public c(String string2, int n2) {
            super(string2, n2);
        }
    }

    public static class d
    extends com.google.android.gms.drive.metadata.internal.e
    implements SortableMetadataField<Long> {
        public d(String string2, int n2) {
            super(string2, n2);
        }
    }

    public static class e
    extends com.google.android.gms.drive.metadata.internal.a
    implements SearchableMetadataField<Boolean> {
        public e(String string2, int n2) {
            super(string2, n2);
        }
    }

    public static class f
    extends j
    implements SearchableMetadataField<String>,
    SortableMetadataField<String> {
        public f(String string2, int n2) {
            super(string2, n2);
        }
    }

    public static class g
    extends com.google.android.gms.drive.metadata.internal.a
    implements SearchableMetadataField<Boolean> {
        public g(String string2, int n2) {
            super(string2, n2);
        }

        @Override
        protected /* synthetic */ Object b(DataHolder dataHolder, int n2, int n3) {
            return this.d(dataHolder, n2, n3);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        protected Boolean d(DataHolder dataHolder, int n2, int n3) {
            boolean bl2;
            if (dataHolder.getInteger(this.getName(), n2, n3) != 0) {
                bl2 = true;
                do {
                    return bl2;
                    break;
                } while (true);
            }
            bl2 = false;
            return bl2;
        }
    }

}

