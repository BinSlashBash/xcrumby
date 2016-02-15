/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.Named;
import java.lang.annotation.Annotation;

public interface BeanProperty
extends Named {
    public void depositSchemaProperty(JsonObjectFormatVisitor var1) throws JsonMappingException;

    public <A extends Annotation> A getAnnotation(Class<A> var1);

    public <A extends Annotation> A getContextAnnotation(Class<A> var1);

    public PropertyName getFullName();

    public AnnotatedMember getMember();

    public PropertyMetadata getMetadata();

    @Override
    public String getName();

    public JavaType getType();

    public PropertyName getWrapperName();

    public boolean isRequired();

    public static class Std
    implements BeanProperty {
        protected final Annotations _contextAnnotations;
        protected final AnnotatedMember _member;
        protected final PropertyMetadata _metadata;
        protected final PropertyName _name;
        protected final JavaType _type;
        protected final PropertyName _wrapperName;

        public Std(PropertyName propertyName, JavaType javaType, PropertyName propertyName2, Annotations annotations, AnnotatedMember annotatedMember, PropertyMetadata propertyMetadata) {
            this._name = propertyName;
            this._type = javaType;
            this._wrapperName = propertyName2;
            this._metadata = propertyMetadata;
            this._member = annotatedMember;
            this._contextAnnotations = annotations;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Deprecated
        public Std(String object, JavaType javaType, PropertyName propertyName, Annotations annotations, AnnotatedMember annotatedMember, boolean bl2) {
            PropertyName propertyName2 = new PropertyName((String)object);
            object = bl2 ? PropertyMetadata.STD_REQUIRED : PropertyMetadata.STD_OPTIONAL;
            this(propertyName2, javaType, propertyName, annotations, annotatedMember, (PropertyMetadata)object);
        }

        @Override
        public void depositSchemaProperty(JsonObjectFormatVisitor jsonObjectFormatVisitor) {
            throw new UnsupportedOperationException("Instances of " + this.getClass().getName() + " should not get visited");
        }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> class_) {
            if (this._member == null) {
                return null;
            }
            return this._member.getAnnotation(class_);
        }

        @Override
        public <A extends Annotation> A getContextAnnotation(Class<A> class_) {
            if (this._contextAnnotations == null) {
                return null;
            }
            return this._contextAnnotations.get(class_);
        }

        @Override
        public PropertyName getFullName() {
            return this._name;
        }

        @Override
        public AnnotatedMember getMember() {
            return this._member;
        }

        @Override
        public PropertyMetadata getMetadata() {
            return this._metadata;
        }

        @Override
        public String getName() {
            return this._name.getSimpleName();
        }

        @Override
        public JavaType getType() {
            return this._type;
        }

        @Override
        public PropertyName getWrapperName() {
            return this._wrapperName;
        }

        @Override
        public boolean isRequired() {
            return this._metadata.isRequired();
        }

        public Std withType(JavaType javaType) {
            return new Std(this._name, javaType, this._wrapperName, this._contextAnnotations, this._member, this._metadata);
        }
    }

}

