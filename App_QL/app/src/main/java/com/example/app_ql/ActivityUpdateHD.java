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
import com.example.app_ql.myadapter.adapterSpinnerPhongban;
import com.example.app_ql.myadapter.adapterUpdateHD_SpinnerPb;
import com.example.app_ql.myhandle.RequestHandler;
import com.example.app_ql.mymodel.phongban;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityUpdateHD extends AppCompatActivity {
    Button btnUpdateHD,btnNgayLHD;
    EditText editTenHD,editNoidungHD,editNgayLHD,editThoihan,editNguoiky;
    Toolbar toolbarHopdong;
    Spinner spinner_pb;
    View v;

    String tenHD;
    String noidungHD;
    String ngayLHD;
    String thoihanHD;
    String Nguoiky;

    int id_chucvu;
    String ten_cv;
    String id_pb;
    String id_nv;

    String ten_hd;
    String noidung_hd;
    String ngaylaphd;
    String thoihan_hd;
    String nguoiky_hd;
    String ten_pb;
    int id_hdld;


    ConfigURL url =new ConfigURL();
    final String URL_UPDATE_DATA=url.getURL()+"hopdong/update_hd.php";
    final String URL_SHOW_ALL_DATA=url.getURL()+"phongban/get_all_Pb.php";

    ArrayList<phongban> ArrayListPhongban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hd);

        btnUpdateHD=findViewById(R.id.btnUpdHopDong);
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

        //lấy dữ liệu
        Intent it=getIntent();
        id_chucvu=it.getIntExtra("id_cv",0);
        id_nv=it.getStringExtra("id_nv");
        ten_hd=it.getStringExtra("ten_hd");
        noidung_hd=it.getStringExtra("noidung_hd");
        ngaylaphd=it.getStringExtra("ngaylaphd");
        thoihan_hd=it.getStringExtra("thoihan_hd");
        nguoiky_hd=it.getStringExtra("nguoiky_hd");
        ten_pb=it.getStringExtra("ten_pb");
        ten_cv=it.getStringExtra("ten_cv");
        id_pb=it.getStringExtra("id_pb");
        id_hdld=it.getIntExtra("id_hdld",0);

        editTenHD.setText(ten_hd);
        editNoidungHD.setText(noidung_hd);
        editNgayLHD.setText(ngaylaphd);
        editThoihan.setText(thoihan_hd);
        editNguoiky.setText(nguoiky_hd);

        show_all_pb(v);

        btnUpdateHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenHD=editTenHD.getText().toString().trim();
                noidungHD=editNoidungHD.getText().toString().trim();
                ngayLHD=editNgayLHD.getText().toString().trim();
                thoihanHD=editThoihan.getText().toString().trim();
                Nguoiky=editNguoiky.getText().toString().trim();
                DialogAdd();
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
            ProgressDialog progressDialog=new ProgressDialog(ActivityUpdateHD.this);
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
                        Toast.makeText(ActivityUpdateHD.this,"S không có giá trị !!!",Toast.LENGTH_LONG).show();
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
                        adapterUpdateHD_SpinnerPb adapter = new adapterUpdateHD_SpinnerPb(ActivityUpdateHD.this, ArrayListPhongban);
                        spinner_pb.setAdapter(adapter);
                        int id=Integer.parseInt(id_pb);
                        spinner_pb.setSelection(id);
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
                    Toast.makeText(ActivityUpdateHD.this,"Exception"+e,Toast.LENGTH_SHORT).show();
                }
            }

        }
        show_all_pb show=new show_all_pb();
        show.execute();
    }
    private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        phongban pb = (phongban) adapter.getItem(position);
        int idpb=pb.getId_pb();
        id_pb=String.valueOf(idpb);
        ten_pb=pb.getTen_pb();
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
    private void  DialogAdd(){

        Dialog dialog=new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialogupdatehd);

        Button btnYes=dialog.findViewById(R.id.btnYes);
        Button btnNo=dialog.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tenHD.equals("")||noidungHD.equals("")||ngayLHD.equals("")||thoihanHD.equals("")||Nguoiky.equals("")){
                    Toast.makeText(ActivityUpdateHD.this,"Thông tin không được trống",Toast.LENGTH_SHORT).show();
                }
                else{
                    class Update extends AsyncTask<Void,Void,String> {
                        ProgressDialog progressDialog=new ProgressDialog(ActivityUpdateHD.this);

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
                            params.put("id_nv",id_nv);
                            params.put("ten_hd",tenHD);
                            params.put("noidung_hd",noidungHD);
                            params.put("ngaylaphd",ngayLHD);
                            params.put("thoihan_hd",thoihanHD);
                            params.put("nguoiky_hd",Nguoiky);
                            params.put("id_pb",id_pb);
                            return requestHandler.sendPostRequest(URL_UPDATE_DATA,params);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            progressDialog.dismiss();
                            try {
                                JSONObject obj=new JSONObject(s);
                                Toast.makeText(ActivityUpdateHD.this,obj.getString("message"),Toast.LENGTH_LONG).show();
                                //Toast.makeText(getApplicationContext(),"Cập nhật thành công!",Toast.LENGTH_LONG).show();
                                Intent it=new Intent(ActivityUpdateHD.this,ActivityXemHopDong.class);
                                it.putExtra("id_nv",id_nv);
                                it.putExtra("id_hdld",id_hdld);
                                it.putExtra("ten_hd",tenHD);
                                it.putExtra("noidung_hd",noidungHD);
                                it.putExtra("ngaylaphd",ngayLHD);
                                it.putExtra("thoihan_hd",ngayLHD);
                                it.putExtra("nguoiky_hd",Nguoiky);
                                it.putExtra("id_pb",id_pb);
                                it.putExtra("ten_cv",ten_cv);
                                it.putExtra("id_cv",id_chucvu);
                                it.putExtra("ten_pb",ten_pb);
                                startActivity(it);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Update up=new Update();
                    up.execute();
                }

            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent it =new Intent(ActivityUpdateHD.this,ActivityXemHopDong.class);
        it.putExtra("id_nv",id_nv);
        it.putExtra("id_hdld",id_hdld);
        it.putExtra("ten_hd",ten_hd);
        it.putExtra("noidung_hd",noidung_hd);
        it.putExtra("ngaylaphd",ngaylaphd);
        it.putExtra("thoihan_hd",thoihan_hd);
        it.putExtra("nguoiky_hd",nguoiky_hd);
        it.putExtra("id_pb",id_pb);
        it.putExtra("ten_cv",ten_cv);
        it.putExtra("id_cv",id_chucvu);
        it.putExtra("ten_pb",ten_pb);
        startActivity(it);
        return super.onOptionsItemSelected(item);
    }
}