package com.example.smsv1;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import static com.example.smsv1.GlobalApplication.CHANNEL_1_ID;
import static com.example.smsv1.GlobalApplication.getAppContext;

public class ThongBaoActivity extends Activity {
    static int count;
    NotificationManager notificationManager;
    CountDownTimer countDownTimer;
    String strThongBao, strSender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
//                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
//                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);
        count+=1;
        Log.d("STTT","onCreate" + count);
        final TextView textView_TieuDe = (TextView)findViewById(R.id.textView_TieuDe);
        TextView textView_ThongBao = findViewById(R.id.textView_ThongBao);
        Button btnBack = (Button)findViewById(R.id.btnBack);



        Intent intent = getIntent();

        strSender = intent.getStringExtra("SENDER") + " SL man hinh " + count;
        strThongBao = intent.getStringExtra("THONGBAO");
        textView_TieuDe.setText(strSender);
      //  textView_ThongBao.setText(strThongBao);
        textView_ThongBao.setText(strThongBao);

        btnBack.setOnClickListener(new View.OnClickListener() {
    @Override
         public void onClick(View v) {
//        Intent intent=new Intent(ThongBaoActivity.this,MainActivity.class);
//        count +=1;
//        startActivity(intent);
      finish();
          }
  });





    }

    @Override
    protected void onStop() {
        Log.d("STTT","onStop" + count);
        super.onStop();
    }

    @Override
    protected void onResume() {
        Log.d("STTT","onResume" + count);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("STTT","onPause" + count );
        countDownTimer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                finish();
            }

        }.start();
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
        //count-= 1;
        super.onDestroy();
    }

}
