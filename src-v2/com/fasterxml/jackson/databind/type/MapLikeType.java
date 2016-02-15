/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBase;
import java.util.Map;

public class MapLikeType
extends TypeBase {
    private static final long serialVersionUID = 416067702302823522L;
    protected final JavaType _keyType;
    protected final JavaType _valueType;

    protected MapLikeType(Class<?> class_, JavaType javaType, JavaType javaType2, Object object, Object object2, boolean bl2) {
        super(class_, javaType.hashCode() ^ javaType2.hashCode(), object, object2, bl2);
        this._keyType = javaType;
        this._valueType = javaType2;
    }

    public static MapLikeType construct(Class<?> class_, JavaType javaType, JavaType javaType2) {
        return new MapLikeType(class_, javaType, javaType2, null, null, false);
    }

    @Override
    protected JavaType _narrow(Class<?> class_) {
        return new MapLikeType(class_, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    protected String buildCanonicalName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._class.getName());
        if (this._keyType != null) {
            stringBuilder.append('<');
            stringBuilder.append(this._keyType.toCanonical());
            stringBuilder.append(',');
            stringBuilder.append(this._valueType.toCanonical());
            stringBuilder.append('>');
        }
        return stringBuilder.toString();
    }

    @Override
    public JavaType containedType(int n2) {
        if (n2 == 0) {
            return this._keyType;
        }
        if (n2 == 1) {
            return this._valueType;
        }
        return null;
    }

    @Override
    public int containedTypeCount() {
        return 2;
    }

    @Override
    public String containedTypeName(int n2) {
        if (n2 == 0) {
            return "K";
        }
        if (n2 == 1) {
            return "V";
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
        object = (MapLikeType)object;
        if (this._class != object._class) return false;
        if (!this._keyType.equals(object._keyType)) return false;
        if (this._valueType.equals(object._valueType)) return true;
        return false;
    }

    @Override
    public JavaType getContentType() {
        return this._valueType;
    }

    @Override
    public StringBuilder getErasedSignature(StringBuilder stringBuilder) {
        return MapLikeType._classSignature(this._class, stringBuilder, true);
    }

    @Override
    public StringBuilder getGenericSignature(StringBuilder stringBuilder) {
        MapLikeType._classSignature(this._class, stringBuilder, false);
        stringBuilder.append('<');
        this._keyType.getGenericSignature(stringBuilder);
        this._valueType.getGenericSignature(stringBuilder);
        stringBuilder.append(">;");
        return stringBuilder;
    }

    @Override
    public JavaType getKeyType() {
        return this._keyType;
    }

    @Override
    public boolean isContainerType() {
        return true;
    }

    @Override
    public boolean isMapLikeType() {
        return true;
    }

    public boolean isTrueMapType() {
        return Map.class.isAssignableFrom(this._class);
    }

    @Override
    public JavaType narrowContentsBy(Class<?> class_) {
        if (class_ == this._valueType.getRawClass()) {
            return this;
        }
        return new MapLikeType(this._class, this._keyType, this._valueType.narrowBy(class_), this._valueHandler, this._typeHandler, this._asStatic);
    }

    public JavaType narrowKey(Class<?> class_) {
        if (class_ == this._keyType.getRawClass()) {
            return this;
        }
        return new MapLikeType(this._class, this._keyType.narrowBy(class_), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public String toString() {
        return "[map-like type; class " + this._class.getName() + ", " + this._keyType + " -> " + this._valueType + "]";
    }

    @Override
    public JavaType widenContentsBy(Class<?> class_) {
        if (class_ == this._valueType.getRawClass()) {
            return this;
        }
        return new MapLikeType(this._class, this._keyType, this._valueType.widenBy(class_), this._valueHandler, this._typeHandler, this._asStatic);
    }

    public JavaType widenKey(Class<?> class_) {
        if (class_ == this._keyType.getRawClass()) {
            return this;
        }
        return new MapLikeType(this._class, this._keyType.widenBy(class_), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public MapLikeType withContentTypeHandler(Object object) {
        return new MapLikeType(this._class, this._keyType, this._valueType.withTypeHandler(object), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public MapLikeType withContentValueHandler(Object object) {
        return new MapLikeType(this._class, this._keyType, this._valueType.withValueHandler(object), this._valueHandler, this._typeHandler, this._asStatic);
    }

    public MapLikeType withKeyTypeHandler(Object object) {
        return new MapLikeType(this._class, this._keyType.withTypeHandler(object), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    public MapLikeType withKeyValueHandler(Object object) {
        return new MapLikeType(this._class, this._keyType.withValueHandler(object), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public MapLikeType withStaticTyping() {
        if (this._asStatic) {
            return this;
        }
        return new MapLikeType(this._class, this._keyType, this._valueType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
    }

    @Override
    public MapLikeType withTypeHandler(Object object) {
        return new MapLikeType(this._class, this._keyType, this._valueType, this._valueHandler, object, this._asStatic);
    }

    @Override
    public MapLikeType withValueHandler(Object object) {
        return new MapLikeType(this._class, this._keyType, this._valueType, object, this._typeHandler, this._asStatic);
    }
}

