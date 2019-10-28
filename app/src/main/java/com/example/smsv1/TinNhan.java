package com.example.smsv1;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TinNhan {
    public void setStrPhone(String strPhone) {
        this.strPhone = strPhone;
    }

    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }

    private String strPhone, strMessage;
    private String strAmBao, strTextBao,strTime;
    private int intKV =4,intSTT;
    private static int count =0;
    final Context context = GlobalApplication.getAppContext();
    DatabaseAccess databaseAccess =DatabaseAccess.getInstance(context);


    public TinNhan(String strPhone, String strMessage) {
        this.strPhone = strPhone;
        this.strMessage = strMessage;
        this.setStrAmBao();
        this.setStrTextBao();
        this.setStrTime();
        this.count +=1;
        this.intSTT = count;
    }



    public String getStrPhone() {
        return strPhone;
    }
    public String getStrMessage() {
        return strMessage;
    }
    public String getStrAmBao() {
        return strAmBao;
    }
    public String getStrTextBao() {
        return strTextBao;
    }

    public int getIntKV() {
        return intKV;
    }
    public int getIntSTT() {
        return intSTT;
    }


    public int getCount() {
        return count;
    }


    public String getStrTime() {
        return strTime;
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
        this.strTextBao = this.strAmBao;
    }


    public void setStrTime() {

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:MM:SS");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        this.strTime =  mdformat.format(calendar.getTime());}
        else {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss",
                    Locale.ENGLISH);
            String var = dateFormat.format(date);
            this.strTime = var;}




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

            strTam += arrTram.length +" Trạm tại: ";
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
            setintKV(Khuvuc);
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
    private void setintKV(String KV) {
        Log.d("KKKKK",""+ intKV);
        if(intKV<5) {
            if (intKV < 4) {
                if (intKV != Integer.parseInt(KV)) {
                    this.intKV = 5;
                }
            } else {
                this.intKV = Integer.parseInt(KV);
            }
            Log.d("KKKKKEEE", "" + intKV);
        }

    }




}
