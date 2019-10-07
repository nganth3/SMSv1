package com.example.smsv1;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static android.icu.util.Calendar.getInstance;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ThongBao extends EditMessage {
    private static int count;
    private static boolean SpKing = true;

    TextToSpeech textToSpeech;
    final Context context = GlobalApplication.getAppContext();


    public ThongBao(String strPhone, String strMessage) {
        super(strPhone, strMessage);
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    public void ShowThongBao(){
        Calendar calendar = getInstance();
        final String time = simpleDateFormat.format(calendar.getTime());
        final int fn=5000;
        CountDownTimer countDownTimer = new CountDownTimer(fn,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            if(SpKing){

               Log.d("TTT",""+millisUntilFinished);
            }
            }
            @Override
            public void onFinish() {
                Show_ThongBao(time);
                SpeedThongBao();
            }


        }.start();



    }

    private void SpeedThongBao(){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status!= TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.speak("Có tin nhắn mới đến",TextToSpeech.QUEUE_ADD,null);
                    Toast.makeText(context , ThongBao.getStrAmBao() + "\n" + ThongBao.getStrTextBao(), Toast.LENGTH_LONG*3).show();

                }
            }


        });

    }

    private void Show_ThongBao(String time){
        Log.d("TTIME","xx");
        Intent i=new Intent(context,ThongBaoActivity.class);
        count +=1;
        i.putExtra("SENDER","STB: " + ThongBao.getStrPhone() + "   " + time + " SLTB " + count );
        i.putExtra("THONGBAO",ThongBao.getStrTextBao());
        i.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);


    }

}
