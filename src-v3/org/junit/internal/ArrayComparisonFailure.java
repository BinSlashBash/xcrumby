package org.junit.internal;

import java.util.ArrayList;
import java.util.List;

public class ArrayComparisonFailure extends AssertionError {
    private static final long serialVersionUID = 1;
    private final AssertionError fCause;
    private List<Integer> fIndices;
    private final String fMessage;

    public ArrayComparisonFailure(String message, AssertionError cause, int index) {
        this.fIndices = new ArrayList();
        this.fMessage = message;
        this.fCause = cause;
        addDimension(index);
    }

    public void addDimension(int index) {
        this.fIndices.add(0, Integer.valueOf(index));
    }

    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        if (this.fMessage != null) {
            builder.append(this.fMessage);
        }
        builder.append("arrays first differed at element ");
        for (Integer intValue : this.fIndices) {
            int each = intValue.intValue();
            builder.append("[");
            builder.append(each);
            builder.append("]");
        }
        builder.append("; ");
        builder.append(this.fCause.getMessage());
        return builder.toString();
    }

    public String toString() {
        return getMessage();
    }
}
