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

import com.belikeastamp.blasuser.adapter.TutorialAdapter;
import com.belikeastamp.blasuser.db.model.Tutorial;
import com.belikeastamp.blasuser.util.TutorialController;

public class TutorialFragment extends ListFragment {
	
	private List<Tutorial> tutorials;
	
	public TutorialFragment(){}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		/*tutorials = new ArrayList<Tutorial>();
		tutorials.add(new Tutorial("Audrey", true, "Quebec", "01/10/14", 200));
		tutorials.add(new Tutorial("Js", true, "Quebec", "01/10/14", 21));
		tutorials.add(new Tutorial("Kratos", false, "Quebec", "01/10/14", 0));*/
		
		
		Request request = new Request();
		try {
			tutorials = (List<Tutorial>) request.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
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
	
	private class Request extends AsyncTask<Void, Void, List<Tutorial>> {

		@SuppressWarnings("unchecked")
		@Override
		protected List<Tutorial> doInBackground(Void... params) {
			TutorialController c = new TutorialController();
			@SuppressWarnings("rawtypes")
			List lists = new ArrayList<Tutorial>();

			try {
				lists = c.getAllTutorials();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return lists;
		}

	}
	
}
