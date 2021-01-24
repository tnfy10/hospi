package com.gradproject.hospi.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gradproject.hospi.LoginFailPopUp;
import com.gradproject.hospi.R;
import com.gradproject.hospi.Utils;

public class RegisterFragment6 extends Fragment {
    RegisterActivity registerActivity;
    EditText inputPW, inputPW2; // 1: 비밀번호 2: 비밀번호 확인
    TextView pwErrorTxt, pwErrorTxt2; // 1: 중복체크 2: 빈칸 체크

    String pw; // 비밀번호 저장

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_register6, container,false);

        registerActivity = (RegisterActivity) getActivity();
        inputPW = rootView.findViewById(R.id.inputPW);
        inputPW2 = rootView.findViewById(R.id.inputPW2);
        pwErrorTxt = rootView.findViewById(R.id.pwErrorTxt);
        pwErrorTxt2 = rootView.findViewById(R.id.pwErrorTxt2);

        Button nextBtn = rootView.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputPW.getText().toString().equals("") || inputPW2.getText().toString().equals("")){
                    pwErrorTxt2.setVisibility(View.VISIBLE);
                    pwErrorTxt.setVisibility(View.INVISIBLE);
                }else if(!(inputPW.getText().toString().equals(inputPW2.getText().toString()))){
                    pwErrorTxt.setVisibility(View.VISIBLE);
                    pwErrorTxt2.setVisibility(View.INVISIBLE);
                }else{
                    pw = Utils.getEncrypt(registerActivity.user.getEmail(), inputPW2.getText().toString());
                    registerActivity.user.setPassword(pw);
                    getActivity().finish();
                    registerSuccess();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                registerActivity.onFragmentChanged(4);
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    // 회원가입 완료 팝업
    private void registerSuccess(){
        startActivity(new Intent(getContext(), RegisterSuccessPopUp.class));
    }
}