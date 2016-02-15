/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.deser.AbstractDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BuilderBasedDeserializer;
import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;
import com.fasterxml.jackson.databind.deser.impl.ValueInjector;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.Annotations;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BeanDeserializerBuilder {
    protected SettableAnyProperty _anySetter;
    protected HashMap<String, SettableBeanProperty> _backRefProperties;
    protected final BeanDescription _beanDesc;
    protected AnnotatedMethod _buildMethod;
    protected JsonPOJOBuilder.Value _builderConfig;
    protected final boolean _defaultViewInclusion;
    protected HashSet<String> _ignorableProps;
    protected boolean _ignoreAllUnknown;
    protected List<ValueInjector> _injectables;
    protected ObjectIdReader _objectIdReader;
    protected final Map<String, SettableBeanProperty> _properties = new LinkedHashMap<String, SettableBeanProperty>();
    protected ValueInstantiator _valueInstantiator;

    public BeanDeserializerBuilder(BeanDescription beanDescription, DeserializationConfig deserializationConfig) {
        this._beanDesc = beanDescription;
        this._defaultViewInclusion = deserializationConfig.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
    }

    protected BeanDeserializerBuilder(BeanDeserializerBuilder beanDeserializerBuilder) {
        this._beanDesc = beanDeserializerBuilder._beanDesc;
        this._defaultViewInclusion = beanDeserializerBuilder._defaultViewInclusion;
        this._properties.putAll(beanDeserializerBuilder._properties);
        this._injectables = BeanDeserializerBuilder._copy(beanDeserializerBuilder._injectables);
        this._backRefProperties = BeanDeserializerBuilder._copy(beanDeserializerBuilder._backRefProperties);
        this._ignorableProps = beanDeserializerBuilder._ignorableProps;
        this._valueInstantiator = beanDeserializerBuilder._valueInstantiator;
        this._objectIdReader = beanDeserializerBuilder._objectIdReader;
        this._anySetter = beanDeserializerBuilder._anySetter;
        this._ignoreAllUnknown = beanDeserializerBuilder._ignoreAllUnknown;
        this._buildMethod = beanDeserializerBuilder._buildMethod;
        this._builderConfig = beanDeserializerBuilder._builderConfig;
    }

    private static HashMap<String, SettableBeanProperty> _copy(HashMap<String, SettableBeanProperty> hashMap) {
        if (hashMap == null) {
            return null;
        }
        return new HashMap<String, SettableBeanProperty>(hashMap);
    }

    private static <T> List<T> _copy(List<T> list) {
        if (list == null) {
            return null;
        }
        return new ArrayList<T>(list);
    }

    public void addBackReferenceProperty(String string2, SettableBeanProperty settableBeanProperty) {
        if (this._backRefProperties == null) {
            this._backRefProperties = new HashMap(4);
        }
        this._backRefProperties.put(string2, settableBeanProperty);
        if (this._properties != null) {
            this._properties.remove(settableBeanProperty.getName());
        }
    }

    public void addCreatorProperty(SettableBeanProperty settableBeanProperty) {
        this.addProperty(settableBeanProperty);
    }

    public void addIgnorable(String string2) {
        if (this._ignorableProps == null) {
            this._ignorableProps = new HashSet();
        }
        this._ignorableProps.add(string2);
    }

    public void addInjectable(PropertyName propertyName, JavaType javaType, Annotations annotations, AnnotatedMember annotatedMember, Object object) {
        if (this._injectables == null) {
            this._injectables = new ArrayList<ValueInjector>();
        }
        this._injectables.add(new ValueInjector(propertyName, javaType, annotations, annotatedMember, object));
    }

    @Deprecated
    public void addInjectable(String string2, JavaType javaType, Annotations annotations, AnnotatedMember annotatedMember, Object object) {
        this.addInjectable(new PropertyName(string2), javaType, annotations, annotatedMember, object);
    }

    public void addOrReplaceProperty(SettableBeanProperty settableBeanProperty, boolean bl2) {
        this._properties.put(settableBeanProperty.getName(), settableBeanProperty);
    }

    public void addProperty(SettableBeanProperty settableBeanProperty) {
        SettableBeanProperty settableBeanProperty2 = this._properties.put(settableBeanProperty.getName(), settableBeanProperty);
        if (settableBeanProperty2 != null && settableBeanProperty2 != settableBeanProperty) {
            throw new IllegalArgumentException("Duplicate property '" + settableBeanProperty.getName() + "' for " + this._beanDesc.getType());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonDeserializer<?> build() {
        Object object;
        boolean bl2;
        BeanPropertyMap beanPropertyMap;
        block3 : {
            object = this._properties.values();
            beanPropertyMap = new BeanPropertyMap((Collection<SettableBeanProperty>)object);
            beanPropertyMap.assignIndexes();
            boolean bl3 = !this._defaultViewInclusion;
            bl2 = bl3;
            if (!bl3) {
                object = object.iterator();
                do {
                    bl2 = bl3;
                    if (!object.hasNext()) break block3;
                } while (!((SettableBeanProperty)object.next()).hasViews());
                bl2 = true;
            }
        }
        object = beanPropertyMap;
        if (this._objectIdReader != null) {
            object = beanPropertyMap.withProperty(new ObjectIdValueProperty(this._objectIdReader, PropertyMetadata.STD_REQUIRED));
        }
        return new BeanDeserializer(this, this._beanDesc, (BeanPropertyMap)object, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, bl2);
    }

    public AbstractDeserializer buildAbstract() {
        return new AbstractDeserializer(this, this._beanDesc, this._backRefProperties);
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonDeserializer<?> buildBuilderBased(JavaType object, String class_) {
        boolean bl2;
        block5 : {
            if (this._buildMethod == null) {
                throw new IllegalArgumentException("Builder class " + this._beanDesc.getBeanClass().getName() + " does not have build method '" + (String)((Object)class_) + "()'");
            }
            class_ = this._buildMethod.getRawReturnType();
            if (!object.getRawClass().isAssignableFrom(class_)) {
                throw new IllegalArgumentException("Build method '" + this._buildMethod.getFullName() + " has bad return type (" + class_.getName() + "), not compatible with POJO type (" + object.getRawClass().getName() + ")");
            }
            object = this._properties.values();
            class_ = new BeanPropertyMap((Collection<SettableBeanProperty>)object);
            class_.assignIndexes();
            boolean bl3 = !this._defaultViewInclusion;
            bl2 = bl3;
            if (!bl3) {
                object = object.iterator();
                do {
                    bl2 = bl3;
                    if (!object.hasNext()) break block5;
                } while (!object.next().hasViews());
                bl2 = true;
            }
        }
        object = class_;
        if (this._objectIdReader != null) {
            object = class_.withProperty(new ObjectIdValueProperty(this._objectIdReader, PropertyMetadata.STD_REQUIRED));
        }
        return new BuilderBasedDeserializer(this, this._beanDesc, (BeanPropertyMap)object, (Map<String, SettableBeanProperty>)this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, bl2);
    }

    public SettableBeanProperty findProperty(PropertyName propertyName) {
        return this._properties.get(propertyName.getSimpleName());
    }

    @Deprecated
    public SettableBeanProperty findProperty(String string2) {
        return this._properties.get(string2);
    }

    public SettableAnyProperty getAnySetter() {
        return this._anySetter;
    }

    public AnnotatedMethod getBuildMethod() {
        return this._buildMethod;
    }

    public JsonPOJOBuilder.Value getBuilderConfig() {
        return this._builderConfig;
    }

    public List<ValueInjector> getInjectables() {
        return this._injectables;
    }

    public ObjectIdReader getObjectIdReader() {
        return this._objectIdReader;
    }

    public Iterator<SettableBeanProperty> getProperties() {
        return this._properties.values().iterator();
    }

    public ValueInstantiator getValueInstantiator() {
        return this._valueInstantiator;
    }

    public boolean hasProperty(PropertyName propertyName) {
        if (this.findProperty(propertyName) != null) {
            return true;
        }
        return false;
    }

    @Deprecated
    public boolean hasProperty(String string2) {
        if (this.findProperty(string2) != null) {
            return true;
        }
        return false;
    }

    public SettableBeanProperty removeProperty(PropertyName propertyName) {
        return this._properties.remove(propertyName.getSimpleName());
    }

    @Deprecated
    public SettableBeanProperty removeProperty(String string2) {
        return this._properties.remove(string2);
    }

    public void setAnySetter(SettableAnyProperty settableAnyProperty) {
        if (this._anySetter != null && settableAnyProperty != null) {
            throw new IllegalStateException("_anySetter already set to non-null");
        }
        this._anySetter = settableAnyProperty;
    }

    public void setIgnoreUnknownProperties(boolean bl2) {
        this._ignoreAllUnknown = bl2;
    }

    public void setObjectIdReader(ObjectIdReader objectIdReader) {
        this._objectIdReader = objectIdReader;
    }

    public void setPOJOBuilder(AnnotatedMethod annotatedMethod, JsonPOJOBuilder.Value value) {
        this._buildMethod = annotatedMethod;
        this._builderConfig = value;
    }

    public void setValueInstantiator(ValueInstantiator valueInstantiator) {
        this._valueInstantiator = valueInstantiator;
    }
}

