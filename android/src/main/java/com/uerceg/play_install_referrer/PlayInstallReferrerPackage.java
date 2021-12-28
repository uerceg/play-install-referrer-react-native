//
//  PlayInstallReferrerPackage.java
//  play-install-referrer-react-native
//
//  Created by Uglješa Erceg (@uerceg) on 25th April 2020.
//  Copyright (c) 2021 uerceg. All rights reserved.
//

package com.uerceg.play_install_referrer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

public class PlayInstallReferrerPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new PlayInstallReferrer(reactContext));
        return modules;
    }

    // deprecated in react-native 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
