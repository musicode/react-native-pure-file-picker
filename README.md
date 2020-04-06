# react-native-pure-file-picker

This is a module which help you pick a document file, such as word, excel, ppt and pdf.

## Installation

```
npm i react-native-pure-file-picker
// link below 0.60
react-native link react-native-pure-file-picker
```

## Setup

### iOS

`Capabilities` -> `iCloud`, check `iCloud Documents`.

### Android

Add `jitpack` in your `android/build.gradle` at the end of repositories:

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

## Usage

```js
import filePicker from 'react-native-pure-file-picker'

// At first, make sure you have the permissions.
// ios: nothing
// android: WRITE_EXTERNAL_STORAGE

// If you don't have these permissions, you can't call open method.

// The first `options` param is just available for android.
filePicker.open({
  // optional
  submitButtonTitle: '确定',
  // optional
  cancelButtonTitle: '取消',
  // optional
  dateFormatCurrentDate: 'HH:mm',
  // optional
  dateFormatCurrentYear: 'MM月dd日',
  // optional
  dateFormatAnyTime: 'yyyy年MM月dd日',
})
.then(file => {
  let { path, name, size } = file
})
.catch(() => {
  // click cancel button
})
```