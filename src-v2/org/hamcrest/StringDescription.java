/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import java.io.IOException;
import org.hamcrest.BaseDescription;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class StringDescription
extends BaseDescription {
    private final Appendable out;

    public StringDescription() {
        this(new StringBuilder());
    }

    public StringDescription(Appendable appendable) {
        this.out = appendable;
    }

    public static String asString(SelfDescribing selfDescribing) {
        return StringDescription.toString(selfDescribing);
    }

    public static String toString(SelfDescribing selfDescribing) {
        return new StringDescription().appendDescriptionOf(selfDescribing).toString();
    }

    @Override
    protected void append(char c2) {
        try {
            this.out.append(c2);
            return;
        }
        catch (IOException var2_2) {
            throw new RuntimeException("Could not write description", var2_2);
        }
    }

    @Override
    protected void append(String string2) {
        try {
            this.out.append(string2);
            return;
        }
        catch (IOException var1_2) {
            throw new RuntimeException("Could not write description", var1_2);
        }
    }

    public String toString() {
        return this.out.toString();
    }
}

