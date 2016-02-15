/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.reflect;

import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.internal.$Gson$Types;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

public class TypeToken<T> {
    final int hashCode;
    final Class<? super T> rawType;
    final Type type;

    protected TypeToken() {
        this.type = TypeToken.getSuperclassTypeParameter(this.getClass());
        this.rawType = $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    TypeToken(Type type) {
        this.type = $Gson$Types.canonicalize($Gson$Preconditions.checkNotNull(type));
        this.rawType = $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    private static /* varargs */ AssertionError buildUnexpectedTypeError(Type type, Class<?> ... arrclass) {
        StringBuilder stringBuilder = new StringBuilder("Unexpected type. Expected one of: ");
        int n2 = arrclass.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            stringBuilder.append(arrclass[i2].getName()).append(", ");
        }
        stringBuilder.append("but got: ").append(type.getClass().getName()).append(", for type token: ").append(type.toString()).append('.');
        return new AssertionError((Object)stringBuilder.toString());
    }

    public static <T> TypeToken<T> get(Class<T> class_) {
        return new TypeToken<T>(class_);
    }

    public static TypeToken<?> get(Type type) {
        return new TypeToken<T>(type);
    }

    static Type getSuperclassTypeParameter(Class<?> type) {
        if ((type = type.getGenericSuperclass()) instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        return $Gson$Types.canonicalize(((ParameterizedType)type).getActualTypeArguments()[0]);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean isAssignableFrom(Type class_, GenericArrayType genericArrayType) {
        void var1_4;
        Type type = genericArrayType.getGenericComponentType();
        if (!(type instanceof ParameterizedType)) return true;
        Class class_2 = class_;
        if (class_ instanceof GenericArrayType) {
            Type type2 = ((GenericArrayType)((Object)class_)).getGenericComponentType();
            return TypeToken.isAssignableFrom((Type)var1_4, (ParameterizedType)type, new HashMap<String, Type>());
        }
        if (!(class_ instanceof Class)) return TypeToken.isAssignableFrom((Type)var1_4, (ParameterizedType)type, new HashMap<String, Type>());
        do {
            if (!class_.isArray()) {
                Class class_3 = class_;
                return TypeToken.isAssignableFrom((Type)var1_4, (ParameterizedType)type, new HashMap<String, Type>());
            }
            class_ = class_.getComponentType();
        } while (true);
    }

    private static boolean isAssignableFrom(Type object, ParameterizedType parameterizedType, Map<String, Type> map) {
        int n2;
        if (object == null) {
            return false;
        }
        if (parameterizedType.equals(object)) {
            return true;
        }
        Class class_ = $Gson$Types.getRawType((Type)object);
        ParameterizedType parameterizedType2 = null;
        if (object instanceof ParameterizedType) {
            parameterizedType2 = (ParameterizedType)object;
        }
        if (parameterizedType2 != null) {
            Type[] arrtype = parameterizedType2.getActualTypeArguments();
            TypeVariable<Class<?>>[] arrtypeVariable = class_.getTypeParameters();
            for (n2 = 0; n2 < arrtype.length; ++n2) {
                object = arrtype[n2];
                TypeVariable typeVariable = arrtypeVariable[n2];
                while (object instanceof TypeVariable) {
                    object = map.get(((TypeVariable)object).getName());
                }
                map.put(typeVariable.getName(), (Type)object);
            }
            if (TypeToken.typeEquals(parameterizedType2, parameterizedType, map)) {
                return true;
            }
        }
        object = class_.getGenericInterfaces();
        int n3 = object.length;
        for (n2 = 0; n2 < n3; ++n2) {
            if (!TypeToken.isAssignableFrom(object[n2], parameterizedType, new HashMap<String, Type>(map))) continue;
            return true;
        }
        return TypeToken.isAssignableFrom(class_.getGenericSuperclass(), parameterizedType, new HashMap<String, Type>(map));
    }

    private static boolean matches(Type type, Type type2, Map<String, Type> map) {
        if (type2.equals(type) || type instanceof TypeVariable && type2.equals(map.get(((TypeVariable)type).getName()))) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean typeEquals(ParameterizedType arrtype, ParameterizedType arrtype2, Map<String, Type> map) {
        if (!arrtype.getRawType().equals(arrtype2.getRawType())) return false;
        arrtype = arrtype.getActualTypeArguments();
        arrtype2 = arrtype2.getActualTypeArguments();
        int n2 = 0;
        while (n2 < arrtype.length) {
            if (!TypeToken.matches(arrtype[n2], arrtype2[n2], map)) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    public final boolean equals(Object object) {
        if (object instanceof TypeToken && $Gson$Types.equals(this.type, ((TypeToken)object).type)) {
            return true;
        }
        return false;
    }

    public final Class<? super T> getRawType() {
        return this.rawType;
    }

    public final Type getType() {
        return this.type;
    }

    public final int hashCode() {
        return this.hashCode;
    }

    @Deprecated
    public boolean isAssignableFrom(TypeToken<?> typeToken) {
        return this.isAssignableFrom(typeToken.getType());
    }

    @Deprecated
    public boolean isAssignableFrom(Class<?> class_) {
        return this.isAssignableFrom((Type)class_);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Deprecated
    public boolean isAssignableFrom(Type type) {
        if (type == null) {
            return false;
        }
        if (this.type.equals(type)) {
            return true;
        }
        if (this.type instanceof Class) {
            return this.rawType.isAssignableFrom($Gson$Types.getRawType(type));
        }
        if (this.type instanceof ParameterizedType) {
            return TypeToken.isAssignableFrom(type, (ParameterizedType)this.type, new HashMap<String, Type>());
        }
        if (!(this.type instanceof GenericArrayType)) throw TypeToken.buildUnexpectedTypeError(this.type, Class.class, ParameterizedType.class, GenericArrayType.class);
        if (!this.rawType.isAssignableFrom($Gson$Types.getRawType(type))) return false;
        if (!TypeToken.isAssignableFrom(type, (GenericArrayType)this.type)) return false;
        return true;
    }

    public final String toString() {
        return $Gson$Types.typeToString(this.type);
    }
}

