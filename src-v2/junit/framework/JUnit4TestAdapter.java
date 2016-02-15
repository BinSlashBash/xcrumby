/*
 * Decompiled with CFR 0_110.
 */
package junit.framework;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import junit.framework.JUnit4TestAdapterCache;
import junit.framework.Test;
import junit.framework.TestResult;
import org.junit.Ignore;
import org.junit.runner.Describable;
import org.junit.runner.Description;
import org.junit.runner.Request;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.RunNotifier;

public class JUnit4TestAdapter
implements Test,
Filterable,
Sortable,
Describable {
    private final JUnit4TestAdapterCache fCache;
    private final Class<?> fNewTestClass;
    private final Runner fRunner;

    public JUnit4TestAdapter(Class<?> class_) {
        this(class_, JUnit4TestAdapterCache.getDefault());
    }

    public JUnit4TestAdapter(Class<?> class_, JUnit4TestAdapterCache jUnit4TestAdapterCache) {
        this.fCache = jUnit4TestAdapterCache;
        this.fNewTestClass = class_;
        this.fRunner = Request.classWithoutSuiteMethod(class_).getRunner();
    }

    private boolean isIgnored(Description description) {
        if (description.getAnnotation(Ignore.class) != null) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Description removeIgnored(Description description) {
        if (this.isIgnored(description)) {
            return Description.EMPTY;
        }
        Description description2 = description.childlessCopy();
        Iterator<Description> iterator = description.getChildren().iterator();
        do {
            description = description2;
            if (!iterator.hasNext()) return description;
            description = this.removeIgnored(iterator.next());
            if (description.isEmpty()) continue;
            description2.addChild(description);
        } while (true);
    }

    @Override
    public int countTestCases() {
        return this.fRunner.testCount();
    }

    @Override
    public void filter(Filter filter) throws NoTestsRemainException {
        filter.apply(this.fRunner);
    }

    @Override
    public Description getDescription() {
        return this.removeIgnored(this.fRunner.getDescription());
    }

    public Class<?> getTestClass() {
        return this.fNewTestClass;
    }

    public List<Test> getTests() {
        return this.fCache.asTestList(this.getDescription());
    }

    @Override
    public void run(TestResult testResult) {
        this.fRunner.run(this.fCache.getNotifier(testResult, this));
    }

    @Override
    public void sort(Sorter sorter) {
        sorter.apply(this.fRunner);
    }

    public String toString() {
        return this.fNewTestClass.getName();
    }
}

