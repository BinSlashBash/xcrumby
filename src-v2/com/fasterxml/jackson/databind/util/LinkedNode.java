/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

public final class LinkedNode<T> {
    private LinkedNode<T> next;
    private final T value;

    public LinkedNode(T t2, LinkedNode<T> linkedNode) {
        this.value = t2;
        this.next = linkedNode;
    }

    public static <ST> boolean contains(LinkedNode<ST> linkedNode, ST ST) {
        while (linkedNode != null) {
            if (linkedNode.value() == ST) {
                return true;
            }
            linkedNode = linkedNode.next();
        }
        return false;
    }

    public void linkNext(LinkedNode<T> linkedNode) {
        if (this.next != null) {
            throw new IllegalStateException();
        }
        this.next = linkedNode;
    }

    public LinkedNode<T> next() {
        return this.next;
    }

    public T value() {
        return this.value;
    }
}

