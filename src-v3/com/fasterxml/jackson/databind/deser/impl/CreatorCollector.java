package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CreatorCollector {
    protected final BeanDescription _beanDesc;
    protected AnnotatedWithParams _booleanCreator;
    protected final boolean _canFixAccess;
    protected AnnotatedWithParams _defaultConstructor;
    protected CreatorProperty[] _delegateArgs;
    protected AnnotatedWithParams _delegateCreator;
    protected AnnotatedWithParams _doubleCreator;
    protected AnnotatedParameter _incompleteParameter;
    protected AnnotatedWithParams _intCreator;
    protected AnnotatedWithParams _longCreator;
    protected CreatorProperty[] _propertyBasedArgs;
    protected AnnotatedWithParams _propertyBasedCreator;
    protected AnnotatedWithParams _stringCreator;

    protected static final class Vanilla extends ValueInstantiator implements Serializable {
        public static final int TYPE_COLLECTION = 1;
        public static final int TYPE_HASH_MAP = 3;
        public static final int TYPE_MAP = 2;
        private static final long serialVersionUID = 1;
        private final int _type;

        public Vanilla(int t) {
            this._type = t;
        }

        public String getValueTypeDesc() {
            switch (this._type) {
                case TYPE_COLLECTION /*1*/:
                    return ArrayList.class.getName();
                case TYPE_MAP /*2*/:
                    return LinkedHashMap.class.getName();
                case TYPE_HASH_MAP /*3*/:
                    return HashMap.class.getName();
                default:
                    return Object.class.getName();
            }
        }

        public boolean canInstantiate() {
            return true;
        }

        public boolean canCreateUsingDefault() {
            return true;
        }

        public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
            switch (this._type) {
                case TYPE_COLLECTION /*1*/:
                    return new ArrayList();
                case TYPE_MAP /*2*/:
                    return new LinkedHashMap();
                case TYPE_HASH_MAP /*3*/:
                    return new HashMap();
                default:
                    throw new IllegalStateException("Unknown type " + this._type);
            }
        }
    }

    public CreatorCollector(BeanDescription beanDesc, boolean canFixAccess) {
        this._propertyBasedArgs = null;
        this._beanDesc = beanDesc;
        this._canFixAccess = canFixAccess;
    }

    public ValueInstantiator constructValueInstantiator(DeserializationConfig config) {
        boolean maybeVanilla;
        JavaType delegateType;
        int i = 0;
        if (this._delegateCreator == null) {
            maybeVanilla = true;
        } else {
            maybeVanilla = false;
        }
        if (maybeVanilla) {
            delegateType = null;
        } else {
            int ix = 0;
            if (this._delegateArgs != null) {
                int len = this._delegateArgs.length;
                for (int i2 = 0; i2 < len; i2++) {
                    if (this._delegateArgs[i2] == null) {
                        ix = i2;
                        break;
                    }
                }
            }
            delegateType = this._beanDesc.bindingsForBeanType().resolveType(this._delegateCreator.getGenericParameterType(ix));
        }
        JavaType type = this._beanDesc.getType();
        if (this._propertyBasedCreator == null && this._delegateCreator == null && this._stringCreator == null && this._longCreator == null && this._doubleCreator == null && this._booleanCreator == null) {
            i = 1;
        }
        if (maybeVanilla & i) {
            Class<?> rawType = type.getRawClass();
            if (rawType == Collection.class || rawType == List.class || rawType == ArrayList.class) {
                return new Vanilla(1);
            }
            if (rawType == Map.class || rawType == LinkedHashMap.class) {
                return new Vanilla(2);
            }
            if (rawType == HashMap.class) {
                return new Vanilla(3);
            }
        }
        ValueInstantiator inst = new StdValueInstantiator(config, type);
        inst.configureFromObjectSettings(this._defaultConstructor, this._delegateCreator, delegateType, this._delegateArgs, this._propertyBasedCreator, this._propertyBasedArgs);
        inst.configureFromStringCreator(this._stringCreator);
        inst.configureFromIntCreator(this._intCreator);
        inst.configureFromLongCreator(this._longCreator);
        inst.configureFromDoubleCreator(this._doubleCreator);
        inst.configureFromBooleanCreator(this._booleanCreator);
        inst.configureIncompleteParameter(this._incompleteParameter);
        return inst;
    }

    public void setDefaultCreator(AnnotatedWithParams creator) {
        this._defaultConstructor = (AnnotatedWithParams) _fixAccess(creator);
    }

    public void addStringCreator(AnnotatedWithParams creator) {
        this._stringCreator = verifyNonDup(creator, this._stringCreator, "String");
    }

    public void addIntCreator(AnnotatedWithParams creator) {
        this._intCreator = verifyNonDup(creator, this._intCreator, "int");
    }

    public void addLongCreator(AnnotatedWithParams creator) {
        this._longCreator = verifyNonDup(creator, this._longCreator, "long");
    }

    public void addDoubleCreator(AnnotatedWithParams creator) {
        this._doubleCreator = verifyNonDup(creator, this._doubleCreator, "double");
    }

    public void addBooleanCreator(AnnotatedWithParams creator) {
        this._booleanCreator = verifyNonDup(creator, this._booleanCreator, "boolean");
    }

    public void addDelegatingCreator(AnnotatedWithParams creator, CreatorProperty[] injectables) {
        this._delegateCreator = verifyNonDup(creator, this._delegateCreator, "delegate");
        this._delegateArgs = injectables;
    }

    public void addPropertyCreator(AnnotatedWithParams creator, CreatorProperty[] properties) {
        this._propertyBasedCreator = verifyNonDup(creator, this._propertyBasedCreator, "property-based");
        if (properties.length > 1) {
            HashMap<String, Integer> names = new HashMap();
            int i = 0;
            int len = properties.length;
            while (i < len) {
                String name = properties[i].getName();
                if (name.length() != 0 || properties[i].getInjectableValueId() == null) {
                    Integer old = (Integer) names.put(name, Integer.valueOf(i));
                    if (old != null) {
                        throw new IllegalArgumentException("Duplicate creator property \"" + name + "\" (index " + old + " vs " + i + ")");
                    }
                }
                i++;
            }
        }
        this._propertyBasedArgs = properties;
    }

    public void addIncompeteParameter(AnnotatedParameter parameter) {
        if (this._incompleteParameter == null) {
            this._incompleteParameter = parameter;
        }
    }

    public boolean hasDefaultCreator() {
        return this._defaultConstructor != null;
    }

    private <T extends AnnotatedMember> T _fixAccess(T member) {
        if (member != null && this._canFixAccess) {
            ClassUtil.checkAndFixAccess((Member) member.getAnnotated());
        }
        return member;
    }

    protected AnnotatedWithParams verifyNonDup(AnnotatedWithParams newOne, AnnotatedWithParams oldOne, String type) {
        if (oldOne == null || oldOne.getClass() != newOne.getClass()) {
            return (AnnotatedWithParams) _fixAccess(newOne);
        }
        throw new IllegalArgumentException("Conflicting " + type + " creators: already had " + oldOne + ", encountered " + newOne);
    }
}
