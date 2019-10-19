package com.example.smsv1;

import android.app.Activity;
import android.app.Notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static com.example.smsv1.GlobalApplication.CHANNEL_1_ID;


public class ThongBao extends Activity {
    SharedPreferences sharedPreferences;
    private NotificationManagerCompat notificationManager;
    private final String strPhone, strMessage;
    private String strAmBao, stSP, strTextBao;
    private Time time;
    private int intKhuvuc =4,Status =1;
    final int fn=15000;
    private static int count;
    final Context context = GlobalApplication.getAppContext();
    DatabaseAccess databaseAccess =DatabaseAccess.getInstance(context);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    TextToSpeech textToSpeech;

    public ThongBao(String strPhone, String strMessage) {
        this.strPhone = strPhone;
        this.strMessage = strMessage;
        this.setStrAmBao();
        this.setStrTextBao();
    }





    public String getStrTextBao() {
        strTextBao=strMessage;
        return strTextBao;
    }

   // @RequiresApi(api = Build.VERSION_CODES.N)
    public void ShowThongBao(){
        count+=1;
        PowerManager powerManager = (PowerManager)context.getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, powerManager.toString());
        wakeLock.acquire(20000);
        sharedPreferences=context.getSharedPreferences("SHARED_PREFERENCES_SETUP", Context.MODE_PRIVATE);
        speechThongBao();
        if(sharedPreferences.getBoolean("Notify",true)){
            createNotification();
        }
        if(sharedPreferences.getBoolean("Screen",true)){
            createThongBao();
        }



   // wakeLock.release();
    }

    public void createNotification(){

        notificationManager = NotificationManagerCompat.from(context);
        Intent intent = new Intent(context, ThongBao.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        RemoteViews layoutnotify = new RemoteViews(context.getPackageName(),R.layout.layout_notify);
        layoutnotify.setTextViewText(R.id.textView_tittle,strPhone);
        layoutnotify.setTextViewText(R.id.textView_meseage,strMessage);

        Notification notificationCompat = new NotificationCompat.Builder(context,  CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(strPhone)
                .setCustomContentView(layoutnotify)
                .setTimeoutAfter(40000)
              .build();
        notificationManager.notify(count,notificationCompat );

    }
    private void speechThongBao(){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status!= TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.speak(strAmBao,TextToSpeech.QUEUE_ADD,null);
                 //   Toast.makeText(context , strAmBao + "\n" +getStrTextBao() , Toast.LENGTH_LONG).show();
                }           }


        });

    }
    private void createThongBao(){

        Intent intent=new Intent(context,ThongBaoActivity.class);
        count +=1;
        Log.d("XXXXX",""+count);
       intent.putExtra("SENDER","STB: " + strPhone + "   " + time + " SLTB " + count );
        intent.putExtra("THONGBAO",strAmBao +"\n" + getStrTextBao());
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    private void setStrAmBao() {
        switch (strPhone){
            case "171":
                strAmBao =am171();
                break;
            case "CSKHVIETTEL":
                strAmBao =amCSKH();
                break;
            case "198":
                strAmBao="một chín tám";
                break;
            case "VOFFICE":
                strAmBao="Có văn bản";
                break;
            default:
                strAmBao ="Có tin nhắn";
        }
    }
    private void setStrTextBao(){
        this.strTextBao = this.strMessage;
    }
    private void setIntKhuvuc(String intKhuvuc) {
        this.intKhuvuc = Integer.parseInt(intKhuvuc);
    }

    private String amCSKH(){

        String strTam, strTam2;

        switch (strMessage.substring(0,4)){
            case "Tram":
                strTam="Cảnh báo trạm " + getTinh(strMessage.substring(5,8))+ " ";
                strTam+= converNumtoStr(strMessage.substring(8,12));
                strTam+= ": Có " + strMessage.substring(strMessage.indexOf(": Co ")+5,strMessage.indexOf(" phan anh")).trim() + " phản ánh ";

                break;
            case "Ten ":
                strTam="Cảnh báo ";
                strTam+= getTenLoi(strMessage.substring(0,strMessage.indexOf(":")+1).trim());
                strTam+= ": Có " + strMessage.substring(strMessage.indexOf(": Co ")+5,strMessage.indexOf(" phan anh")).trim() + " phản ánh ";
                break;
            default:strTam="Cảnh báo lỗi khác ";
                strTam+= ": Có " + strMessage.substring(strMessage.indexOf(" Co ")+4,strMessage.indexOf(" phan anh")).trim() + " phản ánh ";

        }

        return strTam;

    }
    private String getTenLoi(String maloi){
        databaseAccess.open();
        String tenLoi = databaseAccess.getLoi(maloi);
        if(tenLoi.isEmpty()){ tenLoi = "Lỗi Khác";
        }
        databaseAccess.close();
        return tenLoi+=" ";
    }
    private String am171(){
        String strTam, strTam2;
        if (strMessage.indexOf("ke hoach")>0){
            strTam ="Có Kế Hoạch ";
        }else{
            strTam ="Có Sự Cố ";
        }
        strTam2 =strMessage.substring(strMessage.indexOf(":") +1,strMessage.indexOf("luc") -2).trim();


        if (strTam2.length()<9){
            strTam += "Trạm ";
            strTam += getTinh(strTam2.substring(0,3));
            strTam += converNumtoStr(strTam2.substring(3)) ;
        }else{
            String[] arrTram,arrTram2;
            String tinhTam="";
            arrTram = strTam2.split(",");

            strTam += arrTram.length +" Trạm tại các Tỉnh: ";
            for(int i=0;i<arrTram.length;i++){
               if(!tinhTam.contains(arrTram[i].substring(0,3))) {
                   tinhTam+=arrTram[i].substring(0,3).trim()+",";
               }
            }
            tinhTam=tinhTam.substring(0,tinhTam.length()-1);
            Log.d("xxxxxx",tinhTam);

            arrTram=tinhTam.split(",");

            for(int i = 0; i< arrTram.length;i++){
                strTam += getTinh(arrTram[i]).trim()+", " ;
            }
            strTam = strTam.substring(0,strTam.length()-2);
        }
        return strTam ;
    }
    private String getTinh(String matinh){

        databaseAccess.open();
        String tentinh = databaseAccess.getTentinh(matinh);
        String Khuvuc = databaseAccess.getKhuVuc(matinh);
        if (Khuvuc!=null){
            setIntKhuvuc(Khuvuc);
        }
        databaseAccess.close();

        return tentinh+=" " ;


    }
    private String converNumtoStr(String Num){
        String strNum ="";
        for(int i = 0; i <Num.length();i++){

            switch (Num.charAt(i)){
                case '0':strNum+= "không ";break;
                case '1':strNum+= "một ";break;
                case '2':strNum+= "hai ";break;
                case '3':strNum+= "ba ";break;
                case '4':strNum+= "bốn ";break;
                case '5':strNum+= "năm ";break;
                case '6':strNum+= "sáu ";break;
                case '7':strNum+= "bảy ";break;
                case '8':strNum+= "tám ";break;
                case '9':strNum+= "chín ";break;
                default: strNum+= "";
            }


        }


        return strNum;
    }



}
