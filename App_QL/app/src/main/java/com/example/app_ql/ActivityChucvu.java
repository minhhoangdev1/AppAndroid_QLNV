package com.example.app_ql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.app_ql.myadapter.adapterchucvu;
import com.example.app_ql.myhandle.RequestHandler;
import com.example.app_ql.mymodel.chucvu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityChucvu extends AppCompatActivity {
    Toolbar toolbar;
    ListView listViewchucvu;
    ArrayList<chucvu> ArrayListChucvu;
    int  count=0;
    ConfigURL url=new ConfigURL();
    final String URL_SHOW_ALL_DATA=url.getURL()+"chucvu/get_all_chucvu.php";
    final String URL_DELETE_DATA=url.getURL()+"chucvu/delete_chucvu.php";
    final String URL_SHOW_DATA=url.getURL()+"chucvu/get_detail_chucvu.php";
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chucvu);

        toolbar =findViewById(R.id.toolbarChucvu);
        listViewchucvu=findViewById(R.id.lvChucvu);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        show_all_chucvu(v);
        listViewchucvu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it=new Intent(ActivityChucvu.this,ActivityNhanvien.class);
                int id_chucvu=ArrayListChucvu.get(position).getId_cv();
                String ten_cv=ArrayListChucvu.get(position).getTen_cv();
                //truyền dư liệu qua activitynhanvien
                it.putExtra("id_cv",id_chucvu);
                it.putExtra("ten_cv",ten_cv);
                startActivity(it);
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(ActivityChucvu.this,ActivityAddChucvu.class);
                startActivity(it);
                startActivity(it);
            }
        });
    }
    public  void  show_all_chucvu(View view){
        class show_all_chucvu extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog=new ProgressDialog(ActivityChucvu.this);
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
                        Toast.makeText(ActivityChucvu.this,"S không có giá trị !!!",Toast.LENGTH_LONG).show();
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
                        adapterchucvu adapter =new adapterchucvu(ActivityChucvu.this,ArrayListChucvu);
                        listViewchucvu.setAdapter(adapter);
                        progressDialog.dismiss();
                    }
                }catch (Exception e){
                    progressDialog.dismiss();
                    Toast.makeText(ActivityChucvu.this,"Exception",Toast.LENGTH_SHORT).show();
                }
            }

        }
        show_all_chucvu show=new show_all_chucvu();
        show.execute();
    }
//    //thêm 1 menu là add vào toolbar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menuadd,menu);
//        return  true;
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //nếu click vào nút add
            case R.id.menuadd:
                Intent it1=new Intent(ActivityChucvu.this,ActivityAddChucvu.class);
                startActivity(it1);
                break;

            //nếu click vào nút còn lại là nút back thì quay lại main
            default:
                Intent it =new Intent(ActivityChucvu.this,MainActivity.class);
                startActivity(it);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    //nếu click vào back thì trở về main
    @Override
    public void onBackPressed() {
        count ++;
        if(count>=1){
            Intent it=new Intent(ActivityChucvu.this,MainActivity.class);
            startActivity(it);
            finish();
        }
    }

    public  void delete(final  int position){
        Dialog dialog=new Dialog(this);
        String id_cv=String.valueOf(position) ;
        //gọi dialog thông báo
        dialog.setContentView(R.layout.dialogdeletechucvu);
        dialog.setCanceledOnTouchOutside(false);


        Button btnYesdeletechucvu=dialog.findViewById(R.id.btnYesdeletechucvu);
        Button btnNodeletechucvu=dialog.findViewById(R.id.btnNodeletechucvu);

        btnYesdeletechucvu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                class Delete extends AsyncTask<Void,Void,String>{
                    ProgressDialog progressDialog=new ProgressDialog(ActivityChucvu.this);

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
                        params.put("id_cv",id_cv);
                        return requestHandler.sendPostRequest(URL_DELETE_DATA,params);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        progressDialog.dismiss();
                        try {
                            JSONObject obj=new JSONObject(s);
                            Toast.makeText(ActivityChucvu.this,obj.getString("message"),Toast.LENGTH_LONG).show();
                            //cập nhật lại chucvu
                            Intent it=new Intent(ActivityChucvu.this,ActivityChucvu.class);
                            startActivity(it);
                        }catch (JSONException e){
                            Toast.makeText(ActivityChucvu.this,"Exception:"+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }
                Delete delete=new Delete();
                delete.execute();
            }
        });
        btnNodeletechucvu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public  void  update(final int position){
        String id_cv=String.valueOf(position) ;
        class show_data_detail extends AsyncTask<Void,Void,String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler=new RequestHandler();
                HashMap<String,String> params=new HashMap<>();
                params.put("id_cv",id_cv);
                return requestHandler.sendPostRequest(URL_SHOW_DATA,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj=new JSONObject(s);
                    JSONArray jo=obj.getJSONArray("alldata");
                    Intent it=new Intent(ActivityChucvu.this,ActivityUpdateChucvu.class);

                    //truyền dữ liệu qua view update
                    it.putExtra("id_cv",id_cv);
                    it.putExtra("ten_cv",jo.getJSONObject(0).getString("ten_cv"));
                    it.putExtra("luong_cv",jo.getJSONObject(0).getString("luong_cv"));
                    startActivity(it);
                }catch (JSONException e){
                    Toast.makeText(ActivityChucvu.this,"Exception: "+e,Toast.LENGTH_LONG).show();
                }
            }
        }
        show_data_detail show=new show_data_detail();
        show.execute();

    }
}