package com.example.app_ql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.app_ql.config.ConfigURL;
import com.example.app_ql.myhandle.RequestHandler;
import com.example.app_ql.mymodel.chucvu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import javax.security.auth.Subject;

public class ActivityAddChucvu extends AppCompatActivity {
    Button btnThemchucvu;
    EditText editTenchucvu,editLuongchucvu;
    chucvu chucvu;
    Toolbar toolbarNhanvien;
    ConfigURL url =new ConfigURL();
    final String URL_ADD_DATA=url.getURL()+"chucvu/create_chucvu.php";

    String tenchucvu;
    String luongchuccu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chucvu);
        toolbarNhanvien=findViewById(R.id.toolbarNhanvien);
        btnThemchucvu=findViewById(R.id.btnThemchucvu);
        editTenchucvu=findViewById(R.id.editTenchucvu);
        editLuongchucvu=findViewById(R.id.editLuongchucvu);

        setSupportActionBar(toolbarNhanvien);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnThemchucvu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenchucvu=editTenchucvu.getText().toString().trim();
                luongchuccu=editLuongchucvu.getText().toString().trim();
                DialogAdd();
            }
        });
    }

    private  void DialogAdd(){
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialogadd);
        dialog.setCanceledOnTouchOutside(false);

        Button btnyes=dialog.findViewById(R.id.btnYes);
        Button btnNo=dialog.findViewById(R.id.btnNo);

        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nếu chưa nhập tên
                if(tenchucvu.equals("") || luongchuccu.equals("")){
                    Toast.makeText(ActivityAddChucvu.this,"Thông tin không được trống",Toast.LENGTH_SHORT).show();
                }
                else {
                    //gán cho đôi tượng chức vụ các giá trị nhập vào
                    chucvu chucvu =Createchucvu();
                    class Chucvu extends AsyncTask<Void,Void,String> {
                        ProgressDialog progressDialog=new ProgressDialog(ActivityAddChucvu.this);

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
                            params.put("ten_cv",chucvu.getTen_cv());
                            params.put("luong_cv",chucvu.getLuong_cv());
                            return requestHandler.sendPostRequest(URL_ADD_DATA,params);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            progressDialog.dismiss();
                            try {
                                JSONObject obj=new JSONObject(s);
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                                //add thành công chuyển qua giao diện chức vụ
                                Intent it =new Intent(ActivityAddChucvu.this,ActivityChucvu.class);
                                startActivity(it);
                            }catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(ActivityAddChucvu.this,"Exception",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    Chucvu product_exec=new Chucvu();
                    product_exec.execute();
                }
            }
        });
        // nếu không add
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();


    }
    //tạo chức vụ
    private chucvu Createchucvu(){
        chucvu chucvu=new chucvu(tenchucvu,luongchuccu);
        return  chucvu;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent it =new Intent(ActivityAddChucvu.this,ActivityChucvu.class);
        startActivity(it);
        return super.onOptionsItemSelected(item);
    }

}