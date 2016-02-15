package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.C1316b;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONException;

/* renamed from: com.google.android.gms.drive.metadata.internal.i */
public class C1483i extends C1316b<String> {
    public C1483i(String str, int i) {
        super(str, i);
    }

    public static final Collection<String> ay(String str) throws JSONException {
        if (str == null) {
            return null;
        }
        Collection arrayList = new ArrayList();
        JSONArray jSONArray = new JSONArray(str);
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(jSONArray.getString(i));
        }
        return Collections.unmodifiableCollection(arrayList);
    }

    protected void m3287a(Bundle bundle, Collection<String> collection) {
        bundle.putStringArrayList(getName(), new ArrayList(collection));
    }

    protected /* synthetic */ Object m3288b(DataHolder dataHolder, int i, int i2) {
        return m3289c(dataHolder, i, i2);
    }

    protected Collection<String> m3289c(DataHolder dataHolder, int i, int i2) {
        try {
            return C1483i.ay(dataHolder.getString(getName(), i, i2));
        } catch (Throwable e) {
            throw new IllegalStateException("DataHolder supplied invalid JSON", e);
        }
    }

    protected /* synthetic */ Object m3290e(Bundle bundle) {
        return m3291j(bundle);
    }

    protected Collection<String> m3291j(Bundle bundle) {
        return bundle.getStringArrayList(getName());
    }
}
