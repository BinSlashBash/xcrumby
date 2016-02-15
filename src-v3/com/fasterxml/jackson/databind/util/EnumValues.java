package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public final class EnumValues {
    private final Class<Enum<?>> _enumClass;
    private final EnumMap<?, SerializableString> _values;

    private EnumValues(Class<Enum<?>> enumClass, Map<Enum<?>, SerializableString> v) {
        this._enumClass = enumClass;
        this._values = new EnumMap(v);
    }

    public static EnumValues construct(SerializationConfig config, Class<Enum<?>> enumClass) {
        if (config.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)) {
            return constructFromToString(config, enumClass);
        }
        return constructFromName(config, enumClass);
    }

    public static EnumValues constructFromName(MapperConfig<?> config, Class<Enum<?>> enumClass) {
        Enum[] values = (Enum[]) ClassUtil.findEnumType((Class) enumClass).getEnumConstants();
        if (values != null) {
            Map<Enum<?>, SerializableString> map = new HashMap();
            for (Enum<?> en : values) {
                map.put(en, config.compileString(config.getAnnotationIntrospector().findEnumValue(en)));
            }
            return new EnumValues(enumClass, map);
        }
        throw new IllegalArgumentException("Can not determine enum constants for Class " + enumClass.getName());
    }

    public static EnumValues constructFromToString(MapperConfig<?> config, Class<Enum<?>> enumClass) {
        Enum[] values = (Enum[]) ClassUtil.findEnumType((Class) enumClass).getEnumConstants();
        if (values != null) {
            Map<Enum<?>, SerializableString> map = new HashMap();
            for (Enum<?> en : values) {
                map.put(en, config.compileString(en.toString()));
            }
            return new EnumValues(enumClass, map);
        }
        throw new IllegalArgumentException("Can not determine enum constants for Class " + enumClass.getName());
    }

    public SerializableString serializedValueFor(Enum<?> key) {
        return (SerializableString) this._values.get(key);
    }

    public Collection<SerializableString> values() {
        return this._values.values();
    }

    public EnumMap<?, SerializableString> internalMap() {
        return this._values;
    }

    public Class<Enum<?>> getEnumClass() {
        return this._enumClass;
    }
}
