/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;

public class DateDeserializers {
    private static final HashSet<String> _classNames = new HashSet();

    static {
        for (Class class_ : new Class[]{Calendar.class, GregorianCalendar.class, Date.class, java.util.Date.class, Timestamp.class}) {
            _classNames.add(class_.getName());
        }
    }

    public static JsonDeserializer<?> find(Class<?> class_, String string2) {
        if (_classNames.contains(string2)) {
            if (class_ == Calendar.class) {
                return new CalendarDeserializer();
            }
            if (class_ == java.util.Date.class) {
                return DateDeserializer.instance;
            }
            if (class_ == Date.class) {
                return new SqlDateDeserializer();
            }
            if (class_ == Timestamp.class) {
                return new TimestampDeserializer();
            }
            if (class_ == GregorianCalendar.class) {
                return new CalendarDeserializer(GregorianCalendar.class);
            }
        }
        return null;
    }

    @JacksonStdImpl
    public static class CalendarDeserializer
    extends DateBasedDeserializer<Calendar> {
        protected final Class<? extends Calendar> _calendarClass;

        public CalendarDeserializer() {
            super(Calendar.class);
            this._calendarClass = null;
        }

        public CalendarDeserializer(CalendarDeserializer calendarDeserializer, DateFormat dateFormat, String string2) {
            super(calendarDeserializer, dateFormat, string2);
            this._calendarClass = calendarDeserializer._calendarClass;
        }

        public CalendarDeserializer(Class<? extends Calendar> class_) {
            super(class_);
            this._calendarClass = class_;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public Calendar deserialize(JsonParser object, DeserializationContext deserializationContext) throws IOException {
            TimeZone timeZone;
            Calendar calendar;
            if ((object = this._parseDate((JsonParser)object, deserializationContext)) == null) {
                return null;
            }
            if (this._calendarClass == null) {
                return deserializationContext.constructCalendar((java.util.Date)object);
            }
            try {
                calendar = this._calendarClass.newInstance();
                calendar.setTimeInMillis(object.getTime());
                timeZone = deserializationContext.getTimeZone();
                object = calendar;
                if (timeZone == null) return object;
            }
            catch (Exception var1_2) {
                throw deserializationContext.instantiationException(this._calendarClass, var1_2);
            }
            calendar.setTimeZone(timeZone);
            return calendar;
        }

        protected CalendarDeserializer withDateFormat(DateFormat dateFormat, String string2) {
            return new CalendarDeserializer(this, dateFormat, string2);
        }
    }

    protected static abstract class DateBasedDeserializer<T>
    extends StdScalarDeserializer<T>
    implements ContextualDeserializer {
        protected final DateFormat _customFormat;
        protected final String _formatString;

        protected DateBasedDeserializer(DateBasedDeserializer<T> dateBasedDeserializer, DateFormat dateFormat, String string2) {
            super(dateBasedDeserializer._valueClass);
            this._customFormat = dateFormat;
            this._formatString = string2;
        }

        protected DateBasedDeserializer(Class<?> class_) {
            super(class_);
            this._customFormat = null;
            this._formatString = null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected java.util.Date _parseDate(JsonParser object, DeserializationContext object2) throws IOException, JsonProcessingException {
            if (this._customFormat == null) return super._parseDate((JsonParser)object, (DeserializationContext)object2);
            Object object3 = object.getCurrentToken();
            if (object3 == JsonToken.VALUE_STRING) {
                object2 = object.getText().trim();
                if (object2.length() == 0) {
                    return (java.util.Date)this.getEmptyValue();
                }
                object = this._customFormat;
                synchronized (object) {
                    try {
                        return this._customFormat.parse((String)object2);
                    }
                    catch (ParseException var3_4) {
                        throw new IllegalArgumentException("Failed to parse Date value '" + (String)object2 + "' (format: \"" + this._formatString + "\"): " + var3_4.getMessage());
                    }
                }
            }
            if (object3 != JsonToken.START_ARRAY) return super._parseDate((JsonParser)object, (DeserializationContext)object2);
            if (!object2.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) return super._parseDate((JsonParser)object, (DeserializationContext)object2);
            object.nextToken();
            object3 = this._parseDate((JsonParser)object, (DeserializationContext)object2);
            if (object.nextToken() == JsonToken.END_ARRAY) return object3;
            throw object2.wrongTokenException((JsonParser)object, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single 'java.util.Date' value but there was more than a single value in the array");
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty object) throws JsonMappingException {
            void var1_3;
            Object object2;
            void var1_5;
            DateBasedDeserializer<T> dateBasedDeserializer = this;
            if (object2 == null) return dateBasedDeserializer;
            Object object3 = deserializationContext.getAnnotationIntrospector().findFormat(object2.getMember());
            dateBasedDeserializer = this;
            if (object3 == null) return dateBasedDeserializer;
            TimeZone timeZone = object3.getTimeZone();
            if (object3.hasPattern()) {
                dateBasedDeserializer = object3.getPattern();
                object2 = object3.hasLocale() ? object3.getLocale() : deserializationContext.getLocale();
                object3 = new SimpleDateFormat((String)((Object)dateBasedDeserializer), (Locale)object2);
                object2 = timeZone;
                if (timeZone == null) {
                    object2 = deserializationContext.getTimeZone();
                }
                object3.setTimeZone((TimeZone)object2);
                return this.withDateFormat((DateFormat)object3, (String)((Object)dateBasedDeserializer));
            }
            dateBasedDeserializer = this;
            if (timeZone == null) return dateBasedDeserializer;
            object2 = deserializationContext.getConfig().getDateFormat();
            if (object2.getClass() != StdDateFormat.class) {
                DateFormat dateFormat = (DateFormat)object2.clone();
                dateFormat.setTimeZone(timeZone);
                return this.withDateFormat((DateFormat)var1_5, this._formatString);
            }
            if (object3.hasLocale()) {
                Locale locale = object3.getLocale();
            } else {
                Locale locale = deserializationContext.getLocale();
            }
            StdDateFormat stdDateFormat = ((StdDateFormat)object2).withTimeZone(timeZone).withLocale((Locale)var1_3);
            return this.withDateFormat((DateFormat)var1_5, this._formatString);
        }

        protected abstract DateBasedDeserializer<T> withDateFormat(DateFormat var1, String var2);
    }

    public static class DateDeserializer
    extends DateBasedDeserializer<java.util.Date> {
        public static final DateDeserializer instance = new DateDeserializer();

        public DateDeserializer() {
            super(java.util.Date.class);
        }

        public DateDeserializer(DateDeserializer dateDeserializer, DateFormat dateFormat, String string2) {
            super(dateDeserializer, dateFormat, string2);
        }

        @Override
        public java.util.Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return this._parseDate(jsonParser, deserializationContext);
        }

        protected DateDeserializer withDateFormat(DateFormat dateFormat, String string2) {
            return new DateDeserializer(this, dateFormat, string2);
        }
    }

    public static class SqlDateDeserializer
    extends DateBasedDeserializer<Date> {
        public SqlDateDeserializer() {
            super(Date.class);
        }

        public SqlDateDeserializer(SqlDateDeserializer sqlDateDeserializer, DateFormat dateFormat, String string2) {
            super(sqlDateDeserializer, dateFormat, string2);
        }

        @Override
        public Date deserialize(JsonParser object, DeserializationContext deserializationContext) throws IOException {
            if ((object = this._parseDate((JsonParser)object, deserializationContext)) == null) {
                return null;
            }
            return new Date(object.getTime());
        }

        protected SqlDateDeserializer withDateFormat(DateFormat dateFormat, String string2) {
            return new SqlDateDeserializer(this, dateFormat, string2);
        }
    }

    public static class TimestampDeserializer
    extends DateBasedDeserializer<Timestamp> {
        public TimestampDeserializer() {
            super(Timestamp.class);
        }

        public TimestampDeserializer(TimestampDeserializer timestampDeserializer, DateFormat dateFormat, String string2) {
            super(timestampDeserializer, dateFormat, string2);
        }

        @Override
        public Timestamp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new Timestamp(this._parseDate(jsonParser, deserializationContext).getTime());
        }

        protected TimestampDeserializer withDateFormat(DateFormat dateFormat, String string2) {
            return new TimestampDeserializer(this, dateFormat, string2);
        }
    }

}

