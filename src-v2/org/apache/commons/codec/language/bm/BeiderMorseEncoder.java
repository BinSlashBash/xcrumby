/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language.bm;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.language.bm.NameType;
import org.apache.commons.codec.language.bm.PhoneticEngine;
import org.apache.commons.codec.language.bm.RuleType;

public class BeiderMorseEncoder
implements StringEncoder {
    private PhoneticEngine engine = new PhoneticEngine(NameType.GENERIC, RuleType.APPROX, true);

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("BeiderMorseEncoder encode parameter is not of type String");
        }
        return this.encode((String)object);
    }

    @Override
    public String encode(String string2) throws EncoderException {
        if (string2 == null) {
            return null;
        }
        return this.engine.encode(string2);
    }

    public NameType getNameType() {
        return this.engine.getNameType();
    }

    public RuleType getRuleType() {
        return this.engine.getRuleType();
    }

    public boolean isConcat() {
        return this.engine.isConcat();
    }

    public void setConcat(boolean bl2) {
        this.engine = new PhoneticEngine(this.engine.getNameType(), this.engine.getRuleType(), bl2, this.engine.getMaxPhonemes());
    }

    public void setMaxPhonemes(int n2) {
        this.engine = new PhoneticEngine(this.engine.getNameType(), this.engine.getRuleType(), this.engine.isConcat(), n2);
    }

    public void setNameType(NameType nameType) {
        this.engine = new PhoneticEngine(nameType, this.engine.getRuleType(), this.engine.isConcat(), this.engine.getMaxPhonemes());
    }

    public void setRuleType(RuleType ruleType) {
        this.engine = new PhoneticEngine(this.engine.getNameType(), ruleType, this.engine.isConcat(), this.engine.getMaxPhonemes());
    }
}

