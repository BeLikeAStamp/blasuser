package com.belikeastamp.blasuser.fragments;


import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.activities.MainActivity;
import com.belikeastamp.blasuser.activities.PreMainActivity;
import com.belikeastamp.blasuser.activities.SavedProjectsActivity;
import com.belikeastamp.blasuser.adapter.SavedProjectAdapter;
import com.belikeastamp.blasuser.util.GlobalVariable;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class SavedProjectsFragment extends ListFragment {
	private GlobalVariable globalVariable;

	public SavedProjectsFragment(){}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		globalVariable = (GlobalVariable) getActivity().getApplicationContext();
		BaseAdapter adapter = new SavedProjectAdapter(getActivity(), globalVariable.getData().getSavedProjects());
		setListAdapter(adapter);
		
		if(globalVariable.getData().getSavedProjects().size() == 0)
				displayDialogWindow();

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

	public void displayDialogWindow()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

		// set title
		alertDialogBuilder.setTitle("Reload");

		// set dialog message
		alertDialogBuilder
		.setMessage(getActivity().getResources().getString(R.string.reload_advice))
		.setCancelable(false)
		.setPositiveButton(getActivity().getResources().getString(R.string.btn_reload),new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				Intent i2 = new Intent(getActivity(), PreMainActivity.class);
				startActivity(i2);
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}


}
