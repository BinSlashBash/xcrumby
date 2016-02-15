/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runner.manipulation;

import java.util.Comparator;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Sortable;

public class Sorter
implements Comparator<Description> {
    public static Sorter NULL = new Sorter(new Comparator<Description>(){

        @Override
        public int compare(Description description, Description description2) {
            return 0;
        }
    });
    private final Comparator<Description> fComparator;

    public Sorter(Comparator<Description> comparator) {
        this.fComparator = comparator;
    }

    public void apply(Object object) {
        if (object instanceof Sortable) {
            ((Sortable)object).sort(this);
        }
    }

    @Override
    public int compare(Description description, Description description2) {
        return this.fComparator.compare(description, description2);
    }

}

