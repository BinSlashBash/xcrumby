/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.categories;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface Category {
    public Class<?>[] value();
}

