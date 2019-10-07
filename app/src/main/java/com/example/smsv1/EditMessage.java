package com.example.smsv1;

public class EditMessage {

    private static String strPhone, strMessage, strAmBao, strTextBao;


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
