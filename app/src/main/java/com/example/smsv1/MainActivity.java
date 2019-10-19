package com.example.smsv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int count;
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS =0 ;
    public static Boolean blnThongBao;
    Switch wstThongBao,wstNotify,wstSreen;
    CheckBox ckbKV1,ckbKV2,ckbKV3;
    Button btnSave;
    public static SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count+=1;

        anhXa();
        checkPermission();
        setValue();

        wstThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveValue();
                 if (wstThongBao.isChecked()==false){
                  finish();
                 };            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveValue();
                finish();
                Toast.makeText(MainActivity.this, "SAVE VALUE",Toast.LENGTH_LONG).show();

            }
        });
    }


    private void anhXa(){
        sharedPreferences = getSharedPreferences("SHARED_PREFERENCES_SETUP", Context.MODE_PRIVATE);
        wstThongBao = (Switch) findViewById(R.id.swtThongbao);
        wstSreen= findViewById(R.id.switch_activity);
        wstNotify =findViewById(R.id.switch_notify);
        ckbKV1=findViewById(R.id.checkBoxKV1);
        ckbKV2=findViewById(R.id.checkBoxKV2);
        ckbKV3=findViewById(R.id.checkBoxKV3);
        btnSave =findViewById(R.id.button);
    }
    private void setValue(){
        wstThongBao.setChecked(sharedPreferences.getBoolean("ThongBao",false));
        wstNotify.setChecked(sharedPreferences.getBoolean("Notify",false));
        wstSreen.setChecked(sharedPreferences.getBoolean("Notify",false));
        ckbKV1.setChecked(sharedPreferences.getBoolean("KV1",false));
        ckbKV2.setChecked(sharedPreferences.getBoolean("KV2",false));
        ckbKV3.setChecked(sharedPreferences.getBoolean("KV3",false));


    }
    private void saveValue(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("ThongBao",wstThongBao.isChecked());
        editor.putBoolean("Notify",wstNotify.isChecked());
        editor.putBoolean("Screen",wstSreen.isChecked());
        editor.putBoolean("KV1",ckbKV1.isChecked());
        editor.putBoolean("KV2",ckbKV2.isChecked());
        editor.putBoolean("KV3",ckbKV3.isChecked());
        editor.apply();
    }

    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions [], int [] grantResults )
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS:
            {
                if(grantResults.length >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText (this ,"Thanks you for permitting",Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText (this ,"kHONG LAM GI CA",Toast.LENGTH_LONG).show();
                }
            }
        }
    }



}
