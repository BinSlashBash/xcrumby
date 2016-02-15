/*
 * Decompiled with CFR 0_110.
 */
package junit.extensions;

import junit.extensions.TestDecorator;
import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestResult;

public class TestSetup
extends TestDecorator {
    public TestSetup(Test test) {
        super(test);
    }

    @Override
    public void run(final TestResult testResult) {
        testResult.runProtected(this, new Protectable(){

            @Override
            public void protect() throws Exception {
                TestSetup.this.setUp();
                TestSetup.this.basicRun(testResult);
                TestSetup.this.tearDown();
            }
        });
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

}

