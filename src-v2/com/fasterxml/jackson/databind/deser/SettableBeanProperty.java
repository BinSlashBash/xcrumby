/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.impl.FailingDeserializer;
import com.fasterxml.jackson.databind.deser.impl.NullProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ViewMatcher;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;

public abstract class SettableBeanProperty
implements BeanProperty,
Serializable {
    protected static final JsonDeserializer<Object> MISSING_VALUE_DESERIALIZER = new FailingDeserializer("No _valueDeserializer assigned");
    protected final transient Annotations _contextAnnotations;
    protected String _managedReferenceName;
    protected final PropertyMetadata _metadata;
    protected final NullProvider _nullProvider;
    protected ObjectIdInfo _objectIdInfo;
    protected final PropertyName _propName;
    protected int _propertyIndex;
    protected final JavaType _type;
    protected final JsonDeserializer<Object> _valueDeserializer;
    protected final TypeDeserializer _valueTypeDeserializer;
    protected ViewMatcher _viewMatcher;
    protected final PropertyName _wrapperName;

    /*
     * Enabled aggressive block sorting
     */
    protected SettableBeanProperty(PropertyName propertyName, JavaType javaType, PropertyMetadata propertyMetadata, JsonDeserializer<Object> jsonDeserializer) {
        this._propertyIndex = -1;
        this._propName = propertyName == null ? PropertyName.NO_NAME : propertyName.internSimpleName();
        this._type = javaType;
        this._wrapperName = null;
        this._metadata = propertyMetadata;
        this._contextAnnotations = null;
        this._viewMatcher = null;
        this._nullProvider = null;
        this._valueTypeDeserializer = null;
        this._valueDeserializer = jsonDeserializer;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected SettableBeanProperty(PropertyName object, JavaType javaType, PropertyName propertyName, TypeDeserializer typeDeserializer, Annotations annotations, PropertyMetadata propertyMetadata) {
        this._propertyIndex = -1;
        this._propName = object == null ? PropertyName.NO_NAME : object.internSimpleName();
        this._type = javaType;
        this._wrapperName = propertyName;
        this._metadata = propertyMetadata;
        this._contextAnnotations = annotations;
        this._viewMatcher = null;
        this._nullProvider = null;
        object = typeDeserializer;
        if (typeDeserializer != null) {
            object = typeDeserializer.forProperty(this);
        }
        this._valueTypeDeserializer = object;
        this._valueDeserializer = MISSING_VALUE_DESERIALIZER;
    }

    protected SettableBeanProperty(SettableBeanProperty settableBeanProperty) {
        this._propertyIndex = -1;
        this._propName = settableBeanProperty._propName;
        this._type = settableBeanProperty._type;
        this._wrapperName = settableBeanProperty._wrapperName;
        this._metadata = settableBeanProperty._metadata;
        this._contextAnnotations = settableBeanProperty._contextAnnotations;
        this._valueDeserializer = settableBeanProperty._valueDeserializer;
        this._valueTypeDeserializer = settableBeanProperty._valueTypeDeserializer;
        this._nullProvider = settableBeanProperty._nullProvider;
        this._managedReferenceName = settableBeanProperty._managedReferenceName;
        this._propertyIndex = settableBeanProperty._propertyIndex;
        this._viewMatcher = settableBeanProperty._viewMatcher;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected SettableBeanProperty(SettableBeanProperty settableBeanProperty, JsonDeserializer<?> jsonDeserializer) {
        NullProvider nullProvider = null;
        this._propertyIndex = -1;
        this._propName = settableBeanProperty._propName;
        this._type = settableBeanProperty._type;
        this._wrapperName = settableBeanProperty._wrapperName;
        this._metadata = settableBeanProperty._metadata;
        this._contextAnnotations = settableBeanProperty._contextAnnotations;
        this._valueTypeDeserializer = settableBeanProperty._valueTypeDeserializer;
        this._managedReferenceName = settableBeanProperty._managedReferenceName;
        this._propertyIndex = settableBeanProperty._propertyIndex;
        if (jsonDeserializer == null) {
            this._nullProvider = null;
            this._valueDeserializer = MISSING_VALUE_DESERIALIZER;
        } else {
            Object obj = jsonDeserializer.getNullValue();
            if (obj != null) {
                nullProvider = new NullProvider(this._type, obj);
            }
            this._nullProvider = nullProvider;
            this._valueDeserializer = jsonDeserializer;
        }
        this._viewMatcher = settableBeanProperty._viewMatcher;
    }

    protected SettableBeanProperty(SettableBeanProperty settableBeanProperty, PropertyName propertyName) {
        this._propertyIndex = -1;
        this._propName = propertyName;
        this._type = settableBeanProperty._type;
        this._wrapperName = settableBeanProperty._wrapperName;
        this._metadata = settableBeanProperty._metadata;
        this._contextAnnotations = settableBeanProperty._contextAnnotations;
        this._valueDeserializer = settableBeanProperty._valueDeserializer;
        this._valueTypeDeserializer = settableBeanProperty._valueTypeDeserializer;
        this._nullProvider = settableBeanProperty._nullProvider;
        this._managedReferenceName = settableBeanProperty._managedReferenceName;
        this._propertyIndex = settableBeanProperty._propertyIndex;
        this._viewMatcher = settableBeanProperty._viewMatcher;
    }

    @Deprecated
    protected SettableBeanProperty(SettableBeanProperty settableBeanProperty, String string2) {
        this(settableBeanProperty, new PropertyName(string2));
    }

    protected SettableBeanProperty(BeanPropertyDefinition beanPropertyDefinition, JavaType javaType, TypeDeserializer typeDeserializer, Annotations annotations) {
        this(beanPropertyDefinition.getFullName(), javaType, beanPropertyDefinition.getWrapperName(), typeDeserializer, annotations, beanPropertyDefinition.getMetadata());
    }

    @Deprecated
    protected SettableBeanProperty(String string2, JavaType javaType, PropertyName propertyName, TypeDeserializer typeDeserializer, Annotations annotations) {
        this(new PropertyName(string2), javaType, propertyName, typeDeserializer, annotations, PropertyMetadata.STD_OPTIONAL);
    }

    @Deprecated
    protected SettableBeanProperty(String string2, JavaType javaType, PropertyName propertyName, TypeDeserializer typeDeserializer, Annotations annotations, boolean bl2) {
        this(new PropertyName(string2), javaType, propertyName, typeDeserializer, annotations, PropertyMetadata.construct(bl2, null, null));
    }

    protected IOException _throwAsIOE(Exception throwable) throws IOException {
        if (throwable instanceof IOException) {
            throw (IOException)throwable;
        }
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
        }
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        throw new JsonMappingException(throwable.getMessage(), null, throwable);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _throwAsIOE(Exception exception, Object object) throws IOException {
        if (!(exception instanceof IllegalArgumentException)) {
            this._throwAsIOE(exception);
            return;
        }
        object = object == null ? "[NULL]" : object.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder("Problem deserializing property '").append(this.getName());
        stringBuilder.append("' (expected type: ").append(this.getType());
        stringBuilder.append("; actual type: ").append((String)object).append(")");
        object = exception.getMessage();
        if (object != null) {
            stringBuilder.append(", problem: ").append((String)object);
            throw new JsonMappingException(stringBuilder.toString(), null, exception);
        }
        stringBuilder.append(" (no error message provided)");
        throw new JsonMappingException(stringBuilder.toString(), null, exception);
    }

    public void assignIndex(int n2) {
        if (this._propertyIndex != -1) {
            throw new IllegalStateException("Property '" + this.getName() + "' already had index (" + this._propertyIndex + "), trying to assign " + n2);
        }
        this._propertyIndex = n2;
    }

    @Override
    public void depositSchemaProperty(JsonObjectFormatVisitor jsonObjectFormatVisitor) throws JsonMappingException {
        if (this.isRequired()) {
            jsonObjectFormatVisitor.property(this);
            return;
        }
        jsonObjectFormatVisitor.optionalProperty(this);
    }

    public final Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
            if (this._nullProvider == null) {
                return null;
            }
            return this._nullProvider.nullValue(deserializationContext);
        }
        if (this._valueTypeDeserializer != null) {
            return this._valueDeserializer.deserializeWithType(jsonParser, deserializationContext, this._valueTypeDeserializer);
        }
        return this._valueDeserializer.deserialize(jsonParser, deserializationContext);
    }

    public abstract void deserializeAndSet(JsonParser var1, DeserializationContext var2, Object var3) throws IOException, JsonProcessingException;

    public abstract Object deserializeSetAndReturn(JsonParser var1, DeserializationContext var2, Object var3) throws IOException, JsonProcessingException;

    @Override
    public abstract <A extends Annotation> A getAnnotation(Class<A> var1);

    @Override
    public <A extends Annotation> A getContextAnnotation(Class<A> class_) {
        return this._contextAnnotations.get(class_);
    }

    public int getCreatorIndex() {
        return -1;
    }

    protected final Class<?> getDeclaringClass() {
        return this.getMember().getDeclaringClass();
    }

    @Override
    public PropertyName getFullName() {
        return this._propName;
    }

    public Object getInjectableValueId() {
        return null;
    }

    public String getManagedReferenceName() {
        return this._managedReferenceName;
    }

    @Override
    public abstract AnnotatedMember getMember();

    @Override
    public PropertyMetadata getMetadata() {
        return this._metadata;
    }

    @Override
    public final String getName() {
        return this._propName.getSimpleName();
    }

    public ObjectIdInfo getObjectIdInfo() {
        return this._objectIdInfo;
    }

    public int getPropertyIndex() {
        return this._propertyIndex;
    }

    @Override
    public JavaType getType() {
        return this._type;
    }

    public JsonDeserializer<Object> getValueDeserializer() {
        JsonDeserializer<Object> jsonDeserializer;
        JsonDeserializer<Object> jsonDeserializer2 = jsonDeserializer = this._valueDeserializer;
        if (jsonDeserializer == MISSING_VALUE_DESERIALIZER) {
            jsonDeserializer2 = null;
        }
        return jsonDeserializer2;
    }

    public TypeDeserializer getValueTypeDeserializer() {
        return this._valueTypeDeserializer;
    }

    @Override
    public PropertyName getWrapperName() {
        return this._wrapperName;
    }

    public boolean hasValueDeserializer() {
        if (this._valueDeserializer != null && this._valueDeserializer != MISSING_VALUE_DESERIALIZER) {
            return true;
        }
        return false;
    }

    public boolean hasValueTypeDeserializer() {
        if (this._valueTypeDeserializer != null) {
            return true;
        }
        return false;
    }

    public boolean hasViews() {
        if (this._viewMatcher != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isRequired() {
        return this._metadata.isRequired();
    }

    public abstract void set(Object var1, Object var2) throws IOException;

    public abstract Object setAndReturn(Object var1, Object var2) throws IOException;

    public void setManagedReferenceName(String string2) {
        this._managedReferenceName = string2;
    }

    public void setObjectIdInfo(ObjectIdInfo objectIdInfo) {
        this._objectIdInfo = objectIdInfo;
    }

    public void setViews(Class<?>[] arrclass) {
        if (arrclass == null) {
            this._viewMatcher = null;
            return;
        }
        this._viewMatcher = ViewMatcher.construct(arrclass);
    }

    public String toString() {
        return "[property '" + this.getName() + "']";
    }

    public boolean visibleInView(Class<?> class_) {
        if (this._viewMatcher == null || this._viewMatcher.isVisibleForView(class_)) {
            return true;
        }
        return false;
    }

    public abstract SettableBeanProperty withName(PropertyName var1);

    @Deprecated
    public SettableBeanProperty withName(String string2) {
        return this.withName(new PropertyName(string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public SettableBeanProperty withSimpleName(String object) {
        object = this._propName == null ? new PropertyName((String)object) : this._propName.withSimpleName((String)object);
        if (object == this._propName) {
            return this;
        }
        return this.withName((PropertyName)object);
    }

    public abstract SettableBeanProperty withValueDeserializer(JsonDeserializer<?> var1);
}

