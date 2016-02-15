/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import java.io.Serializable;

public final class SerializerFactoryConfig
implements Serializable {
    protected static final BeanSerializerModifier[] NO_MODIFIERS;
    protected static final Serializers[] NO_SERIALIZERS;
    private static final long serialVersionUID = 1;
    protected final Serializers[] _additionalKeySerializers;
    protected final Serializers[] _additionalSerializers;
    protected final BeanSerializerModifier[] _modifiers;

    static {
        NO_SERIALIZERS = new Serializers[0];
        NO_MODIFIERS = new BeanSerializerModifier[0];
    }

    public SerializerFactoryConfig() {
        this(null, null, null);
    }

    protected SerializerFactoryConfig(Serializers[] arrserializers, Serializers[] arrserializers2, BeanSerializerModifier[] arrbeanSerializerModifier) {
        Serializers[] arrserializers3 = arrserializers;
        if (arrserializers == null) {
            arrserializers3 = NO_SERIALIZERS;
        }
        this._additionalSerializers = arrserializers3;
        arrserializers = arrserializers2;
        if (arrserializers2 == null) {
            arrserializers = NO_SERIALIZERS;
        }
        this._additionalKeySerializers = arrserializers;
        arrserializers = arrbeanSerializerModifier;
        if (arrbeanSerializerModifier == null) {
            arrserializers = NO_MODIFIERS;
        }
        this._modifiers = arrserializers;
    }

    public boolean hasKeySerializers() {
        if (this._additionalKeySerializers.length > 0) {
            return true;
        }
        return false;
    }

    public boolean hasSerializerModifiers() {
        if (this._modifiers.length > 0) {
            return true;
        }
        return false;
    }

    public boolean hasSerializers() {
        if (this._additionalSerializers.length > 0) {
            return true;
        }
        return false;
    }

    public Iterable<Serializers> keySerializers() {
        return new ArrayIterator<Serializers>(this._additionalKeySerializers);
    }

    public Iterable<BeanSerializerModifier> serializerModifiers() {
        return new ArrayIterator<BeanSerializerModifier>(this._modifiers);
    }

    public Iterable<Serializers> serializers() {
        return new ArrayIterator<Serializers>(this._additionalSerializers);
    }

    public SerializerFactoryConfig withAdditionalKeySerializers(Serializers arrserializers) {
        if (arrserializers == null) {
            throw new IllegalArgumentException("Can not pass null Serializers");
        }
        arrserializers = ArrayBuilders.insertInListNoDup(this._additionalKeySerializers, arrserializers);
        return new SerializerFactoryConfig(this._additionalSerializers, arrserializers, this._modifiers);
    }

    public SerializerFactoryConfig withAdditionalSerializers(Serializers serializers) {
        if (serializers == null) {
            throw new IllegalArgumentException("Can not pass null Serializers");
        }
        return new SerializerFactoryConfig(ArrayBuilders.insertInListNoDup(this._additionalSerializers, serializers), this._additionalKeySerializers, this._modifiers);
    }

    public SerializerFactoryConfig withSerializerModifier(BeanSerializerModifier arrbeanSerializerModifier) {
        if (arrbeanSerializerModifier == null) {
            throw new IllegalArgumentException("Can not pass null modifier");
        }
        arrbeanSerializerModifier = ArrayBuilders.insertInListNoDup(this._modifiers, arrbeanSerializerModifier);
        return new SerializerFactoryConfig(this._additionalSerializers, this._additionalKeySerializers, arrbeanSerializerModifier);
    }
}

