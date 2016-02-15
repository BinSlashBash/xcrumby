/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.drive.query.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Iterator;
import java.util.Set;

class e {
    static MetadataField<?> b(MetadataBundle object) {
        if ((object = object.fU()).size() != 1) {
            throw new IllegalArgumentException("bundle should have exactly 1 populated field");
        }
        return (MetadataField)object.iterator().next();
    }
}

