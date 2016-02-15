/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.theories.suppliers;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.suppliers.TestedOnSupplier;

@Retention(value=RetentionPolicy.RUNTIME)
@ParametersSuppliedBy(value=TestedOnSupplier.class)
public @interface TestedOn {
    public int[] ints();
}

