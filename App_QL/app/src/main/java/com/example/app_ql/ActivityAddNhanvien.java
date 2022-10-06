package com.example.app_ql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import android.app.DatePickerDialog.OnDateSetListener;
import androidx.appcompat.widget.Toolbar;

import com.example.app_ql.config.ConfigURL;
import com.example.app_ql.myhandle.RequestHandler;
import com.example.app_ql.mymodel.diachi;
import com.example.app_ql.mymodel.nhanvien;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ActivityAddNhanvien extends AppCompatActivity {
    Button btnThemnv,btnNgaysinh;
    EditText editTenNhanvien,editDienthoainv,editNgaysinh,editCmnd_Cccd,editLoainv,editDiachi;
    RadioButton radiobtnNam,radiobtnNu;
    Toolbar toolbarNhanvien;

    String name;
    String gioitinh;
    String cmnd_cccd;
    String ngaysinh;
    String diachi;
    String dienthoai;
    String loainv;
    int id_chucvu;
    String ten_cv;

    ConfigURL url =new ConfigURL();
    final String URL_ADD_DATA=url.getURL()+"nhanvien/create_nhanvien.php";

    //Cặp đôi để dùng cho AutoCompleteTextView
    //ArrayList<String> my_arrAuto; //DataSource của AutoCompleteTextView
//    ArrayAdapter<String> my_adapterAuto; //Bộ kết nối của AutoCompleteTextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nhanvien);

        btnThemnv=findViewById(R.id.btnThemNhanvien);
        editTenNhanvien=findViewById(R.id.editTenNhanvien);
        editDienthoainv=findViewById(R.id.editDienthoainv);
        editNgaysinh=findViewById(R.id.editNgaysinh);
        editCmnd_Cccd=findViewById(R.id.editCMND_CCCD);
        editLoainv=findViewById(R.id.editLoainv);
        radiobtnNam=findViewById(R.id.radiobtnNam);
        radiobtnNu=findViewById(R.id.radiobtnNu);
        btnNgaysinh=findViewById(R.id.btnNgaysinh);
        toolbarNhanvien=findViewById(R.id.toolbarNhanvien);
        editDiachi=findViewById(R.id.editDiachi);



        //Khởi tạo DataSource
//        my_arrAuto = new ArrayList<String>();
//        //Đưa vào autoComplete_placeOfBirth
//        my_adapterAuto = new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_list_item_1,
//                my_arrAuto);
//        //đưa Adapter vào AutoComplete
//        autoComplete_placeOfBirth.setAdapter(my_adapterAuto);

        setSupportActionBar(toolbarNhanvien);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent it=getIntent();
        id_chucvu=it.getIntExtra("id_cv",0);
        name=it.getStringExtra("ten_nv");
        gioitinh=it.getStringExtra("gioitinh");
        dienthoai=it.getStringExtra("dienthoai");
        cmnd_cccd=it.getStringExtra("cmnd_cccd");
        ngaysinh=it.getStringExtra("ngaysinh");
        diachi=it.getStringExtra("diachi");
        loainv=it.getStringExtra("loai_nv");
        ten_cv=it.getStringExtra("ten_cv");

        if(name!=null){
            editTenNhanvien.setText(name);
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
            editLoainv.setText(loainv);
        }

        btnThemnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=editTenNhanvien.getText().toString().trim();
                if(radiobtnNam.isChecked()) {
                    gioitinh="Nam";
                }
                else if(radiobtnNu.isChecked()){
                    gioitinh="Nữ";
                }
                cmnd_cccd=editCmnd_Cccd.getText().toString().trim();
                ngaysinh=editNgaysinh.getText().toString().trim();
                diachi=editDiachi.getText().toString().trim();
                dienthoai=editDienthoainv.getText().toString().trim();
                loainv=editLoainv.getText().toString().trim();
                Continue();
            }
        });
        btnNgaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processBirthday();
            }
        });

        //Load dữ liệu từ bảng tỉnh
//        ArrayList<diachi> t_province = database.get_all_Record();
//        if(!t_province.isEmpty())
//        {
//            my_arrAuto.clear();
//            for (int i = 0; i < t_province.size(); i++)
//                my_arrAuto.add(t_province.get(i).getName());
//            my_adapterAuto.notifyDataSetChanged();
//        }
    }
    /**
     * Hàm hiển thị DatePickerDialog để chọn năm sinh
     */
    public void processBirthday(){
        OnDateSetListener callBack = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                editNgaysinh.setText(year+"/"+month+"/"+dayOfMonth);
            }
        };
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
    private  void Continue(){
        if(name.equals("")||dienthoai.equals("")||cmnd_cccd.equals("")||ngaysinh.equals("")||diachi.equals("")||loainv.equals("")){
            Toast.makeText(ActivityAddNhanvien.this,"Thông tin không được trống",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent it=new Intent(ActivityAddNhanvien.this,ActivityLapHopDong.class);
            it.putExtra("ten_nv",name);
            it.putExtra("gioitinh",gioitinh);
            it.putExtra("dienthoai",dienthoai);
            it.putExtra("cmnd_cccd",cmnd_cccd);
            it.putExtra("ngaysinh",ngaysinh);
            it.putExtra("diachi",diachi);
            it.putExtra("loai_nv",loainv);
            it.putExtra("id_cv",id_chucvu);
            it.putExtra("ten_cv",ten_cv);
            startActivity(it);
        }
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent it =new Intent(ActivityAddNhanvien.this,ActivityNhanvien.class);
        it.putExtra("id_cv",id_chucvu);
        it.putExtra("ten_cv",ten_cv);
        startActivity(it);
        return super.onOptionsItemSelected(item);
    }



}