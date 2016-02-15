/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.util.EmptyIterator;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class JsonNode
implements TreeNode,
Iterable<JsonNode> {
    protected JsonNode() {
    }

    protected abstract JsonNode _at(JsonPointer var1);

    public boolean asBoolean() {
        return this.asBoolean(false);
    }

    public boolean asBoolean(boolean bl2) {
        return bl2;
    }

    public double asDouble() {
        return this.asDouble(0.0);
    }

    public double asDouble(double d2) {
        return d2;
    }

    public int asInt() {
        return this.asInt(0);
    }

    public int asInt(int n2) {
        return n2;
    }

    public long asLong() {
        return this.asLong(0);
    }

    public long asLong(long l2) {
        return l2;
    }

    public abstract String asText();

    public String asText(String string2) {
        String string3 = this.asText();
        if (string3 == null) {
            return string2;
        }
        return string3;
    }

    @Override
    public final JsonNode at(JsonPointer jsonPointer) {
        if (jsonPointer.matches()) {
            return this;
        }
        JsonNode jsonNode = this._at(jsonPointer);
        if (jsonNode == null) {
            return MissingNode.getInstance();
        }
        return jsonNode.at(jsonPointer.tail());
    }

    @Override
    public final JsonNode at(String string2) {
        return this.at(JsonPointer.compile(string2));
    }

    public BigInteger bigIntegerValue() {
        return BigInteger.ZERO;
    }

    public byte[] binaryValue() throws IOException {
        return null;
    }

    public boolean booleanValue() {
        return false;
    }

    public boolean canConvertToInt() {
        return false;
    }

    public boolean canConvertToLong() {
        return false;
    }

    public BigDecimal decimalValue() {
        return BigDecimal.ZERO;
    }

    public abstract <T extends JsonNode> T deepCopy();

    public double doubleValue() {
        return 0.0;
    }

    public Iterator<JsonNode> elements() {
        return EmptyIterator.instance();
    }

    public abstract boolean equals(Object var1);

    @Override
    public Iterator<String> fieldNames() {
        return EmptyIterator.instance();
    }

    public Iterator<Map.Entry<String, JsonNode>> fields() {
        return EmptyIterator.instance();
    }

    public abstract JsonNode findParent(String var1);

    public final List<JsonNode> findParents(String list) {
        List<JsonNode> list2;
        list = list2 = this.findParents((String)((Object)list), null);
        if (list2 == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    public abstract List<JsonNode> findParents(String var1, List<JsonNode> var2);

    public abstract JsonNode findPath(String var1);

    public abstract JsonNode findValue(String var1);

    public final List<JsonNode> findValues(String list) {
        List<JsonNode> list2;
        list = list2 = this.findValues((String)((Object)list), null);
        if (list2 == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    public abstract List<JsonNode> findValues(String var1, List<JsonNode> var2);

    public final List<String> findValuesAsText(String list) {
        List<String> list2;
        list = list2 = this.findValuesAsText((String)((Object)list), null);
        if (list2 == null) {
            list = Collections.emptyList();
        }
        return list;
    }

    public abstract List<String> findValuesAsText(String var1, List<String> var2);

    public float floatValue() {
        return 0.0f;
    }

    @Override
    public abstract JsonNode get(int var1);

    @Override
    public JsonNode get(String string2) {
        return null;
    }

    public abstract JsonNodeType getNodeType();

    public boolean has(int n2) {
        if (this.get(n2) != null) {
            return true;
        }
        return false;
    }

    public boolean has(String string2) {
        if (this.get(string2) != null) {
            return true;
        }
        return false;
    }

    public boolean hasNonNull(int n2) {
        TreeNode treeNode = this.get(n2);
        if (treeNode != null && !treeNode.isNull()) {
            return true;
        }
        return false;
    }

    public boolean hasNonNull(String object) {
        if ((object = this.get((String)object)) != null && !object.isNull()) {
            return true;
        }
        return false;
    }

    public int intValue() {
        return 0;
    }

    @Override
    public final boolean isArray() {
        if (this.getNodeType() == JsonNodeType.ARRAY) {
            return true;
        }
        return false;
    }

    public boolean isBigDecimal() {
        return false;
    }

    public boolean isBigInteger() {
        return false;
    }

    public final boolean isBinary() {
        if (this.getNodeType() == JsonNodeType.BINARY) {
            return true;
        }
        return false;
    }

    public final boolean isBoolean() {
        if (this.getNodeType() == JsonNodeType.BOOLEAN) {
            return true;
        }
        return false;
    }

    @Override
    public final boolean isContainerNode() {
        JsonNodeType jsonNodeType = this.getNodeType();
        if (jsonNodeType == JsonNodeType.OBJECT || jsonNodeType == JsonNodeType.ARRAY) {
            return true;
        }
        return false;
    }

    public boolean isDouble() {
        return false;
    }

    public boolean isFloat() {
        return false;
    }

    public boolean isFloatingPointNumber() {
        return false;
    }

    public boolean isInt() {
        return false;
    }

    public boolean isIntegralNumber() {
        return false;
    }

    public boolean isLong() {
        return false;
    }

    @Override
    public final boolean isMissingNode() {
        if (this.getNodeType() == JsonNodeType.MISSING) {
            return true;
        }
        return false;
    }

    public final boolean isNull() {
        if (this.getNodeType() == JsonNodeType.NULL) {
            return true;
        }
        return false;
    }

    public final boolean isNumber() {
        if (this.getNodeType() == JsonNodeType.NUMBER) {
            return true;
        }
        return false;
    }

    @Override
    public final boolean isObject() {
        if (this.getNodeType() == JsonNodeType.OBJECT) {
            return true;
        }
        return false;
    }

    public final boolean isPojo() {
        if (this.getNodeType() == JsonNodeType.POJO) {
            return true;
        }
        return false;
    }

    public boolean isShort() {
        return false;
    }

    public final boolean isTextual() {
        if (this.getNodeType() == JsonNodeType.STRING) {
            return true;
        }
        return false;
    }

    @Override
    public final boolean isValueNode() {
        switch (.$SwitchMap$com$fasterxml$jackson$databind$node$JsonNodeType[this.getNodeType().ordinal()]) {
            default: {
                return true;
            }
            case 1: 
            case 2: 
            case 3: 
        }
        return false;
    }

    @Override
    public final Iterator<JsonNode> iterator() {
        return this.elements();
    }

    public long longValue() {
        return 0;
    }

    public Number numberValue() {
        return null;
    }

    @Override
    public abstract JsonNode path(int var1);

    @Override
    public abstract JsonNode path(String var1);

    public short shortValue() {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    public String textValue() {
        return null;
    }

    public abstract String toString();

    public JsonNode with(String string2) {
        throw new UnsupportedOperationException("JsonNode not of type ObjectNode (but " + this.getClass().getName() + "), can not call with() on it");
    }

    public JsonNode withArray(String string2) {
        throw new UnsupportedOperationException("JsonNode not of type ObjectNode (but " + this.getClass().getName() + "), can not call withArray() on it");
    }

}

