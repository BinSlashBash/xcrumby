package com.tapstream.sdk;

import com.tapstream.sdk.Hit.CompletionHandler;

public interface Api {
    void fireEvent(Event event);

    void fireHit(Hit hit, CompletionHandler completionHandler);
}
