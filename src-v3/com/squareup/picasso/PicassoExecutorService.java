package com.squareup.picasso;

import android.net.NetworkInfo;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.games.GamesStatusCodes;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.zip.JSONzip;

class PicassoExecutorService extends ThreadPoolExecutor {
    private static final int DEFAULT_THREAD_COUNT = 3;

    PicassoExecutorService() {
        super(DEFAULT_THREAD_COUNT, DEFAULT_THREAD_COUNT, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new PicassoThreadFactory());
    }

    void adjustThreadCount(NetworkInfo info) {
        if (info == null || !info.isConnectedOrConnecting()) {
            setThreadCount(DEFAULT_THREAD_COUNT);
            return;
        }
        switch (info.getType()) {
            case JSONzip.zipEmptyObject /*0*/:
                switch (info.getSubtype()) {
                    case Std.STD_FILE /*1*/:
                    case Std.STD_URL /*2*/:
                        setThreadCount(1);
                    case DEFAULT_THREAD_COUNT /*3*/:
                    case Std.STD_CLASS /*4*/:
                    case Std.STD_JAVA_TYPE /*5*/:
                    case Std.STD_CURRENCY /*6*/:
                    case Std.STD_INET_SOCKET_ADDRESS /*12*/:
                        setThreadCount(2);
                    case CommonStatusCodes.ERROR /*13*/:
                    case GamesStatusCodes.STATUS_INTERRUPTED /*14*/:
                    case GamesStatusCodes.STATUS_TIMEOUT /*15*/:
                        setThreadCount(DEFAULT_THREAD_COUNT);
                    default:
                        setThreadCount(DEFAULT_THREAD_COUNT);
                }
            case Std.STD_FILE /*1*/:
            case Std.STD_CURRENCY /*6*/:
            case Std.STD_CHARSET /*9*/:
                setThreadCount(4);
            default:
                setThreadCount(DEFAULT_THREAD_COUNT);
        }
    }

    private void setThreadCount(int threadCount) {
        setCorePoolSize(threadCount);
        setMaximumPoolSize(threadCount);
    }
}
