/*
 * Decompiled with CFR 0_110.
 */
package android.support.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.ANNOTATION_TYPE})
public @interface IntDef {
    public boolean flag() default 0;

    public long[] value() default {};
}

