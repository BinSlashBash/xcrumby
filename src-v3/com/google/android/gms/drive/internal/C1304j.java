package com.google.android.gms.drive.internal;

import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* renamed from: com.google.android.gms.drive.internal.j */
public final class C1304j extends Metadata {
    private final MetadataBundle ED;

    public C1304j(MetadataBundle metadataBundle) {
        this.ED = metadataBundle;
    }

    protected <T> T m2663a(MetadataField<T> metadataField) {
        return this.ED.m1744a((MetadataField) metadataField);
    }

    public Metadata fB() {
        return new C1304j(MetadataBundle.m1743a(this.ED));
    }

    public /* synthetic */ Object freeze() {
        return fB();
    }

    public boolean isDataValid() {
        return this.ED != null;
    }

    public String toString() {
        return "Metadata [mImpl=" + this.ED + "]";
    }
}
