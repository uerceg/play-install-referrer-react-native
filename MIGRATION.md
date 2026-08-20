## Migrate react-native-play-install-referrer plugin to v2.0.0

The JavaScript API is unchanged - `getInstallReferrerInfo(callback)` has the same signature and delivers the same keys.

### The plugin now requires AGP 7.0 or newer

The Android library declares its package with the `namespace` DSL instead of the `package` attribute in the manifest, because that is what AGP 8 expects and the attribute is gone there. `namespace` was introduced in AGP 7.0, so a project on an older Android Gradle Plugin will now fail with `Could not find method namespace()`. React Native ships AGP 7 from **0.68** onwards, so that is the effective minimum - the declared peer dependency was bumped to match. Upgrade React Native, or stay on v1.1.9.

### Fields now carry their native types

Every field used to cross the bridge as a string, whatever the native library reported it as. They now carry the type Google's `ReferrerDetails` actually returns:

| field | native type | before | now |
| :---- | :---------- | :----- | :-- |
| `installReferrer` | `String` | `string` | `string \| null` |
| `installVersion` | `String` | `string` | `string \| null` |
| `googlePlayInstant` | `boolean` | `"true"` / `"false"` | `true` / `false` |
| `referrerClickTimestampSeconds` | `long` | `"1755640000"` | `1755640000` |
| `installBeginTimestampSeconds` | `long` | `"1755640000"` | `1755640000` |
| `referrerClickTimestampServerSeconds` | `long` | `"1755640000"` | `1755640000` |
| `installBeginTimestampServerSeconds` | `long` | `"1755640000"` | `1755640000` |

The one to watch is **`googlePlayInstant`**, because `"false"` is truthy in JavaScript:

```js
if (installReferrerInfo.googlePlayInstant) {
    // this used to run even when the value was "false"
}
```

If you wrote that, it has been wrong all along and now behaves as you intended. If you worked around it by comparing to a string, drop the comparison:

```js
// before
if (installReferrerInfo.googlePlayInstant === 'true') {
// now
if (installReferrerInfo.googlePlayInstant) {
```

Timestamps no longer need parsing:

```js
// before
new Date(parseInt(installReferrerInfo.referrerClickTimestampSeconds, 10) * 1000)
// now
new Date(installReferrerInfo.referrerClickTimestampSeconds * 1000)
```

They are sent as doubles, because the bridge has no 64 bit integer type.

Both string fields are declared `string | null` because the native getters can return null. `installVersion` in particular comes back null on installs which did not come from Google Play.

### The error passed to the callback is a plain object

`PlayInstallReferrerError` was declared as `extends Error`, but nothing on the native side creates an `Error` - it is a plain object that crosses the bridge. `error instanceof Error` was therefore always `false`. The definition is now a plain type:

```ts
export type PlayInstallReferrerError = {
  message: string
  responseCode?: string
}
```

`responseCode` is now the native `int` rather than a string name, and it is correctly optional - present only when the native library actually reported a code, absent for exceptions. If you were matching on the name, match on the number instead, or read `message`, which still carries the readable name:

```js
// before
if (error.responseCode === 'FEATURE_NOT_SUPPORTED') {
// now
if (error.responseCode === 2) {
```

The values are `-1` `SERVICE_DISCONNECTED`, `1` `SERVICE_UNAVAILABLE`, `2` `FEATURE_NOT_SUPPORTED`, `3` `DEVELOPER_ERROR`, `4` `PERMISSION_ERROR`.
