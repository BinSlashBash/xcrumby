/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runner;

import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import junit.framework.Test;
import junit.runner.Version;
import org.junit.internal.JUnitSystem;
import org.junit.internal.RealSystem;
import org.junit.internal.TextListener;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.Computer;
import org.junit.runner.Description;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

public class JUnitCore {
    private final RunNotifier fNotifier = new RunNotifier();

    static Computer defaultComputer() {
        return new Computer();
    }

    public static /* varargs */ void main(String ... arrstring) {
        JUnitCore.runMainAndExit(new RealSystem(), arrstring);
    }

    public static /* varargs */ Result runClasses(Computer computer, Class<?> ... arrclass) {
        return new JUnitCore().run(computer, arrclass);
    }

    public static /* varargs */ Result runClasses(Class<?> ... arrclass) {
        return new JUnitCore().run(JUnitCore.defaultComputer(), arrclass);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private /* varargs */ Result runMain(JUnitSystem object, String ... iterator) {
        object.out().println("JUnit version " + Version.id());
        ArrayList arrayList = new ArrayList();
        ArrayList<Failure> arrayList2 = new ArrayList<Failure>();
        for (Iterator iterator2 : iterator) {
            try {
                arrayList.add(Class.forName(iterator2));
                continue;
            }
            catch (ClassNotFoundException var8_10) {
                object.out().println("Could not find class: " + iterator2);
                arrayList2.add(new Failure(Description.createSuiteDescription(iterator2, new Annotation[0]), var8_10));
            }
        }
        this.addListener(new TextListener((JUnitSystem)object));
        object = this.run(arrayList.toArray(new Class[0]));
        iterator = arrayList2.iterator();
        while (iterator.hasNext()) {
            Failure failure = (Failure)iterator.next();
            object.getFailures().add(failure);
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static /* varargs */ void runMainAndExit(JUnitSystem jUnitSystem, String ... arrstring) {
        int n2 = new JUnitCore().runMain(jUnitSystem, arrstring).wasSuccessful() ? 0 : 1;
        System.exit(n2);
    }

    public void addListener(RunListener runListener) {
        this.fNotifier.addListener(runListener);
    }

    public String getVersion() {
        return Version.id();
    }

    public void removeListener(RunListener runListener) {
        this.fNotifier.removeListener(runListener);
    }

    public Result run(Test test) {
        return this.run(new JUnit38ClassRunner(test));
    }

    public /* varargs */ Result run(Computer computer, Class<?> ... arrclass) {
        return this.run(Request.classes(computer, arrclass));
    }

    public Result run(Request request) {
        return this.run(request.getRunner());
    }

    public Result run(Runner runner) {
        Result result = new Result();
        RunListener runListener = result.createListener();
        this.fNotifier.addFirstListener(runListener);
        try {
            this.fNotifier.fireTestRunStarted(runner.getDescription());
            runner.run(this.fNotifier);
            this.fNotifier.fireTestRunFinished(result);
            return result;
        }
        finally {
            this.removeListener(runListener);
        }
    }

    public /* varargs */ Result run(Class<?> ... arrclass) {
        return this.run(Request.classes(JUnitCore.defaultComputer(), arrclass));
    }
}

