/*
 * Decompiled with CFR 0_110.
 */
package junit.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import junit.framework.JUnit4TestAdapter;
import junit.framework.JUnit4TestCaseFacade;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

public class JUnit4TestAdapterCache
extends HashMap<Description, Test> {
    private static final JUnit4TestAdapterCache fInstance = new JUnit4TestAdapterCache();
    private static final long serialVersionUID = 1;

    public static JUnit4TestAdapterCache getDefault() {
        return fInstance;
    }

    public Test asTest(Description description) {
        if (description.isSuite()) {
            return this.createTest(description);
        }
        if (!this.containsKey(description)) {
            this.put(description, this.createTest(description));
        }
        return (Test)this.get(description);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<Test> asTestList(Description list) {
        if (list.isTest()) {
            return Arrays.asList(this.asTest((Description)((Object)list)));
        }
        ArrayList<Test> arrayList = new ArrayList<Test>();
        Iterator<Description> iterator = list.getChildren().iterator();
        do {
            list = arrayList;
            if (!iterator.hasNext()) return list;
            arrayList.add(this.asTest(iterator.next()));
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    Test createTest(Description object) {
        if (object.isTest()) {
            return new JUnit4TestCaseFacade((Description)object);
        }
        TestSuite testSuite = new TestSuite(object.getDisplayName());
        Iterator<Description> iterator = object.getChildren().iterator();
        do {
            object = testSuite;
            if (!iterator.hasNext()) return object;
            testSuite.addTest(this.asTest(iterator.next()));
        } while (true);
    }

    public RunNotifier getNotifier(final TestResult testResult, JUnit4TestAdapter object) {
        object = new RunNotifier();
        object.addListener(new RunListener(){

            @Override
            public void testFailure(Failure failure) throws Exception {
                testResult.addError(JUnit4TestAdapterCache.this.asTest(failure.getDescription()), failure.getException());
            }

            @Override
            public void testFinished(Description description) throws Exception {
                testResult.endTest(JUnit4TestAdapterCache.this.asTest(description));
            }

            @Override
            public void testStarted(Description description) throws Exception {
                testResult.startTest(JUnit4TestAdapterCache.this.asTest(description));
            }
        });
        return object;
    }

}

