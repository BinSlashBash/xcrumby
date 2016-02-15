/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.plus.model.moments;

import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.internal.ic;
import com.google.android.gms.internal.ie;
import com.google.android.gms.plus.model.moments.ItemScope;
import java.util.HashSet;
import java.util.Set;

public interface Moment
extends Freezable<Moment> {
    public String getId();

    public ItemScope getResult();

    public String getStartDate();

    public ItemScope getTarget();

    public String getType();

    public boolean hasId();

    public boolean hasResult();

    public boolean hasStartDate();

    public boolean hasTarget();

    public boolean hasType();

    public static class Builder {
        private String Rd;
        private final Set<Integer> UJ = new HashSet<Integer>();
        private ic VE;
        private ic VF;
        private String Vw;
        private String wp;

        public Moment build() {
            return new ie(this.UJ, this.wp, this.VE, this.Vw, this.VF, this.Rd);
        }

        public Builder setId(String string2) {
            this.wp = string2;
            this.UJ.add(2);
            return this;
        }

        public Builder setResult(ItemScope itemScope) {
            this.VE = (ic)itemScope;
            this.UJ.add(4);
            return this;
        }

        public Builder setStartDate(String string2) {
            this.Vw = string2;
            this.UJ.add(5);
            return this;
        }

        public Builder setTarget(ItemScope itemScope) {
            this.VF = (ic)itemScope;
            this.UJ.add(6);
            return this;
        }

        public Builder setType(String string2) {
            this.Rd = string2;
            this.UJ.add(7);
            return this;
        }
    }

}

