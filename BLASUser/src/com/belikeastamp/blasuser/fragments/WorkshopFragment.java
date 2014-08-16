package com.belikeastamp.blasuser.fragments;


import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.belikeastamp.blasuser.activities.SavedProjectsActivity;
import com.belikeastamp.blasuser.adapter.WorkshopAdapter;
import com.belikeastamp.blasuser.db.dao.ProjectsData;
import com.belikeastamp.blasuser.db.model.Workshop;

public class WorkshopFragment extends ListFragment {
	private ProjectsData datasource;
	private List<Workshop> workshops;


	public WorkshopFragment(){}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		workshops = new ArrayList<Workshop>();
		workshops.add(new Workshop("Audrey", "Quebec", "le monde", "Quebec", "01/10/14", 3, 3, 200));
		workshops.add(new Workshop("Js", "Quebec", "le monde", "Quebec", "01/10/14", 10, 3, 21));
		workshops.add(new Workshop("Kratos", "Quebec", "le monde", "Quebec", "01/10/14", 10, 0, 21));
		
		BaseAdapter adapter = new WorkshopAdapter(getActivity().getApplicationContext(), workshops);
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
