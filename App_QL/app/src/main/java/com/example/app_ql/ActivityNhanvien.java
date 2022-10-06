package com.example.app_ql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.accessibility.AccessibilityViewCommand;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_ql.config.ConfigURL;
import com.example.app_ql.myadapter.adapterchucvu;
import com.example.app_ql.myadapter.adapternhanvien;
import com.example.app_ql.myhandle.RequestHandler;
import com.example.app_ql.mymodel.chucvu;
import com.example.app_ql.mymodel.nhanvien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityNhanvien extends AppCompatActivity {
    Toolbar toolbar;
    ListView listViewNhanvien;
    ArrayList<nhanvien> ArrayListNhanvien;
    adapternhanvien adapter;

    ConfigURL url=new ConfigURL();
    final String URL_SHOW_ALL_DATA=url.getURL()+"nhanvien/get_all_nhanvien.php";
    final String URL_DELETE_DATA=url.getURL()+"nhanvien/delete_nhanvien.php";
    final String URL_SHOW_DATA=url.getURL()+"nhanvien/get_detail_nhanvien.php";
    final String URL_SHOW_HD=url.getURL()+"hopdong/show_detail_hd.php";

    int id_chucvu;
    String ten_cv;
    int count=0;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhanvien);

        toolbar =findViewById(R.id.toolbarNhanvien);
        listViewNhanvien=findViewById(R.id.lvNhanvien);

        Intent it=getIntent();
        id_chucvu=it.getIntExtra("id_cv",0);
        ten_cv=it.getStringExtra("ten_cv");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayListNhanvien=new ArrayList<>();
        ArrayListNhanvien.clear();
        show_all_nhanvien(v,id_chucvu);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(ActivityNhanvien.this,ActivityAddNhanvien.class);
                it.putExtra("id_cv",id_chucvu);
                it.putExtra("ten_cv",ten_cv);
                startActivity(it);
            }
        });
    }
    public  void  show_all_nhanvien(View view,int id){
        String id_cv=String.valueOf(id);
        class show_all_chucvu extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog=new ProgressDialog(ActivityNhanvien.this);
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
                params.put("id_cv",id_cv);
                //return the Response
                return requestHandler.sendPostRequest(URL_SHOW_ALL_DATA,params);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    if(s.isEmpty()){
                        progressDialog.dismiss();
                        Toast.makeText(ActivityNhanvien.this,"S không có giá trị !!!",Toast.LENGTH_LONG).show();
                    }else {
                        //converting response to json object
                        JSONObject obj=new JSONObject(s);
                        //get data
                        JSONArray jo=obj.getJSONArray("alldata");
                        ArrayListNhanvien=new ArrayList<nhanvien>();
                        for (int i=0;i<jo.length();i++){
                            ArrayListNhanvien.add(new nhanvien(jo.getJSONObject(i).getInt("id_nv"),
                                    jo.getJSONObject(i).getString("ten_nv"),jo.getJSONObject(i).getString("gioitinh")));
                        }
                        adapter =new adapternhanvien(ActivityNhanvien.this,ArrayListNhanvien);
                        listViewNhanvien.setAdapter(adapter);
                        progressDialog.dismiss();
                    }
                }catch (Exception e){
                    progressDialog.dismiss();
                    Toast.makeText(ActivityNhanvien.this,"Exception",Toast.LENGTH_SHORT).show();
                }
            }

        }
        show_all_chucvu show=new show_all_chucvu();
        show.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);

        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Nhập tên nhân viên");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.action_search:

                break;
            default:
                Intent it1=new Intent(ActivityNhanvien.this,ActivityChucvu.class);
                startActivity(it1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        count++;
        if(count>=1){
            Intent it=new Intent(ActivityNhanvien.this,ActivityChucvu.class);
            startActivity(it);
            finish();
        }
        super.onBackPressed();
    }

    public void info(final int position){
        String id=String.valueOf(position) ;
        class show_data_detail extends AsyncTask<Void,Void,String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler=new RequestHandler();
                HashMap<String,String> params=new HashMap<>();
                params.put("id_nv",id);
                return requestHandler.sendPostRequest(URL_SHOW_DATA,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj=new JSONObject(s);
                    JSONArray jo=obj.getJSONArray("alldata");
                    Intent it=new Intent(ActivityNhanvien.this,ActivityInfoNhanvien.class);

                    //truyền dữ liệu qua view update
                    it.putExtra("id_nv",id);
                    it.putExtra("ten_nv",jo.getJSONObject(0).getString("ten_nv"));
                    it.putExtra("gioitinh",jo.getJSONObject(0).getString("gioitinh"));
                    it.putExtra("dienthoai",jo.getJSONObject(0).getString("dienthoai"));
                    it.putExtra("cmnd_cccd",jo.getJSONObject(0).getString("cmnd_cccd"));
                    it.putExtra("ngaysinh",jo.getJSONObject(0).getString("ngaysinh"));
                    it.putExtra("diachi",jo.getJSONObject(0).getString("diachi"));
                    it.putExtra("loai_nv",jo.getJSONObject(0).getString("loai_nv"));
                    it.putExtra("ten_cv",ten_cv);
                    it.putExtra("id_cv",id_chucvu);
                    startActivity(it);
                }catch (JSONException e){
                    Toast.makeText(ActivityNhanvien.this,"Exception: "+e,Toast.LENGTH_LONG).show();
                }
            }
        }
        show_data_detail show=new show_data_detail();
        show.execute();

    }
    public void show_hd(final int position){
        String id=String.valueOf(position) ;
        class show_data_detail extends AsyncTask<Void,Void,String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler=new RequestHandler();
                HashMap<String,String> params=new HashMap<>();
                params.put("id_nv",id);
                return requestHandler.sendPostRequest(URL_SHOW_HD,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj=new JSONObject(s);
                    JSONArray jo=obj.getJSONArray("alldata");
                    Intent it=new Intent(ActivityNhanvien.this,ActivityXemHopDong.class);

                    //truyền dữ liệu qua view update
                    it.putExtra("id_nv",id);
                    it.putExtra("id_hdld",jo.getJSONObject(0).getInt("id_hdld"));
                    it.putExtra("ten_hd",jo.getJSONObject(0).getString("ten_hd"));
                    it.putExtra("noidung_hd",jo.getJSONObject(0).getString("noidung_hd"));
                    it.putExtra("ngaylaphd",jo.getJSONObject(0).getString("ngaylaphd"));
                    it.putExtra("thoihan_hd",jo.getJSONObject(0).getString("thoihan_hd"));
                    it.putExtra("nguoiky_hd",jo.getJSONObject(0).getString("nguoiky_hd"));
                    it.putExtra("id_pb",jo.getJSONObject(0).getInt("id_pb"));
                    it.putExtra("ten_cv",ten_cv);
                    it.putExtra("id_cv",id_chucvu);
                    startActivity(it);
                }catch (JSONException e){
                    Toast.makeText(ActivityNhanvien.this,"Exception: "+e,Toast.LENGTH_LONG).show();
                }
            }
        }
        show_data_detail show=new show_data_detail();
        show.execute();

    }
    public void update(final int id_nv){
        String id=String.valueOf(id_nv) ;
        class show_data_detail extends AsyncTask<Void,Void,String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler=new RequestHandler();
                HashMap<String,String> params=new HashMap<>();
                params.put("id_nv",id);
                return requestHandler.sendPostRequest(URL_SHOW_DATA,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj=new JSONObject(s);
                    JSONArray jo=obj.getJSONArray("alldata");
                    Intent it=new Intent(ActivityNhanvien.this,ActivityUpdateNhanvien.class);

                    //truyền dữ liệu qua view update
                    it.putExtra("id_nv",id);
                    it.putExtra("ten_nv",jo.getJSONObject(0).getString("ten_nv"));
                    it.putExtra("gioitinh",jo.getJSONObject(0).getString("gioitinh"));
                    it.putExtra("dienthoai",jo.getJSONObject(0).getString("dienthoai"));
                    it.putExtra("cmnd_cccd",jo.getJSONObject(0).getString("cmnd_cccd"));
                    it.putExtra("ngaysinh",jo.getJSONObject(0).getString("ngaysinh"));
                    it.putExtra("diachi",jo.getJSONObject(0).getString("diachi"));
                    it.putExtra("loai_nv",jo.getJSONObject(0).getString("loai_nv"));
                    it.putExtra("id_cv",id_chucvu);
                    it.putExtra("ten_cv",ten_cv);
                    startActivity(it);
                }catch (JSONException e){
                    Toast.makeText(ActivityNhanvien.this,"Exception: "+e,Toast.LENGTH_LONG).show();
                }
            }
        }
        show_data_detail show=new show_data_detail();
        show.execute();
    }
    public  void delete(final  int position){
        String id_nv=String.valueOf(position) ;

        //gọi dialog thông báo
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialogdeletenhanvien);
        dialog.setCanceledOnTouchOutside(false);


        Button btnYesdeletenhanvien=dialog.findViewById(R.id.btnYesdeletenhanvien);
        Button btnNodeletenhanvien=dialog.findViewById(R.id.btnNodeletenhanvien);

        btnYesdeletenhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class Delete extends AsyncTask<Void,Void,String>{
                    ProgressDialog progressDialog=new ProgressDialog(ActivityNhanvien.this);

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
                        params.put("id_nv",id_nv);
                        return requestHandler.sendPostRequest(URL_DELETE_DATA,params);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        progressDialog.dismiss();
                        try {
                            JSONObject obj=new JSONObject(s);
                            Toast.makeText(ActivityNhanvien.this,obj.getString("message"),Toast.LENGTH_LONG).show();
                            //cập nhật lại chucvu
                            Intent it=new Intent(ActivityNhanvien.this,ActivityNhanvien.class);
                            it.putExtra("id_cv",id_chucvu);
                            startActivity(it);
                        }catch (JSONException e){
                            Toast.makeText(ActivityNhanvien.this,"Exception:"+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }
                Delete delete=new Delete();
                delete.execute();
            }
        });
        btnNodeletenhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}