package com.manishkprpickersdkit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.OrientationEventListener;
import android.widget.Toast;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;



public class CameraActivity extends AppCompatActivity implements CameraHostProvider {

    public static CameraActivity cameraActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView(savedInstanceState);
    }
    OrientationEventListener myOrientationEventListener;

    void initView(Bundle savedInstanceState){
        cameraActivity = this;
        if (savedInstanceState == null) {
            CwacCameraFragment cwacCameraFragment = new CwacCameraFragment();
            CwacCameraFragment.setConfig(ImagePickerActivity.getConfig());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.root_view, cwacCameraFragment)
                    .commit();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraActivity = null;
        //ImagePickerActivity.imagePickerActivity.updatePicture();

    }

    public void closeCameraActivity(){
        callIntentResults();
    }
    public static  int INTENT_CAMERA = 786;


    void callIntentResults(){
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public CameraHost getCameraHost() {
        return ImagePickerActivity.imagePickerActivity.mMyCameraHost;
    }


}