/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.internal.ie;
import com.google.android.gms.internal.if;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.google.android.gms.plus.model.moments.Moment;

public final class ig
extends b
implements Moment {
    private ie VG;

    public ig(DataHolder dataHolder, int n2) {
        super(dataHolder, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ie ju() {
        synchronized (this) {
            if (this.VG == null) {
                byte[] arrby = this.getByteArray("momentImpl");
                Parcel parcel = Parcel.obtain();
                parcel.unmarshall(arrby, 0, arrby.length);
                parcel.setDataPosition(0);
                this.VG = ie.CREATOR.aM(parcel);
                parcel.recycle();
            }
            return this.VG;
        }
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.jt();
    }

    @Override
    public String getId() {
        return this.ju().getId();
    }

    @Override
    public ItemScope getResult() {
        return this.ju().getResult();
    }

    @Override
    public String getStartDate() {
        return this.ju().getStartDate();
    }

    @Override
    public ItemScope getTarget() {
        return this.ju().getTarget();
    }

    @Override
    public String getType() {
        return this.ju().getType();
    }

    @Override
    public boolean hasId() {
        return this.ju().hasId();
    }

    @Override
    public boolean hasResult() {
        return this.ju().hasId();
    }

    @Override
    public boolean hasStartDate() {
        return this.ju().hasStartDate();
    }

    @Override
    public boolean hasTarget() {
        return this.ju().hasTarget();
    }

    @Override
    public boolean hasType() {
        return this.ju().hasType();
    }

    public ie jt() {
        return this.ju();
    }
}

