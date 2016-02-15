/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson;

import com.google.gson.JsonElement;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.internal.LazilyParsedNumber;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class JsonPrimitive
extends JsonElement {
    private static final Class<?>[] PRIMITIVE_TYPES = new Class[]{Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    private Object value;

    public JsonPrimitive(Boolean bl2) {
        this.setValue(bl2);
    }

    public JsonPrimitive(Character c2) {
        this.setValue(c2);
    }

    public JsonPrimitive(Number number) {
        this.setValue(number);
    }

    JsonPrimitive(Object object) {
        this.setValue(object);
    }

    public JsonPrimitive(String string2) {
        this.setValue(string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean isIntegral(JsonPrimitive object) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (!(object.value instanceof Number)) return bl3;
        object = (Number)object.value;
        if (object instanceof BigInteger) return true;
        if (object instanceof Long) return true;
        if (object instanceof Integer) return true;
        if (object instanceof Short) return true;
        bl3 = bl2;
        if (!(object instanceof Byte)) return bl3;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static boolean isPrimitiveOrString(Object class_) {
        if (class_ instanceof String) {
            return true;
        }
        class_ = class_.getClass();
        Class<?>[] arrclass = PRIMITIVE_TYPES;
        int n2 = arrclass.length;
        int n3 = 0;
        while (n3 < n2) {
            if (arrclass[n3].isAssignableFrom(class_)) return true;
            ++n3;
        }
        return false;
    }

    @Override
    JsonPrimitive deepCopy() {
        return this;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        double d2;
        boolean bl2 = false;
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (JsonPrimitive)object;
        if (this.value == null) {
            if (object.value == null) return true;
            return false;
        }
        if (JsonPrimitive.isIntegral(this) && JsonPrimitive.isIntegral((JsonPrimitive)object)) {
            if (this.getAsNumber().longValue() == object.getAsNumber().longValue()) return true;
            return false;
        }
        if (!(this.value instanceof Number)) return this.value.equals(object.value);
        if (!(object.value instanceof Number)) return this.value.equals(object.value);
        double d3 = this.getAsNumber().doubleValue();
        if (d3 == (d2 = object.getAsNumber().doubleValue())) return true;
        boolean bl3 = bl2;
        if (!Double.isNaN(d3)) return bl3;
        bl3 = bl2;
        if (!Double.isNaN(d2)) return bl3;
        return true;
    }

    @Override
    public BigDecimal getAsBigDecimal() {
        if (this.value instanceof BigDecimal) {
            return (BigDecimal)this.value;
        }
        return new BigDecimal(this.value.toString());
    }

    @Override
    public BigInteger getAsBigInteger() {
        if (this.value instanceof BigInteger) {
            return (BigInteger)this.value;
        }
        return new BigInteger(this.value.toString());
    }

    @Override
    public boolean getAsBoolean() {
        if (this.isBoolean()) {
            return this.getAsBooleanWrapper();
        }
        return Boolean.parseBoolean(this.getAsString());
    }

    @Override
    Boolean getAsBooleanWrapper() {
        return (Boolean)this.value;
    }

    @Override
    public byte getAsByte() {
        if (this.isNumber()) {
            return this.getAsNumber().byteValue();
        }
        return Byte.parseByte(this.getAsString());
    }

    @Override
    public char getAsCharacter() {
        return this.getAsString().charAt(0);
    }

    @Override
    public double getAsDouble() {
        if (this.isNumber()) {
            return this.getAsNumber().doubleValue();
        }
        return Double.parseDouble(this.getAsString());
    }

    @Override
    public float getAsFloat() {
        if (this.isNumber()) {
            return this.getAsNumber().floatValue();
        }
        return Float.parseFloat(this.getAsString());
    }

    @Override
    public int getAsInt() {
        if (this.isNumber()) {
            return this.getAsNumber().intValue();
        }
        return Integer.parseInt(this.getAsString());
    }

    @Override
    public long getAsLong() {
        if (this.isNumber()) {
            return this.getAsNumber().longValue();
        }
        return Long.parseLong(this.getAsString());
    }

    @Override
    public Number getAsNumber() {
        if (this.value instanceof String) {
            return new LazilyParsedNumber((String)this.value);
        }
        return (Number)this.value;
    }

    @Override
    public short getAsShort() {
        if (this.isNumber()) {
            return this.getAsNumber().shortValue();
        }
        return Short.parseShort(this.getAsString());
    }

    @Override
    public String getAsString() {
        if (this.isNumber()) {
            return this.getAsNumber().toString();
        }
        if (this.isBoolean()) {
            return this.getAsBooleanWrapper().toString();
        }
        return (String)this.value;
    }

    public int hashCode() {
        if (this.value == null) {
            return 31;
        }
        if (JsonPrimitive.isIntegral(this)) {
            long l2 = this.getAsNumber().longValue();
            return (int)(l2 >>> 32 ^ l2);
        }
        if (this.value instanceof Number) {
            long l3 = Double.doubleToLongBits(this.getAsNumber().doubleValue());
            return (int)(l3 >>> 32 ^ l3);
        }
        return this.value.hashCode();
    }

    public boolean isBoolean() {
        return this.value instanceof Boolean;
    }

    public boolean isNumber() {
        return this.value instanceof Number;
    }

    public boolean isString() {
        return this.value instanceof String;
    }

    /*
     * Enabled aggressive block sorting
     */
    void setValue(Object object) {
        if (object instanceof Character) {
            this.value = String.valueOf(((Character)object).charValue());
            return;
        }
        boolean bl2 = object instanceof Number || JsonPrimitive.isPrimitiveOrString(object);
        $Gson$Preconditions.checkArgument(bl2);
        this.value = object;
    }
}

