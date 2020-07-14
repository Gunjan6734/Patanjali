package com.patanjali.patanjaliiasclasses.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.SaveSharedPreference;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.fregment.notification.NotificationFragment;
import com.patanjali.patanjaliiasclasses.fregment.recordingvedio.VediosPlayFragment;
import com.patanjali.patanjaliiasclasses.fregment.videoplay.VedioPlayerFragment;
import com.patanjali.patanjaliiasclasses.model.ClassData;
import com.patanjali.patanjaliiasclasses.model.LiveClass_Model;
import com.patanjali.patanjaliiasclasses.service.GetData;
import com.patanjali.patanjaliiasclasses.service.OnTaskCompleted;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import static com.patanjali.patanjaliiasclasses.activity.MainActivity.notifyimage;
import static com.patanjali.patanjaliiasclasses.activity.MainActivity.relativeoutnotify;

public class MoringClassAdapter extends RecyclerView.Adapter<MoringClassAdapter.Holder> {

    private List<ClassData> morningClas_modelList;
    Context context;
    Onitemclick onitemclick;
    UserSession userSession;
    Config ut;
    private List<LiveClass_Model>liveClass_modelList;

    public MoringClassAdapter(Context context, List<ClassData> morningClas_models, Onitemclick onitemclick) {
        this.context=context;
        this.onitemclick=onitemclick;
        this.morningClas_modelList = morningClas_models;
        userSession=new UserSession(context);

    }

    public interface Onitemclick{
        void itemclick(int position);
        void startRecord(int position);

    }

   public void callingApi(ClassData model) {
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
                                     // VedioPlayerFragment vedioPlayerFragment = new VedioPlayerFragment();
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
    public MoringClassAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.morningbatch_layout, parent, false);

        return new MoringClassAdapter.Holder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MoringClassAdapter.Holder holder, final int position) {

        final ClassData morningClas_model=morningClas_modelList.get(position);
        holder.classname.setText(morningClas_model.getClassname());
        holder.bredcrumbs.setText(morningClas_model.getBredcrumbs());
        holder.onlnsetstext1.setText(morningClas_model.getOnlineclassseats());
        holder.onlnfeetext1.setText("₹"+morningClas_model.getOnlineclassfee());
        holder.clsdatetxt.setText(morningClas_model.getStartdate());
        holder.clastimtext.setText(morningClas_model.getStarttime());
        holder.aouttext2.setText(morningClas_model.getClassdescription());
        holder.goalsidtext.setText(morningClas_model.getGoalID());
        //holder.meetingid.setText(morningClas_model.getMeetingID());
        Log.d("kjndknsn","jdnskjnkjfbkjdkjf");

        if (UserSession.getIsLogin(context)){

            if (morningClas_model.getLivestreaming().equals("true")&&morningClas_model.getOnlinepayment().equals("True")){
                holder.buynowbutton.setVisibility(View.GONE);
                holder.recordbuton.setVisibility(View.GONE);
                holder.livebuton.setVisibility(View.VISIBLE);

            } else if (morningClas_model.getOnlinepayment().equals("True")&&morningClas_model.getLivestreaming().equals("false")){
                holder.buynowbutton.setVisibility(View.GONE);
                holder.livebuton.setVisibility(View.GONE);
                holder.recordbuton.setVisibility(View.VISIBLE);

            } else if(morningClas_model.getOnlinepayment().equals("False")&&morningClas_model.getLivestreaming().equals("false")){
                holder.buynowbutton.setVisibility(View.VISIBLE);
                holder.livebuton.setVisibility(View.GONE);
                holder.recordbuton.setVisibility(View.GONE);

            }else if (morningClas_model.getOnlinepayment().equals("False")&&morningClas_model.getLivestreaming().equals("true")) {
                holder.buynowbutton.setVisibility(View.VISIBLE);
                holder.recordbuton.setVisibility(View.GONE);
                holder.livebuton.setVisibility(View.GONE);

            }else{
                holder.buynowbutton.setVisibility(View.VISIBLE);
                holder.recordbuton.setVisibility(View.GONE);
                holder.livebuton.setVisibility(View.GONE);
            }
        }else{
            if(morningClas_model.getOnlinepayment().equals("True")&&morningClas_model.getLivestreaming().equals("false")){
                holder.buynowbutton.setVisibility(View.GONE);
                holder.recordbuton.setVisibility(View.VISIBLE);
                holder.livebuton.setVisibility(View.GONE);

            }else {
                holder.buynowbutton.setVisibility(View.VISIBLE);
                holder.recordbuton.setVisibility(View.GONE);
                holder.livebuton.setVisibility(View.GONE);
            }
        }

        holder.livebuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callingApi(morningClas_modelList.get(position));

            }
        });

        holder.buynowbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (morningClas_model.getIsenquiry().equals("True")&&morningClas_model.getOnlinepayment().equals("False")){
                    openAlertDialog();
                }else {
                    onitemclick.itemclick(position);
                }
            }
        });

        holder.recordbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemclick.startRecord(position);
               /* VediosPlayFragment fragmentB=new VediosPlayFragment();
                Bundle bundle=new Bundle();
                bundle.putString("meetingID",morningClas_model.getMeetingID());
                fragmentB.setArguments(bundle);*/

                String meetingID=morningClas_model.getMeetingID();
                SaveSharedPreference.setSession_Token(meetingID);
                Log.d("meetingID",SaveSharedPreference
                        .getSession_Token());

            //    Log.d("njdkjnjkd",""+morningClas_model.getMeetingID());
              //  Toast.makeText(context, ""+morningClas_model.getMeetingID(), Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void openAlertDialog() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage("Your purchase enquiry is already submitted for this batch, back-office team will connect you very soon.!!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return morningClas_modelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView goalsidtext,classname,bredcrumbs,onlnsetstext1,offlnsetstext2,onlnfeetext1,offlnfeetext2,clsdatetxt,clastimtext,aouttext2,meetingid;
        Button buynowbutton,livebuton,recordbuton;

        public Holder(@NonNull View itemView) {
            super(itemView);
            goalsidtext=itemView.findViewById(R.id.goalsidtext);
            bredcrumbs=itemView.findViewById(R.id.bredcrumbs);
            classname=itemView.findViewById(R.id.classname);
            onlnsetstext1=itemView.findViewById(R.id.onlnsetstext1);
            onlnfeetext1=itemView.findViewById(R.id.onlnfeetext1);
            clsdatetxt=itemView.findViewById(R.id.clsdatetxt);
            clastimtext=itemView.findViewById(R.id.clastimtext);
            aouttext2=itemView.findViewById(R.id.aouttext2);
//            meetingid=itemView.findViewById(R.id.meetingid);
            buynowbutton=itemView.findViewById(R.id.buynowbutton);
            livebuton=itemView.findViewById(R.id.livebuton);
            recordbuton=itemView.findViewById(R.id.recordbuton);

        }
    }
}
