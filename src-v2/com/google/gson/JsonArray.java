/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class JsonArray
extends JsonElement
implements Iterable<JsonElement> {
    private final List<JsonElement> elements = new ArrayList<JsonElement>();

    public void add(JsonElement jsonElement) {
        JsonElement jsonElement2 = jsonElement;
        if (jsonElement == null) {
            jsonElement2 = JsonNull.INSTANCE;
        }
        this.elements.add(jsonElement2);
    }

    public void addAll(JsonArray jsonArray) {
        this.elements.addAll(jsonArray.elements);
    }

    @Override
    JsonArray deepCopy() {
        JsonArray jsonArray = new JsonArray();
        Iterator<JsonElement> iterator = this.elements.iterator();
        while (iterator.hasNext()) {
            jsonArray.add(iterator.next().deepCopy());
        }
        return jsonArray;
    }

    public boolean equals(Object object) {
        if (object == this || object instanceof JsonArray && ((JsonArray)object).elements.equals(this.elements)) {
            return true;
        }
        return false;
    }

    public JsonElement get(int n2) {
        return this.elements.get(n2);
    }

    @Override
    public BigDecimal getAsBigDecimal() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsBigDecimal();
        }
        throw new IllegalStateException();
    }

    @Override
    public BigInteger getAsBigInteger() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsBigInteger();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean getAsBoolean() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsBoolean();
        }
        throw new IllegalStateException();
    }

    @Override
    public byte getAsByte() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsByte();
        }
        throw new IllegalStateException();
    }

    @Override
    public char getAsCharacter() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsCharacter();
        }
        throw new IllegalStateException();
    }

    @Override
    public double getAsDouble() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsDouble();
        }
        throw new IllegalStateException();
    }

    @Override
    public float getAsFloat() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsFloat();
        }
        throw new IllegalStateException();
    }

    @Override
    public int getAsInt() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsInt();
        }
        throw new IllegalStateException();
    }

    @Override
    public long getAsLong() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsLong();
        }
        throw new IllegalStateException();
    }

    @Override
    public Number getAsNumber() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsNumber();
        }
        throw new IllegalStateException();
    }

    @Override
    public short getAsShort() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsShort();
        }
        throw new IllegalStateException();
    }

    @Override
    public String getAsString() {
        if (this.elements.size() == 1) {
            return this.elements.get(0).getAsString();
        }
        throw new IllegalStateException();
    }

    public int hashCode() {
        return this.elements.hashCode();
    }

    @Override
    public Iterator<JsonElement> iterator() {
        return this.elements.iterator();
    }

    public int size() {
        return this.elements.size();
    }
}

