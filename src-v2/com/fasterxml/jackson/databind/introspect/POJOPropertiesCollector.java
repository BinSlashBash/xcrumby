/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class POJOPropertiesCollector {
    protected final AnnotationIntrospector _annotationIntrospector;
    protected LinkedList<AnnotatedMember> _anyGetters;
    protected LinkedList<AnnotatedMethod> _anySetters;
    protected final AnnotatedClass _classDef;
    protected final MapperConfig<?> _config;
    protected LinkedList<POJOPropertyBuilder> _creatorProperties;
    protected final boolean _forSerialization;
    protected HashSet<String> _ignoredPropertyNames;
    protected LinkedHashMap<Object, AnnotatedMember> _injectables;
    protected LinkedList<AnnotatedMethod> _jsonValueGetters;
    protected final String _mutatorPrefix;
    protected final LinkedHashMap<String, POJOPropertyBuilder> _properties;
    protected final JavaType _type;
    protected final VisibilityChecker<?> _visibilityChecker;

    protected POJOPropertiesCollector(MapperConfig<?> mapperConfig, boolean bl2, JavaType object, AnnotatedClass annotatedClass, String string2) {
        Object var6_6 = null;
        this._properties = new LinkedHashMap();
        this._creatorProperties = null;
        this._anyGetters = null;
        this._anySetters = null;
        this._jsonValueGetters = null;
        this._config = mapperConfig;
        this._forSerialization = bl2;
        this._type = object;
        this._classDef = annotatedClass;
        object = string2;
        if (string2 == null) {
            object = "set";
        }
        this._mutatorPrefix = object;
        object = var6_6;
        if (mapperConfig.isAnnotationProcessingEnabled()) {
            object = this._config.getAnnotationIntrospector();
        }
        this._annotationIntrospector = object;
        if (this._annotationIntrospector == null) {
            this._visibilityChecker = this._config.getDefaultVisibilityChecker();
            return;
        }
        this._visibilityChecker = this._annotationIntrospector.findAutoDetectVisibility(annotatedClass, this._config.getDefaultVisibilityChecker());
    }

    private void _addIgnored(String string2) {
        if (!this._forSerialization) {
            if (this._ignoredPropertyNames == null) {
                this._ignoredPropertyNames = new HashSet();
            }
            this._ignoredPropertyNames.add(string2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private PropertyNamingStrategy _findNamingStrategy() {
        if (this._annotationIntrospector == null) {
            return this._config.getPropertyNamingStrategy();
        }
        Object object = this._annotationIntrospector.findNamingStrategy(this._classDef);
        if (object == null) {
            return this._config.getPropertyNamingStrategy();
        }
        if (object instanceof PropertyNamingStrategy) {
            return (PropertyNamingStrategy)object;
        }
        if (!(object instanceof Class)) {
            throw new IllegalStateException("AnnotationIntrospector returned PropertyNamingStrategy definition of type " + object.getClass().getName() + "; expected type PropertyNamingStrategy or Class<PropertyNamingStrategy> instead");
        }
        if (!PropertyNamingStrategy.class.isAssignableFrom(object = (Class)object)) {
            throw new IllegalStateException("AnnotationIntrospector returned Class " + object.getName() + "; expected Class<PropertyNamingStrategy>");
        }
        Object object2 = this._config.getHandlerInstantiator();
        if (object2 != null && (object2 = object2.namingStrategyInstance(this._config, this._classDef, object)) != null) {
            return object2;
        }
        return (PropertyNamingStrategy)ClassUtil.createInstance(object, this._config.canOverrideAccessModifiers());
    }

    private PropertyName _propNameFromSimple(String string2) {
        return PropertyName.construct(string2, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addCreatorParam(AnnotatedParameter annotatedParameter) {
        Object object;
        Object object2 = object = this._annotationIntrospector.findImplicitPropertyName(annotatedParameter);
        if (object == null) {
            object2 = "";
        }
        boolean bl2 = (object = this._annotationIntrospector.findNameForDeserialization(annotatedParameter)) != null && !object.isEmpty();
        if (!bl2) {
            if (object2.isEmpty()) {
                return;
            }
            object = new PropertyName((String)object2);
        }
        object2 = bl2 ? this._property((PropertyName)object) : this._property((String)object2);
        object2.addCtor(annotatedParameter, (PropertyName)object, bl2, true, false);
        this._creatorProperties.add((POJOPropertyBuilder)object2);
    }

    protected void _addCreators() {
        if (this._annotationIntrospector != null) {
            int n3;
            int n2;
            for (AnnotatedConstructor annotatedWithParams2 : this._classDef.getConstructors()) {
                if (this._creatorProperties == null) {
                    this._creatorProperties = new LinkedList();
                }
                n2 = annotatedWithParams2.getParameterCount();
                for (n3 = 0; n3 < n2; ++n3) {
                    this._addCreatorParam(annotatedWithParams2.getParameter(n3));
                }
            }
            for (AnnotatedMethod annotatedMethod : this._classDef.getStaticMethods()) {
                if (this._creatorProperties == null) {
                    this._creatorProperties = new LinkedList();
                }
                n2 = annotatedMethod.getParameterCount();
                for (n3 = 0; n3 < n2; ++n3) {
                    this._addCreatorParam(annotatedMethod.getParameter(n3));
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addFields() {
        AnnotationIntrospector annotationIntrospector = this._annotationIntrospector;
        boolean bl2 = !this._forSerialization && !this._config.isEnabled(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS);
        Iterator<AnnotatedField> iterator = this._classDef.fields().iterator();
        while (iterator.hasNext()) {
            AnnotatedField annotatedField = iterator.next();
            Object object = annotationIntrospector == null ? null : annotationIntrospector.findImplicitPropertyName(annotatedField);
            String string2 = object;
            if (object == null) {
                string2 = annotatedField.getName();
            }
            object = annotationIntrospector == null ? null : (this._forSerialization ? annotationIntrospector.findNameForSerialization(annotatedField) : annotationIntrospector.findNameForDeserialization(annotatedField));
            boolean bl3 = object != null;
            Object object2 = object;
            boolean bl4 = bl3;
            if (bl3) {
                object2 = object;
                bl4 = bl3;
                if (object.isEmpty()) {
                    object2 = this._propNameFromSimple(string2);
                    bl4 = false;
                }
            }
            bl3 = object2 != null;
            boolean bl5 = bl3;
            if (!bl3) {
                bl5 = this._visibilityChecker.isFieldVisible(annotatedField);
            }
            bl3 = annotationIntrospector != null && annotationIntrospector.hasIgnoreMarker(annotatedField);
            if (bl2 && object2 == null && !bl3 && Modifier.isFinal(annotatedField.getModifiers())) continue;
            this._property(string2).addField(annotatedField, (PropertyName)object2, bl4, bl5, bl3);
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void _addGetterMethod(AnnotatedMethod annotatedMethod, AnnotationIntrospector annotationIntrospector) {
        boolean bl2;
        boolean bl3 = false;
        String string2 = null;
        Object object = null;
        if (!annotatedMethod.hasReturnType()) {
            return;
        }
        if (annotationIntrospector != null) {
            if (annotationIntrospector.hasAnyGetterAnnotation(annotatedMethod)) {
                if (this._anyGetters == null) {
                    this._anyGetters = new LinkedList();
                }
                this._anyGetters.add(annotatedMethod);
                return;
            }
            if (annotationIntrospector.hasAsValueAnnotation(annotatedMethod)) {
                if (this._jsonValueGetters == null) {
                    this._jsonValueGetters = new LinkedList();
                }
                this._jsonValueGetters.add(annotatedMethod);
                return;
            }
        }
        Object object2 = annotationIntrospector == null ? null : annotationIntrospector.findNameForSerialization(annotatedMethod);
        boolean bl4 = object2 != null;
        if (!bl4) {
            if (annotationIntrospector != null) {
                object = annotationIntrospector.findImplicitPropertyName(annotatedMethod);
            }
            string2 = object;
            if (object == null) {
                string2 = BeanUtil.okNameForRegularGetter(annotatedMethod, annotatedMethod.getName());
            }
            if (string2 == null) {
                string2 = BeanUtil.okNameForIsGetter(annotatedMethod, annotatedMethod.getName());
                if (string2 == null) return;
                bl2 = this._visibilityChecker.isIsGetterVisible(annotatedMethod);
            } else {
                bl2 = this._visibilityChecker.isGetterVisible(annotatedMethod);
            }
        } else {
            if (annotationIntrospector != null) {
                string2 = annotationIntrospector.findImplicitPropertyName(annotatedMethod);
            }
            object = string2;
            if (string2 == null) {
                object = BeanUtil.okNameForGetter(annotatedMethod);
            }
            string2 = object;
            if (object == null) {
                string2 = annotatedMethod.getName();
            }
            object = object2;
            if (object2.isEmpty()) {
                object = this._propNameFromSimple(string2);
                bl4 = false;
            }
            bl2 = true;
            object2 = object;
        }
        if (annotationIntrospector != null) {
            bl3 = annotationIntrospector.hasIgnoreMarker(annotatedMethod);
        }
        this._property(string2).addGetter(annotatedMethod, (PropertyName)object2, bl4, bl2, bl3);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addInjectables() {
        AnnotationIntrospector annotationIntrospector = this._annotationIntrospector;
        if (annotationIntrospector != null) {
            for (AnnotatedField annotatedField : this._classDef.fields()) {
                this._doAddInjectable(annotationIntrospector.findInjectableValueId(annotatedField), annotatedField);
            }
            for (AnnotatedMethod annotatedMethod : this._classDef.memberMethods()) {
                if (annotatedMethod.getParameterCount() != 1) continue;
                this._doAddInjectable(annotationIntrospector.findInjectableValueId(annotatedMethod), annotatedMethod);
            }
        }
    }

    protected void _addMethods() {
        AnnotationIntrospector annotationIntrospector = this._annotationIntrospector;
        for (AnnotatedMethod annotatedMethod : this._classDef.memberMethods()) {
            int n2 = annotatedMethod.getParameterCount();
            if (n2 == 0) {
                this._addGetterMethod(annotatedMethod, annotationIntrospector);
                continue;
            }
            if (n2 == 1) {
                this._addSetterMethod(annotatedMethod, annotationIntrospector);
                continue;
            }
            if (n2 != 2 || annotationIntrospector == null || !annotationIntrospector.hasAnySetterAnnotation(annotatedMethod)) continue;
            if (this._anySetters == null) {
                this._anySetters = new LinkedList();
            }
            this._anySetters.add(annotatedMethod);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addSetterMethod(AnnotatedMethod annotatedMethod, AnnotationIntrospector annotationIntrospector) {
        boolean bl2;
        boolean bl3 = false;
        Object object = null;
        Object object2 = null;
        Object object3 = annotationIntrospector == null ? null : annotationIntrospector.findNameForDeserialization(annotatedMethod);
        boolean bl4 = object3 != null;
        if (!bl4) {
            if (annotationIntrospector != null) {
                object2 = annotationIntrospector.findImplicitPropertyName(annotatedMethod);
            }
            object = object2;
            if (object2 == null) {
                object = BeanUtil.okNameForMutator(annotatedMethod, this._mutatorPrefix);
            }
            if (object == null) {
                return;
            }
            bl2 = this._visibilityChecker.isSetterVisible(annotatedMethod);
            object2 = object;
        } else {
            object2 = annotationIntrospector == null ? object : annotationIntrospector.findImplicitPropertyName(annotatedMethod);
            object = object2;
            if (object2 == null) {
                object = BeanUtil.okNameForMutator(annotatedMethod, this._mutatorPrefix);
            }
            object2 = object;
            if (object == null) {
                object2 = annotatedMethod.getName();
            }
            object = object3;
            if (object3.isEmpty()) {
                object = this._propNameFromSimple((String)object2);
                bl4 = false;
            }
            bl2 = true;
            object3 = object;
        }
        if (annotationIntrospector != null) {
            bl3 = annotationIntrospector.hasIgnoreMarker(annotatedMethod);
        }
        this._property((String)object2).addSetter(annotatedMethod, (PropertyName)object3, bl4, bl2, bl3);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void _doAddInjectable(Object object, AnnotatedMember object2) {
        if (object == null) {
            return;
        }
        if (this._injectables == null) {
            this._injectables = new LinkedHashMap();
        }
        if ((AnnotatedMember)this._injectables.put(object, (AnnotatedMember)object2) == null) return;
        object2 = object.getClass().getName();
        throw new IllegalArgumentException("Duplicate injectable value with id '" + String.valueOf(object) + "' (of type " + (String)object2 + ")");
    }

    protected POJOPropertyBuilder _property(PropertyName propertyName) {
        return this._property(propertyName.getSimpleName());
    }

    protected POJOPropertyBuilder _property(String string2) {
        POJOPropertyBuilder pOJOPropertyBuilder;
        POJOPropertyBuilder pOJOPropertyBuilder2 = pOJOPropertyBuilder = this._properties.get(string2);
        if (pOJOPropertyBuilder == null) {
            pOJOPropertyBuilder2 = new POJOPropertyBuilder(new PropertyName(string2), this._annotationIntrospector, this._forSerialization);
            this._properties.put(string2, pOJOPropertyBuilder2);
        }
        return pOJOPropertyBuilder2;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _removeUnwantedProperties() {
        Iterator<Map.Entry<String, POJOPropertyBuilder>> iterator = this._properties.entrySet().iterator();
        boolean bl2 = !this._config.isEnabled(MapperFeature.INFER_PROPERTY_MUTATORS);
        while (iterator.hasNext()) {
            POJOPropertyBuilder pOJOPropertyBuilder = iterator.next().getValue();
            if (!pOJOPropertyBuilder.anyVisible()) {
                iterator.remove();
                continue;
            }
            if (pOJOPropertyBuilder.anyIgnorals()) {
                if (!pOJOPropertyBuilder.isExplicitlyIncluded()) {
                    iterator.remove();
                    this._addIgnored(pOJOPropertyBuilder.getName());
                    continue;
                }
                pOJOPropertyBuilder.removeIgnored();
                if (!this._forSerialization && !pOJOPropertyBuilder.couldDeserialize()) {
                    this._addIgnored(pOJOPropertyBuilder.getName());
                }
            }
            pOJOPropertyBuilder.removeNonVisible(bl2);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void _renameProperties() {
        Iterator<Map.Entry<String, POJOPropertyBuilder>> iterator = this._properties.entrySet().iterator();
        Object object = null;
        while (iterator.hasNext()) {
            void var2_3;
            POJOPropertyBuilder pOJOPropertyBuilder = iterator.next().getValue();
            Set<PropertyName> set = pOJOPropertyBuilder.findExplicitNames();
            if (set.isEmpty()) continue;
            iterator.remove();
            Object object2 = object;
            if (object == null) {
                LinkedList linkedList = new LinkedList();
            }
            if (set.size() == 1) {
                var2_3.add(pOJOPropertyBuilder.withName(set.iterator().next()));
                object = var2_3;
                continue;
            }
            var2_3.addAll(pOJOPropertyBuilder.explode(set));
            object = var2_3;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void _renameUsing(PropertyNamingStrategy var1_1) {
        var7_2 = this._properties.values().toArray(new POJOPropertyBuilder[this._properties.size()]);
        this._properties.clear();
        var3_3 = var7_2.length;
        var2_4 = 0;
        while (var2_4 < var3_3) {
            var5_7 = var7_2[var2_4];
            var8_11 = var5_7.getFullName();
            var4_5 = var6_10 = null;
            if (var5_7.isExplicitlyNamed()) ** GOTO lbl-1000
            if (this._forSerialization) {
                if (var5_7.hasGetter()) {
                    var4_5 = var1_1.nameForGetterMethod(this._config, var5_7.getGetter(), var8_11.getSimpleName());
                } else {
                    var4_5 = var6_10;
                    if (var5_7.hasField()) {
                        var4_5 = var1_1.nameForField(this._config, var5_7.getField(), var8_11.getSimpleName());
                    }
                }
            } else if (var5_7.hasSetter()) {
                var4_5 = var1_1.nameForSetterMethod(this._config, var5_7.getSetter(), var8_11.getSimpleName());
            } else if (var5_7.hasConstructorParameter()) {
                var4_5 = var1_1.nameForConstructorParameter(this._config, var5_7.getConstructorParameter(), var8_11.getSimpleName());
            } else if (var5_7.hasField()) {
                var4_5 = var1_1.nameForField(this._config, var5_7.getField(), var8_11.getSimpleName());
            } else {
                var4_5 = var6_10;
                if (var5_7.hasGetter()) {
                    var4_5 = var1_1.nameForGetterMethod(this._config, var5_7.getGetter(), var8_11.getSimpleName());
                }
            }
            if (var4_5 != null && !var8_11.hasSimpleName((String)var4_5)) {
                var6_10 = var5_7.withSimpleName((String)var4_5);
                var5_8 = var4_5;
                var4_5 = var6_10;
            } else lbl-1000: // 2 sources:
            {
                var6_10 = var8_11.getSimpleName();
                var4_5 = var5_7;
                var5_9 = var6_10;
            }
            if ((var6_10 = this._properties.get(var5_6)) == null) {
                this._properties.put((String)var5_6, (POJOPropertyBuilder)var4_5);
            } else {
                var6_10.addAll((POJOPropertyBuilder)var4_5);
            }
            this._updateCreatorProperty((POJOPropertyBuilder)var4_5, this._creatorProperties);
            ++var2_4;
        }
    }

    protected void _renameWithWrappers() {
        POJOPropertyBuilder pOJOPropertyBuilder;
        Object object = this._properties.entrySet().iterator();
        Object linkedList = null;
        while (object.hasNext()) {
            PropertyName propertyName;
            void var2_7;
            pOJOPropertyBuilder = object.next().getValue();
            AnnotatedMember annotatedMember = pOJOPropertyBuilder.getPrimaryMember();
            if (annotatedMember == null || (propertyName = this._annotationIntrospector.findWrapperName(annotatedMember)) == null || !propertyName.hasSimpleName() || propertyName.equals(pOJOPropertyBuilder.getFullName())) continue;
            Object object2 = linkedList;
            if (linkedList == null) {
                LinkedList linkedList2 = new LinkedList();
            }
            var2_7.add(pOJOPropertyBuilder.withName(propertyName));
            object.remove();
            linkedList = var2_7;
        }
        if (linkedList != null) {
            linkedList = linkedList.iterator();
            while (linkedList.hasNext()) {
                POJOPropertyBuilder pOJOPropertyBuilder2 = (POJOPropertyBuilder)linkedList.next();
                object = pOJOPropertyBuilder2.getName();
                pOJOPropertyBuilder = this._properties.get(object);
                if (pOJOPropertyBuilder == null) {
                    this._properties.put((String)object, pOJOPropertyBuilder2);
                    continue;
                }
                pOJOPropertyBuilder.addAll(pOJOPropertyBuilder2);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _sortProperties() {
        void var4_3;
        void var4_6;
        String[] arrstring = this._annotationIntrospector;
        if (arrstring == null) {
            Object var4_2 = null;
        } else {
            Boolean bl2 = arrstring.findSerializationSortAlphabetically((Annotated)this._classDef);
        }
        boolean bl3 = var4_3 == null ? this._config.shouldSortPropertiesAlphabetically() : var4_3.booleanValue();
        arrstring = arrstring == null ? null : arrstring.findSerializationPropertyOrder(this._classDef);
        if (!bl3 && this._creatorProperties == null && arrstring == null) {
            return;
        }
        int n2 = this._properties.size();
        if (bl3) {
            TreeMap treeMap = new TreeMap();
        } else {
            LinkedHashMap linkedHashMap = new LinkedHashMap(n2 + n2);
        }
        for (POJOPropertyBuilder pOJOPropertyBuilder : this._properties.values()) {
            var4_6.put(pOJOPropertyBuilder.getName(), pOJOPropertyBuilder);
        }
        LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>(n2 + n2);
        if (arrstring != null) {
            for (TreeMap treeMap : arrstring) {
                void var6_16;
                void var7_28;
                block15 : {
                    POJOPropertyBuilder pOJOPropertyBuilder2 = (POJOPropertyBuilder)var4_6.get(treeMap);
                    TreeMap treeMap2 = treeMap;
                    POJOPropertyBuilder pOJOPropertyBuilder3 = pOJOPropertyBuilder2;
                    if (pOJOPropertyBuilder2 == null) {
                        POJOPropertyBuilder pOJOPropertyBuilder4;
                        Iterator<POJOPropertyBuilder> iterator = this._properties.values().iterator();
                        do {
                            TreeMap treeMap3 = treeMap;
                            POJOPropertyBuilder pOJOPropertyBuilder5 = pOJOPropertyBuilder2;
                            if (!iterator.hasNext()) break block15;
                        } while (!treeMap.equals((pOJOPropertyBuilder4 = iterator.next()).getInternalName()));
                        POJOPropertyBuilder pOJOPropertyBuilder6 = pOJOPropertyBuilder4;
                        String string2 = pOJOPropertyBuilder4.getName();
                    }
                }
                if (var6_16 == null) continue;
                linkedHashMap.put(var7_28, var6_16);
            }
        }
        if (this._creatorProperties != null) {
            if (bl3) {
                arrstring = new TreeMap();
                for (POJOPropertyBuilder pOJOPropertyBuilder7 : this._creatorProperties) {
                    arrstring.put(pOJOPropertyBuilder7.getName(), pOJOPropertyBuilder7);
                }
                arrstring = arrstring.values();
            } else {
                arrstring = this._creatorProperties;
            }
            for (POJOPropertyBuilder pOJOPropertyBuilder8 : arrstring) {
                linkedHashMap.put(pOJOPropertyBuilder8.getName(), pOJOPropertyBuilder8);
            }
        }
        linkedHashMap.putAll((Map<Object, Object>)var4_6);
        this._properties.clear();
        this._properties.putAll(linkedHashMap);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void _updateCreatorProperty(POJOPropertyBuilder pOJOPropertyBuilder, List<POJOPropertyBuilder> list) {
        if (list == null) return;
        int n2 = 0;
        int n3 = list.size();
        while (n2 < n3) {
            if (list.get(n2).getInternalName().equals(pOJOPropertyBuilder.getInternalName())) {
                list.set(n2, pOJOPropertyBuilder);
                return;
            }
            ++n2;
        }
    }

    public POJOPropertiesCollector collect() {
        this._properties.clear();
        this._addFields();
        this._addMethods();
        this._addCreators();
        this._addInjectables();
        this._removeUnwantedProperties();
        this._renameProperties();
        Iterator<POJOPropertyBuilder> iterator = this._findNamingStrategy();
        if (iterator != null) {
            this._renameUsing((PropertyNamingStrategy)((Object)iterator));
        }
        iterator = this._properties.values().iterator();
        while (iterator.hasNext()) {
            ((POJOPropertyBuilder)iterator.next()).trimByVisibility();
        }
        iterator = this._properties.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().mergeAnnotations(this._forSerialization);
        }
        if (this._config.isEnabled(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME)) {
            this._renameWithWrappers();
        }
        this._sortProperties();
        return this;
    }

    public Class<?> findPOJOBuilderClass() {
        return this._annotationIntrospector.findPOJOBuilder(this._classDef);
    }

    public AnnotationIntrospector getAnnotationIntrospector() {
        return this._annotationIntrospector;
    }

    public AnnotatedMember getAnyGetter() {
        if (this._anyGetters != null) {
            if (this._anyGetters.size() > 1) {
                this.reportProblem("Multiple 'any-getters' defined (" + this._anyGetters.get(0) + " vs " + this._anyGetters.get(1) + ")");
            }
            return this._anyGetters.getFirst();
        }
        return null;
    }

    public AnnotatedMethod getAnySetterMethod() {
        if (this._anySetters != null) {
            if (this._anySetters.size() > 1) {
                this.reportProblem("Multiple 'any-setters' defined (" + this._anySetters.get(0) + " vs " + this._anySetters.get(1) + ")");
            }
            return this._anySetters.getFirst();
        }
        return null;
    }

    public AnnotatedClass getClassDef() {
        return this._classDef;
    }

    public MapperConfig<?> getConfig() {
        return this._config;
    }

    public Set<String> getIgnoredPropertyNames() {
        return this._ignoredPropertyNames;
    }

    public Map<Object, AnnotatedMember> getInjectables() {
        return this._injectables;
    }

    public AnnotatedMethod getJsonValueMethod() {
        if (this._jsonValueGetters != null) {
            if (this._jsonValueGetters.size() > 1) {
                this.reportProblem("Multiple value properties defined (" + this._jsonValueGetters.get(0) + " vs " + this._jsonValueGetters.get(1) + ")");
            }
            return this._jsonValueGetters.get(0);
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ObjectIdInfo getObjectIdInfo() {
        ObjectIdInfo objectIdInfo;
        if (this._annotationIntrospector == null) {
            return null;
        }
        ObjectIdInfo objectIdInfo2 = objectIdInfo = this._annotationIntrospector.findObjectIdInfo(this._classDef);
        if (objectIdInfo == null) return objectIdInfo2;
        return this._annotationIntrospector.findObjectReferenceInfo(this._classDef, objectIdInfo);
    }

    public List<BeanPropertyDefinition> getProperties() {
        return new ArrayList<BeanPropertyDefinition>(this._properties.values());
    }

    protected Map<String, POJOPropertyBuilder> getPropertyMap() {
        return this._properties;
    }

    public JavaType getType() {
        return this._type;
    }

    protected void reportProblem(String string2) {
        throw new IllegalArgumentException("Problem with definition of " + this._classDef + ": " + string2);
    }
}

