package com.example.app_ql.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.app_ql.ActivityChucvu;

import com.example.app_ql.ActivityUpdateNhanvien;
import com.example.app_ql.R;
import com.example.app_ql.mymodel.chucvu;


import java.util.ArrayList;

public class adapterSpinner extends BaseAdapter {
    private ActivityUpdateNhanvien context;
    private ArrayList<chucvu> ArrayListChucvu;

    public adapterSpinner(ActivityUpdateNhanvien context, ArrayList<chucvu> arrayListChucvu) {
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
        convertView=inflater.inflate(R.layout.list_spinner,null);
        TextView txtSpinnerTencv=convertView.findViewById(R.id.txtSpinnerTencv);
        chucvu chucvu=ArrayListChucvu.get(position);
        txtSpinnerTencv.setText(chucvu.getTen_cv());

        return convertView;
    }
}
