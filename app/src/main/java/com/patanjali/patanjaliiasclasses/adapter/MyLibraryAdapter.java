package com.patanjali.patanjaliiasclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.fregment.videoplay.VedioPlayerFragment;
import com.patanjali.patanjaliiasclasses.model.ClassData;
import com.patanjali.patanjaliiasclasses.model.LiveClass_Model;
import com.patanjali.patanjaliiasclasses.model.MorningClas_Model;
import com.patanjali.patanjaliiasclasses.service.GetData;
import com.patanjali.patanjaliiasclasses.service.OnTaskCompleted;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyLibraryAdapter extends RecyclerView.Adapter<MyLibraryAdapter.Holder> {

   // private List<MorningClas_Model> morningClas_modelList;
    private List<ClassData> morningClas_modelList;
    Context context;
    UserSession userSession;
    Config ut;
    private List<LiveClass_Model>liveClass_modelList;

    public MyLibraryAdapter(Context context, List<ClassData> morningClas_models) {
        this.context=context;
        this.morningClas_modelList = morningClas_models;
        userSession=new UserSession(context);
    }

    private void callingApi(ClassData model) {
        try {
            new GetData(context, new JSONObject(), ut.BASE_URL +"joinmeeting/"+ut.Securetkey+"/"+userSession.getMobile()+"/"+model.getMeetingID(),
                    new OnTaskCompleted<String>() {
                        @Override
                        public void onTaskCompleted(String response) {
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                JSONObject object=jsonArray.getJSONObject(0);
                                int responseCode=object.getInt("responsecode");
                                if (responseCode==1){
                                    String url=object.getString("message");
                                    try {
                                        Intent bundle = new Intent(context, VedioPlayerFragment.class);
                                        bundle.putExtra("url",url);
                                        context.startActivity(bundle);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    },true).execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public MyLibraryAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mylibrary_layout, parent, false);

        return new MyLibraryAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLibraryAdapter.Holder holder, final int position) {
        final ClassData morningClas_model=morningClas_modelList.get(position);
        holder.classname004.setText(morningClas_model.getClassname());
        holder.bredcrumbs004.setText(morningClas_model.getBredcrumbs());
        holder.clsdatetxt004.setText(morningClas_model.getStartdate());
        holder.clastimtext004.setText(morningClas_model.getStarttime());
        holder.goalsidtext004.setText(morningClas_model.getGoalID());
        holder.meetingid004.setText(morningClas_model.getMeetingID());

        if (morningClas_model.getLivestreaming().equals("true")){
            holder.imagerecoding.setVisibility(View.GONE);
            holder.imagelive.setVisibility(View.VISIBLE);

        }else if (morningClas_model.getLivestreaming().equals("false")){
            holder.imagerecoding.setVisibility(View.VISIBLE);
            holder.imagelive.setVisibility(View.GONE);
        }

        holder.imagelive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callingApi(morningClas_modelList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return morningClas_modelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView goalsidtext004,classname004,bredcrumbs004,clsdatetxt004,clastimtext004,meetingid004;
        ImageView imagerecoding,imagelive;

        public Holder(@NonNull View itemView) {
            super(itemView);

            goalsidtext004=itemView.findViewById(R.id.goalsidtext004);
            bredcrumbs004=itemView.findViewById(R.id.bredcrumbs004);
            classname004=itemView.findViewById(R.id.classname004);
            clsdatetxt004=itemView.findViewById(R.id.clsdatetxt004);
            clastimtext004=itemView.findViewById(R.id.clastimtext004);
            meetingid004=itemView.findViewById(R.id.meetingid004);
            imagerecoding=itemView.findViewById(R.id.imagerecoding);
            imagelive=itemView.findViewById(R.id.imagelive);
        }
    }
}
