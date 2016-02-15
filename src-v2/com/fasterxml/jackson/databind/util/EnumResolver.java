/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnumResolver<T extends Enum<T>>
implements Serializable {
    private static final long serialVersionUID = 1;
    protected final Class<T> _enumClass;
    protected final T[] _enums;
    protected final HashMap<String, T> _enumsById;

    protected EnumResolver(Class<T> class_, T[] arrT, HashMap<String, T> hashMap) {
        this._enumClass = class_;
        this._enums = arrT;
        this._enumsById = hashMap;
    }

    public static <ET extends Enum<ET>> EnumResolver<ET> constructFor(Class<ET> class_, AnnotationIntrospector annotationIntrospector) {
        Enum[] arrenum = (Enum[])class_.getEnumConstants();
        if (arrenum == null) {
            throw new IllegalArgumentException("No enum constants for class " + class_.getName());
        }
        HashMap<String, Enum> hashMap = new HashMap<String, Enum>();
        int n2 = arrenum.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            Enum enum_ = arrenum[i2];
            hashMap.put(annotationIntrospector.findEnumValue(enum_), enum_);
        }
        return new EnumResolver(class_, arrenum, hashMap);
    }

    public static EnumResolver<?> constructUnsafe(Class<?> class_, AnnotationIntrospector annotationIntrospector) {
        return EnumResolver.constructFor(class_, annotationIntrospector);
    }

    public static EnumResolver<?> constructUnsafeUsingMethod(Class<?> class_, Method method) {
        return EnumResolver.constructUsingMethod(class_, method);
    }

    public static EnumResolver<?> constructUnsafeUsingToString(Class<?> class_) {
        return EnumResolver.constructUsingToString(class_);
    }

    public static <ET extends Enum<ET>> EnumResolver<ET> constructUsingMethod(Class<ET> class_, Method method) {
        int n2;
        Enum[] arrenum = (Enum[])class_.getEnumConstants();
        HashMap<String, Enum> hashMap = new HashMap<String, Enum>();
        int n3 = arrenum.length;
        while ((n2 = n3 - 1) >= 0) {
            Object object;
            Enum enum_ = arrenum[n2];
            try {
                object = method.invoke(enum_, new Object[0]);
                n3 = n2;
                if (object == null) continue;
            }
            catch (Exception var0_1) {
                throw new IllegalArgumentException("Failed to access @JsonValue of Enum value " + enum_ + ": " + var0_1.getMessage());
            }
            hashMap.put(object.toString(), enum_);
            n3 = n2;
            continue;
        }
        return new EnumResolver(class_, arrenum, hashMap);
    }

    public static <ET extends Enum<ET>> EnumResolver<ET> constructUsingToString(Class<ET> class_) {
        Enum[] arrenum = (Enum[])class_.getEnumConstants();
        HashMap<String, Enum> hashMap = new HashMap<String, Enum>();
        int n2 = arrenum.length;
        while (--n2 >= 0) {
            Enum enum_ = arrenum[n2];
            hashMap.put(enum_.toString(), enum_);
        }
        return new EnumResolver(class_, arrenum, hashMap);
    }

    public T findEnum(String string2) {
        return (T)((Enum)this._enumsById.get(string2));
    }

    public T getEnum(int n2) {
        if (n2 < 0 || n2 >= this._enums.length) {
            return null;
        }
        return this._enums[n2];
    }

    public Class<T> getEnumClass() {
        return this._enumClass;
    }

    public List<T> getEnums() {
        ArrayList<T> arrayList = new ArrayList<T>(this._enums.length);
        T[] arrT = this._enums;
        int n2 = arrT.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(arrT[i2]);
        }
        return arrayList;
    }

    public int lastValidIndex() {
        return this._enums.length - 1;
    }
}

