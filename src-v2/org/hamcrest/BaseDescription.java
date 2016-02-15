/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import java.util.Arrays;
import java.util.Iterator;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.internal.ArrayIterator;
import org.hamcrest.internal.SelfDescribingValueIterator;

public abstract class BaseDescription
implements Description {
    private Description appendList(String string2, String string3, String string4, Iterator<? extends SelfDescribing> iterator) {
        boolean bl2 = false;
        this.append(string2);
        while (iterator.hasNext()) {
            if (bl2) {
                this.append(string3);
            }
            this.appendDescriptionOf(iterator.next());
            bl2 = true;
        }
        this.append(string4);
        return this;
    }

    private <T> Description appendValueList(String string2, String string3, String string4, Iterator<T> iterator) {
        return this.appendList(string2, string3, string4, new SelfDescribingValueIterator<T>(iterator));
    }

    private String descriptionOf(Object object) {
        try {
            String string2 = String.valueOf(object);
            return string2;
        }
        catch (Exception var2_3) {
            return object.getClass().getName() + "@" + Integer.toHexString(object.hashCode());
        }
    }

    private void toJavaSyntax(char c2) {
        switch (c2) {
            default: {
                this.append(c2);
                return;
            }
            case '\"': {
                this.append("\\\"");
                return;
            }
            case '\n': {
                this.append("\\n");
                return;
            }
            case '\r': {
                this.append("\\r");
                return;
            }
            case '\t': 
        }
        this.append("\\t");
    }

    private void toJavaSyntax(String string2) {
        this.append('\"');
        for (int i2 = 0; i2 < string2.length(); ++i2) {
            this.toJavaSyntax(string2.charAt(i2));
        }
        this.append('\"');
    }

    protected abstract void append(char var1);

    protected void append(String string2) {
        for (int i2 = 0; i2 < string2.length(); ++i2) {
            this.append(string2.charAt(i2));
        }
    }

    @Override
    public Description appendDescriptionOf(SelfDescribing selfDescribing) {
        selfDescribing.describeTo(this);
        return this;
    }

    @Override
    public Description appendList(String string2, String string3, String string4, Iterable<? extends SelfDescribing> iterable) {
        return this.appendList(string2, string3, string4, iterable.iterator());
    }

    @Override
    public Description appendText(String string2) {
        this.append(string2);
        return this;
    }

    @Override
    public Description appendValue(Object object) {
        if (object == null) {
            this.append("null");
            return this;
        }
        if (object instanceof String) {
            this.toJavaSyntax((String)object);
            return this;
        }
        if (object instanceof Character) {
            this.append('\"');
            this.toJavaSyntax(((Character)object).charValue());
            this.append('\"');
            return this;
        }
        if (object instanceof Short) {
            this.append('<');
            this.append(this.descriptionOf(object));
            this.append("s>");
            return this;
        }
        if (object instanceof Long) {
            this.append('<');
            this.append(this.descriptionOf(object));
            this.append("L>");
            return this;
        }
        if (object instanceof Float) {
            this.append('<');
            this.append(this.descriptionOf(object));
            this.append("F>");
            return this;
        }
        if (object.getClass().isArray()) {
            this.appendValueList("[", ", ", "]", new ArrayIterator(object));
            return this;
        }
        this.append('<');
        this.append(this.descriptionOf(object));
        this.append('>');
        return this;
    }

    @Override
    public <T> Description appendValueList(String string2, String string3, String string4, Iterable<T> iterable) {
        return this.appendValueList(string2, string3, string4, iterable.iterator());
    }

    @Override
    public /* varargs */ <T> Description appendValueList(String string2, String string3, String string4, T ... arrT) {
        return this.appendValueList(string2, string3, string4, (Iterable<T>)Arrays.asList(arrT));
    }
}

