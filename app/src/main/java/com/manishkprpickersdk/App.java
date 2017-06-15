package com.manishkprpickersdk;

import android.app.Application;

import com.adobe.creativesdk.foundation.AdobeCSDKFoundation;
import com.adobe.creativesdk.foundation.auth.IAdobeAuthClientCredentials;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by edge on 28/4/17.
 */

public class App extends Application implements IAdobeAuthClientCredentials{

    /* Be sure to fill in the two strings below. */
    private static final String CREATIVE_SDK_CLIENT_ID      = "0f174073830740b3b5d6ae522a37309a";
    private static final String CREATIVE_SDK_CLIENT_SECRET  = "6fce2d10-f58b-4769-94fe-2018f79955b7";
    private static final String CREATIVE_SDK_REDIRECT_URI   = "ams+5430e36c2931f5ed0ae301ca2d32f4c04f684574://adobeid/0f174073830740b3b5d6ae522a37309a";
    private static final String[] CREATIVE_SDK_SCOPES       = {"email", "profile", "address"};

    @Override
    public void onCreate() {
        super.onCreate();
        initLeakCanary();
        AdobeCSDKFoundation.initializeCSDKFoundation(getApplicationContext());
    }

    void initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    @Override
    public String getClientID() {
        return CREATIVE_SDK_CLIENT_ID;
    }

    @Override
    public String getClientSecret() {
        return CREATIVE_SDK_CLIENT_SECRET;
    }

    @Override
    public String[] getAdditionalScopesList() {
        return CREATIVE_SDK_SCOPES;
    }

    @Override
    public String getRedirectURI() {
        return CREATIVE_SDK_REDIRECT_URI;
    }
}
