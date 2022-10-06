package com.example.app_ql.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.app_ql.ActivityChucvu;
import com.example.app_ql.R;
import com.example.app_ql.mymodel.chucvu;

import java.util.ArrayList;

public class adapterchucvu extends BaseAdapter {
    private ActivityChucvu context;
    private ArrayList<chucvu> ArrayListChucvu;

    public adapterchucvu(ActivityChucvu context, ArrayList<chucvu> arrayListChucvu) {
        this.context = context;
        ArrayListChucvu = arrayListChucvu;
    }

    @Override
    public int getCount() {
        return ArrayListChucvu.size();
    }

    @Override
    public Object getItem(int position) {
        return ArrayListChucvu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.listviewchucvu,null);
        TextView txtChucvu=convertView.findViewById(R.id.txtChucvu);
        ImageButton imgUpdateChucvu=convertView.findViewById(R.id.UpdateChucvu);
        ImageButton imgDeleteChucvu=convertView.findViewById(R.id.DelateChucvu);
        chucvu chucvu=ArrayListChucvu.get(position);
        txtChucvu.setText(chucvu.getTen_cv());
        int id_cv=chucvu.getId_cv();

        //click icon delete
        imgDeleteChucvu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gọi hàm xóa dữ liệu
                context.delete(id_cv);
            }
        });
        //click icon update
        imgUpdateChucvu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.update(id_cv);
            }
        });
        return convertView;
    }
}
