# react-native-pure-file-picker

This is a module which help you pick a document file, such as word, excel, ppt and pdf.

## Installation

```
npm i react-native-pure-file-picker
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
import FilePicker from 'react-native-pure-file-picker'

// the first `options` param is just available for android.
FilePicker.open({
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
.catch(error => {
  let { code } = error
  // -1: click cancel button
  // 1: has no permissions
  // 2: denied the requested permissions
  // 3: external storage is not writable
})
```