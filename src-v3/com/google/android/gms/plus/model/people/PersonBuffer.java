package com.google.android.gms.plus.model.people;

import com.google.android.gms.common.data.C0795c;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.ih;
import com.google.android.gms.internal.is;

public final class PersonBuffer extends DataBuffer<Person> {
    private final C0795c<ih> Wr;

    public PersonBuffer(DataHolder dataHolder) {
        super(dataHolder);
        if (dataHolder.getMetadata() == null || !dataHolder.getMetadata().getBoolean("com.google.android.gms.plus.IsSafeParcelable", false)) {
            this.Wr = null;
        } else {
            this.Wr = new C0795c(dataHolder, ih.CREATOR);
        }
    }

    public Person get(int position) {
        return this.Wr != null ? (Person) this.Wr.m1689F(position) : new is(this.BB, position);
    }
}
