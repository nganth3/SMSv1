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
public class ThongBao {
    private final String strPhone, strMessage;
    private String strAmBao, stSP, strTextBao;
    private int intKhuvuc =4;
    private int Status =1;
    private static int count;
    private static boolean SpKing = false;
    final Context context = GlobalApplication.getAppContext();
    DatabaseAccess databaseAccess =DatabaseAccess.getInstance(context);
    CountDownTimer countDownTimer;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    TextToSpeech textToSpeech;

    public ThongBao(String strPhone, String strMessage) {
        this.strPhone = strPhone;
        this.strMessage = strMessage;
    }
    final int fn=15000;

    public String getStrTextBao() {
        strTextBao=strMessage;
        return strTextBao;
    }

    public void ShowThongBao(){
        Calendar calendar = getInstance();
        final String time = simpleDateFormat.format(calendar.getTime());
        databaseAccess.open();
    //   databaseAccess.insertSMS(strPhone,intKhuvuc,strAmBao,strTextBao,time,Status);
     //  databaseAccess.insertSMS();
        String textambao = databaseAccess.getTinnhan("CSKHVIETTEL");


        Log.d("getambao",""+textambao);

        databaseAccess.close();


        Show_ThongBao(time);
        SpeedThongBao();
        countDownTimer = new CountDownTimer(fn, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("CCCC", "" + textToSpeech.isSpeaking());

            }

            @Override
            public void onFinish() {
//                Show_ThongBao(time);
//                SpeedThongBao();


            }


        }.start();

    }

    private void SpeedThongBao(){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status!= TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.speak(strAmBao,TextToSpeech.QUEUE_ADD,null);
                    Toast.makeText(context , strAmBao + "\n" +getStrTextBao() , Toast.LENGTH_LONG).show();

                }


            }


        });

    }
    private void Show_ThongBao(String time){

        Intent intent=new Intent(context,ThongBaoActivity.class);
        count +=1;
        Log.d("XXXXX",""+count);
        intent.putExtra("SENDER","STB: " + strPhone + "   " + time + " SLTB " + count );
        intent.putExtra("THONGBAO",getStrAmBao() +"\n" + getStrTextBao());
       intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);


    }
    public String getStrAmBao() {

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
            default:
                strAmBao ="Có tin nhắn";
        }
        return strAmBao;
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
