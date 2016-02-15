/*
 * Decompiled with CFR 0_110.
 */
package org.junit.rules;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class TestName
extends TestWatcher {
    private String fName;

    public String getMethodName() {
        return this.fName;
    }

    @Override
    protected void starting(Description description) {
        this.fName = description.getMethodName();
    }
}

