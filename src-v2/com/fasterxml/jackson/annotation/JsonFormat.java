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
import java.util.Locale;
import java.util.TimeZone;

@JacksonAnnotation
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
public @interface JsonFormat {
    public static final String DEFAULT_LOCALE = "##default";
    public static final String DEFAULT_TIMEZONE = "##default";

    public String locale() default "##default";

    public String pattern() default "";

    public Shape shape() default Shape.ANY;

    public String timezone() default "##default";

    public static enum Shape {
        ANY,
        SCALAR,
        ARRAY,
        OBJECT,
        NUMBER,
        NUMBER_FLOAT,
        NUMBER_INT,
        STRING,
        BOOLEAN;
        

        private Shape() {
        }

        public boolean isNumeric() {
            if (this == NUMBER || this == NUMBER_INT || this == NUMBER_FLOAT) {
                return true;
            }
            return false;
        }

        public boolean isStructured() {
            if (this == OBJECT || this == ARRAY) {
                return true;
            }
            return false;
        }
    }

    public static class Value {
        private TimeZone _timezone;
        private final Locale locale;
        private final String pattern;
        private final Shape shape;
        private final String timezoneStr;

        public Value() {
            this("", Shape.ANY, "", "");
        }

        public Value(JsonFormat jsonFormat) {
            this(jsonFormat.pattern(), jsonFormat.shape(), jsonFormat.locale(), jsonFormat.timezone());
        }

        /*
         * Enabled aggressive block sorting
         */
        public Value(String string2, Shape shape, String object, String string3) {
            object = object == null || object.length() == 0 || "##default".equals(object) ? null : new Locale((String)object);
            if (string3 == null || string3.length() == 0 || "##default".equals(string3)) {
                string3 = null;
            }
            this(string2, shape, (Locale)object, string3, null);
        }

        public Value(String string2, Shape shape, Locale locale, String string3, TimeZone timeZone) {
            this.pattern = string2;
            this.shape = shape;
            this.locale = locale;
            this._timezone = timeZone;
            this.timezoneStr = string3;
        }

        public Value(String string2, Shape shape, Locale locale, TimeZone timeZone) {
            this.pattern = string2;
            this.shape = shape;
            this.locale = locale;
            this._timezone = timeZone;
            this.timezoneStr = null;
        }

        public Locale getLocale() {
            return this.locale;
        }

        public String getPattern() {
            return this.pattern;
        }

        public Shape getShape() {
            return this.shape;
        }

        public TimeZone getTimeZone() {
            TimeZone timeZone;
            TimeZone timeZone2 = timeZone = this._timezone;
            if (timeZone == null) {
                if (this.timezoneStr == null) {
                    return null;
                }
                this._timezone = timeZone2 = TimeZone.getTimeZone(this.timezoneStr);
            }
            return timeZone2;
        }

        public boolean hasLocale() {
            if (this.locale != null) {
                return true;
            }
            return false;
        }

        public boolean hasPattern() {
            if (this.pattern != null && this.pattern.length() > 0) {
                return true;
            }
            return false;
        }

        public boolean hasShape() {
            if (this.shape != Shape.ANY) {
                return true;
            }
            return false;
        }

        public boolean hasTimeZone() {
            if (this._timezone != null || this.timezoneStr != null && !this.timezoneStr.isEmpty()) {
                return true;
            }
            return false;
        }

        public String timeZoneAsString() {
            if (this._timezone != null) {
                return this._timezone.getID();
            }
            return this.timezoneStr;
        }

        public Value withLocale(Locale locale) {
            return new Value(this.pattern, this.shape, locale, this.timezoneStr, this._timezone);
        }

        public Value withPattern(String string2) {
            return new Value(string2, this.shape, this.locale, this.timezoneStr, this._timezone);
        }

        public Value withShape(Shape shape) {
            return new Value(this.pattern, shape, this.locale, this.timezoneStr, this._timezone);
        }

        public Value withTimeZone(TimeZone timeZone) {
            return new Value(this.pattern, this.shape, this.locale, null, timeZone);
        }
    }

}

