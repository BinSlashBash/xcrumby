/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.introspect.POJOPropertiesCollector;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasicBeanDescription
extends BeanDescription {
    protected final AnnotationIntrospector _annotationIntrospector;
    protected AnnotatedMember _anyGetter;
    protected AnnotatedMethod _anySetterMethod;
    protected TypeBindings _bindings;
    protected final AnnotatedClass _classInfo;
    protected final MapperConfig<?> _config;
    protected Set<String> _ignoredPropertyNames;
    protected Map<Object, AnnotatedMember> _injectables;
    protected AnnotatedMethod _jsonValueMethod;
    protected ObjectIdInfo _objectIdInfo;
    protected final List<BeanPropertyDefinition> _properties;

    /*
     * Enabled aggressive block sorting
     */
    protected BasicBeanDescription(MapperConfig<?> mapperConfig, JavaType javaType, AnnotatedClass annotatedClass, List<BeanPropertyDefinition> list) {
        void var2_5;
        void var1_3;
        void var3_6;
        void var4_7;
        super((JavaType)var2_5);
        this._config = mapperConfig;
        if (mapperConfig == null) {
            Object var1_2 = null;
        } else {
            AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        }
        this._annotationIntrospector = var1_3;
        this._classInfo = var3_6;
        this._properties = var4_7;
    }

    protected BasicBeanDescription(POJOPropertiesCollector pOJOPropertiesCollector) {
        this(pOJOPropertiesCollector.getConfig(), pOJOPropertiesCollector.getType(), pOJOPropertiesCollector.getClassDef(), pOJOPropertiesCollector.getProperties());
        this._objectIdInfo = pOJOPropertiesCollector.getObjectIdInfo();
    }

    public static BasicBeanDescription forDeserialization(POJOPropertiesCollector pOJOPropertiesCollector) {
        BasicBeanDescription basicBeanDescription = new BasicBeanDescription(pOJOPropertiesCollector);
        basicBeanDescription._anySetterMethod = pOJOPropertiesCollector.getAnySetterMethod();
        basicBeanDescription._ignoredPropertyNames = pOJOPropertiesCollector.getIgnoredPropertyNames();
        basicBeanDescription._injectables = pOJOPropertiesCollector.getInjectables();
        basicBeanDescription._jsonValueMethod = pOJOPropertiesCollector.getJsonValueMethod();
        return basicBeanDescription;
    }

    public static BasicBeanDescription forOtherUse(MapperConfig<?> mapperConfig, JavaType javaType, AnnotatedClass annotatedClass) {
        return new BasicBeanDescription(mapperConfig, javaType, annotatedClass, Collections.<BeanPropertyDefinition>emptyList());
    }

    public static BasicBeanDescription forSerialization(POJOPropertiesCollector pOJOPropertiesCollector) {
        BasicBeanDescription basicBeanDescription = new BasicBeanDescription(pOJOPropertiesCollector);
        basicBeanDescription._jsonValueMethod = pOJOPropertiesCollector.getJsonValueMethod();
        basicBeanDescription._anyGetter = pOJOPropertiesCollector.getAnyGetter();
        return basicBeanDescription;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Converter<Object, Object> _createConverter(Object object) {
        Converter converter = null;
        if (object == null) {
            return null;
        }
        if (object instanceof Converter) {
            return (Converter)object;
        }
        if (!(object instanceof Class)) {
            throw new IllegalStateException("AnnotationIntrospector returned Converter definition of type " + object.getClass().getName() + "; expected type Converter or Class<Converter> instead");
        }
        Class class_ = (Class)object;
        if (class_ == Converter.None.class) return null;
        if (ClassUtil.isBogusClass(class_)) {
            return null;
        }
        if (!Converter.class.isAssignableFrom(class_)) {
            throw new IllegalStateException("AnnotationIntrospector returned Class " + class_.getName() + "; expected Class<Converter>");
        }
        object = this._config.getHandlerInstantiator();
        object = object == null ? converter : object.converterInstance(this._config, this._classInfo, class_);
        converter = object;
        if (object != null) return converter;
        return (Converter)ClassUtil.createInstance(class_, this._config.canOverrideAccessModifiers());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected PropertyName _findCreatorPropertyName(AnnotatedParameter object) {
        PropertyName propertyName;
        PropertyName propertyName2 = this._annotationIntrospector.findNameForDeserialization((Annotated)object);
        if (propertyName2 != null) {
            propertyName = propertyName2;
            if (!propertyName2.isEmpty()) return propertyName;
        }
        object = this._annotationIntrospector.findImplicitPropertyName((AnnotatedMember)object);
        propertyName = propertyName2;
        if (object == null) return propertyName;
        propertyName = propertyName2;
        if (object.isEmpty()) return propertyName;
        return new PropertyName((String)object);
    }

    public LinkedHashMap<String, AnnotatedField> _findPropertyFields(Collection<String> collection, boolean bl2) {
        LinkedHashMap<String, AnnotatedField> linkedHashMap = new LinkedHashMap<String, AnnotatedField>();
        Iterator<BeanPropertyDefinition> iterator = this._properties.iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            AnnotatedField annotatedField = object.getField();
            if (annotatedField == null) continue;
            object = object.getName();
            if (collection != null && collection.contains(object)) continue;
            linkedHashMap.put((String)object, annotatedField);
        }
        return linkedHashMap;
    }

    @Override
    public TypeBindings bindingsForBeanType() {
        if (this._bindings == null) {
            this._bindings = new TypeBindings(this._config.getTypeFactory(), this._type);
        }
        return this._bindings;
    }

    @Override
    public AnnotatedMember findAnyGetter() throws IllegalArgumentException {
        if (this._anyGetter != null && !Map.class.isAssignableFrom(this._anyGetter.getRawType())) {
            throw new IllegalArgumentException("Invalid 'any-getter' annotation on method " + this._anyGetter.getName() + "(): return type is not instance of java.util.Map");
        }
        return this._anyGetter;
    }

    @Override
    public AnnotatedMethod findAnySetter() throws IllegalArgumentException {
        Class class_;
        if (this._anySetterMethod != null && (class_ = this._anySetterMethod.getRawParameterType(0)) != String.class && class_ != Object.class) {
            throw new IllegalArgumentException("Invalid 'any-setter' annotation on method " + this._anySetterMethod.getName() + "(): first argument not of type String or Object, but " + class_.getName());
        }
        return this._anySetterMethod;
    }

    @Override
    public Map<String, AnnotatedMember> findBackReferenceProperties() {
        HashMap<Object, AnnotatedMember> hashMap = null;
        Iterator<BeanPropertyDefinition> iterator = this._properties.iterator();
        while (iterator.hasNext()) {
            Object object;
            AnnotatedMember annotatedMember = iterator.next().getMutator();
            if (annotatedMember == null || (object = this._annotationIntrospector.findReferenceType(annotatedMember)) == null || !object.isBackReference()) continue;
            HashMap<Object, AnnotatedMember> hashMap2 = hashMap;
            if (hashMap == null) {
                hashMap2 = new HashMap<Object, AnnotatedMember>();
            }
            object = object.getName();
            hashMap = hashMap2;
            if (hashMap2.put(object, annotatedMember) == null) continue;
            throw new IllegalArgumentException("Multiple back-reference properties with name '" + (String)object + "'");
        }
        return hashMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    public List<PropertyName> findCreatorParameterNames() {
        int n2 = 0;
        while (n2 < 2) {
            List<AnnotatedConstructor> list = n2 == 0 ? this.getConstructors() : this.getFactoryMethods();
            Object object = list.iterator();
            while (object.hasNext()) {
                AnnotatedWithParams annotatedWithParams = (AnnotatedWithParams)object.next();
                int n3 = annotatedWithParams.getParameterCount();
                if (n3 < 1 || (list = this._findCreatorPropertyName(annotatedWithParams.getParameter(0))) == null || list.isEmpty()) continue;
                object = new ArrayList();
                object.add(list);
                n2 = 1;
                do {
                    list = object;
                    if (n2 >= n3) return list;
                    object.add(this._findCreatorPropertyName(annotatedWithParams.getParameter(n2)));
                    ++n2;
                } while (true);
            }
            ++n2;
        }
        return Collections.emptyList();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Deprecated
    public List<String> findCreatorPropertyNames() {
        List list = this.findCreatorParameterNames();
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<String> arrayList = new ArrayList<String>(list.size());
        Iterator iterator = list.iterator();
        do {
            list = arrayList;
            if (!iterator.hasNext()) return list;
            arrayList.add(((PropertyName)iterator.next()).getSimpleName());
        } while (true);
    }

    @Override
    public AnnotatedConstructor findDefaultConstructor() {
        return this._classInfo.getDefaultConstructor();
    }

    @Override
    public Converter<Object, Object> findDeserializationConverter() {
        if (this._annotationIntrospector == null) {
            return null;
        }
        return this._createConverter(this._annotationIntrospector.findDeserializationConverter(this._classInfo));
    }

    @Override
    public JsonFormat.Value findExpectedFormat(JsonFormat.Value value) {
        JsonFormat.Value value2;
        if (this._annotationIntrospector != null && (value2 = this._annotationIntrospector.findFormat(this._classInfo)) != null) {
            return value2;
        }
        return value;
    }

    @Override
    public /* varargs */ Method findFactoryMethod(Class<?> ... arrclass) {
        for (AnnotatedMethod annotatedMethod : this._classInfo.getStaticMethods()) {
            if (!this.isFactoryMethod(annotatedMethod)) continue;
            Class class_ = annotatedMethod.getRawParameterType(0);
            int n2 = arrclass.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                if (!class_.isAssignableFrom(arrclass[i2])) continue;
                return annotatedMethod.getAnnotated();
            }
        }
        return null;
    }

    @Override
    public Map<Object, AnnotatedMember> findInjectables() {
        return this._injectables;
    }

    @Override
    public AnnotatedMethod findJsonValueMethod() {
        return this._jsonValueMethod;
    }

    @Override
    public AnnotatedMethod findMethod(String string2, Class<?>[] arrclass) {
        return this._classInfo.findMethod(string2, arrclass);
    }

    @Override
    public Class<?> findPOJOBuilder() {
        if (this._annotationIntrospector == null) {
            return null;
        }
        return this._annotationIntrospector.findPOJOBuilder(this._classInfo);
    }

    @Override
    public JsonPOJOBuilder.Value findPOJOBuilderConfig() {
        if (this._annotationIntrospector == null) {
            return null;
        }
        return this._annotationIntrospector.findPOJOBuilderConfig(this._classInfo);
    }

    @Override
    public List<BeanPropertyDefinition> findProperties() {
        return this._properties;
    }

    @Override
    public Converter<Object, Object> findSerializationConverter() {
        if (this._annotationIntrospector == null) {
            return null;
        }
        return this._createConverter(this._annotationIntrospector.findSerializationConverter(this._classInfo));
    }

    @Override
    public JsonInclude.Include findSerializationInclusion(JsonInclude.Include include) {
        if (this._annotationIntrospector == null) {
            return include;
        }
        return this._annotationIntrospector.findSerializationInclusion(this._classInfo, include);
    }

    @Override
    public /* varargs */ Constructor<?> findSingleArgConstructor(Class<?> ... arrclass) {
        for (AnnotatedConstructor annotatedConstructor : this._classInfo.getConstructors()) {
            if (annotatedConstructor.getParameterCount() != 1) continue;
            Class class_ = annotatedConstructor.getRawParameterType(0);
            int n2 = arrclass.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                if (arrclass[i2] != class_) continue;
                return annotatedConstructor.getAnnotated();
            }
        }
        return null;
    }

    @Override
    public Annotations getClassAnnotations() {
        return this._classInfo.getAnnotations();
    }

    @Override
    public AnnotatedClass getClassInfo() {
        return this._classInfo;
    }

    @Override
    public List<AnnotatedConstructor> getConstructors() {
        return this._classInfo.getConstructors();
    }

    @Override
    public List<AnnotatedMethod> getFactoryMethods() {
        List<AnnotatedMethod> list = this._classInfo.getStaticMethods();
        if (list.isEmpty()) {
            return list;
        }
        ArrayList<AnnotatedMethod> arrayList = new ArrayList<AnnotatedMethod>();
        for (AnnotatedMethod annotatedMethod : list) {
            if (!this.isFactoryMethod(annotatedMethod)) continue;
            arrayList.add(annotatedMethod);
        }
        return arrayList;
    }

    @Override
    public Set<String> getIgnoredPropertyNames() {
        if (this._ignoredPropertyNames == null) {
            return Collections.emptySet();
        }
        return this._ignoredPropertyNames;
    }

    @Override
    public ObjectIdInfo getObjectIdInfo() {
        return this._objectIdInfo;
    }

    @Override
    public boolean hasKnownClassAnnotations() {
        return this._classInfo.hasAnnotations();
    }

    @Override
    public Object instantiateBean(boolean bl2) {
        AnnotatedConstructor annotatedConstructor = this._classInfo.getDefaultConstructor();
        if (annotatedConstructor == null) {
            return null;
        }
        if (bl2) {
            annotatedConstructor.fixAccess();
        }
        try {
            annotatedConstructor = annotatedConstructor.getAnnotated().newInstance(new Object[0]);
            return annotatedConstructor;
        }
        catch (Exception var2_3) {
            Throwable throwable;
            while (throwable.getCause() != null) {
                throwable = throwable.getCause();
            }
            if (throwable instanceof Error) {
                throw (Error)throwable;
            }
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException)throwable;
            }
            throw new IllegalArgumentException("Failed to instantiate bean of type " + this._classInfo.getAnnotated().getName() + ": (" + throwable.getClass().getName() + ") " + throwable.getMessage(), throwable);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected boolean isFactoryMethod(AnnotatedMethod annotatedMethod) {
        Object object = annotatedMethod.getRawReturnType();
        if (!this.getBeanClass().isAssignableFrom(object)) {
            return false;
        }
        if (this._annotationIntrospector.hasCreatorAnnotation(annotatedMethod)) {
            return true;
        }
        object = annotatedMethod.getName();
        if ("valueOf".equals(object)) {
            return true;
        }
        if (!"fromString".equals(object)) return false;
        if (1 != annotatedMethod.getParameterCount()) return false;
        Class class_ = annotatedMethod.getRawParameterType(0);
        if (class_ == String.class) return true;
        if (!CharSequence.class.isAssignableFrom(class_)) return false;
        return true;
    }

    public boolean removeProperty(String string2) {
        Iterator<BeanPropertyDefinition> iterator = this._properties.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getName().equals(string2)) continue;
            iterator.remove();
            return true;
        }
        return false;
    }

    @Override
    public JavaType resolveType(Type type) {
        if (type == null) {
            return null;
        }
        return this.bindingsForBeanType().resolveType(type);
    }
}

