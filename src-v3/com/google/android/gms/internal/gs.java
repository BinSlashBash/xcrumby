package com.google.android.gms.internal;

import android.graphics.Bitmap;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.C1316b;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.SortableMetadataField;
import com.google.android.gms.drive.metadata.internal.C1318a;
import com.google.android.gms.drive.metadata.internal.C1320e;
import com.google.android.gms.drive.metadata.internal.C1321h;
import com.google.android.gms.drive.metadata.internal.C1322j;
import com.google.android.gms.drive.metadata.internal.C1482g;
import com.google.android.gms.drive.metadata.internal.C1483i;
import com.google.android.gms.plus.PlusShare;

public class gs {
    public static final MetadataField<DriveId> FR;
    public static final MetadataField<String> FS;
    public static final MetadataField<String> FT;
    public static final MetadataField<String> FU;
    public static final MetadataField<String> FV;
    public static final MetadataField<Long> FW;
    public static final MetadataField<Boolean> FX;
    public static final MetadataField<String> FY;
    public static final MetadataField<Boolean> FZ;
    public static final MetadataField<Boolean> Ga;
    public static final MetadataField<Boolean> Gb;
    public static final C1490a Gc;
    public static final MetadataField<Boolean> Gd;
    public static final MetadataField<Boolean> Ge;
    public static final MetadataField<Boolean> Gf;
    public static final MetadataField<Boolean> Gg;
    public static final C1491b Gh;
    public static final MetadataField<String> Gi;
    public static final C1316b<String> Gj;
    public static final C1536c Gk;
    public static final C1492d Gl;
    public static final C1493e Gm;
    public static final MetadataField<Bitmap> Gn;
    public static final C1494f Go;
    public static final C1495g Gp;
    public static final MetadataField<String> Gq;
    public static final MetadataField<String> Gr;

    /* renamed from: com.google.android.gms.internal.gs.1 */
    static class C14891 extends C1321h<Bitmap> {
        C14891(String str, int i) {
            super(str, i);
        }

        protected /* synthetic */ Object m3418b(DataHolder dataHolder, int i, int i2) {
            return m3419i(dataHolder, i, i2);
        }

        protected Bitmap m3419i(DataHolder dataHolder, int i, int i2) {
            throw new IllegalStateException("Thumbnail field is write only");
        }
    }

    /* renamed from: com.google.android.gms.internal.gs.a */
    public static class C1490a extends C1318a implements SearchableMetadataField<Boolean> {
        public C1490a(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: com.google.android.gms.internal.gs.b */
    public static class C1491b extends C1322j implements SearchableMetadataField<String> {
        public C1491b(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: com.google.android.gms.internal.gs.d */
    public static class C1492d extends C1320e implements SortableMetadataField<Long> {
        public C1492d(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: com.google.android.gms.internal.gs.e */
    public static class C1493e extends C1318a implements SearchableMetadataField<Boolean> {
        public C1493e(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: com.google.android.gms.internal.gs.f */
    public static class C1494f extends C1322j implements SearchableMetadataField<String>, SortableMetadataField<String> {
        public C1494f(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: com.google.android.gms.internal.gs.g */
    public static class C1495g extends C1318a implements SearchableMetadataField<Boolean> {
        public C1495g(String str, int i) {
            super(str, i);
        }

        protected /* synthetic */ Object m3420b(DataHolder dataHolder, int i, int i2) {
            return m3421d(dataHolder, i, i2);
        }

        protected Boolean m3421d(DataHolder dataHolder, int i, int i2) {
            return Boolean.valueOf(dataHolder.getInteger(getName(), i, i2) != 0);
        }
    }

    /* renamed from: com.google.android.gms.internal.gs.c */
    public static class C1536c extends C1482g<DriveId> implements SearchableCollectionMetadataField<DriveId> {
        public C1536c(String str, int i) {
            super(str, i);
        }
    }

    static {
        FR = gu.Gx;
        FS = new C1322j("alternateLink", 4300000);
        FT = new C1322j(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, 4300000);
        FU = new C1322j("embedLink", 4300000);
        FV = new C1322j("fileExtension", 4300000);
        FW = new C1320e("fileSize", 4300000);
        FX = new C1318a("hasThumbnail", 4300000);
        FY = new C1322j("indexableText", 4300000);
        FZ = new C1318a("isAppData", 4300000);
        Ga = new C1318a("isCopyable", 4300000);
        Gb = new C1318a("isEditable", 4100000);
        Gc = new C1490a("isPinned", 4100000);
        Gd = new C1318a("isRestricted", 4300000);
        Ge = new C1318a("isShared", 4300000);
        Gf = new C1318a("isTrashable", 4400000);
        Gg = new C1318a("isViewed", 4300000);
        Gh = new C1491b("mimeType", 4100000);
        Gi = new C1322j("originalFilename", 4300000);
        Gj = new C1483i("ownerNames", 4300000);
        Gk = new C1536c("parents", 4100000);
        Gl = new C1492d("quotaBytesUsed", 4300000);
        Gm = new C1493e("starred", 4100000);
        Gn = new C14891("thumbnail", 4400000);
        Go = new C1494f(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE, 4100000);
        Gp = new C1495g("trashed", 4100000);
        Gq = new C1322j("webContentLink", 4300000);
        Gr = new C1322j("webViewLink", 4300000);
    }
}
