/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Iterator;
import java.util.Map;

abstract class NodeCursor
extends JsonStreamContext {
    protected String _currentName;
    protected final NodeCursor _parent;

    public NodeCursor(int n2, NodeCursor nodeCursor) {
        this._type = n2;
        this._index = -1;
        this._parent = nodeCursor;
    }

    public abstract boolean currentHasChildren();

    public abstract JsonNode currentNode();

    public abstract JsonToken endToken();

    @Override
    public final String getCurrentName() {
        return this._currentName;
    }

    @Override
    public final NodeCursor getParent() {
        return this._parent;
    }

    public final NodeCursor iterateChildren() {
        JsonNode jsonNode = this.currentNode();
        if (jsonNode == null) {
            throw new IllegalStateException("No current node");
        }
        if (jsonNode.isArray()) {
            return new Array(jsonNode, this);
        }
        if (jsonNode.isObject()) {
            return new Object(jsonNode, this);
        }
        throw new IllegalStateException("Current node of type " + jsonNode.getClass().getName());
    }

    public abstract JsonToken nextToken();

    public abstract JsonToken nextValue();

    public void overrideCurrentName(String string2) {
        this._currentName = string2;
    }

    protected static final class Array
    extends NodeCursor {
        protected Iterator<JsonNode> _contents;
        protected JsonNode _currentNode;

        public Array(JsonNode jsonNode, NodeCursor nodeCursor) {
            super(1, nodeCursor);
            this._contents = jsonNode.elements();
        }

        @Override
        public boolean currentHasChildren() {
            if (((ContainerNode)this.currentNode()).size() > 0) {
                return true;
            }
            return false;
        }

        @Override
        public JsonNode currentNode() {
            return this._currentNode;
        }

        @Override
        public JsonToken endToken() {
            return JsonToken.END_ARRAY;
        }

        @Override
        public JsonToken nextToken() {
            if (!this._contents.hasNext()) {
                this._currentNode = null;
                return null;
            }
            this._currentNode = this._contents.next();
            return this._currentNode.asToken();
        }

        @Override
        public JsonToken nextValue() {
            return this.nextToken();
        }
    }

    protected static final class Object
    extends NodeCursor {
        protected Iterator<Map.Entry<String, JsonNode>> _contents;
        protected Map.Entry<String, JsonNode> _current;
        protected boolean _needEntry;

        public Object(JsonNode jsonNode, NodeCursor nodeCursor) {
            super(2, nodeCursor);
            this._contents = ((ObjectNode)jsonNode).fields();
            this._needEntry = true;
        }

        @Override
        public boolean currentHasChildren() {
            if (((ContainerNode)this.currentNode()).size() > 0) {
                return true;
            }
            return false;
        }

        @Override
        public JsonNode currentNode() {
            if (this._current == null) {
                return null;
            }
            return this._current.getValue();
        }

        @Override
        public JsonToken endToken() {
            return JsonToken.END_OBJECT;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public JsonToken nextToken() {
            if (!this._needEntry) {
                this._needEntry = true;
                return this._current.getValue().asToken();
            }
            if (!this._contents.hasNext()) {
                this._currentName = null;
                this._current = null;
                return null;
            }
            this._needEntry = false;
            this._current = this._contents.next();
            String string2 = this._current == null ? null : this._current.getKey();
            this._currentName = string2;
            return JsonToken.FIELD_NAME;
        }

        @Override
        public JsonToken nextValue() {
            JsonToken jsonToken;
            JsonToken jsonToken2 = jsonToken = this.nextToken();
            if (jsonToken == JsonToken.FIELD_NAME) {
                jsonToken2 = this.nextToken();
            }
            return jsonToken2;
        }
    }

    protected static final class RootValue
    extends NodeCursor {
        protected boolean _done = false;
        protected JsonNode _node;

        public RootValue(JsonNode jsonNode, NodeCursor nodeCursor) {
            super(0, nodeCursor);
            this._node = jsonNode;
        }

        @Override
        public boolean currentHasChildren() {
            return false;
        }

        @Override
        public JsonNode currentNode() {
            return this._node;
        }

        @Override
        public JsonToken endToken() {
            return null;
        }

        @Override
        public JsonToken nextToken() {
            if (!this._done) {
                this._done = true;
                return this._node.asToken();
            }
            this._node = null;
            return null;
        }

        @Override
        public JsonToken nextValue() {
            return this.nextToken();
        }

        @Override
        public void overrideCurrentName(String string2) {
        }
    }

}

