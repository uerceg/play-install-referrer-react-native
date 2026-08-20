# Play Install Referrer Library wrapper for React Native

<table>
    <tr>
        <td align="left">Supported platforms:</td>
        <td align="left"><img src="https://images-fe.ssl-images-amazon.com/images/I/21EctgvtXUL.png" width="16"></td>
    </tr>
    <tr>
        <td align="left">Current version:</td>
        <td align="left"><a href=../../releases/tag/v2.0.1><b>2.0.1</b></a></td>
    </tr>
    <tr>
        <td align="left">Troubles?</td>
        <td align="left"><a href="../../issues/new"><b>Report an issue</b></a></td>
    </tr>
</table>

**react-native-play-install-referrer** is a simple wrapper around Google's [Play Install Referrer Library](https://developer.android.com/google/play/installreferrer/library) which offers basic functionality of obtaining Android referrer information from React Native app.

More information about Play Install Referrer API can be found in [official Google documentation](https://developer.android.com/google/play/installreferrer/igetinstallreferrerservice).

Version of native Play Install Referrer Library which is being used inside of latest **react-native-play-install-referrer** plugin version is [2.2](https://mvnrepository.com/artifact/com.android.installreferrer/installreferrer/2.2).

## Add plugin to your app

**react-native-play-install-referrer** plugin is hosted on [npm repo](https://www.npmjs.com/package/react-native-play-install-referrer) and can be added from there.

**Note**: as of **v2.0.0** the plugin requires **Android Gradle Plugin 7.0** or newer, since it declares its package with the `namespace` DSL. That means **React Native 0.68** or newer - 0.68 is the first release whose template ships AGP 7, everything before it ships AGP 4.2. On older React Native, use v1.1.9.

**Note**: the plugin works with React Native's **New Architecture**. It is verified in bridgeless mode against a minified release build, and needs no additional configuration - `newArchEnabled` can be on or off.

**Yarn**:

```
yarn add react-native-play-install-referrer
```

**npm**:

```
npm install --save react-native-play-install-referrer
```

## Usage

In order to obtain install referrer details, call **getInstallReferrerInfo** static method of **PlayInstallReferrer** class:

```js
import { PlayInstallReferrer } from 'react-native-play-install-referrer';

PlayInstallReferrer.getInstallReferrerInfo((installReferrerInfo, error) => {
  if (!error) {
    console.log("Install referrer = " + installReferrerInfo.installReferrer);
    console.log("Referrer click timestamp seconds = " + installReferrerInfo.referrerClickTimestampSeconds);
    console.log("Install begin timestamp seconds = " + installReferrerInfo.installBeginTimestampSeconds);
    console.log("Referrer click timestamp server seconds = " + installReferrerInfo.referrerClickTimestampServerSeconds);
    console.log("Install begin timestamp server seconds = " + installReferrerInfo.installBeginTimestampServerSeconds);
    console.log("Install version = " + installReferrerInfo.installVersion);
    console.log("Google Play instant = " + installReferrerInfo.googlePlayInstant);
  } else {
    console.log("Failed to get install referrer info!");
    console.log("Response code: " + error.responseCode);
    console.log("Message: " + error.message);
  }
});
```

If successfully obtained, map with content of install referrer information will be delivered into callback method as first parameter. From that map, you can get following install referrer details:

Each field carries the type Google's native `ReferrerDetails` reports it as:

| key | type | description |
| :-- | :--- | :---------- |
| **installReferrer** | `string \| null` | Install referrer string value. |
| **referrerClickTimestampSeconds** | `number` | Timestamp of when user clicked on URL which redirected him/her to Play Store to download your app. |
| **installBeginTimestampSeconds** | `number` | Timestamp of when app installation on device begun. |
| **referrerClickTimestampServerSeconds** | `number` | Server timestamp of when user clicked on URL which redirected him/her to Play Store to download your app. |
| **installBeginTimestampServerSeconds** | `number` | Server timestamp of when app installation on device begun. |
| **installVersion** | `string \| null` | Original app version which was installed. |
| **googlePlayInstant** | `boolean` | Information if your app's instant version (if you have one) was launched in past 7 days. |

**Note**: prior to **v2.0.0** every one of these was delivered as a string, including `googlePlayInstant` - which meant `"false"`, a truthy value in JavaScript. See the [migration guide](MIGRATION.md) if you are upgrading.

Timestamps are sent as doubles, since the React Native bridge has no 64 bit integer type. That is lossless for second resolution values.

You should first check if second parameter in the callback - **error** is **null** or not. If not, for some reason reading of install referrer details failed. In case no error is reported, install referrer information should be delivered into the first map parameter of the callback method.

In case error is reported, you can get following information about the error:

- **responseCode** (`number`): the response code the native Install Referrer Library reported, as its own `int` value. `OK` is never reported here, since it is a success code. Not always present - only when the native library actually returned a code, otherwise **undefined**.

| responseCode | meaning |
| :----------- | :------ |
| `-1` | `SERVICE_DISCONNECTED` |
| `1` | `SERVICE_UNAVAILABLE` |
| `2` | `FEATURE_NOT_SUPPORTED` |
| `3` | `DEVELOPER_ERROR` |
| `4` | `PERMISSION_ERROR` |

The full list is in [Google's documentation](https://developer.android.com/reference/com/android/installreferrer/api/InstallReferrerClient.InstallReferrerResponse). **message** always carries the readable name, so you do not need this table to log an error.
- **Message**: Additional string message which describes error more in detail. **Note**: Message field should always be present in error map.

## Under the hood

Important thing to notice is that in order to work properly, Play Install Referrer Library requires following permission to be added to your app's `AndroidManifest.xml`:

```xml
<uses-permission android:name="com.google.android.finsky.permission.BIND_GET_INSTALL_REFERRER_SERVICE"/>
```

Play Install Referrer Library is added to **react-native-play-install-referrer** plugin as an [Gradle dependency](./android/build.gradle#L29) and it will automatically make sure that manifest file ends up with above mentioned permission added to it upon building your app.

## Testing your integration

Most reports about this plugin turn out to be about how the referrer was tested rather than about the plugin, so it is worth being precise about what is testable.

**What the plugin returns is whatever Google Play hands it.** The plugin does not parse, decode or synthesise the referrer string - if `installReferrer` looks wrong, it is what Play returned.

**Values Play returns when there is no custom referrer to give you:**

- `utm_source=google-play&utm_medium=organic` - Play considers the install organic
- `utm_source=(not%20set)&utm_medium=(not%20set)` - Play had nothing to attribute

Both are Play's own fallbacks, not failures of the plugin, and neither can be turned into your custom referrer after the fact.

**Sideloading cannot produce a referrer.** `adb install`, or building straight to a device, means the install never went through Play, so there is no referrer to read. Timestamps come back as `0` in that case.

**A referrer only survives a real Play install where the click carried it.** Opening a `details?id=...&referrer=...` link so that the Play Store app resolves it directly will often drop the referrer. Opening the same link in a browser, and letting the browser hand off to Play, tends to preserve it.

**The referrer is read once per install.** Uninstall and reinstall through Play to test again - repeat calls on the same install return the same value.

**Internal/Closed testing tracks are not a reliable way to verify this.** They can install without carrying a referrer through, which is a frequent source of confusion.

To check the plugin itself rather than the referrer, look at the error path: on an emulator without the Play Store you should get `FEATURE_NOT_SUPPORTED` or `SERVICE_UNAVAILABLE` delivered to the callback. That proves the plugin is wired up correctly.

## Example app

You can find example app using **react-native-play-install-referrer** plugin inside [**example**](./example) folder of this repository. In case you want to run the app (with re-adding plugin), you can run this command **inside app folder**:

```
yarn remove react-native-play-install-referrer && yarn add ../ && npx react-native run-android
```

## Migration

Instructions for migrating between plugin versions can be found in [here](./MIGRATION.md).
