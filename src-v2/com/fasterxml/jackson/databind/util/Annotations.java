/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import java.lang.annotation.Annotation;

public interface Annotations {
    public <A extends Annotation> A get(Class<A> var1);

    public int size();
}

