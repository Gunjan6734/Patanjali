package com.patanjali.patanjaliiasclasses.fregment.freedownloads;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.patanjali.patanjaliiasclasses.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreeDownloadViewFragment extends Fragment {



    public FreeDownloadViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_free_download_view, container, false);



        return root;
    }

}
