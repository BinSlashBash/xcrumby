/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBase;
import java.util.Collection;

public class CollectionLikeType
extends TypeBase {
    private static final long serialVersionUID = 4611641304150899138L;
    protected final JavaType _elementType;

    protected CollectionLikeType(Class<?> class_, JavaType javaType, Object object, Object object2, boolean bl2) {
        super(class_, javaType.hashCode(), object, object2, bl2);
        this._elementType = javaType;
    }

    public static CollectionLikeType construct(Class<?> class_, JavaType javaType) {
        return new CollectionLikeType(class_, javaType, null, null, false);
    }

    @Override
    protected JavaType _narrow(Class<?> class_) {
        return new CollectionLikeType(class_, this._elementType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    protected String buildCanonicalName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._class.getName());
        if (this._elementType != null) {
            stringBuilder.append('<');
            stringBuilder.append(this._elementType.toCanonical());
            stringBuilder.append('>');
        }
        return stringBuilder.toString();
    }

    @Override
    public JavaType containedType(int n2) {
        if (n2 == 0) {
            return this._elementType;
        }
        return null;
    }

    @Override
    public int containedTypeCount() {
        return 1;
    }

    @Override
    public String containedTypeName(int n2) {
        if (n2 == 0) {
            return "E";
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        object = (CollectionLikeType)object;
        if (this._class != object._class) return false;
        if (this._elementType.equals(object._elementType)) return true;
        return false;
    }

    @Override
    public JavaType getContentType() {
        return this._elementType;
    }

    @Override
    public StringBuilder getErasedSignature(StringBuilder stringBuilder) {
        return CollectionLikeType._classSignature(this._class, stringBuilder, true);
    }

    @Override
    public StringBuilder getGenericSignature(StringBuilder stringBuilder) {
        CollectionLikeType._classSignature(this._class, stringBuilder, false);
        stringBuilder.append('<');
        this._elementType.getGenericSignature(stringBuilder);
        stringBuilder.append(">;");
        return stringBuilder;
    }

    @Override
    public boolean isCollectionLikeType() {
        return true;
    }

    @Override
    public boolean isContainerType() {
        return true;
    }

    public boolean isTrueCollectionType() {
        return Collection.class.isAssignableFrom(this._class);
    }

    @Override
    public JavaType narrowContentsBy(Class<?> class_) {
        if (class_ == this._elementType.getRawClass()) {
            return this;
        }
        return new CollectionLikeType(this._class, this._elementType.narrowBy(class_), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public String toString() {
        return "[collection-like type; class " + this._class.getName() + ", contains " + this._elementType + "]";
    }

    @Override
    public JavaType widenContentsBy(Class<?> class_) {
        if (class_ == this._elementType.getRawClass()) {
            return this;
        }
        return new CollectionLikeType(this._class, this._elementType.widenBy(class_), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public CollectionLikeType withContentTypeHandler(Object object) {
        return new CollectionLikeType(this._class, this._elementType.withTypeHandler(object), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public CollectionLikeType withContentValueHandler(Object object) {
        return new CollectionLikeType(this._class, this._elementType.withValueHandler(object), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public CollectionLikeType withStaticTyping() {
        if (this._asStatic) {
            return this;
        }
        return new CollectionLikeType(this._class, this._elementType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
    }

    @Override
    public CollectionLikeType withTypeHandler(Object object) {
        return new CollectionLikeType(this._class, this._elementType, this._valueHandler, object, this._asStatic);
    }

    @Override
    public CollectionLikeType withValueHandler(Object object) {
        return new CollectionLikeType(this._class, this._elementType, object, this._typeHandler, this._asStatic);
    }
}

