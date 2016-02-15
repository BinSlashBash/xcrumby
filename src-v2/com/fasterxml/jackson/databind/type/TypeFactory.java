/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.HierarchicType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeModifier;
import com.fasterxml.jackson.databind.type.TypeParser;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.LRUMap;
import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TypeFactory
implements Serializable {
    protected static final SimpleType CORE_TYPE_BOOL;
    protected static final SimpleType CORE_TYPE_INT;
    protected static final SimpleType CORE_TYPE_LONG;
    protected static final SimpleType CORE_TYPE_STRING;
    private static final JavaType[] NO_TYPES;
    protected static final TypeFactory instance;
    private static final long serialVersionUID = 1;
    protected transient HierarchicType _cachedArrayListType;
    protected transient HierarchicType _cachedHashMapType;
    protected final TypeModifier[] _modifiers;
    protected final TypeParser _parser;
    protected final LRUMap<ClassKey, JavaType> _typeCache = new LRUMap(16, 100);

    static {
        NO_TYPES = new JavaType[0];
        instance = new TypeFactory();
        CORE_TYPE_STRING = new SimpleType(String.class);
        CORE_TYPE_BOOL = new SimpleType(Boolean.TYPE);
        CORE_TYPE_INT = new SimpleType(Integer.TYPE);
        CORE_TYPE_LONG = new SimpleType(Long.TYPE);
    }

    private TypeFactory() {
        this._parser = new TypeParser(this);
        this._modifiers = null;
    }

    protected TypeFactory(TypeParser typeParser, TypeModifier[] arrtypeModifier) {
        this._parser = typeParser;
        this._modifiers = arrtypeModifier;
    }

    private JavaType _collectionType(Class<?> class_) {
        JavaType[] arrjavaType = this.findTypeParameters(class_, Collection.class);
        if (arrjavaType == null) {
            return CollectionType.construct(class_, this._unknownType());
        }
        if (arrjavaType.length != 1) {
            throw new IllegalArgumentException("Strange Collection type " + class_.getName() + ": can not determine type parameters");
        }
        return CollectionType.construct(class_, arrjavaType[0]);
    }

    private JavaType _mapType(Class<?> class_) {
        JavaType[] arrjavaType = this.findTypeParameters(class_, Map.class);
        if (arrjavaType == null) {
            return MapType.construct(class_, this._unknownType(), this._unknownType());
        }
        if (arrjavaType.length != 2) {
            throw new IllegalArgumentException("Strange Map type " + class_.getName() + ": can not determine type parameters");
        }
        return MapType.construct(class_, arrjavaType[0], arrjavaType[1]);
    }

    public static TypeFactory defaultInstance() {
        return instance;
    }

    public static Class<?> rawClass(Type type) {
        if (type instanceof Class) {
            return (Class)type;
        }
        return TypeFactory.defaultInstance().constructType(type).getRawClass();
    }

    public static JavaType unknownType() {
        return TypeFactory.defaultInstance()._unknownType();
    }

    protected HierarchicType _arrayListSuperInterfaceChain(HierarchicType hierarchicType) {
        synchronized (this) {
            HierarchicType hierarchicType2;
            if (this._cachedArrayListType == null) {
                hierarchicType2 = hierarchicType.deepCloneWithoutSubtype();
                this._doFindSuperInterfaceChain(hierarchicType2, List.class);
                this._cachedArrayListType = hierarchicType2.getSuperType();
            }
            hierarchicType2 = this._cachedArrayListType.deepCloneWithoutSubtype();
            hierarchicType.setSuperType(hierarchicType2);
            hierarchicType2.setSubType(hierarchicType);
            return hierarchicType;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected JavaType _constructType(Type var1_1, TypeBindings var2_2) {
        if (!(var1_1 instanceof Class)) ** GOTO lbl4
        var5_3 = this._fromClass((Class)var1_1, (TypeBindings)var2_2);
        ** GOTO lbl17
lbl4: // 1 sources:
        if (!(var1_1 instanceof ParameterizedType)) ** GOTO lbl7
        var5_3 = this._fromParamType((ParameterizedType)var1_1, (TypeBindings)var2_2);
        ** GOTO lbl17
lbl7: // 1 sources:
        if (var1_1 instanceof JavaType) {
            return (JavaType)var1_1;
        }
        if (!(var1_1 instanceof GenericArrayType)) ** GOTO lbl12
        var5_3 = this._fromArrayType((GenericArrayType)var1_1, (TypeBindings)var2_2);
        ** GOTO lbl17
lbl12: // 1 sources:
        if (!(var1_1 instanceof TypeVariable)) ** GOTO lbl15
        var5_3 = this._fromVariable((TypeVariable)var1_1, (TypeBindings)var2_2);
        ** GOTO lbl17
lbl15: // 1 sources:
        if (var1_1 instanceof WildcardType) {
            var5_3 = this._fromWildcard((WildcardType)var1_1, (TypeBindings)var2_2);
lbl17: // 5 sources:
            var6_4 = var5_3;
            if (this._modifiers == null) return var6_4;
            var6_4 = var5_3;
            if (var5_3.isContainerType() != false) return var6_4;
            var7_5 = this._modifiers;
            var4_6 = var7_5.length;
            var3_7 = 0;
            do {
                var6_4 = var5_3;
                if (var3_7 >= var4_6) return var6_4;
                var5_3 = var7_5[var3_7].modifyType(var5_3, (Type)var1_1, (TypeBindings)var2_2, this);
                ++var3_7;
            } while (true);
        }
        var2_2 = new StringBuilder().append("Unrecognized Type: ");
        if (var1_1 == null) {
            var1_1 = "[null]";
            throw new IllegalArgumentException(var2_2.append((String)var1_1).toString());
        }
        var1_1 = var1_1.toString();
        throw new IllegalArgumentException(var2_2.append((String)var1_1).toString());
    }

    protected HierarchicType _doFindSuperInterfaceChain(HierarchicType hierarchicType, Class<?> object) {
        Type type = hierarchicType.getRawClass();
        Type[] arrtype = type.getGenericInterfaces();
        if (arrtype != null) {
            int n2 = arrtype.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                HierarchicType hierarchicType2 = this._findSuperInterfaceChain(arrtype[i2], object);
                if (hierarchicType2 == null) continue;
                hierarchicType2.setSubType(hierarchicType);
                hierarchicType.setSuperType(hierarchicType2);
                return hierarchicType;
            }
        }
        if ((type = type.getGenericSuperclass()) != null && (object = this._findSuperInterfaceChain(type, object)) != null) {
            object.setSubType(hierarchicType);
            hierarchicType.setSuperType((HierarchicType)object);
            return hierarchicType;
        }
        return null;
    }

    protected HierarchicType _findSuperClassChain(Type object, Class<?> object2) {
        Type type = (object = new HierarchicType((Type)object)).getRawClass();
        if (type == object2) {
            return object;
        }
        if ((type = type.getGenericSuperclass()) != null && (object2 = this._findSuperClassChain(type, object2)) != null) {
            object2.setSubType((HierarchicType)object);
            object.setSuperType((HierarchicType)object2);
            return object;
        }
        return null;
    }

    protected HierarchicType _findSuperInterfaceChain(Type type, Class<?> class_) {
        HierarchicType hierarchicType = new HierarchicType(type);
        Class class_2 = hierarchicType.getRawClass();
        if (class_2 == class_) {
            return new HierarchicType(type);
        }
        if (class_2 == HashMap.class && class_ == Map.class) {
            return this._hashMapSuperInterfaceChain(hierarchicType);
        }
        if (class_2 == ArrayList.class && class_ == List.class) {
            return this._arrayListSuperInterfaceChain(hierarchicType);
        }
        return this._doFindSuperInterfaceChain(hierarchicType, class_);
    }

    protected HierarchicType _findSuperTypeChain(Class<?> class_, Class<?> class_2) {
        if (class_2.isInterface()) {
            return this._findSuperInterfaceChain(class_, class_2);
        }
        return this._findSuperClassChain(class_, class_2);
    }

    protected JavaType _fromArrayType(GenericArrayType genericArrayType, TypeBindings typeBindings) {
        return ArrayType.construct(this._constructType(genericArrayType.getGenericComponentType(), typeBindings), null, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JavaType _fromClass(Class<?> class_, TypeBindings object) {
        void var1_3;
        JavaType javaType;
        if (class_ == String.class) {
            return CORE_TYPE_STRING;
        }
        if (class_ == Boolean.TYPE) {
            return CORE_TYPE_BOOL;
        }
        if (class_ == Integer.TYPE) {
            return CORE_TYPE_INT;
        }
        if (class_ == Long.TYPE) {
            return CORE_TYPE_LONG;
        }
        ClassKey classKey = new ClassKey(class_);
        JavaType javaType2 = javaType = this._typeCache.get(classKey);
        if (javaType != null) return javaType2;
        if (class_.isArray()) {
            ArrayType arrayType = ArrayType.construct(this._constructType(class_.getComponentType(), null), null, null);
        } else if (class_.isEnum()) {
            SimpleType simpleType = new SimpleType(class_);
        } else if (Map.class.isAssignableFrom(class_)) {
            JavaType javaType3 = this._mapType(class_);
        } else if (Collection.class.isAssignableFrom(class_)) {
            JavaType javaType4 = this._collectionType(class_);
        } else {
            SimpleType simpleType = new SimpleType(class_);
        }
        this._typeCache.put(classKey, (JavaType)var1_3);
        return var1_3;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JavaType _fromParamType(ParameterizedType arrjavaType, TypeBindings typeBindings) {
        Class class_ = (Class)arrjavaType.getRawType();
        Type[] arrtype = arrjavaType.getActualTypeArguments();
        int n2 = arrtype == null ? 0 : arrtype.length;
        if (n2 == 0) {
            arrjavaType = NO_TYPES;
        } else {
            JavaType[] arrjavaType2 = new JavaType[n2];
            int n3 = 0;
            do {
                arrjavaType = arrjavaType2;
                if (n3 >= n2) break;
                arrjavaType2[n3] = this._constructType(arrtype[n3], typeBindings);
                ++n3;
            } while (true);
        }
        if (Map.class.isAssignableFrom(class_)) {
            if ((arrjavaType = this.findTypeParameters(this.constructSimpleType(class_, arrjavaType), Map.class)).length != 2) {
                throw new IllegalArgumentException("Could not find 2 type parameters for Map class " + class_.getName() + " (found " + arrjavaType.length + ")");
            }
            return MapType.construct(class_, arrjavaType[0], arrjavaType[1]);
        }
        if (Collection.class.isAssignableFrom(class_)) {
            if ((arrjavaType = this.findTypeParameters(this.constructSimpleType(class_, arrjavaType), Collection.class)).length != 1) {
                throw new IllegalArgumentException("Could not find 1 type parameter for Collection class " + class_.getName() + " (found " + arrjavaType.length + ")");
            }
            return CollectionType.construct(class_, arrjavaType[0]);
        }
        if (n2 == 0) {
            return new SimpleType(class_);
        }
        return this.constructSimpleType(class_, arrjavaType);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JavaType _fromParameterizedClass(Class<?> class_, List<JavaType> object) {
        if (class_.isArray()) {
            return ArrayType.construct(this._constructType(class_.getComponentType(), null), null, null);
        }
        if (class_.isEnum()) {
            return new SimpleType(class_);
        }
        if (Map.class.isAssignableFrom(class_)) {
            if (object.size() <= 0) return this._mapType(class_);
            JavaType javaType = (JavaType)object.get(0);
            if (object.size() >= 2) {
                object = (JavaType)object.get(1);
                do {
                    return MapType.construct(class_, javaType, (JavaType)object);
                    break;
                } while (true);
            }
            object = this._unknownType();
            return MapType.construct(class_, javaType, (JavaType)object);
        }
        if (Collection.class.isAssignableFrom(class_)) {
            if (object.size() < 1) return this._collectionType(class_);
            return CollectionType.construct(class_, (JavaType)object.get(0));
        }
        if (object.size() != 0) return this.constructSimpleType(class_, object.toArray(new JavaType[object.size()]));
        return new SimpleType(class_);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JavaType _fromVariable(TypeVariable<?> arrtype, TypeBindings typeBindings) {
        JavaType javaType;
        if (typeBindings == null) {
            return this._unknownType();
        }
        String string2 = arrtype.getName();
        JavaType javaType2 = javaType = typeBindings.findType(string2);
        if (javaType != null) return javaType2;
        arrtype = arrtype.getBounds();
        typeBindings._addPlaceholder(string2);
        return this._constructType(arrtype[0], typeBindings);
    }

    protected JavaType _fromWildcard(WildcardType wildcardType, TypeBindings typeBindings) {
        return this._constructType(wildcardType.getUpperBounds()[0], typeBindings);
    }

    protected HierarchicType _hashMapSuperInterfaceChain(HierarchicType hierarchicType) {
        synchronized (this) {
            HierarchicType hierarchicType2;
            if (this._cachedHashMapType == null) {
                hierarchicType2 = hierarchicType.deepCloneWithoutSubtype();
                this._doFindSuperInterfaceChain(hierarchicType2, Map.class);
                this._cachedHashMapType = hierarchicType2.getSuperType();
            }
            hierarchicType2 = this._cachedHashMapType.deepCloneWithoutSubtype();
            hierarchicType.setSuperType(hierarchicType2);
            hierarchicType2.setSubType(hierarchicType);
            return hierarchicType;
        }
    }

    protected JavaType _resolveVariableViaSubTypes(HierarchicType hierarchicType, String object, TypeBindings typeBindings) {
        if (hierarchicType != null && hierarchicType.isGeneric()) {
            TypeVariable<Class<?>>[] arrtypeVariable = hierarchicType.getRawClass().getTypeParameters();
            int n2 = arrtypeVariable.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                if (!object.equals(arrtypeVariable[i2].getName())) continue;
                object = hierarchicType.asGeneric().getActualTypeArguments()[i2];
                if (object instanceof TypeVariable) {
                    return this._resolveVariableViaSubTypes(hierarchicType.getSubType(), ((TypeVariable)object).getName(), typeBindings);
                }
                return this._constructType((Type)object, typeBindings);
            }
        }
        return this._unknownType();
    }

    protected JavaType _unknownType() {
        return new SimpleType(Object.class);
    }

    public void clearCache() {
        this._typeCache.clear();
    }

    public ArrayType constructArrayType(JavaType javaType) {
        return ArrayType.construct(javaType, null, null);
    }

    public ArrayType constructArrayType(Class<?> class_) {
        return ArrayType.construct(this._constructType(class_, null), null, null);
    }

    public CollectionLikeType constructCollectionLikeType(Class<?> class_, JavaType javaType) {
        return CollectionLikeType.construct(class_, javaType);
    }

    public CollectionLikeType constructCollectionLikeType(Class<?> class_, Class<?> class_2) {
        return CollectionLikeType.construct(class_, this.constructType(class_2));
    }

    public CollectionType constructCollectionType(Class<? extends Collection> class_, JavaType javaType) {
        return CollectionType.construct(class_, javaType);
    }

    public CollectionType constructCollectionType(Class<? extends Collection> class_, Class<?> class_2) {
        return CollectionType.construct(class_, this.constructType(class_2));
    }

    public JavaType constructFromCanonical(String string2) throws IllegalArgumentException {
        return this._parser.parse(string2);
    }

    public MapLikeType constructMapLikeType(Class<?> class_, JavaType javaType, JavaType javaType2) {
        return MapLikeType.construct(class_, javaType, javaType2);
    }

    public MapLikeType constructMapLikeType(Class<?> class_, Class<?> class_2, Class<?> class_3) {
        return MapType.construct(class_, this.constructType(class_2), this.constructType(class_3));
    }

    public MapType constructMapType(Class<? extends Map> class_, JavaType javaType, JavaType javaType2) {
        return MapType.construct(class_, javaType, javaType2);
    }

    public MapType constructMapType(Class<? extends Map> class_, Class<?> class_2, Class<?> class_3) {
        return MapType.construct(class_, this.constructType(class_2), this.constructType(class_3));
    }

    public /* varargs */ JavaType constructParametricType(Class<?> class_, JavaType ... arrjavaType) {
        if (class_.isArray()) {
            if (arrjavaType.length != 1) {
                throw new IllegalArgumentException("Need exactly 1 parameter type for arrays (" + class_.getName() + ")");
            }
            return this.constructArrayType(arrjavaType[0]);
        }
        if (Map.class.isAssignableFrom(class_)) {
            if (arrjavaType.length != 2) {
                throw new IllegalArgumentException("Need exactly 2 parameter types for Map types (" + class_.getName() + ")");
            }
            return this.constructMapType(class_, arrjavaType[0], arrjavaType[1]);
        }
        if (Collection.class.isAssignableFrom(class_)) {
            if (arrjavaType.length != 1) {
                throw new IllegalArgumentException("Need exactly 1 parameter type for Collection types (" + class_.getName() + ")");
            }
            return this.constructCollectionType(class_, arrjavaType[0]);
        }
        return this.constructSimpleType(class_, arrjavaType);
    }

    public /* varargs */ JavaType constructParametricType(Class<?> class_, Class<?> ... arrclass) {
        int n2 = arrclass.length;
        JavaType[] arrjavaType = new JavaType[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            arrjavaType[i2] = this._fromClass(arrclass[i2], null);
        }
        return this.constructParametricType(class_, arrjavaType);
    }

    public CollectionLikeType constructRawCollectionLikeType(Class<?> class_) {
        return CollectionLikeType.construct(class_, TypeFactory.unknownType());
    }

    public CollectionType constructRawCollectionType(Class<? extends Collection> class_) {
        return CollectionType.construct(class_, TypeFactory.unknownType());
    }

    public MapLikeType constructRawMapLikeType(Class<?> class_) {
        return MapLikeType.construct(class_, TypeFactory.unknownType(), TypeFactory.unknownType());
    }

    public MapType constructRawMapType(Class<? extends Map> class_) {
        return MapType.construct(class_, TypeFactory.unknownType(), TypeFactory.unknownType());
    }

    public JavaType constructSimpleType(Class<?> class_, JavaType[] arrjavaType) {
        TypeVariable<Class<?>>[] arrtypeVariable = class_.getTypeParameters();
        if (arrtypeVariable.length != arrjavaType.length) {
            throw new IllegalArgumentException("Parameter type mismatch for " + class_.getName() + ": expected " + arrtypeVariable.length + " parameters, was given " + arrjavaType.length);
        }
        String[] arrstring = new String[arrtypeVariable.length];
        int n2 = arrtypeVariable.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrstring[i2] = arrtypeVariable[i2].getName();
        }
        return new SimpleType(class_, arrstring, arrjavaType, null, null, false);
    }

    public JavaType constructSpecializedType(JavaType type, Class<?> class_) {
        if (type.getRawClass() == class_) {
            return type;
        }
        if (type instanceof SimpleType && (class_.isArray() || Map.class.isAssignableFrom(class_) || Collection.class.isAssignableFrom(class_))) {
            if (!type.getRawClass().isAssignableFrom(class_)) {
                throw new IllegalArgumentException("Class " + class_.getClass().getName() + " not subtype of " + type);
            }
            JavaType javaType = this._fromClass(class_, new TypeBindings(this, type.getRawClass()));
            Object t2 = type.getValueHandler();
            class_ = javaType;
            if (t2 != null) {
                class_ = javaType.withValueHandler(t2);
            }
            javaType = type.getTypeHandler();
            type = class_;
            if (javaType != null) {
                type = class_.withTypeHandler(javaType);
            }
            return type;
        }
        return type.narrowBy(class_);
    }

    public JavaType constructType(TypeReference<?> typeReference) {
        return this._constructType(typeReference.getType(), null);
    }

    public JavaType constructType(Type type) {
        return this._constructType(type, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JavaType constructType(Type type, JavaType object) {
        if (object == null) {
            object = null;
            do {
                return this._constructType(type, (TypeBindings)object);
                break;
            } while (true);
        }
        object = new TypeBindings(this, (JavaType)object);
        return this._constructType(type, (TypeBindings)object);
    }

    public JavaType constructType(Type type, TypeBindings typeBindings) {
        return this._constructType(type, typeBindings);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JavaType constructType(Type type, Class<?> object) {
        if (object == null) {
            object = null;
            do {
                return this._constructType(type, (TypeBindings)object);
                break;
            } while (true);
        }
        object = new TypeBindings(this, object);
        return this._constructType(type, (TypeBindings)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JavaType[] findTypeParameters(JavaType javaType, Class<?> arrjavaType) {
        JavaType[] arrjavaType2 = javaType.getRawClass();
        if (arrjavaType2 != arrjavaType) return this.findTypeParameters(arrjavaType2, arrjavaType, new TypeBindings(this, javaType));
        int n2 = javaType.containedTypeCount();
        if (n2 == 0) {
            return null;
        }
        arrjavaType2 = new JavaType[n2];
        int n3 = 0;
        do {
            arrjavaType = arrjavaType2;
            if (n3 >= n2) return arrjavaType;
            arrjavaType2[n3] = javaType.containedType(n3);
            ++n3;
        } while (true);
    }

    public JavaType[] findTypeParameters(Class<?> class_, Class<?> class_2) {
        return this.findTypeParameters(class_, class_2, new TypeBindings(this, class_));
    }

    public JavaType[] findTypeParameters(Class<?> object, Class<?> object2, TypeBindings object3) {
        Type[] arrtype = this._findSuperTypeChain(object, object2);
        if (arrtype == null) {
            throw new IllegalArgumentException("Class " + object.getName() + " is not a subtype of " + object2.getName());
        }
        object = arrtype;
        while (object.getSuperType() != null) {
            object = object.getSuperType();
            TypeVariable<Class<?>>[] arrtypeVariable = object.getRawClass();
            object2 = new TypeBindings(this, arrtypeVariable);
            if (object.isGeneric()) {
                arrtype = object.asGeneric().getActualTypeArguments();
                arrtypeVariable = arrtypeVariable.getTypeParameters();
                int n2 = arrtype.length;
                for (int i2 = 0; i2 < n2; ++i2) {
                    object2.addBinding(arrtypeVariable[i2].getName(), this._constructType(arrtype[i2], (TypeBindings)object3));
                }
            }
            object3 = object2;
        }
        if (!object.isGeneric()) {
            return null;
        }
        return object3.typesAsArray();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JavaType moreSpecificType(JavaType javaType, JavaType javaType2) {
        if (javaType == null) {
            return javaType2;
        }
        JavaType javaType3 = javaType;
        if (javaType2 == null) return javaType3;
        Class class_ = javaType.getRawClass();
        Class class_2 = javaType2.getRawClass();
        javaType3 = javaType;
        if (class_ == class_2) return javaType3;
        javaType3 = javaType;
        if (!class_.isAssignableFrom(class_2)) return javaType3;
        return javaType2;
    }

    public JavaType uncheckedSimpleType(Class<?> class_) {
        return new SimpleType(class_);
    }

    public TypeFactory withModifier(TypeModifier typeModifier) {
        if (typeModifier == null) {
            return new TypeFactory(this._parser, this._modifiers);
        }
        if (this._modifiers == null) {
            return new TypeFactory(this._parser, new TypeModifier[]{typeModifier});
        }
        return new TypeFactory(this._parser, ArrayBuilders.insertInListNoDup(this._modifiers, typeModifier));
    }
}

