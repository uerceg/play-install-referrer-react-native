//
//  index.d.ts
//  play-install-referrer-react-native
//
//  Created by Uglješa Erceg (@uerceg) on 24th April 2020.
//  Copyright © 2020-Present Uglješa Erceg. All rights reserved.
//

declare module 'react-native-play-install-referrer' {
  // plain object emitted over the bridge, not an Error instance
  export type PlayInstallReferrerError = {
    message: string
    // the native response code, present only when the library reported one
    // -1 SERVICE_DISCONNECTED
    // 1 SERVICE_UNAVAILABLE,
    // 2 FEATURE_NOT_SUPPORTED,
    // 3 DEVELOPER_ERROR,
    // 4 PERMISSION_ERROR
    responseCode?: number
  }

  // each field carries the type the native Play Install Referrer Library reports
  export type PlayInstallReferrerInfo = {
    installReferrer: string | null
    installVersion: string | null
    googlePlayInstant: boolean
    installBeginTimestampSeconds: number
    installBeginTimestampServerSeconds: number
    referrerClickTimestampSeconds: number
    referrerClickTimestampServerSeconds: number
  }

  export type PlayInstallReferrerCallback = (
    info: PlayInstallReferrerInfo | null,
    error: PlayInstallReferrerError | null,
  ) => void

  export const PlayInstallReferrer: {
    getInstallReferrerInfo: (callback: PlayInstallReferrerCallback) => void
  }
}
