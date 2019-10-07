package com.example.smsv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import java.util.Locale;

public class ThongBaoActivity extends Activity {

    String strThongBao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);
        Log.d("STTT","onCreate");
        final TextView textView = (TextView)findViewById(R.id.textViewthongbao);
        Intent intent = getIntent();
        String strSender = intent.getStringExtra("SENDER");
        strThongBao = intent.getStringExtra("THONGBAO");

        textView.setText(strSender + "\n" + strThongBao);






        CountDownTimer countDownTimer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                finish();
            }

        }.start();

    }


}
