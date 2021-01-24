package com.gradproject.hospi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gradproject.hospi.home.HomeActivity;
import com.gradproject.hospi.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {
    public static final String Email = "test";   // 임시 아이디
    public static final String PW = "af5570f5a1810b7af78caf4bc70a660f0df51e42baf91d4de5b2328de0e83dfc";   // 임시 비밀번호: 1234

    EditText inputEmail, inputPW;

    String id, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputPW = findViewById(R.id.inputPW);

        // 로그인 버튼
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = inputEmail.getText().toString();
                pw = Utils.getEncrypt(id, inputPW.getText().toString());
                if(!processedLogin(id, pw)){
                    loginFail();
                }else{
                    SharedPreferences pref = getSharedPreferences("account",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("id", id);
                    editor.putString("pw", pw);
                    editor.commit();

                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            }
        });

        // 아이디, 비밀번호 찾기 버튼
        TextView accountFindBtn = findViewById(R.id.accountFindBtn);
        accountFindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO - 계정 찾기 화면 이동
            }
        });

        // 회원가입 버튼
        TextView registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(autoLogin()){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }

    // 로그인 처리
    public static boolean processedLogin(String id, String pw){
        // TODO - 로그인 처리 로직 구현
        if(id.equals(Email)){
            if(pw.equals(PW)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    // 로그인 실패 팝업 띄우기
    private void loginFail(){
        startActivity(new Intent(getApplicationContext(), LoginFailPopUp.class));
    }

    // 자동 로그인
    private boolean autoLogin(){
        SharedPreferences pref = getSharedPreferences("account", MODE_PRIVATE);

        String id = pref.getString("id","");
        String pw = pref.getString("pw","");

        if(id.equals("") || pw.equals("")){
            return false;
        }else if(processedLogin(id, pw)){
            return true;
        }else{
            return false;
        }
    }
}