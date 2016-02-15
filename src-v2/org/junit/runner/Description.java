/*
 * Decompiled with CFR 0_110.
 */
package org.junit.runner;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Description
implements Serializable {
    public static final Description EMPTY;
    private static final Pattern METHOD_AND_CLASS_NAME_PATTERN;
    public static final Description TEST_MECHANISM;
    private static final long serialVersionUID = 1;
    private final Annotation[] fAnnotations;
    private final ArrayList<Description> fChildren = new ArrayList();
    private final String fDisplayName;
    private Class<?> fTestClass;
    private final Serializable fUniqueId;

    static {
        METHOD_AND_CLASS_NAME_PATTERN = Pattern.compile("(.*)\\((.*)\\)");
        EMPTY = new Description(null, "No Tests", new Annotation[0]);
        TEST_MECHANISM = new Description(null, "Test mechanism", new Annotation[0]);
    }

    private /* varargs */ Description(Class<?> class_, String string2, Serializable serializable, Annotation ... arrannotation) {
        if (string2 == null || string2.length() == 0) {
            throw new IllegalArgumentException("The display name must not be empty.");
        }
        if (serializable == null) {
            throw new IllegalArgumentException("The unique id must not be null.");
        }
        this.fTestClass = class_;
        this.fDisplayName = string2;
        this.fUniqueId = serializable;
        this.fAnnotations = arrannotation;
    }

    private /* varargs */ Description(Class<?> class_, String string2, Annotation ... arrannotation) {
        this(class_, string2, (Serializable)((Object)string2), arrannotation);
    }

    public static Description createSuiteDescription(Class<?> class_) {
        return new Description(class_, class_.getName(), class_.getAnnotations());
    }

    public static /* varargs */ Description createSuiteDescription(String string2, Serializable serializable, Annotation ... arrannotation) {
        return new Description(null, string2, serializable, arrannotation);
    }

    public static /* varargs */ Description createSuiteDescription(String string2, Annotation ... arrannotation) {
        return new Description(null, string2, arrannotation);
    }

    public static Description createTestDescription(Class<?> class_, String string2) {
        return new Description(class_, Description.formatDisplayName(string2, class_.getName()), new Annotation[0]);
    }

    public static /* varargs */ Description createTestDescription(Class<?> class_, String string2, Annotation ... arrannotation) {
        return new Description(class_, Description.formatDisplayName(string2, class_.getName()), arrannotation);
    }

    public static Description createTestDescription(String string2, String string3, Serializable serializable) {
        return new Description(null, Description.formatDisplayName(string3, string2), serializable, new Annotation[0]);
    }

    public static /* varargs */ Description createTestDescription(String string2, String string3, Annotation ... arrannotation) {
        return new Description(null, Description.formatDisplayName(string3, string2), arrannotation);
    }

    private static String formatDisplayName(String string2, String string3) {
        return String.format("%s(%s)", string2, string3);
    }

    private String methodAndClassNamePatternGroupOrDefault(int n2, String string2) {
        Matcher matcher = METHOD_AND_CLASS_NAME_PATTERN.matcher(this.toString());
        if (matcher.matches()) {
            string2 = matcher.group(n2);
        }
        return string2;
    }

    public void addChild(Description description) {
        this.getChildren().add(description);
    }

    public Description childlessCopy() {
        return new Description(this.fTestClass, this.fDisplayName, this.fAnnotations);
    }

    public boolean equals(Object object) {
        if (!(object instanceof Description)) {
            return false;
        }
        object = (Description)object;
        return this.fUniqueId.equals(object.fUniqueId);
    }

    public <T extends Annotation> T getAnnotation(Class<T> class_) {
        for (Annotation annotation : this.fAnnotations) {
            if (!annotation.annotationType().equals(class_)) continue;
            return (T)((Annotation)class_.cast(annotation));
        }
        return null;
    }

    public Collection<Annotation> getAnnotations() {
        return Arrays.asList(this.fAnnotations);
    }

    public ArrayList<Description> getChildren() {
        return this.fChildren;
    }

    public String getClassName() {
        if (this.fTestClass != null) {
            return this.fTestClass.getName();
        }
        return this.methodAndClassNamePatternGroupOrDefault(2, this.toString());
    }

    public String getDisplayName() {
        return this.fDisplayName;
    }

    public String getMethodName() {
        return this.methodAndClassNamePatternGroupOrDefault(1, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Class<?> getTestClass() {
        Class class_ = null;
        if (this.fTestClass != null) {
            return this.fTestClass;
        }
        String string2 = this.getClassName();
        if (string2 == null) return class_;
        try {
            this.fTestClass = Class.forName(string2, false, this.getClass().getClassLoader());
            return this.fTestClass;
        }
        catch (ClassNotFoundException var1_2) {
            return null;
        }
    }

    public int hashCode() {
        return this.fUniqueId.hashCode();
    }

    public boolean isEmpty() {
        return this.equals(EMPTY);
    }

    public boolean isSuite() {
        if (!this.isTest()) {
            return true;
        }
        return false;
    }

    public boolean isTest() {
        return this.getChildren().isEmpty();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int testCount() {
        if (this.isTest()) {
            return 1;
        }
        int n2 = 0;
        Iterator<Description> iterator = this.getChildren().iterator();
        do {
            int n3 = n2;
            if (!iterator.hasNext()) return n3;
            n2 += iterator.next().testCount();
        } while (true);
    }

    public String toString() {
        return this.getDisplayName();
    }
}

