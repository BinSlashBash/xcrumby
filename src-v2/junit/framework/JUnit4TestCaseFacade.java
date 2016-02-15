/*
 * Decompiled with CFR 0_110.
 */
package junit.framework;

import junit.framework.Test;
import junit.framework.TestResult;
import org.junit.runner.Describable;
import org.junit.runner.Description;

public class JUnit4TestCaseFacade
implements Test,
Describable {
    private final Description fDescription;

    JUnit4TestCaseFacade(Description description) {
        this.fDescription = description;
    }

    @Override
    public int countTestCases() {
        return 1;
    }

    @Override
    public Description getDescription() {
        return this.fDescription;
    }

    @Override
    public void run(TestResult testResult) {
        throw new RuntimeException("This test stub created only for informational purposes.");
    }

    public String toString() {
        return this.getDescription().toString();
    }
}

