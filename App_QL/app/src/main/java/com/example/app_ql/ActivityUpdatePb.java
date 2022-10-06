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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityUpdatePb extends AppCompatActivity {
    Button btnUpdate;
    EditText editUpdateTenpb;
    Toolbar toolbarPb;

    ConfigURL url=new ConfigURL();
    final String URL_UPDT_DATA=url.getURL()+"phongban/update_Pb.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pb);

        editUpdateTenpb=findViewById(R.id.editUpdateTenpb);
        btnUpdate=findViewById(R.id.btnUpdatepb);
        toolbarPb=findViewById(R.id.toolbarPb);

        setSupportActionBar(toolbarPb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent it=getIntent();
        String id_pb=it.getStringExtra("id_pb");
        String ten_pb=it.getStringExtra("ten_pb");

        editUpdateTenpb.setText(ten_pb);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogUpdate(id_pb);
            }
        });
    }
    private  void  DialogUpdate(String id){
        Dialog dialog=new Dialog(this);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialogupdatepb);

        Button btnYes=dialog.findViewById(R.id.btnYesupdatepb);
        Button btnNo=dialog.findViewById(R.id.btnNoupdatepb);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenpb=editUpdateTenpb.getText().toString().trim();
                if(tenpb.equals("")){
                    Toast.makeText(ActivityUpdatePb.this,"Thông tin không được trống",Toast.LENGTH_SHORT).show();
                }
                else {

                    class Update extends AsyncTask<Void,Void,String> {
                        ProgressDialog progressDialog=new ProgressDialog(ActivityUpdatePb.this);

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
                            params.put("id_pb",id);
                            params.put("ten_pb",tenpb);
                            return requestHandler.sendPostRequest(URL_UPDT_DATA,params);
                        }
                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            progressDialog.dismiss();
                            try {
                                JSONObject obj=new JSONObject(s);
                                Intent it=new Intent(ActivityUpdatePb.this,ActivityPhongban.class);
                                startActivity(it);
                                Toast.makeText(ActivityUpdatePb.this,obj.getString("message"),Toast.LENGTH_LONG).show();
                            }catch (JSONException e){
                                Toast.makeText(ActivityUpdatePb.this,"Exception: "+e,Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    Update update=new Update();
                    update.execute();
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
        Intent it =new Intent(ActivityUpdatePb.this,ActivityPhongban.class);
        startActivity(it);
        return super.onOptionsItemSelected(item);
    }
}