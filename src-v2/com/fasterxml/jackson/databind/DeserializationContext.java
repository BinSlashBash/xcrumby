/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.deser.DeserializerCache;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.UnresolvedForwardReference;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.deser.impl.TypeWrappedDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.LinkedNode;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

public abstract class DeserializationContext
extends DatabindContext
implements Serializable {
    private static final int MAX_ERROR_STR_LEN = 500;
    private static final long serialVersionUID = -4290063686213707727L;
    protected transient ArrayBuilders _arrayBuilders;
    protected transient ContextAttributes _attributes;
    protected final DeserializerCache _cache;
    protected final DeserializationConfig _config;
    protected transient DateFormat _dateFormat;
    protected final DeserializerFactory _factory;
    protected final int _featureFlags;
    protected final InjectableValues _injectableValues;
    protected transient ObjectBuffer _objectBuffer;
    protected transient JsonParser _parser;
    protected final Class<?> _view;

    protected DeserializationContext(DeserializationContext deserializationContext, DeserializationConfig deserializationConfig, JsonParser jsonParser, InjectableValues injectableValues) {
        this._cache = deserializationContext._cache;
        this._factory = deserializationContext._factory;
        this._config = deserializationConfig;
        this._featureFlags = deserializationConfig.getDeserializationFeatures();
        this._view = deserializationConfig.getActiveView();
        this._parser = jsonParser;
        this._injectableValues = injectableValues;
        this._attributes = deserializationConfig.getAttributes();
    }

    protected DeserializationContext(DeserializationContext deserializationContext, DeserializerFactory deserializerFactory) {
        this._cache = deserializationContext._cache;
        this._factory = deserializerFactory;
        this._config = deserializationContext._config;
        this._featureFlags = deserializationContext._featureFlags;
        this._view = deserializationContext._view;
        this._parser = deserializationContext._parser;
        this._injectableValues = deserializationContext._injectableValues;
        this._attributes = deserializationContext._attributes;
    }

    protected DeserializationContext(DeserializerFactory deserializerFactory) {
        this(deserializerFactory, null);
    }

    protected DeserializationContext(DeserializerFactory object, DeserializerCache deserializerCache) {
        if (object == null) {
            throw new IllegalArgumentException("Can not pass null DeserializerFactory");
        }
        this._factory = object;
        object = deserializerCache;
        if (deserializerCache == null) {
            object = new DeserializerCache();
        }
        this._cache = object;
        this._featureFlags = 0;
        this._config = null;
        this._injectableValues = null;
        this._view = null;
        this._attributes = null;
    }

    protected String _calcName(Class<?> class_) {
        if (class_.isArray()) {
            return this._calcName(class_.getComponentType()) + "[]";
        }
        return class_.getName();
    }

    protected String _desc(String string2) {
        String string3 = string2;
        if (string2.length() > 500) {
            string3 = string2.substring(0, 500) + "]...[" + string2.substring(string2.length() - 500);
        }
        return string3;
    }

    protected String _valueDesc() {
        try {
            String string2 = this._desc(this._parser.getText());
            return string2;
        }
        catch (Exception var1_2) {
            return "[N/A]";
        }
    }

    public abstract void checkUnresolvedObjectId() throws UnresolvedForwardReference;

    public Calendar constructCalendar(Date date) {
        Calendar calendar = Calendar.getInstance(this.getTimeZone());
        calendar.setTime(date);
        return calendar;
    }

    public final JavaType constructType(Class<?> class_) {
        return this._config.constructType(class_);
    }

    public abstract JsonDeserializer<Object> deserializerInstance(Annotated var1, Object var2) throws JsonMappingException;

    protected String determineClassName(Object object) {
        return ClassUtil.getClassDescription(object);
    }

    public JsonMappingException endOfInputException(Class<?> class_) {
        return JsonMappingException.from(this._parser, "Unexpected end-of-input when trying to deserialize a " + class_.getName());
    }

    public Class<?> findClass(String string2) throws ClassNotFoundException {
        return ClassUtil.findClass(string2);
    }

    public final JsonDeserializer<Object> findContextualValueDeserializer(JavaType jsonDeserializer, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer<Object> jsonDeserializer2;
        jsonDeserializer = jsonDeserializer2 = this._cache.findValueDeserializer(this, this._factory, (JavaType)((Object)jsonDeserializer));
        if (jsonDeserializer2 != null) {
            jsonDeserializer = this.handleSecondaryContextualization(jsonDeserializer2, beanProperty);
        }
        return jsonDeserializer;
    }

    public final Object findInjectableValue(Object object, BeanProperty beanProperty, Object object2) {
        if (this._injectableValues == null) {
            throw new IllegalStateException("No 'injectableValues' configured, can not inject value with id [" + object + "]");
        }
        return this._injectableValues.findInjectableValue(object, this, beanProperty, object2);
    }

    public final KeyDeserializer findKeyDeserializer(JavaType object, BeanProperty beanProperty) throws JsonMappingException {
        KeyDeserializer keyDeserializer;
        object = keyDeserializer = this._cache.findKeyDeserializer(this, this._factory, (JavaType)object);
        if (keyDeserializer instanceof ContextualKeyDeserializer) {
            object = ((ContextualKeyDeserializer)((Object)keyDeserializer)).createContextual(this, beanProperty);
        }
        return object;
    }

    @Deprecated
    public abstract ReadableObjectId findObjectId(Object var1, ObjectIdGenerator<?> var2);

    public abstract ReadableObjectId findObjectId(Object var1, ObjectIdGenerator<?> var2, ObjectIdResolver var3);

    public final JsonDeserializer<Object> findRootValueDeserializer(JavaType object) throws JsonMappingException {
        JsonDeserializer jsonDeserializer = this._cache.findValueDeserializer(this, this._factory, (JavaType)object);
        if (jsonDeserializer == null) {
            return null;
        }
        jsonDeserializer = this.handleSecondaryContextualization(jsonDeserializer, null);
        if ((object = this._factory.findTypeDeserializer(this._config, (JavaType)object)) != null) {
            return new TypeWrappedDeserializer(object.forProperty(null), jsonDeserializer);
        }
        return jsonDeserializer;
    }

    @Override
    public final Class<?> getActiveView() {
        return this._view;
    }

    @Override
    public final AnnotationIntrospector getAnnotationIntrospector() {
        return this._config.getAnnotationIntrospector();
    }

    public final ArrayBuilders getArrayBuilders() {
        if (this._arrayBuilders == null) {
            this._arrayBuilders = new ArrayBuilders();
        }
        return this._arrayBuilders;
    }

    @Override
    public Object getAttribute(Object object) {
        return this._attributes.getAttribute(object);
    }

    public final Base64Variant getBase64Variant() {
        return this._config.getBase64Variant();
    }

    public DeserializationConfig getConfig() {
        return this._config;
    }

    protected DateFormat getDateFormat() {
        DateFormat dateFormat;
        if (this._dateFormat != null) {
            return this._dateFormat;
        }
        this._dateFormat = dateFormat = (DateFormat)this._config.getDateFormat().clone();
        return dateFormat;
    }

    public DeserializerFactory getFactory() {
        return this._factory;
    }

    public Locale getLocale() {
        return this._config.getLocale();
    }

    public final JsonNodeFactory getNodeFactory() {
        return this._config.getNodeFactory();
    }

    public final JsonParser getParser() {
        return this._parser;
    }

    public TimeZone getTimeZone() {
        return this._config.getTimeZone();
    }

    @Override
    public final TypeFactory getTypeFactory() {
        return this._config.getTypeFactory();
    }

    public JsonDeserializer<?> handlePrimaryContextualization(JsonDeserializer<?> jsonDeserializer, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer<?> jsonDeserializer2 = jsonDeserializer;
        if (jsonDeserializer != null) {
            jsonDeserializer2 = jsonDeserializer;
            if (jsonDeserializer instanceof ContextualDeserializer) {
                jsonDeserializer2 = ((ContextualDeserializer)((Object)jsonDeserializer)).createContextual(this, beanProperty);
            }
        }
        return jsonDeserializer2;
    }

    public JsonDeserializer<?> handleSecondaryContextualization(JsonDeserializer<?> jsonDeserializer, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer<?> jsonDeserializer2 = jsonDeserializer;
        if (jsonDeserializer != null) {
            jsonDeserializer2 = jsonDeserializer;
            if (jsonDeserializer instanceof ContextualDeserializer) {
                jsonDeserializer2 = ((ContextualDeserializer)((Object)jsonDeserializer)).createContextual(this, beanProperty);
            }
        }
        return jsonDeserializer2;
    }

    public boolean handleUnknownProperty(JsonParser jsonParser, JsonDeserializer<?> jsonDeserializer, Object object, String string2) throws IOException, JsonProcessingException {
        LinkedNode<DeserializationProblemHandler> linkedNode = this._config.getProblemHandlers();
        if (linkedNode != null) {
            while (linkedNode != null) {
                if (linkedNode.value().handleUnknownProperty(this, jsonParser, jsonDeserializer, object, string2)) {
                    return true;
                }
                linkedNode = linkedNode.next();
            }
        }
        return false;
    }

    public final boolean hasDeserializationFeatures(int n2) {
        return this._config.hasDeserializationFeatures(n2);
    }

    @Deprecated
    public boolean hasValueDeserializerFor(JavaType javaType) {
        return this.hasValueDeserializerFor(javaType, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean hasValueDeserializerFor(JavaType javaType, AtomicReference<Throwable> atomicReference) {
        try {
            return this._cache.hasValueDeserializerFor(this, this._factory, javaType);
        }
        catch (JsonMappingException var1_2) {
            if (atomicReference == null) return false;
            atomicReference.set(var1_2);
            do {
                return false;
                break;
            } while (true);
        }
        catch (RuntimeException var1_3) {
            if (atomicReference == null) {
                throw var1_3;
            }
            atomicReference.set(var1_3);
            return false;
        }
    }

    public JsonMappingException instantiationException(Class<?> class_, String string2) {
        return JsonMappingException.from(this._parser, "Can not construct instance of " + class_.getName() + ", problem: " + string2);
    }

    public JsonMappingException instantiationException(Class<?> class_, Throwable throwable) {
        return JsonMappingException.from(this._parser, "Can not construct instance of " + class_.getName() + ", problem: " + throwable.getMessage(), throwable);
    }

    public final boolean isEnabled(DeserializationFeature deserializationFeature) {
        if ((this._featureFlags & deserializationFeature.getMask()) != 0) {
            return true;
        }
        return false;
    }

    public abstract KeyDeserializer keyDeserializerInstance(Annotated var1, Object var2) throws JsonMappingException;

    public final ObjectBuffer leaseObjectBuffer() {
        ObjectBuffer objectBuffer = this._objectBuffer;
        if (objectBuffer == null) {
            return new ObjectBuffer();
        }
        this._objectBuffer = null;
        return objectBuffer;
    }

    public JsonMappingException mappingException(Class<?> class_) {
        return this.mappingException(class_, this._parser.getCurrentToken());
    }

    public JsonMappingException mappingException(Class<?> class_, JsonToken jsonToken) {
        return JsonMappingException.from(this._parser, "Can not deserialize instance of " + this._calcName(class_) + " out of " + (Object)((Object)jsonToken) + " token");
    }

    public JsonMappingException mappingException(String string2) {
        return JsonMappingException.from(this.getParser(), string2);
    }

    public Date parseDate(String string2) throws IllegalArgumentException {
        try {
            Date date = this.getDateFormat().parse(string2);
            return date;
        }
        catch (ParseException var2_3) {
            throw new IllegalArgumentException("Failed to parse Date value '" + string2 + "': " + var2_3.getMessage());
        }
    }

    public <T> T readPropertyValue(JsonParser jsonParser, BeanProperty object, JavaType javaType) throws IOException {
        if ((object = this.findContextualValueDeserializer(javaType, (BeanProperty)object)) == null) {
            // empty if block
        }
        return object.deserialize(jsonParser, this);
    }

    public <T> T readPropertyValue(JsonParser jsonParser, BeanProperty beanProperty, Class<T> class_) throws IOException {
        return this.readPropertyValue(jsonParser, beanProperty, this.getTypeFactory().constructType(class_));
    }

    public <T> T readValue(JsonParser jsonParser, JavaType object) throws IOException {
        if ((object = this.findRootValueDeserializer((JavaType)object)) == null) {
            // empty if block
        }
        return object.deserialize(jsonParser, this);
    }

    public <T> T readValue(JsonParser jsonParser, Class<T> class_) throws IOException {
        return this.readValue(jsonParser, this.getTypeFactory().constructType(class_));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void reportUnknownProperty(Object object, String string2, JsonDeserializer<?> collection) throws JsonMappingException {
        if (!this.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
            return;
        }
        if (collection == null) {
            collection = null;
            do {
                throw UnrecognizedPropertyException.from(this._parser, object, string2, collection);
                break;
            } while (true);
        }
        collection = collection.getKnownPropertyNames();
        throw UnrecognizedPropertyException.from(this._parser, object, string2, collection);
    }

    public final void returnObjectBuffer(ObjectBuffer objectBuffer) {
        if (this._objectBuffer == null || objectBuffer.initialCapacity() >= this._objectBuffer.initialCapacity()) {
            this._objectBuffer = objectBuffer;
        }
    }

    @Override
    public DeserializationContext setAttribute(Object object, Object object2) {
        this._attributes = this._attributes.withPerCallAttribute(object, object2);
        return this;
    }

    public JsonMappingException unknownTypeException(JavaType javaType, String string2) {
        return JsonMappingException.from(this._parser, "Could not resolve type id '" + string2 + "' into a subtype of " + javaType);
    }

    public JsonMappingException weirdKeyException(Class<?> class_, String string2, String string3) {
        return InvalidFormatException.from(this._parser, "Can not construct Map key of type " + class_.getName() + " from String \"" + this._desc(string2) + "\": " + string3, string2, class_);
    }

    @Deprecated
    public JsonMappingException weirdNumberException(Class<?> class_, String string2) {
        return this.weirdStringException(null, class_, string2);
    }

    public JsonMappingException weirdNumberException(Number number, Class<?> class_, String string2) {
        return InvalidFormatException.from(this._parser, "Can not construct instance of " + class_.getName() + " from number value (" + this._valueDesc() + "): " + string2, null, class_);
    }

    @Deprecated
    public JsonMappingException weirdStringException(Class<?> class_, String string2) {
        return this.weirdStringException(null, class_, string2);
    }

    public JsonMappingException weirdStringException(String string2, Class<?> class_, String string3) {
        return InvalidFormatException.from(this._parser, "Can not construct instance of " + class_.getName() + " from String value '" + this._valueDesc() + "': " + string3, string2, class_);
    }

    public JsonMappingException wrongTokenException(JsonParser jsonParser, JsonToken object, String string2) {
        String string3;
        object = string3 = "Unexpected token (" + (Object)((Object)jsonParser.getCurrentToken()) + "), expected " + object;
        if (string2 != null) {
            object = string3 + ": " + string2;
        }
        return JsonMappingException.from(jsonParser, (String)object);
    }
}

