package com.patanjali.patanjaliiasclasses.fregment.logout;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.UserSession;
import com.patanjali.patanjaliiasclasses.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogOutFragment extends Fragment {

    Button logoutbtn;
    UserSession userSession;

    public LogOutFragment() {
        // Required empty public constructor
    }
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_log_out, container, false);

        userSession = new UserSession(getActivity());
        logoutbtn=root.findViewById(R.id.logoutbtn);
        mainActivity= (MainActivity) getActivity();
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutService();
            }
        });

        return root;
    }

    private void LogoutService() {
            final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setCancelable(false);
            builder.setTitle("Exit");
            builder.setMessage("Are you sure you want to leave ?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(DialogInterface dialog, int which) {

                        mainActivity.navigationView.getMenu().findItem(R.id.nav_mylibrary).setVisible(false);
                        mainActivity.navigationView.getMenu().findItem(R.id.nav_aboutus).setVisible(false);
                        mainActivity.navigationView.getMenu().findItem(R.id.nav_yourprofile).setVisible(false);
                        mainActivity.navigationView.getMenu().findItem(R.id.nav_contact).setVisible(false);
                        mainActivity.navigationView.getMenu().findItem(R.id.nav_logoutfregment).setVisible(false);

                    // Clear the session data
                    // This will clear all session data and
                    // redirect user to LoginActivity
                    userSession.logoutUser();
                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    getActivity().finishAffinity();

                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //dialog gone..
                    dialog.dismiss();
                }
            });
            // create the dialog and show it..
            builder.create().show();
        }
    }
