package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.util.VersionUtil;

public final class PackageVersion
  implements Versioned
{
  public static final Version VERSION = VersionUtil.parseVersion("2.4.1", "com.fasterxml.jackson.core", "jackson-databind");
  
  public Version version()
  {
    return VERSION;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/cfg/PackageVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */