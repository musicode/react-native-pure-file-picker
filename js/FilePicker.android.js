
import { NativeModules } from 'react-native'

const { RNTFilePicker } = NativeModules

export default {
  open() {
    return RNTFilePicker.open()
  }
}
