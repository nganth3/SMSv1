package com.example.smsv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ShowableListMenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListThongBao extends AppCompatActivity {
    public ListView listViewThongBao;
    Button btnClear, btnClose;
    SharedPreferences sharedPreferences;// =MainActivity.sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_list_thong_bao);
        super.onCreate(savedInstanceState);
        sharedPreferences=ListThongBao.this.getSharedPreferences("SHARED_PREFERENCES_SETUP", Context.MODE_PRIVATE);
        listViewThongBao = findViewById(R.id.lvThongBao);
/*
        listViewThongBao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("IIIDDD",""+id);
            }
        });

 */
//        listViewThongBao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               // Object itemObject = parent.getAdapter().getItem(position);
//             //   ListViewItemDTO itemDto = (ListViewItemDTO)itemObject;
//                    TextView v = view.findViewById(R.id.textView_TextBao);
//                    Log.d("IIIDDD","" + v.getText());
//
//            }
//        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.layout_menu_thongbao,menu);
        MenuItem OnOff = (MenuItem) menu.findItem(R.id.menu_OnOff1);
        OnOff.setActionView(R.layout.switch_item);
        final Switch sw = (Switch) menu.findItem(R.id.menu_OnOff1).getActionView().findViewById(R.id.switch_item_customs);

        Boolean check = true;
        check = sharedPreferences.getBoolean("ThongBao",false);
        sw.setChecked(check);


        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putBoolean("ThongBao",sw.isChecked());
               editor.apply();
               String str ="ĐÃ TẮT THÔNG BÁO";
               if(sw.isChecked()){
                   str ="ĐÃ BẬT THÔNG BÁO";
               }
               Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), str, Snackbar.LENGTH_LONG);
               snackbar.show();
       }

        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_Clear:
                ThongBao.arrayListTinNhan.clear();
                Show();
                break;
            case R.id.menu_ClearClose:
                ThongBao.arrayListTinNhan.clear();
                finish();
                break;
            case R.id.menu_Close:
                finish();
                break;
            case R.id.menu_Setting:
                Intent intent = new Intent(ListThongBao.this,MainActivity.class);
                startActivity(intent);
                finish();

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onContentChanged() {
        Log.d("MAINNNN","onContentChanged");
        super.onContentChanged();
    }


    @Override
    protected void onRestart() {
        Log.d("MAINNNN","onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d("MAINNNN","onResume");
        Show();
        super.onResume();


    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("MAINNNN","onNewIntent");
        super.onNewIntent(intent);
    }

    public void Show(){
        TinNhanAdapter tinNhanAdapter = new TinNhanAdapter(
                this,
                R.layout.layout_listview,
                ThongBao.arrayListTinNhan
        );
        listViewThongBao.setAdapter(tinNhanAdapter);


    }

}
