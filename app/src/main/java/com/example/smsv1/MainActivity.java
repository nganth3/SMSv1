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
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS =0 ;
    public static Boolean blnThongBao;
    Switch wstThongBao;
    public static SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wstThongBao = (Switch) findViewById(R.id.swtThongbao);


        sharedPreferences = getSharedPreferences("SHARED_PREFERENCES_SETUP", Context.MODE_PRIVATE);
        blnThongBao=sharedPreferences.getBoolean("ThongBao",false);


        wstThongBao.setChecked(blnThongBao);
        wstThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blnThongBao = wstThongBao.isChecked();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("ThongBao",blnThongBao);
                editor.apply();
            }
        });





        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MainActivity.this,ThongBaoActivity.class);
                startActivity(i);
            }
        });



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
