package com.gradproject.hospi.home.mypage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.gradproject.hospi.OnBackPressedListener;
import com.gradproject.hospi.R;

import java.util.ArrayList;
import java.util.Collections;

import static com.gradproject.hospi.home.HomeActivity.user;

public class NoticeFragment extends Fragment implements OnBackPressedListener {
    private static final String TAG = "NoticeFragment";

    RecyclerView noticeRecyclerView;
    LinearLayoutManager layoutManager;
    NoticeAdapter noticeAdapter = new NoticeAdapter();

    ImageButton backBtn;
    FloatingActionButton writeBtn;
    int pos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_notice, container,false);

        backBtn = rootView.findViewById(R.id.backBtn);
        writeBtn = rootView.findViewById(R.id.writeBtn);
        noticeRecyclerView = rootView.findViewById(R.id.noticeList);

        noticeRecyclerView.setLayoutManager(layoutManager);

        if(getArguments()!=null){
            pos = getArguments().getInt("pos", -1);
            if(pos!=-1){
                noticeAdapter.items.remove(pos);
                noticeAdapter.notifyItemRemoved(pos);
                noticeAdapter.notifyItemRangeChanged(pos, noticeAdapter.items.size());
            }

            if(getArguments().getBoolean("write", false)){
                noticeAdapter.notifyDataSetChanged();
            }
        }

        getNoticeList();

        if(user.isAdmin()){
            writeBtn.setVisibility(View.VISIBLE);
        }

        backBtn.setOnClickListener(v -> onBackPressed());

        writeBtn.setOnClickListener(v -> getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settingContainer, new NoticeWriteFragment())
                .commit());

        noticeAdapter.setOnItemClickListener((holder, view, position) -> {
            Notice notice = noticeAdapter.getItem(position);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            NoticeDetailFragment noticeDetailFragment = new NoticeDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("pos", position);
            bundle.putSerializable("notice", notice);
            noticeDetailFragment.setArguments(bundle);
            transaction.replace(R.id.settingContainer, noticeDetailFragment);
            transaction.commit();
        });

        return rootView;
    }

    @Override
    public void onBackPressed() {
        getActivity().finish();
    }

    private void getNoticeList(){
        noticeAdapter.items.clear(); // 기존 항목 모두 삭제
        noticeAdapter.notifyDataSetChanged(); // 어댑터 갱신

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Notice.DB_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    ArrayList<Notice> tmpArrList = new ArrayList<>();

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Notice notice = document.toObject(Notice.class);
                            notice.setDocumentId(document.getId());
                            tmpArrList.add(notice);
                        }

                        Collections.sort(tmpArrList, new NoticeComparator());

                        for(int i=0; i<tmpArrList.size(); i++){
                            noticeAdapter.addItem(tmpArrList.get(i));
                        }

                        noticeRecyclerView.setAdapter(noticeAdapter);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                        String msg = "공지사항을 불러올 수 없습니다.";
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                    }
                });
    }
}