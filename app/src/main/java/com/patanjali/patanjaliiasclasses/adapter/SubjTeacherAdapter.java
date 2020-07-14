package com.patanjali.patanjaliiasclasses.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.model.Detail;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubjTeacherAdapter extends RecyclerView.Adapter<SubjTeacherAdapter.Holder> {

    private ArrayList<Detail> subjTecher_modelList;
    Context context;

    public SubjTeacherAdapter(Context context, ArrayList<Detail> subjTecher_models) {
        this.context=context;
        this.subjTecher_modelList = subjTecher_models;
    }

    @NonNull
    @Override
    public SubjTeacherAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subjteacher_layout, parent, false);

        return new SubjTeacherAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjTeacherAdapter.Holder holder, int position) {

        final Detail subjTecher_model=subjTecher_modelList.get(position);
        holder.cardcount.setText(subjTecher_model.getCdid());
        holder.subjectname.setText(subjTecher_model.getSubject());
        holder.teachername.setText(subjTecher_model.getTeacher());
        holder.classcount.setText(subjTecher_model.getNoofclassess());
        holder.duration.setText(subjTecher_model.getClassduration()+" hrs");

    }

    @Override
    public int getItemCount() {
        return subjTecher_modelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView cardcount,subjectname,teachername,classcount,duration;

        public Holder(@NonNull View itemView) {
            super(itemView);
            cardcount=itemView.findViewById(R.id.cardcount);
            subjectname=itemView.findViewById(R.id.subjectname);
            teachername=itemView.findViewById(R.id.teachername);
            classcount=itemView.findViewById(R.id.classcount);
            duration=itemView.findViewById(R.id.duration);
        }
    }
}
