package com.belikeastamp.blasuser.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belikeastamp.blasuser.R;

public class TutorialFragment extends Fragment {
	
	public TutorialFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_tuto, container, false);
         
        return rootView;
    }
}
