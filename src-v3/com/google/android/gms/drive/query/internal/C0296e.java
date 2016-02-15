package com.google.android.gms.drive.query.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Set;

/* renamed from: com.google.android.gms.drive.query.internal.e */
class C0296e {
    static MetadataField<?> m339b(MetadataBundle metadataBundle) {
        Set fU = metadataBundle.fU();
        if (fU.size() == 1) {
            return (MetadataField) fU.iterator().next();
        }
        throw new IllegalArgumentException("bundle should have exactly 1 populated field");
    }
}
