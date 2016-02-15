package com.crumby.lib.router;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface FileOpener {
    InputStream open(String str) throws FileNotFoundException;
}
