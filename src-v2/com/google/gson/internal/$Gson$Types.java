/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson.internal;

import com.google.gson.internal.$Gson$Preconditions;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

public final class $Gson$Types {
    static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    private $Gson$Types() {
    }

    public static GenericArrayType arrayOf(Type type) {
        return new GenericArrayTypeImpl(type);
    }

    public static Type canonicalize(Type type) {
        if (type instanceof Class) {
            if ((type = (Class)type).isArray()) {
                type = new GenericArrayTypeImpl($Gson$Types.canonicalize(type.getComponentType()));
            }
            return type;
        }
        if (type instanceof ParameterizedType) {
            type = (ParameterizedType)type;
            return new ParameterizedTypeImpl(type.getOwnerType(), type.getRawType(), type.getActualTypeArguments());
        }
        if (type instanceof GenericArrayType) {
            return new GenericArrayTypeImpl(((GenericArrayType)type).getGenericComponentType());
        }
        if (type instanceof WildcardType) {
            type = (WildcardType)type;
            return new WildcardTypeImpl(type.getUpperBounds(), type.getLowerBounds());
        }
        return type;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void checkNotPrimitive(Type type) {
        boolean bl2 = !(type instanceof Class) || !((Class)type).isPrimitive();
        $Gson$Preconditions.checkArgument(bl2);
    }

    private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
        if ((typeVariable = typeVariable.getGenericDeclaration()) instanceof Class) {
            return (Class)((Object)typeVariable);
        }
        return null;
    }

