package com.patanjali.patanjaliiasclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.activity.PersentationActivity;
import com.patanjali.patanjaliiasclasses.activity.RecordingPlayActivity;
import com.patanjali.patanjaliiasclasses.activity.VideoPlayActivity;
import com.patanjali.patanjaliiasclasses.model.FreeDownloadModel;
import com.patanjali.patanjaliiasclasses.model.VedioRecord_Model;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VedioRecordingAdapter extends RecyclerView.Adapter<VedioRecordingAdapter.Holder> {

    private List<VedioRecord_Model> vedioRecord_modelList;
    Context context;
    UserSession userSession;
    Onitemclick onitemclick;
    Config ut;
    Bitmap bitmap = null;
    MediaMetadataRetriever mediaMetadataRetriever = null;

    public VedioRecordingAdapter(Context context, List<VedioRecord_Model> vedioRecord_models,Onitemclick onitemclick) {
        this.context=context;
        this.onitemclick=onitemclick;
        this.vedioRecord_modelList = vedioRecord_models;
        userSession=new UserSession(context);
    }

    public interface Onitemclick{
        void itemclick(int position);
    }

    @NonNull
    @Override
    public VedioRecordingAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recordingvedio_layout, parent, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VedioRecordingAdapter.Holder holder, final int position) {
        VedioRecord_Model vedioRecord_model=vedioRecord_modelList.get(position);
        holder.techernametext.setText(vedioRecord_model.getTeacher());
        holder.topicnametext.setText(vedioRecord_model.getTopic());
        holder.datetext.setText(vedioRecord_model.getDate());
        final String vediourl=vedioRecord_model.getVideourl();
        final String persentationurl=vedioRecord_model.getPresentationurl();
        final String urlType=vedioRecord_model.getUrlType();

        try
        {

            if(urlType.equalsIgnoreCase("mp4"))
            {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.isMemoryCacheable();
                Glide.with(context)
                        .setDefaultRequestOptions(requestOptions)
                        .load(vediourl)
                        .into(holder.vedioimage);

            }
            else if(urlType.equalsIgnoreCase("url"))
            {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.isMemoryCacheable();
                Glide.with(context)
                        .setDefaultRequestOptions(requestOptions)
                        .load(vediourl).into(holder.vedioimage);
            }
            else
            {

            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        holder.vedioimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemclick.itemclick(position);
                Intent bundle=new Intent(context, RecordingPlayActivity.class);
                bundle.putExtra("vediourl",vediourl);
                context.startActivity(bundle);
            }
        });

        holder.topicnametext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemclick.itemclick(position);
                Intent bundle=new Intent(context, PersentationActivity.class);
                bundle.putExtra("persentationurl",persentationurl);
                context.startActivity(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vedioRecord_modelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView datetext,techernametext,topicnametext;
        ImageView vedioimage;

        public Holder(@NonNull View itemView) {
            super(itemView);

            datetext=itemView.findViewById(R.id.datetext);
            techernametext=itemView.findViewById(R.id.techernametext);
            topicnametext=itemView.findViewById(R.id.topicnametext);
            vedioimage=itemView.findViewById(R.id.vedioimage);

        }
    }
}
