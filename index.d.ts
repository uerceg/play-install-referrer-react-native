//
//  index.d.ts
//  play-install-referrer-react-native
//
//  Created by Uglješa Erceg (@uerceg) on 24th April 2020.
//  Copyright (c) 2021-2022 uerceg. All rights reserved.
//

declare module 'react-native-play-install-referrer' {
  export class PlayInstallReferrerError extends Error {
    responseCode: string
  }

  export type PlayInstallReferrerInfo = {
    googlePlayInstant: boolean
    installBeginTimestampSeconds: string
    installBeginTimestampServerSeconds: string
    installReferrer: string
    installVersion: string
    referrerClickTimestampSeconds: string
    referrerClickTimestampServerSeconds: string
  }

  export type PlayInstallReferrerCallback = (
    info: PlayInstallReferrerInfo | null,
    error: PlayInstallReferrerError | null,
  ) => void

  export const PlayInstallReferrer: {
    getInstallReferrerInfo: (callback: PlayInstallReferrerCallback) => void
  }
}
