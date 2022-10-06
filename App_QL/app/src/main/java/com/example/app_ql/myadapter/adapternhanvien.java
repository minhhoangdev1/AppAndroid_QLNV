package com.example.app_ql.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.app_ql.ActivityNhanvien;
import com.example.app_ql.R;
import com.example.app_ql.mymodel.nhanvien;

import java.util.ArrayList;
import java.util.List;

public class adapternhanvien extends BaseAdapter implements Filterable {

    private ActivityNhanvien context;
    private ArrayList<nhanvien> ArrayListNhanvien;
    private ArrayList<nhanvien> ArrayListNhanvienFilter;
    private ValueFilter valueFilter;

    public adapternhanvien(ActivityNhanvien context, ArrayList<nhanvien> arrayListNhanvien) {
        this.context = context;
        ArrayListNhanvien = arrayListNhanvien;
        ArrayListNhanvienFilter=arrayListNhanvien;
    }



    @Override
    public int getCount() {
        return ArrayListNhanvien.size();
    }

    @Override
    public Object getItem(int position) {
        return ArrayListNhanvien.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView=layoutInflater.inflate(R.layout.listviewnhanvien,null);

        TextView txtTennv=convertView.findViewById(R.id.txtTennv);
        TextView txtGoitinhnv=convertView.findViewById(R.id.txtGioitinhnv);
        ImageButton imgInfoNhanvien=convertView.findViewById(R.id.InfoNhanvien);
        ImageButton imgUpdateNhanvien=convertView.findViewById(R.id.Updatenhanvien);
        ImageButton imgDeleteNhanvien=convertView.findViewById(R.id.DeleteNhanvien);
        ImageButton imgHDNhanvien=convertView.findViewById(R.id.XemHopdongnv);

        nhanvien nhanvien=ArrayListNhanvien.get(position);
        txtTennv.setText(nhanvien.getTen_nv());
        txtGoitinhnv.setText(nhanvien.getGioitinh());

        int id=nhanvien.getId_nv();

        //click icon info
        imgInfoNhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.info(id);
            }
        });
        //click icon delete
        imgUpdateNhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.update(id);
            }
        });
        //click icon insert
        imgDeleteNhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.delete(id);
            }
        });
        imgHDNhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.show_hd(id);
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(valueFilter==null) {

            valueFilter=new ValueFilter();
        }

        return valueFilter;
    }
    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<nhanvien> filterList=new ArrayList<nhanvien>();
                for(int i=0;i<ArrayListNhanvienFilter.size();i++){
                    if((ArrayListNhanvienFilter.get(i).getTen_nv().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        nhanvien nv = new nhanvien();
                        nv.setTen_nv(ArrayListNhanvienFilter.get(i).getTen_nv());
                        nv.setId_nv(ArrayListNhanvienFilter.get(i).getId_nv());
                        filterList.add(nv);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=ArrayListNhanvienFilter.size();
                results.values=ArrayListNhanvienFilter;
            }
            return results;
        }


        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            ArrayListNhanvien=(ArrayList<nhanvien>) results.values;
            notifyDataSetChanged();
        }
    }
}
