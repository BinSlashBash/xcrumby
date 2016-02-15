/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.helper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class DescendableLinkedList<E>
extends LinkedList<E> {
    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingIterator(this.size());
    }

    @Override
    public E peekLast() {
        if (this.size() == 0) {
            return null;
        }
        return this.getLast();
    }

    @Override
    public E pollLast() {
        if (this.size() == 0) {
            return null;
        }
        return this.removeLast();
    }

    @Override
    public void push(E e2) {
        this.addFirst(e2);
    }

    private class DescendingIterator<E>
    implements Iterator<E> {
        private final ListIterator<E> iter;

        private DescendingIterator(int n2) {
            this.iter = DescendableLinkedList.this.listIterator(n2);
        }

        @Override
        public boolean hasNext() {
            return this.iter.hasPrevious();
        }

        @Override
        public E next() {
            return this.iter.previous();
        }

        @Override
        public void remove() {
            this.iter.remove();
        }
    }

}

