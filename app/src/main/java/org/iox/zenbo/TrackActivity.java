/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*  This file has been modified by Nataniel Ruiz affiliated with Wall Lab
 *  at the Georgia Institute of Technology School of Interactive Computing
 */

package org.iox.zenbo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.asus.robotframework.API.RobotAPI;
import com.asus.robotframework.API.RobotFace;

import java.util.TimerTask;


public class TrackActivity extends Activity {
    private static final int PERMISSIONS_REQUEST = 1;

    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private static final String PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;

    private RobotAPI mRobotAPI;
    private int ExpressionIndex = 0;
    private String[] numbers = new String[27];

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if (hasPermission()) {
            if (null == savedInstanceState) {
//                setFragment();
            }
        } else {
            requestPermission();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        this.mRobotAPI = new RobotAPI(getApplicationContext());
        numbers[0] = "zero";
        numbers[1] = "one";
        numbers[2] = "two";
        numbers[3] = "three";
        numbers[4] = "four";
        numbers[5] = "five";
        numbers[6] = "six";
        numbers[7] = "seven";
        numbers[8] = "eight";
        numbers[9] = "nine";
        numbers[10] = "ten";
        numbers[11] = "eleven";
        numbers[12] = "twelve";
        numbers[13] = "thirteen";
        numbers[14] = "fourteen";
        numbers[15] = "fifteen";
        numbers[16] = "sixteen";
        numbers[17] = "seventeen";
        numbers[18] = "eighteen";
        numbers[19] = "nineteen";
        numbers[20] = "twenty";
        numbers[21] = "twenty one";
        numbers[22] = "twenty two";
        numbers[23] = "twenty three";
        numbers[24] = "twenty four";
        numbers[25] = "twenty five";
        numbers[26] = "twenty six";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    setFragment();
                } else {
                    requestPermission();
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mRobotAPI.robot.setPressOnHeadAction(false);
        mRobotAPI.robot.setVoiceTrigger(false);     //disable the voice trigger

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
        java.util.Timer timer_change_facial_expressions = new java.util.Timer(true);
        long delay = 1000;
        long period = 5000;        //33 ms

        TimerTask task_change_facial_expression = new TimerTask() {
            public void run() {
                ExpressionIndex++;
                if( ExpressionIndex == 27)
                    ExpressionIndex = 0;
                mRobotAPI.robot.setExpression(RobotFace.getRobotFace(ExpressionIndex));
                mRobotAPI.robot.speak(numbers[ExpressionIndex]);
            }
        };

        timer_change_facial_expressions.schedule(task_change_facial_expression, delay, period);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(PERMISSION_CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(PERMISSION_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(PERMISSION_RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(PERMISSION_CAMERA) ||
                    shouldShowRequestPermissionRationale(PERMISSION_STORAGE) ||
                    shouldShowRequestPermissionRationale(PERMISSION_RECORD_AUDIO)) {
                Toast.makeText(this, "Camera AND storage permission are required for this demo", Toast.LENGTH_LONG).show();
            }
            requestPermissions(new String[]{PERMISSION_CAMERA, PERMISSION_STORAGE, PERMISSION_RECORD_AUDIO}, PERMISSIONS_REQUEST);
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }
}
