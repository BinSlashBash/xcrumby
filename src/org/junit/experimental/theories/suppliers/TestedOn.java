package org.junit.experimental.theories.suppliers;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.junit.experimental.theories.ParametersSuppliedBy;

@Retention(RetentionPolicy.RUNTIME)
@ParametersSuppliedBy(TestedOnSupplier.class)
public @interface TestedOn
{
  int[] ints();
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/theories/suppliers/TestedOn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */