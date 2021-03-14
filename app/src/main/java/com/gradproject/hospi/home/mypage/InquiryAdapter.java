package com.gradproject.hospi.home.mypage;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gradproject.hospi.Inquiry;
import com.gradproject.hospi.R;

import java.util.ArrayList;

public class InquiryAdapter extends RecyclerView.Adapter<InquiryAdapter.ViewHolder>
        implements OnInquiryItemClickListener {
    ArrayList<Inquiry> items = new ArrayList<Inquiry>();
    OnInquiryItemClickListener listener;

    public void addItem(Inquiry item){
        items.add(item);
    }

    public void setItems(ArrayList<Inquiry> items){
        this.items = items;
    }

    public Inquiry getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, Inquiry item){
        items.set(position, item);
    }

    @NonNull
    @Override
    public InquiryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.inquiry_item, parent, false);

        return new InquiryAdapter.ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull InquiryAdapter.ViewHolder holder, int position) {
        Inquiry item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnInquiryItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(InquiryAdapter.ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTxt, dateTxt, answerCheckTxt, hospitalNameTxt;

        public ViewHolder(View itemView, final OnInquiryItemClickListener listener){
            super(itemView);

            titleTxt = itemView.findViewById(R.id.titleTxt);
            dateTxt = itemView.findViewById(R.id.dateTxt);
            answerCheckTxt = itemView.findViewById(R.id.answerCheckTxt);
            hospitalNameTxt = itemView.findViewById(R.id.hospitalNameTxt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(InquiryAdapter.ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(Inquiry item){
            titleTxt.setText(item.getTitle());
            dateTxt.setText(item.getDate());
            hospitalNameTxt.setText(item.getHospital_name());

            if(item.getAnswer().equals("")){
                answerCheckTxt.setText("미답변");
                answerCheckTxt.setTextColor(Color.parseColor("#ff0000"));
            }else{
                answerCheckTxt.setText("답변완료");
                answerCheckTxt.setTextColor(Color.parseColor("#0000ff"));
            }
        }
    }
}
