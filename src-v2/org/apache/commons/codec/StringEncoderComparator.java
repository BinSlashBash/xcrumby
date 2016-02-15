/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec;

import java.util.Comparator;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class StringEncoderComparator
implements Comparator {
    private final StringEncoder stringEncoder;

    @Deprecated
    public StringEncoderComparator() {
        this.stringEncoder = null;
    }

    public StringEncoderComparator(StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
    }

    public int compare(Object object, Object object2) {
        try {
            int n2 = ((Comparable)this.stringEncoder.encode(object)).compareTo((Comparable)this.stringEncoder.encode(object2));
            return n2;
        }
        catch (EncoderException var1_2) {
            return 0;
        }
    }
}

