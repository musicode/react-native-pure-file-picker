package com.github.musicode.filepicker

import android.app.Activity

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableMap
import com.github.herokotlin.filepicker.FilePickerActivity
import com.github.herokotlin.filepicker.FilePickerCallback
import com.github.herokotlin.filepicker.FilePickerConfiguration
import com.github.herokotlin.filepicker.model.PickedFile

class RNTFilePickerModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "RNTFilePicker"
    }

    @ReactMethod
    fun open(options: ReadableMap, promise: Promise) {

        val configuration = object : FilePickerConfiguration() {

        }

        configuration.maxSelectCount = 1

        if (options.hasKey("cancelButtonTitle")) {
            configuration.cancelButtonTitle = options.getString("cancelButtonTitle")!!
        }
        if (options.hasKey("submitButtonTitle")) {
            configuration.submitButtonTitle = options.getString("submitButtonTitle")!!
        }

        if (options.hasKey("dateFormatCurrentDate")) {
            configuration.dateFormatCurrentDate = options.getString("dateFormatCurrentDate")!!
        }
        if (options.hasKey("dateFormatCurrentYear")) {
            configuration.dateFormatCurrentYear = options.getString("dateFormatCurrentYear")!!
        }
        if (options.hasKey("dateFormatAnyTime")) {
            configuration.dateFormatAnyTime = options.getString("dateFormatAnyTime")!!
        }

        val callback = object : FilePickerCallback {

            override fun onCancel(activity: Activity) {
                activity.finish()
                promise.reject("-1", "cancel")
            }

            override fun onSubmit(activity: Activity, assetList: List<PickedFile>) {

                activity.finish()

                val map = Arguments.createMap()
                val (path, name, size) = assetList[0]

                map.putString("path", path)
                map.putString("name", name)
                map.putInt("size", size)

                promise.resolve(map)

            }
        }

        FilePickerActivity.configuration = configuration
        FilePickerActivity.callback = callback

        FilePickerActivity.newInstance(currentActivity!!)

    }

}