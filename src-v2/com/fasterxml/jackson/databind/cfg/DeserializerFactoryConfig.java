/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializers;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import java.io.Serializable;

public class DeserializerFactoryConfig
implements Serializable {
    protected static final KeyDeserializers[] DEFAULT_KEY_DESERIALIZERS;
    protected static final AbstractTypeResolver[] NO_ABSTRACT_TYPE_RESOLVERS;
    protected static final Deserializers[] NO_DESERIALIZERS;
    protected static final BeanDeserializerModifier[] NO_MODIFIERS;
    protected static final ValueInstantiators[] NO_VALUE_INSTANTIATORS;
    private static final long serialVersionUID = 3683541151102256824L;
    protected final AbstractTypeResolver[] _abstractTypeResolvers;
    protected final Deserializers[] _additionalDeserializers;
    protected final KeyDeserializers[] _additionalKeyDeserializers;
    protected final BeanDeserializerModifier[] _modifiers;
    protected final ValueInstantiators[] _valueInstantiators;

    static {
        NO_DESERIALIZERS = new Deserializers[0];
        NO_MODIFIERS = new BeanDeserializerModifier[0];
        NO_ABSTRACT_TYPE_RESOLVERS = new AbstractTypeResolver[0];
        NO_VALUE_INSTANTIATORS = new ValueInstantiators[0];
        DEFAULT_KEY_DESERIALIZERS = new KeyDeserializers[]{new StdKeyDeserializers()};
    }

    public DeserializerFactoryConfig() {
        this(null, null, null, null, null);
    }

    protected DeserializerFactoryConfig(Deserializers[] arrdeserializers, KeyDeserializers[] arrkeyDeserializers, BeanDeserializerModifier[] arrbeanDeserializerModifier, AbstractTypeResolver[] arrabstractTypeResolver, ValueInstantiators[] arrvalueInstantiators) {
        Deserializers[] arrdeserializers2 = arrdeserializers;
        if (arrdeserializers == null) {
            arrdeserializers2 = NO_DESERIALIZERS;
        }
        this._additionalDeserializers = arrdeserializers2;
        arrdeserializers = arrkeyDeserializers;
        if (arrkeyDeserializers == null) {
            arrdeserializers = DEFAULT_KEY_DESERIALIZERS;
        }
        this._additionalKeyDeserializers = arrdeserializers;
        arrdeserializers = arrbeanDeserializerModifier;
        if (arrbeanDeserializerModifier == null) {
            arrdeserializers = NO_MODIFIERS;
        }
        this._modifiers = arrdeserializers;
        arrdeserializers = arrabstractTypeResolver;
        if (arrabstractTypeResolver == null) {
            arrdeserializers = NO_ABSTRACT_TYPE_RESOLVERS;
        }
        this._abstractTypeResolvers = arrdeserializers;
        arrdeserializers = arrvalueInstantiators;
        if (arrvalueInstantiators == null) {
            arrdeserializers = NO_VALUE_INSTANTIATORS;
        }
        this._valueInstantiators = arrdeserializers;
    }

    public Iterable<AbstractTypeResolver> abstractTypeResolvers() {
        return new ArrayIterator<AbstractTypeResolver>(this._abstractTypeResolvers);
    }

    public Iterable<BeanDeserializerModifier> deserializerModifiers() {
        return new ArrayIterator<BeanDeserializerModifier>(this._modifiers);
    }

    public Iterable<Deserializers> deserializers() {
        return new ArrayIterator<Deserializers>(this._additionalDeserializers);
    }

    public boolean hasAbstractTypeResolvers() {
        if (this._abstractTypeResolvers.length > 0) {
            return true;
        }
        return false;
    }

    public boolean hasDeserializerModifiers() {
        if (this._modifiers.length > 0) {
            return true;
        }
        return false;
    }

    public boolean hasDeserializers() {
        if (this._additionalDeserializers.length > 0) {
            return true;
        }
        return false;
    }

    public boolean hasKeyDeserializers() {
        if (this._additionalKeyDeserializers.length > 0) {
            return true;
        }
        return false;
    }

    public boolean hasValueInstantiators() {
        if (this._valueInstantiators.length > 0) {
            return true;
        }
        return false;
    }

    public Iterable<KeyDeserializers> keyDeserializers() {
        return new ArrayIterator<KeyDeserializers>(this._additionalKeyDeserializers);
    }

    public Iterable<ValueInstantiators> valueInstantiators() {
        return new ArrayIterator<ValueInstantiators>(this._valueInstantiators);
    }

    public DeserializerFactoryConfig withAbstractTypeResolver(AbstractTypeResolver arrabstractTypeResolver) {
        if (arrabstractTypeResolver == null) {
            throw new IllegalArgumentException("Can not pass null resolver");
        }
        arrabstractTypeResolver = ArrayBuilders.insertInListNoDup(this._abstractTypeResolvers, arrabstractTypeResolver);
        return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, this._modifiers, arrabstractTypeResolver, this._valueInstantiators);
    }

    public DeserializerFactoryConfig withAdditionalDeserializers(Deserializers deserializers) {
        if (deserializers == null) {
            throw new IllegalArgumentException("Can not pass null Deserializers");
        }
        return new DeserializerFactoryConfig(ArrayBuilders.insertInListNoDup(this._additionalDeserializers, deserializers), this._additionalKeyDeserializers, this._modifiers, this._abstractTypeResolvers, this._valueInstantiators);
    }

    public DeserializerFactoryConfig withAdditionalKeyDeserializers(KeyDeserializers arrkeyDeserializers) {
        if (arrkeyDeserializers == null) {
            throw new IllegalArgumentException("Can not pass null KeyDeserializers");
        }
        arrkeyDeserializers = ArrayBuilders.insertInListNoDup(this._additionalKeyDeserializers, arrkeyDeserializers);
        return new DeserializerFactoryConfig(this._additionalDeserializers, arrkeyDeserializers, this._modifiers, this._abstractTypeResolvers, this._valueInstantiators);
    }

    public DeserializerFactoryConfig withDeserializerModifier(BeanDeserializerModifier arrbeanDeserializerModifier) {
        if (arrbeanDeserializerModifier == null) {
            throw new IllegalArgumentException("Can not pass null modifier");
        }
        arrbeanDeserializerModifier = ArrayBuilders.insertInListNoDup(this._modifiers, arrbeanDeserializerModifier);
        return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, arrbeanDeserializerModifier, this._abstractTypeResolvers, this._valueInstantiators);
    }

    public DeserializerFactoryConfig withValueInstantiators(ValueInstantiators arrvalueInstantiators) {
        if (arrvalueInstantiators == null) {
            throw new IllegalArgumentException("Can not pass null resolver");
        }
        arrvalueInstantiators = ArrayBuilders.insertInListNoDup(this._valueInstantiators, arrvalueInstantiators);
        return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, this._modifiers, this._abstractTypeResolvers, arrvalueInstantiators);
    }
}

