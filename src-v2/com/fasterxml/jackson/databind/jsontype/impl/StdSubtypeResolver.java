/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class StdSubtypeResolver
extends SubtypeResolver
implements Serializable {
    private static final long serialVersionUID = 1;
    protected LinkedHashSet<NamedType> _registeredSubtypes;

    /*
     * Enabled aggressive block sorting
     */
    protected void _collectAndResolve(AnnotatedClass object, NamedType namedType, MapperConfig<?> mapperConfig, AnnotationIntrospector annotationIntrospector, HashMap<NamedType, NamedType> hashMap) {
        Object object2;
        Object object3 = namedType;
        if (!namedType.hasName()) {
            object2 = annotationIntrospector.findTypeName((AnnotatedClass)object);
            object3 = namedType;
            if (object2 != null) {
                object3 = new NamedType(namedType.getType(), (String)object2);
            }
        }
        if (hashMap.containsKey(object3)) {
            if (!object3.hasName() || hashMap.get(object3).hasName()) return;
            {
                hashMap.put((NamedType)object3, (NamedType)object3);
            }
            return;
        } else {
            hashMap.put((NamedType)object3, (NamedType)object3);
            if ((object = annotationIntrospector.findSubtypes((Annotated)object)) == null || object.isEmpty()) return;
            {
                object3 = object.iterator();
                while (object3.hasNext()) {
                    namedType = (NamedType)object3.next();
                    object2 = AnnotatedClass.constructWithoutSuperTypes(namedType.getType(), annotationIntrospector, mapperConfig);
                    object = namedType;
                    if (!namedType.hasName()) {
                        object = new NamedType(namedType.getType(), annotationIntrospector.findTypeName((AnnotatedClass)object2));
                    }
                    this._collectAndResolve((AnnotatedClass)object2, (NamedType)object, mapperConfig, annotationIntrospector, hashMap);
                }
            }
        }
    }

    @Override
    public Collection<NamedType> collectAndResolveSubtypes(AnnotatedClass annotatedClass, MapperConfig<?> mapperConfig, AnnotationIntrospector annotationIntrospector) {
        HashMap<NamedType, NamedType> hashMap = new HashMap<NamedType, NamedType>();
        if (this._registeredSubtypes != null) {
            Class class_ = annotatedClass.getRawType();
            for (NamedType namedType : this._registeredSubtypes) {
                if (!class_.isAssignableFrom(namedType.getType())) continue;
                this._collectAndResolve(AnnotatedClass.constructWithoutSuperTypes(namedType.getType(), annotationIntrospector, mapperConfig), namedType, mapperConfig, annotationIntrospector, hashMap);
            }
        }
        this._collectAndResolve(annotatedClass, new NamedType(annotatedClass.getRawType(), null), mapperConfig, annotationIntrospector, hashMap);
        return new ArrayList<NamedType>(hashMap.values());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Collection<NamedType> collectAndResolveSubtypes(AnnotatedMember object, MapperConfig<?> mapperConfig, AnnotationIntrospector annotationIntrospector, JavaType javaType) {
        void var4_6;
        if (javaType == null) {
            Class class_ = object.getRawType();
        } else {
            Class class_ = javaType.getRawClass();
        }
        HashMap<NamedType, NamedType> hashMap = new HashMap<NamedType, NamedType>();
        if (this._registeredSubtypes != null) {
            for (NamedType namedType : this._registeredSubtypes) {
                if (!var4_6.isAssignableFrom(namedType.getType())) continue;
                this._collectAndResolve(AnnotatedClass.constructWithoutSuperTypes(namedType.getType(), annotationIntrospector, mapperConfig), namedType, mapperConfig, annotationIntrospector, hashMap);
            }
        }
        if ((object = annotationIntrospector.findSubtypes((Annotated)object)) != null) {
            object = object.iterator();
            while (object.hasNext()) {
                NamedType namedType = (NamedType)object.next();
                this._collectAndResolve(AnnotatedClass.constructWithoutSuperTypes(namedType.getType(), annotationIntrospector, mapperConfig), namedType, mapperConfig, annotationIntrospector, hashMap);
            }
        }
        object = new NamedType(var4_6, null);
        this._collectAndResolve(AnnotatedClass.constructWithoutSuperTypes(var4_6, annotationIntrospector, mapperConfig), (NamedType)object, mapperConfig, annotationIntrospector, hashMap);
        return new ArrayList<NamedType>(hashMap.values());
    }

    @Override
    public /* varargs */ void registerSubtypes(NamedType ... arrnamedType) {
        if (this._registeredSubtypes == null) {
            this._registeredSubtypes = new LinkedHashSet();
        }
        for (NamedType namedType : arrnamedType) {
            this._registeredSubtypes.add(namedType);
        }
    }

    @Override
    public /* varargs */ void registerSubtypes(Class<?> ... arrclass) {
        NamedType[] arrnamedType = new NamedType[arrclass.length];
        int n2 = arrclass.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrnamedType[i2] = new NamedType(arrclass[i2]);
        }
        this.registerSubtypes(arrnamedType);
    }
}

