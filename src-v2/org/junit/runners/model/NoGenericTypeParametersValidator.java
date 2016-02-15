/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runners.model;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;

class NoGenericTypeParametersValidator {
    private final Method fMethod;

    NoGenericTypeParametersValidator(Method method) {
        this.fMethod = method;
    }

    private void validateNoTypeParameterOnGenericArrayType(GenericArrayType genericArrayType, List<Throwable> list) {
        this.validateNoTypeParameterOnType(genericArrayType.getGenericComponentType(), list);
    }

    private void validateNoTypeParameterOnParameterizedType(ParameterizedType arrtype, List<Throwable> list) {
        arrtype = arrtype.getActualTypeArguments();
        int n2 = arrtype.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            this.validateNoTypeParameterOnType(arrtype[i2], list);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void validateNoTypeParameterOnType(Type type, List<Throwable> list) {
        if (type instanceof TypeVariable) {
            list.add(new Exception("Method " + this.fMethod.getName() + "() contains unresolved type variable " + type));
            return;
        } else {
            if (type instanceof ParameterizedType) {
                this.validateNoTypeParameterOnParameterizedType((ParameterizedType)type, list);
                return;
            }
            if (type instanceof WildcardType) {
                this.validateNoTypeParameterOnWildcardType((WildcardType)type, list);
                return;
            }
            if (!(type instanceof GenericArrayType)) return;
            {
                this.validateNoTypeParameterOnGenericArrayType((GenericArrayType)type, list);
                return;
            }
        }
    }

    private void validateNoTypeParameterOnWildcardType(WildcardType arrtype, List<Throwable> list) {
        int n2;
        Type[] arrtype2 = arrtype.getUpperBounds();
        int n3 = arrtype2.length;
        for (n2 = 0; n2 < n3; ++n2) {
            this.validateNoTypeParameterOnType(arrtype2[n2], list);
        }
        arrtype = arrtype.getLowerBounds();
        n3 = arrtype.length;
        for (n2 = 0; n2 < n3; ++n2) {
            this.validateNoTypeParameterOnType(arrtype[n2], list);
        }
    }

    void validate(List<Throwable> list) {
        Type[] arrtype = this.fMethod.getGenericParameterTypes();
        int n2 = arrtype.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            this.validateNoTypeParameterOnType(arrtype[i2], list);
        }
    }
}

