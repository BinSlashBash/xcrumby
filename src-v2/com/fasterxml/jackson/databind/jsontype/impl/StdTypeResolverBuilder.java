/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsExternalTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsExternalTypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsWrapperTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsWrapperTypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.MinimalClassNameIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeNameIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.Collection;

public class StdTypeResolverBuilder
implements TypeResolverBuilder<StdTypeResolverBuilder> {
    protected TypeIdResolver _customIdResolver;
    protected Class<?> _defaultImpl;
    protected JsonTypeInfo.Id _idType;
    protected JsonTypeInfo.As _includeAs;
    protected boolean _typeIdVisible = false;
    protected String _typeProperty;

    public static StdTypeResolverBuilder noTypeInfoBuilder() {
        return new StdTypeResolverBuilder().init(JsonTypeInfo.Id.NONE, null);
    }

    @Override
    public TypeDeserializer buildTypeDeserializer(DeserializationConfig object, JavaType javaType, Collection<NamedType> collection) {
        if (this._idType == JsonTypeInfo.Id.NONE) {
            return null;
        }
        object = this.idResolver(object, javaType, collection, false, true);
        switch (.$SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As[this._includeAs.ordinal()]) {
            default: {
                throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + (Object)((Object)this._includeAs));
            }
            case 1: {
                return new AsArrayTypeDeserializer(javaType, (TypeIdResolver)object, this._typeProperty, this._typeIdVisible, this._defaultImpl);
            }
            case 2: {
                return new AsPropertyTypeDeserializer(javaType, (TypeIdResolver)object, this._typeProperty, this._typeIdVisible, this._defaultImpl);
            }
            case 3: {
                return new AsWrapperTypeDeserializer(javaType, (TypeIdResolver)object, this._typeProperty, this._typeIdVisible, this._defaultImpl);
            }
            case 4: 
        }
        return new AsExternalTypeDeserializer(javaType, (TypeIdResolver)object, this._typeProperty, this._typeIdVisible, this._defaultImpl);
    }

    @Override
    public TypeSerializer buildTypeSerializer(SerializationConfig object, JavaType javaType, Collection<NamedType> collection) {
        if (this._idType == JsonTypeInfo.Id.NONE) {
            return null;
        }
        object = this.idResolver(object, javaType, collection, true, false);
        switch (.$SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As[this._includeAs.ordinal()]) {
            default: {
                throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + (Object)((Object)this._includeAs));
            }
            case 1: {
                return new AsArrayTypeSerializer((TypeIdResolver)object, null);
            }
            case 2: {
                return new AsPropertyTypeSerializer((TypeIdResolver)object, null, this._typeProperty);
            }
            case 3: {
                return new AsWrapperTypeSerializer((TypeIdResolver)object, null);
            }
            case 4: 
        }
        return new AsExternalTypeSerializer((TypeIdResolver)object, null, this._typeProperty);
    }

    @Override
    public StdTypeResolverBuilder defaultImpl(Class<?> class_) {
        this._defaultImpl = class_;
        return this;
    }

    @Override
    public Class<?> getDefaultImpl() {
        return this._defaultImpl;
    }

    public String getTypeProperty() {
        return this._typeProperty;
    }

    protected TypeIdResolver idResolver(MapperConfig<?> mapperConfig, JavaType javaType, Collection<NamedType> collection, boolean bl2, boolean bl3) {
        if (this._customIdResolver != null) {
            return this._customIdResolver;
        }
        if (this._idType == null) {
            throw new IllegalStateException("Can not build, 'init()' not yet called");
        }
        switch (.$SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$Id[this._idType.ordinal()]) {
            default: {
                throw new IllegalStateException("Do not know how to construct standard type id resolver for idType: " + (Object)((Object)this._idType));
            }
            case 1: {
                return new ClassNameIdResolver(javaType, mapperConfig.getTypeFactory());
            }
            case 2: {
                return new MinimalClassNameIdResolver(javaType, mapperConfig.getTypeFactory());
            }
            case 3: {
                return TypeNameIdResolver.construct(mapperConfig, javaType, collection, bl2, bl3);
            }
            case 4: 
        }
        return null;
    }

    @Override
    public StdTypeResolverBuilder inclusion(JsonTypeInfo.As as2) {
        if (as2 == null) {
            throw new IllegalArgumentException("includeAs can not be null");
        }
        this._includeAs = as2;
        return this;
    }

    @Override
    public StdTypeResolverBuilder init(JsonTypeInfo.Id id2, TypeIdResolver typeIdResolver) {
        if (id2 == null) {
            throw new IllegalArgumentException("idType can not be null");
        }
        this._idType = id2;
        this._customIdResolver = typeIdResolver;
        this._typeProperty = id2.getDefaultPropertyName();
        return this;
    }

    public boolean isTypeIdVisible() {
        return this._typeIdVisible;
    }

    @Override
    public StdTypeResolverBuilder typeIdVisibility(boolean bl2) {
        this._typeIdVisible = bl2;
        return this;
    }

    @Override
    public StdTypeResolverBuilder typeProperty(String string2) {
        String string3;
        block2 : {
            if (string2 != null) {
                string3 = string2;
                if (string2.length() != 0) break block2;
            }
            string3 = this._idType.getDefaultPropertyName();
        }
        this._typeProperty = string3;
        return this;
    }

}

