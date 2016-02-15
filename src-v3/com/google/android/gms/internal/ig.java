package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.data.C0251b;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.google.android.gms.plus.model.moments.Moment;

public final class ig extends C0251b implements Moment {
    private ie VG;

    public ig(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    private ie ju() {
        synchronized (this) {
            if (this.VG == null) {
                byte[] byteArray = getByteArray("momentImpl");
                Parcel obtain = Parcel.obtain();
                obtain.unmarshall(byteArray, 0, byteArray.length);
                obtain.setDataPosition(0);
                this.VG = ie.CREATOR.aM(obtain);
                obtain.recycle();
            }
        }
        return this.VG;
    }

    public /* synthetic */ Object freeze() {
        return jt();
    }

    public String getId() {
        return ju().getId();
    }

    public ItemScope getResult() {
        return ju().getResult();
    }

    public String getStartDate() {
        return ju().getStartDate();
    }

    public ItemScope getTarget() {
        return ju().getTarget();
    }

    public String getType() {
        return ju().getType();
    }

    public boolean hasId() {
        return ju().hasId();
    }

    public boolean hasResult() {
        return ju().hasId();
    }

    public boolean hasStartDate() {
        return ju().hasStartDate();
    }

    public boolean hasTarget() {
        return ju().hasTarget();
    }

    public boolean hasType() {
        return ju().hasType();
    }

    public ie jt() {
        return ju();
    }
}
