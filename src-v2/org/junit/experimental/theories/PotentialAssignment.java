/*
 * Decompiled with CFR 0_110.
 */
package org.junit.experimental.theories;

public abstract class PotentialAssignment {
    public static PotentialAssignment forValue(final String string2, final Object object) {
        return new PotentialAssignment(){

            @Override
            public String getDescription() throws CouldNotGenerateValueException {
                return string2;
            }

            @Override
            public Object getValue() throws CouldNotGenerateValueException {
                return object;
            }

            public String toString() {
                return String.format("[%s]", object);
            }
        };
    }

    public abstract String getDescription() throws CouldNotGenerateValueException;

    public abstract Object getValue() throws CouldNotGenerateValueException;

    public static class CouldNotGenerateValueException
    extends Exception {
        private static final long serialVersionUID = 1;
    }

}

