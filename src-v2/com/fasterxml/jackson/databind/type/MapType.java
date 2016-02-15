/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.MapLikeType;

public final class MapType
extends MapLikeType {
    private static final long serialVersionUID = -811146779148281500L;

    private MapType(Class<?> class_, JavaType javaType, JavaType javaType2, Object object, Object object2, boolean bl2) {
        super(class_, javaType, javaType2, object, object2, bl2);
    }

    public static MapType construct(Class<?> class_, JavaType javaType, JavaType javaType2) {
        return new MapType(class_, javaType, javaType2, null, null, false);
    }

    @Override
    protected JavaType _narrow(Class<?> class_) {
        return new MapType(class_, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public JavaType narrowContentsBy(Class<?> class_) {
        if (class_ == this._valueType.getRawClass()) {
            return this;
        }
        return new MapType(this._class, this._keyType, this._valueType.narrowBy(class_), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public JavaType narrowKey(Class<?> class_) {
        if (class_ == this._keyType.getRawClass()) {
            return this;
        }
        return new MapType(this._class, this._keyType.narrowBy(class_), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public String toString() {
        return "[map type; class " + this._class.getName() + ", " + this._keyType + " -> " + this._valueType + "]";
    }

    @Override
    public JavaType widenContentsBy(Class<?> class_) {
        if (class_ == this._valueType.getRawClass()) {
            return this;
        }
        return new MapType(this._class, this._keyType, this._valueType.widenBy(class_), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public JavaType widenKey(Class<?> class_) {
        if (class_ == this._keyType.getRawClass()) {
            return this;
        }
        return new MapType(this._class, this._keyType.widenBy(class_), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public MapType withContentTypeHandler(Object object) {
        return new MapType(this._class, this._keyType, this._valueType.withTypeHandler(object), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public MapType withContentValueHandler(Object object) {
        return new MapType(this._class, this._keyType, this._valueType.withValueHandler(object), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public MapType withKeyTypeHandler(Object object) {
        return new MapType(this._class, this._keyType.withTypeHandler(object), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public MapType withKeyValueHandler(Object object) {
        return new MapType(this._class, this._keyType.withValueHandler(object), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public MapType withStaticTyping() {
        if (this._asStatic) {
            return this;
        }
        return new MapType(this._class, this._keyType.withStaticTyping(), this._valueType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
    }

    @Override
    public MapType withTypeHandler(Object object) {
        return new MapType(this._class, this._keyType, this._valueType, this._valueHandler, object, this._asStatic);
    }

    @Override
    public MapType withValueHandler(Object object) {
        return new MapType(this._class, this._keyType, this._valueType, object, this._typeHandler, this._asStatic);
    }
}

