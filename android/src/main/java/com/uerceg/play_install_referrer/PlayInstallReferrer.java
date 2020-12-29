//
//  PlayInstallReferrer.java
//  play-install-referrer-react-native
//
//  Created by Uglješa Erceg (@uerceg) on 24th April 2020.
//  Copyright (c) 2020 uerceg. All rights reserved.
//

package com.uerceg.play_install_referrer;

import android.os.RemoteException;

import java.util.Map;

import javax.annotation.Nullable;

import com.facebook.react.bridge.*;
import com.facebook.react.modules.core.*;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;

public class PlayInstallReferrer extends ReactContextBaseJavaModule {
    public PlayInstallReferrer(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "PlayInstallReferrer";
    }

    @Override
    public void initialize() {
    }

    @ReactMethod
    public void getInstallReferrerInfo() {
        // emitting two event types:
        //  - play_install_referrer_value in case value was successfully read
        //  - play_install_referrer_error in case value failed to be read
        try {
            final InstallReferrerClient referrerClient = InstallReferrerClient.newBuilder(getReactApplicationContext()).build();
            referrerClient.startConnection(new InstallReferrerStateListener() {
                @Override
                public void onInstallReferrerSetupFinished(int responseCode) {
                    switch (responseCode) {
                        case InstallReferrerClient.InstallReferrerResponse.OK: {
                            try {
                                ReferrerDetails response = referrerClient.getInstallReferrer();
                                String installReferrer = null;
                                long referrerClickTimestampSeconds = 0L;
                                long installBeginTimestampSeconds = 0L;
                                long referrerClickTimestampServerSeconds = 0L;
                                long installBeginTimestampServerSeconds = 0L;
                                String installVersion = null;
                                boolean googlePlayInstant = false;
                                if (response != null) {
                                    installReferrer = response.getInstallReferrer();
                                    referrerClickTimestampSeconds = response.getReferrerClickTimestampSeconds();
                                    installBeginTimestampSeconds = response.getInstallBeginTimestampSeconds();
                                    referrerClickTimestampServerSeconds = response.getReferrerClickTimestampServerSeconds();
                                    installBeginTimestampServerSeconds = response.getInstallBeginTimestampServerSeconds();
                                    installVersion = response.getInstallVersion();
                                    googlePlayInstant = response.getGooglePlayInstantParam();

                                    // create the map with install referrer details and ping callback
                                    WritableMap installReferrerInfo = Arguments.createMap();
                                    installReferrerInfo.putString("installReferrer", installReferrer);
                                    installReferrerInfo.putString("referrerClickTimestampSeconds", Long.toString(referrerClickTimestampSeconds));
                                    installReferrerInfo.putString("installBeginTimestampSeconds", Long.toString(installBeginTimestampSeconds));
                                    installReferrerInfo.putString("referrerClickTimestampServerSeconds", Long.toString(referrerClickTimestampServerSeconds));
                                    installReferrerInfo.putString("installBeginTimestampServerSeconds", Long.toString(installBeginTimestampServerSeconds));
                                    installReferrerInfo.putString("installVersion", installVersion);
                                    installReferrerInfo.putString("googlePlayInstant", Boolean.toString(googlePlayInstant));
                                    sendEvent(getReactApplicationContext(), "play_install_referrer_value", installReferrerInfo);
                                } else {
                                    WritableMap error = Arguments.createMap();
                                    error.putString("message", "Response from install referrer library was null");
                                    sendEvent(getReactApplicationContext(), "play_install_referrer_error", error);
                                }
                            } catch (RemoteException ex) {
                                WritableMap error = Arguments.createMap();
                                error.putString("message", "Exception while reading install referrer info: " + ex.getMessage());
                                sendEvent(getReactApplicationContext(), "play_install_referrer_error", error);
                            }
                            break;
                        }
                        case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED: {
                            WritableMap error = Arguments.createMap();
                            error.putString("responseCode", "FEATURE_NOT_SUPPORTED");
                            error.putString("message", "FEATURE_NOT_SUPPORTED");
                            sendEvent(getReactApplicationContext(), "play_install_referrer_error", error);
                            break;
                        }
                        case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE: {
                            WritableMap error = Arguments.createMap();
                            error.putString("responseCode", "SERVICE_UNAVAILABLE");
                            error.putString("message", "SERVICE_UNAVAILABLE");
                            sendEvent(getReactApplicationContext(), "play_install_referrer_error", error);
                            break;
                        }
                        case InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR: {
                            WritableMap error = Arguments.createMap();
                            error.putString("responseCode", "DEVELOPER_ERROR");
                            error.putString("message", "DEVELOPER_ERROR");
                            sendEvent(getReactApplicationContext(), "play_install_referrer_error", error);
                            break;
                        }
                        case InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED: {
                            WritableMap error = Arguments.createMap();
                            error.putString("responseCode", "SERVICE_DISCONNECTED");
                            error.putString("message", "SERVICE_DISCONNECTED");
                            sendEvent(getReactApplicationContext(), "play_install_referrer_error", error);
                            break;
                        }
                    }
                }

                @Override
                public void onInstallReferrerServiceDisconnected() {
                    // no need to handle this
                }
            });
        } catch (Throwable ex) {
            WritableMap error = Arguments.createMap();
            error.putString("message", "Exception while starting connection with referrer client: " + ex.getMessage());
            sendEvent(getReactApplicationContext(), "play_install_referrer_error", error);
        }
    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, params);
    }
}
