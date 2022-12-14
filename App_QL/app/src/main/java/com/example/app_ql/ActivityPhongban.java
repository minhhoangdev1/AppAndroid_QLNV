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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app_ql.config.ConfigURL;
import com.example.app_ql.myadapter.adapterPhongban;
import com.example.app_ql.myadapter.adapterchucvu;
import com.example.app_ql.myhandle.RequestHandler;
import com.example.app_ql.mymodel.chucvu;
import com.example.app_ql.mymodel.phongban;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityPhongban extends AppCompatActivity {
    Toolbar toolbar;
    ListView listViewpb;
    ArrayList<phongban> ArrayListPb;
    int  count=0;
    ConfigURL url=new ConfigURL();
    final String URL_SHOW_ALL_DATA=url.getURL()+"phongban/get_all_Pb.php";
    final String URL_DELETE_DATA=url.getURL()+"phongban/delete_Pb.php";
    final String URL_SHOW_DATA=url.getURL()+"phongban/get_detail_Pb.php";
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phongban);

        toolbar =findViewById(R.id.toolbarPb);
        listViewpb=findViewById(R.id.lvPb);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        show_all_pb(v);
    }
    public  void  show_all_pb(View view){
        class show_all_chucvu extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog=new ProgressDialog(ActivityPhongban.this);
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
                        Toast.makeText(ActivityPhongban.this,"S kh??ng c?? gi?? tr??? !!!",Toast.LENGTH_LONG).show();
                    }else {
                        //converting response to json object
                        JSONObject obj=new JSONObject(s);
                        //get data
                        JSONArray jo=obj.getJSONArray("alldata");
                        ArrayListPb=new ArrayList<phongban>();
                        for (int i=0;i<jo.length();i++){
                            ArrayListPb.add(new phongban(jo.getJSONObject(i).getInt("id_pb"),
                                    jo.getJSONObject(i).getString("ten_pb")));
                        }
                        adapterPhongban adapter =new adapterPhongban(ActivityPhongban.this,ArrayListPb);
                        listViewpb.setAdapter(adapter);
                        progressDialog.dismiss();
                    }
                }catch (Exception e){
                    progressDialog.dismiss();
                    Toast.makeText(ActivityPhongban.this,"Exception",Toast.LENGTH_SHORT).show();
                }
            }

        }
        show_all_chucvu show=new show_all_chucvu();
        show.execute();
    }
    //th??m 1 menu l?? add v??o toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuadd,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //n???u click v??o n??t add
            case R.id.menuadd:
                Intent it1=new Intent(ActivityPhongban.this,ActivityAddPB.class);
                startActivity(it1);
                break;

            //n???u click v??o n??t c??n l???i l?? n??t back th?? quay l???i main
            default:
                Intent it =new Intent(ActivityPhongban.this,MainActivity.class);
                startActivity(it);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    //n???u click v??o back th?? tr??? v??? main
    @Override
    public void onBackPressed() {
        count ++;
        if(count>=1){
            Intent it=new Intent(ActivityPhongban.this,MainActivity.class);
            startActivity(it);
            finish();
        }
    }

    public  void delete(final  int position){
        Dialog dialog=new Dialog(this);
        String id_pb=String.valueOf(position) ;
        //g???i dialog th??ng b??o
        dialog.setContentView(R.layout.dialogdeletepb);
        dialog.setCanceledOnTouchOutside(false);


        Button btnYesdeletepb=dialog.findViewById(R.id.btnYesdeletepb);
        Button btnNodeletepb=dialog.findViewById(R.id.btnNodeletepb);

        btnYesdeletepb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                class Delete extends AsyncTask<Void,Void,String>{
                    ProgressDialog progressDialog=new ProgressDialog(ActivityPhongban.this);

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
                        params.put("id_pb",id_pb);
                        return requestHandler.sendPostRequest(URL_DELETE_DATA,params);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        progressDialog.dismiss();
                        try {
                            JSONObject obj=new JSONObject(s);
                            Toast.makeText(ActivityPhongban.this,obj.getString("message"),Toast.LENGTH_LONG).show();
                            Intent it=new Intent(ActivityPhongban.this,ActivityPhongban.class);
                            startActivity(it);
                        }catch (JSONException e){
                            Toast.makeText(ActivityPhongban.this,"Exception:"+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }
                Delete delete=new Delete();
                delete.execute();
            }
        });
        btnNodeletepb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public  void  update(final int position){
        String id_pb=String.valueOf(position) ;
        class show_data_detail extends AsyncTask<Void,Void,String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler=new RequestHandler();
                HashMap<String,String> params=new HashMap<>();
                params.put("id_pb",id_pb);
                return requestHandler.sendPostRequest(URL_SHOW_DATA,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj=new JSONObject(s);
                    JSONArray jo=obj.getJSONArray("alldata");
                    Intent it=new Intent(ActivityPhongban.this,ActivityUpdatePb.class);

                    //truy???n d??? li???u qua view update
                    it.putExtra("id_pb",id_pb);
                    it.putExtra("ten_pb",jo.getJSONObject(0).getString("ten_pb"));
                    startActivity(it);
                }catch (JSONException e){
                    Toast.makeText(ActivityPhongban.this,"Exception: "+e,Toast.LENGTH_LONG).show();
                }
            }
        }
        show_data_detail show=new show_data_detail();
        show.execute();

    }
}