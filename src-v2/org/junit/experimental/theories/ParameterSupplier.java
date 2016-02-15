/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.theories;

import java.util.List;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.PotentialAssignment;

public abstract class ParameterSupplier {
    public abstract List<PotentialAssignment> getValueSources(ParameterSignature var1);
}

