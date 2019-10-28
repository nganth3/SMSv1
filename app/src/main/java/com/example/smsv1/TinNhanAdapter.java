package com.example.smsv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TinNhanAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<TinNhan> arrTinNhan;

    public TinNhanAdapter(Context context, int layout, List<TinNhan> arrTinNhan) {
        this.context = context;
        this.layout = layout;
        this.arrTinNhan = arrTinNhan;
    }

    @Override
    public int getCount() {
        return arrTinNhan.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.layout_listview,null);

        TextView txtPhone = convertView.findViewById(R.id.textView_Phone);
        TextView txtTextBao = convertView.findViewById(R.id.textView_TextBao);
        TextView txtTime = convertView.findViewById(R.id.textView_Time);
        TextView txtMSG = convertView.findViewById(R.id.textView_Msg);

        txtPhone.setText(arrTinNhan.get(position).getStrPhone());
        txtTextBao.setText("   Tin thứ:  " + arrTinNhan.get(position).getIntSTT()
                + "_KV: " + arrTinNhan.get(position).getIntKV() );
        txtTime.setText(arrTinNhan.get(position).getStrTime());
        txtMSG.setText(arrTinNhan.get(position).getStrMessage());
        return convertView;
    }
}
