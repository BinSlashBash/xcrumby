/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public class ClassNameIdResolver
extends TypeIdResolverBase {
    public ClassNameIdResolver(JavaType javaType, TypeFactory typeFactory) {
        super(javaType, typeFactory);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final String _idFrom(Object object, Class<?> object2) {
        Object object3 = object2;
        if (Enum.class.isAssignableFrom(object2)) {
            object3 = object2;
            if (!object2.isEnum()) {
                object3 = object2.getSuperclass();
            }
        }
        if ((object2 = object3.getName()).startsWith("java.util")) {
            if (object instanceof EnumSet) {
                object = ClassUtil.findEnumType((EnumSet)object);
                return TypeFactory.defaultInstance().constructCollectionType((Class<? extends Collection>)EnumSet.class, object).toCanonical();
            }
            if (object instanceof EnumMap) {
                object = ClassUtil.findEnumType((EnumMap)object);
                return TypeFactory.defaultInstance().constructMapType((Class<? extends Map>)EnumMap.class, object, Object.class).toCanonical();
            }
            object3 = object2.substring(9);
            if (!object3.startsWith(".Arrays$")) {
                object = object2;
                if (!object3.startsWith(".Collections$")) return object;
            }
            object = object2;
            if (object2.indexOf("List") < 0) return object;
            return "java.util.ArrayList";
        }
        object = object2;
        if (object2.indexOf(36) < 0) return object;
        object = object2;
        if (ClassUtil.getOuterClass(object3) == null) return object;
        object = object2;
        if (ClassUtil.getOuterClass(this._baseType.getRawClass()) != null) return object;
        return this._baseType.getRawClass().getName();
    }

    protected JavaType _typeFromId(String string2, TypeFactory serializable) {
        if (string2.indexOf(60) > 0) {
            return serializable.constructFromCanonical(string2);
        }
        try {
            Class class_ = ClassUtil.findClass(string2);
            serializable = serializable.constructSpecializedType(this._baseType, class_);
            return serializable;
        }
        catch (ClassNotFoundException var2_3) {
            throw new IllegalArgumentException("Invalid type id '" + string2 + "' (for id type 'Id.class'): no such class found");
        }
        catch (Exception var2_4) {
            throw new IllegalArgumentException("Invalid type id '" + string2 + "' (for id type 'Id.class'): " + var2_4.getMessage(), var2_4);
        }
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CLASS;
    }

    @Override
    public String idFromValue(Object object) {
        return this._idFrom(object, object.getClass());
    }

    @Override
    public String idFromValueAndType(Object object, Class<?> class_) {
        return this._idFrom(object, class_);
    }

    public void registerSubtype(Class<?> class_, String string2) {
    }

    @Override
    public JavaType typeFromId(DatabindContext databindContext, String string2) {
        return this._typeFromId(string2, databindContext.getTypeFactory());
    }

    @Deprecated
    @Override
    public JavaType typeFromId(String string2) {
        return this._typeFromId(string2, this._typeFactory);
    }
}

