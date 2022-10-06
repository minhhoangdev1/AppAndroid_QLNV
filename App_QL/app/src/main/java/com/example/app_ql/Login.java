package com.example.app_ql;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    Button btnlogin;
    TextInputEditText editUser, editPassword;
    CheckBox chksaveaccount;
    //đặt tên cho tập tin lưu trạng thái
    String prefname="my_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        get_myWidget();
        set_myEvent();
    }
    private void get_myWidget()
    {
        btnlogin=(Button) findViewById(R.id.btnlogin);
        editUser =(TextInputEditText) findViewById(R.id.editUser);
        editPassword=(TextInputEditText) findViewById(R.id.editPassword);
        chksaveaccount=(CheckBox) findViewById(R.id.chksaveacount);

        editUser.setText("admin");
        editPassword.setText("123456");
    }
    private void set_myEvent()
    {
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }
    private void doLogin()
    {

        String username="admin";
        String pass="123456";
        //kiểm tra username và pass
        if(editUser.getText().toString().equals(username) && editPassword.getText().toString().equals(pass))
        {
            Toast.makeText(this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
            finish();//đóng màn hình hiện tại
            Intent it=new Intent(this, MainActivity.class);
            //truyền dữ liệu qua màn hình mới
            it.putExtra("user", editUser.getText().toString());
            startActivity(it);//mở màn hình mới
        }
        else
        {
            Toast.makeText(this,"Sai user & password",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        //gọi hàm lưu trạng thái ở đây
        savingPreferences();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //gọi hàm đọc trạng thái ở đây
        restoringPreferences();
    }
    public void savingPreferences()
    {
        //tạo đối tượng getSharedPreferences
        SharedPreferences pre = getSharedPreferences(prefname, MODE_PRIVATE);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        String user = editUser.getText().toString();
        String pwd = editPassword.getText().toString();
        boolean bchk = chksaveaccount.isChecked();
        if(!bchk)
        {
            //xóa mọi lưu trữ trước đó
            editor.clear();
        }
        else
        {
            //lưu vào editor
            editor.putString("user", user);
            editor.putString("pwd", pwd);
            editor.putBoolean("checked", bchk);
        }
        //chấp nhận lưu xuống file
        editor.commit();
    }
    /**
     * hàm đọc trạng thái đã lưu trước đó
     */
    public void restoringPreferences()
    {
        SharedPreferences pre = getSharedPreferences(prefname,MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        boolean bchk = pre.getBoolean("checked", false);
        if(bchk)
        {
            //lấy user, pwd, nếu không thấy giá trị mặc định là rỗng
            String user = pre.getString("user", "");
            String pwd = pre.getString("pwd", "");
            editUser.setText(user);
            editPassword.setText(pwd);
        }
        chksaveaccount.setChecked(bchk);
    }
}