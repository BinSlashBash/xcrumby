package com.google.android.gms.internal;

import com.google.android.gms.drive.metadata.SearchableOrderedMetadataField;
import com.google.android.gms.drive.metadata.SortableMetadataField;
import com.google.android.gms.drive.metadata.internal.C1481b;
import java.util.Date;

public class gt {
    public static final C1537a Gs;
    public static final C1538b Gt;
    public static final C1540d Gu;
    public static final C1539c Gv;
    public static final C1541e Gw;

    /* renamed from: com.google.android.gms.internal.gt.a */
    public static class C1537a extends C1481b implements SortableMetadataField<Date> {
        public C1537a(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: com.google.android.gms.internal.gt.b */
    public static class C1538b extends C1481b implements SearchableOrderedMetadataField<Date>, SortableMetadataField<Date> {
        public C1538b(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: com.google.android.gms.internal.gt.c */
    public static class C1539c extends C1481b implements SortableMetadataField<Date> {
        public C1539c(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: com.google.android.gms.internal.gt.d */
    public static class C1540d extends C1481b implements SearchableOrderedMetadataField<Date>, SortableMetadataField<Date> {
        public C1540d(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: com.google.android.gms.internal.gt.e */
    public static class C1541e extends C1481b implements SearchableOrderedMetadataField<Date>, SortableMetadataField<Date> {
        public C1541e(String str, int i) {
            super(str, i);
        }
    }

    static {
        Gs = new C1537a("created", 4100000);
        Gt = new C1538b("lastOpenedTime", 4300000);
        Gu = new C1540d("modified", 4100000);
        Gv = new C1539c("modifiedByMe", 4100000);
        Gw = new C1541e("sharedWithMe", 4100000);
    }
}
