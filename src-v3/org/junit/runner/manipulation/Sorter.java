package org.junit.runner.manipulation;

import java.util.Comparator;
import org.junit.runner.Description;

public class Sorter implements Comparator<Description> {
    public static Sorter NULL;
    private final Comparator<Description> fComparator;

    /* renamed from: org.junit.runner.manipulation.Sorter.1 */
    static class C07051 implements Comparator<Description> {
        C07051() {
        }

        public int compare(Description o1, Description o2) {
            return 0;
        }
    }

    static {
        NULL = new Sorter(new C07051());
    }

    public Sorter(Comparator<Description> comparator) {
        this.fComparator = comparator;
    }

    public void apply(Object object) {
        if (object instanceof Sortable) {
            ((Sortable) object).sort(this);
        }
    }

    public int compare(Description o1, Description o2) {
        return this.fComparator.compare(o1, o2);
    }
}
