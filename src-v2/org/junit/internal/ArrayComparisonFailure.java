/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayComparisonFailure
extends AssertionError {
    private static final long serialVersionUID = 1;
    private final AssertionError fCause;
    private List<Integer> fIndices = new ArrayList<Integer>();
    private final String fMessage;

    public ArrayComparisonFailure(String string2, AssertionError assertionError, int n2) {
        this.fMessage = string2;
        this.fCause = assertionError;
        this.addDimension(n2);
    }

    public void addDimension(int n2) {
        this.fIndices.add(0, n2);
    }

    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.fMessage != null) {
            stringBuilder.append(this.fMessage);
        }
        stringBuilder.append("arrays first differed at element ");
        Iterator<Integer> iterator = this.fIndices.iterator();
        while (iterator.hasNext()) {
            int n2 = iterator.next();
            stringBuilder.append("[");
            stringBuilder.append(n2);
            stringBuilder.append("]");
        }
        stringBuilder.append("; ");
        stringBuilder.append(this.fCause.getMessage());
        return stringBuilder.toString();
    }

    public String toString() {
        return this.getMessage();
    }
}

