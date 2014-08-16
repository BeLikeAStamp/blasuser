package com.belikeastamp.blasuser.fragments;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.belikeastamp.blasuser.adapter.WorkshopAdapter;
import com.belikeastamp.blasuser.db.model.Workshop;
import com.belikeastamp.blasuser.util.WorkshopController;

public class WorkshopFragment extends ListFragment {
	private List<Workshop> workshops;


	public WorkshopFragment(){}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		workshops = new ArrayList<Workshop>();
		
		Request request = new Request();
		try {
			workshops = (List<Workshop>) request.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
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
	
	private class Request extends AsyncTask<Void, Void, List<Workshop>> {

		@SuppressWarnings("unchecked")
		@Override
		protected List<Workshop> doInBackground(Void... params) {
			WorkshopController c = new WorkshopController();
			@SuppressWarnings("rawtypes")
			List lists = new ArrayList<Workshop>();

			try {
				lists = c.getAllWorkshops();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return lists;
		}

	}
}
