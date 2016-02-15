/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal;

import java.lang.reflect.Array;
import org.junit.Assert;
import org.junit.internal.ArrayComparisonFailure;

public abstract class ComparisonCriteria {
    private int assertArraysAreSameLength(Object object, Object object2, String string2) {
        int n2;
        int n3;
        if (object == null) {
            Assert.fail(string2 + "expected array was null");
        }
        if (object2 == null) {
            Assert.fail(string2 + "actual array was null");
        }
        if ((n3 = Array.getLength(object2)) != (n2 = Array.getLength(object))) {
            Assert.fail(string2 + "array lengths differed, expected.length=" + n2 + " actual.length=" + n3);
        }
        return n2;
    }

    private boolean isArray(Object object) {
        if (object != null && object.getClass().isArray()) {
            return true;
        }
        return false;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void arrayEquals(String string2, Object object, Object object2) throws ArrayComparisonFailure {
        if (object == object2) {
            return;
        }
        String string3 = string2 == null ? "" : string2 + ": ";
        int n2 = this.assertArraysAreSameLength(object, object2, string3);
        int n3 = 0;
        while (n3 < n2) {
            Object object3 = Array.get(object, n3);
            Object object4 = Array.get(object2, n3);
            if (this.isArray(object3) && this.isArray(object4)) {
                try {
                    this.arrayEquals(string2, object3, object4);
                }
                catch (ArrayComparisonFailure var1_2) {
                    var1_2.addDimension(n3);
                    throw var1_2;
                }
            } else {
                this.assertElementsEqual(object3, object4);
            }
            ++n3;
        }
        return;
        catch (AssertionError assertionError) {
            throw new ArrayComparisonFailure(string3, assertionError, n3);
        }
    }

    protected abstract void assertElementsEqual(Object var1, Object var2);
}

