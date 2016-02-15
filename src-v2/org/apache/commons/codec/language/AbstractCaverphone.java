/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public abstract class AbstractCaverphone
implements StringEncoder {
    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("Parameter supplied to Caverphone encode is not of type java.lang.String");
        }
        return this.encode((String)object);
    }

    public boolean isEncodeEqual(String string2, String string3) throws EncoderException {
        return this.encode(string2).equals(this.encode(string3));
    }
}

