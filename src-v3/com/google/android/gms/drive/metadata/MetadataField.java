package com.google.android.gms.drive.metadata;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Collection;

public interface MetadataField<T> {
    T m326a(DataHolder dataHolder, int i, int i2);

    void m327a(DataHolder dataHolder, MetadataBundle metadataBundle, int i, int i2);

    void m328a(T t, Bundle bundle);

    T m329d(Bundle bundle);

    Collection<String> fR();

    String getName();
}
