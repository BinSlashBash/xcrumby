/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal;

import org.junit.Assert;
import org.junit.internal.ComparisonCriteria;

public class ExactComparisonCriteria
extends ComparisonCriteria {
    @Override
    protected void assertElementsEqual(Object object, Object object2) {
        Assert.assertEquals(object, object2);
    }
}

