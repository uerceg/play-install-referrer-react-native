### Version 1.1.9 [14th May 2025]
#### Fixed
- Moved fetching of the install referrer information into the background thread (https://github.com/uerceg/play-install-referrer-react-native/pull/44). (thanks to @mariuskurgonas)

#### Changed
- Updated compile and target SDK API to 35.
- Made dependabot happy (hopefully).

---

### Version 1.1.8 [19th January 2022]
#### Added
- Added example app in TypeScript.

#### Changed
- Updated TypeScript definition for callback. (thanks to @apfritts)
- Ignored this module on non-Android platforms. (thanks to @apfritts)

---

### Version 1.1.7 [20th October 2021]
#### Changed
- Updated native Play Install Referrer library to **v2.2**.

#### Fixed
- Fixed `addListener` and `removeListeners` warnings ([same issue](https://github.com/react-native-netinfo/react-native-netinfo/issues/486) with [suggested fix](https://github.com/software-mansion/react-native-reanimated/pull/2316/files) which got copy pasted in this plugin as well). (thanks to @mikehardy)

---

### Version 1.1.6 [29th December 2020]
#### Added
- Added **Example app** chapter to README.

#### Changed
- Unified plugin and example app package names (under the hood changes, no affect on plugin functionality).

---

### Version 1.1.5 [15th September 2020]
#### Fixed
- Fixed issue with multiple callback invocation from native code which caused occasional crashes on some devices (https://github.com/uerceg/play-install-referrer-react-native/issues/1).

#### Changed
- Changed repository structure by moving contents of **plugin** folder to the root of repository to hopefully make README visible at https://www.npmjs.com/package/react-native-play-install-referrer.

---

### Version 1.1.4 [7th September 2020]
#### Changed
- Changed **package.json** `homepage` value to hopefully make README visible at https://www.npmjs.com/package/react-native-play-install-referrer.

---

### Version 1.1.3 [7th September 2020]
#### Changed
- Changed **package.json** `homepage` value to hopefully make README visible at https://www.npmjs.com/package/react-native-play-install-referrer. Spoiler: I failed again. New attempt(s) coming soon, stay tuned.

---

### Version 1.1.2 [7th September 2020]
#### Changed
- Changed **package.json** `homepage` value to hopefully make README visible at https://www.npmjs.com/package/react-native-play-install-referrer. Spoiler: I failed again. New attempt(s) coming soon, stay tuned.

---

### Version 1.1.1 [12th July 2020]
#### Changed
- Changed **package.json** `homepage` value to hopefully make README visible at https://www.npmjs.com/package/react-native-play-install-referrer. Spoiler: I failed. New attempt(s) coming soon, stay tuned.

---

### Version 1.1.0 [12th July 2020]
#### Added
- Added reading of 3 new fields introduced in Play Install Referrer library **v2.0** - `referrerClickTimestampServerSeconds`, `installBeginTimestampServerSeconds` and `installVersion`.

#### Changed
- Changed my GitHub username from @uerceg to @ugi.
- Updated Play Install Referrer library to **v2.1**.
- Updated example app to show newly read fields as well.

**Note**: Project is moved from https://github.com/uerceg/play-install-referrer-react-native.

---

### Version 1.0.0 [25th May 2020]
#### Added
- Initial release of **react-native-play-install-referrer** plugin.
