/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public abstract class PropertyBindingException
extends JsonMappingException {
    private static final int MAX_DESC_LENGTH = 200;
    protected transient String _propertiesAsString;
    protected final Collection<Object> _propertyIds;
    protected final String _propertyName;
    protected final Class<?> _referringClass;

    protected PropertyBindingException(String string2, JsonLocation jsonLocation, Class<?> class_, String string3, Collection<Object> collection) {
        super(string2, jsonLocation);
        this._referringClass = class_;
        this._propertyName = string3;
        this._propertyIds = collection;
    }

    public Collection<Object> getKnownPropertyIds() {
        if (this._propertyIds == null) {
            return null;
        }
        return Collections.unmodifiableCollection(this._propertyIds);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public String getMessageSuffix() {
        var2_2 = var3_1 = this._propertiesAsString;
        if (var3_1 != null) return var2_2;
        var2_2 = var3_1;
        if (this._propertyIds == null) return var2_2;
        var2_2 = new StringBuilder(100);
        var1_3 = this._propertyIds.size();
        if (var1_3 != 1) ** GOTO lbl12
        var2_2.append(" (one known property: \"");
        var2_2.append(String.valueOf(this._propertyIds.iterator().next()));
        var2_2.append('\"');
        ** GOTO lbl-1000
lbl12: // 1 sources:
        var2_2.append(" (").append(var1_3).append(" known properties: ");
        var3_1 = this._propertyIds.iterator();
        do {
            if (!var3_1.hasNext()) lbl-1000: // 3 sources:
            {
                do {
                    var2_2.append("])");
                    this._propertiesAsString = var2_2 = var2_2.toString();
                    return var2_2;
                    break;
                } while (true);
            }
            var2_2.append('\"');
            var2_2.append(String.valueOf(var3_1.next()));
            var2_2.append('\"');
            if (var2_2.length() > 200) {
                var2_2.append(" [truncated]");
                ** continue;
            }
            if (!var3_1.hasNext()) continue;
            var2_2.append(", ");
        } while (true);
    }

    public String getPropertyName() {
        return this._propertyName;
    }

    public Class<?> getReferringClass() {
        return this._referringClass;
    }
}

