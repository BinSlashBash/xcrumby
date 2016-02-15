/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.plus.model.people;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.c;
import com.google.android.gms.internal.ih;
import com.google.android.gms.internal.ii;
import com.google.android.gms.internal.is;
import com.google.android.gms.plus.model.people.Person;

public final class PersonBuffer
extends DataBuffer<Person> {
    private final c<ih> Wr;

    public PersonBuffer(DataHolder dataHolder) {
        super(dataHolder);
        if (dataHolder.getMetadata() != null && dataHolder.getMetadata().getBoolean("com.google.android.gms.plus.IsSafeParcelable", false)) {
            this.Wr = new c(dataHolder, ih.CREATOR);
            return;
        }
        this.Wr = null;
    }

    @Override
    public Person get(int n2) {
        if (this.Wr != null) {
            return this.Wr.F(n2);
        }
        return new is(this.BB, n2);
    }
}

