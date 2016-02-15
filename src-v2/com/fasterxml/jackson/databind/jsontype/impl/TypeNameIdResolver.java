/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class TypeNameIdResolver
extends TypeIdResolverBase {
    protected final MapperConfig<?> _config;
    protected final HashMap<String, JavaType> _idToType;
    protected final HashMap<String, String> _typeToId;

    protected TypeNameIdResolver(MapperConfig<?> mapperConfig, JavaType javaType, HashMap<String, String> hashMap, HashMap<String, JavaType> hashMap2) {
        super(javaType, mapperConfig.getTypeFactory());
        this._config = mapperConfig;
        this._typeToId = hashMap;
        this._idToType = hashMap2;
    }

    protected static String _defaultTypeId(Class<?> object) {
        int n2 = (object = object.getName()).lastIndexOf(46);
        if (n2 < 0) {
            return object;
        }
        return object.substring(n2 + 1);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static TypeNameIdResolver construct(MapperConfig<?> mapperConfig, JavaType javaType, Collection<NamedType> object, boolean bl2, boolean bl3) {
        if (bl2 == bl3) {
            throw new IllegalArgumentException();
        }
        HashMap<String, String> hashMap = null;
        HashMap<String, JavaType> hashMap2 = null;
        if (bl2) {
            hashMap = new HashMap<String, String>();
        }
        if (bl3) {
            hashMap2 = new HashMap<String, JavaType>();
        }
        if (object != null) {
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                JavaType javaType2;
                object = (NamedType)iterator.next();
                Class class_ = object.getType();
                object = object.hasName() ? object.getName() : TypeNameIdResolver._defaultTypeId(class_);
                if (bl2) {
                    hashMap.put(class_.getName(), (String)object);
                }
                if (!bl3 || (javaType2 = hashMap2.get(object)) != null && class_.isAssignableFrom(javaType2.getRawClass())) continue;
                hashMap2.put((String)object, mapperConfig.constructType(class_));
            }
        }
        return new TypeNameIdResolver(mapperConfig, javaType, hashMap, hashMap2);
    }

    protected JavaType _typeFromId(String string2) {
        return this._idToType.get(string2);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.NAME;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String idFromValue(Object object) {
        Class class_ = this._typeFactory.constructType(object.getClass()).getRawClass();
        String string2 = class_.getName();
        HashMap<String, String> hashMap = this._typeToId;
        synchronized (hashMap) {
            Object object2 = object = this._typeToId.get(string2);
            if (object == null) {
                if (this._config.isAnnotationProcessingEnabled()) {
                    object = this._config.introspectClassAnnotations(class_);
                    object = this._config.getAnnotationIntrospector().findTypeName(object.getClassInfo());
                }
                object2 = object;
                if (object == null) {
                    object2 = TypeNameIdResolver._defaultTypeId(class_);
                }
                this._typeToId.put(string2, (String)object2);
            }
            return object2;
        }
    }

    @Override
    public String idFromValueAndType(Object object, Class<?> class_) {
        if (object == null) {
            return null;
        }
        return this.idFromValue(object);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[').append(this.getClass().getName());
        stringBuilder.append("; id-to-type=").append(this._idToType);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public JavaType typeFromId(DatabindContext databindContext, String string2) {
        return this._typeFromId(string2);
    }

    @Deprecated
    @Override
    public JavaType typeFromId(String string2) {
        return this._typeFromId(string2);
    }
}

