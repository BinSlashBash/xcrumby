/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.b;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONException;

public class i
extends b<String> {
    public i(String string2, int n2) {
        super(string2, n2);
    }

    public static final Collection<String> ay(String object) throws JSONException {
        if (object == null) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        object = new JSONArray((String)object);
        for (int i2 = 0; i2 < object.length(); ++i2) {
            arrayList.add(object.getString(i2));
        }
        return Collections.unmodifiableCollection(arrayList);
    }

    @Override
    protected void a(Bundle bundle, Collection<String> collection) {
        bundle.putStringArrayList(this.getName(), new ArrayList<String>(collection));
    }

    @Override
    protected /* synthetic */ Object b(DataHolder dataHolder, int n2, int n3) {
        return this.c(dataHolder, n2, n3);
    }

    @Override
    protected Collection<String> c(DataHolder object, int n2, int n3) {
        try {
            object = i.ay(object.getString(this.getName(), n2, n3));
            return object;
        }
        catch (JSONException var1_2) {
            throw new IllegalStateException("DataHolder supplied invalid JSON", var1_2);
        }
    }

    @Override
    protected /* synthetic */ Object e(Bundle bundle) {
        return this.j(bundle);
    }

    protected Collection<String> j(Bundle bundle) {
        return bundle.getStringArrayList(this.getName());
    }
}

