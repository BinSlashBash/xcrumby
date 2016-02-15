/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.BasicSerializerFactory;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.lang.reflect.AnnotatedElement;

public class PropertyBuilder {
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final BeanDescription _beanDesc;
    protected final SerializationConfig _config;
    protected Object _defaultBean;
    protected final JsonInclude.Include _outputProps;

    public PropertyBuilder(SerializationConfig serializationConfig, BeanDescription beanDescription) {
        this._config = serializationConfig;
        this._beanDesc = beanDescription;
        this._outputProps = beanDescription.findSerializationInclusion(serializationConfig.getSerializationInclusion());
        this._annotationIntrospector = this._config.getAnnotationIntrospector();
    }

    protected Object _throwWrapped(Exception throwable, String string2, Object object) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
        }
        throw new IllegalArgumentException("Failed to get property '" + string2 + "' of default " + object.getClass().getName() + " instance");
    }

    /*
     * Enabled aggressive block sorting
     */
    protected BeanPropertyWriter buildWriter(SerializerProvider object, BeanPropertyDefinition beanPropertyDefinition, JavaType object2, JsonSerializer<?> jsonSerializer, TypeSerializer typeSerializer, TypeSerializer object3, AnnotatedMember annotatedMember, boolean bl2) throws JsonMappingException {
        void var5_6;
        Object object4;
        boolean bl3;
        Object object5;
        Object object6;
        void var4_5;
        void var7_8;
        Object object7 = object4 = this.findSerializationType((Annotated)var7_8, bl3, (JavaType)object6);
        if (object5 != null) {
            object7 = object4;
            if (object4 == null) {
                object7 = object6;
            }
            if (object7.getContentType() == null) {
                throw new IllegalStateException("Problem trying to create BeanPropertyWriter for property '" + beanPropertyDefinition.getName() + "' (of type " + this._beanDesc.getType() + "); serialization type " + object7 + " has no content");
            }
            object7 = object7.withContentTypeHandler(object5);
            object7.getContentType();
        }
        object4 = null;
        boolean bl4 = false;
        boolean bl5 = false;
        JsonInclude.Include include = this._annotationIntrospector.findSerializationInclusion((Annotated)var7_8, this._outputProps);
        bl3 = bl5;
        object5 = object4;
        if (include != null) {
            switch (.$SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[include.ordinal()]) {
                default: {
                    object5 = object4;
                    bl3 = bl5;
                    break;
                }
                case 1: {
                    object4 = this.getDefaultValue(beanPropertyDefinition.getName(), (AnnotatedMember)var7_8);
                    if (object4 == null) {
                        bl3 = true;
                        object5 = object4;
                        break;
                    }
                    bl3 = bl5;
                    object5 = object4;
                    if (!object4.getClass().isArray()) break;
                    object5 = ArrayBuilders.getArrayComparator(object4);
                    bl3 = bl5;
                    break;
                }
                case 2: {
                    bl3 = true;
                    object5 = BeanPropertyWriter.MARKER_FOR_EMPTY;
                    break;
                }
                case 3: {
                    bl4 = true;
                }
                case 4: {
                    bl3 = bl4;
                    object5 = object4;
                    if (!object6.isContainerType()) break;
                    bl3 = bl4;
                    object5 = object4;
                    if (this._config.isEnabled(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS)) break;
                    object5 = BeanPropertyWriter.MARKER_FOR_EMPTY;
                    bl3 = bl4;
                }
            }
        }
        BeanPropertyWriter beanPropertyWriter = new BeanPropertyWriter(beanPropertyDefinition, (AnnotatedMember)var7_8, this._beanDesc.getClassAnnotations(), (JavaType)object6, var4_5, (TypeSerializer)var5_6, (JavaType)object7, bl3, object5);
        object6 = this._annotationIntrospector.findNullSerializer((Annotated)var7_8);
        if (object6 != null) {
            beanPropertyWriter.assignNullSerializer(object.serializerInstance((Annotated)var7_8, object6));
        }
        object6 = this._annotationIntrospector.findUnwrappingNameTransformer((AnnotatedMember)var7_8);
        object = beanPropertyWriter;
        if (object6 == null) return object;
        return beanPropertyWriter.unwrappingWriter((NameTransformer)object6);
    }

    @Deprecated
    protected final BeanPropertyWriter buildWriter(BeanPropertyDefinition beanPropertyDefinition, JavaType javaType, JsonSerializer<?> jsonSerializer, TypeSerializer typeSerializer, TypeSerializer typeSerializer2, AnnotatedMember annotatedMember, boolean bl2) {
        throw new IllegalStateException();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JavaType findSerializationType(Annotated object, boolean bl2, JavaType javaType) {
        void var3_6;
        void var5_12;
        Class class_ = this._annotationIntrospector.findSerializationType((Annotated)object);
        JavaType javaType2 = javaType;
        if (class_ != null) {
            Class class_2 = javaType.getRawClass();
            if (class_.isAssignableFrom(class_2)) {
                JavaType javaType3 = javaType.widenBy(class_);
            } else {
                if (!class_2.isAssignableFrom(class_)) {
                    throw new IllegalArgumentException("Illegal concrete-type annotation for method '" + object.getName() + "': class " + class_.getName() + " not a super-type of (declared) class " + class_2.getName());
                }
                JavaType javaType4 = this._config.constructSpecializedType(javaType, class_);
            }
            bl2 = true;
        }
        class_ = BasicSerializerFactory.modifySecondaryTypesByAnnotation(this._config, (Annotated)object, var5_12);
        void var3_4 = var5_12;
        if (class_ != var5_12) {
            bl2 = true;
            Class class_3 = class_;
        }
        object = this._annotationIntrospector.findSerializationTyping((Annotated)object);
        boolean bl3 = bl2;
        if (object != null) {
            bl3 = bl2;
            if (object != JsonSerialize.Typing.DEFAULT_TYPING) {
                if (object != JsonSerialize.Typing.STATIC) return null;
                return var3_6;
            }
        }
        if (!bl3) return null;
        return var3_6;
    }

    public Annotations getClassAnnotations() {
        return this._beanDesc.getClassAnnotations();
    }

    protected Object getDefaultBean() {
        if (this._defaultBean == null) {
            this._defaultBean = this._beanDesc.instantiateBean(this._config.canOverrideAccessModifiers());
            if (this._defaultBean == null) {
                AnnotatedElement annotatedElement = this._beanDesc.getClassInfo().getAnnotated();
                throw new IllegalArgumentException("Class " + annotatedElement.getName() + " has no default constructor; can not instantiate default bean value to support 'properties=JsonSerialize.Inclusion.NON_DEFAULT' annotation");
            }
        }
        return this._defaultBean;
    }

    protected Object getDefaultValue(String string2, AnnotatedMember object) {
        Object object2 = this.getDefaultBean();
        try {
            object = object.getValue(object2);
            return object;
        }
        catch (Exception var2_3) {
            return this._throwWrapped(var2_3, string2, object2);
        }
    }

}

