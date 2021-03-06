package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.util.VersionUtil;

public final class PackageVersion implements Versioned {
    public static final Version VERSION;

    static {
        VERSION = VersionUtil.parseVersion("2.4.1", "com.fasterxml.jackson.core", "jackson-databind");
    }

    public Version version() {
        return VERSION;
    }
}
