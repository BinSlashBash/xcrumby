/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;

public final class CollectionType
extends CollectionLikeType {
    private static final long serialVersionUID = -7834910259750909424L;

    private CollectionType(Class<?> class_, JavaType javaType, Object object, Object object2, boolean bl2) {
        super(class_, javaType, object, object2, bl2);
    }

    public static CollectionType construct(Class<?> class_, JavaType javaType) {
        return new CollectionType(class_, javaType, null, null, false);
    }

    @Override
    protected JavaType _narrow(Class<?> class_) {
        return new CollectionType(class_, this._elementType, null, null, this._asStatic);
    }

    @Override
    public JavaType narrowContentsBy(Class<?> class_) {
        if (class_ == this._elementType.getRawClass()) {
            return this;
        }
        return new CollectionType(this._class, this._elementType.narrowBy(class_), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public String toString() {
        return "[collection type; class " + this._class.getName() + ", contains " + this._elementType + "]";
    }

    @Override
    public JavaType widenContentsBy(Class<?> class_) {
        if (class_ == this._elementType.getRawClass()) {
            return this;
        }
        return new CollectionType(this._class, this._elementType.widenBy(class_), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public CollectionType withContentTypeHandler(Object object) {
        return new CollectionType(this._class, this._elementType.withTypeHandler(object), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public CollectionType withContentValueHandler(Object object) {
        return new CollectionType(this._class, this._elementType.withValueHandler(object), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public CollectionType withStaticTyping() {
        if (this._asStatic) {
            return this;
        }
        return new CollectionType(this._class, this._elementType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
    }

    @Override
    public CollectionType withTypeHandler(Object object) {
        return new CollectionType(this._class, this._elementType, this._valueHandler, object, this._asStatic);
    }

    @Override
    public CollectionType withValueHandler(Object object) {
        return new CollectionType(this._class, this._elementType, object, this._typeHandler, this._asStatic);
    }
}

