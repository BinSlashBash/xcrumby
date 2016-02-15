/*
 * Decompiled with CFR 0_110.
 */
package org.junit.rules;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public interface MethodRule {
    public Statement apply(Statement var1, FrameworkMethod var2, Object var3);
}

