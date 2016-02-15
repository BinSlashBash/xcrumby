/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.theories;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface Theory {
    public boolean nullsAccepted() default 1;
}

