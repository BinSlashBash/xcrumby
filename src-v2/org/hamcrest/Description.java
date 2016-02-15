/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import org.hamcrest.SelfDescribing;

public interface Description {
    public static final Description NONE = new NullDescription();

    public Description appendDescriptionOf(SelfDescribing var1);

    public Description appendList(String var1, String var2, String var3, Iterable<? extends SelfDescribing> var4);

    public Description appendText(String var1);

    public Description appendValue(Object var1);

    public <T> Description appendValueList(String var1, String var2, String var3, Iterable<T> var4);

    public /* varargs */ <T> Description appendValueList(String var1, String var2, String var3, T ... var4);

    public static final class NullDescription
    implements Description {
        @Override
        public Description appendDescriptionOf(SelfDescribing selfDescribing) {
            return this;
        }

        @Override
        public Description appendList(String string2, String string3, String string4, Iterable<? extends SelfDescribing> iterable) {
            return this;
        }

        @Override
        public Description appendText(String string2) {
            return this;
        }

        @Override
        public Description appendValue(Object object) {
            return this;
        }

        @Override
        public <T> Description appendValueList(String string2, String string3, String string4, Iterable<T> iterable) {
            return this;
        }

        @Override
        public /* varargs */ <T> Description appendValueList(String string2, String string3, String string4, T ... arrT) {
            return this;
        }

        public String toString() {
            return "";
        }
    }

}

