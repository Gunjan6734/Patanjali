package com.patanjali.patanjaliiasclasses.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.model.Spinerlist_Model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpineerListAdapter extends RecyclerView.Adapter<SpineerListAdapter.Holder> {

    private List<Spinerlist_Model> spinnerlistdata_models;
    Context context;

    public SpineerListAdapter(Context context, List<Spinerlist_Model> spinerlist_modelList) {
        this.context=context;
        this.spinnerlistdata_models = spinerlist_modelList;
    }
    public interface OnSelect{
      //  void onChecked
    }
    @NonNull
    @Override
    public SpineerListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spinnerlist_layout, parent, false);

        return new SpineerListAdapter.Holder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final SpineerListAdapter.Holder holder, final int position) {

        final Spinerlist_Model spinerlistModel=spinnerlistdata_models.get(position);
        holder.selectradiobtn.setText(spinerlistModel.getValue());
        holder.boardtext.setText(spinerlistModel.getData());


       /* holder.selectradiobtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (ischecked) {
                   //holder.selectradiobtn.setTextColor(context.getResources().getColor(R.color.colorgreen));
                   ischecked = false;
               }else {
                  // holder.selectradiobtn.setTextColor(context.getResources().getColor(R.color.colorrgray));
                   ischecked = true;
               }
           }
       });*/

    }

    @Override
    public int getItemCount() {
        return spinnerlistdata_models.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView boardtext;
        RadioButton selectradiobtn;

        public Holder(@NonNull View itemView) {
            super(itemView);

            boardtext=itemView.findViewById(R.id.boardtext);
            selectradiobtn=itemView.findViewById(R.id.selectradiobtn);
        }
    }
}
