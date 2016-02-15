/*
 * Decompiled with CFR 0_110.
 */
package uk.co.senab.photoview.log;

import uk.co.senab.photoview.log.Logger;
import uk.co.senab.photoview.log.LoggerDefault;

public final class LogManager {
    private static Logger logger = new LoggerDefault();

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        LogManager.logger = logger;
    }
}

