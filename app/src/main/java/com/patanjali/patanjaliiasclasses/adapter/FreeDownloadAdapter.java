package com.patanjali.patanjaliiasclasses.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.activity.ImageViewActivity;
import com.patanjali.patanjaliiasclasses.activity.PdfViewActivity;
import com.patanjali.patanjaliiasclasses.activity.VideoPlayActivity;
import com.patanjali.patanjaliiasclasses.fregment.videoplay.VedioPlayerFragment;
import com.patanjali.patanjaliiasclasses.model.ClassData;
import com.patanjali.patanjaliiasclasses.model.FreeDownloadModel;
import com.patanjali.patanjaliiasclasses.model.LiveClass_Model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FreeDownloadAdapter extends RecyclerView.Adapter<FreeDownloadAdapter.ViewHolder> {

    private List<FreeDownloadModel> freeDownloadModelList;
    Context context;
    UserSession userSession;
    Onitemclick onitemclick;
    Config ut;

    public FreeDownloadAdapter(Context context, List<FreeDownloadModel> freeDownloadModels,Onitemclick onitemclick) {
        this.context=context;
        this.onitemclick=onitemclick;
        this.freeDownloadModelList = freeDownloadModels;
        userSession=new UserSession(context);
    }

    public interface Onitemclick{
        void itemclick(int position);
    }

    @NonNull
    @Override
    public FreeDownloadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.free_downloader_layout, parent, false);

        return new FreeDownloadAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FreeDownloadAdapter.ViewHolder holder, final int position) {

        FreeDownloadModel freeDownloadModel=freeDownloadModelList.get(position);
       // holder.fd_create.setText(freeDownloadModel.getCreatedate());
        holder.fd_description.setText(freeDownloadModel.getDescription());
        holder.fd_name.setText(freeDownloadModel.getTitle());
        final String url=freeDownloadModel.getUrl();

        if (freeDownloadModel.getType().equals("video")) {
            holder.fd_view.setVisibility(View.GONE);
            holder.view_fd3.setVisibility(View.GONE);
            holder.view_fd2.setVisibility(View.VISIBLE);

        }else if (freeDownloadModel.getType().equals("Image")){
            holder.fd_view.setVisibility(View.GONE);
            holder.view_fd3.setVisibility(View.VISIBLE);
            holder.view_fd2.setVisibility(View.GONE);

        }else if (freeDownloadModel.getType().equals("pdf")){
            holder.fd_view.setVisibility(View.VISIBLE);
            holder.view_fd3.setVisibility(View.GONE);
            holder.view_fd2.setVisibility(View.GONE);

        }else {

        }

        holder.view_fd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemclick.itemclick(position);
                Intent bundle = new Intent(context, VideoPlayActivity.class);
                bundle.putExtra("url",url);
                context.startActivity(bundle);
            }
        });

        holder.view_fd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemclick.itemclick(position);
                Intent bundle = new Intent(context, ImageViewActivity.class);
                bundle.putExtra("url",url);
                context.startActivity(bundle);
            }
        });

        holder.fd_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemclick.itemclick(position);
                /*Intent bundle = new Intent(context, PdfViewActivity.class);
                bundle.putExtra("url",url);
                context.startActivity(bundle);*/
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(url.replaceAll(" ","%20")),"application/pdf");
                context.startActivity(intent);            }
        });
    }

    @Override
    public int getItemCount() {
        return freeDownloadModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fd_name,fd_description,view_fd2,view_fd3;
        Button fd_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //fd_create=itemView.findViewById(R.id.fd_create);
            fd_name=itemView.findViewById(R.id.fd_name);
            fd_description=itemView.findViewById(R.id.fd_description);
            fd_view=itemView.findViewById(R.id.view_fd);
            view_fd2=itemView.findViewById(R.id.view_fd2);
            view_fd3=itemView.findViewById(R.id.view_fd3);
        }
    }
}
