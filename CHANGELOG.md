### Version 2.0.1 [20th August 2026]
#### Fixed
- Reported a lost connection to the install referrer service through the callback. `onInstallReferrerServiceDisconnected` used to be ignored, so a disconnect arriving before a value had been delivered left the callback waiting forever.
- Emitted exactly one event per call. The service can report a disconnect after a value has already gone out, which used to arrive as an error on top of a successful read.

---

### Version 2.0.0 [20th August 2026]
#### Added
- Added **Testing your integration** chapter to README, explaining what Play actually returns and why sideloaded installs never carry a referrer (https://github.com/uerceg/play-install-referrer-react-native/issues/16).
- Added handling of `PERMISSION_ERROR` response code, introduced in Install Referrer Library v2.2.
- Added [migration guide](MIGRATION.md).

#### Changed
- Declared an explicit `namespace` in the Android build file and dropped the deprecated `package` attribute from the manifest, which is how AGP 8 expects a library to declare it. **This raises the minimum Android Gradle Plugin to 7.0** - the version which introduced the `namespace` DSL - which means **React Native 0.68** or newer. The declared `react-native` peer dependency was bumped to match.
- Changed install referrer fields to carry the type Google's native `ReferrerDetails` reports, instead of flattening everything to a string: `googlePlayInstant` is a `boolean`, the four timestamps are `number`, and the two string fields are `string | null`. **`"false"` is truthy in JavaScript**, so a plain `if (info.googlePlayInstant)` check has been wrong until now and starts behaving as intended. See the [migration guide](MIGRATION.md).
- Changed `responseCode` on the error to the native `int` rather than a string name, which keeps it stable across library versions. `message` still carries the readable name.
- Switched **index.js** from an ESM `import` to `require`. The package ships untranspiled and Jest does not transform **node_modules** by default, so the import made `jest` fail with `Cannot use import statement outside a module` in any app which rendered a component importing the plugin.
- Added **example/typecheck.ts**, a set of compile-time assertions over the plugin's public types, so `tsc --noEmit` in the example fails if the definitions ever stop matching what the native side sends.
- Corrected the TypeScript definition of `PlayInstallReferrerError`: it is a plain object with an optional `responseCode`, not an `Error` subclass - nothing on the native side ever created an `Error`.
- Declared `types`, an explicit `files` allowlist and a `react-native` peer dependency in **package.json**, so the published package no longer depends on **.npmignore** and cannot leak local build state.
- Rebuilt the example app on React Native **0.87.0** with minification and the New Architecture enabled, so that R8 and bridgeless mode are both exercised whenever the example is built.

#### Fixed
- Removed a `buildTypes` block from the Android library which set `minifyEnabled false`, pointed at a `proguard-rules.pro` that does not exist, and used `getDefaultProguardFile('proguard-android.txt')`. Recent AGP rejects that file outright, so the library failed to even configure - none of it did anything for a library module in the first place.
- Reported response codes the plugin did not recognise through the callback. Previously the native switch had no default branch, so nothing was emitted at all and the callback never fired.

**Note**: For migration to v2.0.0, please check [migration guide](MIGRATION.md).

---

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
