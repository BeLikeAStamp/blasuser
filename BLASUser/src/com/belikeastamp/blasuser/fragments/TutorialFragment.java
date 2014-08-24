package com.belikeastamp.blasuser.fragments;


import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.adapter.TutorialAdapter;
import com.belikeastamp.blasuser.util.GlobalVariable;

public class TutorialFragment extends ListFragment {
	private GlobalVariable globalVariable;
	
	public TutorialFragment(){}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		globalVariable = (GlobalVariable) getActivity().getApplicationContext();
		BaseAdapter adapter = new TutorialAdapter(getActivity(), globalVariable.getData().getTutorials());
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// do something with the data

	}
}
