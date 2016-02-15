package com.crumby;

import android.support.v4.view.ViewCompat;
import java.util.concurrent.atomic.AtomicInteger;

public enum ViewIdGenerator {
    INSTANCE;
    
    private final AtomicInteger sNextGeneratedId;

    public int generateViewId() {
        int result;
        int newValue;
        do {
            result = this.sNextGeneratedId.get();
            newValue = result + 1;
            if (newValue > ViewCompat.MEASURED_SIZE_MASK) {
                newValue = 1;
            }
        } while (!this.sNextGeneratedId.compareAndSet(result, newValue));
        return result;
    }
}
