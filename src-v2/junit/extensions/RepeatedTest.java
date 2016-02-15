/*
 * Decompiled with CFR 0_110.
 */
package junit.extensions;

import junit.extensions.TestDecorator;
import junit.framework.Test;
import junit.framework.TestResult;

public class RepeatedTest
extends TestDecorator {
    private int fTimesRepeat;

    public RepeatedTest(Test test, int n2) {
        super(test);
        if (n2 < 0) {
            throw new IllegalArgumentException("Repetition count must be >= 0");
        }
        this.fTimesRepeat = n2;
    }

    @Override
    public int countTestCases() {
        return super.countTestCases() * this.fTimesRepeat;
    }

    @Override
    public void run(TestResult testResult) {
        int n2 = 0;
        while (n2 < this.fTimesRepeat && !testResult.shouldStop()) {
            super.run(testResult);
            ++n2;
        }
        return;
    }

    @Override
    public String toString() {
        return super.toString() + "(repeated)";
    }
}

