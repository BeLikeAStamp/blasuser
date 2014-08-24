package com.belikeastamp.blasuser.fragments;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.adapter.SubmitProjectAdapter;
import com.belikeastamp.blasuser.util.GlobalVariable;


public class SubmitProjectsFragment extends ListFragment {
	private GlobalVariable globalVariable;
	
	public SubmitProjectsFragment(){}
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    globalVariable = (GlobalVariable) getActivity().getApplicationContext();
	    String[] status = getResources().getStringArray(R.array.status_arrays);
	   
	    BaseAdapter adapter = new SubmitProjectAdapter(getActivity().getApplicationContext(), globalVariable.getData().getSubmitProjects(), status);
	    setListAdapter(adapter);
	  }

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // do something with the data
	  }

}
