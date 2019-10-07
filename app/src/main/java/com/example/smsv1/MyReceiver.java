package com.example.smsv1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.POWER_SERVICE;

public class MyReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED ="android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG="SmsBroadcastReceiver";
    String msg, phoneNo ="";
    TextToSpeech ttsPeech;
    Boolean wstThongBao2;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {


        SharedPreferences sharedPreferences;
        sharedPreferences=context.getSharedPreferences("SHARED_PREFERENCES_SETUP", Context.MODE_PRIVATE);
        wstThongBao2 = sharedPreferences.getBoolean("ThongBao",false);

        if (wstThongBao2) {


            PowerManager powerManager = (PowerManager) context.getSystemService(POWER_SERVICE);
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
            wakeLock.acquire(1000);
            //Un looc thiet bi
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
            keyguardLock.reenableKeyguard();
            keyguardLock.disableKeyguard();



            Log.i(TAG, "Intent Received:     " + intent.getAction());
            if (intent.getAction() == SMS_RECEIVED) {
                Bundle dataBundle = intent.getExtras();
                if (dataBundle != null) {

                    Object[] mypdu = (Object[]) dataBundle.get("pdus");

                    final SmsMessage[] messages = new SmsMessage[mypdu.length];


                    for (int i = 0; i < mypdu.length; i++) {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                            String fortmat = dataBundle.getString("format");
                            messages[i] = SmsMessage.createFromPdu((byte[]) mypdu[i], fortmat);

                        } else {
                            messages[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);
                        }

                        msg = "";
                        for (int j = 0; j <= i; j++) {
                            msg = msg + messages[j].getMessageBody();
                        }
                        phoneNo = messages[i].getOriginatingAddress();
                    }
//                Toast.makeText(context, "MSG" + msg + "\nPhone:" + phoneNo, Toast.LENGTH_LONG).show();
                }


            }
            ThongBao thongBao = new ThongBao(phoneNo, msg);
            thongBao.ShowThongBao();


        }

    }


}
