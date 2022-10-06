package com.example.app_ql.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app_ql.ActivityLapHopDong;
import com.example.app_ql.R;
import com.example.app_ql.mymodel.phongban;

import java.util.ArrayList;

public class adapterSpinnerPhongban extends BaseAdapter {
    private ActivityLapHopDong context;
    private ArrayList<phongban> ArrayListPhongban;

    public adapterSpinnerPhongban(ActivityLapHopDong context, ArrayList<phongban> arrayListPhongban) {
        this.context = context;
        ArrayListPhongban = arrayListPhongban;
    }

    @Override
    public int getCount() {
        return ArrayListPhongban.size();
    }

    @Override
    public Object getItem(int position) {
        return ArrayListPhongban.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.list_spinner_phongban,null);
        TextView txtSpinnerTenPb=convertView.findViewById(R.id.txtSpinnerTenPb);
        phongban pb=ArrayListPhongban.get(position);
        txtSpinnerTenPb.setText(pb.getTen_pb());

        return convertView;
    }
}
