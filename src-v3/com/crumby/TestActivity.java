package com.crumby;

import android.app.Activity;
import android.os.Bundle;

public class TestActivity extends Activity {
    public Activity getActivity() {
        return this;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0065R.layout.test_activity);
    }
}
