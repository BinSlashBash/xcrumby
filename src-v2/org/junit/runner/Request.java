/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runner;

import java.util.Comparator;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.internal.requests.ClassRequest;
import org.junit.internal.requests.FilterRequest;
import org.junit.internal.requests.SortingRequest;
import org.junit.internal.runners.ErrorReportingRunner;
import org.junit.runner.Computer;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public abstract class Request {
    public static Request aClass(Class<?> class_) {
        return new ClassRequest(class_);
    }

    public static Request classWithoutSuiteMethod(Class<?> class_) {
        return new ClassRequest(class_, false);
    }

    public static /* varargs */ Request classes(Computer object, Class<?> ... arrclass) {
        try {
            object = Request.runner(object.getSuite(new AllDefaultPossibilitiesBuilder(true), arrclass));
            return object;
        }
        catch (InitializationError var0_1) {
            throw new RuntimeException("Bug in saff's brain: Suite constructor, called as above, should always complete");
        }
    }

    public static /* varargs */ Request classes(Class<?> ... arrclass) {
        return Request.classes(JUnitCore.defaultComputer(), arrclass);
    }

    @Deprecated
    public static Request errorReport(Class<?> class_, Throwable throwable) {
        return Request.runner(new ErrorReportingRunner(class_, throwable));
    }

    public static Request method(Class<?> class_, String object) {
        object = Description.createTestDescription(class_, (String)object);
        return Request.aClass(class_).filterWith((Description)object);
    }

    public static Request runner(final Runner runner) {
        return new Request(){

            @Override
            public Runner getRunner() {
                return runner;
            }
        };
    }

    public Request filterWith(Description description) {
        return this.filterWith(Filter.matchMethodDescription(description));
    }

    public Request filterWith(Filter filter) {
        return new FilterRequest(this, filter);
    }

    public abstract Runner getRunner();

    public Request sortWith(Comparator<Description> comparator) {
        return new SortingRequest(this, comparator);
    }

}

