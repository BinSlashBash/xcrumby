package org.junit.experimental.theories;

import java.util.List;

public abstract class ParameterSupplier
{
  public abstract List<PotentialAssignment> getValueSources(ParameterSignature paramParameterSignature);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/theories/ParameterSupplier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */