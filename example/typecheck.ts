/**
 * Compile-time assertions for the plugin's public types.
 *
 * Nothing imports this file - it exists so that `npx tsc --noEmit` fails if
 * index.d.ts ever stops describing what the native side actually sends.
 * This is what would have caught googlePlayInstant being declared a boolean
 * while the bridge delivered the string "false".
 */

import {
  PlayInstallReferrer,
  PlayInstallReferrerCallback,
  PlayInstallReferrerError,
  PlayInstallReferrerInfo,
} from 'react-native-play-install-referrer';

declare const info: PlayInstallReferrerInfo;
declare const error: PlayInstallReferrerError;

// values carry the types the native library reports
export const installReferrer: string | null = info.installReferrer;
export const installVersion: string | null = info.installVersion;
export const googlePlayInstant: boolean = info.googlePlayInstant;
export const referrerClick: number = info.referrerClickTimestampSeconds;
export const installBegin: number = info.installBeginTimestampSeconds;
export const referrerClickServer: number = info.referrerClickTimestampServerSeconds;
export const installBeginServer: number = info.installBeginTimestampServerSeconds;

// timestamps are usable as numbers without parsing
export const clickedAt: Date = new Date(info.referrerClickTimestampSeconds * 1000);

// the error is a plain object, not an Error, and responseCode is optional
export const message: string = error.message;
export const responseCode: number | undefined = error.responseCode;

// the callback signature takes both nullable parameters
export const callback: PlayInstallReferrerCallback = (i, e) => {
  if (e) {
    const code: number | undefined = e.responseCode;
    console.log('response code', code);
    return;
  }
  if (i) {
    const instant: boolean = i.googlePlayInstant;
    console.log('instant', instant);
  }
};

PlayInstallReferrer.getInstallReferrerInfo(callback);
