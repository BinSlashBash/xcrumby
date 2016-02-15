package org.junit.experimental.max;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import junit.framework.TestSuite;
import org.junit.internal.requests.SortingRequest;
import org.junit.internal.runners.ErrorReportingRunner;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

public class MaxCore {
    private static final String MALFORMED_JUNIT_3_TEST_CLASS_PREFIX = "malformed JUnit 3 test class: ";
    private final MaxHistory fHistory;

    /* renamed from: org.junit.experimental.max.MaxCore.1 */
    class C12481 extends Request {
        final /* synthetic */ List val$runners;

        /* renamed from: org.junit.experimental.max.MaxCore.1.1 */
        class C15501 extends Suite {
            C15501(Class x0, List x1) {
                super(x0, x1);
            }
        }

        C12481(List list) {
            this.val$runners = list;
        }

        public Runner getRunner() {
            try {
                return new C15501((Class) null, this.val$runners);
            } catch (InitializationError e) {
                return new ErrorReportingRunner(null, e);
            }
        }
    }

    @Deprecated
    public static MaxCore forFolder(String folderName) {
        return storedLocally(new File(folderName));
    }

    public static MaxCore storedLocally(File storedResults) {
        return new MaxCore(storedResults);
    }

    private MaxCore(File storedResults) {
        this.fHistory = MaxHistory.forFolder(storedResults);
    }

    public Result run(Class<?> testClass) {
        return run(Request.aClass(testClass));
    }

    public Result run(Request request) {
        return run(request, new JUnitCore());
    }

    public Result run(Request request, JUnitCore core) {
        core.addListener(this.fHistory.listener());
        return core.run(sortRequest(request).getRunner());
    }

    public Request sortRequest(Request request) {
        if (request instanceof SortingRequest) {
            return request;
        }
        List<Description> leaves = findLeaves(request);
        Collections.sort(leaves, this.fHistory.testComparator());
        return constructLeafRequest(leaves);
    }

    private Request constructLeafRequest(List<Description> leaves) {
        List<Runner> runners = new ArrayList();
        for (Description each : leaves) {
            runners.add(buildRunner(each));
        }
        return new C12481(runners);
    }

    private Runner buildRunner(Description each) {
        if (each.toString().equals("TestSuite with 0 tests")) {
            return Suite.emptySuite();
        }
        if (each.toString().startsWith(MALFORMED_JUNIT_3_TEST_CLASS_PREFIX)) {
            return new JUnit38ClassRunner(new TestSuite(getMalformedTestClass(each)));
        }
        Class<?> type = each.getTestClass();
        if (type == null) {
            throw new RuntimeException("Can't build a runner from description [" + each + "]");
        }
        String methodName = each.getMethodName();
        if (methodName == null) {
            return Request.aClass(type).getRunner();
        }
        return Request.method(type, methodName).getRunner();
    }

    private Class<?> getMalformedTestClass(Description each) {
        try {
            return Class.forName(each.toString().replace(MALFORMED_JUNIT_3_TEST_CLASS_PREFIX, UnsupportedUrlFragment.DISPLAY_NAME));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public List<Description> sortedLeavesForTest(Request request) {
        return findLeaves(sortRequest(request));
    }

    private List<Description> findLeaves(Request request) {
        List<Description> results = new ArrayList();
        findLeaves(null, request.getRunner().getDescription(), results);
        return results;
    }

    private void findLeaves(Description parent, Description description, List<Description> results) {
        if (!description.getChildren().isEmpty()) {
            Iterator i$ = description.getChildren().iterator();
            while (i$.hasNext()) {
                findLeaves(description, (Description) i$.next(), results);
            }
        } else if (description.toString().equals("warning(junit.framework.TestSuite$1)")) {
            results.add(Description.createSuiteDescription(MALFORMED_JUNIT_3_TEST_CLASS_PREFIX + parent, new Annotation[0]));
        } else {
            results.add(description);
        }
    }
}
