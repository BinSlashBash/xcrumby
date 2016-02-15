/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ISO8601DateFormat
extends DateFormat {
    private static Calendar CALENDAR = new GregorianCalendar();
    private static NumberFormat NUMBER_FORMAT = new DecimalFormat();
    private static final long serialVersionUID = 1;

    public ISO8601DateFormat() {
        this.numberFormat = NUMBER_FORMAT;
        this.calendar = CALENDAR;
    }

    @Override
    public Object clone() {
        return this;
    }

    @Override
    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        stringBuffer.append(ISO8601Utils.format(date));
        return stringBuffer;
    }

    @Override
    public Date parse(String string2) throws ParseException {
        return ISO8601Utils.parse(string2, new ParsePosition(0));
    }

    @Override
    public Date parse(String object, ParsePosition parsePosition) {
        try {
            object = ISO8601Utils.parse((String)object, parsePosition);
            return object;
        }
        catch (ParseException var1_2) {
            return null;
        }
    }

    public String toString() {
        return this.getClass().getName();
    }
}

