/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.AbstractDeserializer;
import com.fasterxml.jackson.databind.deser.BasicDeserializerFactory;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.FieldProperty;
import com.fasterxml.jackson.databind.deser.impl.MethodProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedObjectIdGenerator;
import com.fasterxml.jackson.databind.deser.impl.SetterlessProperty;
import com.fasterxml.jackson.databind.deser.std.AtomicReferenceDeserializer;
import com.fasterxml.jackson.databind.deser.std.ThrowableDeserializer;
import com.fasterxml.jackson.databind.ext.OptionalHandlerFactory;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.SimpleBeanPropertyDefinition;
import java.io.Serializable;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class BeanDeserializerFactory
extends BasicDeserializerFactory
implements Serializable {
    private static final Class<?>[] INIT_CAUSE_PARAMS = new Class[]{Throwable.class};
    private static final Class<?>[] NO_VIEWS = new Class[0];
    public static final BeanDeserializerFactory instance = new BeanDeserializerFactory(new DeserializerFactoryConfig());
    private static final long serialVersionUID = 1;

    public BeanDeserializerFactory(DeserializerFactoryConfig deserializerFactoryConfig) {
        super(deserializerFactoryConfig);
    }

    protected JsonDeserializer<Object> _findCustomBeanDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, BeanDescription beanDescription) throws JsonMappingException {
        Iterator<Deserializers> iterator = this._factoryConfig.deserializers().iterator();
        while (iterator.hasNext()) {
            JsonDeserializer<Object> jsonDeserializer = iterator.next().findBeanDeserializer(javaType, deserializationConfig, beanDescription);
            if (jsonDeserializer == null) continue;
            return jsonDeserializer;
        }
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void addBeanProps(DeserializationContext var1_1, BeanDescription var2_2, BeanDeserializerBuilder var3_3) throws JsonMappingException {
        var10_4 = var3_3.getValueInstantiator().getFromObjectArguments(var1_1.getConfig());
        var7_5 = var1_1.getAnnotationIntrospector();
        var8_6 = var7_5.findIgnoreUnknownProperties(var2_2.getClassInfo());
        if (var8_6 != null) {
            var3_3.setIgnoreUnknownProperties(var8_6.booleanValue());
        }
        var7_5 = ArrayBuilders.arrayToSet(var7_5.findPropertiesToIgnore(var2_2.getClassInfo()));
        var8_6 = var7_5.iterator();
        while (var8_6.hasNext()) {
            var3_3.addIgnorable((String)var8_6.next());
        }
        var8_6 = var2_2.findAnySetter();
        if (var8_6 != null) {
            var3_3.setAnySetter(this.constructAnySetter(var1_1, var2_2, (AnnotatedMethod)var8_6));
        }
        if (var8_6 == null && (var8_6 = var2_2.getIgnoredPropertyNames()) != null) {
            var8_6 = var8_6.iterator();
            while (var8_6.hasNext()) {
                var3_3.addIgnorable((String)var8_6.next());
            }
        }
        var4_7 = var1_1.isEnabled(MapperFeature.USE_GETTERS_AS_SETTERS) != false && var1_1.isEnabled(MapperFeature.AUTO_DETECT_GETTERS) != false;
        var8_6 = var7_5 = this.filterBeanProps(var1_1, var2_2, var3_3, var2_2.findProperties(), (Set<String>)var7_5);
        if (this._factoryConfig.hasDeserializerModifiers()) {
            var9_8 = this._factoryConfig.deserializerModifiers().iterator();
            do {
                var8_6 = var7_5;
                if (!var9_8.hasNext()) break;
                var7_5 = ((BeanDeserializerModifier)var9_8.next()).updateProperties(var1_1.getConfig(), var2_2, (List<BeanPropertyDefinition>)var7_5);
            } while (true);
        }
        var11_9 = var8_6.iterator();
        while (var11_9.hasNext() != false) {
            var9_8 = (BeanPropertyDefinition)var11_9.next();
            var8_6 = null;
            if (!var9_8.hasSetter()) ** GOTO lbl35
            var7_5 = this.constructSettableProperty(var1_1, var2_2, (BeanPropertyDefinition)var9_8, var9_8.getSetter().getGenericParameterType(0));
            ** GOTO lbl47
lbl35: // 1 sources:
            if (!var9_8.hasField()) ** GOTO lbl38
            var7_5 = this.constructSettableProperty(var1_1, var2_2, (BeanPropertyDefinition)var9_8, var9_8.getField().getGenericType());
            ** GOTO lbl47
lbl38: // 1 sources:
            var7_5 = var8_6;
            if (!var4_7) ** GOTO lbl47
            var7_5 = var8_6;
            if (!var9_8.hasGetter()) ** GOTO lbl47
            var12_12 = var9_8.getGetter().getRawType();
            if (Collection.class.isAssignableFrom(var12_12)) ** GOTO lbl-1000
            var7_5 = var8_6;
            if (Map.class.isAssignableFrom(var12_12)) lbl-1000: // 2 sources:
            {
                var7_5 = this.constructSetterlessProperty(var1_1, var2_2, (BeanPropertyDefinition)var9_8);
            }
lbl47: // 7 sources:
            if (!var9_8.hasConstructorParameter()) ** GOTO lbl52
            var12_12 = var9_8.getName();
            var8_6 = var9_8 = null;
            if (var10_4 == null) ** GOTO lbl69
            ** GOTO lbl61
lbl52: // 1 sources:
            if (var7_5 == null) continue;
            var8_6 = var9_8 = var9_8.findViews();
            if (var9_8 == null) {
                var8_6 = var9_8;
                if (!var1_1.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION)) {
                    var8_6 = BeanDeserializerFactory.NO_VIEWS;
                }
            }
            var7_5.setViews((Class<?>[])var8_6);
            var3_3.addProperty((SettableBeanProperty)var7_5);
            continue;
lbl61: // 1 sources:
            var6_11 = var10_4.length;
            var5_10 = 0;
            do {
                var8_6 = var9_8;
                if (var5_10 >= var6_11) ** GOTO lbl69
                var8_6 = var10_4[var5_10];
                if (var12_12.equals((Object)var8_6.getName())) {
                    var8_6 = (CreatorProperty)var8_6;
lbl69: // 3 sources:
                    if (var8_6 != null) break;
                    throw var1_1.mappingException("Could not find creator property with name '" + (String)var12_12 + "' (in class " + var2_2.getBeanClass().getName() + ")");
                }
                ++var5_10;
            } while (true);
            var9_8 = var8_6;
            if (var7_5 != null) {
                var9_8 = var8_6.withFallbackSetter((SettableBeanProperty)var7_5);
            }
            var3_3.addCreatorProperty((SettableBeanProperty)var9_8);
        }
    }

    protected void addInjectables(DeserializationContext object, BeanDescription beanDescription, BeanDeserializerBuilder beanDeserializerBuilder) throws JsonMappingException {
        Map<Object, AnnotatedMember> map2 = beanDescription.findInjectables();
        if (map2 != null) {
            boolean bl2 = object.canOverrideAccessModifiers();
            for (Map.Entry<Object, AnnotatedMember> entry : map2.entrySet()) {
                AnnotatedMember annotatedMember = entry.getValue();
                if (bl2) {
                    annotatedMember.fixAccess();
                }
                beanDeserializerBuilder.addInjectable(new PropertyName(annotatedMember.getName()), beanDescription.resolveType(annotatedMember.getGenericType()), beanDescription.getClassAnnotations(), annotatedMember, entry.getKey());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void addObjectIdReader(DeserializationContext jsonDeserializer, BeanDescription serializable, BeanDeserializerBuilder beanDeserializerBuilder) throws JsonMappingException {
        SettableBeanProperty settableBeanProperty;
        void var4_8;
        ObjectIdInfo objectIdInfo = serializable.getObjectIdInfo();
        if (objectIdInfo == null) {
            return;
        }
        Class class_ = objectIdInfo.getGeneratorType();
        ObjectIdResolver objectIdResolver = jsonDeserializer.objectIdResolverInstance((Annotated)serializable.getClassInfo(), objectIdInfo);
        if (class_ == ObjectIdGenerators.PropertyGenerator.class) {
            PropertyName propertyName = objectIdInfo.getPropertyName();
            settableBeanProperty = beanDeserializerBuilder.findProperty(propertyName);
            if (settableBeanProperty == null) {
                throw new IllegalArgumentException("Invalid Object Id definition for " + serializable.getBeanClass().getName() + ": can not find property with name '" + propertyName + "'");
            }
            serializable = settableBeanProperty.getType();
            PropertyBasedObjectIdGenerator propertyBasedObjectIdGenerator = new PropertyBasedObjectIdGenerator(objectIdInfo.getScope());
        } else {
            JavaType javaType = jsonDeserializer.constructType(class_);
            JavaType javaType2 = jsonDeserializer.getTypeFactory().findTypeParameters(javaType, ObjectIdGenerator.class)[0];
            settableBeanProperty = null;
            ObjectIdGenerator objectIdGenerator = jsonDeserializer.objectIdGeneratorInstance((Annotated)serializable.getClassInfo(), objectIdInfo);
            serializable = javaType2;
            ObjectIdGenerator objectIdGenerator2 = objectIdGenerator;
        }
        jsonDeserializer = jsonDeserializer.findRootValueDeserializer(serializable);
        beanDeserializerBuilder.setObjectIdReader(ObjectIdReader.construct(serializable, objectIdInfo.getPropertyName(), var4_8, jsonDeserializer, settableBeanProperty, objectIdResolver));
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void addReferenceProperties(DeserializationContext deserializationContext, BeanDescription beanDescription, BeanDeserializerBuilder beanDeserializerBuilder) throws JsonMappingException {
        Object object = beanDescription.findBackReferenceProperties();
        if (object != null) {
            Iterator<Map.Entry<String, AnnotatedMember>> iterator = object.entrySet().iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                String string2 = (String)object.getKey();
                AnnotatedMember annotatedMember = (AnnotatedMember)object.getValue();
                object = annotatedMember instanceof AnnotatedMethod ? ((AnnotatedMethod)annotatedMember).getGenericParameterType(0) : annotatedMember.getRawType();
                beanDeserializerBuilder.addBackReferenceProperty(string2, this.constructSettableProperty(deserializationContext, beanDescription, SimpleBeanPropertyDefinition.construct(deserializationContext.getConfig(), annotatedMember), (Type)object));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonDeserializer<Object> buildBeanDeserializer(DeserializationContext abstractDeserializer, JavaType object, BeanDescription beanDescription) throws JsonMappingException {
        ValueInstantiator valueInstantiator = this.findValueInstantiator((DeserializationContext)((Object)abstractDeserializer), beanDescription);
        Object object2 = this.constructBeanDeserializerBuilder((DeserializationContext)((Object)abstractDeserializer), beanDescription);
        object2.setValueInstantiator(valueInstantiator);
        this.addBeanProps((DeserializationContext)((Object)abstractDeserializer), beanDescription, (BeanDeserializerBuilder)object2);
        this.addObjectIdReader((DeserializationContext)((Object)abstractDeserializer), beanDescription, (BeanDeserializerBuilder)object2);
        this.addReferenceProperties((DeserializationContext)((Object)abstractDeserializer), beanDescription, (BeanDeserializerBuilder)object2);
        this.addInjectables((DeserializationContext)((Object)abstractDeserializer), beanDescription, (BeanDeserializerBuilder)object2);
        DeserializationConfig deserializationConfig = abstractDeserializer.getConfig();
        Object object3 = object2;
        if (this._factoryConfig.hasDeserializerModifiers()) {
            Iterator<BeanDeserializerModifier> iterator = this._factoryConfig.deserializerModifiers().iterator();
            abstractDeserializer = object2;
            do {
                object3 = abstractDeserializer;
                if (!iterator.hasNext()) break;
                abstractDeserializer = iterator.next().updateBuilder(deserializationConfig, beanDescription, (BeanDeserializerBuilder)((Object)abstractDeserializer));
            } while (true);
        }
        abstractDeserializer = object.isAbstract() && !valueInstantiator.canInstantiate() ? object3.buildAbstract() : object3.build();
        object = abstractDeserializer;
        if (this._factoryConfig.hasDeserializerModifiers()) {
            object2 = this._factoryConfig.deserializerModifiers().iterator();
            do {
                object = abstractDeserializer;
                if (!object2.hasNext()) break;
                abstractDeserializer = ((BeanDeserializerModifier)object2.next()).modifyDeserializer(deserializationConfig, beanDescription, abstractDeserializer);
            } while (true);
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonDeserializer<Object> buildBuilderBasedDeserializer(DeserializationContext jsonDeserializer, JavaType jsonDeserializer2, BeanDescription beanDescription) throws JsonMappingException {
        Object object = this.findValueInstantiator((DeserializationContext)((Object)jsonDeserializer), beanDescription);
        DeserializationConfig deserializationConfig = jsonDeserializer.getConfig();
        Object object2 = this.constructBeanDeserializerBuilder((DeserializationContext)((Object)jsonDeserializer), beanDescription);
        object2.setValueInstantiator((ValueInstantiator)object);
        this.addBeanProps((DeserializationContext)((Object)jsonDeserializer), beanDescription, (BeanDeserializerBuilder)object2);
        this.addObjectIdReader((DeserializationContext)((Object)jsonDeserializer), beanDescription, (BeanDeserializerBuilder)object2);
        this.addReferenceProperties((DeserializationContext)((Object)jsonDeserializer), beanDescription, (BeanDeserializerBuilder)object2);
        this.addInjectables((DeserializationContext)((Object)jsonDeserializer), beanDescription, (BeanDeserializerBuilder)object2);
        jsonDeserializer = beanDescription.findPOJOBuilderConfig();
        object = jsonDeserializer == null ? "build" : jsonDeserializer.buildMethodName;
        Object object3 = beanDescription.findMethod((String)object, null);
        if (object3 != null && deserializationConfig.canOverrideAccessModifiers()) {
            ClassUtil.checkAndFixAccess(object3.getMember());
        }
        object2.setPOJOBuilder((AnnotatedMethod)object3, (JsonPOJOBuilder.Value)((Object)jsonDeserializer));
        object3 = object2;
        if (this._factoryConfig.hasDeserializerModifiers()) {
            Iterator<BeanDeserializerModifier> iterator = this._factoryConfig.deserializerModifiers().iterator();
            jsonDeserializer = object2;
            do {
                object3 = jsonDeserializer;
                if (!iterator.hasNext()) break;
                jsonDeserializer = iterator.next().updateBuilder(deserializationConfig, beanDescription, (BeanDeserializerBuilder)((Object)jsonDeserializer));
            } while (true);
        }
        jsonDeserializer2 = jsonDeserializer = object3.buildBuilderBased((JavaType)((Object)jsonDeserializer2), (String)object);
        if (this._factoryConfig.hasDeserializerModifiers()) {
            object2 = this._factoryConfig.deserializerModifiers().iterator();
            do {
                jsonDeserializer2 = jsonDeserializer;
                if (!object2.hasNext()) break;
                jsonDeserializer = ((BeanDeserializerModifier)object2.next()).modifyDeserializer(deserializationConfig, beanDescription, jsonDeserializer);
            } while (true);
        }
        return jsonDeserializer2;
    }

    public JsonDeserializer<Object> buildThrowableDeserializer(DeserializationContext object, JavaType object2, BeanDescription beanDescription) throws JsonMappingException {
        DeserializationConfig deserializationConfig = object.getConfig();
        object2 = this.constructBeanDeserializerBuilder((DeserializationContext)object, beanDescription);
        object2.setValueInstantiator(this.findValueInstantiator((DeserializationContext)object, beanDescription));
        this.addBeanProps((DeserializationContext)object, beanDescription, (BeanDeserializerBuilder)object2);
        Object object3 = beanDescription.findMethod("initCause", INIT_CAUSE_PARAMS);
        if (object3 != null && (object = this.constructSettableProperty((DeserializationContext)object, beanDescription, SimpleBeanPropertyDefinition.construct(object.getConfig(), (AnnotatedMember)object3, "cause"), object3.getGenericParameterType(0))) != null) {
            object2.addOrReplaceProperty((SettableBeanProperty)object, true);
        }
        object2.addIgnorable("localizedMessage");
        object2.addIgnorable("suppressed");
        object2.addIgnorable("message");
        object3 = object2;
        if (this._factoryConfig.hasDeserializerModifiers()) {
            Iterator<BeanDeserializerModifier> iterator = this._factoryConfig.deserializerModifiers().iterator();
            object = object2;
            do {
                object3 = object;
                if (!iterator.hasNext()) break;
                object = iterator.next().updateBuilder(deserializationConfig, beanDescription, (BeanDeserializerBuilder)object);
            } while (true);
        }
        object = object2 = object3.build();
        if (object2 instanceof BeanDeserializer) {
            object = new ThrowableDeserializer((BeanDeserializer)object2);
        }
        object2 = object;
        if (this._factoryConfig.hasDeserializerModifiers()) {
            object3 = this._factoryConfig.deserializerModifiers().iterator();
            do {
                object2 = object;
                if (!object3.hasNext()) break;
                object = ((BeanDeserializerModifier)object3.next()).modifyDeserializer(deserializationConfig, beanDescription, object);
            } while (true);
        }
        return object2;
    }

    protected SettableAnyProperty constructAnySetter(DeserializationContext object, BeanDescription object2, AnnotatedMethod annotatedMethod) throws JsonMappingException {
        if (object.canOverrideAccessModifiers()) {
            annotatedMethod.fixAccess();
        }
        JavaType javaType = object2.bindingsForBeanType().resolveType(annotatedMethod.getGenericParameterType(1));
        BeanProperty.Std std = new BeanProperty.Std(new PropertyName(annotatedMethod.getName()), javaType, null, object2.getClassAnnotations(), (AnnotatedMember)annotatedMethod, PropertyMetadata.STD_OPTIONAL);
        javaType = this.resolveType((DeserializationContext)object, (BeanDescription)object2, javaType, annotatedMethod);
        object2 = this.findDeserializerFromAnnotation((DeserializationContext)object, annotatedMethod);
        javaType = this.modifyTypeByAnnotation((DeserializationContext)object, annotatedMethod, javaType);
        object = object2;
        if (object2 == null) {
            object = (JsonDeserializer)javaType.getValueHandler();
        }
        return new SettableAnyProperty((BeanProperty)std, annotatedMethod, javaType, (JsonDeserializer<Object>)object, (TypeDeserializer)javaType.getTypeHandler());
    }

    protected BeanDeserializerBuilder constructBeanDeserializerBuilder(DeserializationContext deserializationContext, BeanDescription beanDescription) {
        return new BeanDeserializerBuilder(beanDescription, deserializationContext.getConfig());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected SettableBeanProperty constructSettableProperty(DeserializationContext object, BeanDescription object2, BeanPropertyDefinition beanPropertyDefinition, Type jsonDeserializer) throws JsonMappingException {
        AnnotatedMember annotatedMember = beanPropertyDefinition.getNonConstructorMutator();
        if (object.canOverrideAccessModifiers()) {
            annotatedMember.fixAccess();
        }
        jsonDeserializer = object2.resolveType((Type)((Object)jsonDeserializer));
        BeanProperty.Std std = new BeanProperty.Std(beanPropertyDefinition.getFullName(), (JavaType)((Object)jsonDeserializer), beanPropertyDefinition.getWrapperName(), object2.getClassAnnotations(), annotatedMember, beanPropertyDefinition.getMetadata());
        Object object3 = this.resolveType((DeserializationContext)object, (BeanDescription)object2, (JavaType)((Object)jsonDeserializer), annotatedMember);
        if (object3 != jsonDeserializer) {
            std.withType((JavaType)object3);
        }
        jsonDeserializer = this.findDeserializerFromAnnotation((DeserializationContext)object, annotatedMember);
        object = this.modifyTypeByAnnotation((DeserializationContext)object, annotatedMember, object3);
        object3 = (TypeDeserializer)object.getTypeHandler();
        object = annotatedMember instanceof AnnotatedMethod ? new MethodProperty(beanPropertyDefinition, (JavaType)object, (TypeDeserializer)object3, object2.getClassAnnotations(), (AnnotatedMethod)annotatedMember) : new FieldProperty(beanPropertyDefinition, (JavaType)object, (TypeDeserializer)object3, object2.getClassAnnotations(), (AnnotatedField)annotatedMember);
        object2 = object;
        if (jsonDeserializer != null) {
            object2 = object.withValueDeserializer(jsonDeserializer);
        }
        if ((object = beanPropertyDefinition.findReferenceType()) != null && object.isManagedReference()) {
            object2.setManagedReferenceName(object.getName());
        }
        if ((object = beanPropertyDefinition.findObjectIdInfo()) != null) {
            object2.setObjectIdInfo((ObjectIdInfo)object);
        }
        return object2;
    }

    protected SettableBeanProperty constructSetterlessProperty(DeserializationContext object, BeanDescription object2, BeanPropertyDefinition beanPropertyDefinition) throws JsonMappingException {
        AnnotatedMethod annotatedMethod = beanPropertyDefinition.getGetter();
        if (object.canOverrideAccessModifiers()) {
            annotatedMethod.fixAccess();
        }
        JavaType javaType = annotatedMethod.getType(object2.bindingsForBeanType());
        JsonDeserializer<Object> jsonDeserializer = this.findDeserializerFromAnnotation((DeserializationContext)object, annotatedMethod);
        object = this.modifyTypeByAnnotation((DeserializationContext)object, annotatedMethod, javaType);
        object = object2 = new SetterlessProperty(beanPropertyDefinition, (JavaType)object, (TypeDeserializer)object.getTypeHandler(), object2.getClassAnnotations(), annotatedMethod);
        if (jsonDeserializer != null) {
            object = object2.withValueDeserializer(jsonDeserializer);
        }
        return object;
    }

    @Override
    public JsonDeserializer<Object> createBeanDeserializer(DeserializationContext deserializationContext, JavaType javaType, BeanDescription beanDescription) throws JsonMappingException {
        JsonDeserializer jsonDeserializer = deserializationContext.getConfig();
        JsonDeserializer<Object> jsonDeserializer2 = this._findCustomBeanDeserializer(javaType, (DeserializationConfig)((Object)jsonDeserializer), beanDescription);
        if (jsonDeserializer2 != null) {
            return jsonDeserializer2;
        }
        if (javaType.isThrowable()) {
            return this.buildThrowableDeserializer(deserializationContext, javaType, beanDescription);
        }
        if (javaType.isAbstract() && (jsonDeserializer2 = this.materializeAbstractType(deserializationContext, javaType, beanDescription)) != null) {
            return this.buildBeanDeserializer(deserializationContext, (JavaType)((Object)jsonDeserializer2), (BeanDescription)jsonDeserializer.introspect((JavaType)((Object)jsonDeserializer2)));
        }
        jsonDeserializer = this.findStdDeserializer(deserializationContext, javaType, beanDescription);
        if (jsonDeserializer != null) {
            return jsonDeserializer;
        }
        if (!this.isPotentialBeanType(javaType.getRawClass())) {
            return null;
        }
        return this.buildBeanDeserializer(deserializationContext, javaType, beanDescription);
    }

    @Override
    public JsonDeserializer<Object> createBuilderBasedDeserializer(DeserializationContext deserializationContext, JavaType javaType, BeanDescription object, Class<?> class_) throws JsonMappingException {
        object = deserializationContext.constructType(class_);
        return this.buildBuilderBasedDeserializer(deserializationContext, javaType, (BeanDescription)deserializationContext.getConfig().introspectForBuilder((JavaType)object));
    }

    /*
     * Enabled aggressive block sorting
     */
    protected List<BeanPropertyDefinition> filterBeanProps(DeserializationContext deserializationContext, BeanDescription beanDescription, BeanDeserializerBuilder beanDeserializerBuilder, List<BeanPropertyDefinition> class_, Set<String> set) throws JsonMappingException {
        ArrayList<BeanPropertyDefinition> arrayList = new ArrayList<BeanPropertyDefinition>(Math.max(4, class_.size()));
        HashMap hashMap = new HashMap();
        Iterator iterator = class_.iterator();
        while (iterator.hasNext()) {
            BeanPropertyDefinition beanPropertyDefinition = (BeanPropertyDefinition)iterator.next();
            String string2 = beanPropertyDefinition.getName();
            if (set.contains(string2)) continue;
            if (!beanPropertyDefinition.hasConstructorParameter()) {
                class_ = null;
                if (beanPropertyDefinition.hasSetter()) {
                    class_ = beanPropertyDefinition.getSetter().getRawParameterType(0);
                } else if (beanPropertyDefinition.hasField()) {
                    class_ = beanPropertyDefinition.getField().getRawType();
                }
                if (class_ != null && this.isIgnorableType(deserializationContext.getConfig(), beanDescription, class_, hashMap)) {
                    beanDeserializerBuilder.addIgnorable(string2);
                    continue;
                }
            }
            arrayList.add(beanPropertyDefinition);
        }
        return arrayList;
    }

    protected JsonDeserializer<?> findOptionalStdDeserializer(DeserializationContext deserializationContext, JavaType javaType, BeanDescription beanDescription) throws JsonMappingException {
        return OptionalHandlerFactory.instance.findDeserializer(javaType, deserializationContext.getConfig(), beanDescription);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JsonDeserializer<?> findStdDeserializer(DeserializationContext deserializationContext, JavaType arrjavaType, BeanDescription beanDescription) throws JsonMappingException {
        JsonDeserializer jsonDeserializer = this.findDefaultDeserializer(deserializationContext, (JavaType)arrjavaType, beanDescription);
        if (jsonDeserializer != null) {
            return jsonDeserializer;
        }
        if (!AtomicReference.class.isAssignableFrom(arrjavaType.getRawClass())) return this.findOptionalStdDeserializer(deserializationContext, (JavaType)arrjavaType, beanDescription);
        arrjavaType = deserializationContext.getTypeFactory().findTypeParameters((JavaType)arrjavaType, AtomicReference.class);
        if (arrjavaType == null || arrjavaType.length < 1) {
            arrjavaType = TypeFactory.unknownType();
            do {
                return new AtomicReferenceDeserializer((JavaType)arrjavaType, this.findTypeDeserializer(deserializationContext.getConfig(), (JavaType)arrjavaType), this.findDeserializerFromAnnotation(deserializationContext, deserializationContext.getConfig().introspectClassAnnotations((JavaType)arrjavaType).getClassInfo()));
                break;
            } while (true);
        }
        arrjavaType = arrjavaType[0];
        return new AtomicReferenceDeserializer((JavaType)arrjavaType, this.findTypeDeserializer(deserializationContext.getConfig(), (JavaType)arrjavaType), this.findDeserializerFromAnnotation(deserializationContext, deserializationContext.getConfig().introspectClassAnnotations((JavaType)arrjavaType).getClassInfo()));
    }

    protected boolean isIgnorableType(DeserializationConfig serializable, BeanDescription object, Class<?> class_, Map<Class<?>, Boolean> object2) {
        object = object2 = object2.get(class_);
        if (object2 == null) {
            object = serializable.introspectClassAnnotations(class_);
            object = serializable = serializable.getAnnotationIntrospector().isIgnorableType(object.getClassInfo());
            if (serializable == null) {
                object = Boolean.FALSE;
            }
        }
        return object.booleanValue();
    }

    protected boolean isPotentialBeanType(Class<?> class_) {
        String string2 = ClassUtil.canBeABeanType(class_);
        if (string2 != null) {
            throw new IllegalArgumentException("Can not deserialize Class " + class_.getName() + " (of type " + string2 + ") as a Bean");
        }
        if (ClassUtil.isProxyType(class_)) {
            throw new IllegalArgumentException("Can not deserialize Proxy class " + class_.getName() + " as a Bean");
        }
        string2 = ClassUtil.isLocalType(class_, true);
        if (string2 != null) {
            throw new IllegalArgumentException("Can not deserialize Class " + class_.getName() + " (of type " + string2 + ") as a Bean");
        }
        return true;
    }

    protected JavaType materializeAbstractType(DeserializationContext deserializationContext, JavaType javaType, BeanDescription object) throws JsonMappingException {
        javaType = object.getType();
        object = this._factoryConfig.abstractTypeResolvers().iterator();
        while (object.hasNext()) {
            JavaType javaType2 = ((AbstractTypeResolver)object.next()).resolveAbstractType(deserializationContext.getConfig(), javaType);
            if (javaType2 == null) continue;
            return javaType2;
        }
        return null;
    }

    @Override
    public DeserializerFactory withConfig(DeserializerFactoryConfig deserializerFactoryConfig) {
        if (this._factoryConfig == deserializerFactoryConfig) {
            return this;
        }
        if (this.getClass() != BeanDeserializerFactory.class) {
            throw new IllegalStateException("Subtype of BeanDeserializerFactory (" + this.getClass().getName() + ") has not properly overridden method 'withAdditionalDeserializers': can not instantiate subtype with " + "additional deserializer definitions");
        }
        return new BeanDeserializerFactory(deserializerFactoryConfig);
    }
}

