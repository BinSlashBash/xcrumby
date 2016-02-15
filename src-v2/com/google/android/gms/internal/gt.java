/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.drive.metadata.SearchableOrderedMetadataField;
import com.google.android.gms.drive.metadata.SortableMetadataField;
import java.util.Date;

public class gt {
    public static final a Gs = new a("created", 4100000);
    public static final b Gt = new b("lastOpenedTime", 4300000);
    public static final d Gu = new d("modified", 4100000);
    public static final c Gv = new c("modifiedByMe", 4100000);
    public static final e Gw = new e("sharedWithMe", 4100000);

    public static class a
    extends com.google.android.gms.drive.metadata.internal.b
    implements SortableMetadataField<Date> {
        public a(String string2, int n2) {
            super(string2, n2);
        }
    }

    public static class b
    extends com.google.android.gms.drive.metadata.internal.b
    implements SearchableOrderedMetadataField<Date>,
    SortableMetadataField<Date> {
        public b(String string2, int n2) {
            super(string2, n2);
        }
    }

    public static class c
    extends com.google.android.gms.drive.metadata.internal.b
    implements SortableMetadataField<Date> {
        public c(String string2, int n2) {
            super(string2, n2);
        }
    }

    public static class d
    extends com.google.android.gms.drive.metadata.internal.b
    implements SearchableOrderedMetadataField<Date>,
    SortableMetadataField<Date> {
        public d(String string2, int n2) {
            super(string2, n2);
        }
    }

    public static class e
    extends com.google.android.gms.drive.metadata.internal.b
    implements SearchableOrderedMetadataField<Date>,
    SortableMetadataField<Date> {
        public e(String string2, int n2) {
            super(string2, n2);
        }
    }

}

