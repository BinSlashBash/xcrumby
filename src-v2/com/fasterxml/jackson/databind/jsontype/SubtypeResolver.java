/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import java.util.Collection;

public abstract class SubtypeResolver {
    public abstract Collection<NamedType> collectAndResolveSubtypes(AnnotatedClass var1, MapperConfig<?> var2, AnnotationIntrospector var3);

    public abstract Collection<NamedType> collectAndResolveSubtypes(AnnotatedMember var1, MapperConfig<?> var2, AnnotationIntrospector var3, JavaType var4);

    public /* varargs */ abstract void registerSubtypes(NamedType ... var1);

    public /* varargs */ abstract void registerSubtypes(Class<?> ... var1);
}

