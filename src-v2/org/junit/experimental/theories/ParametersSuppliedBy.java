/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.theories;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.junit.experimental.theories.ParameterSupplier;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface ParametersSuppliedBy {
    public Class<? extends ParameterSupplier> value();
}

