package com.example.app_ql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_ql.config.ConfigURL;
import com.example.app_ql.myadapter.adapternhanvien;
import com.example.app_ql.myhandle.RequestHandler;
import com.example.app_ql.mymodel.nhanvien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityXemHopDong extends AppCompatActivity {
    TextView txtTenhd,txtnoidung,txtThoihan,txtLHD,txtNguoiky,txtPb;
    ImageButton UpdateHd;
    Toolbar toolbarXemHD;
    int id_chucvu;
    String ten_cv;
    String ten_pb;
    View v;

    ConfigURL url=new ConfigURL();
    final String URL_SHOW_DATA=url.getURL()+"phongban/get_detail_Pb.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_hop_dong);

        txtTenhd=findViewById(R.id.txtTenhd);
        txtnoidung=findViewById(R.id.txtnoidung);
        txtThoihan=findViewById(R.id.txtThoihan);
        txtLHD=findViewById(R.id.txtLHD);
        txtNguoiky=findViewById(R.id.txtNguoiky);
        txtPb=findViewById(R.id.txtPb);
        toolbarXemHD=findViewById(R.id.toolbarXemHD);
        UpdateHd=findViewById(R.id.UpdateHd);


        setSupportActionBar(toolbarXemHD);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //lấy dữ liệu
        Intent it=getIntent();
        id_chucvu=it.getIntExtra("id_cv",0);
        String id_nv=it.getStringExtra("id_nv");
        String ten_hd=it.getStringExtra("ten_hd");
        String noidung_hd=it.getStringExtra("noidung_hd");
        String ngaylaphd=it.getStringExtra("ngaylaphd");
        String thoihan_hd=it.getStringExtra("thoihan_hd");
        String nguoiky_hd=it.getStringExtra("nguoiky_hd");
        ten_pb=it.getStringExtra("ten_pb");
        ten_cv=it.getStringExtra("ten_cv");
        int id_pb=it.getIntExtra("id_pb",0);
        int id_hdld=it.getIntExtra("id_hdld",0);

        show_pb(v,id_pb);

        //gán dữ liệu
        txtTenhd.setText(ten_hd);
        txtnoidung.setText(noidung_hd);
        txtThoihan.setText(thoihan_hd);
        txtLHD.setText(ngaylaphd);
        txtNguoiky.setText(nguoiky_hd);
        txtPb.setText(ten_pb);

        UpdateHd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(ActivityXemHopDong.this,ActivityUpdateHD.class);

                it.putExtra("id_nv",id_nv);
                it.putExtra("id_hdld",id_hdld);
                it.putExtra("ten_hd",ten_hd);
                it.putExtra("noidung_hd",noidung_hd);
                it.putExtra("ngaylaphd",ngaylaphd);
                it.putExtra("thoihan_hd",thoihan_hd);
                it.putExtra("nguoiky_hd",nguoiky_hd);
                it.putExtra("ten_pb",ten_pb);
                it.putExtra("id_pb",id_pb);
                it.putExtra("ten_cv",ten_cv);
                it.putExtra("id_cv",id_chucvu);
                startActivity(it);
            }
        });

    }
    public  void  show_pb(View view, int id){
        String id_pb=String.valueOf(id);
        class show_detail_pb extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog=new ProgressDialog(ActivityXemHopDong.this);
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
                params.put("id_pb",id_pb);
                //return the Response
                return requestHandler.sendPostRequest(URL_SHOW_DATA,params);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    if(s.isEmpty()){
                        progressDialog.dismiss();
                        Toast.makeText(ActivityXemHopDong.this,"S không có giá trị !!!",Toast.LENGTH_LONG).show();
                    }else {
                        JSONObject obj=new JSONObject(s);
                        JSONArray jo=obj.getJSONArray("alldata");
                        ten_pb=jo.getJSONObject(0).getString("ten_pb");
                        txtPb.setText(ten_pb);
                        progressDialog.dismiss();
                    }
                }catch (Exception e){
                    progressDialog.dismiss();
                    Toast.makeText(ActivityXemHopDong.this,"Exception"+e,Toast.LENGTH_SHORT).show();
                }
            }

        }
        show_detail_pb show=new show_detail_pb();
        show.execute();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent it =new Intent(ActivityXemHopDong.this,ActivityNhanvien.class);
        it.putExtra("id_cv",id_chucvu);
        it.putExtra("ten_cv",ten_cv);
        startActivity(it);
        return super.onOptionsItemSelected(item);
    }
}