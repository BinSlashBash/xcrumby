/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

@JacksonAnnotation
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface JsonAutoDetect {
    public Visibility creatorVisibility() default Visibility.DEFAULT;

    public Visibility fieldVisibility() default Visibility.DEFAULT;

    public Visibility getterVisibility() default Visibility.DEFAULT;

    public Visibility isGetterVisibility() default Visibility.DEFAULT;

    public Visibility setterVisibility() default Visibility.DEFAULT;

    public static enum Visibility {
        ANY,
        NON_PRIVATE,
        PROTECTED_AND_PUBLIC,
        PUBLIC_ONLY,
        NONE,
        DEFAULT;
        

        private Visibility() {
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public boolean isVisible(Member member) {
            boolean bl2;
            boolean bl3 = bl2 = true;
            switch (.$SwitchMap$com$fasterxml$jackson$annotation$JsonAutoDetect$Visibility[this.ordinal()]) {
                default: {
                    bl3 = false;
                }
                case 1: {
                    return bl3;
                }
                case 2: {
                    return false;
                }
                case 3: {
                    bl3 = bl2;
                    if (!Modifier.isPrivate(member.getModifiers())) return bl3;
                    return false;
                }
                case 4: {
                    bl3 = bl2;
                    if (Modifier.isProtected(member.getModifiers())) return bl3;
                }
                case 5: 
            }
            return Modifier.isPublic(member.getModifiers());
        }
    }

}

