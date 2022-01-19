/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  Button,
  ToastAndroid,
  Platform,
} from 'react-native';
import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';
import { PlayInstallReferrer } from 'react-native-play-install-referrer';

const App: () => React$Node = () => {
  function _onPress_getInstallReferrerInfo() {
    if (Platform.OS === 'android') {
      PlayInstallReferrer.getInstallReferrerInfo((installReferrerInfo, error) => {
        if (!error) {
          var msg = "Install referrer = " + installReferrerInfo.installReferrer;
          console.log("Install referrer = " + installReferrerInfo.installReferrer);
          msg += "\n\nReferrer click timestamp seconds = " + installReferrerInfo.referrerClickTimestampSeconds;
          console.log("Referrer click timestamp seconds = " + installReferrerInfo.referrerClickTimestampSeconds);
          msg += "\n\nInstall begin timestamp seconds = " + installReferrerInfo.installBeginTimestampSeconds;
          console.log("Install begin timestamp seconds = " + installReferrerInfo.installBeginTimestampSeconds);
          msg += "\n\nReferrer click timestamp server seconds = " + installReferrerInfo.referrerClickTimestampServerSeconds;
          console.log("Referrer click timestamp server seconds = " + installReferrerInfo.referrerClickTimestampServerSeconds);
          msg += "\n\nInstall begin timestamp seconds = " + installReferrerInfo.installBeginTimestampServerSeconds;
          console.log("Install begin timestamp seconds = " + installReferrerInfo.installBeginTimestampServerSeconds);
          msg += "\n\nInstall version = " + installReferrerInfo.installVersion;
          console.log("Install version = " + installReferrerInfo.installVersion);
          msg += "\n\nGoogle Play instant = " + installReferrerInfo.googlePlayInstant;
          console.log("Google Play instant = " + installReferrerInfo.googlePlayInstant);
          ToastAndroid.show(msg, ToastAndroid.LONG);
        } else {
          var msg = "Failed to get install referrer info!";
          console.log("Failed to get install referrer info!");
          msg += "\n\nResponse code: " + error.responseCode;
          console.log("Response code: " + error.responseCode);
          msg += "\n\nMessage: " + error.message;
          console.log("Message: " + error.message);
          ToastAndroid.show(msg, ToastAndroid.LONG);
        }
      });
    }
  }

  return (
    <>
      <View style={styles.buttonSection}>
        <Button onPress={() => _onPress_getInstallReferrerInfo()} 
          style={styles.buttonStyle}
          title="Get Install Referrer Info"
        />
      </View>
    </>
  );
};

const styles = StyleSheet.create({
  buttonSection: {
    width: '70%',
    height: '100%',
    alignSelf: 'center',
    justifyContent: 'center',
  },
  buttonStyle: {
    backgroundColor: 'blue',
    color: 'white'
  },
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});

export default App;
