/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import java.io.IOException;
import java.io.Serializable;

@JacksonStdImpl
public class StdValueInstantiator
extends ValueInstantiator
implements Serializable {
    private static final long serialVersionUID = 1;
    protected CreatorProperty[] _constructorArguments;
    protected AnnotatedWithParams _defaultCreator;
    protected CreatorProperty[] _delegateArguments;
    protected AnnotatedWithParams _delegateCreator;
    protected JavaType _delegateType;
    protected AnnotatedWithParams _fromBooleanCreator;
    protected AnnotatedWithParams _fromDoubleCreator;
    protected AnnotatedWithParams _fromIntCreator;
    protected AnnotatedWithParams _fromLongCreator;
    protected AnnotatedWithParams _fromStringCreator;
    protected AnnotatedParameter _incompleteParameter;
    protected final String _valueTypeDesc;
    protected AnnotatedWithParams _withArgsCreator;

    /*
     * Enabled aggressive block sorting
     */
    public StdValueInstantiator(DeserializationConfig object, JavaType javaType) {
        object = javaType == null ? "UNKNOWN TYPE" : javaType.toString();
        this._valueTypeDesc = object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public StdValueInstantiator(DeserializationConfig object, Class<?> class_) {
        object = class_ == null ? "UNKNOWN TYPE" : class_.getName();
        this._valueTypeDesc = object;
    }

    protected StdValueInstantiator(StdValueInstantiator stdValueInstantiator) {
        this._valueTypeDesc = stdValueInstantiator._valueTypeDesc;
        this._defaultCreator = stdValueInstantiator._defaultCreator;
        this._constructorArguments = stdValueInstantiator._constructorArguments;
        this._withArgsCreator = stdValueInstantiator._withArgsCreator;
        this._delegateType = stdValueInstantiator._delegateType;
        this._delegateCreator = stdValueInstantiator._delegateCreator;
        this._delegateArguments = stdValueInstantiator._delegateArguments;
        this._fromStringCreator = stdValueInstantiator._fromStringCreator;
        this._fromIntCreator = stdValueInstantiator._fromIntCreator;
        this._fromLongCreator = stdValueInstantiator._fromLongCreator;
        this._fromDoubleCreator = stdValueInstantiator._fromDoubleCreator;
        this._fromBooleanCreator = stdValueInstantiator._fromBooleanCreator;
    }

    @Override
    public boolean canCreateFromBoolean() {
        if (this._fromBooleanCreator != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canCreateFromDouble() {
        if (this._fromDoubleCreator != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canCreateFromInt() {
        if (this._fromIntCreator != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canCreateFromLong() {
        if (this._fromLongCreator != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canCreateFromObjectWith() {
        if (this._withArgsCreator != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canCreateFromString() {
        if (this._fromStringCreator != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canCreateUsingDefault() {
        if (this._defaultCreator != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canCreateUsingDelegate() {
        if (this._delegateType != null) {
            return true;
        }
        return false;
    }

    public void configureFromBooleanCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromBooleanCreator = annotatedWithParams;
    }

    public void configureFromDoubleCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromDoubleCreator = annotatedWithParams;
    }

    public void configureFromIntCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromIntCreator = annotatedWithParams;
    }

    public void configureFromLongCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromLongCreator = annotatedWithParams;
    }

    public void configureFromObjectSettings(AnnotatedWithParams annotatedWithParams, AnnotatedWithParams annotatedWithParams2, JavaType javaType, CreatorProperty[] arrcreatorProperty, AnnotatedWithParams annotatedWithParams3, CreatorProperty[] arrcreatorProperty2) {
        this._defaultCreator = annotatedWithParams;
        this._delegateCreator = annotatedWithParams2;
        this._delegateType = javaType;
        this._delegateArguments = arrcreatorProperty;
        this._withArgsCreator = annotatedWithParams3;
        this._constructorArguments = arrcreatorProperty2;
    }

    public void configureFromStringCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromStringCreator = annotatedWithParams;
    }

    public void configureIncompleteParameter(AnnotatedParameter annotatedParameter) {
        this._incompleteParameter = annotatedParameter;
    }

    @Override
    public Object createFromBoolean(DeserializationContext object, boolean bl2) throws IOException, JsonProcessingException {
        try {
            if (this._fromBooleanCreator != null) {
                object = this._fromBooleanCreator.call1(bl2);
                return object;
            }
        }
        catch (Exception var1_2) {
            throw this.wrapException(var1_2);
        }
        catch (ExceptionInInitializerError var1_3) {
            throw this.wrapException(var1_3);
        }
        throw object.mappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from Boolean value (" + bl2 + "); no single-boolean/Boolean-arg constructor/factory method");
    }

    @Override
    public Object createFromDouble(DeserializationContext object, double d2) throws IOException, JsonProcessingException {
        try {
            if (this._fromDoubleCreator != null) {
                object = this._fromDoubleCreator.call1(d2);
                return object;
            }
        }
        catch (Exception var1_2) {
            throw this.wrapException(var1_2);
        }
        catch (ExceptionInInitializerError var1_3) {
            throw this.wrapException(var1_3);
        }
        throw object.mappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from Floating-point number (" + d2 + "); no one-double/Double-arg constructor/factory method");
    }

    @Override
    public Object createFromInt(DeserializationContext object, int n2) throws IOException, JsonProcessingException {
        try {
            if (this._fromIntCreator != null) {
                return this._fromIntCreator.call1(n2);
            }
            if (this._fromLongCreator != null) {
                object = this._fromLongCreator.call1(Long.valueOf(n2));
                return object;
            }
        }
        catch (Exception var1_2) {
            throw this.wrapException(var1_2);
        }
        catch (ExceptionInInitializerError var1_3) {
            throw this.wrapException(var1_3);
        }
        throw object.mappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from Integral number (" + n2 + "); no single-int-arg constructor/factory method");
    }

    @Override
    public Object createFromLong(DeserializationContext object, long l2) throws IOException, JsonProcessingException {
        try {
            if (this._fromLongCreator != null) {
                object = this._fromLongCreator.call1(l2);
                return object;
            }
        }
        catch (Exception var1_2) {
            throw this.wrapException(var1_2);
        }
        catch (ExceptionInInitializerError var1_3) {
            throw this.wrapException(var1_3);
        }
        throw object.mappingException("Can not instantiate value of type " + this.getValueTypeDesc() + " from Long integral number (" + l2 + "); no single-long-arg constructor/factory method");
    }

    @Override
    public Object createFromObjectWith(DeserializationContext object, Object[] arrobject) throws IOException, JsonProcessingException {
        if (this._withArgsCreator == null) {
            throw new IllegalStateException("No with-args constructor for " + this.getValueTypeDesc());
        }
        try {
            object = this._withArgsCreator.call(arrobject);
            return object;
        }
        catch (ExceptionInInitializerError var1_2) {
            throw this.wrapException(var1_2);
        }
        catch (Exception var1_3) {
            throw this.wrapException(var1_3);
        }
    }

    @Override
    public Object createFromString(DeserializationContext object, String string2) throws IOException, JsonProcessingException {
        if (this._fromStringCreator != null) {
            try {
                object = this._fromStringCreator.call1(string2);
                return object;
            }
            catch (Exception var1_2) {
                throw this.wrapException(var1_2);
            }
            catch (ExceptionInInitializerError var1_3) {
                throw this.wrapException(var1_3);
            }
        }
        return this._createFromStringFallbacks((DeserializationContext)object, string2);
    }

    @Override
    public Object createUsingDefault(DeserializationContext object) throws IOException, JsonProcessingException {
        if (this._defaultCreator == null) {
            throw new IllegalStateException("No default constructor for " + this.getValueTypeDesc());
        }
        try {
            object = this._defaultCreator.call();
            return object;
        }
        catch (ExceptionInInitializerError var1_2) {
            throw this.wrapException(var1_2);
        }
        catch (Exception var1_3) {
            throw this.wrapException(var1_3);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Object createUsingDelegate(DeserializationContext var1_1, Object var2_4) throws IOException, JsonProcessingException {
        if (this._delegateCreator == null) {
            throw new IllegalStateException("No delegate constructor for " + this.getValueTypeDesc());
        }
        try {
            if (this._delegateArguments == null) {
                return this._delegateCreator.call1(var2_4);
            }
            var4_5 = this._delegateArguments.length;
            var5_6 = new Object[var4_5];
            var3_7 = 0;
lbl9: // 2 sources:
            if (var3_7 >= var4_5) return this._delegateCreator.call(var5_6);
            var6_8 = this._delegateArguments[var3_7];
            var5_6[var3_7] = var6_8 == null ? var2_4 : var1_1.findInjectableValue(var6_8.getInjectableValueId(), var6_8, null);
        }
        catch (ExceptionInInitializerError var1_2) {
            throw this.wrapException(var1_2);
        }
        catch (Exception var1_3) {
            throw this.wrapException(var1_3);
        }
        ++var3_7;
        ** GOTO lbl9
    }

    @Override
    public AnnotatedWithParams getDefaultCreator() {
        return this._defaultCreator;
    }

    @Override
    public AnnotatedWithParams getDelegateCreator() {
        return this._delegateCreator;
    }

    @Override
    public JavaType getDelegateType(DeserializationConfig deserializationConfig) {
        return this._delegateType;
    }

    @Override
    public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig deserializationConfig) {
        return this._constructorArguments;
    }

    @Override
    public AnnotatedParameter getIncompleteParameter() {
        return this._incompleteParameter;
    }

    @Override
    public String getValueTypeDesc() {
        return this._valueTypeDesc;
    }

    @Override
    public AnnotatedWithParams getWithArgsCreator() {
        return this._withArgsCreator;
    }

    protected JsonMappingException wrapException(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof JsonMappingException) {
            return (JsonMappingException)throwable;
        }
        return new JsonMappingException("Instantiation of " + this.getValueTypeDesc() + " value failed: " + throwable.getMessage(), throwable);
    }
}

