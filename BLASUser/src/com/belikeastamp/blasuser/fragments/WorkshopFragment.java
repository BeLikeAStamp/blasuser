package com.belikeastamp.blasuser.fragments;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.belikeastamp.blasuser.adapter.WorkshopAdapter;
import com.belikeastamp.blasuser.util.GlobalVariable;

public class WorkshopFragment extends ListFragment {
	private GlobalVariable globalVariable;

	public WorkshopFragment(){}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		globalVariable = (GlobalVariable) getActivity().getApplicationContext();
		BaseAdapter adapter = new WorkshopAdapter(getActivity().getApplicationContext(), globalVariable.getData().getWorkshops());
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// do something with the data
		//SavedProjectsActivity

		// TODO Auto-generated method stub
		//Intent intent = new Intent(getActivity(),SavedProjectsActivity.class);
		//intent.putExtra("project",workshops.get(position));
		//startActivity(intent);

	}
}
