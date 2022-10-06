package com.example.app_ql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app_ql.config.ConfigURL;
import com.example.app_ql.myadapter.adapterPhongban;
import com.example.app_ql.myadapter.adapterSpinner;
import com.example.app_ql.myadapter.adapterSpinnerPhongban;
import com.example.app_ql.myhandle.RequestHandler;
import com.example.app_ql.mymodel.chucvu;
import com.example.app_ql.mymodel.phongban;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityLapHopDong extends AppCompatActivity {
    Button btnTaoHD,btnNgayLHD;
    EditText editTenHD,editNoidungHD,editNgayLHD,editThoihan,editNguoiky;
    Toolbar toolbarHopdong;
    Spinner spinner_pb;
    View v;

    String tenHD;
    String noidungHD;
    String ngayLHD;
    String thoihanHD;
    String Nguoiky;

    String ten_nv;
    String gioitinh;
    String dienthoai;
    String cmnd_cccd;
    String ngaysinh;
    String diachi;
    String loai_nv;
    int id_chucvu;
    String ten_cv;
    int id_pb;


    ConfigURL url =new ConfigURL();
    final String URL_ADD_DATA=url.getURL()+"hopdong/tao_nv_laphopdong.php";
    final String URL_SHOW_ALL_DATA=url.getURL()+"phongban/get_all_Pb.php";

    ArrayList<phongban> ArrayListPhongban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_hop_dong);

        btnTaoHD=findViewById(R.id.btnTaoHopDong);
        editTenHD=findViewById(R.id.editTenhopdong);
        editNoidungHD=findViewById(R.id.editNoidunghd);
        editNgayLHD=findViewById(R.id.editNgayLHD);
        editThoihan=findViewById(R.id.editThoihan);
        editNguoiky=findViewById(R.id.editNguoiky);
        btnNgayLHD=findViewById(R.id.btnLHD);
        toolbarHopdong=findViewById(R.id.toolbarHopdong);
        spinner_pb=findViewById(R.id.spinner_pb);


        setSupportActionBar(toolbarHopdong);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent it=getIntent();
        ten_nv=it.getStringExtra("ten_nv");
        gioitinh=it.getStringExtra("gioitinh");
        dienthoai=it.getStringExtra("dienthoai");
        cmnd_cccd=it.getStringExtra("cmnd_cccd");
        ngaysinh=it.getStringExtra("ngaysinh");
        diachi=it.getStringExtra("diachi");
        loai_nv=it.getStringExtra("loai_nv");
        id_chucvu=it.getIntExtra("id_cv",0);
        ten_cv=it.getStringExtra("ten_cv");

        show_all_pb(v);

        btnTaoHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenHD=editTenHD.getText().toString().trim();
                noidungHD=editNoidungHD.getText().toString().trim();
                ngayLHD=editNgayLHD.getText().toString().trim();
                thoihanHD=editThoihan.getText().toString().trim();
                Nguoiky=editNguoiky.getText().toString().trim();
                DialogAdd(id_chucvu);
            }
        });
        btnNgayLHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processDay();
            }
        });

    }
    public  void  show_all_pb(View view){
        class show_all_pb extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog=new ProgressDialog(ActivityLapHopDong.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("\tLoading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler=new RequestHandler();
                //creating request parameters
                HashMap<String, String> params=new HashMap<>();
                //return the Response
                return requestHandler.sendPostRequest(URL_SHOW_ALL_DATA,params);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    if(s.isEmpty()){
                        progressDialog.dismiss();
                        Toast.makeText(ActivityLapHopDong.this,"S không có giá trị !!!",Toast.LENGTH_LONG).show();
                    }else {
                        //converting response to json object
                        JSONObject obj=new JSONObject(s);
                        //get data
                        JSONArray jo=obj.getJSONArray("alldata");
                        ArrayListPhongban=new ArrayList<phongban>();
                        for (int i=0;i<jo.length();i++){
                            ArrayListPhongban.add(new phongban(jo.getJSONObject(i).getInt("id_pb"),
                                    jo.getJSONObject(i).getString("ten_pb")));
                        }
                        adapterSpinnerPhongban adapter = new adapterSpinnerPhongban(ActivityLapHopDong.this, ArrayListPhongban);
                        spinner_pb.setAdapter(adapter);
                        spinner_pb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                onItemSelectedHandler(parent, view, position, id);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        progressDialog.dismiss();
                    }
                }catch (Exception e){
                    progressDialog.dismiss();
                    Toast.makeText(ActivityLapHopDong.this,"Exception"+e,Toast.LENGTH_SHORT).show();
                }
            }

        }
        show_all_pb show=new show_all_pb();
        show.execute();
    }
    private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        phongban pb = (phongban) adapter.getItem(position);
        id_pb=pb.getId_pb();
    }
    /**
     * Hàm hiển thị DatePickerDialog để chọn năm sinh
     */
    public void processDay() {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                editNgayLHD.setText(year+"/"+month+"/"+dayOfMonth);
            }
        };
        String str_date = editNgayLHD.getText().toString().trim();
        //Lấy ngày tháng năm mặc đinh của hệ thống
        int my_dayofmounth = 2;
        int my_month = 9;
        int my_year=1982;
        String [] ls_data_it = str_date.split("/");
        if(ls_data_it.length==3)
        {
            my_dayofmounth = Integer.parseInt(ls_data_it[0]);
            my_month = Integer.parseInt(ls_data_it[1]);
            my_year = Integer.parseInt(ls_data_it[2]);
        }
        DatePickerDialog dateDialog = new DatePickerDialog(this, callBack, my_year, my_month,
                my_dayofmounth);
        dateDialog.setTitle("Birthday");
        dateDialog.show();
    }
    private void  DialogAdd(int id_chucvu){
        String id_cv=String.valueOf(id_chucvu);
        String idpb=String.valueOf(id_pb);

        Dialog dialog=new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialogaddnv);

        Button btnYesThemnv=dialog.findViewById(R.id.btnYesThemNhanvien);
        Button btnNoThemnv=dialog.findViewById(R.id.btnNoThemNhanvien);

        btnYesThemnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tenHD.equals("")||noidungHD.equals("")||ngayLHD.equals("")||thoihanHD.equals("")||Nguoiky.equals("")){
                    Toast.makeText(ActivityLapHopDong.this,"Thông tin không được trống",Toast.LENGTH_SHORT).show();
                }
                else{
                    class Chucvu extends AsyncTask<Void,Void,String> {
                        ProgressDialog progressDialog=new ProgressDialog(ActivityLapHopDong.this);

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            progressDialog.setMessage("\tLoading...");
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                        }

                        @Override
                        protected String doInBackground(Void... voids) {
                            RequestHandler requestHandler=new RequestHandler();
                            HashMap<String,String> params=new HashMap<>();

                            params.put("ten_nv",ten_nv);
                            params.put("gioitinh",gioitinh);
                            params.put("dienthoai",dienthoai);
                            params.put("cmnd_cccd",cmnd_cccd);
                            params.put("ngaysinh",ngaysinh);
                            params.put("diachi",diachi);
                            params.put("loai_nv",loai_nv);
                            params.put("id_cv",id_cv);

                            params.put("ten_hd",tenHD);
                            params.put("noidung_hd",noidungHD);
                            params.put("ngaylaphd",ngayLHD);
                            params.put("thoihan_hd",thoihanHD);
                            params.put("nguoiky_hd",Nguoiky);
                            params.put("id_pb",idpb);
                            return requestHandler.sendPostRequest(URL_ADD_DATA,params);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Thêm nhân viên thành công!",Toast.LENGTH_LONG).show();
                            Intent it=new Intent(ActivityLapHopDong.this,ActivityNhanvien.class);
                            it.putExtra("id_cv",id_chucvu);
                            it.putExtra("ten_cv",ten_cv);
                            startActivity(it);
                        }
                    }
                    Chucvu product_exec=new Chucvu();
                    product_exec.execute();

                }

            }
        });
        btnNoThemnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent it =new Intent(ActivityLapHopDong.this,ActivityAddNhanvien.class);
        it.putExtra("id_cv",id_chucvu);
        it.putExtra("ten_nv",ten_nv);
        it.putExtra("gioitinh",gioitinh);
        it.putExtra("dienthoai",dienthoai);
        it.putExtra("cmnd_cccd",cmnd_cccd);
        it.putExtra("ngaysinh",ngaysinh);
        it.putExtra("diachi",diachi);
        it.putExtra("loai_nv",loai_nv);
        it.putExtra("ten_cv",ten_cv);
        startActivity(it);
        return super.onOptionsItemSelected(item);
    }
}