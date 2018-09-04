package com.example.sriambikasstockmgmt;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission_group.CAMERA;

//used the below tutorial
// https://www.youtube.com/watch?v=otkz5Cwdw38//

public class BarcodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    static final int REQUEST_CAMERA = 1;

    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
      setContentView(scannerView);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if(checkbarPermission()) {
                Toast.makeText(BarcodeActivity.this,"permission granted",Toast.LENGTH_LONG).show();
            }
            else {

                requestbarPermissions();
            }
        }
    }

    @Override
    public void handleResult(Result result) {
        String scanResult = result.getText();
        Log.d("Result of the scan", scanResult);
    }

    private void requestbarPermissions() {
        ActivityCompat.requestPermissions(this,new String[]{CAMERA},REQUEST_CAMERA);
    }

    private boolean checkbarPermission() {

        return (ContextCompat.checkSelfPermission(BarcodeActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED);
    }

    public void onRequestbarPermission(int requestcode, String permission[],  int  grantResults[]) {

        switch (requestcode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted) {
                        Toast.makeText(BarcodeActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(BarcodeActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(CAMERA)) {
                            displayalertmessagedial("you need to give access to both the permission", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    // Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                    }
                                }
                            });
                            return;
                        }
                    }

                }
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {

            if(checkbarPermission())
            {
                if(scannerView==null){
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
            else {
                requestbarPermissions();
            }
        }
    }

    public void displayalertmessagedial(String message, DialogInterface.OnClickListener listener)
    {
        new AlertDialog.Builder(BarcodeActivity.this).setMessage(message).setPositiveButton("OK",listener).setNegativeButton("Cancel", null).create().show();


    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }
}
