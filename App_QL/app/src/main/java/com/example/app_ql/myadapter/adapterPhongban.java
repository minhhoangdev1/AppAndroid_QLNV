package com.example.app_ql.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.app_ql.ActivityPhongban;
import com.example.app_ql.R;
import com.example.app_ql.mymodel.phongban;

import java.util.ArrayList;

public class adapterPhongban extends BaseAdapter {
    private ActivityPhongban context;
    private ArrayList<phongban> ArrayListPhongban;

    public adapterPhongban(ActivityPhongban context, ArrayList<phongban> arrayListPhongban) {
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
        convertView=inflater.inflate(R.layout.listviewphongban,null);
        TextView txtpb=convertView.findViewById(R.id.txtpb);
        ImageButton imgUpdatePb=convertView.findViewById(R.id.UpdatePb);
        ImageButton imgDeletePb=convertView.findViewById(R.id.DeletePb);
        phongban pb=ArrayListPhongban.get(position);
        txtpb.setText(pb.getTen_pb());
        int id=pb.getId_pb();

        //click icon delete
        imgUpdatePb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gọi hàm xóa dữ liệu
                context.update(id);
            }
        });
        //click icon update
        imgDeletePb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.delete(id);
            }
        });

        return convertView;
    }
}
