/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ga;
import com.google.android.gms.internal.ic;
import com.google.android.gms.internal.if;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.google.android.gms.plus.model.moments.Moment;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class ie
extends ga
implements SafeParcelable,
Moment {
    public static final if CREATOR = new if();
    private static final HashMap<String, ga.a<?, ?>> UI = new HashMap();
    private String Rd;
    private final Set<Integer> UJ;
    private ic VE;
    private ic VF;
    private String Vw;
    private String wp;
    private final int xH;

    static {
        UI.put("id", ga.a.j("id", 2));
        UI.put("result", ga.a.a("result", 4, ic.class));
        UI.put("startDate", ga.a.j("startDate", 5));
        UI.put("target", ga.a.a("target", 6, ic.class));
        UI.put("type", ga.a.j("type", 7));
    }

    public ie() {
        this.xH = 1;
        this.UJ = new HashSet<Integer>();
    }

    ie(Set<Integer> set, int n2, String string2, ic ic2, String string3, ic ic3, String string4) {
        this.UJ = set;
        this.xH = n2;
        this.wp = string2;
        this.VE = ic2;
        this.Vw = string3;
        this.VF = ic3;
        this.Rd = string4;
    }

    public ie(Set<Integer> set, String string2, ic ic2, String string3, ic ic3, String string4) {
        this.UJ = set;
        this.xH = 1;
        this.wp = string2;
        this.VE = ic2;
        this.Vw = string3;
        this.VF = ic3;
        this.Rd = string4;
    }

    @Override
    protected boolean a(ga.a a2) {
        return this.UJ.contains(a2.ff());
    }

    @Override
    protected Object aq(String string2) {
        return null;
    }

    @Override
    protected boolean ar(String string2) {
        return false;
    }

    @Override
    protected Object b(ga.a a2) {
        switch (a2.ff()) {
            default: {
                throw new IllegalStateException("Unknown safe parcelable id=" + a2.ff());
            }
            case 2: {
                return this.wp;
            }
            case 4: {
                return this.VE;
            }
            case 5: {
                return this.Vw;
            }
            case 6: {
                return this.VF;
            }
            case 7: 
        }
        return this.Rd;
    }

    public int describeContents() {
        if if_ = CREATOR;
        return 0;
    }

    @Override
    public HashMap<String, ga.a<?, ?>> eY() {
        return UI;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ie)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        object = (ie)object;
        for (ga.a a2 : UI.values()) {
            if (this.a(a2)) {
                if (object.a(a2)) {
                    if (this.b(a2).equals(object.b(a2))) continue;
                    return false;
                }
                return false;
            }
            if (!object.a(a2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.jt();
    }

    @Override
    public String getId() {
        return this.wp;
    }

    @Override
    public ItemScope getResult() {
        return this.VE;
    }

    @Override
    public String getStartDate() {
        return this.Vw;
    }

    @Override
    public ItemScope getTarget() {
        return this.VF;
    }

    @Override
    public String getType() {
        return this.Rd;
    }

    int getVersionCode() {
        return this.xH;
    }

    @Override
    public boolean hasId() {
        return this.UJ.contains(2);
    }

    @Override
    public boolean hasResult() {
        return this.UJ.contains(4);
    }

    @Override
    public boolean hasStartDate() {
        return this.UJ.contains(5);
    }

    @Override
    public boolean hasTarget() {
        return this.UJ.contains(6);
    }

    @Override
    public boolean hasType() {
        return this.UJ.contains(7);
    }

    public int hashCode() {
        Iterator iterator = UI.values().iterator();
        int n2 = 0;
        while (iterator.hasNext()) {
            ga.a a2 = iterator.next();
            if (!this.a(a2)) continue;
            int n3 = a2.ff();
            n2 = this.b(a2).hashCode() + (n2 + n3);
        }
        return n2;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    Set<Integer> ja() {
        return this.UJ;
    }

    ic jr() {
        return this.VE;
    }

    ic js() {
        return this.VF;
    }

    public ie jt() {
        return this;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        if if_ = CREATOR;
        if.a(this, parcel, n2);
    }
}

