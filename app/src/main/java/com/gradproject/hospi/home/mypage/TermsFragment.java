package com.gradproject.hospi.home.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.gradproject.hospi.OnBackPressedListener;
import com.gradproject.hospi.R;

public class TermsFragment extends Fragment implements OnBackPressedListener {
    ImageButton backBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_terms, container,false);

        backBtn = rootView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> onBackPressed());

        return rootView;
    }

    @Override
    public void onBackPressed() {
        getActivity().finish();
    }
}