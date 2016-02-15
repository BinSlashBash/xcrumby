/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBase;
import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.util.HashSet;

public class ThrowableDeserializer
extends BeanDeserializer {
    protected static final String PROP_NAME_MESSAGE = "message";
    private static final long serialVersionUID = 1;

    public ThrowableDeserializer(BeanDeserializer beanDeserializer) {
        super(beanDeserializer);
        this._vanillaProcessing = false;
    }

    protected ThrowableDeserializer(BeanDeserializer beanDeserializer, NameTransformer nameTransformer) {
        super((BeanDeserializerBase)beanDeserializer, nameTransformer);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Object deserializeFromObject(JsonParser object, DeserializationContext object2) throws IOException {
        int n2;
        if (this._propertyBasedCreator != null) {
            return this._deserializeUsingPropertyBased((JsonParser)object, (DeserializationContext)object2);
        }
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate((DeserializationContext)object2, this._delegateDeserializer.deserialize((JsonParser)object, (DeserializationContext)object2));
        }
        if (this._beanType.isAbstract()) {
            throw JsonMappingException.from((JsonParser)object, "Can not instantiate abstract type " + this._beanType + " (need to add/enable type information?)");
        }
        boolean bl2 = this._valueInstantiator.canCreateFromString();
        boolean bl3 = this._valueInstantiator.canCreateUsingDefault();
        if (!bl2 && !bl3) {
            throw new JsonMappingException("Can not deserialize Throwable of type " + this._beanType + " without having a default contructor, a single-String-arg constructor; or explicit @JsonCreator");
        }
        Object object3 = null;
        Object[] arrobject = null;
        int n3 = 0;
        while (object.getCurrentToken() != JsonToken.END_OBJECT) {
            Object[] arrobject2 = object.getCurrentName();
            Object object4 = this._beanProperties.find((String)arrobject2);
            object.nextToken();
            if (object4 != null) {
                if (object3 != null) {
                    object4.deserializeAndSet((JsonParser)object, (DeserializationContext)object2, object3);
                    n2 = n3;
                    arrobject2 = arrobject;
                } else {
                    arrobject2 = arrobject;
                    if (arrobject == null) {
                        n2 = this._beanProperties.size();
                        arrobject2 = new Object[n2 + n2];
                    }
                    int n4 = n3 + 1;
                    arrobject2[n3] = object4;
                    n2 = n4 + 1;
                    arrobject2[n4] = object4.deserialize((JsonParser)object, (DeserializationContext)object2);
                }
            } else if ("message".equals(arrobject2) && bl2) {
                object4 = this._valueInstantiator.createFromString((DeserializationContext)object2, object.getText());
                arrobject2 = arrobject;
                n2 = n3;
                object3 = object4;
                if (arrobject != null) {
                    for (n2 = 0; n2 < n3; n2 += 2) {
                        ((SettableBeanProperty)arrobject[n2]).set(object4, arrobject[n2 + 1]);
                    }
                    arrobject2 = null;
                    n2 = n3;
                    object3 = object4;
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(arrobject2)) {
                object.skipChildren();
                arrobject2 = arrobject;
                n2 = n3;
            } else if (this._anySetter != null) {
                this._anySetter.deserializeAndSet((JsonParser)object, (DeserializationContext)object2, object3, (String)arrobject2);
                arrobject2 = arrobject;
                n2 = n3;
            } else {
                this.handleUnknownProperty((JsonParser)object, (DeserializationContext)object2, object3, (String)arrobject2);
                arrobject2 = arrobject;
                n2 = n3;
            }
            object.nextToken();
            arrobject = arrobject2;
            n3 = n2;
        }
        object = object3;
        if (object3 != null) return object;
        object2 = bl2 ? this._valueInstantiator.createFromString((DeserializationContext)object2, null) : this._valueInstantiator.createUsingDefault((DeserializationContext)object2);
        object = object2;
        if (arrobject == null) return object;
        n2 = 0;
        do {
            object = object2;
            if (n2 >= n3) return object;
            ((SettableBeanProperty)arrobject[n2]).set(object2, arrobject[n2 + 1]);
            n2 += 2;
        } while (true);
    }

    @Override
    public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer nameTransformer) {
        if (this.getClass() != ThrowableDeserializer.class) {
            return this;
        }
        return new ThrowableDeserializer(this, nameTransformer);
    }
}

