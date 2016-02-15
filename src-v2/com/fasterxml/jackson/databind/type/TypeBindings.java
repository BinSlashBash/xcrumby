/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class TypeBindings {
    private static final JavaType[] NO_TYPES = new JavaType[0];
    public static final JavaType UNBOUND = new SimpleType(Object.class);
    protected Map<String, JavaType> _bindings;
    protected final Class<?> _contextClass;
    protected final JavaType _contextType;
    private final TypeBindings _parentBindings;
    protected HashSet<String> _placeholders;
    protected final TypeFactory _typeFactory;

    public TypeBindings(TypeFactory typeFactory, JavaType javaType) {
        this(typeFactory, null, javaType.getRawClass(), javaType);
    }

    private TypeBindings(TypeFactory typeFactory, TypeBindings typeBindings, Class<?> class_, JavaType javaType) {
        this._typeFactory = typeFactory;
        this._parentBindings = typeBindings;
        this._contextClass = class_;
        this._contextType = javaType;
    }

    public TypeBindings(TypeFactory typeFactory, Class<?> class_) {
        this(typeFactory, null, class_, null);
    }

    public void _addPlaceholder(String string2) {
        if (this._placeholders == null) {
            this._placeholders = new HashSet();
        }
        this._placeholders.add(string2);
    }

    protected void _resolve() {
        int n2;
        this._resolveBindings(this._contextClass);
        if (this._contextType != null && (n2 = this._contextType.containedTypeCount()) > 0) {
            for (int i2 = 0; i2 < n2; ++i2) {
                this.addBinding(this._contextType.containedTypeName(i2), (JavaType)this._contextType.containedType(i2));
            }
        }
        if (this._bindings == null) {
            this._bindings = Collections.emptyMap();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void _resolveBindings(Type var1_1) {
        if (var1_1 == null) {
            return;
        }
        if (!(var1_1 instanceof ParameterizedType)) ** GOTO lbl12
        var4_2 = (var1_1 = (ParameterizedType)var1_1).getActualTypeArguments();
        if (var4_2 == null || var4_2.length <= 0) ** GOTO lbl38
        var6_3 = (Class)var1_1.getRawType();
        var5_4 = var6_3.getTypeParameters();
        if (var5_4.length != var4_2.length) {
            throw new IllegalArgumentException("Strange parametrized type (in class " + var6_3.getName() + "): number of type arguments != number of type parameters (" + var4_2.length + " vs " + var5_4.length + ")");
        }
        var3_6 = var4_2.length;
        ** GOTO lbl29
lbl12: // 1 sources:
        if (var1_1 instanceof Class == false) return;
        var5_4 = (TypeVariable<Class<T>>[])var1_1;
        var1_1 = var5_4.getDeclaringClass();
        if (var1_1 != null && !var1_1.isAssignableFrom(var5_4)) {
            this._resolveBindings(var5_4.getDeclaringClass());
        }
        var6_3 = var5_4.getTypeParameters();
        var4_2 = var5_4;
        if (var6_3 == null) ** GOTO lbl39
        var4_2 = var5_4;
        if (var6_3.length <= 0) ** GOTO lbl39
        var1_1 = var4_2 = null;
        if (this._contextType != null) {
            var1_1 = var4_2;
            if (var5_4.isAssignableFrom(this._contextType.getRawClass())) {
                var1_1 = this._typeFactory.findTypeParameters(this._contextType, var5_4);
            }
        }
        var2_5 = 0;
        ** GOTO lbl49
lbl29: // 3 sources:
        for (var2_5 = 0; var2_5 < var3_6; ++var2_5) {
            var6_3 = var5_4[var2_5].getName();
            if (this._bindings == null) {
                this._bindings = new LinkedHashMap<String, JavaType>();
            } else if (this._bindings.containsKey(var6_3)) continue;
            this._addPlaceholder((String)var6_3);
            this._bindings.put((String)var6_3, this._typeFactory._constructType((Type)var4_2[var2_5], this));
        }
lbl38: // 2 sources:
        var4_2 = (Class)var1_1.getRawType();
lbl39: // 4 sources:
        do {
            this._resolveBindings(var4_2.getGenericSuperclass());
            var1_1 = var4_2.getGenericInterfaces();
            var3_6 = var1_1.length;
            var2_5 = 0;
            while (var2_5 < var3_6) {
                this._resolveBindings(var1_1[var2_5]);
                ++var2_5;
            }
            return;
            break;
        } while (true);
lbl49: // 1 sources:
        do {
            var4_2 = var5_4;
            if (var2_5 >= var6_3.length) ** continue;
            var7_7 = var6_3[var2_5];
            var4_2 = var7_7.getName();
            if ((var7_7 = var7_7.getBounds()[0]) == null) ** GOTO lbl64
            if (this._bindings != null) ** GOTO lbl58
            this._bindings = new LinkedHashMap<String, JavaType>();
            ** GOTO lbl-1000
lbl58: // 1 sources:
            if (!this._bindings.containsKey(var4_2)) lbl-1000: // 2 sources:
            {
                this._addPlaceholder((String)var4_2);
                if (var1_1 != null) {
                    this._bindings.put((String)var4_2, (JavaType)var1_1[var2_5]);
                } else {
                    this._bindings.put((String)var4_2, this._typeFactory._constructType((Type)var7_7, this));
                }
            }
lbl64: // 5 sources:
            ++var2_5;
        } while (true);
    }

    public void addBinding(String string2, JavaType javaType) {
        if (this._bindings == null || this._bindings.size() == 0) {
            this._bindings = new LinkedHashMap<String, JavaType>();
        }
        this._bindings.put(string2, javaType);
    }

    public TypeBindings childInstance() {
        return new TypeBindings(this._typeFactory, this, this._contextClass, this._contextType);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JavaType findType(String string2) {
        Object object;
        if (this._bindings == null) {
            this._resolve();
        }
        if ((object = this._bindings.get(string2)) != null) {
            return object;
        }
        if (this._placeholders != null && this._placeholders.contains(string2)) {
            return UNBOUND;
        }
        if (this._parentBindings != null) {
            return this._parentBindings.findType(string2);
        }
        if (this._contextClass != null && this._contextClass.getEnclosingClass() != null && !Modifier.isStatic(this._contextClass.getModifiers())) {
            return UNBOUND;
        }
        if (this._contextClass != null) {
            object = this._contextClass.getName();
            do {
                throw new IllegalArgumentException("Type variable '" + string2 + "' can not be resolved (with context of class " + (String)object + ")");
                break;
            } while (true);
        }
        if (this._contextType != null) {
            object = this._contextType.toString();
            throw new IllegalArgumentException("Type variable '" + string2 + "' can not be resolved (with context of class " + (String)object + ")");
        }
        object = "UNKNOWN";
        throw new IllegalArgumentException("Type variable '" + string2 + "' can not be resolved (with context of class " + (String)object + ")");
    }

    public int getBindingCount() {
        if (this._bindings == null) {
            this._resolve();
        }
        return this._bindings.size();
    }

    public JavaType resolveType(Class<?> class_) {
        return this._typeFactory._constructType(class_, this);
    }

    public JavaType resolveType(Type type) {
        return this._typeFactory._constructType(type, this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        if (this._bindings == null) {
            this._resolve();
        }
        StringBuilder stringBuilder = new StringBuilder("[TypeBindings for ");
        if (this._contextType != null) {
            stringBuilder.append(this._contextType.toString());
        } else {
            stringBuilder.append(this._contextClass.getName());
        }
        stringBuilder.append(": ").append(this._bindings).append("]");
        return stringBuilder.toString();
    }

    public JavaType[] typesAsArray() {
        if (this._bindings == null) {
            this._resolve();
        }
        if (this._bindings.size() == 0) {
            return NO_TYPES;
        }
        return this._bindings.values().toArray(new JavaType[this._bindings.size()]);
    }
}

