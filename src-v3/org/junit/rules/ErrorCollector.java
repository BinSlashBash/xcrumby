package org.junit.rules;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.runners.model.MultipleFailureException;

public class ErrorCollector extends Verifier {
    private List<Throwable> errors;

    /* renamed from: org.junit.rules.ErrorCollector.1 */
    class C07031 implements Callable<Object> {
        final /* synthetic */ Matcher val$matcher;
        final /* synthetic */ String val$reason;
        final /* synthetic */ Object val$value;

        C07031(String str, Object obj, Matcher matcher) {
            this.val$reason = str;
            this.val$value = obj;
            this.val$matcher = matcher;
        }

        public Object call() throws Exception {
            Assert.assertThat(this.val$reason, this.val$value, this.val$matcher);
            return this.val$value;
        }
    }

    public ErrorCollector() {
        this.errors = new ArrayList();
    }

    protected void verify() throws Throwable {
        MultipleFailureException.assertEmpty(this.errors);
    }

    public void addError(Throwable error) {
        this.errors.add(error);
    }

    public <T> void checkThat(T value, Matcher<T> matcher) {
        checkThat(UnsupportedUrlFragment.DISPLAY_NAME, value, matcher);
    }

    public <T> void checkThat(String reason, T value, Matcher<T> matcher) {
        checkSucceeds(new C07031(reason, value, matcher));
    }

    public Object checkSucceeds(Callable<Object> callable) {
        try {
            return callable.call();
        } catch (Throwable e) {
            addError(e);
            return null;
        }
    }
}
