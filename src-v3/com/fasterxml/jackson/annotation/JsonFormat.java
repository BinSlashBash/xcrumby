package com.fasterxml.jackson.annotation;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Locale;
import java.util.TimeZone;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
@JacksonAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonFormat {
    public static final String DEFAULT_LOCALE = "##default";
    public static final String DEFAULT_TIMEZONE = "##default";

    public enum Shape {
        ANY,
        SCALAR,
        ARRAY,
        OBJECT,
        NUMBER,
        NUMBER_FLOAT,
        NUMBER_INT,
        STRING,
        BOOLEAN;

        public boolean isNumeric() {
            return this == NUMBER || this == NUMBER_INT || this == NUMBER_FLOAT;
        }

        public boolean isStructured() {
            return this == OBJECT || this == ARRAY;
        }
    }

    public static class Value {
        private TimeZone _timezone;
        private final Locale locale;
        private final String pattern;
        private final Shape shape;
        private final String timezoneStr;

        public Value() {
            this(UnsupportedUrlFragment.DISPLAY_NAME, Shape.ANY, UnsupportedUrlFragment.DISPLAY_NAME, UnsupportedUrlFragment.DISPLAY_NAME);
        }

        public Value(JsonFormat ann) {
            this(ann.pattern(), ann.shape(), ann.locale(), ann.timezone());
        }

        public Value(String p, Shape sh, String localeStr, String tzStr) {
            Locale locale = (localeStr == null || localeStr.length() == 0 || JsonFormat.DEFAULT_TIMEZONE.equals(localeStr)) ? null : new Locale(localeStr);
            String str = (tzStr == null || tzStr.length() == 0 || JsonFormat.DEFAULT_TIMEZONE.equals(tzStr)) ? null : tzStr;
            this(p, sh, locale, str, null);
        }

        public Value(String p, Shape sh, Locale l, TimeZone tz) {
            this.pattern = p;
            this.shape = sh;
            this.locale = l;
            this._timezone = tz;
            this.timezoneStr = null;
        }

        public Value(String p, Shape sh, Locale l, String tzStr, TimeZone tz) {
            this.pattern = p;
            this.shape = sh;
            this.locale = l;
            this._timezone = tz;
            this.timezoneStr = tzStr;
        }

        public Value withPattern(String p) {
            return new Value(p, this.shape, this.locale, this.timezoneStr, this._timezone);
        }

        public Value withShape(Shape s) {
            return new Value(this.pattern, s, this.locale, this.timezoneStr, this._timezone);
        }

        public Value withLocale(Locale l) {
            return new Value(this.pattern, this.shape, l, this.timezoneStr, this._timezone);
        }

        public Value withTimeZone(TimeZone tz) {
            return new Value(this.pattern, this.shape, this.locale, null, tz);
        }

        public String getPattern() {
            return this.pattern;
        }

        public Shape getShape() {
            return this.shape;
        }

        public Locale getLocale() {
            return this.locale;
        }

        public String timeZoneAsString() {
            if (this._timezone != null) {
                return this._timezone.getID();
            }
            return this.timezoneStr;
        }

        public TimeZone getTimeZone() {
            TimeZone tz = this._timezone;
            if (tz == null) {
                if (this.timezoneStr == null) {
                    return null;
                }
                tz = TimeZone.getTimeZone(this.timezoneStr);
                this._timezone = tz;
            }
            return tz;
        }

        public boolean hasShape() {
            return this.shape != Shape.ANY;
        }

        public boolean hasPattern() {
            return this.pattern != null && this.pattern.length() > 0;
        }

        public boolean hasLocale() {
            return this.locale != null;
        }

        public boolean hasTimeZone() {
            return (this._timezone == null && (this.timezoneStr == null || this.timezoneStr.isEmpty())) ? false : true;
        }
    }

    String locale() default "##default";

    String pattern() default "";

    Shape shape() default Shape.ANY;

    String timezone() default "##default";
}
