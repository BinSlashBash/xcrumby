/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.results;

import java.util.List;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

class FailureList {
    private final List<Failure> failures;

    public FailureList(List<Failure> list) {
        this.failures = list;
    }

    public Result result() {
        Result result = new Result();
        RunListener runListener = result.createListener();
        for (Failure failure : this.failures) {
            try {
                runListener.testFailure(failure);
                continue;
            }
            catch (Exception var1_2) {
                throw new RuntimeException("I can't believe this happened");
            }
        }
        return result;
    }
}

