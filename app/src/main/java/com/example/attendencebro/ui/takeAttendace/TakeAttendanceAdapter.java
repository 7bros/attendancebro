package com.example.attendencebro.ui.takeAttendace;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendencebro.R;

import java.util.ArrayList;

public class TakeAttendanceAdapter extends RecyclerView.Adapter<TakeAttendanceAdapter.ViewHolder> {
    ArrayList<String> memberList;
    ArrayList<String> enrollList;

    public TakeAttendanceAdapter(ArrayList<String> memberList) {
        this.memberList=memberList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_member,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(memberList.get(position));

    }

    @Override
    public int getItemCount() {
        return memberList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvEnroll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvEnroll=itemView.findViewById(R.id.tvEnroll);


        }
    }
}
