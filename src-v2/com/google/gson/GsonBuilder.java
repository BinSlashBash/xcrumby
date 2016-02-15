/*
 * Decompiled with CFR 0_110.
 */
package com.google.gson;

import com.google.gson.DefaultDateTypeAdapter;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.TreeTypeAdapter;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GsonBuilder {
    private boolean complexMapKeySerialization;
    private String datePattern;
    private int dateStyle = 2;
    private boolean escapeHtmlChars = true;
    private Excluder excluder = Excluder.DEFAULT;
    private final List<TypeAdapterFactory> factories = new ArrayList<TypeAdapterFactory>();
    private FieldNamingStrategy fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
    private boolean generateNonExecutableJson;
    private final List<TypeAdapterFactory> hierarchyFactories = new ArrayList<TypeAdapterFactory>();
    private final Map<Type, InstanceCreator<?>> instanceCreators = new HashMap();
    private LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;
    private boolean prettyPrinting;
    private boolean serializeNulls;
    private boolean serializeSpecialFloatingPointValues;
    private int timeStyle = 2;

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void addTypeAdaptersForDate(String object, int n2, int n3, List<TypeAdapterFactory> list) {
        if (object != null && !"".equals(object.trim())) {
            object = new DefaultDateTypeAdapter((String)object);
        } else {
            if (n2 == 2) return;
            if (n3 == 2) return;
            object = new DefaultDateTypeAdapter(n2, n3);
        }
        list.add(TreeTypeAdapter.newFactory(TypeToken.get(java.util.Date.class), object));
        list.add(TreeTypeAdapter.newFactory(TypeToken.get(Timestamp.class), object));
        list.add(TreeTypeAdapter.newFactory(TypeToken.get(Date.class), object));
    }

    public GsonBuilder addDeserializationExclusionStrategy(ExclusionStrategy exclusionStrategy) {
        this.excluder = this.excluder.withExclusionStrategy(exclusionStrategy, false, true);
        return this;
    }

    public GsonBuilder addSerializationExclusionStrategy(ExclusionStrategy exclusionStrategy) {
        this.excluder = this.excluder.withExclusionStrategy(exclusionStrategy, true, false);
        return this;
    }

    public Gson create() {
        ArrayList<TypeAdapterFactory> arrayList = new ArrayList<TypeAdapterFactory>();
        arrayList.addAll(this.factories);
        Collections.reverse(arrayList);
        arrayList.addAll(this.hierarchyFactories);
        this.addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, arrayList);
        return new Gson(this.excluder, this.fieldNamingPolicy, this.instanceCreators, this.serializeNulls, this.complexMapKeySerialization, this.generateNonExecutableJson, this.escapeHtmlChars, this.prettyPrinting, this.serializeSpecialFloatingPointValues, this.longSerializationPolicy, arrayList);
    }

    public GsonBuilder disableHtmlEscaping() {
        this.escapeHtmlChars = false;
        return this;
    }

    public GsonBuilder disableInnerClassSerialization() {
        this.excluder = this.excluder.disableInnerClassSerialization();
        return this;
    }

    public GsonBuilder enableComplexMapKeySerialization() {
        this.complexMapKeySerialization = true;
        return this;
    }

    public /* varargs */ GsonBuilder excludeFieldsWithModifiers(int ... arrn) {
        this.excluder = this.excluder.withModifiers(arrn);
        return this;
    }

    public GsonBuilder excludeFieldsWithoutExposeAnnotation() {
        this.excluder = this.excluder.excludeFieldsWithoutExposeAnnotation();
        return this;
    }

    public GsonBuilder generateNonExecutableJson() {
        this.generateNonExecutableJson = true;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public GsonBuilder registerTypeAdapter(Type type, Object object) {
        boolean bl2 = object instanceof JsonSerializer || object instanceof JsonDeserializer || object instanceof InstanceCreator || object instanceof TypeAdapter;
        $Gson$Preconditions.checkArgument(bl2);
        if (object instanceof InstanceCreator) {
            this.instanceCreators.put(type, (InstanceCreator)object);
        }
        if (object instanceof JsonSerializer || object instanceof JsonDeserializer) {
            TypeToken typeToken = TypeToken.get(type);
            this.factories.add(TreeTypeAdapter.newFactoryWithMatchRawType(typeToken, object));
        }
        if (object instanceof TypeAdapter) {
            this.factories.add(TypeAdapters.newFactory(TypeToken.get(type), (TypeAdapter)object));
        }
        return this;
    }

    public GsonBuilder registerTypeAdapterFactory(TypeAdapterFactory typeAdapterFactory) {
        this.factories.add(typeAdapterFactory);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public GsonBuilder registerTypeHierarchyAdapter(Class<?> class_, Object object) {
        boolean bl2 = object instanceof JsonSerializer || object instanceof JsonDeserializer || object instanceof TypeAdapter;
        $Gson$Preconditions.checkArgument(bl2);
        if (object instanceof JsonDeserializer || object instanceof JsonSerializer) {
            this.hierarchyFactories.add(0, TreeTypeAdapter.newTypeHierarchyFactory(class_, object));
        }
        if (object instanceof TypeAdapter) {
            this.factories.add(TypeAdapters.newTypeHierarchyFactory(class_, (TypeAdapter)object));
        }
        return this;
    }

    public GsonBuilder serializeNulls() {
        this.serializeNulls = true;
        return this;
    }

    public GsonBuilder serializeSpecialFloatingPointValues() {
        this.serializeSpecialFloatingPointValues = true;
        return this;
    }

    public GsonBuilder setDateFormat(int n2) {
        this.dateStyle = n2;
        this.datePattern = null;
        return this;
    }

    public GsonBuilder setDateFormat(int n2, int n3) {
        this.dateStyle = n2;
        this.timeStyle = n3;
        this.datePattern = null;
        return this;
    }

    public GsonBuilder setDateFormat(String string2) {
        this.datePattern = string2;
        return this;
    }

    public /* varargs */ GsonBuilder setExclusionStrategies(ExclusionStrategy ... arrexclusionStrategy) {
        for (ExclusionStrategy exclusionStrategy : arrexclusionStrategy) {
            this.excluder = this.excluder.withExclusionStrategy(exclusionStrategy, true, true);
        }
        return this;
    }

    public GsonBuilder setFieldNamingPolicy(FieldNamingPolicy fieldNamingPolicy) {
        this.fieldNamingPolicy = fieldNamingPolicy;
        return this;
    }

    public GsonBuilder setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
        this.fieldNamingPolicy = fieldNamingStrategy;
        return this;
    }

    public GsonBuilder setLongSerializationPolicy(LongSerializationPolicy longSerializationPolicy) {
        this.longSerializationPolicy = longSerializationPolicy;
        return this;
    }

    public GsonBuilder setPrettyPrinting() {
        this.prettyPrinting = true;
        return this;
    }

    public GsonBuilder setVersion(double d2) {
        this.excluder = this.excluder.withVersion(d2);
        return this;
    }
}

