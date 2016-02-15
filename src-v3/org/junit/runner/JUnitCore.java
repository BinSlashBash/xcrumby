package org.junit.runner;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Test;
import junit.runner.Version;
import org.junit.internal.JUnitSystem;
import org.junit.internal.RealSystem;
import org.junit.internal.TextListener;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

public class JUnitCore {
    private final RunNotifier fNotifier;

    public JUnitCore() {
        this.fNotifier = new RunNotifier();
    }

    public static void main(String... args) {
        runMainAndExit(new RealSystem(), args);
    }

    private static void runMainAndExit(JUnitSystem system, String... args) {
        System.exit(new JUnitCore().runMain(system, args).wasSuccessful() ? 0 : 1);
    }

    public static Result runClasses(Computer computer, Class<?>... classes) {
        return new JUnitCore().run(computer, classes);
    }

    public static Result runClasses(Class<?>... classes) {
        return new JUnitCore().run(defaultComputer(), classes);
    }

    private Result runMain(JUnitSystem system, String... args) {
        system.out().println("JUnit version " + Version.id());
        List<Class<?>> classes = new ArrayList();
        List<Failure> missingClasses = new ArrayList();
        for (String each : args) {
            try {
                classes.add(Class.forName(each));
            } catch (ClassNotFoundException e) {
                system.out().println("Could not find class: " + each);
                missingClasses.add(new Failure(Description.createSuiteDescription(each, new Annotation[0]), e));
            }
        }
        addListener(new TextListener(system));
        Result result = run((Class[]) classes.toArray(new Class[0]));
        for (Failure each2 : missingClasses) {
            result.getFailures().add(each2);
        }
        return result;
    }

    public String getVersion() {
        return Version.id();
    }

    public Result run(Class<?>... classes) {
        return run(Request.classes(defaultComputer(), classes));
    }

    public Result run(Computer computer, Class<?>... classes) {
        return run(Request.classes(computer, classes));
    }

    public Result run(Request request) {
        return run(request.getRunner());
    }

    public Result run(Test test) {
        return run(new JUnit38ClassRunner(test));
    }

    public Result run(Runner runner) {
        Result result = new Result();
        RunListener listener = result.createListener();
        this.fNotifier.addFirstListener(listener);
        try {
            this.fNotifier.fireTestRunStarted(runner.getDescription());
            runner.run(this.fNotifier);
            this.fNotifier.fireTestRunFinished(result);
            return result;
        } finally {
            removeListener(listener);
        }
    }

    public void addListener(RunListener listener) {
        this.fNotifier.addListener(listener);
    }

    public void removeListener(RunListener listener) {
        this.fNotifier.removeListener(listener);
    }

    static Computer defaultComputer() {
        return new Computer();
    }
}
