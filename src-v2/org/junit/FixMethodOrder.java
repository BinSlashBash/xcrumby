/*
 * Decompiled with CFR 0_110.
 */
package org.junit;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.runners.MethodSorters;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface FixMethodOrder {
    public MethodSorters value() default MethodSorters.DEFAULT;
}

