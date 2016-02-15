/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class POJOPropertyBuilder
extends BeanPropertyDefinition
implements Comparable<POJOPropertyBuilder> {
    protected final AnnotationIntrospector _annotationIntrospector;
    protected Linked<AnnotatedParameter> _ctorParameters;
    protected Linked<AnnotatedField> _fields;
    protected final boolean _forSerialization;
    protected Linked<AnnotatedMethod> _getters;
    protected final PropertyName _internalName;
    protected final PropertyName _name;
    protected Linked<AnnotatedMethod> _setters;

    public POJOPropertyBuilder(PropertyName propertyName, AnnotationIntrospector annotationIntrospector, boolean bl2) {
        this(propertyName, propertyName, annotationIntrospector, bl2);
    }

    protected POJOPropertyBuilder(PropertyName propertyName, PropertyName propertyName2, AnnotationIntrospector annotationIntrospector, boolean bl2) {
        this._internalName = propertyName;
        this._name = propertyName2;
        this._annotationIntrospector = annotationIntrospector;
        this._forSerialization = bl2;
    }

    public POJOPropertyBuilder(POJOPropertyBuilder pOJOPropertyBuilder, PropertyName propertyName) {
        this._internalName = pOJOPropertyBuilder._internalName;
        this._name = propertyName;
        this._annotationIntrospector = pOJOPropertyBuilder._annotationIntrospector;
        this._fields = pOJOPropertyBuilder._fields;
        this._ctorParameters = pOJOPropertyBuilder._ctorParameters;
        this._getters = pOJOPropertyBuilder._getters;
        this._setters = pOJOPropertyBuilder._setters;
        this._forSerialization = pOJOPropertyBuilder._forSerialization;
    }

    @Deprecated
    public POJOPropertyBuilder(String string2, AnnotationIntrospector annotationIntrospector, boolean bl2) {
        this(new PropertyName(string2), annotationIntrospector, bl2);
    }

    private <T> boolean _anyExplicitNames(Linked<T> linked) {
        while (linked != null) {
            if (linked.name != null && linked.isNameExplicit) {
                return true;
            }
            linked = linked.next;
        }
        return false;
    }

    private <T> boolean _anyExplicits(Linked<T> linked) {
        while (linked != null) {
            if (linked.name != null && linked.name.hasSimpleName()) {
                return true;
            }
            linked = linked.next;
        }
        return false;
    }

    private <T> boolean _anyIgnorals(Linked<T> linked) {
        while (linked != null) {
            if (linked.isMarkedIgnored) {
                return true;
            }
            linked = linked.next;
        }
        return false;
    }

    private <T> boolean _anyVisible(Linked<T> linked) {
        while (linked != null) {
            if (linked.isVisible) {
                return true;
            }
            linked = linked.next;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _explode(Collection<PropertyName> collection, Map<PropertyName, POJOPropertyBuilder> map, Linked<?> linked) {
        Linked linked2 = linked;
        while (linked2 != null) {
            POJOPropertyBuilder pOJOPropertyBuilder;
            PropertyName propertyName = linked2.name;
            if (!linked2.isNameExplicit || propertyName == null) {
                throw new IllegalStateException("Conflicting/ambiguous property name definitions (implicit name '" + this._name + "'): found multiple explicit names: " + collection + ", but also implicit accessor: " + linked2);
            }
            POJOPropertyBuilder pOJOPropertyBuilder2 = pOJOPropertyBuilder = map.get(propertyName);
            if (pOJOPropertyBuilder == null) {
                pOJOPropertyBuilder2 = new POJOPropertyBuilder(this._internalName, propertyName, this._annotationIntrospector, this._forSerialization);
                map.put(propertyName, pOJOPropertyBuilder2);
            }
            if (linked == this._fields) {
                pOJOPropertyBuilder2._fields = linked2.withNext(pOJOPropertyBuilder2._fields);
            } else if (linked == this._getters) {
                pOJOPropertyBuilder2._getters = linked2.withNext(pOJOPropertyBuilder2._getters);
            } else if (linked == this._setters) {
                pOJOPropertyBuilder2._setters = linked2.withNext(pOJOPropertyBuilder2._setters);
            } else {
                if (linked != this._ctorParameters) {
                    throw new IllegalStateException("Internal error: mismatched accessors, property: " + this);
                }
                pOJOPropertyBuilder2._ctorParameters = linked2.withNext(pOJOPropertyBuilder2._ctorParameters);
            }
            linked2 = linked2.next;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Set<PropertyName> _findExplicitNames(Linked<? extends AnnotatedMember> set, Set<PropertyName> set2) {
        Set<PropertyName> set3 = set;
        void var3_4;
        while (var3_4 != null) {
            set = set2;
            if (var3_4.isNameExplicit) {
                if (var3_4.name == null) {
                    set = set2;
                } else {
                    set = set2;
                    if (set2 == null) {
                        set = new HashSet<PropertyName>();
                    }
                    set.add(var3_4.name);
                }
            }
            Linked linked = var3_4.next;
            set2 = set;
        }
        return set2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private /* varargs */ AnnotationMap _mergeAnnotations(int n2, Linked<? extends AnnotatedMember> ... arrlinked) {
        AnnotationMap annotationMap = ((AnnotatedMember)arrlinked[n2].value).getAllAnnotations();
        ++n2;
        do {
            AnnotationMap annotationMap2 = annotationMap;
            if (n2 >= arrlinked.length) return annotationMap2;
            if (arrlinked[n2] != null) {
                return AnnotationMap.merge(annotationMap, this._mergeAnnotations(n2, arrlinked));
            }
            ++n2;
        } while (true);
    }

    private PropertyName _propName(String string2) {
        return PropertyName.construct(string2, null);
    }

    private <T> Linked<T> _removeIgnored(Linked<T> linked) {
        if (linked == null) {
            return linked;
        }
        return linked.withoutIgnored();
    }

    private <T> Linked<T> _removeNonVisible(Linked<T> linked) {
        if (linked == null) {
            return linked;
        }
        return linked.withoutNonVisible();
    }

    private <T> Linked<T> _trimByVisibility(Linked<T> linked) {
        if (linked == null) {
            return linked;
        }
        return linked.trimByVisibility();
    }

    private static <T> Linked<T> merge(Linked<T> linked, Linked<T> linked2) {
        if (linked == null) {
            return linked2;
        }
        if (linked2 == null) {
            return linked;
        }
        return linked.append(linked2);
    }

    protected String _findDescription() {
        return (String)this.fromMemberAnnotations(new WithMember<String>(){

            @Override
            public String withMember(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.findPropertyDescription(annotatedMember);
            }
        });
    }

    protected Integer _findIndex() {
        return (Integer)this.fromMemberAnnotations(new WithMember<Integer>(){

            @Override
            public Integer withMember(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.findPropertyIndex(annotatedMember);
            }
        });
    }

    protected Boolean _findRequired() {
        return (Boolean)this.fromMemberAnnotations(new WithMember<Boolean>(){

            @Override
            public Boolean withMember(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.hasRequiredMarker(annotatedMember);
            }
        });
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected int _getterPriority(AnnotatedMethod object) {
        int n2 = 2;
        if ((object = object.getName()).startsWith("get") && object.length() > 3) {
            return 1;
        }
        if (!object.startsWith("is")) return 3;
        if (object.length() > 2) return n2;
        return 3;
    }

    protected int _setterPriority(AnnotatedMethod object) {
        if ((object = object.getName()).startsWith("set") && object.length() > 3) {
            return 1;
        }
        return 2;
    }

    public void addAll(POJOPropertyBuilder pOJOPropertyBuilder) {
        this._fields = POJOPropertyBuilder.merge(this._fields, pOJOPropertyBuilder._fields);
        this._ctorParameters = POJOPropertyBuilder.merge(this._ctorParameters, pOJOPropertyBuilder._ctorParameters);
        this._getters = POJOPropertyBuilder.merge(this._getters, pOJOPropertyBuilder._getters);
        this._setters = POJOPropertyBuilder.merge(this._setters, pOJOPropertyBuilder._setters);
    }

    public void addCtor(AnnotatedParameter annotatedParameter, PropertyName propertyName, boolean bl2, boolean bl3, boolean bl4) {
        this._ctorParameters = new Linked<AnnotatedParameter>(annotatedParameter, this._ctorParameters, propertyName, bl2, bl3, bl4);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    public void addCtor(AnnotatedParameter annotatedParameter, String string2, boolean bl2, boolean bl3) {
        PropertyName propertyName = this._propName(string2);
        boolean bl4 = string2 != null;
        this.addCtor(annotatedParameter, propertyName, bl4, bl2, bl3);
    }

    @Deprecated
    public void addCtor(AnnotatedParameter annotatedParameter, String string2, boolean bl2, boolean bl3, boolean bl4) {
        this.addCtor(annotatedParameter, this._propName(string2), bl2, bl3, bl4);
    }

    public void addField(AnnotatedField annotatedField, PropertyName propertyName, boolean bl2, boolean bl3, boolean bl4) {
        this._fields = new Linked<AnnotatedField>(annotatedField, this._fields, propertyName, bl2, bl3, bl4);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    public void addField(AnnotatedField annotatedField, String string2, boolean bl2, boolean bl3) {
        PropertyName propertyName = this._propName(string2);
        boolean bl4 = string2 != null;
        this.addField(annotatedField, propertyName, bl4, bl2, bl3);
    }

    @Deprecated
    public void addField(AnnotatedField annotatedField, String string2, boolean bl2, boolean bl3, boolean bl4) {
        this.addField(annotatedField, this._propName(string2), bl2, bl3, bl4);
    }

    public void addGetter(AnnotatedMethod annotatedMethod, PropertyName propertyName, boolean bl2, boolean bl3, boolean bl4) {
        this._getters = new Linked<AnnotatedMethod>(annotatedMethod, this._getters, propertyName, bl2, bl3, bl4);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    public void addGetter(AnnotatedMethod annotatedMethod, String string2, boolean bl2, boolean bl3) {
        PropertyName propertyName = this._propName(string2);
        boolean bl4 = string2 != null;
        this.addGetter(annotatedMethod, propertyName, bl4, bl2, bl3);
    }

    @Deprecated
    public void addGetter(AnnotatedMethod annotatedMethod, String string2, boolean bl2, boolean bl3, boolean bl4) {
        this.addGetter(annotatedMethod, this._propName(string2), bl2, bl3, bl4);
    }

    public void addSetter(AnnotatedMethod annotatedMethod, PropertyName propertyName, boolean bl2, boolean bl3, boolean bl4) {
        this._setters = new Linked<AnnotatedMethod>(annotatedMethod, this._setters, propertyName, bl2, bl3, bl4);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    public void addSetter(AnnotatedMethod annotatedMethod, String string2, boolean bl2, boolean bl3) {
        PropertyName propertyName = this._propName(string2);
        boolean bl4 = string2 != null;
        this.addSetter(annotatedMethod, propertyName, bl4, bl2, bl3);
    }

    @Deprecated
    public void addSetter(AnnotatedMethod annotatedMethod, String string2, boolean bl2, boolean bl3, boolean bl4) {
        this.addSetter(annotatedMethod, this._propName(string2), bl2, bl3, bl4);
    }

    public boolean anyIgnorals() {
        if (this._anyIgnorals(this._fields) || this._anyIgnorals(this._getters) || this._anyIgnorals(this._setters) || this._anyIgnorals(this._ctorParameters)) {
            return true;
        }
        return false;
    }

    public boolean anyVisible() {
        if (this._anyVisible(this._fields) || this._anyVisible(this._getters) || this._anyVisible(this._setters) || this._anyVisible(this._ctorParameters)) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(POJOPropertyBuilder pOJOPropertyBuilder) {
        if (this._ctorParameters != null) {
            if (pOJOPropertyBuilder._ctorParameters == null) {
                return -1;
            }
        } else if (pOJOPropertyBuilder._ctorParameters != null) {
            return 1;
        }
        return this.getName().compareTo(pOJOPropertyBuilder.getName());
    }

    @Override
    public boolean couldDeserialize() {
        if (this._ctorParameters != null || this._setters != null || this._fields != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean couldSerialize() {
        if (this._getters != null || this._fields != null) {
            return true;
        }
        return false;
    }

    public Collection<POJOPropertyBuilder> explode(Collection<PropertyName> collection) {
        HashMap<PropertyName, POJOPropertyBuilder> hashMap = new HashMap<PropertyName, POJOPropertyBuilder>();
        this._explode(collection, hashMap, this._fields);
        this._explode(collection, hashMap, this._getters);
        this._explode(collection, hashMap, this._setters);
        this._explode(collection, hashMap, this._ctorParameters);
        return hashMap.values();
    }

    public Set<PropertyName> findExplicitNames() {
        Set<PropertyName> set;
        Set set2 = this._findExplicitNames(this._fields, null);
        set2 = this._findExplicitNames(this._getters, set2);
        set2 = this._findExplicitNames(this._setters, set2);
        set2 = set = this._findExplicitNames(this._ctorParameters, set2);
        if (set == null) {
            set2 = Collections.emptySet();
        }
        return set2;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Deprecated
    public String findNewName() {
        Set<PropertyName> set = this.findExplicitNames();
        if (set == null) {
            return null;
        }
        if (set.size() > 1) {
            throw new IllegalStateException("Conflicting/ambiguous property name definitions (implicit name '" + this._name + "'): found more than one explicit name: " + set);
        }
        if ((set = set.iterator().next()).equals((Object)this._name)) return null;
        return set.getSimpleName();
    }

    @Override
    public ObjectIdInfo findObjectIdInfo() {
        return (ObjectIdInfo)this.fromMemberAnnotations(new WithMember<ObjectIdInfo>(){

            @Override
            public ObjectIdInfo withMember(AnnotatedMember annotatedMember) {
                ObjectIdInfo objectIdInfo;
                ObjectIdInfo objectIdInfo2 = objectIdInfo = POJOPropertyBuilder.this._annotationIntrospector.findObjectIdInfo(annotatedMember);
                if (objectIdInfo != null) {
                    objectIdInfo2 = POJOPropertyBuilder.this._annotationIntrospector.findObjectReferenceInfo(annotatedMember, objectIdInfo);
                }
                return objectIdInfo2;
            }
        });
    }

    @Override
    public AnnotationIntrospector.ReferenceProperty findReferenceType() {
        return (AnnotationIntrospector.ReferenceProperty)this.fromMemberAnnotations(new WithMember<AnnotationIntrospector.ReferenceProperty>(){

            @Override
            public AnnotationIntrospector.ReferenceProperty withMember(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.findReferenceType(annotatedMember);
            }
        });
    }

    @Override
    public Class<?>[] findViews() {
        return (Class[])this.fromMemberAnnotations(new WithMember<Class<?>[]>(){

            @Override
            public Class<?>[] withMember(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.findViews(annotatedMember);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    protected <T> T fromMemberAnnotations(WithMember<T> withMember) {
        Object var3_2 = null;
        Object var4_3 = null;
        Object var2_4 = null;
        if (this._annotationIntrospector == null) return var3_2;
        if (this._forSerialization) {
            if (this._getters != null) {
                var2_4 = withMember.withMember((AnnotatedMember)this._getters.value);
            }
        } else {
            var3_2 = var4_3;
            if (this._ctorParameters != null) {
                var3_2 = withMember.withMember((AnnotatedMember)this._ctorParameters.value);
            }
            var2_4 = var3_2;
            if (var3_2 == null) {
                var2_4 = var3_2;
                if (this._setters != null) {
                    var2_4 = withMember.withMember((AnnotatedMember)this._setters.value);
                }
            }
        }
        var3_2 = var2_4;
        if (var2_4 == null) {
            var3_2 = var2_4;
            if (this._fields != null) {
                var3_2 = withMember.withMember((AnnotatedMember)this._fields.value);
            }
        }
        return var3_2;
    }

    @Override
    public AnnotatedMember getAccessor() {
        AnnotatedMethod annotatedMethod;
        AnnotatedMember annotatedMember = annotatedMethod = this.getGetter();
        if (annotatedMethod == null) {
            annotatedMember = this.getField();
        }
        return annotatedMember;
    }

    @Override
    public AnnotatedParameter getConstructorParameter() {
        Linked linked;
        if (this._ctorParameters == null) {
            return null;
        }
        Linked linked2 = this._ctorParameters;
        do {
            if (((AnnotatedParameter)linked2.value).getOwner() instanceof AnnotatedConstructor) {
                return (AnnotatedParameter)linked2.value;
            }
            linked2 = linked = linked2.next;
        } while (linked != null);
        return (AnnotatedParameter)this._ctorParameters.value;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public AnnotatedField getField() {
        if (this._fields == null) {
            return null;
        }
        AnnotatedField annotatedField = (AnnotatedField)this._fields.value;
        Linked linked = this._fields.next;
        do {
            Class class_;
            AnnotatedField annotatedField2 = annotatedField;
            if (linked == null) return annotatedField2;
            AnnotatedField annotatedField3 = (AnnotatedField)linked.value;
            Class class_2 = annotatedField.getDeclaringClass();
            if (class_2 == (class_ = annotatedField3.getDeclaringClass())) throw new IllegalArgumentException("Multiple fields representing property \"" + this.getName() + "\": " + annotatedField.getFullName() + " vs " + annotatedField3.getFullName());
            if (class_2.isAssignableFrom(class_)) {
                annotatedField2 = annotatedField3;
            } else {
                annotatedField2 = annotatedField;
                if (!class_.isAssignableFrom(class_2)) throw new IllegalArgumentException("Multiple fields representing property \"" + this.getName() + "\": " + annotatedField.getFullName() + " vs " + annotatedField3.getFullName());
            }
            linked = linked.next;
            annotatedField = annotatedField2;
        } while (true);
    }

    @Override
    public PropertyName getFullName() {
        return this._name;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public AnnotatedMethod getGetter() {
        var4_1 = this._getters;
        if (var4_1 == null) {
            return null;
        }
        var6_8 = var4_1.next;
        var5_9 = var4_1;
        var3_10 = var6_8;
        if (var6_8 == null) {
            return (AnnotatedMethod)var4_1.value;
        }
        do {
            if (var3_10 == null) {
                this._getters = var5_9.withoutNext();
                return (AnnotatedMethod)var5_9.value;
            }
            var6_8 = ((AnnotatedMethod)var5_9.value).getDeclaringClass();
            if (var6_8 == (var7_13 = ((AnnotatedMethod)var3_10.value).getDeclaringClass())) ** GOTO lbl-1000
            if (var6_8.isAssignableFrom(var7_13)) {
                var4_3 = var3_10;
            } else {
                var4_5 = var5_9;
                if (!var7_13.isAssignableFrom(var6_8)) lbl-1000: // 2 sources:
                {
                    if ((var1_11 = this._getterPriority((AnnotatedMethod)var3_10.value)) == (var2_12 = this._getterPriority((AnnotatedMethod)var5_9.value))) throw new IllegalArgumentException("Conflicting getter definitions for property \"" + this.getName() + "\": " + ((AnnotatedMethod)var5_9.value).getFullName() + " vs " + ((AnnotatedMethod)var3_10.value).getFullName());
                    var4_6 = var5_9;
                    if (var1_11 < var2_12) {
                        var4_7 = var3_10;
                    }
                }
            }
            var3_10 = var3_10.next;
            var5_9 = var4_4;
        } while (true);
    }

    @Override
    public String getInternalName() {
        return this._internalName.getSimpleName();
    }

    @Override
    public PropertyMetadata getMetadata() {
        Boolean bl2 = this._findRequired();
        String string2 = this._findDescription();
        Integer n2 = this._findIndex();
        if (bl2 == null && n2 == null) {
            if (string2 == null) {
                return PropertyMetadata.STD_REQUIRED_OR_OPTIONAL;
            }
            return PropertyMetadata.STD_REQUIRED_OR_OPTIONAL.withDescription(string2);
        }
        return PropertyMetadata.construct(bl2, string2, n2);
    }

    @Override
    public AnnotatedMember getMutator() {
        AnnotatedMember annotatedMember;
        AnnotatedMember annotatedMember2 = annotatedMember = this.getConstructorParameter();
        if (annotatedMember == null) {
            annotatedMember2 = annotatedMember = this.getSetter();
            if (annotatedMember == null) {
                annotatedMember2 = this.getField();
            }
        }
        return annotatedMember2;
    }

    @Override
    public String getName() {
        if (this._name == null) {
            return null;
        }
        return this._name.getSimpleName();
    }

    @Override
    public AnnotatedMember getNonConstructorMutator() {
        AnnotatedMethod annotatedMethod;
        AnnotatedMember annotatedMember = annotatedMethod = this.getSetter();
        if (annotatedMethod == null) {
            annotatedMember = this.getField();
        }
        return annotatedMember;
    }

    @Override
    public AnnotatedMember getPrimaryMember() {
        if (this._forSerialization) {
            return this.getAccessor();
        }
        return this.getMutator();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public AnnotatedMethod getSetter() {
        var4_1 = this._setters;
        if (var4_1 == null) {
            return null;
        }
        var6_8 = var4_1.next;
        var5_9 = var4_1;
        var3_10 = var6_8;
        if (var6_8 == null) {
            return (AnnotatedMethod)var4_1.value;
        }
        do {
            if (var3_10 == null) {
                this._setters = var5_9.withoutNext();
                return (AnnotatedMethod)var5_9.value;
            }
            var6_8 = ((AnnotatedMethod)var5_9.value).getDeclaringClass();
            if (var6_8 == (var7_13 = ((AnnotatedMethod)var3_10.value).getDeclaringClass())) ** GOTO lbl-1000
            if (var6_8.isAssignableFrom(var7_13)) {
                var4_3 = var3_10;
            } else {
                var4_5 = var5_9;
                if (!var7_13.isAssignableFrom(var6_8)) lbl-1000: // 2 sources:
                {
                    if ((var1_11 = this._setterPriority((AnnotatedMethod)var3_10.value)) == (var2_12 = this._setterPriority((AnnotatedMethod)var5_9.value))) throw new IllegalArgumentException("Conflicting setter definitions for property \"" + this.getName() + "\": " + ((AnnotatedMethod)var5_9.value).getFullName() + " vs " + ((AnnotatedMethod)var3_10.value).getFullName());
                    var4_6 = var5_9;
                    if (var1_11 < var2_12) {
                        var4_7 = var3_10;
                    }
                }
            }
            var3_10 = var3_10.next;
            var5_9 = var4_4;
        } while (true);
    }

    @Override
    public PropertyName getWrapperName() {
        AnnotatedMember annotatedMember = this.getPrimaryMember();
        if (annotatedMember == null || this._annotationIntrospector == null) {
            return null;
        }
        return this._annotationIntrospector.findWrapperName(annotatedMember);
    }

    @Override
    public boolean hasConstructorParameter() {
        if (this._ctorParameters != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasField() {
        if (this._fields != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasGetter() {
        if (this._getters != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasSetter() {
        if (this._setters != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isExplicitlyIncluded() {
        if (this._anyExplicits(this._fields) || this._anyExplicits(this._getters) || this._anyExplicits(this._setters) || this._anyExplicits(this._ctorParameters)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isExplicitlyNamed() {
        if (this._anyExplicitNames(this._fields) || this._anyExplicitNames(this._getters) || this._anyExplicitNames(this._setters) || this._anyExplicitNames(this._ctorParameters)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isTypeId() {
        Boolean bl2 = (Boolean)this.fromMemberAnnotations(new WithMember<Boolean>(){

            @Override
            public Boolean withMember(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.isTypeId(annotatedMember);
            }
        });
        if (bl2 != null && bl2.booleanValue()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void mergeAnnotations(boolean bl2) {
        if (bl2) {
            if (this._getters != null) {
                AnnotationMap annotationMap = this._mergeAnnotations(0, this._getters, this._fields, this._ctorParameters, this._setters);
                this._getters = this._getters.withValue((AnnotatedMethod)((AnnotatedMethod)this._getters.value).withAnnotations(annotationMap));
                return;
            } else {
                if (this._fields == null) return;
                {
                    AnnotationMap annotationMap = this._mergeAnnotations(0, this._fields, this._ctorParameters, this._setters);
                    this._fields = this._fields.withValue((AnnotatedField)((AnnotatedField)this._fields.value).withAnnotations(annotationMap));
                    return;
                }
            }
        } else {
            if (this._ctorParameters != null) {
                AnnotationMap annotationMap = this._mergeAnnotations(0, this._ctorParameters, this._setters, this._fields, this._getters);
                this._ctorParameters = this._ctorParameters.withValue((AnnotatedParameter)((AnnotatedParameter)this._ctorParameters.value).withAnnotations(annotationMap));
                return;
            }
            if (this._setters != null) {
                AnnotationMap annotationMap = this._mergeAnnotations(0, this._setters, this._fields, this._getters);
                this._setters = this._setters.withValue((AnnotatedMethod)((AnnotatedMethod)this._setters.value).withAnnotations(annotationMap));
                return;
            }
            if (this._fields == null) return;
            {
                AnnotationMap annotationMap = this._mergeAnnotations(0, this._fields, this._getters);
                this._fields = this._fields.withValue((AnnotatedField)((AnnotatedField)this._fields.value).withAnnotations(annotationMap));
                return;
            }
        }
    }

    public void removeIgnored() {
        this._fields = this._removeIgnored(this._fields);
        this._getters = this._removeIgnored(this._getters);
        this._setters = this._removeIgnored(this._setters);
        this._ctorParameters = this._removeIgnored(this._ctorParameters);
    }

    public void removeNonVisible(boolean bl2) {
        this._getters = this._removeNonVisible(this._getters);
        this._ctorParameters = this._removeNonVisible(this._ctorParameters);
        if (bl2 || this._getters == null) {
            this._fields = this._removeNonVisible(this._fields);
            this._setters = this._removeNonVisible(this._setters);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[Property '").append(this._name).append("'; ctors: ").append(this._ctorParameters).append(", field(s): ").append(this._fields).append(", getter(s): ").append(this._getters).append(", setter(s): ").append(this._setters);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void trimByVisibility() {
        this._fields = this._trimByVisibility(this._fields);
        this._getters = this._trimByVisibility(this._getters);
        this._setters = this._trimByVisibility(this._setters);
        this._ctorParameters = this._trimByVisibility(this._ctorParameters);
    }

    @Override
    public POJOPropertyBuilder withName(PropertyName propertyName) {
        return new POJOPropertyBuilder(this, propertyName);
    }

    @Deprecated
    @Override
    public POJOPropertyBuilder withName(String string2) {
        return this.withSimpleName(string2);
    }

    @Override
    public POJOPropertyBuilder withSimpleName(String object) {
        if ((object = this._name.withSimpleName((String)object)) == this._name) {
            return this;
        }
        return new POJOPropertyBuilder(this, (PropertyName)object);
    }

    private static final class Linked<T> {
        public final boolean isMarkedIgnored;
        public final boolean isNameExplicit;
        public final boolean isVisible;
        public final PropertyName name;
        public final Linked<T> next;
        public final T value;

        /*
         * Enabled aggressive block sorting
         */
        public Linked(T object, Linked<T> linked, PropertyName propertyName, boolean bl2, boolean bl3, boolean bl4) {
            this.value = object;
            this.next = linked;
            object = propertyName == null || propertyName.isEmpty() ? null : propertyName;
            this.name = object;
            boolean bl5 = bl2;
            if (bl2) {
                if (this.name == null) {
                    throw new IllegalArgumentException("Can not pass true for 'explName' if name is null/empty");
                }
                bl5 = bl2;
                if (!propertyName.hasSimpleName()) {
                    bl5 = false;
                }
            }
            this.isNameExplicit = bl5;
            this.isVisible = bl3;
            this.isMarkedIgnored = bl4;
        }

        private Linked<T> append(Linked<T> linked) {
            if (this.next == null) {
                return this.withNext(linked);
            }
            return this.withNext(super.append(linked));
        }

        public String toString() {
            String string2;
            String string3 = string2 = this.value.toString() + "[visible=" + this.isVisible + ",ignore=" + this.isMarkedIgnored + ",explicitName=" + this.isNameExplicit + "]";
            if (this.next != null) {
                string3 = string2 + ", " + this.next.toString();
            }
            return string3;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public Linked<T> trimByVisibility() {
            if (this.next == null) {
                return this;
            }
            Linked<T> linked = this.next.trimByVisibility();
            if (this.name != null) {
                if (linked.name != null) return this.withNext(linked);
                return this.withNext(null);
            }
            Linked<T> linked2 = linked;
            if (linked.name != null) return linked2;
            if (this.isVisible == linked.isVisible) {
                return this.withNext(linked);
            }
            linked2 = linked;
            if (!this.isVisible) return linked2;
            return this.withNext(null);
        }

        public Linked<T> withNext(Linked<T> linked) {
            if (linked == this.next) {
                return this;
            }
            return new Linked<T>(this.value, linked, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
        }

        public Linked<T> withValue(T t2) {
            if (t2 == this.value) {
                return this;
            }
            return new Linked<T>(t2, this.next, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
        }

        public Linked<T> withoutIgnored() {
            Linked<T> linked;
            if (this.isMarkedIgnored) {
                if (this.next == null) {
                    return null;
                }
                return this.next.withoutIgnored();
            }
            if (this.next != null && (linked = this.next.withoutIgnored()) != this.next) {
                return this.withNext(linked);
            }
            return this;
        }

        public Linked<T> withoutNext() {
            if (this.next == null) {
                return this;
            }
            return new Linked<T>(this.value, null, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
        }

        /*
         * Enabled aggressive block sorting
         */
        public Linked<T> withoutNonVisible() {
            Linked<T> linked = this.next == null ? null : this.next.withoutNonVisible();
            Linked<T> linked2 = linked;
            if (!this.isVisible) return linked2;
            return this.withNext(linked);
        }
    }

    private static interface WithMember<T> {
        public T withMember(AnnotatedMember var1);
    }

}

