package com.example.app_ql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_ql.config.ConfigURL;
import com.example.app_ql.myhandle.RequestHandler;
import com.example.app_ql.mymodel.chucvu;
import com.example.app_ql.mymodel.phongban;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityAddPB extends AppCompatActivity {
    Button btnThempb;
    EditText editTenPb;
    phongban pb;
    Toolbar toolbarPb;
    ConfigURL url =new ConfigURL();
    final String URL_ADD_DATA=url.getURL()+"phongban/create_Pb.php";

    String tenPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pb);
        toolbarPb=findViewById(R.id.toolbarAddPb);
        btnThempb=findViewById(R.id.btnThemPb);
        editTenPb=findViewById(R.id.editTenPb);

        setSupportActionBar(toolbarPb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnThempb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenPb=editTenPb.getText().toString().trim();
                DialogAdd();
            }
        });
    }
    private  void DialogAdd(){
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialogaddpb);
        dialog.setCanceledOnTouchOutside(false);

        Button btnyes=dialog.findViewById(R.id.btnYes);
        Button btnNo=dialog.findViewById(R.id.btnNo);

        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nếu chưa nhập tên
                if(tenPb.equals("")){
                    Toast.makeText(ActivityAddPB.this,"Thông tin không được trống",Toast.LENGTH_SHORT).show();
                }
                else {
                    class Chucvu extends AsyncTask<Void,Void,String> {
                        ProgressDialog progressDialog=new ProgressDialog(ActivityAddPB.this);

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
                            params.put("ten_pb",tenPb);
                            return requestHandler.sendPostRequest(URL_ADD_DATA,params);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            progressDialog.dismiss();
                            try {
                                JSONObject obj=new JSONObject(s);
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                                //add thành công chuyển qua giao diện Pb
                                Intent it =new Intent(ActivityAddPB.this,ActivityPhongban.class);
                                startActivity(it);
                            }catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(ActivityAddPB.this,"Exception",Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent it =new Intent(ActivityAddPB.this,ActivityPhongban.class);
        startActivity(it);
        return super.onOptionsItemSelected(item);
    }
}