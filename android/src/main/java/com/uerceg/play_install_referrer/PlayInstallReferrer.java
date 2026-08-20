//
//  PlayInstallReferrer.java
//  play-install-referrer-react-native
//
//  Created by Uglješa Erceg (@uerceg) on 24th April 2020.
//  Copyright © 2020-Present Uglješa Erceg. All rights reserved.
//

package com.uerceg.play_install_referrer;

import javax.annotation.Nullable;
import android.os.RemoteException;
import com.facebook.react.bridge.*;
import com.facebook.react.modules.core.DeviceEventManagerModule;
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
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
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

                                            // create the map with install referrer details and ping callback.
                                            // each field keeps the type the native library reports it as - the
                                            // timestamps go over as doubles because the bridge has no 64 bit
                                            // integer, which is lossless for second resolution values
                                            WritableMap installReferrerInfo = Arguments.createMap();
                                            installReferrerInfo.putString("installReferrer", installReferrer);
                                            installReferrerInfo.putDouble("referrerClickTimestampSeconds", (double)referrerClickTimestampSeconds);
                                            installReferrerInfo.putDouble("installBeginTimestampSeconds", (double)installBeginTimestampSeconds);
                                            installReferrerInfo.putDouble("referrerClickTimestampServerSeconds", (double)referrerClickTimestampServerSeconds);
                                            installReferrerInfo.putDouble("installBeginTimestampServerSeconds", (double)installBeginTimestampServerSeconds);
                                            installReferrerInfo.putString("installVersion", installVersion);
                                            installReferrerInfo.putBoolean("googlePlayInstant", googlePlayInstant);
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
                                    } finally {
                                        // Clean up the connection
                                        referrerClient.endConnection();
                                    }
                                }
                            }).start();
                            break;
                        }
                        case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED: {
                            WritableMap error = Arguments.createMap();
                            error.putInt("responseCode", responseCode);
                            error.putString("message", "FEATURE_NOT_SUPPORTED");
                            sendEvent(getReactApplicationContext(), "play_install_referrer_error", error);
                            referrerClient.endConnection();
                            break;
                        }
                        case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE: {
                            WritableMap error = Arguments.createMap();
                            error.putInt("responseCode", responseCode);
                            error.putString("message", "SERVICE_UNAVAILABLE");
                            sendEvent(getReactApplicationContext(), "play_install_referrer_error", error);
                            referrerClient.endConnection();
                            break;
                        }
                        case InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR: {
                            WritableMap error = Arguments.createMap();
                            error.putInt("responseCode", responseCode);
                            error.putString("message", "DEVELOPER_ERROR");
                            sendEvent(getReactApplicationContext(), "play_install_referrer_error", error);
                            referrerClient.endConnection();
                            break;
                        }
                        case InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED: {
                            WritableMap error = Arguments.createMap();
                            error.putInt("responseCode", responseCode);
                            error.putString("message", "SERVICE_DISCONNECTED");
                            sendEvent(getReactApplicationContext(), "play_install_referrer_error", error);
                            referrerClient.endConnection();
                            break;
                        }
                        case InstallReferrerClient.InstallReferrerResponse.PERMISSION_ERROR: {
                            WritableMap error = Arguments.createMap();
                            error.putInt("responseCode", responseCode);
                            error.putString("message", "PERMISSION_ERROR");
                            sendEvent(getReactApplicationContext(), "play_install_referrer_error", error);
                            referrerClient.endConnection();
                            break;
                        }
                        default: {
                            // without this the callback on the JS side is never invoked at all
                            // for any response code the library adds in the future
                            WritableMap error = Arguments.createMap();
                            error.putInt("responseCode", responseCode);
                            error.putString("message", "Unexpected response code arrived: " + responseCode);
                            sendEvent(getReactApplicationContext(), "play_install_referrer_error", error);
                            referrerClient.endConnection();
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

    @ReactMethod
    public void addListener(String eventName) {
        // trying to fix following warning:
        // WARN  `new NativeEventEmitter()` was called with a non-null argument without the required `addListener` method.
    }

    @ReactMethod
    public void removeListeners(Integer count) {
        // trying to fix following warning:
        // WARN  `new NativeEventEmitter()` was called with a non-null argument without the required `removeListeners` method.
    }
}
