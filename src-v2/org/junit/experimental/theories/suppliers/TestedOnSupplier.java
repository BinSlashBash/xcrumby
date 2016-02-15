/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.theories.suppliers;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.suppliers.TestedOn;

public class TestedOnSupplier
extends ParameterSupplier {
    @Override
    public List<PotentialAssignment> getValueSources(ParameterSignature parameterSignature) {
        ArrayList<PotentialAssignment> arrayList = new ArrayList<PotentialAssignment>();
        parameterSignature = (ParameterSignature)((TestedOn)parameterSignature.getAnnotation(TestedOn.class)).ints();
        int n2 = parameterSignature.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(PotentialAssignment.forValue("ints", (int)parameterSignature[i2]));
        }
        return arrayList;
    }
}

