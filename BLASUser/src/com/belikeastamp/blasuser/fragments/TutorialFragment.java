package com.belikeastamp.blasuser.fragments;


import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.belikeastamp.blasuser.adapter.TutorialAdapter;
import com.belikeastamp.blasuser.db.dao.ProjectsData;
import com.belikeastamp.blasuser.db.model.Tutorial;

public class TutorialFragment extends ListFragment {
	
	private ProjectsData datasource;
	private List<Tutorial> tutorials;
	
	public TutorialFragment(){}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		tutorials = new ArrayList<Tutorial>();
		tutorials.add(new Tutorial("Audrey", true, "Quebec", "01/10/14", 200));
		tutorials.add(new Tutorial("Js", true, "Quebec", "01/10/14", 21));
		tutorials.add(new Tutorial("Kratos", false, "Quebec", "01/10/14", 0));
		
		BaseAdapter adapter = new TutorialAdapter(getActivity().getApplicationContext(), tutorials);
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
