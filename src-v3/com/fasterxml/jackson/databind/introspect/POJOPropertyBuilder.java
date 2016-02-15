package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class POJOPropertyBuilder extends BeanPropertyDefinition implements Comparable<POJOPropertyBuilder> {
    protected final AnnotationIntrospector _annotationIntrospector;
    protected Linked<AnnotatedParameter> _ctorParameters;
    protected Linked<AnnotatedField> _fields;
    protected final boolean _forSerialization;
    protected Linked<AnnotatedMethod> _getters;
    protected final PropertyName _internalName;
    protected final PropertyName _name;
    protected Linked<AnnotatedMethod> _setters;

    private static final class Linked<T> {
        public final boolean isMarkedIgnored;
        public final boolean isNameExplicit;
        public final boolean isVisible;
        public final PropertyName name;
        public final Linked<T> next;
        public final T value;

        public Linked(T v, Linked<T> n, PropertyName name, boolean explName, boolean visible, boolean ignored) {
            PropertyName propertyName;
            this.value = v;
            this.next = n;
            if (name == null || name.isEmpty()) {
                propertyName = null;
            } else {
                propertyName = name;
            }
            this.name = propertyName;
            if (explName) {
                if (this.name == null) {
                    throw new IllegalArgumentException("Can not pass true for 'explName' if name is null/empty");
                } else if (!name.hasSimpleName()) {
                    explName = false;
                }
            }
            this.isNameExplicit = explName;
            this.isVisible = visible;
            this.isMarkedIgnored = ignored;
        }

        public Linked<T> withoutNext() {
            return this.next == null ? this : new Linked(this.value, null, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
        }

        public Linked<T> withValue(T newValue) {
            if (newValue == this.value) {
                return this;
            }
            return new Linked(newValue, this.next, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
        }

        public Linked<T> withNext(Linked<T> newNext) {
            if (newNext == this.next) {
                return this;
            }
            return new Linked(this.value, newNext, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
        }

        public Linked<T> withoutIgnored() {
            if (this.isMarkedIgnored) {
                return this.next == null ? null : this.next.withoutIgnored();
            } else {
                if (this.next != null) {
                    Linked<T> newNext = this.next.withoutIgnored();
                    if (newNext != this.next) {
                        return withNext(newNext);
                    }
                }
                return this;
            }
        }

        public Linked<T> withoutNonVisible() {
            Linked<T> newNext = this.next == null ? null : this.next.withoutNonVisible();
            return this.isVisible ? withNext(newNext) : newNext;
        }

        private Linked<T> append(Linked<T> appendable) {
            if (this.next == null) {
                return withNext(appendable);
            }
            return withNext(this.next.append(appendable));
        }

        public Linked<T> trimByVisibility() {
            if (this.next == null) {
                return this;
            }
            Linked<T> newNext = this.next.trimByVisibility();
            if (this.name != null) {
                if (newNext.name == null) {
                    return withNext(null);
                }
                return withNext(newNext);
            } else if (newNext.name != null) {
                return newNext;
            } else {
                if (this.isVisible == newNext.isVisible) {
                    return withNext(newNext);
                }
                return this.isVisible ? withNext(null) : newNext;
            }
        }

        public String toString() {
            String msg = this.value.toString() + "[visible=" + this.isVisible + ",ignore=" + this.isMarkedIgnored + ",explicitName=" + this.isNameExplicit + "]";
            if (this.next != null) {
                return msg + ", " + this.next.toString();
            }
            return msg;
        }
    }

    private interface WithMember<T> {
        T withMember(AnnotatedMember annotatedMember);
    }

    /* renamed from: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.1 */
    class C07411 implements WithMember<Class<?>[]> {
        C07411() {
        }

        public Class<?>[] withMember(AnnotatedMember member) {
            return POJOPropertyBuilder.this._annotationIntrospector.findViews(member);
        }
    }

    /* renamed from: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.2 */
    class C07422 implements WithMember<ReferenceProperty> {
        C07422() {
        }

        public ReferenceProperty withMember(AnnotatedMember member) {
            return POJOPropertyBuilder.this._annotationIntrospector.findReferenceType(member);
        }
    }

    /* renamed from: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.3 */
    class C07433 implements WithMember<Boolean> {
        C07433() {
        }

        public Boolean withMember(AnnotatedMember member) {
            return POJOPropertyBuilder.this._annotationIntrospector.isTypeId(member);
        }
    }

    /* renamed from: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.4 */
    class C07444 implements WithMember<Boolean> {
        C07444() {
        }

        public Boolean withMember(AnnotatedMember member) {
            return POJOPropertyBuilder.this._annotationIntrospector.hasRequiredMarker(member);
        }
    }

    /* renamed from: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.5 */
    class C07455 implements WithMember<String> {
        C07455() {
        }

        public String withMember(AnnotatedMember member) {
            return POJOPropertyBuilder.this._annotationIntrospector.findPropertyDescription(member);
        }
    }

    /* renamed from: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.6 */
    class C07466 implements WithMember<Integer> {
        C07466() {
        }

        public Integer withMember(AnnotatedMember member) {
            return POJOPropertyBuilder.this._annotationIntrospector.findPropertyIndex(member);
        }
    }

    /* renamed from: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.7 */
    class C07477 implements WithMember<ObjectIdInfo> {
        C07477() {
        }

        public ObjectIdInfo withMember(AnnotatedMember member) {
            ObjectIdInfo info = POJOPropertyBuilder.this._annotationIntrospector.findObjectIdInfo(member);
            if (info != null) {
                return POJOPropertyBuilder.this._annotationIntrospector.findObjectReferenceInfo(member, info);
            }
            return info;
        }
    }

    public POJOPropertyBuilder(PropertyName internalName, AnnotationIntrospector ai, boolean forSerialization) {
        this(internalName, internalName, ai, forSerialization);
    }

    protected POJOPropertyBuilder(PropertyName internalName, PropertyName name, AnnotationIntrospector annotationIntrospector, boolean forSerialization) {
        this._internalName = internalName;
        this._name = name;
        this._annotationIntrospector = annotationIntrospector;
        this._forSerialization = forSerialization;
    }

    @Deprecated
    public POJOPropertyBuilder(String simpleInternalName, AnnotationIntrospector annotationIntrospector, boolean forSerialization) {
        this(new PropertyName(simpleInternalName), annotationIntrospector, forSerialization);
    }

    public POJOPropertyBuilder(POJOPropertyBuilder src, PropertyName newName) {
        this._internalName = src._internalName;
        this._name = newName;
        this._annotationIntrospector = src._annotationIntrospector;
        this._fields = src._fields;
        this._ctorParameters = src._ctorParameters;
        this._getters = src._getters;
        this._setters = src._setters;
        this._forSerialization = src._forSerialization;
    }

    @Deprecated
    public POJOPropertyBuilder withName(String newName) {
        return withSimpleName(newName);
    }

    public POJOPropertyBuilder withName(PropertyName newName) {
        return new POJOPropertyBuilder(this, newName);
    }

    public POJOPropertyBuilder withSimpleName(String newSimpleName) {
        PropertyName newName = this._name.withSimpleName(newSimpleName);
        return newName == this._name ? this : new POJOPropertyBuilder(this, newName);
    }

    public int compareTo(POJOPropertyBuilder other) {
        if (this._ctorParameters != null) {
            if (other._ctorParameters == null) {
                return -1;
            }
        } else if (other._ctorParameters != null) {
            return 1;
        }
        return getName().compareTo(other.getName());
    }

    public String getName() {
        return this._name == null ? null : this._name.getSimpleName();
    }

    public PropertyName getFullName() {
        return this._name;
    }

    public String getInternalName() {
        return this._internalName.getSimpleName();
    }

    public PropertyName getWrapperName() {
        AnnotatedMember member = getPrimaryMember();
        return (member == null || this._annotationIntrospector == null) ? null : this._annotationIntrospector.findWrapperName(member);
    }

    public boolean isExplicitlyIncluded() {
        return _anyExplicits(this._fields) || _anyExplicits(this._getters) || _anyExplicits(this._setters) || _anyExplicits(this._ctorParameters);
    }

    public boolean isExplicitlyNamed() {
        return _anyExplicitNames(this._fields) || _anyExplicitNames(this._getters) || _anyExplicitNames(this._setters) || _anyExplicitNames(this._ctorParameters);
    }

    public boolean hasGetter() {
        return this._getters != null;
    }

    public boolean hasSetter() {
        return this._setters != null;
    }

    public boolean hasField() {
        return this._fields != null;
    }

    public boolean hasConstructorParameter() {
        return this._ctorParameters != null;
    }

    public boolean couldDeserialize() {
        return (this._ctorParameters == null && this._setters == null && this._fields == null) ? false : true;
    }

    public boolean couldSerialize() {
        return (this._getters == null && this._fields == null) ? false : true;
    }

    public AnnotatedMethod getGetter() {
        Linked<AnnotatedMethod> curr = this._getters;
        if (curr == null) {
            return null;
        }
        Linked<AnnotatedMethod> next = curr.next;
        if (next == null) {
            return (AnnotatedMethod) curr.value;
        }
        while (next != null) {
            Class<?> currClass = ((AnnotatedMethod) curr.value).getDeclaringClass();
            Class<?> nextClass = ((AnnotatedMethod) next.value).getDeclaringClass();
            if (currClass != nextClass) {
                if (currClass.isAssignableFrom(nextClass)) {
                    curr = next;
                } else if (nextClass.isAssignableFrom(currClass)) {
                    continue;
                }
                next = next.next;
            }
            int priNext = _getterPriority((AnnotatedMethod) next.value);
            int priCurr = _getterPriority((AnnotatedMethod) curr.value);
            if (priNext != priCurr) {
                if (priNext < priCurr) {
                    curr = next;
                }
                next = next.next;
            } else {
                throw new IllegalArgumentException("Conflicting getter definitions for property \"" + getName() + "\": " + ((AnnotatedMethod) curr.value).getFullName() + " vs " + ((AnnotatedMethod) next.value).getFullName());
            }
        }
        this._getters = curr.withoutNext();
        return (AnnotatedMethod) curr.value;
    }

    public AnnotatedMethod getSetter() {
        Linked<AnnotatedMethod> curr = this._setters;
        if (curr == null) {
            return null;
        }
        Linked<AnnotatedMethod> next = curr.next;
        if (next == null) {
            return (AnnotatedMethod) curr.value;
        }
        while (next != null) {
            Class<?> currClass = ((AnnotatedMethod) curr.value).getDeclaringClass();
            Class<?> nextClass = ((AnnotatedMethod) next.value).getDeclaringClass();
            if (currClass != nextClass) {
                if (currClass.isAssignableFrom(nextClass)) {
                    curr = next;
                } else if (nextClass.isAssignableFrom(currClass)) {
                    continue;
                }
                next = next.next;
            }
            int priNext = _setterPriority((AnnotatedMethod) next.value);
            int priCurr = _setterPriority((AnnotatedMethod) curr.value);
            if (priNext != priCurr) {
                if (priNext < priCurr) {
                    curr = next;
                }
                next = next.next;
            } else {
                throw new IllegalArgumentException("Conflicting setter definitions for property \"" + getName() + "\": " + ((AnnotatedMethod) curr.value).getFullName() + " vs " + ((AnnotatedMethod) next.value).getFullName());
            }
        }
        this._setters = curr.withoutNext();
        return (AnnotatedMethod) curr.value;
    }

    public AnnotatedField getField() {
        if (this._fields == null) {
            return null;
        }
        AnnotatedField field = this._fields.value;
        Linked<AnnotatedField> next = this._fields.next;
        while (next != null) {
            AnnotatedField nextField = next.value;
            Class<?> fieldClass = field.getDeclaringClass();
            Class<?> nextClass = nextField.getDeclaringClass();
            if (fieldClass != nextClass) {
                if (fieldClass.isAssignableFrom(nextClass)) {
                    field = nextField;
                } else if (nextClass.isAssignableFrom(fieldClass)) {
                }
                next = next.next;
            }
            throw new IllegalArgumentException("Multiple fields representing property \"" + getName() + "\": " + field.getFullName() + " vs " + nextField.getFullName());
        }
        return field;
    }

    public AnnotatedParameter getConstructorParameter() {
        if (this._ctorParameters == null) {
            return null;
        }
        Linked<AnnotatedParameter> curr = this._ctorParameters;
        while (!(((AnnotatedParameter) curr.value).getOwner() instanceof AnnotatedConstructor)) {
            curr = curr.next;
            if (curr == null) {
                return (AnnotatedParameter) this._ctorParameters.value;
            }
        }
        return (AnnotatedParameter) curr.value;
    }

    public AnnotatedMember getAccessor() {
        AnnotatedMember m = getGetter();
        if (m == null) {
            return getField();
        }
        return m;
    }

    public AnnotatedMember getMutator() {
        AnnotatedMember m = getConstructorParameter();
        if (m != null) {
            return m;
        }
        m = getSetter();
        if (m == null) {
            return getField();
        }
        return m;
    }

    public AnnotatedMember getNonConstructorMutator() {
        AnnotatedMember m = getSetter();
        if (m == null) {
            return getField();
        }
        return m;
    }

    public AnnotatedMember getPrimaryMember() {
        if (this._forSerialization) {
            return getAccessor();
        }
        return getMutator();
    }

    protected int _getterPriority(AnnotatedMethod m) {
        String name = m.getName();
        if (name.startsWith("get") && name.length() > 3) {
            return 1;
        }
        if (!name.startsWith("is") || name.length() <= 2) {
            return 3;
        }
        return 2;
    }

    protected int _setterPriority(AnnotatedMethod m) {
        String name = m.getName();
        if (!name.startsWith("set") || name.length() <= 3) {
            return 2;
        }
        return 1;
    }

    public Class<?>[] findViews() {
        return (Class[]) fromMemberAnnotations(new C07411());
    }

    public ReferenceProperty findReferenceType() {
        return (ReferenceProperty) fromMemberAnnotations(new C07422());
    }

    public boolean isTypeId() {
        Boolean b = (Boolean) fromMemberAnnotations(new C07433());
        return b != null && b.booleanValue();
    }

    public PropertyMetadata getMetadata() {
        Boolean b = _findRequired();
        String desc = _findDescription();
        Integer idx = _findIndex();
        if (b == null && idx == null) {
            return desc == null ? PropertyMetadata.STD_REQUIRED_OR_OPTIONAL : PropertyMetadata.STD_REQUIRED_OR_OPTIONAL.withDescription(desc);
        } else {
            return PropertyMetadata.construct(b.booleanValue(), desc, idx);
        }
    }

    protected Boolean _findRequired() {
        return (Boolean) fromMemberAnnotations(new C07444());
    }

    protected String _findDescription() {
        return (String) fromMemberAnnotations(new C07455());
    }

    protected Integer _findIndex() {
        return (Integer) fromMemberAnnotations(new C07466());
    }

    public ObjectIdInfo findObjectIdInfo() {
        return (ObjectIdInfo) fromMemberAnnotations(new C07477());
    }

    public void addField(AnnotatedField a, PropertyName name, boolean explName, boolean visible, boolean ignored) {
        this._fields = new Linked(a, this._fields, name, explName, visible, ignored);
    }

    public void addCtor(AnnotatedParameter a, PropertyName name, boolean explName, boolean visible, boolean ignored) {
        this._ctorParameters = new Linked(a, this._ctorParameters, name, explName, visible, ignored);
    }

    public void addGetter(AnnotatedMethod a, PropertyName name, boolean explName, boolean visible, boolean ignored) {
        this._getters = new Linked(a, this._getters, name, explName, visible, ignored);
    }

    public void addSetter(AnnotatedMethod a, PropertyName name, boolean explName, boolean visible, boolean ignored) {
        this._setters = new Linked(a, this._setters, name, explName, visible, ignored);
    }

    public void addAll(POJOPropertyBuilder src) {
        this._fields = merge(this._fields, src._fields);
        this._ctorParameters = merge(this._ctorParameters, src._ctorParameters);
        this._getters = merge(this._getters, src._getters);
        this._setters = merge(this._setters, src._setters);
    }

    private static <T> Linked<T> merge(Linked<T> chain1, Linked<T> chain2) {
        if (chain1 == null) {
            return chain2;
        }
        if (chain2 == null) {
            return chain1;
        }
        return chain1.append(chain2);
    }

    @Deprecated
    public void addField(AnnotatedField a, String name, boolean visible, boolean ignored) {
        addField(a, _propName(name), name != null, visible, ignored);
    }

    @Deprecated
    public void addField(AnnotatedField a, String name, boolean explName, boolean visible, boolean ignored) {
        addField(a, _propName(name), explName, visible, ignored);
    }

    @Deprecated
    public void addCtor(AnnotatedParameter a, String name, boolean visible, boolean ignored) {
        addCtor(a, _propName(name), name != null, visible, ignored);
    }

    @Deprecated
    public void addCtor(AnnotatedParameter a, String name, boolean explName, boolean visible, boolean ignored) {
        addCtor(a, _propName(name), explName, visible, ignored);
    }

    @Deprecated
    public void addGetter(AnnotatedMethod a, String name, boolean visible, boolean ignored) {
        addGetter(a, _propName(name), name != null, visible, ignored);
    }

    @Deprecated
    public void addGetter(AnnotatedMethod a, String name, boolean explName, boolean visible, boolean ignored) {
        addGetter(a, _propName(name), explName, visible, ignored);
    }

    @Deprecated
    public void addSetter(AnnotatedMethod a, String name, boolean visible, boolean ignored) {
        addSetter(a, _propName(name), name != null, visible, ignored);
    }

    @Deprecated
    public void addSetter(AnnotatedMethod a, String name, boolean explName, boolean visible, boolean ignored) {
        addSetter(a, _propName(name), explName, visible, ignored);
    }

    private PropertyName _propName(String simple) {
        return PropertyName.construct(simple, null);
    }

    public void removeIgnored() {
        this._fields = _removeIgnored(this._fields);
        this._getters = _removeIgnored(this._getters);
        this._setters = _removeIgnored(this._setters);
        this._ctorParameters = _removeIgnored(this._ctorParameters);
    }

    public void removeNonVisible(boolean force) {
        this._getters = _removeNonVisible(this._getters);
        this._ctorParameters = _removeNonVisible(this._ctorParameters);
        if (force || this._getters == null) {
            this._fields = _removeNonVisible(this._fields);
            this._setters = _removeNonVisible(this._setters);
        }
    }

    public void trimByVisibility() {
        this._fields = _trimByVisibility(this._fields);
        this._getters = _trimByVisibility(this._getters);
        this._setters = _trimByVisibility(this._setters);
        this._ctorParameters = _trimByVisibility(this._ctorParameters);
    }

    public void mergeAnnotations(boolean forSerialization) {
        if (forSerialization) {
            if (this._getters != null) {
                this._getters = this._getters.withValue(((AnnotatedMethod) this._getters.value).withAnnotations(_mergeAnnotations(0, this._getters, this._fields, this._ctorParameters, this._setters)));
            } else if (this._fields != null) {
                this._fields = this._fields.withValue(((AnnotatedField) this._fields.value).withAnnotations(_mergeAnnotations(0, this._fields, this._ctorParameters, this._setters)));
            }
        } else if (this._ctorParameters != null) {
            this._ctorParameters = this._ctorParameters.withValue(((AnnotatedParameter) this._ctorParameters.value).withAnnotations(_mergeAnnotations(0, this._ctorParameters, this._setters, this._fields, this._getters)));
        } else if (this._setters != null) {
            this._setters = this._setters.withValue(((AnnotatedMethod) this._setters.value).withAnnotations(_mergeAnnotations(0, this._setters, this._fields, this._getters)));
        } else if (this._fields != null) {
            this._fields = this._fields.withValue(((AnnotatedField) this._fields.value).withAnnotations(_mergeAnnotations(0, this._fields, this._getters)));
        }
    }

    private AnnotationMap _mergeAnnotations(int index, Linked<? extends AnnotatedMember>... nodes) {
        AnnotationMap ann = ((AnnotatedMember) nodes[index].value).getAllAnnotations();
        for (index++; index < nodes.length; index++) {
            if (nodes[index] != null) {
                return AnnotationMap.merge(ann, _mergeAnnotations(index, nodes));
            }
        }
        return ann;
    }

    private <T> Linked<T> _removeIgnored(Linked<T> node) {
        return node == null ? node : node.withoutIgnored();
    }

    private <T> Linked<T> _removeNonVisible(Linked<T> node) {
        return node == null ? node : node.withoutNonVisible();
    }

    private <T> Linked<T> _trimByVisibility(Linked<T> node) {
        return node == null ? node : node.trimByVisibility();
    }

    private <T> boolean _anyExplicits(Linked<T> n) {
        while (n != null) {
            if (n.name != null && n.name.hasSimpleName()) {
                return true;
            }
            n = n.next;
        }
        return false;
    }

    private <T> boolean _anyExplicitNames(Linked<T> n) {
        while (n != null) {
            if (n.name != null && n.isNameExplicit) {
                return true;
            }
            n = n.next;
        }
        return false;
    }

    public boolean anyVisible() {
        return _anyVisible(this._fields) || _anyVisible(this._getters) || _anyVisible(this._setters) || _anyVisible(this._ctorParameters);
    }

    private <T> boolean _anyVisible(Linked<T> n) {
        while (n != null) {
            if (n.isVisible) {
                return true;
            }
            n = n.next;
        }
        return false;
    }

    public boolean anyIgnorals() {
        return _anyIgnorals(this._fields) || _anyIgnorals(this._getters) || _anyIgnorals(this._setters) || _anyIgnorals(this._ctorParameters);
    }

    private <T> boolean _anyIgnorals(Linked<T> n) {
        while (n != null) {
            if (n.isMarkedIgnored) {
                return true;
            }
            n = n.next;
        }
        return false;
    }

    @Deprecated
    public String findNewName() {
        Collection<PropertyName> l = findExplicitNames();
        if (l == null) {
            return null;
        }
        if (l.size() > 1) {
            throw new IllegalStateException("Conflicting/ambiguous property name definitions (implicit name '" + this._name + "'): found more than one explicit name: " + l);
        }
        PropertyName first = (PropertyName) l.iterator().next();
        if (first.equals(this._name)) {
            return null;
        }
        return first.getSimpleName();
    }

    public Set<PropertyName> findExplicitNames() {
        Set<PropertyName> renamed = _findExplicitNames(this._ctorParameters, _findExplicitNames(this._setters, _findExplicitNames(this._getters, _findExplicitNames(this._fields, null))));
        if (renamed == null) {
            return Collections.emptySet();
        }
        return renamed;
    }

    public Collection<POJOPropertyBuilder> explode(Collection<PropertyName> newNames) {
        HashMap<PropertyName, POJOPropertyBuilder> props = new HashMap();
        _explode(newNames, props, this._fields);
        _explode(newNames, props, this._getters);
        _explode(newNames, props, this._setters);
        _explode(newNames, props, this._ctorParameters);
        return props.values();
    }

    private void _explode(Collection<PropertyName> newNames, Map<PropertyName, POJOPropertyBuilder> props, Linked<?> accessors) {
        Linked<?> firstAcc = accessors;
        for (Linked<?> node = accessors; node != null; node = node.next) {
            PropertyName name = node.name;
            if (!node.isNameExplicit || name == null) {
                throw new IllegalStateException("Conflicting/ambiguous property name definitions (implicit name '" + this._name + "'): found multiple explicit names: " + newNames + ", but also implicit accessor: " + node);
            }
            POJOPropertyBuilder prop = (POJOPropertyBuilder) props.get(name);
            if (prop == null) {
                prop = new POJOPropertyBuilder(this._internalName, name, this._annotationIntrospector, this._forSerialization);
                props.put(name, prop);
            }
            if (firstAcc == this._fields) {
                prop._fields = node.withNext(prop._fields);
            } else if (firstAcc == this._getters) {
                prop._getters = node.withNext(prop._getters);
            } else if (firstAcc == this._setters) {
                prop._setters = node.withNext(prop._setters);
            } else if (firstAcc == this._ctorParameters) {
                prop._ctorParameters = node.withNext(prop._ctorParameters);
            } else {
                throw new IllegalStateException("Internal error: mismatched accessors, property: " + this);
            }
        }
    }

    private Set<PropertyName> _findExplicitNames(Linked<? extends AnnotatedMember> node, Set<PropertyName> renamed) {
        while (node != null) {
            if (node.isNameExplicit && node.name != null) {
                if (renamed == null) {
                    renamed = new HashSet();
                }
                renamed.add(node.name);
            }
            node = node.next;
        }
        return renamed;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Property '").append(this._name).append("'; ctors: ").append(this._ctorParameters).append(", field(s): ").append(this._fields).append(", getter(s): ").append(this._getters).append(", setter(s): ").append(this._setters);
        sb.append("]");
        return sb.toString();
    }

    protected <T> T fromMemberAnnotations(WithMember<T> func) {
        T result = null;
        if (this._annotationIntrospector == null) {
            return null;
        }
        if (!this._forSerialization) {
            if (this._ctorParameters != null) {
                result = func.withMember((AnnotatedMember) this._ctorParameters.value);
            }
            if (result == null && this._setters != null) {
                result = func.withMember((AnnotatedMember) this._setters.value);
            }
        } else if (this._getters != null) {
            result = func.withMember((AnnotatedMember) this._getters.value);
        }
        if (result != null || this._fields == null) {
            return result;
        }
        return func.withMember((AnnotatedMember) this._fields.value);
    }
}
