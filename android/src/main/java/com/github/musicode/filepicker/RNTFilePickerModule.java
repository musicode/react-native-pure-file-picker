package com.github.musicode.filepicker;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.github.herokotlin.filepicker.FilePickerActivity;
import com.github.herokotlin.filepicker.FilePickerCallback;
import com.github.herokotlin.filepicker.FilePickerConfiguration;
import com.github.herokotlin.filepicker.model.PickedFile;

import java.util.ArrayList;
import java.util.List;

public class RNTFilePickerModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RNTFilePickerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNTFilePicker";
    }

    @ReactMethod
    public void open(ReadableMap options, final Promise promise) {

        FilePickerConfiguration configuration = new FilePickerConfiguration() {
            @Override
            public boolean requestPermissions(Activity activity, List<String> permissions, int requestCode) {
                List<String> list = new ArrayList<>();

                for (String permission: permissions) {
                    if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                        list.add(permission);
                    }
                }

                if (list.size() > 0) {
                    ActivityCompat.requestPermissions(activity, list.toArray(new String[list.size()]), requestCode);
                    return false;
                }

                return true;
            }
        };

        configuration.setMaxSelectCount(1);

        if (options.hasKey("cancelButtonTitle")) {
            configuration.setCancelButtonTitle(options.getString("cancelButtonTitle"));
        }
        if (options.hasKey("submitButtonTitle")) {
            configuration.setSubmitButtonTitle(options.getString("submitButtonTitle"));
        }

        if (options.hasKey("dateFormatCurrentDate")) {
            configuration.setDateFormatCurrentDate(options.getString("dateFormatCurrentDate"));
        }
        if (options.hasKey("dateFormatCurrentYear")) {
            configuration.setDateFormatCurrentYear(options.getString("dateFormatCurrentYear"));
        }
        if (options.hasKey("dateFormatAnyTime")) {
            configuration.setDateFormatAnyTime(options.getString("dateFormatAnyTime"));
        }

        FilePickerCallback callback = new FilePickerCallback() {

            @Override
            public void onCancel(Activity activity) {
                activity.finish();
                promise.reject("-1", "cancel");
            }

            @Override
            public void onFetchWithoutExternalStorage(Activity activity) {
                activity.finish();
                promise.reject("1", "external storage is not available");
            }

            @Override
            public void onFetchWithoutPermissions(Activity activity) {
                activity.finish();
                promise.reject("2", "has no permissions");
            }

            @Override
            public void onPermissionsDenied(Activity activity) {
                activity.finish();
                promise.reject("3", "you denied the requested permissions.");
            }

            @Override
            public void onPermissionsGranted(Activity activity) {

            }

            @Override
            public void onSubmit(Activity activity, List<PickedFile> list) {

                activity.finish();

                WritableMap map = Arguments.createMap();
                PickedFile file = list.get(0);

                map.putString("path", file.getPath());
                map.putString("name", file.getName());
                map.putInt("size", file.getSize());

                promise.resolve(map);

            }
        };

        FilePickerActivity.Companion.setConfiguration(configuration);
        FilePickerActivity.Companion.setCallback(callback);

        FilePickerActivity.Companion.newInstance(reactContext.getCurrentActivity());

    }

}