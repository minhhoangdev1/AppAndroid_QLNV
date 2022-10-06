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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app_ql.config.ConfigURL;
import com.example.app_ql.myadapter.adapterSpinner;
import com.example.app_ql.myadapter.adapterchucvu;
import com.example.app_ql.myhandle.RequestHandler;
import com.example.app_ql.mymodel.chucvu;
import com.example.app_ql.mymodel.diachi;
import com.example.app_ql.mymodel.nhanvien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityUpdateNhanvien extends AppCompatActivity {
    Button btnUpdate,btnNgaysinh;
    EditText editTenNhanvien,editDienthoainv,editDiachi,editNgaysinh,editLoainv,editCmnd_Cccd;
    RadioButton radiobtnNam,radiobtnNu;
    Toolbar toolbarNhanvien;
    ArrayList<chucvu> ArrayListChucvu;
    Spinner spinner_cv;
    View v;
   // AutoCompleteTextView autoComplete_placeOfBirth;
    int id_chucvu;
    String idcv;
    String ten_cv;
    ConfigURL url =new ConfigURL();
    final String URL_UPDT_DATA=url.getURL()+"nhanvien/update_nhanvien.php";
    final String URL_SHOW_ALL_DATA=url.getURL()+"chucvu/get_all_chucvu.php";
//    //Cặp đôi để dùng cho AutoCompleteTextView
//    ArrayList<String> my_arrAuto; //DataSource của AutoCompleteTextView
//    ArrayAdapter<String> my_adapterAuto; //Bộ kết nối của AutoCompleteTextView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nhanvien);

        btnUpdate=findViewById(R.id.btnUpdateNhanvien);
        editTenNhanvien=findViewById(R.id.editTenNhanvien);
        editDienthoainv=findViewById(R.id.editDienthoainv);
        //autoComplete_placeOfBirth=findViewById(R.id.autoComplete_placeOfBirth );
        editDiachi=findViewById(R.id.editDiachi);
        editCmnd_Cccd=findViewById(R.id.editCMND_CCCD);
        editNgaysinh=findViewById(R.id.editNgaysinh);
        editLoainv=findViewById(R.id.editLoainv);
        radiobtnNam=findViewById(R.id.radiobtnNam);
        radiobtnNu=findViewById(R.id.radiobtnNu);
        toolbarNhanvien=findViewById(R.id.toolbarNhanvien);
        btnNgaysinh=findViewById(R.id.btnNgaysinh);
        spinner_cv=findViewById(R.id.spinner_cv);
//
//        //Khởi tạo DataSource
//        my_arrAuto = new ArrayList<String>();
//        //Đưa vào autoComplete_placeOfBirth
//        my_adapterAuto = new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_list_item_2,
//                my_arrAuto);
//        //đưa Adapter vào AutoComplete
//        autoComplete_placeOfBirth.setAdapter(my_adapterAuto);
//
        setSupportActionBar(toolbarNhanvien);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //lấy dữ liệu
        Intent it=getIntent();
        String id_nv=it.getStringExtra("id_nv");
        String ten_nv=it.getStringExtra("ten_nv");
        String gioitinh=it.getStringExtra("gioitinh");
        String dienthoai=it.getStringExtra("dienthoai");
        String cmnd_cccd=it.getStringExtra("cmnd_cccd");
        String diachi=it.getStringExtra("diachi");
        String ngaysinh=it.getStringExtra("ngaysinh");
        String loai_nv=it.getStringExtra("loai_nv");
        id_chucvu=it.getIntExtra("id_cv",0);
        ten_cv=it.getStringExtra("ten_cv");

        //gán dữ liệu
        editTenNhanvien.setText(ten_nv);
        if(gioitinh.equals("Nam")){
            radiobtnNam.setChecked(true);
            radiobtnNu.setChecked(false);
        }
        else {
            radiobtnNam.setChecked(false);
            radiobtnNu.setChecked(true);
        }
        editDienthoainv.setText(dienthoai);
        editCmnd_Cccd.setText(cmnd_cccd);
        //autoComplete_placeOfBirth.setText(diachi);
        editDiachi.setText(diachi);
        editNgaysinh.setText(ngaysinh);
        editLoainv.setText(loai_nv);
        show_all_chucvu(v);

        //click button update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_update(id_nv,id_chucvu);
            }
        });

        btnNgaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processBirthday();
            }
        });

