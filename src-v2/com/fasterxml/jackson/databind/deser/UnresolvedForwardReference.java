/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.UnresolvedId;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class UnresolvedForwardReference
extends JsonMappingException {
    private static final long serialVersionUID = 1;
    private ReadableObjectId _roid;
    private List<UnresolvedId> _unresolvedIds;

    public UnresolvedForwardReference(String string2) {
        super(string2);
        this._unresolvedIds = new ArrayList<UnresolvedId>();
    }

    public UnresolvedForwardReference(String string2, JsonLocation jsonLocation, ReadableObjectId readableObjectId) {
        super(string2, jsonLocation);
        this._roid = readableObjectId;
    }

    public void addUnresolvedId(Object object, Class<?> class_, JsonLocation jsonLocation) {
        this._unresolvedIds.add(new UnresolvedId(object, class_, jsonLocation));
    }

    @Override
    public String getMessage() {
        CharSequence charSequence = super.getMessage();
        if (this._unresolvedIds == null) {
            return charSequence;
        }
        charSequence = new StringBuilder((String)charSequence);
        Iterator<UnresolvedId> iterator = this._unresolvedIds.iterator();
        while (iterator.hasNext()) {
            charSequence.append(iterator.next().toString());
            if (!iterator.hasNext()) continue;
            charSequence.append(", ");
        }
        charSequence.append('.');
        return charSequence.toString();
    }

    public ReadableObjectId getRoid() {
        return this._roid;
    }

    public Object getUnresolvedId() {
        return this._roid.getKey().key;
    }

    public List<UnresolvedId> getUnresolvedIds() {
        return this._unresolvedIds;
    }
}

