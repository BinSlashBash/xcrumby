package org.junit.internal;

import java.io.PrintStream;

public class RealSystem
  implements JUnitSystem
{
  public PrintStream out()
  {
    return System.out;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/RealSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */