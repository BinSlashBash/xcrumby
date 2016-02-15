/*
 * Decompiled with CFR 0_110.
 */
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
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
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
    protected CreatorProperty[] _propertyBasedArgs = null;
    protected AnnotatedWithParams _propertyBasedCreator;
    protected AnnotatedWithParams _stringCreator;

    public CreatorCollector(BeanDescription beanDescription, boolean bl2) {
        this._beanDesc = beanDescription;
        this._canFixAccess = bl2;
    }

    private <T extends AnnotatedMember> T _fixAccess(T t2) {
        if (t2 != null && this._canFixAccess) {
            ClassUtil.checkAndFixAccess((Member)((Object)t2.getAnnotated()));
        }
        return t2;
    }

    public void addBooleanCreator(AnnotatedWithParams annotatedWithParams) {
        this._booleanCreator = this.verifyNonDup(annotatedWithParams, this._booleanCreator, "boolean");
    }

    public void addDelegatingCreator(AnnotatedWithParams annotatedWithParams, CreatorProperty[] arrcreatorProperty) {
        this._delegateCreator = this.verifyNonDup(annotatedWithParams, this._delegateCreator, "delegate");
        this._delegateArgs = arrcreatorProperty;
    }

    public void addDoubleCreator(AnnotatedWithParams annotatedWithParams) {
        this._doubleCreator = this.verifyNonDup(annotatedWithParams, this._doubleCreator, "double");
    }

    public void addIncompeteParameter(AnnotatedParameter annotatedParameter) {
        if (this._incompleteParameter == null) {
            this._incompleteParameter = annotatedParameter;
        }
    }

    public void addIntCreator(AnnotatedWithParams annotatedWithParams) {
        this._intCreator = this.verifyNonDup(annotatedWithParams, this._intCreator, "int");
    }

    public void addLongCreator(AnnotatedWithParams annotatedWithParams) {
        this._longCreator = this.verifyNonDup(annotatedWithParams, this._longCreator, "long");
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addPropertyCreator(AnnotatedWithParams serializable, CreatorProperty[] arrcreatorProperty) {
        void var2_3;
        this._propertyBasedCreator = this.verifyNonDup((AnnotatedWithParams)serializable, this._propertyBasedCreator, "property-based");
        if (var2_3.length > 1) {
            HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
            int n2 = var2_3.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                Integer n3;
                String string2 = var2_3[i2].getName();
                if (string2.length() == 0 && var2_3[i2].getInjectableValueId() != null || (n3 = hashMap.put(string2, i2)) == null) continue;
                throw new IllegalArgumentException("Duplicate creator property \"" + string2 + "\" (index " + n3 + " vs " + i2 + ")");
            }
        }
        this._propertyBasedArgs = var2_3;
    }

    public void addStringCreator(AnnotatedWithParams annotatedWithParams) {
        this._stringCreator = this.verifyNonDup(annotatedWithParams, this._stringCreator, "String");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public ValueInstantiator constructValueInstantiator(DeserializationConfig var1_1) {
        var5_3 = 0;
        var3_4 = this._delegateCreator == null;
        if (!var3_4) ** GOTO lbl6
        var8_5 = null;
        ** GOTO lbl9
lbl6: // 1 sources:
        var4_10 = var6_9 = 0;
        if (this._delegateArgs == null) ** GOTO lbl51
        ** GOTO lbl44
lbl9: // 2 sources:
        do {
            var9_7 = this._beanDesc.getType();
            var2_6 = var5_3;
            if (this._propertyBasedCreator == null) {
                var2_6 = var5_3;
                if (this._delegateCreator == null) {
                    var2_6 = var5_3;
                    if (this._stringCreator == null) {
                        var2_6 = var5_3;
                        if (this._longCreator == null) {
                            var2_6 = var5_3;
                            if (this._doubleCreator == null) {
                                var2_6 = var5_3;
                                if (this._booleanCreator == null) {
                                    var2_6 = 1;
                                }
                            }
                        }
                    }
                }
            }
            if (var3_4 & var2_6) {
                var10_8 = var9_7.getRawClass();
                if (var10_8 == Collection.class) return new Vanilla(1);
                if (var10_8 == List.class) return new Vanilla(1);
                if (var10_8 == ArrayList.class) {
                    return new Vanilla(1);
                }
                if (var10_8 == Map.class) return new Vanilla(2);
                if (var10_8 == LinkedHashMap.class) {
                    return new Vanilla(2);
                }
                if (var10_8 == HashMap.class) {
                    return new Vanilla(3);
                }
            }
            var1_2 = new StdValueInstantiator(var1_1 /* !! */ , var9_7);
            var1_2.configureFromObjectSettings(this._defaultConstructor, this._delegateCreator, var8_5, this._delegateArgs, this._propertyBasedCreator, this._propertyBasedArgs);
            var1_2.configureFromStringCreator(this._stringCreator);
            var1_2.configureFromIntCreator(this._intCreator);
            var1_2.configureFromLongCreator(this._longCreator);
            var1_2.configureFromDoubleCreator(this._doubleCreator);
            var1_2.configureFromBooleanCreator(this._booleanCreator);
            var1_2.configureIncompleteParameter(this._incompleteParameter);
            return var1_2;
            break;
        } while (true);
lbl44: // 1 sources:
        var2_6 = 0;
        var7_11 = this._delegateArgs.length;
        do {
            var4_10 = var6_9;
            if (var2_6 >= var7_11) ** GOTO lbl51
            if (this._delegateArgs[var2_6] == null) {
                var4_10 = var2_6;
lbl51: // 3 sources:
                var8_5 = this._beanDesc.bindingsForBeanType().resolveType(this._delegateCreator.getGenericParameterType(var4_10));
                ** continue;
            }
            ++var2_6;
        } while (true);
    }

    public boolean hasDefaultCreator() {
        if (this._defaultConstructor != null) {
            return true;
        }
        return false;
    }

    public void setDefaultCreator(AnnotatedWithParams annotatedWithParams) {
        this._defaultConstructor = this._fixAccess(annotatedWithParams);
    }

    protected AnnotatedWithParams verifyNonDup(AnnotatedWithParams annotatedWithParams, AnnotatedWithParams annotatedWithParams2, String string2) {
        if (annotatedWithParams2 != null && annotatedWithParams2.getClass() == annotatedWithParams.getClass()) {
            throw new IllegalArgumentException("Conflicting " + string2 + " creators: already had " + annotatedWithParams2 + ", encountered " + annotatedWithParams);
        }
        return this._fixAccess(annotatedWithParams);
    }

    protected static final class Vanilla
    extends ValueInstantiator
    implements Serializable {
        public static final int TYPE_COLLECTION = 1;
        public static final int TYPE_HASH_MAP = 3;
        public static final int TYPE_MAP = 2;
        private static final long serialVersionUID = 1;
        private final int _type;

        public Vanilla(int n2) {
            this._type = n2;
        }

        @Override
        public boolean canCreateUsingDefault() {
            return true;
        }

        @Override
        public boolean canInstantiate() {
            return true;
        }

        @Override
        public Object createUsingDefault(DeserializationContext deserializationContext) throws IOException {
            switch (this._type) {
                default: {
                    throw new IllegalStateException("Unknown type " + this._type);
                }
                case 1: {
                    return new ArrayList();
                }
                case 2: {
                    return new LinkedHashMap();
                }
                case 3: 
            }
            return new HashMap();
        }

        @Override
        public String getValueTypeDesc() {
            switch (this._type) {
                default: {
                    return Object.class.getName();
                }
                case 1: {
                    return ArrayList.class.getName();
                }
                case 2: {
                    return LinkedHashMap.class.getName();
                }
                case 3: 
            }
            return HashMap.class.getName();
        }
    }

}

