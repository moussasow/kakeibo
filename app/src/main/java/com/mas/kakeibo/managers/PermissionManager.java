package com.mas.kakeibo.managers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by sow.m on 2018/12/10.
 */
public class PermissionManager {

    private static final int REQUEST_CODE = 10;
    private final Activity activity;

    public PermissionManager(Activity activity) {
        this.activity = activity;
    }

    public boolean hasCameraPermission() {
        int permissionCheckResult = ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.CAMERA
        );
        return permissionCheckResult == PackageManager.PERMISSION_GRANTED;
    }

    public void requestCameraPermission() {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.CAMERA},
                REQUEST_CODE
        );
    }

    public boolean resultGranted(int requestCode,
                          String[] permissions,
                          int[] grantResults) {

        if (requestCode != REQUEST_CODE) {
            return false;
        }

        if (grantResults.length < 1) {
            return false;
        }
        if (!(permissions[0].equals(Manifest.permission.CAMERA))) {
            return false;
        }

        //View noPermissionView = activity.findViewById(R.id.no_permission);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //noPermissionView.setVisibility(View.GONE);
            return true;
        }

        requestCameraPermission();
        //noPermissionView.setVisibility(View.VISIBLE);
        return false;
    }
}
