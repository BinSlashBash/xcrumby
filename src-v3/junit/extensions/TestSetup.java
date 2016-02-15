package junit.extensions;

import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestResult;

public class TestSetup extends TestDecorator {

    /* renamed from: junit.extensions.TestSetup.1 */
    class C12051 implements Protectable {
        final /* synthetic */ TestResult val$result;

        C12051(TestResult testResult) {
            this.val$result = testResult;
        }

        public void protect() throws Exception {
            TestSetup.this.setUp();
            TestSetup.this.basicRun(this.val$result);
            TestSetup.this.tearDown();
        }
    }

    public TestSetup(Test test) {
        super(test);
    }

    public void run(TestResult result) {
        result.runProtected(this, new C12051(result));
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
}
