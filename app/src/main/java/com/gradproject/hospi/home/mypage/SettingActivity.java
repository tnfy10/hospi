package com.gradproject.hospi.home.mypage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.gradproject.hospi.OnBackPressedListener;
import com.gradproject.hospi.R;

import java.util.List;

public class SettingActivity extends AppCompatActivity implements OnBackPressedListener {

    // 마이페이지 각 목록과 연결되는 화면
    EditMyInfoFragment editMyInfoFragment; FavoriteFragment favoriteFragment;
    InquiryListFragment inquiryListFragment; NoticeFragment noticeFragment;
    PrescriptionFragment prescriptionFragment; TermsFragment termsFragment;
    InquiryDetailFragment inquiryDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        editMyInfoFragment = new EditMyInfoFragment(); favoriteFragment = new FavoriteFragment();
        inquiryListFragment = new InquiryListFragment(); noticeFragment = new NoticeFragment();
        prescriptionFragment = new PrescriptionFragment(); termsFragment = new TermsFragment();
        inquiryDetailFragment = new InquiryDetailFragment();

        String select = getIntent().getStringExtra("selectBtn");

        switch (select){
            case "myInfoEditBtn":
                getSupportFragmentManager().beginTransaction().replace(R.id.settingContainer, editMyInfoFragment).commit();
                break;
            case "favoritesBtn":
                getSupportFragmentManager().beginTransaction().replace(R.id.settingContainer, favoriteFragment).commit();
                break;
            case "prescriptionBtn":
                getSupportFragmentManager().beginTransaction().replace(R.id.settingContainer, prescriptionFragment).commit();
                break;
            case "inquiryDetailsBtn":
                getSupportFragmentManager().beginTransaction().replace(R.id.settingContainer, inquiryListFragment).commit();
                break;
            case "termsBtn":
                getSupportFragmentManager().beginTransaction().replace(R.id.settingContainer, termsFragment).commit();
                break;
            case "noticeBtn":
                getSupportFragmentManager().beginTransaction().replace(R.id.settingContainer, noticeFragment).commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if(fragmentList!=null){
            for(Fragment fragment : fragmentList) {
                if (fragment instanceof OnBackPressedListener) {
                    ((OnBackPressedListener) fragment).onBackPressed();
                }
            }
        }else{
            super.onBackPressed();
        }
    }
}