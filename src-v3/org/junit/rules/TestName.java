package org.junit.rules;

import org.junit.runner.Description;

public class TestName extends TestWatcher {
    private String fName;

    protected void starting(Description d) {
        this.fName = d.getMethodName();
    }

    public String getMethodName() {
        return this.fName;
    }
}
