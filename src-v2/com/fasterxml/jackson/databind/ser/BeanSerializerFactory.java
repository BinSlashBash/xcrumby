/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.AnyGetterWriter;
import com.fasterxml.jackson.databind.ser.BasicSerializerFactory;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.PropertyBuilder;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.impl.FilteredBeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.impl.PropertyBasedObjectIdGenerator;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class BeanSerializerFactory
extends BasicSerializerFactory
implements Serializable {
    public static final BeanSerializerFactory instance = new BeanSerializerFactory(null);
    private static final long serialVersionUID = 1;

    protected BeanSerializerFactory(SerializerFactoryConfig serializerFactoryConfig) {
        super(serializerFactoryConfig);
    }

    protected BeanPropertyWriter _constructWriter(SerializerProvider serializerProvider, BeanPropertyDefinition beanPropertyDefinition, TypeBindings object, PropertyBuilder propertyBuilder, boolean bl2, AnnotatedMember annotatedMember) throws JsonMappingException {
        JsonSerializer jsonSerializer = beanPropertyDefinition.getFullName();
        if (serializerProvider.canOverrideAccessModifiers()) {
            annotatedMember.fixAccess();
        }
        JavaType javaType = annotatedMember.getType((TypeBindings)object);
        object = new BeanProperty.Std((PropertyName)((Object)jsonSerializer), javaType, beanPropertyDefinition.getWrapperName(), propertyBuilder.getClassAnnotations(), annotatedMember, beanPropertyDefinition.getMetadata());
        jsonSerializer = this.findSerializerFromAnnotation(serializerProvider, annotatedMember);
        if (jsonSerializer instanceof ResolvableSerializer) {
            ((ResolvableSerializer)((Object)jsonSerializer)).resolve(serializerProvider);
        }
        jsonSerializer = serializerProvider.handlePrimaryContextualization(jsonSerializer, (BeanProperty)object);
        object = null;
        if (ClassUtil.isCollectionMapOrArray(javaType.getRawClass()) || javaType.isCollectionLikeType() || javaType.isMapLikeType()) {
            object = this.findPropertyContentTypeSerializer(javaType, serializerProvider.getConfig(), annotatedMember);
        }
        return propertyBuilder.buildWriter(serializerProvider, beanPropertyDefinition, javaType, jsonSerializer, this.findPropertyTypeSerializer(javaType, serializerProvider.getConfig(), annotatedMember), (TypeSerializer)object, annotatedMember, bl2);
    }

    protected JsonSerializer<?> _createSerializer2(SerializerProvider jsonSerializer, JavaType object, BeanDescription beanDescription, boolean bl2) throws JsonMappingException {
        JsonSerializer jsonSerializer2;
        SerializationConfig serializationConfig;
        boolean bl3;
        JsonSerializer jsonSerializer3;
        block13 : {
            jsonSerializer3 = this.findSerializerByAnnotations((SerializerProvider)((Object)jsonSerializer), (JavaType)object, beanDescription);
            if (jsonSerializer3 != null) {
                return jsonSerializer3;
            }
            serializationConfig = jsonSerializer.getConfig();
            if (object.isContainerType()) {
                bl3 = bl2;
                if (!bl2) {
                    bl3 = this.usesStaticTyping(serializationConfig, beanDescription, null);
                }
                jsonSerializer3 = jsonSerializer2 = this.buildContainerSerializer((SerializerProvider)((Object)jsonSerializer), (JavaType)object, beanDescription, bl3);
                if (jsonSerializer2 != null) {
                    return jsonSerializer2;
                }
            } else {
                Iterator<Serializers> iterator = this.customSerializers().iterator();
                do {
                    bl3 = bl2;
                    if (!iterator.hasNext()) break block13;
                    jsonSerializer3 = jsonSerializer2 = iterator.next().findSerializer(serializationConfig, (JavaType)object, beanDescription);
                } while (jsonSerializer2 == null);
                bl3 = bl2;
                jsonSerializer3 = jsonSerializer2;
            }
        }
        jsonSerializer2 = jsonSerializer3;
        if (jsonSerializer3 == null) {
            jsonSerializer2 = jsonSerializer3 = this.findSerializerByLookup((JavaType)object, serializationConfig, beanDescription, bl3);
            if (jsonSerializer3 == null) {
                jsonSerializer2 = jsonSerializer3 = this.findSerializerByPrimaryType((SerializerProvider)((Object)jsonSerializer), (JavaType)object, beanDescription, bl3);
                if (jsonSerializer3 == null) {
                    jsonSerializer2 = jsonSerializer = this.findBeanSerializer((SerializerProvider)((Object)jsonSerializer), (JavaType)object, beanDescription);
                    if (jsonSerializer == null) {
                        jsonSerializer2 = this.findSerializerByAddonType(serializationConfig, (JavaType)object, beanDescription, bl3);
                    }
                }
            }
        }
        jsonSerializer = jsonSerializer2;
        if (jsonSerializer2 != null) {
            jsonSerializer = jsonSerializer2;
            if (this._factoryConfig.hasSerializerModifiers()) {
                object = this._factoryConfig.serializerModifiers().iterator();
                do {
                    jsonSerializer = jsonSerializer2;
                    if (!object.hasNext()) break;
                    jsonSerializer2 = ((BeanSerializerModifier)object.next()).modifySerializer(serializationConfig, beanDescription, jsonSerializer2);
                } while (true);
            }
        }
        return jsonSerializer;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JsonSerializer<Object> constructBeanSerializer(SerializerProvider object, BeanDescription beanDescription) throws JsonMappingException {
        Iterator<BeanSerializerModifier> iterator;
        Object object2;
        if (beanDescription.getBeanClass() == Object.class) {
            return object.getUnknownTypeSerializer(Object.class);
        }
        SerializationConfig serializationConfig = object.getConfig();
        Object object3 = this.constructBeanSerializerBuilder(beanDescription);
        object3.setConfig(serializationConfig);
        Object object4 = object2 = this.findBeanProperties((SerializerProvider)object, beanDescription, (BeanSerializerBuilder)object3);
        if (object2 == null) {
            object4 = new ArrayList<BeanPropertyWriter>();
        }
        object2 = object4;
        if (this._factoryConfig.hasSerializerModifiers()) {
            iterator = this._factoryConfig.serializerModifiers().iterator();
            do {
                object2 = object4;
                if (!iterator.hasNext()) break;
                object4 = iterator.next().changeProperties(serializationConfig, beanDescription, (List<BeanPropertyWriter>)object4);
            } while (true);
        }
        object2 = object4 = this.filterBeanProperties(serializationConfig, beanDescription, (List<BeanPropertyWriter>)object2);
        if (this._factoryConfig.hasSerializerModifiers()) {
            iterator = this._factoryConfig.serializerModifiers().iterator();
            do {
                object2 = object4;
                if (!iterator.hasNext()) break;
                object4 = iterator.next().orderProperties(serializationConfig, beanDescription, (List<BeanPropertyWriter>)object4);
            } while (true);
        }
        object3.setObjectIdWriter(this.constructObjectIdHandler((SerializerProvider)object, beanDescription, (List<BeanPropertyWriter>)object2));
        object3.setProperties((List<BeanPropertyWriter>)object2);
        object3.setFilterId(this.findFilterId(serializationConfig, beanDescription));
        object = beanDescription.findAnyGetter();
        if (object != null) {
            if (serializationConfig.canOverrideAccessModifiers()) {
                object.fixAccess();
            }
            object2 = object.getType(beanDescription.bindingsForBeanType());
            boolean bl2 = serializationConfig.isEnabled(MapperFeature.USE_STATIC_TYPING);
            object4 = object2.getContentType();
            object2 = MapSerializer.construct(null, object2, bl2, this.createTypeSerializer(serializationConfig, (JavaType)object4), null, null, null);
            object3.setAnyGetter(new AnyGetterWriter(new BeanProperty.Std(new PropertyName(object.getName()), (JavaType)object4, null, beanDescription.getClassAnnotations(), (AnnotatedMember)object, PropertyMetadata.STD_OPTIONAL), (AnnotatedMember)object, (MapSerializer)object2));
        }
        this.processViews(serializationConfig, (BeanSerializerBuilder)object3);
        object4 = object3;
        if (this._factoryConfig.hasSerializerModifiers()) {
            object2 = this._factoryConfig.serializerModifiers().iterator();
            object = object3;
            do {
                object4 = object;
                if (!object2.hasNext()) break;
                object = object2.next().updateBuilder(serializationConfig, beanDescription, (BeanSerializerBuilder)object);
            } while (true);
        }
        object = object3 = object4.build();
        if (object3 != null) return object;
        object = object3;
        if (!beanDescription.hasKnownClassAnnotations()) return object;
        return object4.createDummy();
    }

    protected BeanSerializerBuilder constructBeanSerializerBuilder(BeanDescription beanDescription) {
        return new BeanSerializerBuilder(beanDescription);
    }

    protected BeanPropertyWriter constructFilteredBeanWriter(BeanPropertyWriter beanPropertyWriter, Class<?>[] arrclass) {
        return FilteredBeanPropertyWriter.constructViewBased(beanPropertyWriter, arrclass);
    }

    protected ObjectIdWriter constructObjectIdHandler(SerializerProvider object, BeanDescription object2, List<BeanPropertyWriter> object3) throws JsonMappingException {
        ObjectIdInfo objectIdInfo = object2.getObjectIdInfo();
        if (objectIdInfo == null) {
            return null;
        }
        Class class_ = objectIdInfo.getGeneratorType();
        if (class_ == ObjectIdGenerators.PropertyGenerator.class) {
            class_ = objectIdInfo.getPropertyName().getSimpleName();
            int n2 = 0;
            int n3 = object3.size();
            do {
                if (n2 == n3) {
                    throw new IllegalArgumentException("Invalid Object Id definition for " + object2.getBeanClass().getName() + ": can not find property with name '" + (String)((Object)class_) + "'");
                }
                object = (BeanPropertyWriter)object3.get(n2);
                if (class_.equals(object.getName())) {
                    if (n2 > 0) {
                        object3.remove(n2);
                        object3.add(0, object);
                    }
                    object2 = object.getType();
                    object = new PropertyBasedObjectIdGenerator(objectIdInfo, (BeanPropertyWriter)object);
                    return ObjectIdWriter.construct((JavaType)object2, (PropertyName)null, object, objectIdInfo.getAlwaysAsId());
                }
                ++n2;
            } while (true);
        }
        object3 = object.constructType(class_);
        object3 = object.getTypeFactory().findTypeParameters((JavaType)object3, ObjectIdGenerator.class)[0];
        object = object.objectIdGeneratorInstance(object2.getClassInfo(), objectIdInfo);
        return ObjectIdWriter.construct((JavaType)object3, objectIdInfo.getPropertyName(), object, objectIdInfo.getAlwaysAsId());
    }

    protected PropertyBuilder constructPropertyBuilder(SerializationConfig serializationConfig, BeanDescription beanDescription) {
        return new PropertyBuilder(serializationConfig, beanDescription);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonSerializer<Object> createSerializer(SerializerProvider serializerProvider, JavaType jsonSerializer) throws JsonMappingException {
        Converter<Object, Object> converter;
        void var6_8;
        boolean bl2;
        SerializationConfig serializationConfig = serializerProvider.getConfig();
        Object t2 = serializationConfig.introspect((JavaType)((Object)jsonSerializer));
        JsonSerializer<Object> jsonSerializer2 = this.findSerializerFromAnnotation(serializerProvider, t2.getClassInfo());
        if (jsonSerializer2 != null) {
            return jsonSerializer2;
        }
        JsonSerializer<Object> jsonSerializer3 = this.modifyTypeByAnnotation(serializationConfig, t2.getClassInfo(), jsonSerializer);
        if (jsonSerializer3 == jsonSerializer) {
            bl2 = false;
        } else {
            boolean bl3;
            bl2 = bl3 = true;
            if (!jsonSerializer3.hasRawClass(jsonSerializer.getRawClass())) {
                t2 = serializationConfig.introspect((JavaType)((Object)jsonSerializer3));
                bl2 = bl3;
            }
        }
        if ((converter = t2.findSerializationConverter()) == null) {
            return this._createSerializer2(serializerProvider, (JavaType)((Object)jsonSerializer3), (BeanDescription)t2, bl2);
        }
        JavaType javaType = converter.getOutputType(serializerProvider.getTypeFactory());
        jsonSerializer = jsonSerializer2;
        if (!javaType.hasRawClass(jsonSerializer3.getRawClass())) {
            t2 = serializationConfig.introspect(javaType);
            jsonSerializer = this.findSerializerFromAnnotation(serializerProvider, t2.getClassInfo());
        }
        JsonSerializer<Object> jsonSerializer4 = jsonSerializer;
        if (jsonSerializer == null) {
            JsonSerializer jsonSerializer5 = this._createSerializer2(serializerProvider, javaType, (BeanDescription)t2, true);
        }
        return new StdDelegatingSerializer(converter, javaType, var6_8);
    }

    @Override
    protected Iterable<Serializers> customSerializers() {
        return this._factoryConfig.serializers();
    }

    protected List<BeanPropertyWriter> filterBeanProperties(SerializationConfig object, BeanDescription object2, List<BeanPropertyWriter> list) {
        if ((object = object.getAnnotationIntrospector().findPropertiesToIgnore(object2.getClassInfo())) != null && object.length > 0) {
            object = ArrayBuilders.arrayToSet(object);
            object2 = list.iterator();
            while (object2.hasNext()) {
                if (!object.contains(((BeanPropertyWriter)object2.next()).getName())) continue;
                object2.remove();
            }
        }
        return list;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected List<BeanPropertyWriter> findBeanProperties(SerializerProvider serializerProvider, BeanDescription object, BeanSerializerBuilder beanSerializerBuilder) throws JsonMappingException {
        Object object2 = object.findProperties();
        SerializationConfig serializationConfig = serializerProvider.getConfig();
        this.removeIgnorableTypes(serializationConfig, (BeanDescription)object, (List<BeanPropertyDefinition>)object2);
        if (serializationConfig.isEnabled(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS)) {
            this.removeSetterlessGetters(serializationConfig, (BeanDescription)object, (List<BeanPropertyDefinition>)object2);
        }
        if (object2.isEmpty()) {
            return null;
        }
        boolean bl2 = this.usesStaticTyping(serializationConfig, (BeanDescription)object, null);
        PropertyBuilder propertyBuilder = this.constructPropertyBuilder(serializationConfig, (BeanDescription)object);
        ArrayList<BeanPropertyWriter> arrayList = new ArrayList<BeanPropertyWriter>(object2.size());
        TypeBindings typeBindings = object.bindingsForBeanType();
        object2 = object2.iterator();
        do {
            object = arrayList;
            if (!object2.hasNext()) return object;
            object = (BeanPropertyDefinition)object2.next();
            AnnotatedMember annotatedMember = object.getAccessor();
            if (object.isTypeId()) {
                if (annotatedMember == null) continue;
                if (serializationConfig.canOverrideAccessModifiers()) {
                    annotatedMember.fixAccess();
                }
                beanSerializerBuilder.setTypeId(annotatedMember);
                continue;
            }
            AnnotationIntrospector.ReferenceProperty referenceProperty = object.findReferenceType();
            if (referenceProperty != null && referenceProperty.isBackReference()) continue;
            if (annotatedMember instanceof AnnotatedMethod) {
                arrayList.add(this._constructWriter(serializerProvider, (BeanPropertyDefinition)object, typeBindings, propertyBuilder, bl2, (AnnotatedMethod)annotatedMember));
                continue;
            }
            arrayList.add(this._constructWriter(serializerProvider, (BeanPropertyDefinition)object, typeBindings, propertyBuilder, bl2, (AnnotatedField)annotatedMember));
        } while (true);
    }

    public JsonSerializer<Object> findBeanSerializer(SerializerProvider serializerProvider, JavaType javaType, BeanDescription beanDescription) throws JsonMappingException {
        if (!this.isPotentialBeanType(javaType.getRawClass()) && !javaType.isEnumType()) {
            return null;
        }
        return this.constructBeanSerializer(serializerProvider, beanDescription);
    }

    public TypeSerializer findPropertyContentTypeSerializer(JavaType typeResolverBuilder, SerializationConfig serializationConfig, AnnotatedMember annotatedMember) throws JsonMappingException {
        ResolvedType resolvedType = typeResolverBuilder.getContentType();
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        typeResolverBuilder = annotationIntrospector.findPropertyContentTypeResolver(serializationConfig, annotatedMember, (JavaType)((Object)typeResolverBuilder));
        if (typeResolverBuilder == null) {
            return this.createTypeSerializer(serializationConfig, (JavaType)resolvedType);
        }
        return typeResolverBuilder.buildTypeSerializer(serializationConfig, (JavaType)resolvedType, serializationConfig.getSubtypeResolver().collectAndResolveSubtypes(annotatedMember, serializationConfig, annotationIntrospector, (JavaType)resolvedType));
    }

    public TypeSerializer findPropertyTypeSerializer(JavaType javaType, SerializationConfig serializationConfig, AnnotatedMember annotatedMember) throws JsonMappingException {
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        TypeResolverBuilder typeResolverBuilder = annotationIntrospector.findPropertyTypeResolver(serializationConfig, annotatedMember, javaType);
        if (typeResolverBuilder == null) {
            return this.createTypeSerializer(serializationConfig, javaType);
        }
        return typeResolverBuilder.buildTypeSerializer(serializationConfig, javaType, serializationConfig.getSubtypeResolver().collectAndResolveSubtypes(annotatedMember, serializationConfig, annotationIntrospector, javaType));
    }

    protected boolean isPotentialBeanType(Class<?> class_) {
        if (ClassUtil.canBeABeanType(class_) == null && !ClassUtil.isProxyType(class_)) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void processViews(SerializationConfig arrbeanPropertyWriter, BeanSerializerBuilder beanSerializerBuilder) {
        List<BeanPropertyWriter> list = beanSerializerBuilder.getProperties();
        boolean bl2 = arrbeanPropertyWriter.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
        int n2 = list.size();
        int n3 = 0;
        arrbeanPropertyWriter = new BeanPropertyWriter[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            int n4;
            BeanPropertyWriter beanPropertyWriter = list.get(i2);
            Class<?>[] arrclass = beanPropertyWriter.getViews();
            if (arrclass == null) {
                n4 = n3;
                if (bl2) {
                    arrbeanPropertyWriter[i2] = beanPropertyWriter;
                    n4 = n3;
                }
            } else {
                n4 = n3 + 1;
                arrbeanPropertyWriter[i2] = this.constructFilteredBeanWriter(beanPropertyWriter, arrclass);
            }
            n3 = n4;
        }
        if (bl2 && n3 == 0) {
            return;
        }
        beanSerializerBuilder.setFilteredProperties(arrbeanPropertyWriter);
    }

    protected void removeIgnorableTypes(SerializationConfig serializationConfig, BeanDescription object, List<BeanPropertyDefinition> object2) {
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        HashMap hashMap = new HashMap();
        Iterator<BeanPropertyDefinition> iterator = object2.iterator();
        while (iterator.hasNext()) {
            object = iterator.next().getAccessor();
            if (object == null) {
                iterator.remove();
                continue;
            }
            Class class_ = object.getRawType();
            object = object2 = (Boolean)hashMap.get(class_);
            if (object2 == null) {
                object = object2 = annotationIntrospector.isIgnorableType(serializationConfig.introspectClassAnnotations(class_).getClassInfo());
                if (object2 == null) {
                    object = Boolean.FALSE;
                }
                hashMap.put(class_, object);
            }
            if (!object.booleanValue()) continue;
            iterator.remove();
        }
    }

    protected void removeSetterlessGetters(SerializationConfig object, BeanDescription object2, List<BeanPropertyDefinition> list) {
        object = list.iterator();
        while (object.hasNext()) {
            object2 = (BeanPropertyDefinition)object.next();
            if (object2.couldDeserialize() || object2.isExplicitlyIncluded()) continue;
            object.remove();
        }
    }

    @Override
    public SerializerFactory withConfig(SerializerFactoryConfig serializerFactoryConfig) {
        if (this._factoryConfig == serializerFactoryConfig) {
            return this;
        }
        if (this.getClass() != BeanSerializerFactory.class) {
            throw new IllegalStateException("Subtype of BeanSerializerFactory (" + this.getClass().getName() + ") has not properly overridden method 'withAdditionalSerializers': can not instantiate subtype with " + "additional serializer definitions");
        }
        return new BeanSerializerFactory(serializerFactoryConfig);
    }
}

