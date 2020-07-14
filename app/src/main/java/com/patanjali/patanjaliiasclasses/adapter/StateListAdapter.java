package com.patanjali.patanjaliiasclasses.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.model.State_Model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StateListAdapter extends RecyclerView.Adapter<StateListAdapter.Holder> {

    private List<State_Model> state_modelList;
    Context context;

    public StateListAdapter(Context context, List<State_Model> state_modelList) {
        this.context=context;
        this.state_modelList = state_modelList;
    }
    public interface OnSelect{
      //  void onChecked
    }
    @NonNull
    @Override
    public StateListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.statelist_layout, parent, false);

        return new StateListAdapter.Holder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final StateListAdapter.Holder holder, final int position) {

        final State_Model spinerlistModel=state_modelList.get(position);
        holder.selectradiobutn.setText(spinerlistModel.getValue());
        holder.statetext.setText(spinerlistModel.getData());

    }

    @Override
    public int getItemCount() {
        return state_modelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView statetext;
        RadioButton selectradiobutn;

        public Holder(@NonNull View itemView) {
            super(itemView);

            statetext=itemView.findViewById(R.id.statetext);
            selectradiobutn=itemView.findViewById(R.id.selectradiobutn);
        }
    }
}
