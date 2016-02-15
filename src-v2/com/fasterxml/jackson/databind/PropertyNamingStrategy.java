/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import java.io.Serializable;

public abstract class PropertyNamingStrategy
implements Serializable {
    public static final PropertyNamingStrategy CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES = new LowerCaseWithUnderscoresStrategy();
    public static final PropertyNamingStrategy LOWER_CASE;
    public static final PropertyNamingStrategy PASCAL_CASE_TO_CAMEL_CASE;

    static {
        PASCAL_CASE_TO_CAMEL_CASE = new PascalCaseStrategy();
        LOWER_CASE = new LowerCaseStrategy();
    }

    public String nameForConstructorParameter(MapperConfig<?> mapperConfig, AnnotatedParameter annotatedParameter, String string2) {
        return string2;
    }

    public String nameForField(MapperConfig<?> mapperConfig, AnnotatedField annotatedField, String string2) {
        return string2;
    }

    public String nameForGetterMethod(MapperConfig<?> mapperConfig, AnnotatedMethod annotatedMethod, String string2) {
        return string2;
    }

    public String nameForSetterMethod(MapperConfig<?> mapperConfig, AnnotatedMethod annotatedMethod, String string2) {
        return string2;
    }

    public static class LowerCaseStrategy
    extends PropertyNamingStrategyBase {
        @Override
        public String translate(String string2) {
            return string2.toLowerCase();
        }
    }

    public static class LowerCaseWithUnderscoresStrategy
    extends PropertyNamingStrategyBase {
        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public String translate(String var1_1) {
            if (var1_1 == null) {
                return var1_1;
            }
            var8_2 = var1_1.length();
            var9_3 = new StringBuilder(var8_2 * 2);
            var3_4 = 0;
            var6_5 = 0;
            var5_6 = 0;
            do {
                if (var5_6 >= var8_2) {
                    if (var3_4 <= 0) return var1_1;
                    return var9_3.toString();
                }
                var2_7 = var1_1.charAt(var5_6);
                if (var5_6 > 0) ** GOTO lbl-1000
                var7_9 = var3_4;
                var4_8 = var6_5;
                if (var2_7 != '_') lbl-1000: // 2 sources:
                {
                    if (Character.isUpperCase(var2_7)) {
                        var4_8 = var3_4;
                        if (var6_5 == 0) {
                            var4_8 = var3_4;
                            if (var3_4 > 0) {
                                var4_8 = var3_4;
                                if (var9_3.charAt(var3_4 - 1) != '_') {
                                    var9_3.append('_');
                                    var4_8 = var3_4 + 1;
                                }
                            }
                        }
                        var2_7 = Character.toLowerCase(var2_7);
                        var3_4 = 1;
                    } else {
                        var6_5 = 0;
                        var4_8 = var3_4;
                        var3_4 = var6_5;
                    }
                    var9_3.append(var2_7);
                    var7_9 = var4_8 + 1;
                    var4_8 = var3_4;
                }
                ++var5_6;
                var3_4 = var7_9;
                var6_5 = var4_8;
            } while (true);
        }
    }

    public static class PascalCaseStrategy
    extends PropertyNamingStrategyBase {
        /*
         * Enabled aggressive block sorting
         */
        @Override
        public String translate(String string2) {
            char c2;
            char c3;
            if (string2 == null || string2.length() == 0 || (c2 = string2.charAt(0)) == (c3 = Character.toUpperCase(c2))) {
                return string2;
            }
            StringBuilder stringBuilder = new StringBuilder(string2);
            stringBuilder.setCharAt(0, c3);
            return stringBuilder.toString();
        }
    }

    public static abstract class PropertyNamingStrategyBase
    extends PropertyNamingStrategy {
        @Override
        public String nameForConstructorParameter(MapperConfig<?> mapperConfig, AnnotatedParameter annotatedParameter, String string2) {
            return this.translate(string2);
        }

        @Override
        public String nameForField(MapperConfig<?> mapperConfig, AnnotatedField annotatedField, String string2) {
            return this.translate(string2);
        }

        @Override
        public String nameForGetterMethod(MapperConfig<?> mapperConfig, AnnotatedMethod annotatedMethod, String string2) {
            return this.translate(string2);
        }

        @Override
        public String nameForSetterMethod(MapperConfig<?> mapperConfig, AnnotatedMethod annotatedMethod, String string2) {
            return this.translate(string2);
        }

        public abstract String translate(String var1);
    }

}

