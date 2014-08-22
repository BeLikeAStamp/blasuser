package com.belikeastamp.blasuser.fragments;


import com.belikeastamp.blasuser.activities.SavedProjectsActivity;
import com.belikeastamp.blasuser.adapter.SavedProjectAdapter;
import com.belikeastamp.blasuser.util.GlobalVariable;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;


public class SavedProjectsFragment extends ListFragment {
	private GlobalVariable globalVariable;
	
	public SavedProjectsFragment(){}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		globalVariable = (GlobalVariable) getActivity().getApplicationContext();
		BaseAdapter adapter = new SavedProjectAdapter(getActivity().getApplicationContext(), globalVariable.getData().getSavedProjects());
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// do something with the data
		//SavedProjectsActivity

		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(),SavedProjectsActivity.class);
		intent.putExtra("project",globalVariable.getData().getSavedProjects().get(position));
		startActivity(intent);

	}


}
