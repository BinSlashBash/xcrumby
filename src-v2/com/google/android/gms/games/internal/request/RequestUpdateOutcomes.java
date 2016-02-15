/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.request;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.constants.RequestUpdateResultOutcome;
import com.google.android.gms.internal.fq;
import java.util.HashMap;
import java.util.Set;

public final class RequestUpdateOutcomes {
    private static final String[] LN = new String[]{"requestId", "outcome"};
    private final int Ah;
    private final HashMap<String, Integer> LO;

    private RequestUpdateOutcomes(int n2, HashMap<String, Integer> hashMap) {
        this.Ah = n2;
        this.LO = hashMap;
    }

    public static RequestUpdateOutcomes J(DataHolder dataHolder) {
        Builder builder = new Builder();
        builder.bm(dataHolder.getStatusCode());
        int n2 = dataHolder.getCount();
        for (int i2 = 0; i2 < n2; ++i2) {
            int n3 = dataHolder.G(i2);
            builder.s(dataHolder.getString("requestId", i2, n3), dataHolder.getInteger("outcome", i2, n3));
        }
        return builder.hB();
    }

    public Set<String> getRequestIds() {
        return this.LO.keySet();
    }

    public int getRequestOutcome(String string2) {
        fq.b(this.LO.containsKey(string2), (Object)("Request " + string2 + " was not part of the update operation!"));
        return this.LO.get(string2);
    }

    public static final class Builder {
        private int Ah = 0;
        private HashMap<String, Integer> LO = new HashMap();

        public Builder bm(int n2) {
            this.Ah = n2;
            return this;
        }

        public RequestUpdateOutcomes hB() {
            return new RequestUpdateOutcomes(this.Ah, this.LO);
        }

        public Builder s(String string2, int n2) {
            if (RequestUpdateResultOutcome.isValid(n2)) {
                this.LO.put(string2, n2);
            }
            return this;
        }
    }

}

