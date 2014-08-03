package com.belikeastamp.blasuser.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.belikeastamp.blasuser.R;

public class HomeFragment extends Fragment {
	
	public HomeFragment(){}
	ImageView avatar;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }
}
