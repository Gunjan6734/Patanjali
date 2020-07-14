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

public class SubjectTeacher2Adapter extends RecyclerView.Adapter<SubjectTeacher2Adapter.Holder> {

    private ArrayList<Detail> subjTecher_modelList;
    Context context;

    public SubjectTeacher2Adapter(Context context, ArrayList<Detail> subjTecher_models) {
        this.context=context;
        this.subjTecher_modelList = subjTecher_models;
    }

    @NonNull
    @Override
    public SubjectTeacher2Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subjectteacher2_layout, parent, false);

        return new SubjectTeacher2Adapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectTeacher2Adapter.Holder holder, int position) {

        final Detail subjTecher_model=subjTecher_modelList.get(position);
        holder.cardcount02.setText(subjTecher_model.getCdid());
        holder.subjectname02.setText(subjTecher_model.getSubject());
        holder.teachername02.setText(subjTecher_model.getTeacher());
        holder.classcount02.setText(subjTecher_model.getNoofclassess());
        holder.duration02.setText(subjTecher_model.getClassduration()+" hrs");

    }

    @Override
    public int getItemCount() {
        return subjTecher_modelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView cardcount02,subjectname02,teachername02,classcount02,duration02;

        public Holder(@NonNull View itemView) {
            super(itemView);
            cardcount02=itemView.findViewById(R.id.cardcount02);
            subjectname02=itemView.findViewById(R.id.subjectname02);
            teachername02=itemView.findViewById(R.id.teachername02);
            classcount02=itemView.findViewById(R.id.classcount02);
            duration02=itemView.findViewById(R.id.duration02);
        }
    }
}
