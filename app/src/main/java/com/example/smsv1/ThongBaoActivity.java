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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class ThongBaoActivity extends Activity {

    static int count;
    String strThongBao, strSender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
//                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
//                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        super.onCreate(savedInstanceState);
        count+=1;
        setContentView(R.layout.activity_thong_bao);
        Log.d("STTT","onCreate" + count);
        final TextView textView_TieuDe = (TextView)findViewById(R.id.textView_TieuDe);
        TextView textView_ThongBao = findViewById(R.id.textView_ThongBao);
        Button btnBack = (Button)findViewById(R.id.btnBack);



        Intent intent = getIntent();

        strSender = intent.getStringExtra("SENDER") + " SL man hinh " + count;
        strThongBao = intent.getStringExtra("THONGBAO");
        textView_TieuDe.setText(strSender);
      //  textView_ThongBao.setText(strThongBao);
        textView_ThongBao.append(strThongBao);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            finish();
            }
        });


//        CountDownTimer countDownTimer = new CountDownTimer(15000,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//            @Override
//            public void onFinish() {
//               // WindowManager.LayoutParams
//             // finish();
//            }
//
//        }.start();
    }

    @Override
    protected void onStop() {
        Log.d("STTT","onStop" + count);
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d("STTT","onPause" + count );
        super.onPause();

    }

    @Override
    protected void onRestart() {
        Log.d("STTT","onRestart" + count );
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d("STTT","onDestroy" + count );
        count-= 1;
        super.onDestroy();
    }
}
