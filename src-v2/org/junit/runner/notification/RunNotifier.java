/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runner.notification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.StoppedByUserException;

public class RunNotifier {
    private final List<RunListener> fListeners = Collections.synchronizedList(new ArrayList());
    private volatile boolean fPleaseStop = false;

    private void fireTestFailures(List<RunListener> list, final List<Failure> list2) {
        if (!list2.isEmpty()) {
            new SafeNotifier(list){

                @Override
                protected void notifyListener(RunListener runListener) throws Exception {
                    Iterator iterator = list2.iterator();
                    while (iterator.hasNext()) {
                        runListener.testFailure((Failure)iterator.next());
                    }
                }
            }.run();
        }
    }

    public void addFirstListener(RunListener runListener) {
        this.fListeners.add(0, runListener);
    }

    public void addListener(RunListener runListener) {
        this.fListeners.add(runListener);
    }

    public void fireTestAssumptionFailed(final Failure failure) {
        new SafeNotifier(){

            @Override
            protected void notifyListener(RunListener runListener) throws Exception {
                runListener.testAssumptionFailure(failure);
            }
        }.run();
    }

    public void fireTestFailure(Failure failure) {
        this.fireTestFailures(this.fListeners, Arrays.asList(failure));
    }

    public void fireTestFinished(final Description description) {
        new SafeNotifier(){

            @Override
            protected void notifyListener(RunListener runListener) throws Exception {
                runListener.testFinished(description);
            }
        }.run();
    }

    public void fireTestIgnored(final Description description) {
        new SafeNotifier(){

            @Override
            protected void notifyListener(RunListener runListener) throws Exception {
                runListener.testIgnored(description);
            }
        }.run();
    }

    public void fireTestRunFinished(final Result result) {
        new SafeNotifier(){

            @Override
            protected void notifyListener(RunListener runListener) throws Exception {
                runListener.testRunFinished(result);
            }
        }.run();
    }

    public void fireTestRunStarted(final Description description) {
        new SafeNotifier(){

            @Override
            protected void notifyListener(RunListener runListener) throws Exception {
                runListener.testRunStarted(description);
            }
        }.run();
    }

    public void fireTestStarted(final Description description) throws StoppedByUserException {
        if (this.fPleaseStop) {
            throw new StoppedByUserException();
        }
        new SafeNotifier(){

            @Override
            protected void notifyListener(RunListener runListener) throws Exception {
                runListener.testStarted(description);
            }
        }.run();
    }

    public void pleaseStop() {
        this.fPleaseStop = true;
    }

    public void removeListener(RunListener runListener) {
        this.fListeners.remove(runListener);
    }

    private abstract class SafeNotifier {
        private final List<RunListener> fCurrentListeners;

        SafeNotifier() {
            this(runNotifier.fListeners);
        }

        SafeNotifier(List<RunListener> list) {
            this.fCurrentListeners = list;
        }

        protected abstract void notifyListener(RunListener var1) throws Exception;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void run() {
            List list = RunNotifier.this.fListeners;
            synchronized (list) {
                ArrayList<RunListener> arrayList = new ArrayList<RunListener>();
                ArrayList<Failure> arrayList2 = new ArrayList<Failure>();
                Iterator<RunListener> iterator = this.fCurrentListeners.iterator();
                do {
                    boolean bl2;
                    if (!(bl2 = iterator.hasNext())) {
                        RunNotifier.this.fireTestFailures(arrayList, arrayList2);
                        return;
                    }
                    try {
                        RunListener runListener = iterator.next();
                        this.notifyListener(runListener);
                        arrayList.add(runListener);
                    }
                    catch (Exception var6_7) {
                        arrayList2.add(new Failure(Description.TEST_MECHANISM, var6_7));
                        continue;
                    }
                    break;
                } while (true);
            }
        }
    }

}

