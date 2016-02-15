/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runner.manipulation;

import java.util.ArrayList;
import java.util.Iterator;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;

public abstract class Filter {
    public static Filter ALL = new Filter(){

        @Override
        public void apply(Object object) throws NoTestsRemainException {
        }

        @Override
        public String describe() {
            return "all tests";
        }

        @Override
        public Filter intersect(Filter filter) {
            return filter;
        }

        @Override
        public boolean shouldRun(Description description) {
            return true;
        }
    };

    public static Filter matchMethodDescription(final Description description) {
        return new Filter(){

            @Override
            public String describe() {
                return String.format("Method %s", description.getDisplayName());
            }

            @Override
            public boolean shouldRun(Description object) {
                if (object.isTest()) {
                    return description.equals(object);
                }
                object = object.getChildren().iterator();
                while (object.hasNext()) {
                    if (!this.shouldRun((Description)object.next())) continue;
                    return true;
                }
                return false;
            }
        };
    }

    public void apply(Object object) throws NoTestsRemainException {
        if (!(object instanceof Filterable)) {
            return;
        }
        ((Filterable)object).filter(this);
    }

    public abstract String describe();

    public Filter intersect(final Filter filter) {
        if (filter == this || filter == ALL) {
            return this;
        }
        return new Filter(){

            @Override
            public String describe() {
                return this.describe() + " and " + filter.describe();
            }

            @Override
            public boolean shouldRun(Description description) {
                if (this.shouldRun(description) && filter.shouldRun(description)) {
                    return true;
                }
                return false;
            }
        };
    }

    public abstract boolean shouldRun(Description var1);

}

