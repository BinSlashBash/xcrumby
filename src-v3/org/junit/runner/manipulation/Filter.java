package org.junit.runner.manipulation;

import java.util.Iterator;
import org.junit.runner.Description;

public abstract class Filter {
    public static Filter ALL;

    /* renamed from: org.junit.runner.manipulation.Filter.1 */
    static class C12581 extends Filter {
        C12581() {
        }

        public boolean shouldRun(Description description) {
            return true;
        }

        public String describe() {
            return "all tests";
        }

        public void apply(Object child) throws NoTestsRemainException {
        }

        public Filter intersect(Filter second) {
            return second;
        }
    }

    /* renamed from: org.junit.runner.manipulation.Filter.2 */
    static class C12592 extends Filter {
        final /* synthetic */ Description val$desiredDescription;

        C12592(Description description) {
            this.val$desiredDescription = description;
        }

        public boolean shouldRun(Description description) {
            if (description.isTest()) {
                return this.val$desiredDescription.equals(description);
            }
            Iterator i$ = description.getChildren().iterator();
            while (i$.hasNext()) {
                if (shouldRun((Description) i$.next())) {
                    return true;
                }
            }
            return false;
        }

        public String describe() {
            return String.format("Method %s", new Object[]{this.val$desiredDescription.getDisplayName()});
        }
    }

    /* renamed from: org.junit.runner.manipulation.Filter.3 */
    class C12603 extends Filter {
        final /* synthetic */ Filter val$first;
        final /* synthetic */ Filter val$second;

        C12603(Filter filter, Filter filter2) {
            this.val$first = filter;
            this.val$second = filter2;
        }

        public boolean shouldRun(Description description) {
            return this.val$first.shouldRun(description) && this.val$second.shouldRun(description);
        }

        public String describe() {
            return this.val$first.describe() + " and " + this.val$second.describe();
        }
    }

    public abstract String describe();

    public abstract boolean shouldRun(Description description);

    static {
        ALL = new C12581();
    }

    public static Filter matchMethodDescription(Description desiredDescription) {
        return new C12592(desiredDescription);
    }

    public void apply(Object child) throws NoTestsRemainException {
        if (child instanceof Filterable) {
            ((Filterable) child).filter(this);
        }
    }

    public Filter intersect(Filter second) {
        return (second == this || second == ALL) ? this : new C12603(this, second);
    }
}
