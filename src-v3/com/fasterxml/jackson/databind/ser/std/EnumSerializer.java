package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.util.EnumValues;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

@JacksonStdImpl
public class EnumSerializer extends StdScalarSerializer<Enum<?>> implements ContextualSerializer {
    protected final Boolean _serializeAsIndex;
    protected final EnumValues _values;

    @Deprecated
    public EnumSerializer(EnumValues v) {
        this(v, null);
    }

    public EnumSerializer(EnumValues v, Boolean serializeAsIndex) {
        super(Enum.class, false);
        this._values = v;
        this._serializeAsIndex = serializeAsIndex;
    }

    public static EnumSerializer construct(Class<Enum<?>> enumClass, SerializationConfig config, BeanDescription beanDesc, Value format) {
        return new EnumSerializer(EnumValues.construct(config, enumClass), _isShapeWrittenUsingIndex(enumClass, format, true));
    }

    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property == null) {
            return this;
        }
        Value format = prov.getAnnotationIntrospector().findFormat(property.getMember());
        if (format == null) {
            return this;
        }
        Boolean serializeAsIndex = _isShapeWrittenUsingIndex(property.getType().getRawClass(), format, false);
        if (serializeAsIndex != this._serializeAsIndex) {
            return new EnumSerializer(this._values, serializeAsIndex);
        }
        return this;
    }

    public EnumValues getEnumValues() {
        return this._values;
    }

    public final void serialize(Enum<?> en, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        if (_serializeAsIndex(provider)) {
            jgen.writeNumber(en.ordinal());
        } else {
            jgen.writeString(this._values.serializedValueFor(en));
        }
    }

    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        if (_serializeAsIndex(provider)) {
            return createSchemaNode("integer", true);
        }
        JsonNode objectNode = createSchemaNode("string", true);
        if (typeHint == null || !provider.constructType(typeHint).isEnumType()) {
            return objectNode;
        }
        ArrayNode enumNode = objectNode.putArray("enum");
        for (SerializableString value : this._values.values()) {
            enumNode.add(value.getValue());
        }
        return objectNode;
    }

    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        if (visitor.getProvider().isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX)) {
            JsonIntegerFormatVisitor v2 = visitor.expectIntegerFormat(typeHint);
            if (v2 != null) {
                v2.numberType(NumberType.INT);
                return;
            }
            return;
        }
        JsonStringFormatVisitor stringVisitor = visitor.expectStringFormat(typeHint);
        if (typeHint != null && stringVisitor != null && typeHint.isEnumType()) {
            Set<String> enums = new LinkedHashSet();
            for (SerializableString value : this._values.values()) {
                enums.add(value.getValue());
            }
            stringVisitor.enumTypes(enums);
        }
    }

    protected final boolean _serializeAsIndex(SerializerProvider provider) {
        if (this._serializeAsIndex != null) {
            return this._serializeAsIndex.booleanValue();
        }
        return provider.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX);
    }

    protected static Boolean _isShapeWrittenUsingIndex(Class<?> enumClass, Value format, boolean fromClass) {
        Shape shape = format == null ? null : format.getShape();
        if (shape == null || shape == Shape.ANY || shape == Shape.SCALAR) {
            return null;
        }
        if (shape == Shape.STRING) {
            return Boolean.FALSE;
        }
        if (shape.isNumeric()) {
            return Boolean.TRUE;
        }
        throw new IllegalArgumentException("Unsupported serialization shape (" + shape + ") for Enum " + enumClass.getName() + ", not supported as " + (fromClass ? "class" : "property") + " annotation");
    }
}
