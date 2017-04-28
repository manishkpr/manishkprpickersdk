package com.manishkprpickersdk;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by edge on 28/4/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLeakCanary();
    }

    void initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
