package com.example.app_ql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button btnChucvu,btnThoat,btnKhac,btnPb;
    ImageView img_nv,img_pb,img_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get_myWidget();
        set_myEvent();
    }

    private void get_myWidget() {
        btnChucvu=findViewById(R.id.btnChucvu);
        btnThoat=findViewById(R.id.btnExit);
        btnKhac=findViewById(R.id.btnKhac);
        btnPb=findViewById(R.id.btnPb);
        img_nv=findViewById(R.id.img_nv);
        img_pb=findViewById(R.id.img_pb);
        img_home=findViewById(R.id.img_home);
    }
    private  void  set_myEvent()
    {
        btnKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { DialogKhac(); }
        });
        btnChucvu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,ActivityChucvu.class);
                startActivity(it);
            }
        });
        btnPb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,ActivityPhongban.class);
                startActivity(it);
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {DialogExit(); }
        });
        img_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,ActivityChucvu.class);
                startActivity(it);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,MainActivity.class);
                startActivity(it);
            }
        });
        img_pb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,ActivityPhongban.class);
                startActivity(it);
            }
        });
    }
    private  void DialogExit()
    {
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialogexit);
        //tắt click ngoài là thoát
        dialog.setCanceledOnTouchOutside(false);
        Button btnYes=dialog.findViewById(R.id.btnYes);
        Button btnNo=dialog.findViewById(R.id.btnNo);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,Login.class);
                startActivity(it);
                //thoát
                Intent it1=new Intent(Intent.ACTION_MAIN);
                it1.addCategory(Intent.CATEGORY_HOME);
                startActivity(it1);
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
    private  void  DialogKhac()
    {
        //tạo đối tượng cửa sổ dialog
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialoginfo);
        dialog.show();
    }

}