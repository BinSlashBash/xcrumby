/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AnnotationIntrospectorPair
extends AnnotationIntrospector
implements Serializable {
    private static final long serialVersionUID = 1;
    protected final AnnotationIntrospector _primary;
    protected final AnnotationIntrospector _secondary;

    public AnnotationIntrospectorPair(AnnotationIntrospector annotationIntrospector, AnnotationIntrospector annotationIntrospector2) {
        this._primary = annotationIntrospector;
        this._secondary = annotationIntrospector2;
    }

    public static AnnotationIntrospector create(AnnotationIntrospector annotationIntrospector, AnnotationIntrospector annotationIntrospector2) {
        if (annotationIntrospector == null) {
            return annotationIntrospector2;
        }
        if (annotationIntrospector2 == null) {
            return annotationIntrospector;
        }
        return new AnnotationIntrospectorPair(annotationIntrospector, annotationIntrospector2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected boolean _isExplicitClassOrOb(Object object, Class<?> class_) {
        boolean bl2 = true;
        if (object == null) {
            return false;
        }
        boolean bl3 = bl2;
        if (!(object instanceof Class)) return bl3;
        if ((object = (Class)object) == class_) return false;
        bl3 = bl2;
        if (!ClassUtil.isBogusClass(object)) return bl3;
        return false;
    }

    @Override
    public Collection<AnnotationIntrospector> allIntrospectors() {
        return this.allIntrospectors(new ArrayList<AnnotationIntrospector>());
    }

    @Override
    public Collection<AnnotationIntrospector> allIntrospectors(Collection<AnnotationIntrospector> collection) {
        this._primary.allIntrospectors(collection);
        this._secondary.allIntrospectors(collection);
        return collection;
    }

    @Override
    public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass annotatedClass, VisibilityChecker<?> visibilityChecker) {
        visibilityChecker = this._secondary.findAutoDetectVisibility(annotatedClass, visibilityChecker);
        return this._primary.findAutoDetectVisibility(annotatedClass, visibilityChecker);
    }

    @Override
    public Object findContentDeserializer(Annotated annotated) {
        Object object = this._primary.findContentDeserializer(annotated);
        if (this._isExplicitClassOrOb(object, JsonDeserializer.None.class)) {
            return object;
        }
        return this._secondary.findContentDeserializer(annotated);
    }

    @Override
    public Object findContentSerializer(Annotated annotated) {
        Object object = this._primary.findContentSerializer(annotated);
        if (this._isExplicitClassOrOb(object, JsonSerializer.None.class)) {
            return object;
        }
        return this._secondary.findContentSerializer(annotated);
    }

    @Override
    public Object findDeserializationContentConverter(AnnotatedMember annotatedMember) {
        Object object;
        Object object2 = object = this._primary.findDeserializationContentConverter(annotatedMember);
        if (object == null) {
            object2 = this._secondary.findDeserializationContentConverter(annotatedMember);
        }
        return object2;
    }

    @Override
    public Class<?> findDeserializationContentType(Annotated annotated, JavaType javaType) {
        Class class_;
        Class class_2 = class_ = this._primary.findDeserializationContentType(annotated, javaType);
        if (class_ == null) {
            class_2 = this._secondary.findDeserializationContentType(annotated, javaType);
        }
        return class_2;
    }

    @Override
    public Object findDeserializationConverter(Annotated annotated) {
        Object object;
        Object object2 = object = this._primary.findDeserializationConverter(annotated);
        if (object == null) {
            object2 = this._secondary.findDeserializationConverter(annotated);
        }
        return object2;
    }

    @Override
    public Class<?> findDeserializationKeyType(Annotated annotated, JavaType javaType) {
        Class class_;
        Class class_2 = class_ = this._primary.findDeserializationKeyType(annotated, javaType);
        if (class_ == null) {
            class_2 = this._secondary.findDeserializationKeyType(annotated, javaType);
        }
        return class_2;
    }

    @Override
    public Class<?> findDeserializationType(Annotated annotated, JavaType javaType) {
        Class class_ = this._primary.findDeserializationType(annotated, javaType);
        if (class_ != null) {
            return class_;
        }
        return this._secondary.findDeserializationType(annotated, javaType);
    }

    @Override
    public Object findDeserializer(Annotated annotated) {
        Object object = this._primary.findDeserializer(annotated);
        if (this._isExplicitClassOrOb(object, JsonDeserializer.None.class)) {
            return object;
        }
        return this._secondary.findDeserializer(annotated);
    }

    @Override
    public String findEnumValue(Enum<?> enum_) {
        String string2;
        String string3 = string2 = this._primary.findEnumValue(enum_);
        if (string2 == null) {
            string3 = this._secondary.findEnumValue(enum_);
        }
        return string3;
    }

    @Override
    public Object findFilterId(Annotated annotated) {
        Object object;
        Object object2 = object = this._primary.findFilterId(annotated);
        if (object == null) {
            object2 = this._secondary.findFilterId(annotated);
        }
        return object2;
    }

    @Deprecated
    @Override
    public Object findFilterId(AnnotatedClass annotatedClass) {
        Object object;
        Object object2 = object = this._primary.findFilterId(annotatedClass);
        if (object == null) {
            object2 = this._secondary.findFilterId(annotatedClass);
        }
        return object2;
    }

    @Override
    public JsonFormat.Value findFormat(Annotated annotated) {
        JsonFormat.Value value;
        JsonFormat.Value value2 = value = this._primary.findFormat(annotated);
        if (value == null) {
            value2 = this._secondary.findFormat(annotated);
        }
        return value2;
    }

    @Override
    public Boolean findIgnoreUnknownProperties(AnnotatedClass annotatedClass) {
        Boolean bl2;
        Boolean bl3 = bl2 = this._primary.findIgnoreUnknownProperties(annotatedClass);
        if (bl2 == null) {
            bl3 = this._secondary.findIgnoreUnknownProperties(annotatedClass);
        }
        return bl3;
    }

    @Override
    public String findImplicitPropertyName(AnnotatedMember annotatedMember) {
        String string2;
        String string3 = string2 = this._primary.findImplicitPropertyName(annotatedMember);
        if (string2 == null) {
            string3 = this._secondary.findImplicitPropertyName(annotatedMember);
        }
        return string3;
    }

    @Override
    public Object findInjectableValueId(AnnotatedMember annotatedMember) {
        Object object;
        Object object2 = object = this._primary.findInjectableValueId(annotatedMember);
        if (object == null) {
            object2 = this._secondary.findInjectableValueId(annotatedMember);
        }
        return object2;
    }

    @Override
    public Object findKeyDeserializer(Annotated annotated) {
        Object object = this._primary.findKeyDeserializer(annotated);
        if (this._isExplicitClassOrOb(object, KeyDeserializer.None.class)) {
            return object;
        }
        return this._secondary.findKeyDeserializer(annotated);
    }

    @Override
    public Object findKeySerializer(Annotated annotated) {
        Object object = this._primary.findKeySerializer(annotated);
        if (this._isExplicitClassOrOb(object, JsonSerializer.None.class)) {
            return object;
        }
        return this._secondary.findKeySerializer(annotated);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public PropertyName findNameForDeserialization(Annotated object) {
        PropertyName propertyName = this._primary.findNameForDeserialization((Annotated)object);
        if (propertyName == null) {
            return this._secondary.findNameForDeserialization((Annotated)object);
        }
        PropertyName propertyName2 = propertyName;
        if (propertyName != PropertyName.USE_DEFAULT) return propertyName2;
        object = this._secondary.findNameForDeserialization((Annotated)object);
        propertyName2 = propertyName;
        if (object == null) return propertyName2;
        return object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public PropertyName findNameForSerialization(Annotated object) {
        PropertyName propertyName = this._primary.findNameForSerialization((Annotated)object);
        if (propertyName == null) {
            return this._secondary.findNameForSerialization((Annotated)object);
        }
        PropertyName propertyName2 = propertyName;
        if (propertyName != PropertyName.USE_DEFAULT) return propertyName2;
        object = this._secondary.findNameForSerialization((Annotated)object);
        propertyName2 = propertyName;
        if (object == null) return propertyName2;
        return object;
    }

    @Override
    public Object findNamingStrategy(AnnotatedClass annotatedClass) {
        Object object;
        Object object2 = object = this._primary.findNamingStrategy(annotatedClass);
        if (object == null) {
            object2 = this._secondary.findNamingStrategy(annotatedClass);
        }
        return object2;
    }

    @Override
    public Object findNullSerializer(Annotated annotated) {
        Object object = this._primary.findNullSerializer(annotated);
        if (this._isExplicitClassOrOb(object, JsonSerializer.None.class)) {
            return object;
        }
        return this._secondary.findNullSerializer(annotated);
    }

    @Override
    public ObjectIdInfo findObjectIdInfo(Annotated annotated) {
        ObjectIdInfo objectIdInfo;
        ObjectIdInfo objectIdInfo2 = objectIdInfo = this._primary.findObjectIdInfo(annotated);
        if (objectIdInfo == null) {
            objectIdInfo2 = this._secondary.findObjectIdInfo(annotated);
        }
        return objectIdInfo2;
    }

    @Override
    public ObjectIdInfo findObjectReferenceInfo(Annotated annotated, ObjectIdInfo objectIdInfo) {
        objectIdInfo = this._secondary.findObjectReferenceInfo(annotated, objectIdInfo);
        return this._primary.findObjectReferenceInfo(annotated, objectIdInfo);
    }

    @Override
    public Class<?> findPOJOBuilder(AnnotatedClass annotatedClass) {
        Class class_;
        Class class_2 = class_ = this._primary.findPOJOBuilder(annotatedClass);
        if (class_ == null) {
            class_2 = this._secondary.findPOJOBuilder(annotatedClass);
        }
        return class_2;
    }

    @Override
    public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass annotatedClass) {
        JsonPOJOBuilder.Value value;
        JsonPOJOBuilder.Value value2 = value = this._primary.findPOJOBuilderConfig(annotatedClass);
        if (value == null) {
            value2 = this._secondary.findPOJOBuilderConfig(annotatedClass);
        }
        return value2;
    }

    @Override
    public String[] findPropertiesToIgnore(Annotated annotated) {
        String[] arrstring;
        String[] arrstring2 = arrstring = this._primary.findPropertiesToIgnore(annotated);
        if (arrstring == null) {
            arrstring2 = this._secondary.findPropertiesToIgnore(annotated);
        }
        return arrstring2;
    }

    @Override
    public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        TypeResolverBuilder typeResolverBuilder;
        TypeResolverBuilder typeResolverBuilder2 = typeResolverBuilder = this._primary.findPropertyContentTypeResolver(mapperConfig, annotatedMember, javaType);
        if (typeResolverBuilder == null) {
            typeResolverBuilder2 = this._secondary.findPropertyContentTypeResolver(mapperConfig, annotatedMember, javaType);
        }
        return typeResolverBuilder2;
    }

    @Override
    public String findPropertyDescription(Annotated annotated) {
        String string2;
        String string3 = string2 = this._primary.findPropertyDescription(annotated);
        if (string2 == null) {
            string3 = this._secondary.findPropertyDescription(annotated);
        }
        return string3;
    }

    @Override
    public Integer findPropertyIndex(Annotated annotated) {
        Integer n2;
        Integer n3 = n2 = this._primary.findPropertyIndex(annotated);
        if (n2 == null) {
            n3 = this._secondary.findPropertyIndex(annotated);
        }
        return n3;
    }

    @Override
    public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        TypeResolverBuilder typeResolverBuilder;
        TypeResolverBuilder typeResolverBuilder2 = typeResolverBuilder = this._primary.findPropertyTypeResolver(mapperConfig, annotatedMember, javaType);
        if (typeResolverBuilder == null) {
            typeResolverBuilder2 = this._secondary.findPropertyTypeResolver(mapperConfig, annotatedMember, javaType);
        }
        return typeResolverBuilder2;
    }

    @Override
    public AnnotationIntrospector.ReferenceProperty findReferenceType(AnnotatedMember annotatedMember) {
        AnnotationIntrospector.ReferenceProperty referenceProperty;
        AnnotationIntrospector.ReferenceProperty referenceProperty2 = referenceProperty = this._primary.findReferenceType(annotatedMember);
        if (referenceProperty == null) {
            referenceProperty2 = this._secondary.findReferenceType(annotatedMember);
        }
        return referenceProperty2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public PropertyName findRootName(AnnotatedClass object) {
        PropertyName propertyName = this._primary.findRootName((AnnotatedClass)object);
        if (propertyName == null) {
            return this._secondary.findRootName((AnnotatedClass)object);
        }
        PropertyName propertyName2 = propertyName;
        if (propertyName.hasSimpleName()) return propertyName2;
        object = this._secondary.findRootName((AnnotatedClass)object);
        propertyName2 = propertyName;
        if (object == null) return propertyName2;
        return object;
    }

    @Override
    public Object findSerializationContentConverter(AnnotatedMember annotatedMember) {
        Object object;
        Object object2 = object = this._primary.findSerializationContentConverter(annotatedMember);
        if (object == null) {
            object2 = this._secondary.findSerializationContentConverter(annotatedMember);
        }
        return object2;
    }

    @Override
    public Class<?> findSerializationContentType(Annotated annotated, JavaType javaType) {
        Class class_;
        Class class_2 = class_ = this._primary.findSerializationContentType(annotated, javaType);
        if (class_ == null) {
            class_2 = this._secondary.findSerializationContentType(annotated, javaType);
        }
        return class_2;
    }

    @Override
    public Object findSerializationConverter(Annotated annotated) {
        Object object;
        Object object2 = object = this._primary.findSerializationConverter(annotated);
        if (object == null) {
            object2 = this._secondary.findSerializationConverter(annotated);
        }
        return object2;
    }

    @Override
    public JsonInclude.Include findSerializationInclusion(Annotated annotated, JsonInclude.Include include) {
        include = this._secondary.findSerializationInclusion(annotated, include);
        return this._primary.findSerializationInclusion(annotated, include);
    }

    @Override
    public Class<?> findSerializationKeyType(Annotated annotated, JavaType javaType) {
        Class class_;
        Class class_2 = class_ = this._primary.findSerializationKeyType(annotated, javaType);
        if (class_ == null) {
            class_2 = this._secondary.findSerializationKeyType(annotated, javaType);
        }
        return class_2;
    }

    @Override
    public String[] findSerializationPropertyOrder(AnnotatedClass annotatedClass) {
        String[] arrstring;
        String[] arrstring2 = arrstring = this._primary.findSerializationPropertyOrder(annotatedClass);
        if (arrstring == null) {
            arrstring2 = this._secondary.findSerializationPropertyOrder(annotatedClass);
        }
        return arrstring2;
    }

    @Override
    public Boolean findSerializationSortAlphabetically(Annotated annotated) {
        Boolean bl2;
        Boolean bl3 = bl2 = this._primary.findSerializationSortAlphabetically(annotated);
        if (bl2 == null) {
            bl3 = this._secondary.findSerializationSortAlphabetically(annotated);
        }
        return bl3;
    }

    @Deprecated
    @Override
    public Boolean findSerializationSortAlphabetically(AnnotatedClass annotatedClass) {
        Boolean bl2;
        Boolean bl3 = bl2 = this._primary.findSerializationSortAlphabetically(annotatedClass);
        if (bl2 == null) {
            bl3 = this._secondary.findSerializationSortAlphabetically(annotatedClass);
        }
        return bl3;
    }

    @Override
    public Class<?> findSerializationType(Annotated annotated) {
        Class class_;
        Class class_2 = class_ = this._primary.findSerializationType(annotated);
        if (class_ == null) {
            class_2 = this._secondary.findSerializationType(annotated);
        }
        return class_2;
    }

    @Override
    public JsonSerialize.Typing findSerializationTyping(Annotated annotated) {
        JsonSerialize.Typing typing;
        JsonSerialize.Typing typing2 = typing = this._primary.findSerializationTyping(annotated);
        if (typing == null) {
            typing2 = this._secondary.findSerializationTyping(annotated);
        }
        return typing2;
    }

    @Override
    public Object findSerializer(Annotated annotated) {
        Object object = this._primary.findSerializer(annotated);
        if (this._isExplicitClassOrOb(object, JsonSerializer.None.class)) {
            return object;
        }
        return this._secondary.findSerializer(annotated);
    }

    @Override
    public List<NamedType> findSubtypes(Annotated object) {
        List<NamedType> list = this._primary.findSubtypes((Annotated)object);
        object = this._secondary.findSubtypes((Annotated)object);
        if (list == null || list.isEmpty()) {
            return object;
        }
        if (object == null || object.isEmpty()) {
            return list;
        }
        ArrayList<NamedType> arrayList = new ArrayList<NamedType>(list.size() + object.size());
        arrayList.addAll(list);
        arrayList.addAll((Collection<NamedType>)object);
        return arrayList;
    }

    @Override
    public String findTypeName(AnnotatedClass annotatedClass) {
        String string2;
        block2 : {
            String string3 = this._primary.findTypeName(annotatedClass);
            if (string3 != null) {
                string2 = string3;
                if (string3.length() != 0) break block2;
            }
            string2 = this._secondary.findTypeName(annotatedClass);
        }
        return string2;
    }

    @Override
    public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass, JavaType javaType) {
        TypeResolverBuilder typeResolverBuilder;
        TypeResolverBuilder typeResolverBuilder2 = typeResolverBuilder = this._primary.findTypeResolver(mapperConfig, annotatedClass, javaType);
        if (typeResolverBuilder == null) {
            typeResolverBuilder2 = this._secondary.findTypeResolver(mapperConfig, annotatedClass, javaType);
        }
        return typeResolverBuilder2;
    }

    @Override
    public NameTransformer findUnwrappingNameTransformer(AnnotatedMember annotatedMember) {
        NameTransformer nameTransformer;
        NameTransformer nameTransformer2 = nameTransformer = this._primary.findUnwrappingNameTransformer(annotatedMember);
        if (nameTransformer == null) {
            nameTransformer2 = this._secondary.findUnwrappingNameTransformer(annotatedMember);
        }
        return nameTransformer2;
    }

    @Override
    public Object findValueInstantiator(AnnotatedClass annotatedClass) {
        Object object;
        Object object2 = object = this._primary.findValueInstantiator(annotatedClass);
        if (object == null) {
            object2 = this._secondary.findValueInstantiator(annotatedClass);
        }
        return object2;
    }

    @Override
    public Class<?>[] findViews(Annotated annotated) {
        Class<?>[] arrclass;
        Class<?>[] arrclass2 = arrclass = this._primary.findViews(annotated);
        if (arrclass == null) {
            arrclass2 = this._secondary.findViews(annotated);
        }
        return arrclass2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public PropertyName findWrapperName(Annotated object) {
        PropertyName propertyName = this._primary.findWrapperName((Annotated)object);
        if (propertyName == null) {
            return this._secondary.findWrapperName((Annotated)object);
        }
        PropertyName propertyName2 = propertyName;
        if (propertyName != PropertyName.USE_DEFAULT) return propertyName2;
        object = this._secondary.findWrapperName((Annotated)object);
        propertyName2 = propertyName;
        if (object == null) return propertyName2;
        return object;
    }

    @Override
    public boolean hasAnyGetterAnnotation(AnnotatedMethod annotatedMethod) {
        if (this._primary.hasAnyGetterAnnotation(annotatedMethod) || this._secondary.hasAnyGetterAnnotation(annotatedMethod)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasAnySetterAnnotation(AnnotatedMethod annotatedMethod) {
        if (this._primary.hasAnySetterAnnotation(annotatedMethod) || this._secondary.hasAnySetterAnnotation(annotatedMethod)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasAsValueAnnotation(AnnotatedMethod annotatedMethod) {
        if (this._primary.hasAsValueAnnotation(annotatedMethod) || this._secondary.hasAsValueAnnotation(annotatedMethod)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasCreatorAnnotation(Annotated annotated) {
        if (this._primary.hasCreatorAnnotation(annotated) || this._secondary.hasCreatorAnnotation(annotated)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasIgnoreMarker(AnnotatedMember annotatedMember) {
        if (this._primary.hasIgnoreMarker(annotatedMember) || this._secondary.hasIgnoreMarker(annotatedMember)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean hasRequiredMarker(AnnotatedMember annotatedMember) {
        Boolean bl2;
        Boolean bl3 = bl2 = this._primary.hasRequiredMarker(annotatedMember);
        if (bl2 == null) {
            bl3 = this._secondary.hasRequiredMarker(annotatedMember);
        }
        return bl3;
    }

    @Override
    public boolean isAnnotationBundle(Annotation annotation) {
        if (this._primary.isAnnotationBundle(annotation) || this._secondary.isAnnotationBundle(annotation)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean isIgnorableType(AnnotatedClass annotatedClass) {
        Boolean bl2;
        Boolean bl3 = bl2 = this._primary.isIgnorableType(annotatedClass);
        if (bl2 == null) {
            bl3 = this._secondary.isIgnorableType(annotatedClass);
        }
        return bl3;
    }

    @Override
    public Boolean isTypeId(AnnotatedMember annotatedMember) {
        Boolean bl2;
        Boolean bl3 = bl2 = this._primary.isTypeId(annotatedMember);
        if (bl2 == null) {
            bl3 = this._secondary.isTypeId(annotatedMember);
        }
        return bl3;
    }

    @Override
    public Version version() {
        return this._primary.version();
    }
}

