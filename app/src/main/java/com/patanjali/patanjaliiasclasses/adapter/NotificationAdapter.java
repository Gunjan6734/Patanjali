package com.patanjali.patanjaliiasclasses.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.model.Notification_Model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Holder> {

    private List<Notification_Model> notificationModelList;
    Context context;
    UserSession userSession;
    Config ut;

    public NotificationAdapter(Context context, List<Notification_Model> notification_models) {
        this.context=context;
        this.notificationModelList = notification_models;
        userSession=new UserSession(context);
    }

    @NonNull
    @Override
    public NotificationAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notifications_layout, parent, false);

        return new NotificationAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.Holder holder, final int position) {
        Notification_Model notification_model=notificationModelList.get(position);
        holder.notifydate.setText(notification_model.getDate());
        holder.notififytitle.setText(notification_model.getNotification());
        holder.notififyfulltitle.setText(notification_model.getNotification());
        holder.demonotififytitle.setText(notification_model.getNotification());

        if (UserSession.getIsLogin(context)) {

            if (notification_model.getReadStatus().equals("false")) {
                holder.notifynewbutton.setVisibility(View.VISIBLE);
                holder.demoshowlayout.setVisibility(View.VISIBLE);
                holder.fullmsgshowlayout.setVisibility(View.GONE);

            } else if (notification_model.getReadStatus().equals("true")) {
                holder.notifynewbutton.setVisibility(View.GONE);
                holder.demoshowlayout.setVisibility(View.GONE);
                holder.fullmsgshowlayout.setVisibility(View.VISIBLE);

            } else {

            }
            holder.demoshowlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.demoshowlayout.setVisibility(View.GONE);
                    holder.fullmsgshowlayout.setVisibility(View.VISIBLE);
                   // holder.notifydate.setVisibility(View.VISIBLE);
                    holder.notifynewbutton.setVisibility(View.GONE);
                }
            });
            holder.demonotifynewbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.demoshowlayout.setVisibility(View.GONE);
                    holder.fullmsgshowlayout.setVisibility(View.VISIBLE);
                   // holder.notifydate.setVisibility(View.VISIBLE);
                    holder.notifynewbutton.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView demonotififytitle,demonotifynewbutton,notifydate,notififytitle,notififyfulltitle,notifynewbutton;
        ImageView notifyicons;
        LinearLayout demoshowlayout,fullmsgshowlayout;


        public Holder(@NonNull View itemView) {
            super(itemView);

            demonotifynewbutton=itemView.findViewById(R.id.demonotifynewbutton);
            demonotififytitle=itemView.findViewById(R.id.demonotififytitle);
            notifydate=itemView.findViewById(R.id.notifydate);
            notififytitle=itemView.findViewById(R.id.notififytitle);
            notififyfulltitle=itemView.findViewById(R.id.notififyfulltitle);
            notifyicons=itemView.findViewById(R.id.notifyicons);
            notifynewbutton=itemView.findViewById(R.id.notifynewbutton);

            fullmsgshowlayout=itemView.findViewById(R.id.fullmsgshowlayout);
            demoshowlayout=itemView.findViewById(R.id.demoshowlayout);

        }
    }
}
