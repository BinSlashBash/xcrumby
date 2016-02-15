/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class MinimalClassNameIdResolver
extends ClassNameIdResolver {
    protected final String _basePackageName;
    protected final String _basePackagePrefix;

    protected MinimalClassNameIdResolver(JavaType object, TypeFactory typeFactory) {
        super((JavaType)object, typeFactory);
        object = object.getRawClass().getName();
        int n2 = object.lastIndexOf(46);
        if (n2 < 0) {
            this._basePackageName = "";
            this._basePackagePrefix = ".";
            return;
        }
        this._basePackagePrefix = object.substring(0, n2 + 1);
        this._basePackageName = object.substring(0, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected JavaType _typeFromId(String string2, TypeFactory typeFactory) {
        void var3_6;
        String string3 = string2;
        if (string2.startsWith(".")) {
            StringBuilder stringBuilder = new StringBuilder(string2.length() + this._basePackageName.length());
            if (this._basePackageName.length() == 0) {
                stringBuilder.append(string2.substring(1));
            } else {
                stringBuilder.append(this._basePackageName).append(string2);
            }
            String string4 = stringBuilder.toString();
        }
        return super._typeFromId((String)var3_6, typeFactory);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.MINIMAL_CLASS;
    }

    @Override
    public String idFromValue(Object object) {
        String string2;
        object = string2 = object.getClass().getName();
        if (string2.startsWith(this._basePackagePrefix)) {
            object = string2.substring(this._basePackagePrefix.length() - 1);
        }
        return object;
    }
}

