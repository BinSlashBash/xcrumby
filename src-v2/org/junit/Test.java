/*
 * Decompiled with CFR 0_110.
 */
package org.junit;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface Test {
    public Class<? extends Throwable> expected() default None.class;

    public long timeout() default 0;

    public static class None
    extends Throwable {
        private static final long serialVersionUID = 1;

        private None() {
        }
    }

}

