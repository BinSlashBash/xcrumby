package com.google.android.gms.drive;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* renamed from: com.google.android.gms.drive.b */
public final class C1302b extends Metadata {
    private final MetadataBundle ED;

    public C1302b(MetadataBundle metadataBundle) {
        this.ED = metadataBundle;
    }

    protected <T> T m2654a(MetadataField<T> metadataField) {
        return this.ED.m1744a((MetadataField) metadataField);
    }

    public Metadata fB() {
        return new C1302b(MetadataBundle.m1743a(this.ED));
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
