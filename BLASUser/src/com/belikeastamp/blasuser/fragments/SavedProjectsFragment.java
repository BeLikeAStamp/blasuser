package com.belikeastamp.blasuser.fragments;


import java.util.List;

import com.belikeastamp.blasuser.activities.SavedProjectsActivity;
import com.belikeastamp.blasuser.adapter.SavedProjectAdapter;
import com.belikeastamp.blasuser.db.dao.ProjectsData;
import com.belikeastamp.blasuser.db.model.Project;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;


public class SavedProjectsFragment extends ListFragment {

	private ProjectsData datasource;
	public SavedProjectsFragment(){}
	private List<Project> projects;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		datasource = new ProjectsData(getActivity().getApplicationContext());
		datasource.open();
		projects = datasource.getAllWaitingProjects();
		datasource.close();

		BaseAdapter adapter = new SavedProjectAdapter(getActivity().getApplicationContext(), projects);

		/* ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	        android.R.layout.simple_list_item_1, values);*/
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// do something with the data
		//SavedProjectsActivity

		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(),SavedProjectsActivity.class);
		intent.putExtra("project",projects.get(position));
		startActivity(intent);

	}


}
