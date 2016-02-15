/*
 * Decompiled with CFR 0_110.
 */
package org.junit.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.rules.Verifier;
import org.junit.runners.model.MultipleFailureException;

public class ErrorCollector
extends Verifier {
    private List<Throwable> errors = new ArrayList<Throwable>();

    public void addError(Throwable throwable) {
        this.errors.add(throwable);
    }

    public Object checkSucceeds(Callable<Object> object) {
        try {
            object = object.call();
            return object;
        }
        catch (Throwable var1_2) {
            this.addError(var1_2);
            return null;
        }
    }

    public <T> void checkThat(T t2, Matcher<T> matcher) {
        this.checkThat("", t2, matcher);
    }

    public <T> void checkThat(final String string2, final T t2, final Matcher<T> matcher) {
        this.checkSucceeds(new Callable<Object>(){

            @Override
            public Object call() throws Exception {
                Assert.assertThat(string2, t2, matcher);
                return t2;
            }
        });
    }

    @Override
    protected void verify() throws Throwable {
        MultipleFailureException.assertEmpty(this.errors);
    }

}

