/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.drive.internal;

import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class j
extends Metadata {
    private final MetadataBundle ED;

    public j(MetadataBundle metadataBundle) {
        this.ED = metadataBundle;
    }

    @Override
    protected <T> T a(MetadataField<T> metadataField) {
        return this.ED.a(metadataField);
    }

    public Metadata fB() {
        return new j(MetadataBundle.a(this.ED));
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.fB();
    }

    @Override
    public boolean isDataValid() {
        if (this.ED != null) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Metadata [mImpl=" + this.ED + "]";
    }
}

