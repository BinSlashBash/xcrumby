package com.google.android.gms.plus.model.moments;

import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.internal.ic;
import com.google.android.gms.internal.ie;
import java.util.HashSet;
import java.util.Set;

public interface Moment extends Freezable<Moment> {

    public static class Builder {
        private String Rd;
        private final Set<Integer> UJ;
        private ic VE;
        private ic VF;
        private String Vw;
        private String wp;

        public Builder() {
            this.UJ = new HashSet();
        }

        public Moment build() {
            return new ie(this.UJ, this.wp, this.VE, this.Vw, this.VF, this.Rd);
        }

        public Builder setId(String id) {
            this.wp = id;
            this.UJ.add(Integer.valueOf(2));
            return this;
        }

        public Builder setResult(ItemScope result) {
            this.VE = (ic) result;
            this.UJ.add(Integer.valueOf(4));
            return this;
        }

        public Builder setStartDate(String startDate) {
            this.Vw = startDate;
            this.UJ.add(Integer.valueOf(5));
            return this;
        }

        public Builder setTarget(ItemScope target) {
            this.VF = (ic) target;
            this.UJ.add(Integer.valueOf(6));
            return this;
        }

        public Builder setType(String type) {
            this.Rd = type;
            this.UJ.add(Integer.valueOf(7));
            return this;
        }
    }

    String getId();

    ItemScope getResult();

    String getStartDate();

    ItemScope getTarget();

    String getType();

    boolean hasId();

    boolean hasResult();

    boolean hasStartDate();

    boolean hasTarget();

    boolean hasType();
}
