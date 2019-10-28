package com.example.smsv1;

import android.app.Activity;
import android.app.Notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.speech.tts.TextToSpeech;
import android.util .Log;
import android.widget.ListView;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Switch;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import static android.content.Context.POWER_SERVICE;
import static com.example.smsv1.GlobalApplication.CHANNEL_1_ID;


public class ThongBao extends Activity {
    SharedPreferences sharedPreferences;
    private NotificationManagerCompat notificationManager;
    private String phoneNum, msgBody;
    TinNhan tinnhan;
    public static ArrayList<TinNhan> arrayListTinNhan = new ArrayList<TinNhan>();
    final int fn=15000;
    private static int count;
    final Context context = GlobalApplication.getAppContext();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    TextToSpeech textToSpeech;


    public ThongBao(String phoneNum, String msgBody) {
        this.phoneNum = phoneNum;
        this.msgBody = msgBody;
        tinnhan = new TinNhan(phoneNum,msgBody);

        Log.d("AAAAAA",""+ arrayListTinNhan.size());
    }



    public void ShowThongBao(){

        count+=1;

        sharedPreferences=context.getSharedPreferences("SHARED_PREFERENCES_SETUP", Context.MODE_PRIVATE);
        Boolean blnThongBao = false;
        String strDauso = sharedPreferences.getString("DAUSO","");
        if(sharedPreferences.getBoolean("NHANTATCA",true)){
            blnThongBao = true;
        }else{

            if(strDauso.indexOf(tinnhan.getStrPhone())>=0){
                blnThongBao = true;
            }
        }


        Log.d("DDDD",""+ blnThongBao+ "    "+ strDauso);
        if(blnThongBao) {

            blnThongBao=false;
            Integer a = tinnhan.getIntKV();
            switch (tinnhan.getIntKV()) {
                case 1:
                    if (sharedPreferences.getBoolean("KV1", true)) {
                        blnThongBao = true;
                    }
                    break;
                case 2:
                    if (sharedPreferences.getBoolean("KV2", true)) {
                        blnThongBao = true;
                    }
                    break;
                case 3:
                    if (sharedPreferences.getBoolean("KV3", true)) {
                        blnThongBao = true;
                    }
                    break;
                case 4:
                    blnThongBao = true;
                    break;
                case 5:
                    blnThongBao = true;
                    break;
            }
        }

        Log.d("TTTTBBBB",""+tinnhan.getIntKV()+"   " +tinnhan.getStrPhone()+ "   "+ blnThongBao +"    " + sharedPreferences.getBoolean("KV3",true));
        if (blnThongBao){
            PowerManager powerManager = (PowerManager)context.getSystemService(POWER_SERVICE);
            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, powerManager.toString());
            wakeLock.acquire(20000);

            arrayListTinNhan.add(0,tinnhan);
            if(sharedPreferences.getBoolean("Speech",true)){
                speechThongBao();
            }
            if(sharedPreferences.getBoolean("List",true)){
                createListThongbao();
            }
            if(sharedPreferences.getBoolean("Notify",true)){
                createNotification();
            }
            if(sharedPreferences.getBoolean("Screen",true)){
                createThongBao();
            }
        }

    }

    public void createNotification(){

        notificationManager = NotificationManagerCompat.from(context);
//        Intent intent = new Intent(context, ThongBao.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        RemoteViews layoutnotify = new RemoteViews(context.getPackageName(),R.layout.layout_notify);
        layoutnotify.setTextViewText(R.id.textView_tittle,tinnhan.getStrPhone());
        layoutnotify.setTextViewText(R.id.textView_meseage,tinnhan.getStrMessage());

        Notification notificationCompat = new NotificationCompat.Builder(context,  CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(tinnhan.getStrAmBao())
                .setCustomContentView(layoutnotify)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setTimeoutAfter(40000)
              .build();
        notificationManager.notify(count,notificationCompat );

    }
    private void speechThongBao(){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status!= TextToSpeech.ERROR){
                   // textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.setLanguage(new Locale("vi_VN"));
                    textToSpeech.speak(tinnhan.getStrAmBao(), TextToSpeech.QUEUE_ADD, null);
                }}


        });

    }
    private void createThongBao(){

        Intent intent=new Intent(context,ThongBaoActivity.class);
        count +=1;
        Log.d("XXXXX",""+count);
        intent.putExtra("SENDER","STB: " + tinnhan.getStrPhone() + "   " );
        intent.putExtra("THONGBAO",tinnhan.getStrMessage());
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    private void createListThongbao(){

       Intent cartIntent = new Intent(context, ListThongBao.class);
       cartIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
       context.startActivity(cartIntent);
    }


}
