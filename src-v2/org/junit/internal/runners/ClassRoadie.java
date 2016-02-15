/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.FailedBefore;
import org.junit.internal.runners.TestClass;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

@Deprecated
public class ClassRoadie {
    private Description fDescription;
    private RunNotifier fNotifier;
    private final Runnable fRunnable;
    private TestClass fTestClass;

    public ClassRoadie(RunNotifier runNotifier, TestClass testClass, Description description, Runnable runnable) {
        this.fNotifier = runNotifier;
        this.fTestClass = testClass;
        this.fDescription = description;
        this.fRunnable = runnable;
    }

    private void runAfters() {
        for (Method method : this.fTestClass.getAfters()) {
            try {
                method.invoke(null, new Object[0]);
            }
            catch (InvocationTargetException var2_3) {
                this.addFailure(var2_3.getTargetException());
            }
            catch (Throwable var2_4) {
                this.addFailure(var2_4);
            }
        }
    }

    private void runBefores() throws FailedBefore {
        try {
            Iterator<Method> iterator = this.fTestClass.getBefores().iterator();
            while (iterator.hasNext()) {
                iterator.next().invoke(null, new Object[0]);
            }
        }
        catch (InvocationTargetException var1_2) {
            try {
                throw var1_2.getTargetException();
            }
            catch (AssumptionViolatedException var1_3) {
                throw new FailedBefore();
            }
            catch (Throwable var1_4) {
                this.addFailure(var1_4);
                throw new FailedBefore();
            }
        }
    }

    protected void addFailure(Throwable throwable) {
        this.fNotifier.fireTestFailure(new Failure(this.fDescription, throwable));
    }

    public void runProtected() {
        try {
            this.runBefores();
            this.runUnprotected();
            return;
        }
        catch (FailedBefore var1_1) {
            return;
        }
        finally {
            this.runAfters();
        }
    }

    protected void runUnprotected() {
        this.fRunnable.run();
    }
}

