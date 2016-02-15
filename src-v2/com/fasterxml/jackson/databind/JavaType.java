/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.type.ResolvedType;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public abstract class JavaType
extends ResolvedType
implements Serializable,
Type {
    private static final long serialVersionUID = 1;
    protected final boolean _asStatic;
    protected final Class<?> _class;
    protected final int _hash;
    protected final Object _typeHandler;
    protected final Object _valueHandler;

    protected JavaType(Class<?> class_, int n2, Object object, Object object2, boolean bl2) {
        this._class = class_;
        this._hash = class_.getName().hashCode() + n2;
        this._valueHandler = object;
        this._typeHandler = object2;
        this._asStatic = bl2;
    }

    protected void _assertSubclass(Class<?> class_, Class<?> class_2) {
        if (!this._class.isAssignableFrom(class_)) {
            throw new IllegalArgumentException("Class " + class_.getName() + " is not assignable to " + this._class.getName());
        }
    }

    protected abstract JavaType _narrow(Class<?> var1);

    protected JavaType _widen(Class<?> class_) {
        return this._narrow(class_);
    }

    @Override
    public JavaType containedType(int n2) {
        return null;
    }

    @Override
    public int containedTypeCount() {
        return 0;
    }

    @Override
    public String containedTypeName(int n2) {
        return null;
    }

    public abstract boolean equals(Object var1);

    public JavaType forcedNarrowBy(Class<?> type) {
        Type type2;
        if (type == this._class) {
            return this;
        }
        type = type2 = this._narrow(type);
        if (this._valueHandler != type2.getValueHandler()) {
            type = type2.withValueHandler(this._valueHandler);
        }
        type2 = type;
        if (this._typeHandler != type.getTypeHandler()) {
            type2 = type.withTypeHandler(this._typeHandler);
        }
        return type2;
    }

    @Override
    public JavaType getContentType() {
        return null;
    }

    public String getErasedSignature() {
        StringBuilder stringBuilder = new StringBuilder(40);
        this.getErasedSignature(stringBuilder);
        return stringBuilder.toString();
    }

    public abstract StringBuilder getErasedSignature(StringBuilder var1);

    public String getGenericSignature() {
        StringBuilder stringBuilder = new StringBuilder(40);
        this.getGenericSignature(stringBuilder);
        return stringBuilder.toString();
    }

    public abstract StringBuilder getGenericSignature(StringBuilder var1);

    @Override
    public JavaType getKeyType() {
        return null;
    }

    @Override
    public final Class<?> getRawClass() {
        return this._class;
    }

    public <T> T getTypeHandler() {
        return (T)this._typeHandler;
    }

    public <T> T getValueHandler() {
        return (T)this._valueHandler;
    }

    @Override
    public boolean hasGenericTypes() {
        if (this.containedTypeCount() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public final boolean hasRawClass(Class<?> class_) {
        if (this._class == class_) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this._hash;
    }

    @Override
    public boolean isAbstract() {
        return Modifier.isAbstract(this._class.getModifiers());
    }

    @Override
    public boolean isArrayType() {
        return false;
    }

    @Override
    public boolean isCollectionLikeType() {
        return false;
    }

    @Override
    public boolean isConcrete() {
        if ((this._class.getModifiers() & 1536) == 0) {
            return true;
        }
        return this._class.isPrimitive();
    }

    @Override
    public abstract boolean isContainerType();

    @Override
    public final boolean isEnumType() {
        return this._class.isEnum();
    }

    @Override
    public final boolean isFinal() {
        return Modifier.isFinal(this._class.getModifiers());
    }

    @Override
    public final boolean isInterface() {
        return this._class.isInterface();
    }

    @Override
    public boolean isMapLikeType() {
        return false;
    }

    @Override
    public final boolean isPrimitive() {
        return this._class.isPrimitive();
    }

    @Override
    public boolean isThrowable() {
        return Throwable.class.isAssignableFrom(this._class);
    }

    public JavaType narrowBy(Class<?> type) {
        Type type2;
        if (type == this._class) {
            return this;
        }
        this._assertSubclass(type, this._class);
        type = type2 = this._narrow(type);
        if (this._valueHandler != type2.getValueHandler()) {
            type = type2.withValueHandler(this._valueHandler);
        }
        type2 = type;
        if (this._typeHandler != type.getTypeHandler()) {
            type2 = type.withTypeHandler(this._typeHandler);
        }
        return type2;
    }

    public abstract JavaType narrowContentsBy(Class<?> var1);

    public abstract String toString();

    public final boolean useStaticType() {
        return this._asStatic;
    }

    public JavaType widenBy(Class<?> class_) {
        if (class_ == this._class) {
            return this;
        }
        this._assertSubclass(this._class, class_);
        return this._widen(class_);
    }

    public abstract JavaType widenContentsBy(Class<?> var1);

    public abstract JavaType withContentTypeHandler(Object var1);

    public abstract JavaType withContentValueHandler(Object var1);

    public abstract JavaType withStaticTyping();

    public abstract JavaType withTypeHandler(Object var1);

    public abstract JavaType withValueHandler(Object var1);
}

