/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.language.Caverphone2;

@Deprecated
public class Caverphone
implements StringEncoder {
    private final Caverphone2 encoder = new Caverphone2();

    public String caverphone(String string2) {
        return this.encoder.encode(string2);
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("Parameter supplied to Caverphone encode is not of type java.lang.String");
        }
        return this.caverphone((String)object);
    }

    @Override
    public String encode(String string2) {
        return this.caverphone(string2);
    }

    public boolean isCaverphoneEqual(String string2, String string3) {
        return this.caverphone(string2).equals(this.caverphone(string3));
    }
}

