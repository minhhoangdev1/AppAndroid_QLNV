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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityUpdateChucvu extends AppCompatActivity {
    Button btnUpdate;
    EditText editUpdateTenchucvu,editUpdateLuongchucvu;
    Toolbar toolbarNhanvien;

    ConfigURL url=new ConfigURL();
    final String URL_UPDT_DATA=url.getURL()+"chucvu/update_chucvu.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_chucvu);

        editUpdateTenchucvu=findViewById(R.id.editUpdateTenchucvu);
        editUpdateLuongchucvu=findViewById(R.id.editUpdateLuongchucvu);
        btnUpdate=findViewById(R.id.btnUpdatechucvu);
        toolbarNhanvien=findViewById(R.id.toolbar);

        setSupportActionBar(toolbarNhanvien);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent it=getIntent();
        String id_cv=it.getStringExtra("id_cv");
        String ten_cv=it.getStringExtra("ten_cv");
        String luong_cv=it.getStringExtra("luong_cv");

        editUpdateTenchucvu.setText(ten_cv);
        editUpdateLuongchucvu.setText(luong_cv);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogUpdate(id_cv);
            }
        });
    }
    private  void  DialogUpdate(String id){
        Dialog dialog=new Dialog(this);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialogupdatechucvu);

        Button btnYesUpdatechucvu=dialog.findViewById(R.id.btnYesupdatechucvu);
        Button btnNoupdatechucvu=dialog.findViewById(R.id.btnNoupdatechucvu);

        btnYesUpdatechucvu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenchucvu=editUpdateTenchucvu.getText().toString().trim();
                String luongchucvu=editUpdateLuongchucvu.getText().toString().trim();
                if(tenchucvu.equals("")||luongchucvu.equals("")){
                    Toast.makeText(ActivityUpdateChucvu.this,"Thông tin không được trống",Toast.LENGTH_SHORT).show();
                }
                else {

                    class Update extends AsyncTask<Void,Void,String> {
                        ProgressDialog progressDialog=new ProgressDialog(ActivityUpdateChucvu.this);

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
                            params.put("id_cv",id);
                            params.put("ten_cv",tenchucvu);
                            params.put("luong_cv",luongchucvu);
                            return requestHandler.sendPostRequest(URL_UPDT_DATA,params);
                        }
                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            progressDialog.dismiss();
                            try {
                                JSONObject obj=new JSONObject(s);
                                Intent it=new Intent(ActivityUpdateChucvu.this,ActivityChucvu.class);
                                startActivity(it);
                                Toast.makeText(ActivityUpdateChucvu.this,obj.getString("message"),Toast.LENGTH_LONG).show();
                            }catch (JSONException e){
                                Toast.makeText(ActivityUpdateChucvu.this,"Exception: "+e,Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    Update update=new Update();
                    update.execute();
                }
            }
        });
        btnNoupdatechucvu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent it =new Intent(ActivityUpdateChucvu.this,ActivityChucvu.class);
        startActivity(it);
        return super.onOptionsItemSelected(item);
    }
}