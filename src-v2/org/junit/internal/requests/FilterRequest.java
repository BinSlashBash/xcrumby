/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.requests;

import org.junit.internal.runners.ErrorReportingRunner;
import org.junit.runner.Request;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;

public final class FilterRequest
extends Request {
    private final Filter fFilter;
    private final Request fRequest;

    public FilterRequest(Request request, Filter filter) {
        this.fRequest = request;
        this.fFilter = filter;
    }

    @Override
    public Runner getRunner() {
        try {
            Runner runner = this.fRequest.getRunner();
            this.fFilter.apply(runner);
            return runner;
        }
        catch (NoTestsRemainException var1_2) {
            return new ErrorReportingRunner(Filter.class, new Exception(String.format("No tests found matching %s from %s", this.fFilter.describe(), this.fRequest.toString())));
        }
    }
}

