package org.junit.runner.notification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.Result;

public class RunNotifier {
    private final List<RunListener> fListeners;
    private volatile boolean fPleaseStop;

    private abstract class SafeNotifier {
        private final List<RunListener> fCurrentListeners;

        protected abstract void notifyListener(RunListener runListener) throws Exception;

        SafeNotifier(RunNotifier runNotifier) {
            this(runNotifier.fListeners);
        }

        SafeNotifier(List<RunListener> currentListeners) {
            this.fCurrentListeners = currentListeners;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        void run() {
            /*
            r8 = this;
            r5 = org.junit.runner.notification.RunNotifier.this;
            r6 = r5.fListeners;
            monitor-enter(r6);
            r4 = new java.util.ArrayList;	 Catch:{ all -> 0x0036 }
            r4.<init>();	 Catch:{ all -> 0x0036 }
            r2 = new java.util.ArrayList;	 Catch:{ all -> 0x0036 }
            r2.<init>();	 Catch:{ all -> 0x0036 }
            r5 = r8.fCurrentListeners;	 Catch:{ all -> 0x0036 }
            r0 = r5.iterator();	 Catch:{ all -> 0x0036 }
        L_0x0017:
            r5 = r0.hasNext();	 Catch:{ all -> 0x0036 }
            if (r5 == 0) goto L_0x0039;
        L_0x001d:
            r3 = r0.next();	 Catch:{ Exception -> 0x002a }
            r3 = (org.junit.runner.notification.RunListener) r3;	 Catch:{ Exception -> 0x002a }
            r8.notifyListener(r3);	 Catch:{ Exception -> 0x002a }
            r4.add(r3);	 Catch:{ Exception -> 0x002a }
            goto L_0x0017;
        L_0x002a:
            r1 = move-exception;
            r5 = new org.junit.runner.notification.Failure;	 Catch:{ all -> 0x0036 }
            r7 = org.junit.runner.Description.TEST_MECHANISM;	 Catch:{ all -> 0x0036 }
            r5.<init>(r7, r1);	 Catch:{ all -> 0x0036 }
            r2.add(r5);	 Catch:{ all -> 0x0036 }
            goto L_0x0017;
        L_0x0036:
            r5 = move-exception;
            monitor-exit(r6);	 Catch:{ all -> 0x0036 }
            throw r5;
        L_0x0039:
            r5 = org.junit.runner.notification.RunNotifier.this;	 Catch:{ all -> 0x0036 }
            r5.fireTestFailures(r4, r2);	 Catch:{ all -> 0x0036 }
            monitor-exit(r6);	 Catch:{ all -> 0x0036 }
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.junit.runner.notification.RunNotifier.SafeNotifier.run():void");
        }
    }

    /* renamed from: org.junit.runner.notification.RunNotifier.1 */
    class C12611 extends SafeNotifier {
        final /* synthetic */ Description val$description;

        C12611(Description description) {
            this.val$description = description;
            super(RunNotifier.this);
        }

        protected void notifyListener(RunListener each) throws Exception {
            each.testRunStarted(this.val$description);
        }
    }

    /* renamed from: org.junit.runner.notification.RunNotifier.2 */
    class C12622 extends SafeNotifier {
        final /* synthetic */ Result val$result;

        C12622(Result result) {
            this.val$result = result;
            super(RunNotifier.this);
        }

        protected void notifyListener(RunListener each) throws Exception {
            each.testRunFinished(this.val$result);
        }
    }

    /* renamed from: org.junit.runner.notification.RunNotifier.3 */
    class C12633 extends SafeNotifier {
        final /* synthetic */ Description val$description;

        C12633(Description description) {
            this.val$description = description;
            super(RunNotifier.this);
        }

        protected void notifyListener(RunListener each) throws Exception {
            each.testStarted(this.val$description);
        }
    }

    /* renamed from: org.junit.runner.notification.RunNotifier.4 */
    class C12644 extends SafeNotifier {
        final /* synthetic */ List val$failures;

        C12644(List x0, List list) {
            this.val$failures = list;
            super(x0);
        }

        protected void notifyListener(RunListener listener) throws Exception {
            for (Failure each : this.val$failures) {
                listener.testFailure(each);
            }
        }
    }

    /* renamed from: org.junit.runner.notification.RunNotifier.5 */
    class C12655 extends SafeNotifier {
        final /* synthetic */ Failure val$failure;

        C12655(Failure failure) {
            this.val$failure = failure;
            super(RunNotifier.this);
        }

        protected void notifyListener(RunListener each) throws Exception {
            each.testAssumptionFailure(this.val$failure);
        }
    }

    /* renamed from: org.junit.runner.notification.RunNotifier.6 */
    class C12666 extends SafeNotifier {
        final /* synthetic */ Description val$description;

        C12666(Description description) {
            this.val$description = description;
            super(RunNotifier.this);
        }

        protected void notifyListener(RunListener each) throws Exception {
            each.testIgnored(this.val$description);
        }
    }

    /* renamed from: org.junit.runner.notification.RunNotifier.7 */
    class C12677 extends SafeNotifier {
        final /* synthetic */ Description val$description;

        C12677(Description description) {
            this.val$description = description;
            super(RunNotifier.this);
        }

        protected void notifyListener(RunListener each) throws Exception {
            each.testFinished(this.val$description);
        }
    }

    public RunNotifier() {
        this.fListeners = Collections.synchronizedList(new ArrayList());
        this.fPleaseStop = false;
    }

    public void addListener(RunListener listener) {
        this.fListeners.add(listener);
    }

    public void removeListener(RunListener listener) {
        this.fListeners.remove(listener);
    }

    public void fireTestRunStarted(Description description) {
        new C12611(description).run();
    }

    public void fireTestRunFinished(Result result) {
        new C12622(result).run();
    }

    public void fireTestStarted(Description description) throws StoppedByUserException {
        if (this.fPleaseStop) {
            throw new StoppedByUserException();
        }
        new C12633(description).run();
    }

    public void fireTestFailure(Failure failure) {
        fireTestFailures(this.fListeners, Arrays.asList(new Failure[]{failure}));
    }

    private void fireTestFailures(List<RunListener> listeners, List<Failure> failures) {
        if (!failures.isEmpty()) {
            new C12644(listeners, failures).run();
        }
    }

    public void fireTestAssumptionFailed(Failure failure) {
        new C12655(failure).run();
    }

    public void fireTestIgnored(Description description) {
        new C12666(description).run();
    }

    public void fireTestFinished(Description description) {
        new C12677(description).run();
    }

    public void pleaseStop() {
        this.fPleaseStop = true;
    }

    public void addFirstListener(RunListener listener) {
        this.fListeners.add(0, listener);
    }
}
