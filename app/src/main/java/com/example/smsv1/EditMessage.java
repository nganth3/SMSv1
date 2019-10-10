package com.example.smsv1;

import android.content.Context;
import android.util.Log;

public class EditMessage {

    private static String strPhone, strMessage, strAmBao, strTextBao;
    static final Context context = GlobalApplication.getAppContext();

    public EditMessage(String strPhone, String strMessage) {
        this.strPhone = strPhone;
        this.strMessage = strMessage;
    }

    public static String getStrPhone() {
        return strPhone;
    }

    public void setStrPhone(String strPhone) {
        this.strPhone = strPhone;
    }

    public String getStrMessage() {
        return strMessage;
    }

    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }

    public static String getStrAmBao() {
        DatabaseAccess databaseAccess =DatabaseAccess.getInstance(context);
        databaseAccess.open();
        String tentinh = databaseAccess.getTentinh("HNI");

        if(tentinh.isEmpty()){
            Log.d("TTTT","XXX");
        }else{
            Log.d("TTTT",tentinh);
        }
        databaseAccess.close();

        switch (strPhone){
            case "171":
                strAmBao ="co spm";
                break;
            case "CSKHVIETTEL":
                strAmBao ="Canh bao";
                break;
            default:
                strAmBao ="Co tin nhan";
        }


        return strAmBao;
    }

    public void setStrAmBao(String strAmBao) {
        this.strAmBao = strAmBao;
    }

    public static String getStrTextBao() {
        strTextBao = strMessage;
        return strTextBao;
    }


    public void setStrTextBao(String strTextbao) {
        this.strTextBao = strTextbao;
    }



}
