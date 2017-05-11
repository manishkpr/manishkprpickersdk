package com.manishkprpickersdkit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.flurgle.camerakit.CameraKit;
import com.flurgle.camerakit.CameraListener;
import com.flurgle.camerakit.CameraView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CameraActivity extends AppCompatActivity implements CameraHostProvider {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView(savedInstanceState);
    }

    void initView(Bundle savedInstanceState){
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
    public CameraHost getCameraHost() {
        return ImagePickerActivity.imagePickerActivity.mMyCameraHost;
    }
}