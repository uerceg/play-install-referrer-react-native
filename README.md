# Play Install Referrer Library wrapper for React Native

<table>
    <tr>
        <td align="left">Supported platforms:</td>
        <td align="left"><img src="https://images-fe.ssl-images-amazon.com/images/I/21EctgvtXUL.png" width="16"></td>
    </tr>
    <tr>
        <td align="left">Current version:</td>
        <td align="left"><a href=../../releases/tag/v1.1.6><b>1.1.6</b></a></td>
    </tr>
    <tr>
        <td align="left">Troubles?</td>
        <td align="left"><a href="../../issues/new"><b>Report an issue</b></a></td>
    </tr>
</table>

**react-native-play-install-referrer** is a simple wrapper around Google's [Play Install Referrer Library](https://developer.android.com/google/play/installreferrer/library) which offers basic functionality of obtaining Android referrer information from React Native app.

More information about Play Install Referrer API can be found in [official Google documentation](https://developer.android.com/google/play/installreferrer/igetinstallreferrerservice).

Version of native Play Install Referrer Library which is being used inside of latest **react-native-play-install-referrer** plugin version is [2.1](https://mvnrepository.com/artifact/com.android.installreferrer/installreferrer/2.1).

## Add plugin to your app

**react-native-play-install-referrer** plugin is hosted on [npm repo](https://www.npmjs.com/package/react-native-play-install-referrer) and can be added from there.

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

- Install referrer string value (**installReferrer** key).
- Timestamp of when user clicked on URL which redirected him/her to Play Store to download your app (**referrerClickTimestampSeconds** key).
- Timestamp of when app installation on device begun (**installBeginTimestampSeconds** key).
- Server timestamp of when user clicked on URL which redirected him/her to Play Store to download your app (**referrerClickTimestampServerSeconds** key).
- Server timestamp of when app installation on device begun (**installBeginTimestampServerSeconds** key).
- Original app version which was installed (**installVersion** key).
- Information if your app's instant version (if you have one) was launched in past 7 days (**googlePlayInstant** key).

You should first check if second parameter in the callback - **error** is **null** or not. If not, for some reason reading of install referrer details failed. In case no error is reported, install referrer information should be delivered into the first map parameter of the callback method.

In case error is reported, you can get following information about the error:

- **Response code**: Error response code which native Install Referrer Library might return. Full list of potential response codes can be found in [here](https://developer.android.com/reference/com/android/installreferrer/api/InstallReferrerClient.InstallReferrerResponse) (`OK` will never be reported in this property, since it's a success status code). **Note**: Error code field is not always present in error map - only if error created when one of the error codes from native Install Referrer Library is received; otherwise this field will be **undefined**.
- **Message**: Additional string message which describes error more in detail. **Note**: Message field should always be present in error map.

## Under the hood

Important thing to notice is that in order to work properly, Play Install Referrer Library requires following permission to be added to your app's `AndroidManifest.xml`:

```xml
<uses-permission android:name="com.google.android.finsky.permission.BIND_GET_INSTALL_REFERRER_SERVICE"/>
```

Play Install Referrer Library is added to **react-native-play-install-referrer** plugin as an [Gradle dependency](./plugin/android/build.gradle#L30) and it will automatically make sure that manifest file ends up with above mentioned permission added to it upon building your app.

## Todos

List of tasks to be done in this repository can be found in [here](./TODO.md).
