package com.example.quanlythuchi.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.callback.nguoidung.FindOneCallback;
import com.example.quanlythuchi.model.NguoiDung;
import com.example.quanlythuchi.service.NguoiDungService;
import com.example.quanlythuchi.util.CustomToast;

import org.bson.Document;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class LoginActivity extends AppCompatActivity {
    private static final String uri = "content://com.example.myapplication.ContentProvider/";
    public static final String APP_ID = "quan_ly_chi_tieu-dgcgz";
    Button btn_logIn;
    EditText input_email;
    EditText input_password;
    ProgressDialog progressDialog;
    NguoiDungService nguoiDungService;
    public static NguoiDung nguoiDung;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Realm.init(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang lấy thông tin của bạn");
        progressDialog.setCancelable(false);

        onClick();
    }

    void onClick(){
        btn_logIn = findViewById(R.id.login_btn_logIn);
        input_email = findViewById(R.id.login_inputEmail);
        input_password = findViewById(R.id.login_inputPassword);

        btn_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(input_email.getText());
                String password = String.valueOf(input_password.getText());

                if(!email.trim().isEmpty() && !password.trim().isEmpty()){
                    App app = new App(new AppConfiguration.Builder(LoginActivity.APP_ID).build());
                    Credentials emailPasswordCredentials = Credentials.emailPassword(email, password);
                    AtomicReference<User> user = new AtomicReference<User>();

                    progressDialog.show();

                    app.loginAsync(emailPasswordCredentials, it -> {
                        if (it.isSuccess()) {
                            user.set(app.currentUser());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                            nguoiDungService = new NguoiDungService();
                            Document query = new Document("tenDangNhap", email);
                            CompletableFuture<NguoiDung> future  = nguoiDungService.findOne(query);

                            future.thenAccept(user2 -> {
                                nguoiDung = user2;
                            }).exceptionally(throwable -> {
                                Log.i(TAG, "onFailure: " + "Loi mia r");
                                return null;
                            });
                        }
                        else{
                            new CustomToast(getApplicationContext()).show("Thông tin bạn cung cấp không hợp lệ !");
                        }
                        progressDialog.cancel();
                    });
                }
                else{
                    new CustomToast(getApplicationContext()).show("Vui lòng điền đầy đủ thông tin");
                }
            }
        });
    }
}