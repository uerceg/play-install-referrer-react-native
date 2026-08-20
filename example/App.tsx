/**
 * Example app for react-native-play-install-referrer.
 *
 * @format
 */

import React, {useState} from 'react';
import {
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import {SafeAreaProvider, SafeAreaView} from 'react-native-safe-area-context';
import {
  PlayInstallReferrer,
  PlayInstallReferrerError,
  PlayInstallReferrerInfo,
} from 'react-native-play-install-referrer';

type Row = {label: string; value: string};

function App(): React.JSX.Element {
  const [rows, setRows] = useState<Row[]>([]);
  const [status, setStatus] = useState('tap to read the install referrer');

  function read() {
    setStatus('reading...');
    setRows([]);
    PlayInstallReferrer.getInstallReferrerInfo(
      (info: PlayInstallReferrerInfo | null, error: PlayInstallReferrerError | null) => {
        if (error) {
          setStatus('error');
          setRows([
            {label: 'response code', value: String(error.responseCode ?? '-')},
            {label: 'message', value: String(error.message ?? '-')},
          ]);
          return;
        }
        if (!info) {
          setStatus('no details delivered');
          return;
        }
        setStatus('ok');
        setRows([
          {label: 'install referrer', value: String(info.installReferrer)},
          {label: 'referrer click', value: String(info.referrerClickTimestampSeconds)},
          {label: 'install begin', value: String(info.installBeginTimestampSeconds)},
          {label: 'referrer click (server)', value: String(info.referrerClickTimestampServerSeconds)},
          {label: 'install begin (server)', value: String(info.installBeginTimestampServerSeconds)},
          {label: 'install version', value: String(info.installVersion)},
          {label: 'google play instant', value: String(info.googlePlayInstant)},
        ]);
      },
    );
  }

  return (
    <SafeAreaProvider>
    <SafeAreaView style={styles.screen}>
      <StatusBar barStyle="light-content" />
      <ScrollView contentContainerStyle={styles.content}>
        <Text style={styles.title}># play install referrer</Text>
        <Text style={styles.status}>{status}</Text>
        <TouchableOpacity onPress={read} style={styles.button}>
          <Text style={styles.buttonText}>[ get install referrer ]</Text>
        </TouchableOpacity>
        <View>
          {rows.map(row => (
            <View key={row.label} style={styles.row}>
              <Text style={styles.label}>{row.label}</Text>
              <Text style={styles.value}>{row.value}</Text>
            </View>
          ))}
        </View>
      </ScrollView>
    </SafeAreaView>
    </SafeAreaProvider>
  );
}

const styles = StyleSheet.create({
  screen: {flex: 1, backgroundColor: '#000'},
  content: {padding: 24},
  title: {color: '#AFFFA6', fontSize: 22, fontWeight: 'bold', fontFamily: 'monospace'},
  status: {color: '#777', fontSize: 13, marginTop: 4, fontFamily: 'monospace'},
  button: {marginTop: 24, marginBottom: 24},
  buttonText: {color: '#AFFFA6', fontSize: 17, fontWeight: 'bold', fontFamily: 'monospace'},
  row: {marginBottom: 16},
  label: {color: '#777', fontSize: 13, fontFamily: 'monospace'},
  value: {color: '#A4FFFF', fontSize: 14, fontWeight: 'bold', fontFamily: 'monospace'},
});

export default App;
