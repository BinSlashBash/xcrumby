/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBase;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.lang.reflect.Array;
import java.lang.reflect.Type;

public final class ArrayType
extends TypeBase {
    private static final long serialVersionUID = 9040058063449087477L;
    protected final JavaType _componentType;
    protected final Object _emptyArray;

    private ArrayType(JavaType javaType, Object object, Object object2, Object object3, boolean bl2) {
        super(object.getClass(), javaType.hashCode(), object2, object3, bl2);
        this._componentType = javaType;
        this._emptyArray = object;
    }

    public static ArrayType construct(JavaType javaType, Object object, Object object2) {
        return new ArrayType(javaType, Array.newInstance(javaType.getRawClass(), 0), null, null, false);
    }

    @Override
    protected JavaType _narrow(Class<?> class_) {
        if (!class_.isArray()) {
            throw new IllegalArgumentException("Incompatible narrowing operation: trying to narrow " + this.toString() + " to class " + class_.getName());
        }
        class_ = class_.getComponentType();
        return ArrayType.construct(TypeFactory.defaultInstance().constructType(class_), this._valueHandler, this._typeHandler);
    }

    @Override
    protected String buildCanonicalName() {
        return this._class.getName();
    }

    @Override
    public JavaType containedType(int n2) {
        if (n2 == 0) {
            return this._componentType;
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
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object == this) {
            return true;
        }
        boolean bl3 = bl2;
        if (object == null) return bl3;
        bl3 = bl2;
        if (object.getClass() != this.getClass()) return bl3;
        object = (ArrayType)object;
        return this._componentType.equals(object._componentType);
    }

    @Override
    public JavaType getContentType() {
        return this._componentType;
    }

    @Override
    public StringBuilder getErasedSignature(StringBuilder stringBuilder) {
        stringBuilder.append('[');
        return this._componentType.getErasedSignature(stringBuilder);
    }

    @Override
    public StringBuilder getGenericSignature(StringBuilder stringBuilder) {
        stringBuilder.append('[');
        return this._componentType.getGenericSignature(stringBuilder);
    }

    @Override
    public boolean hasGenericTypes() {
        return this._componentType.hasGenericTypes();
    }

    @Override
    public boolean isAbstract() {
        return false;
    }

    @Override
    public boolean isArrayType() {
        return true;
    }

    @Override
    public boolean isConcrete() {
        return true;
    }

    @Override
    public boolean isContainerType() {
        return true;
    }

    @Override
    public JavaType narrowContentsBy(Class<?> class_) {
        if (class_ == this._componentType.getRawClass()) {
            return this;
        }
        return ArrayType.construct(this._componentType.narrowBy(class_), this._valueHandler, this._typeHandler);
    }

    @Override
    public String toString() {
        return "[array type, component type: " + this._componentType + "]";
    }

    @Override
    public JavaType widenContentsBy(Class<?> class_) {
        if (class_ == this._componentType.getRawClass()) {
            return this;
        }
        return ArrayType.construct(this._componentType.widenBy(class_), this._valueHandler, this._typeHandler);
    }

    @Override
    public ArrayType withContentTypeHandler(Object object) {
        if (object == this._componentType.getTypeHandler()) {
            return this;
        }
        return new ArrayType(this._componentType.withTypeHandler(object), this._emptyArray, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public ArrayType withContentValueHandler(Object object) {
        if (object == this._componentType.getValueHandler()) {
            return this;
        }
        return new ArrayType(this._componentType.withValueHandler(object), this._emptyArray, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public ArrayType withStaticTyping() {
        if (this._asStatic) {
            return this;
        }
        return new ArrayType(this._componentType.withStaticTyping(), this._emptyArray, this._valueHandler, this._typeHandler, true);
    }

    @Override
    public ArrayType withTypeHandler(Object object) {
        if (object == this._typeHandler) {
            return this;
        }
        return new ArrayType(this._componentType, this._emptyArray, this._valueHandler, object, this._asStatic);
    }

    @Override
    public ArrayType withValueHandler(Object object) {
        if (object == this._valueHandler) {
            return this;
        }
        return new ArrayType(this._componentType, this._emptyArray, object, this._typeHandler, this._asStatic);
    }
}

