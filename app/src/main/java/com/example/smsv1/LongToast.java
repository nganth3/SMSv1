package com.example.smsv1;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smsv1.R;

import java.sql.Time;
import java.util.Calendar;

public class LongToast {
    static void makeLongToast(Context context, final String text, long durationInMillis)
    {
        final long durationInMillis2;
        final Toast toastMessage = new Toast(context);
        final Calendar c = Calendar.getInstance();
        //Creating TextView.
        final TextView textView = new TextView(context);

        //Setting up Text Color.
        textView.setTextColor(Color.parseColor("#fafafa"));

        //Setting up Text Size.
        textView.setTextSize(17);

        //Setting up Toast Message Text.


        //Add padding to Toast message.
        textView.setPadding(20, 20, 20, 23);

        //Add Gravity TextView.
        textView.setGravity(Gravity.CENTER);

        //Adding TextView into Toast.


        //Access toast message as View.
        textView.setText(text);
        toastMessage.setView(textView);
        View toastView = toastMessage.getView();
        toastView.setBackgroundResource(R.drawable.test);
       // textView.setText(text);
       // toastMessage.setView(textView);



        new CountDownTimer(durationInMillis, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                Log.d("DDD","" + c.getTime());
                toastMessage.show();
            }
            public void onFinish()
            {
                toastMessage.cancel();
                final Calendar d = Calendar.getInstance();

            }
         }.start();

        durationInMillis+= durationInMillis;
    }


    }
