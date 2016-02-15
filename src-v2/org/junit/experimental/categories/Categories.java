/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.categories;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.junit.experimental.categories.Category;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class Categories
extends Suite {
    public Categories(Class<?> class_, RunnerBuilder runnerBuilder) throws InitializationError {
        super(class_, runnerBuilder);
        try {
            this.filter(new CategoryFilter(this.getIncludedCategory(class_), this.getExcludedCategory(class_)));
            this.assertNoCategorizedDescendentsOfUncategorizeableParents(this.getDescription());
            return;
        }
        catch (NoTestsRemainException var1_2) {
            throw new InitializationError(var1_2);
        }
    }

    private void assertNoCategorizedDescendentsOfUncategorizeableParents(Description object) throws InitializationError {
        if (!Categories.canHaveCategorizedChildren((Description)object)) {
            this.assertNoDescendantsHaveCategoryAnnotations((Description)object);
        }
        object = object.getChildren().iterator();
        while (object.hasNext()) {
            this.assertNoCategorizedDescendentsOfUncategorizeableParents((Description)object.next());
        }
    }

    private void assertNoDescendantsHaveCategoryAnnotations(Description object) throws InitializationError {
        for (Description description : object.getChildren()) {
            if (description.getAnnotation(Category.class) != null) {
                throw new InitializationError("Category annotations on Parameterized classes are not supported on individual methods.");
            }
            this.assertNoDescendantsHaveCategoryAnnotations(description);
        }
    }

    private static boolean canHaveCategorizedChildren(Description object) {
        object = object.getChildren().iterator();
        while (object.hasNext()) {
            if (((Description)object.next()).getTestClass() != null) continue;
            return false;
        }
        return true;
    }

    private Class<?> getExcludedCategory(Class<?> object) {
        if ((object = (ExcludeCategory)object.getAnnotation(ExcludeCategory.class)) == null) {
            return null;
        }
        return object.value();
    }

    private Class<?> getIncludedCategory(Class<?> object) {
        if ((object = (IncludeCategory)object.getAnnotation(IncludeCategory.class)) == null) {
            return null;
        }
        return object.value();
    }

    public static class CategoryFilter
    extends Filter {
        private final Class<?> fExcluded;
        private final Class<?> fIncluded;

        public CategoryFilter(Class<?> class_, Class<?> class_2) {
            this.fIncluded = class_;
            this.fExcluded = class_2;
        }

        private List<Class<?>> categories(Description description) {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(Arrays.asList(this.directCategories(description)));
            arrayList.addAll(Arrays.asList(this.directCategories(this.parentDescription(description))));
            return arrayList;
        }

        private Class<?>[] directCategories(Description object) {
            if (object == null) {
                return new Class[0];
            }
            if ((object = (Category)object.getAnnotation(Category.class)) == null) {
                return new Class[0];
            }
            return object.value();
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean hasCorrectCategoryAnnotation(Description iterator) {
            if ((iterator = this.categories((Description)((Object)iterator))).isEmpty()) {
                if (this.fIncluded == null) return true;
                return false;
            }
            Object object = iterator.iterator();
            while (object.hasNext()) {
                Class class_ = (Class)object.next();
                if (this.fExcluded == null || !this.fExcluded.isAssignableFrom(class_)) continue;
                return false;
            }
            iterator = iterator.iterator();
            do {
                if (!iterator.hasNext()) {
                    return false;
                }
                object = (Class)iterator.next();
                if (this.fIncluded != null) continue;
                return true;
            } while (!this.fIncluded.isAssignableFrom(object));
            return true;
        }

        public static CategoryFilter include(Class<?> class_) {
            return new CategoryFilter(class_, null);
        }

        private Description parentDescription(Description serializable) {
            if ((serializable = serializable.getTestClass()) == null) {
                return null;
            }
            return Description.createSuiteDescription(serializable);
        }

        @Override
        public String describe() {
            return "category " + this.fIncluded;
        }

        @Override
        public boolean shouldRun(Description object) {
            if (this.hasCorrectCategoryAnnotation((Description)object)) {
                return true;
            }
            object = object.getChildren().iterator();
            while (object.hasNext()) {
                if (!this.shouldRun((Description)object.next())) continue;
                return true;
            }
            return false;
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface ExcludeCategory {
        public Class<?> value();
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface IncludeCategory {
        public Class<?> value();
    }

}

