package com.example.app_ql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class ActivityInfoNhanvien extends AppCompatActivity {
    TextView txtTennv,txtgioitinh,txtDienthoai,txtCmnd_cccd,txtDiahci,txtNgaysinh,txtloai_nv,txtTencv;
    Toolbar toolbarNhanvien;
    int id_chucvu;
    String ten_cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_nhanvien);

        txtTennv=findViewById(R.id.txtTennv);
        txtgioitinh=findViewById(R.id.txtgioitinh);
        txtDienthoai=findViewById(R.id.txtDienthoai);
        txtDiahci=findViewById(R.id.txtDiahci);
        txtNgaysinh=findViewById(R.id.txtNgaysinh);
        txtCmnd_cccd=findViewById(R.id.txtCmnd_Cccd);
        txtloai_nv=findViewById(R.id.txtLoainv);
        txtTencv=findViewById(R.id.txtChucvu);
        toolbarNhanvien=findViewById(R.id.toolbarNhanvien);

        setSupportActionBar(toolbarNhanvien);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //lấy dữ liệu
        Intent it=getIntent();
        id_chucvu=it.getIntExtra("id_cv",0);

        String name=it.getStringExtra("ten_nv");
        String gioitinh=it.getStringExtra("gioitinh");
        String dienthoai=it.getStringExtra("dienthoai");
        String cmnd_cccd=it.getStringExtra("cmnd_cccd");
        String diachi=it.getStringExtra("diachi");
        String ngaysinh=it.getStringExtra("ngaysinh");
        String loai_nv=it.getStringExtra("loai_nv");
        ten_cv=it.getStringExtra("ten_cv");

        //gán dữ liệu
        txtTennv.setText(name);
        txtgioitinh.setText(gioitinh);
        txtDienthoai.setText(dienthoai);
        txtDiahci.setText(diachi);
        txtNgaysinh.setText(ngaysinh);
        txtloai_nv.setText(loai_nv);
        txtCmnd_cccd.setText(cmnd_cccd);
        txtTencv.setText(ten_cv);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent it =new Intent(ActivityInfoNhanvien.this,ActivityNhanvien.class);
        it.putExtra("id_cv",id_chucvu);
        it.putExtra("ten_cv",ten_cv);
        startActivity(it);
        return super.onOptionsItemSelected(item);
    }
}