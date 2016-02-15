/*
 * Decompiled with CFR 0_110.
 */
package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.HttpDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public final class Headers {
    private final String[] namesAndValues;

    private Headers(Builder builder) {
        this.namesAndValues = builder.namesAndValues.toArray(new String[builder.namesAndValues.size()]);
    }

    private Headers(String[] arrstring) {
        this.namesAndValues = arrstring;
    }

    private static String get(String[] arrstring, String string2) {
        for (int i2 = arrstring.length - 2; i2 >= 0; i2 -= 2) {
            if (!string2.equalsIgnoreCase(arrstring[i2])) continue;
            return arrstring[i2 + 1];
        }
        return null;
    }

    public static /* varargs */ Headers of(String ... arrstring) {
        int n2;
        if (arrstring == null || arrstring.length % 2 != 0) {
            throw new IllegalArgumentException("Expected alternating header names and values");
        }
        arrstring = (String[])arrstring.clone();
        for (n2 = 0; n2 < arrstring.length; ++n2) {
            if (arrstring[n2] == null) {
                throw new IllegalArgumentException("Headers cannot be null");
            }
            arrstring[n2] = arrstring[n2].trim();
        }
        for (n2 = 0; n2 < arrstring.length; n2 += 2) {
            String string2 = arrstring[n2];
            String string3 = arrstring[n2 + 1];
            if (string2.length() != 0 && string2.indexOf(0) == -1 && string3.indexOf(0) == -1) continue;
            throw new IllegalArgumentException("Unexpected header: " + string2 + ": " + string3);
        }
        return new Headers(arrstring);
    }

    public String get(String string2) {
        return Headers.get(this.namesAndValues, string2);
    }

    public Date getDate(String string2) {
        if ((string2 = this.get(string2)) != null) {
            return HttpDate.parse(string2);
        }
        return null;
    }

    public String name(int n2) {
        if ((n2 *= 2) < 0 || n2 >= this.namesAndValues.length) {
            return null;
        }
        return this.namesAndValues[n2];
    }

    public Set<String> names() {
        TreeSet<String> treeSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (int i2 = 0; i2 < this.size(); ++i2) {
            treeSet.add(this.name(i2));
        }
        return Collections.unmodifiableSet(treeSet);
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.namesAndValues.addAll(Arrays.asList(this.namesAndValues));
        return builder;
    }

    public int size() {
        return this.namesAndValues.length / 2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i2 = 0; i2 < this.size(); ++i2) {
            stringBuilder.append(this.name(i2)).append(": ").append(this.value(i2)).append("\n");
        }
        return stringBuilder.toString();
    }

    public String value(int n2) {
        if ((n2 = n2 * 2 + 1) < 0 || n2 >= this.namesAndValues.length) {
            return null;
        }
        return this.namesAndValues[n2];
    }

    public List<String> values(String string2) {
        ArrayList<String> arrayList = null;
        for (int i2 = 0; i2 < this.size(); ++i2) {
            ArrayList<String> arrayList2 = arrayList;
            if (string2.equalsIgnoreCase(this.name(i2))) {
                arrayList2 = arrayList;
                if (arrayList == null) {
                    arrayList2 = new ArrayList<String>(2);
                }
                arrayList2.add(this.value(i2));
            }
            arrayList = arrayList2;
        }
        if (arrayList != null) {
            return Collections.unmodifiableList(arrayList);
        }
        return Collections.emptyList();
    }

    public static class Builder {
        private final List<String> namesAndValues = new ArrayList<String>(20);

        private Builder addLenient(String string2, String string3) {
            this.namesAndValues.add(string2);
            this.namesAndValues.add(string3.trim());
            return this;
        }

        public Builder add(String string2, String string3) {
            if (string2 == null) {
                throw new IllegalArgumentException("name == null");
            }
            if (string3 == null) {
                throw new IllegalArgumentException("value == null");
            }
            if (string2.length() == 0 || string2.indexOf(0) != -1 || string3.indexOf(0) != -1) {
                throw new IllegalArgumentException("Unexpected header: " + string2 + ": " + string3);
            }
            return this.addLenient(string2, string3);
        }

        Builder addLine(String string2) {
            int n2 = string2.indexOf(":", 1);
            if (n2 != -1) {
                return this.addLenient(string2.substring(0, n2), string2.substring(n2 + 1));
            }
            if (string2.startsWith(":")) {
                return this.addLenient("", string2.substring(1));
            }
            return this.addLenient("", string2);
        }

        public Headers build() {
            return new Headers(this);
        }

        public String get(String string2) {
            for (int i2 = this.namesAndValues.size() - 2; i2 >= 0; i2 -= 2) {
                if (!string2.equalsIgnoreCase(this.namesAndValues.get(i2))) continue;
                return this.namesAndValues.get(i2 + 1);
            }
            return null;
        }

        public Builder removeAll(String string2) {
            int n2 = 0;
            while (n2 < this.namesAndValues.size()) {
                int n3 = n2;
                if (string2.equalsIgnoreCase(this.namesAndValues.get(n2))) {
                    this.namesAndValues.remove(n2);
                    this.namesAndValues.remove(n2);
                    n3 = n2 - 2;
                }
                n2 = n3 + 2;
            }
            return this;
        }

        public Builder set(String string2, String string3) {
            this.removeAll(string2);
            this.add(string2, string3);
            return this;
        }
    }

}

