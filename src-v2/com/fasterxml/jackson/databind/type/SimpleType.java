/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBase;
import java.util.Collection;
import java.util.Map;

public final class SimpleType
extends TypeBase {
    private static final long serialVersionUID = -800374828948534376L;
    protected final String[] _typeNames;
    protected final JavaType[] _typeParameters;

    protected SimpleType(Class<?> class_) {
        this(class_, null, null, null, null, false);
    }

    protected SimpleType(Class<?> class_, String[] arrstring, JavaType[] arrjavaType, Object object, Object object2, boolean bl2) {
        super(class_, 0, object, object2, bl2);
        if (arrstring == null || arrstring.length == 0) {
            this._typeNames = null;
            this._typeParameters = null;
            return;
        }
        this._typeNames = arrstring;
        this._typeParameters = arrjavaType;
    }

    public static SimpleType construct(Class<?> class_) {
        if (Map.class.isAssignableFrom(class_)) {
            throw new IllegalArgumentException("Can not construct SimpleType for a Map (class: " + class_.getName() + ")");
        }
        if (Collection.class.isAssignableFrom(class_)) {
            throw new IllegalArgumentException("Can not construct SimpleType for a Collection (class: " + class_.getName() + ")");
        }
        if (class_.isArray()) {
            throw new IllegalArgumentException("Can not construct SimpleType for an array (class: " + class_.getName() + ")");
        }
        return new SimpleType(class_);
    }

    public static SimpleType constructUnsafe(Class<?> class_) {
        return new SimpleType(class_, null, null, null, null, false);
    }

    @Override
    protected JavaType _narrow(Class<?> class_) {
        return new SimpleType(class_, this._typeNames, this._typeParameters, this._valueHandler, this._typeHandler, this._asStatic);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected String buildCanonicalName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._class.getName());
        if (this._typeParameters != null && this._typeParameters.length > 0) {
            stringBuilder.append('<');
            boolean bl2 = true;
            for (JavaType javaType : this._typeParameters) {
                if (bl2) {
                    bl2 = false;
                } else {
                    stringBuilder.append(',');
                }
                stringBuilder.append(javaType.toCanonical());
            }
            stringBuilder.append('>');
        }
        return stringBuilder.toString();
    }

    @Override
    public JavaType containedType(int n2) {
        if (n2 < 0 || this._typeParameters == null || n2 >= this._typeParameters.length) {
            return null;
        }
        return this._typeParameters[n2];
    }

    @Override
    public int containedTypeCount() {
        if (this._typeParameters == null) {
            return 0;
        }
        return this._typeParameters.length;
    }

    @Override
    public String containedTypeName(int n2) {
        if (n2 < 0 || this._typeNames == null || n2 >= this._typeNames.length) {
            return null;
        }
        return this._typeNames[n2];
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object arrjavaType) {
        boolean bl2 = false;
        if (arrjavaType == this) {
            return true;
        }
        boolean bl3 = bl2;
        if (arrjavaType == null) return bl3;
        bl3 = bl2;
        if (arrjavaType.getClass() != this.getClass()) return bl3;
        JavaType[] arrjavaType2 = arrjavaType;
        bl3 = bl2;
        if (arrjavaType2._class != this._class) return bl3;
        arrjavaType = this._typeParameters;
        arrjavaType2 = arrjavaType2._typeParameters;
        if (arrjavaType == null) {
            if (arrjavaType2 == null) return true;
            bl3 = bl2;
            if (arrjavaType2.length != 0) return bl3;
            return true;
        }
        bl3 = bl2;
        if (arrjavaType2 == null) return bl3;
        bl3 = bl2;
        if (arrjavaType.length != arrjavaType2.length) return bl3;
        int n2 = 0;
        int n3 = arrjavaType.length;
        while (n2 < n3) {
            bl3 = bl2;
            if (!arrjavaType[n2].equals(arrjavaType2[n2])) return bl3;
            ++n2;
        }
        return true;
    }

    @Override
    public StringBuilder getErasedSignature(StringBuilder stringBuilder) {
        return SimpleType._classSignature(this._class, stringBuilder, true);
    }

    @Override
    public StringBuilder getGenericSignature(StringBuilder object) {
        SimpleType._classSignature(this._class, (StringBuilder)object, false);
        JavaType[] arrjavaType = object;
        if (this._typeParameters != null) {
            object.append('<');
            arrjavaType = this._typeParameters;
            int n2 = arrjavaType.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                object = arrjavaType[i2].getGenericSignature((StringBuilder)object);
            }
            object.append('>');
            arrjavaType = object;
        }
        arrjavaType.append(';');
        return arrjavaType;
    }

    @Override
    public boolean isContainerType() {
        return false;
    }

    @Override
    public JavaType narrowContentsBy(Class<?> class_) {
        throw new IllegalArgumentException("Internal error: SimpleType.narrowContentsBy() should never be called");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(40);
        stringBuilder.append("[simple type, class ").append(this.buildCanonicalName()).append(']');
        return stringBuilder.toString();
    }

    @Override
    public JavaType widenContentsBy(Class<?> class_) {
        throw new IllegalArgumentException("Internal error: SimpleType.widenContentsBy() should never be called");
    }

    @Override
    public JavaType withContentTypeHandler(Object object) {
        throw new IllegalArgumentException("Simple types have no content types; can not call withContenTypeHandler()");
    }

    @Override
    public SimpleType withContentValueHandler(Object object) {
        throw new IllegalArgumentException("Simple types have no content types; can not call withContenValueHandler()");
    }

    @Override
    public SimpleType withStaticTyping() {
        if (this._asStatic) {
            return this;
        }
        return new SimpleType(this._class, this._typeNames, this._typeParameters, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override
    public SimpleType withTypeHandler(Object object) {
        return new SimpleType(this._class, this._typeNames, this._typeParameters, this._valueHandler, object, this._asStatic);
    }

    @Override
    public SimpleType withValueHandler(Object object) {
        if (object == this._valueHandler) {
            return this;
        }
        return new SimpleType(this._class, this._typeNames, this._typeParameters, object, this._typeHandler, this._asStatic);
    }
}

