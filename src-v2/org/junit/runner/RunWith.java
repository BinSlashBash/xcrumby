/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runner;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.runner.Runner;

@Inherited
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface RunWith {
    public Class<? extends Runner> value();
}

