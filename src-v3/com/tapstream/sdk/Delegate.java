package com.tapstream.sdk;

interface Delegate {
    int getDelay();

    boolean isRetryAllowed();

    void setDelay(int i);
}
