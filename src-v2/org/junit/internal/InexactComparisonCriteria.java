/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal;

import org.junit.Assert;
import org.junit.internal.ComparisonCriteria;

public class InexactComparisonCriteria
extends ComparisonCriteria {
    public Object fDelta;

    public InexactComparisonCriteria(double d2) {
        this.fDelta = d2;
    }

    public InexactComparisonCriteria(float f2) {
        this.fDelta = Float.valueOf(f2);
    }

    @Override
    protected void assertElementsEqual(Object object, Object object2) {
        if (object instanceof Double) {
            Assert.assertEquals((Double)object, (double)((Double)object2), (double)((Double)this.fDelta));
            return;
        }
        Assert.assertEquals(((Float)object).floatValue(), ((Float)object2).floatValue(), ((Float)this.fDelta).floatValue());
    }
}

