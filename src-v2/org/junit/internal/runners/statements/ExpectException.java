/*
 * Decompiled with CFR 0_110.
 */
package org.junit.internal.runners.statements;

import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.model.Statement;

public class ExpectException
extends Statement {
    private final Class<? extends Throwable> fExpected;
    private Statement fNext;

    public ExpectException(Statement statement, Class<? extends Throwable> class_) {
        this.fNext = statement;
        this.fExpected = class_;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void evaluate() throws Exception {
        boolean bl2;
        block4 : {
            bl2 = false;
            try {
                this.fNext.evaluate();
                bl2 = true;
            }
            catch (AssumptionViolatedException var2_2) {
                throw var2_2;
            }
            catch (Throwable var2_3) {
                if (this.fExpected.isAssignableFrom(var2_3.getClass())) break block4;
                throw new Exception("Unexpected exception, expected<" + this.fExpected.getName() + "> but was<" + var2_3.getClass().getName() + ">", var2_3);
            }
        }
        if (bl2) {
            throw new AssertionError((Object)("Expected exception: " + this.fExpected.getName()));
        }
    }
}

