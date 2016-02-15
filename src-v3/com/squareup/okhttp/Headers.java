package com.squareup.okhttp;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.squareup.okhttp.internal.http.HttpDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public final class Headers {
    private final String[] namesAndValues;

    public static class Builder {
        private final List<String> namesAndValues;

        public Builder() {
            this.namesAndValues = new ArrayList(20);
        }

        Builder addLine(String line) {
            int index = line.indexOf(":", 1);
            if (index != -1) {
                return addLenient(line.substring(0, index), line.substring(index + 1));
            }
            if (line.startsWith(":")) {
                return addLenient(UnsupportedUrlFragment.DISPLAY_NAME, line.substring(1));
            }
            return addLenient(UnsupportedUrlFragment.DISPLAY_NAME, line);
        }

        public Builder add(String name, String value) {
            if (name == null) {
                throw new IllegalArgumentException("name == null");
            } else if (value == null) {
                throw new IllegalArgumentException("value == null");
            } else if (name.length() != 0 && name.indexOf(0) == -1 && value.indexOf(0) == -1) {
                return addLenient(name, value);
            } else {
                throw new IllegalArgumentException("Unexpected header: " + name + ": " + value);
            }
        }

        private Builder addLenient(String name, String value) {
            this.namesAndValues.add(name);
            this.namesAndValues.add(value.trim());
            return this;
        }

        public Builder removeAll(String name) {
            int i = 0;
            while (i < this.namesAndValues.size()) {
                if (name.equalsIgnoreCase((String) this.namesAndValues.get(i))) {
                    this.namesAndValues.remove(i);
                    this.namesAndValues.remove(i);
                    i -= 2;
                }
                i += 2;
            }
            return this;
        }

        public Builder set(String name, String value) {
            removeAll(name);
            add(name, value);
            return this;
        }

        public String get(String name) {
            for (int i = this.namesAndValues.size() - 2; i >= 0; i -= 2) {
                if (name.equalsIgnoreCase((String) this.namesAndValues.get(i))) {
                    return (String) this.namesAndValues.get(i + 1);
                }
            }
            return null;
        }

        public Headers build() {
            return new Headers();
        }
    }

    private Headers(Builder builder) {
        this.namesAndValues = (String[]) builder.namesAndValues.toArray(new String[builder.namesAndValues.size()]);
    }

    private Headers(String[] namesAndValues) {
        this.namesAndValues = namesAndValues;
    }

    public String get(String name) {
        return get(this.namesAndValues, name);
    }

    public Date getDate(String name) {
        String value = get(name);
        return value != null ? HttpDate.parse(value) : null;
    }

    public int size() {
        return this.namesAndValues.length / 2;
    }

    public String name(int index) {
        int nameIndex = index * 2;
        if (nameIndex < 0 || nameIndex >= this.namesAndValues.length) {
            return null;
        }
        return this.namesAndValues[nameIndex];
    }

    public String value(int index) {
        int valueIndex = (index * 2) + 1;
        if (valueIndex < 0 || valueIndex >= this.namesAndValues.length) {
            return null;
        }
        return this.namesAndValues[valueIndex];
    }

    public Set<String> names() {
        TreeSet<String> result = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < size(); i++) {
            result.add(name(i));
        }
        return Collections.unmodifiableSet(result);
    }

    public List<String> values(String name) {
        List<String> result = null;
        for (int i = 0; i < size(); i++) {
            if (name.equalsIgnoreCase(name(i))) {
                if (result == null) {
                    result = new ArrayList(2);
                }
                result.add(value(i));
            }
        }
        return result != null ? Collections.unmodifiableList(result) : Collections.emptyList();
    }

    public Builder newBuilder() {
        Builder result = new Builder();
        result.namesAndValues.addAll(Arrays.asList(this.namesAndValues));
        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            result.append(name(i)).append(": ").append(value(i)).append("\n");
        }
        return result.toString();
    }

    private static String get(String[] namesAndValues, String name) {
        for (int i = namesAndValues.length - 2; i >= 0; i -= 2) {
            if (name.equalsIgnoreCase(namesAndValues[i])) {
                return namesAndValues[i + 1];
            }
        }
        return null;
    }

    public static Headers of(String... namesAndValues) {
        if (namesAndValues == null || namesAndValues.length % 2 != 0) {
            throw new IllegalArgumentException("Expected alternating header names and values");
        }
        int i;
        namesAndValues = (String[]) namesAndValues.clone();
        for (i = 0; i < namesAndValues.length; i++) {
            if (namesAndValues[i] == null) {
                throw new IllegalArgumentException("Headers cannot be null");
            }
            namesAndValues[i] = namesAndValues[i].trim();
        }
        i = 0;
        while (i < namesAndValues.length) {
            String name = namesAndValues[i];
            String value = namesAndValues[i + 1];
            if (name.length() != 0 && name.indexOf(0) == -1 && value.indexOf(0) == -1) {
                i += 2;
            } else {
                throw new IllegalArgumentException("Unexpected header: " + name + ": " + value);
            }
        }
        return new Headers(namesAndValues);
    }
}
