/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JsonMappingException
extends JsonProcessingException {
    static final int MAX_REFS_TO_LIST = 1000;
    private static final long serialVersionUID = 1;
    protected LinkedList<Reference> _path;

    public JsonMappingException(String string2) {
        super(string2);
    }

    public JsonMappingException(String string2, JsonLocation jsonLocation) {
        super(string2, jsonLocation);
    }

    public JsonMappingException(String string2, JsonLocation jsonLocation, Throwable throwable) {
        super(string2, jsonLocation, throwable);
    }

    public JsonMappingException(String string2, Throwable throwable) {
        super(string2, throwable);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static JsonMappingException from(JsonParser object, String string2) {
        if (object == null) {
            object = null;
            do {
                return new JsonMappingException(string2, (JsonLocation)object);
                break;
            } while (true);
        }
        object = object.getTokenLocation();
        return new JsonMappingException(string2, (JsonLocation)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static JsonMappingException from(JsonParser object, String string2, Throwable throwable) {
        if (object == null) {
            object = null;
            do {
                return new JsonMappingException(string2, (JsonLocation)object, throwable);
                break;
            } while (true);
        }
        object = object.getTokenLocation();
        return new JsonMappingException(string2, (JsonLocation)object, throwable);
    }

    public static JsonMappingException fromUnexpectedIOE(IOException iOException) {
        return new JsonMappingException("Unexpected IOException (of type " + iOException.getClass().getName() + "): " + iOException.getMessage(), null, iOException);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static JsonMappingException wrapWithPath(Throwable throwable, Reference reference) {
        if (throwable instanceof JsonMappingException) {
            throwable = (JsonMappingException)throwable;
        } else {
            String string2;
            block4 : {
                String string3 = throwable.getMessage();
                if (string3 != null) {
                    string2 = string3;
                    if (string3.length() != 0) break block4;
                }
                string2 = "(was " + throwable.getClass().getName() + ")";
            }
            throwable = new JsonMappingException(string2, null, throwable);
        }
        throwable.prependPath(reference);
        return throwable;
    }

    public static JsonMappingException wrapWithPath(Throwable throwable, Object object, int n2) {
        return JsonMappingException.wrapWithPath(throwable, new Reference(object, n2));
    }

    public static JsonMappingException wrapWithPath(Throwable throwable, Object object, String string2) {
        return JsonMappingException.wrapWithPath(throwable, new Reference(object, string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _appendPathDesc(StringBuilder stringBuilder) {
        if (this._path != null) {
            Iterator<Reference> iterator = this._path.iterator();
            while (iterator.hasNext()) {
                stringBuilder.append(iterator.next().toString());
                if (!iterator.hasNext()) continue;
                stringBuilder.append("->");
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected String _buildMessage() {
        void var1_3;
        String string2 = super.getMessage();
        if (this._path == null) {
            return string2;
        }
        if (string2 == null) {
            StringBuilder stringBuilder = new StringBuilder();
        } else {
            StringBuilder stringBuilder = new StringBuilder(string2);
        }
        var1_3.append(" (through reference chain: ");
        StringBuilder stringBuilder = this.getPathReference((StringBuilder)var1_3);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    @Override
    public String getLocalizedMessage() {
        return this._buildMessage();
    }

    @Override
    public String getMessage() {
        return this._buildMessage();
    }

    public List<Reference> getPath() {
        if (this._path == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this._path);
    }

    public String getPathReference() {
        return this.getPathReference(new StringBuilder()).toString();
    }

    public StringBuilder getPathReference(StringBuilder stringBuilder) {
        this._appendPathDesc(stringBuilder);
        return stringBuilder;
    }

    public void prependPath(Reference reference) {
        if (this._path == null) {
            this._path = new LinkedList();
        }
        if (this._path.size() < 1000) {
            this._path.addFirst(reference);
        }
    }

    public void prependPath(Object object, int n2) {
        this.prependPath(new Reference(object, n2));
    }

    public void prependPath(Object object, String string2) {
        this.prependPath(new Reference(object, string2));
    }

    @Override
    public String toString() {
        return this.getClass().getName() + ": " + this.getMessage();
    }

    public static class Reference
    implements Serializable {
        private static final long serialVersionUID = 1;
        protected String _fieldName;
        protected Object _from;
        protected int _index = -1;

        protected Reference() {
        }

        public Reference(Object object) {
            this._from = object;
        }

        public Reference(Object object, int n2) {
            this._from = object;
            this._index = n2;
        }

        public Reference(Object object, String string2) {
            this._from = object;
            if (string2 == null) {
                throw new NullPointerException("Can not pass null fieldName");
            }
            this._fieldName = string2;
        }

        public String getFieldName() {
            return this._fieldName;
        }

        public Object getFrom() {
            return this._from;
        }

        public int getIndex() {
            return this._index;
        }

        public void setFieldName(String string2) {
            this._fieldName = string2;
        }

        public void setFrom(Object object) {
            this._from = object;
        }

        public void setIndex(int n2) {
            this._index = n2;
        }

        /*
         * Enabled aggressive block sorting
         */
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            Class class_ = this._from instanceof Class ? (Class)this._from : this._from.getClass();
            Package package_ = class_.getPackage();
            if (package_ != null) {
                stringBuilder.append(package_.getName());
                stringBuilder.append('.');
            }
            stringBuilder.append(class_.getSimpleName());
            stringBuilder.append('[');
            if (this._fieldName != null) {
                stringBuilder.append('\"');
                stringBuilder.append(this._fieldName);
                stringBuilder.append('\"');
            } else if (this._index >= 0) {
                stringBuilder.append(this._index);
            } else {
                stringBuilder.append('?');
            }
            stringBuilder.append(']');
            return stringBuilder.toString();
        }
    }

}

