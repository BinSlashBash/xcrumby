package org.junit.experimental.results;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ResultMatchers {

    /* renamed from: org.junit.experimental.results.ResultMatchers.2 */
    static class C15102 extends BaseMatcher<Object> {
        final /* synthetic */ String val$string;

        C15102(String str) {
            this.val$string = str;
        }

        public boolean matches(Object item) {
            return item.toString().contains(this.val$string) && ResultMatchers.failureCountIs(1).matches(item);
        }

        public void describeTo(Description description) {
            description.appendText("has single failure containing " + this.val$string);
        }
    }

    /* renamed from: org.junit.experimental.results.ResultMatchers.3 */
    static class C15113 extends BaseMatcher<PrintableResult> {
        final /* synthetic */ String val$string;

        C15113(String str) {
            this.val$string = str;
        }

        public boolean matches(Object item) {
            return item.toString().contains(this.val$string);
        }

        public void describeTo(Description description) {
            description.appendText("has failure containing " + this.val$string);
        }
    }

    /* renamed from: org.junit.experimental.results.ResultMatchers.1 */
    static class C15511 extends TypeSafeMatcher<PrintableResult> {
        final /* synthetic */ int val$count;

        C15511(int i) {
            this.val$count = i;
        }

        public void describeTo(Description description) {
            description.appendText("has " + this.val$count + " failures");
        }

        public boolean matchesSafely(PrintableResult item) {
            return item.failureCount() == this.val$count;
        }
    }

    public static Matcher<PrintableResult> isSuccessful() {
        return failureCountIs(0);
    }

    public static Matcher<PrintableResult> failureCountIs(int count) {
        return new C15511(count);
    }

    public static Matcher<Object> hasSingleFailureContaining(String string) {
        return new C15102(string);
    }

    public static Matcher<PrintableResult> hasFailureContaining(String string) {
        return new C15113(string);
    }
}