    static boolean equal(Object object, Object object2) {
        if (object == object2 || object != null && object.equals(object2)) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean equals(Type type, Type type2) {
        boolean bl2;
        boolean bl3 = true;
        boolean bl4 = true;
        boolean bl5 = true;
        boolean bl6 = false;
        if (type == type2) {
            return true;
        }
        if (type instanceof Class) {
            return type.equals(type2);
        }
        if (type instanceof ParameterizedType) {
            bl2 = bl6;
            if (!(type2 instanceof ParameterizedType)) return bl2;
            type = (ParameterizedType)type;
            type2 = (ParameterizedType)type2;
            if (!$Gson$Types.equal(type.getOwnerType(), type2.getOwnerType())) return false;
            if (!type.getRawType().equals(type2.getRawType())) return false;
            if (!Arrays.equals(type.getActualTypeArguments(), type2.getActualTypeArguments())) return false;
            return bl5;
        }
        if (type instanceof GenericArrayType) {
            bl2 = bl6;
            if (!(type2 instanceof GenericArrayType)) return bl2;
            type = (GenericArrayType)type;
            type2 = (GenericArrayType)type2;
            return $Gson$Types.equals(type.getGenericComponentType(), type2.getGenericComponentType());
        }
        if (type instanceof WildcardType) {
            bl2 = bl6;
            if (!(type2 instanceof WildcardType)) return bl2;
            type = (WildcardType)type;
            type2 = (WildcardType)type2;
            if (!Arrays.equals(type.getUpperBounds(), type2.getUpperBounds())) return false;
            if (!Arrays.equals(type.getLowerBounds(), type2.getLowerBounds())) return false;
            return bl3;
        }
        bl2 = bl6;
        if (!(type instanceof TypeVariable)) return bl2;
        bl2 = bl6;
        if (!(type2 instanceof TypeVariable)) return bl2;
        type = (TypeVariable)type;
        type2 = (TypeVariable)type2;
        if (type.getGenericDeclaration() != type2.getGenericDeclaration()) return false;
        if (!type.getName().equals(type2.getName())) return false;
        return bl4;
    }

    public static Type getArrayComponentType(Type type) {
        if (type instanceof GenericArrayType) {
            return ((GenericArrayType)type).getGenericComponentType();
        }
        return ((Class)type).getComponentType();
    }

    public static Type getCollectionElementType(Type type, Class<?> type2) {
        type = type2 = $Gson$Types.getSupertype(type, type2, Collection.class);
        if (type2 instanceof WildcardType) {
            type = ((WildcardType)type2).getUpperBounds()[0];
        }
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType)type).getActualTypeArguments()[0];
        }
        return Object.class;
    }

    static Type getGenericSupertype(Type class_, Class<?> object, Class<?> class_2) {
        if (class_2 == object) {
            return class_;
        }
        if (class_2.isInterface()) {
            class_ = object.getInterfaces();
            int n2 = class_.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                if (class_[i2] == class_2) {
                    return object.getGenericInterfaces()[i2];
                }
                if (!class_2.isAssignableFrom(class_[i2])) continue;
                return $Gson$Types.getGenericSupertype(object.getGenericInterfaces()[i2], class_[i2], class_2);
            }
        }
        if (!object.isInterface()) {
            while (object != Object.class) {
                class_ = object.getSuperclass();
                if (class_ == class_2) {
                    return object.getGenericSuperclass();
                }
                if (class_2.isAssignableFrom(class_)) {
                    return $Gson$Types.getGenericSupertype(object.getGenericSuperclass(), class_, class_2);
                }
                object = class_;
            }
        }
        return class_2;
    }

    public static Type[] getMapKeyAndValueTypes(Type type, Class<?> class_) {
        if (type == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        if ((type = $Gson$Types.getSupertype(type, class_, Map.class)) instanceof ParameterizedType) {
            return ((ParameterizedType)type).getActualTypeArguments();
        }
        return new Type[]{Object.class, Object.class};
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Class<?> getRawType(Type type) {
        String string2;
        if (type instanceof Class) {
            return (Class)type;
        }
        if (type instanceof ParameterizedType) {
            type = ((ParameterizedType)type).getRawType();
            $Gson$Preconditions.checkArgument(type instanceof Class);
            return (Class)type;
        }
        if (type instanceof GenericArrayType) {
            return Array.newInstance($Gson$Types.getRawType(((GenericArrayType)type).getGenericComponentType()), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return $Gson$Types.getRawType(((WildcardType)type).getUpperBounds()[0]);
        }
        if (type == null) {
            string2 = "null";
            do {
                throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + string2);
                break;
            } while (true);
        }
        string2 = type.getClass().getName();
        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + string2);
    }

    static Type getSupertype(Type type, Class<?> class_, Class<?> class_2) {
        $Gson$Preconditions.checkArgument(class_2.isAssignableFrom(class_));
        return $Gson$Types.resolve(type, class_, $Gson$Types.getGenericSupertype(type, class_, class_2));
    }

    private static int hashCodeOrZero(Object object) {
        if (object != null) {
            return object.hashCode();
        }
        return 0;
    }

    private static int indexOf(Object[] arrobject, Object object) {
        for (int i2 = 0; i2 < arrobject.length; ++i2) {
            if (!object.equals(arrobject[i2])) continue;
            return i2;
        }
        throw new NoSuchElementException();
    }

    public static /* varargs */ ParameterizedType newParameterizedTypeWithOwner(Type type, Type type2, Type ... arrtype) {
        return new ParameterizedTypeImpl(type, type2, arrtype);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Type resolve(Type type, Class<?> class_, Type object) {
        Object object2;
        Object object3;
        void var1_8;
        Type[] arrtype;
        while (object2 instanceof TypeVariable) {
            arrtype = (TypeVariable)object2;
            object2 = object3 = $Gson$Types.resolveTypeVariable(type, var1_8, arrtype);
            if (object3 != arrtype) continue;
            return object3;
        }
        if (object2 instanceof Class && ((Class)object2).isArray()) {
            void var0_3;
            Type type2;
            object3 = (object2 = (Class)object2).getComponentType();
            if (object3 == (type2 = $Gson$Types.resolve(type, var1_8, object3))) {
                Type type3 = object2;
                return var0_3;
            }
            GenericArrayType genericArrayType = $Gson$Types.arrayOf(type2);
            return var0_3;
        }
        if (object2 instanceof GenericArrayType) {
            Type type4;
            object3 = (object2 = (GenericArrayType)object2).getGenericComponentType();
            if (object3 == (type4 = $Gson$Types.resolve(type, var1_8, object3))) return object2;
            return $Gson$Types.arrayOf(type4);
        }
        if (object2 instanceof ParameterizedType) {
            arrtype = (ParameterizedType)object2;
            object2 = arrtype.getOwnerType();
            Type type5 = $Gson$Types.resolve(type, var1_8, (Type)object2);
            boolean bl2 = type5 != object2;
            object3 = arrtype.getActualTypeArguments();
            int n2 = 0;
            int n3 = object3.length;
            do {
                if (n2 >= n3) {
                    object2 = arrtype;
                    if (!bl2) return object2;
                    return $Gson$Types.newParameterizedTypeWithOwner(type5, arrtype.getRawType(), (Type[])object3);
                }
                Type type6 = $Gson$Types.resolve(type, var1_8, object3[n2]);
                object2 = object3;
                boolean bl3 = bl2;
                if (type6 != object3[n2]) {
                    object2 = object3;
                    bl3 = bl2;
                    if (!bl2) {
                        object2 = (Type[])object3.clone();
                        bl3 = true;
                    }
                    object2[n2] = type6;
                }
                ++n2;
                object3 = object2;
                bl2 = bl3;
            } while (true);
        }
        if (!(object2 instanceof WildcardType)) return object2;
        object3 = (WildcardType)object2;
        arrtype = object3.getLowerBounds();
        Type[] arrtype2 = object3.getUpperBounds();
        if (arrtype.length == 1) {
            Type type7 = $Gson$Types.resolve(type, var1_8, arrtype[0]);
            object2 = object3;
            if (type7 == arrtype[0]) return object2;
            return $Gson$Types.supertypeOf(type7);
        }
        object2 = object3;
        if (arrtype2.length != 1) return object2;
        Type type8 = $Gson$Types.resolve(type, var1_8, arrtype2[0]);
        object2 = object3;
        if (type8 == arrtype2[0]) return object2;
        return $Gson$Types.subtypeOf(type8);
    }

    /*
     * Enabled aggressive block sorting
     */
    static Type resolveTypeVariable(Type type, Class<?> class_, TypeVariable<?> typeVariable) {
        Class class_2 = $Gson$Types.declaringClassOf(typeVariable);
        if (class_2 == null || !((type = $Gson$Types.getGenericSupertype(type, class_, class_2)) instanceof ParameterizedType)) {
            return typeVariable;
        }
        int n2 = $Gson$Types.indexOf(class_2.getTypeParameters(), typeVariable);
        return ((ParameterizedType)type).getActualTypeArguments()[n2];
    }

    public static WildcardType subtypeOf(Type type) {
        Type[] arrtype = EMPTY_TYPE_ARRAY;
        return new WildcardTypeImpl(new Type[]{type}, arrtype);
    }

    public static WildcardType supertypeOf(Type type) {
        return new WildcardTypeImpl(new Type[]{Object.class}, new Type[]{type});
    }

    public static String typeToString(Type type) {
        if (type instanceof Class) {
            return ((Class)type).getName();
        }
        return type.toString();
    }

    private static final class GenericArrayTypeImpl
    implements GenericArrayType,
    Serializable {
        private static final long serialVersionUID = 0;
        private final Type componentType;

        public GenericArrayTypeImpl(Type type) {
            this.componentType = $Gson$Types.canonicalize(type);
        }

        public boolean equals(Object object) {
            if (object instanceof GenericArrayType && $Gson$Types.equals(this, (GenericArrayType)object)) {
                return true;
            }
            return false;
        }

        @Override
        public Type getGenericComponentType() {
            return this.componentType;
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public String toString() {
            return $Gson$Types.typeToString(this.componentType) + "[]";
        }
    }

    private static final class ParameterizedTypeImpl
    implements ParameterizedType,
    Serializable {
        private static final long serialVersionUID = 0;
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;

        /*
         * Enabled aggressive block sorting
         */
        public /* varargs */ ParameterizedTypeImpl(Type type, Type type2, Type ... arrtype) {
            boolean bl2 = false;
            if (type2 instanceof Class) {
                boolean bl3;
                block4 : {
                    Class class_ = (Class)type2;
                    bl3 = type != null || class_.getEnclosingClass() == null;
                    $Gson$Preconditions.checkArgument(bl3);
                    if (type != null) {
                        bl3 = bl2;
                        if (class_.getEnclosingClass() == null) break block4;
                    }
                    bl3 = true;
                }
                $Gson$Preconditions.checkArgument(bl3);
            }
            type = type == null ? null : $Gson$Types.canonicalize(type);
            this.ownerType = type;
            this.rawType = $Gson$Types.canonicalize(type2);
            this.typeArguments = (Type[])arrtype.clone();
            int n2 = 0;
            while (n2 < this.typeArguments.length) {
                $Gson$Preconditions.checkNotNull(this.typeArguments[n2]);
                $Gson$Types.checkNotPrimitive(this.typeArguments[n2]);
                this.typeArguments[n2] = $Gson$Types.canonicalize(this.typeArguments[n2]);
                ++n2;
            }
        }

        public boolean equals(Object object) {
            if (object instanceof ParameterizedType && $Gson$Types.equals(this, (ParameterizedType)object)) {
                return true;
            }
            return false;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return (Type[])this.typeArguments.clone();
        }

        @Override
        public Type getOwnerType() {
            return this.ownerType;
        }

        @Override
        public Type getRawType() {
            return this.rawType;
        }

        public int hashCode() {
            return Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode() ^ $Gson$Types.hashCodeOrZero(this.ownerType);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder((this.typeArguments.length + 1) * 30);
            stringBuilder.append($Gson$Types.typeToString(this.rawType));
            if (this.typeArguments.length == 0) {
                return stringBuilder.toString();
            }
            stringBuilder.append("<").append($Gson$Types.typeToString(this.typeArguments[0]));
            for (int i2 = 1; i2 < this.typeArguments.length; ++i2) {
                stringBuilder.append(", ").append($Gson$Types.typeToString(this.typeArguments[i2]));
            }
            return stringBuilder.append(">").toString();
        }
    }

    private static final class WildcardTypeImpl
    implements WildcardType,
    Serializable {
        private static final long serialVersionUID = 0;
        private final Type lowerBound;
        private final Type upperBound;

        /*
         * Enabled aggressive block sorting
         */
        public WildcardTypeImpl(Type[] arrtype, Type[] arrtype2) {
            boolean bl2 = true;
            boolean bl3 = arrtype2.length <= 1;
            $Gson$Preconditions.checkArgument(bl3);
            bl3 = arrtype.length == 1;
            $Gson$Preconditions.checkArgument(bl3);
            if (arrtype2.length != 1) {
                $Gson$Preconditions.checkNotNull(arrtype[0]);
                $Gson$Types.checkNotPrimitive(arrtype[0]);
                this.lowerBound = null;
                this.upperBound = $Gson$Types.canonicalize(arrtype[0]);
                return;
            }
            $Gson$Preconditions.checkNotNull(arrtype2[0]);
            $Gson$Types.checkNotPrimitive(arrtype2[0]);
            bl3 = arrtype[0] == Object.class ? bl2 : false;
            $Gson$Preconditions.checkArgument(bl3);
            this.lowerBound = $Gson$Types.canonicalize(arrtype2[0]);
            this.upperBound = Object.class;
        }

        public boolean equals(Object object) {
            if (object instanceof WildcardType && $Gson$Types.equals(this, (WildcardType)object)) {
                return true;
            }
            return false;
        }

        @Override
        public Type[] getLowerBounds() {
            if (this.lowerBound != null) {
                return new Type[]{this.lowerBound};
            }
            return $Gson$Types.EMPTY_TYPE_ARRAY;
        }

        @Override
        public Type[] getUpperBounds() {
            return new Type[]{this.upperBound};
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int hashCode() {
            int n2;
            if (this.lowerBound != null) {
                n2 = this.lowerBound.hashCode() + 31;
                do {
                    return n2 ^ this.upperBound.hashCode() + 31;
                    break;
                } while (true);
            }
            n2 = 1;
            return n2 ^ this.upperBound.hashCode() + 31;
        }

        public String toString() {
            if (this.lowerBound != null) {
                return "? super " + $Gson$Types.typeToString(this.lowerBound);
            }
            if (this.upperBound == Object.class) {
                return "?";
            }
            return "? extends " + $Gson$Types.typeToString(this.upperBound);
        }
    }

}

