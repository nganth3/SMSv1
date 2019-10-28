package com.example.smsv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static SharedPreferences sharedPreferences;
    private static int count;
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS =0 ;
    Switch wstThongBao, wstNotify, wstSreen, wstList, wstSpeech;
    EditText edtDauSo;
    CheckBox ckbKV1, ckbKV2, ckbKV3;
    RadioButton rbtnTatCa,rbtnTheoDauSo;
    RadioGroup radioGroup;
    Button btnSave,btnTTSSetting;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        checkPermission();
        sharedPreferences = getSharedPreferences("SHARED_PREFERENCES_SETUP", Context.MODE_PRIVATE);
        Log.d("MAINNNN","Main_onCreate");
        anhXa();
        setValue();
        wstThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveValue();
                if (wstThongBao.isChecked() == false) {
                    finish();
                    Toast.makeText(MainActivity.this,"ĐÃ TẮT THÔNG BÁO",Toast.LENGTH_LONG).show();
                }
                ;
            }
        });
        wstSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wstSpeech.isChecked()){
                    btnTTSSetting.setVisibility(View.VISIBLE);
                }else{
                    btnTTSSetting.setVisibility(View.GONE);

                }


            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbtNhanTatCa){
                    edtDauSo.setVisibility(View.INVISIBLE);
                }else{
                    edtDauSo.setVisibility(View.VISIBLE);
                }


            }
        });

        btnTTSSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Build.VERSION.SDK_INT >= 14?
                        new Intent().setAction("com.android.settings.TTS_SETTINGS").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) :
                        new Intent().addCategory(Intent.CATEGORY_LAUNCHER).setComponent(new ComponentName("com.android.settings", "com.android.settings.TextToSpeechSettings")).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveValue();
                checkPermissionAgain();
            }
        });
    }

    private void anhXa() {
        wstThongBao = findViewById(R.id.swtThongbao);
        wstSreen = findViewById(R.id.switch_activity);
        wstNotify = findViewById(R.id.switch_notify);
        wstList = findViewById(R.id.switch_List);
        wstSpeech = findViewById(R.id.switch_Speech);
        ckbKV1 = findViewById(R.id.checkBoxKV1);
        ckbKV2 = findViewById(R.id.checkBoxKV2);
        ckbKV3 = findViewById(R.id.checkBoxKV3);
        btnSave = findViewById(R.id.button);
        radioGroup=findViewById(R.id.radioGroup2);

        rbtnTatCa = findViewById(R.id.rbtNhanTatCa);
        rbtnTheoDauSo = findViewById(R.id.rbtTheoDauSo);
        btnTTSSetting=findViewById(R.id.bution_TTSSetting);
        edtDauSo =findViewById(R.id.editTextDauso);

        if(wstSpeech.isChecked()){
            btnTTSSetting.setVisibility(View.VISIBLE);
        }else{
            btnTTSSetting.setVisibility(View.GONE);

        }

    }

    private void setValue() {
        wstThongBao.setChecked(sharedPreferences.getBoolean("ThongBao", true));
        wstNotify.setChecked(sharedPreferences.getBoolean("Notify", false));
        wstSreen.setChecked(sharedPreferences.getBoolean("Screen", false));
        wstList.setChecked(sharedPreferences.getBoolean("List", true));
        wstSpeech.setChecked(sharedPreferences.getBoolean("Speech", true));


        ckbKV1.setChecked(sharedPreferences.getBoolean("KV1", true));
        ckbKV2.setChecked(sharedPreferences.getBoolean("KV2", true));
        ckbKV3.setChecked(sharedPreferences.getBoolean("KV3", true));
        edtDauSo.setText(sharedPreferences.getString("DAUSO","CSKHVIETTEL,171,198"));
        if(wstSpeech.isChecked()){btnTTSSetting.setVisibility(View.VISIBLE);}else{btnTTSSetting.setVisibility(View.INVISIBLE);}

        if(sharedPreferences.getBoolean("NHANTATCA", true)){
            rbtnTatCa.setChecked(true);
            edtDauSo.setVisibility(View.INVISIBLE);
        }else{
            rbtnTheoDauSo.setChecked(true);
            edtDauSo.setVisibility(View.VISIBLE);
        }


    }

    private void saveValue() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("ThongBao", wstThongBao.isChecked());
        editor.putBoolean("Notify", wstNotify.isChecked());
        editor.putBoolean("Screen", wstSreen.isChecked());
        editor.putBoolean("List", wstList.isChecked());
        editor.putBoolean("Speech", wstSpeech.isChecked());

        editor.putBoolean("KV1", ckbKV1.isChecked());
        editor.putBoolean("KV2", ckbKV2.isChecked());
        editor.putBoolean("KV3", ckbKV3.isChecked());
        editor.putBoolean("NHANTATCA",rbtnTatCa.isChecked());
        editor.putString("DAUSO",edtDauSo.getText().toString());
        editor.apply();
    }



    @Override
    protected void onResume() {
        super.onResume();
    }


    private void checkPermissionAgain(){

       int pms=ActivityCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS);
        String strTT = "";

       if(pms>=0){
           if(wstThongBao.isChecked()){
               strTT="ĐÃ BẬT THÔNG BÁO";
               if (wstList.isChecked()|| wstNotify.isChecked()|| wstSpeech.isChecked()|| wstSreen.isChecked()) {
                   finish();
               }else {
                   strTT="CẦN CHỌN MỘT CHẾ ĐỘ THÔNG BÁO";

               }

           }else {
               finish();
               strTT="ĐÃ TẮT THÔNG BÁO";
           }



       }else {
           strTT="CHƯA CÓ QUYỀN NHẬN TIN NHẮN";

       }


        Toast.makeText(this,strTT,Toast.LENGTH_LONG).show();


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
                    Toast.makeText (this ,"Đã cấp quyền nhận tin nhắn",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText (this ,"Chưa có quyền nhân tin nhắn",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        Log.d("MAINNNN","Main_onRestart");

        super.onRestart();
    }
}
