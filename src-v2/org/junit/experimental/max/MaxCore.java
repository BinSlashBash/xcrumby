/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.max;

import java.io.File;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.experimental.max.MaxHistory;
import org.junit.internal.requests.SortingRequest;
import org.junit.internal.runners.ErrorReportingRunner;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

public class MaxCore {
    private static final String MALFORMED_JUNIT_3_TEST_CLASS_PREFIX = "malformed JUnit 3 test class: ";
    private final MaxHistory fHistory;

    private MaxCore(File file) {
        this.fHistory = MaxHistory.forFolder(file);
    }

    private Runner buildRunner(Description object) {
        if (object.toString().equals("TestSuite with 0 tests")) {
            return Suite.emptySuite();
        }
        if (object.toString().startsWith("malformed JUnit 3 test class: ")) {
            return new JUnit38ClassRunner(new TestSuite(this.getMalformedTestClass((Description)object)));
        }
        Class class_ = object.getTestClass();
        if (class_ == null) {
            throw new RuntimeException("Can't build a runner from description [" + object + "]");
        }
        if ((object = object.getMethodName()) == null) {
            return Request.aClass(class_).getRunner();
        }
        return Request.method(class_, (String)object).getRunner();
    }

    private Request constructLeafRequest(List<Description> object) {
        final ArrayList<Runner> arrayList = new ArrayList<Runner>();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(this.buildRunner((Description)object.next()));
        }
        return new Request(){

            @Override
            public Runner getRunner() {
                try {
                    Suite suite = new Suite(null, arrayList){};
                    return suite;
                }
                catch (InitializationError var1_2) {
                    return new ErrorReportingRunner(null, var1_2);
                }
            }

        };
    }

    private List<Description> findLeaves(Request request) {
        ArrayList<Description> arrayList = new ArrayList<Description>();
        this.findLeaves(null, request.getRunner().getDescription(), arrayList);
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void findLeaves(Description iterator, Description description, List<Description> list) {
        if (description.getChildren().isEmpty()) {
            if (!description.toString().equals("warning(junit.framework.TestSuite$1)")) {
                list.add(description);
                return;
            }
            list.add(Description.createSuiteDescription("malformed JUnit 3 test class: " + iterator, new Annotation[0]));
            return;
        } else {
            iterator = description.getChildren().iterator();
            while (iterator.hasNext()) {
                this.findLeaves(description, iterator.next(), list);
            }
        }
    }

    @Deprecated
    public static MaxCore forFolder(String string2) {
        return MaxCore.storedLocally(new File(string2));
    }

    private Class<?> getMalformedTestClass(Description serializable) {
        try {
            serializable = Class.forName(serializable.toString().replace("malformed JUnit 3 test class: ", ""));
            return serializable;
        }
        catch (ClassNotFoundException var1_2) {
            return null;
        }
    }

    public static MaxCore storedLocally(File file) {
        return new MaxCore(file);
    }

    public Result run(Class<?> class_) {
        return this.run(Request.aClass(class_));
    }

    public Result run(Request request) {
        return this.run(request, new JUnitCore());
    }

    public Result run(Request request, JUnitCore jUnitCore) {
        jUnitCore.addListener(this.fHistory.listener());
        return jUnitCore.run(this.sortRequest(request).getRunner());
    }

    public Request sortRequest(Request object) {
        if (object instanceof SortingRequest) {
            return object;
        }
        object = this.findLeaves((Request)object);
        Collections.sort(object, this.fHistory.testComparator());
        return this.constructLeafRequest((List<Description>)object);
    }

    public List<Description> sortedLeavesForTest(Request request) {
        return this.findLeaves(this.sortRequest(request));
    }

}

