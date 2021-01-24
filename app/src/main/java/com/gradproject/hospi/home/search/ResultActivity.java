package com.gradproject.hospi.home.search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gradproject.hospi.R;

public class ResultActivity extends AppCompatActivity {
    SearchWaitFragment searchWaitFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        searchWaitFragment = new SearchWaitFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.resultContainer, searchWaitFragment).commit();
    }
}