//        //Load dữ liệu từ bảng tỉnh
//        ArrayList<diachi> t_province = database.get_all_Record();
//        if(!t_province.isEmpty())
//        {
//            my_arrAuto.clear();
//            for (int i = 0; i < t_province.size(); i++)
//                my_arrAuto.add(t_province.get(i).getName());
//            my_adapterAuto.notifyDataSetChanged();
//        }
    }
    public  void  show_all_chucvu(View view){
        class show_all_chucvu extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog=new ProgressDialog(ActivityUpdateNhanvien.this);
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
                        Toast.makeText(ActivityUpdateNhanvien.this,"S không có giá trị !!!",Toast.LENGTH_LONG).show();
                    }else {
                        //converting response to json object
                        JSONObject obj=new JSONObject(s);
                        //get data
                        JSONArray jo=obj.getJSONArray("alldata");
                        ArrayListChucvu=new ArrayList<chucvu>();
                        for (int i=0;i<jo.length();i++){
                            ArrayListChucvu.add(new chucvu(jo.getJSONObject(i).getInt("id_cv"),
                                    jo.getJSONObject(i).getString("ten_cv")));
                        }
                        adapterSpinner adapter = new adapterSpinner(ActivityUpdateNhanvien.this, ArrayListChucvu);
                        spinner_cv.setAdapter(adapter);
                        spinner_cv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
                    Toast.makeText(ActivityUpdateNhanvien.this,"Exception"+e,Toast.LENGTH_SHORT).show();
                }
            }

        }
        show_all_chucvu show=new show_all_chucvu();
        show.execute();
    }
    private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        chucvu cv = (chucvu) adapter.getItem(position);
        idcv=String.valueOf(cv.getId_cv());
    }

    private  void  Dialog_update(String idvn,int id_cv){

        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialogupdatenhanvien);
        dialog.setCanceledOnTouchOutside(false);

        Button btnYesUpdatenhanvien=dialog.findViewById(R.id.btnYesupdatenhanvien);
        Button btnNoupdatenhanvien=dialog.findViewById(R.id.btnNoupdatenhanvien);

        btnYesUpdatenhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editTenNhanvien.getText().toString().trim();
                String dienthoai=editDienthoainv.getText().toString().trim();
                String diachi= editDiachi.getText().toString().trim();
                String cmnd_cccd=editCmnd_Cccd.getText().toString().trim();
                String ngaysinh=editNgaysinh.getText().toString().trim();
                String loainv=editLoainv.getText().toString().trim();
                String gioitinh;
                if(radiobtnNam.isChecked()){
                    gioitinh="Nam";
                }
                else {
                    gioitinh="Nữ";
                }

                if(name.equals("")||dienthoai.equals("")||diachi.equals("")||ngaysinh.equals("")||loainv.equals("")){
                    Toast.makeText(ActivityUpdateNhanvien.this,"Thông tin không được trống",Toast.LENGTH_SHORT).show();
                }
                else {
                    class Update extends AsyncTask<Void,Void,String> {
                        ProgressDialog progressDialog=new ProgressDialog(ActivityUpdateNhanvien.this);

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
                            params.put("id_nv",idvn);
                            params.put("ten_nv",name);
                            params.put("gioitinh",gioitinh);
                            params.put("dienthoai",dienthoai);
                            params.put("cmnd_cccd",cmnd_cccd);
                            params.put("ngaysinh",ngaysinh);
                            params.put("diachi",diachi);
                            params.put("loai_nv",loainv);
                            params.put("id_cv",idcv);
                            return requestHandler.sendPostRequest(URL_UPDT_DATA,params);
                        }
                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            progressDialog.dismiss();
                            try {
                                JSONObject obj=new JSONObject(s);
                                Intent it=new Intent(ActivityUpdateNhanvien.this,ActivityNhanvien.class);
                                it.putExtra("id_cv",id_cv);
                                it.putExtra("ten_cv",ten_cv);
                                startActivity(it);
                                Toast.makeText(ActivityUpdateNhanvien.this,obj.getString("message"),Toast.LENGTH_LONG).show();
                            }catch (JSONException e){
                                Toast.makeText(ActivityUpdateNhanvien.this,"Exception: "+e,Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    Update update=new Update();
                    update.execute();

//                    //Xử lý cho Autocomplete về nơi sinh => nếu nơi sinh do người dùng đưa vào,
//                    // nếu chưa có thì cập nhật vào DataSource của nó
//
//                    if(!check_Exist_List(my_arrAuto, auto)) {
//                        //Cập nhật dữ liệu cho Bảng Tỉnh
//                        diachi dc=new diachi(auto);
//                        database.insertDiachi(dc);
//                        processAutoComplete(auto);
//                    }
                }
            }
        });
        btnNoupdatenhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
//    public void processAutoComplete(String data)
//    {
//        my_arrAuto.add(data);
//        my_adapterAuto.notifyDataSetChanged();
//    }
//    //Hàm kiểm tra trùng lắp
//    public boolean check_Exist_List(ArrayList<String> _list, String str)
//    {
//        //Chạy từ đầu đến cuối nếu trùng thì thoát khỏi hàm
//        for(int i=0;i < _list.size();i++)
//        {
//            String x = _list.get(i);
//            if(x.trim().equalsIgnoreCase(str.trim()))
//                return true;
//        }
//        return false;
//    }
    /**
     * Hàm hiển thị DatePickerDialog để chọn năm sinh
     */
    public void processBirthday() {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                editNgaysinh.setText(year+"/"+month+"/"+dayOfMonth);
            }
        };
        //Ở đây tôi chỉ xử lý đơn giản việc lấy ngày tháng năm trên EditText editBirthday để
        //đưa vào DatePicker
        //Các bạn xử lý thêm
        String str_date = editNgaysinh.getText().toString().trim();
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent it =new Intent(ActivityUpdateNhanvien.this,ActivityNhanvien.class);
        it.putExtra("id_cv",id_chucvu);
        it.putExtra("ten_cv",ten_cv);
        startActivity(it);
        return super.onOptionsItemSelected(item);
    }

}