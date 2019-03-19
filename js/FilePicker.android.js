
import { NativeModules } from 'react-native'

const { RNTFilePicker } = NativeModules

export default {
  open(options) {
    return RNTFilePicker.open(options || { })
  }
}
