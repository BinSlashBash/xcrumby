/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public final class EnumValues {
    private final Class<Enum<?>> _enumClass;
    private final EnumMap<?, SerializableString> _values;

    private EnumValues(Class<Enum<?>> class_, Map<Enum<?>, SerializableString> map) {
        this._enumClass = class_;
        this._values = new EnumMap(map);
    }

    public static EnumValues construct(SerializationConfig serializationConfig, Class<Enum<?>> class_) {
        if (serializationConfig.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)) {
            return EnumValues.constructFromToString(serializationConfig, class_);
        }
        return EnumValues.constructFromName(serializationConfig, class_);
    }

    public static EnumValues constructFromName(MapperConfig<?> mapperConfig, Class<Enum<?>> class_) {
        Enum<?>[] arrenum = ClassUtil.findEnumType(class_).getEnumConstants();
        if (arrenum != null) {
            HashMap hashMap = new HashMap();
            for (Enum enum_ : arrenum) {
                hashMap.put(enum_, mapperConfig.compileString(mapperConfig.getAnnotationIntrospector().findEnumValue(enum_)));
            }
            return new EnumValues(class_, hashMap);
        }
        throw new IllegalArgumentException("Can not determine enum constants for Class " + class_.getName());
    }

    public static EnumValues constructFromToString(MapperConfig<?> mapperConfig, Class<Enum<?>> class_) {
        Enum<?>[] arrenum = ClassUtil.findEnumType(class_).getEnumConstants();
        if (arrenum != null) {
            HashMap hashMap = new HashMap();
            for (Enum enum_ : arrenum) {
                hashMap.put(enum_, mapperConfig.compileString(enum_.toString()));
            }
            return new EnumValues(class_, hashMap);
        }
        throw new IllegalArgumentException("Can not determine enum constants for Class " + class_.getName());
    }

    public Class<Enum<?>> getEnumClass() {
        return this._enumClass;
    }

    public EnumMap<?, SerializableString> internalMap() {
        return this._values;
    }

    public SerializableString serializedValueFor(Enum<?> enum_) {
        return this._values.get(enum_);
    }

    public Collection<SerializableString> values() {
        return this._values.values();
    }
}

