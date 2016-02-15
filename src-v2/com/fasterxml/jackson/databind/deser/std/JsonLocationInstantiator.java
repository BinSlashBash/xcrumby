/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;

public class JsonLocationInstantiator
extends ValueInstantiator {
    private static final int _int(Object object) {
        if (object == null) {
            return 0;
        }
        return ((Number)object).intValue();
    }

    private static final long _long(Object object) {
        if (object == null) {
            return 0;
        }
        return ((Number)object).longValue();
    }

    private static CreatorProperty creatorProp(String string2, JavaType javaType, int n2) {
        return new CreatorProperty(new PropertyName(string2), javaType, null, null, null, null, n2, (Object)null, PropertyMetadata.STD_REQUIRED);
    }

    @Override
    public boolean canCreateFromObjectWith() {
        return true;
    }

    @Override
    public Object createFromObjectWith(DeserializationContext deserializationContext, Object[] arrobject) {
        return new JsonLocation(arrobject[0], JsonLocationInstantiator._long(arrobject[1]), JsonLocationInstantiator._long(arrobject[2]), JsonLocationInstantiator._int(arrobject[3]), JsonLocationInstantiator._int(arrobject[4]));
    }

    public CreatorProperty[] getFromObjectArguments(DeserializationConfig deserializationConfig) {
        JavaType javaType = deserializationConfig.constructType(Integer.TYPE);
        JavaType javaType2 = deserializationConfig.constructType(Long.TYPE);
        return new CreatorProperty[]{JsonLocationInstantiator.creatorProp("sourceRef", deserializationConfig.constructType(Object.class), 0), JsonLocationInstantiator.creatorProp("byteOffset", javaType2, 1), JsonLocationInstantiator.creatorProp("charOffset", javaType2, 2), JsonLocationInstantiator.creatorProp("lineNr", javaType, 3), JsonLocationInstantiator.creatorProp("columnNr", javaType, 4)};
    }

    @Override
    public String getValueTypeDesc() {
        return JsonLocation.class.getName();
    }
}

