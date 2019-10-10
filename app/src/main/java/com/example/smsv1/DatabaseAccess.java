package com.example.smsv1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    public static DatabaseAccess instance;
    Cursor c = null;
    private DatabaseAccess(Context context){
        this.openHelper = new MyDatabase(context) {
        };
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance==null){
            instance = new DatabaseAccess(context);
        }
        return instance;

    }
    public void open(){
        this.database = openHelper.getWritableDatabase();
    }
    public void close(){
        if(database!=null){
            database.close();
        }
    }

    public String getTentinh(String matinh){

        c=database.rawQuery("select TenTinh from MaTinh where MaTinh ='" + matinh +"'",new String[]{});
        Log.d("yeucau","DDD");
        StringBuffer stringBuffer = new StringBuffer();
        while (c.moveToNext()){
            String tentinh =c.getString(0);
            stringBuffer.append(""+tentinh);
        }
        return stringBuffer.toString();
    }

    public void insertMaTinh(){

        ContentValues values= new ContentValues();
        values.put("MaLoi","xxx" );
        values.put("TenLoi","III" );


        // values.put("Status",Status);
        //  database.

        long createSuccessful = database.insert("MaLoi", null, values);
        Log.d("KQIII",""+createSuccessful);

        // database.wr


    }


    public void insertSMS(String Sender,String Khuvuc,String AmBao,String TextBao,String Time,String Status){

        ContentValues values= new ContentValues();
        values.put("Sender",Sender );

        values.put("Status",Status);
        values.put("AmBao",AmBao);
        values.put("Time",Time);
        values.put("TextBao",TextBao);
        values.put("KhuVuc",Khuvuc );
        values.put("Status",Status);



       long createSuccessful = database.insert("TinNhan", null,values);

     //   long createSuccessful = database.insert("TestTable", null,values);
        Log.d("KQIII",""+createSuccessful);

       // database.wr


    }
    public String getTinnhan(String sender){
        c=database.rawQuery("select AmBao from TinNhan where Sender ='" + sender +"'",new String[]{});
        Log.d("yeucau","DDDxxx");
        StringBuffer stringBuffer = new StringBuffer();
        while (c.moveToNext()){
            String AmBao =c.getString( 0);
            stringBuffer.append("//"+AmBao);
        }
        return stringBuffer.toString();
    }
    public String getLoi(String maloi){
        c=database.rawQuery("select TenLoi from MaLoi where MaLoi ='" + maloi +"'",new String[]{});
        Log.d("yeucau","DDD");
        StringBuffer stringBuffer = new StringBuffer();
        while (c.moveToNext()){
            String tentinh =c.getString(0);
            stringBuffer.append(""+tentinh);
        }
        return stringBuffer.toString();
    }
}
