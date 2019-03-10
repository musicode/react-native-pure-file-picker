
import { NativeModules } from 'react-native'

const { RNTFilePicker } = NativeModules

export default {
  open() {
    return RNTFilePicker.open()
    .then(response => {
      let { path, name, size } = response
      // path 和 name 会经过编码
      if (name !== decodeURIComponent(name)) {
        path = path.substring(0, path.length - name.length)
        name = decodeURIComponent(name)
        path += name
      }
      return { path, name, size }
    })
  }
}
