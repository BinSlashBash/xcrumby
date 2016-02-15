/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.drive.query;

import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.SearchableOrderedMetadataField;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.drive.query.internal.ComparisonFilter;
import com.google.android.gms.drive.query.internal.FieldOnlyFilter;
import com.google.android.gms.drive.query.internal.InFilter;
import com.google.android.gms.drive.query.internal.LogicalFilter;
import com.google.android.gms.drive.query.internal.NotFilter;
import com.google.android.gms.drive.query.internal.Operator;
import java.util.Date;

public class Filters {
    public static /* varargs */ Filter and(Filter filter, Filter ... arrfilter) {
        return new LogicalFilter(Operator.GZ, filter, arrfilter);
    }

    public static Filter and(Iterable<Filter> iterable) {
        return new LogicalFilter(Operator.GZ, iterable);
    }

    public static Filter contains(SearchableMetadataField<String> searchableMetadataField, String string2) {
        return new ComparisonFilter<String>(Operator.Hc, searchableMetadataField, string2);
    }

    public static <T> Filter eq(SearchableMetadataField<T> searchableMetadataField, T t2) {
        return new ComparisonFilter<T>(Operator.GU, searchableMetadataField, t2);
    }

    public static <T extends Comparable<T>> Filter greaterThan(SearchableOrderedMetadataField<T> searchableOrderedMetadataField, T t2) {
        return new ComparisonFilter<T>(Operator.GX, searchableOrderedMetadataField, t2);
    }

    public static <T extends Comparable<T>> Filter greaterThanEquals(SearchableOrderedMetadataField<T> searchableOrderedMetadataField, T t2) {
        return new ComparisonFilter<T>(Operator.GY, searchableOrderedMetadataField, t2);
    }

    public static <T> Filter in(SearchableCollectionMetadataField<T> searchableCollectionMetadataField, T t2) {
        return new InFilter<T>(searchableCollectionMetadataField, t2);
    }

    public static <T extends Comparable<T>> Filter lessThan(SearchableOrderedMetadataField<T> searchableOrderedMetadataField, T t2) {
        return new ComparisonFilter<T>(Operator.GV, searchableOrderedMetadataField, t2);
    }

    public static <T extends Comparable<T>> Filter lessThanEquals(SearchableOrderedMetadataField<T> searchableOrderedMetadataField, T t2) {
        return new ComparisonFilter<T>(Operator.GW, searchableOrderedMetadataField, t2);
    }

    public static Filter not(Filter filter) {
        return new NotFilter(filter);
    }

    public static /* varargs */ Filter or(Filter filter, Filter ... arrfilter) {
        return new LogicalFilter(Operator.Ha, filter, arrfilter);
    }

    public static Filter or(Iterable<Filter> iterable) {
        return new LogicalFilter(Operator.Ha, iterable);
    }

    public static Filter sharedWithMe() {
        return new FieldOnlyFilter(SearchableField.GE);
    }
}

