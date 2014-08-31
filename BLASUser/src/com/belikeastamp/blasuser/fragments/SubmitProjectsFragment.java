package com.belikeastamp.blasuser.fragments;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListFragment;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.activities.PreMainActivity;
import com.belikeastamp.blasuser.activities.SubmitProjectsActivity;
import com.belikeastamp.blasuser.adapter.SubmitProjectAdapter;
import com.belikeastamp.blasuser.db.model.User;
import com.belikeastamp.blasuser.util.GlobalVariable;
import com.belikeastamp.blasuser.util.UserController;
import com.belikeastamp.blasuser.util.asynctask.AsyncTaskManager;


public class SubmitProjectsFragment extends ListFragment {
	private GlobalVariable globalVariable;
	private Long id;
	
	public SubmitProjectsFragment(){}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		globalVariable = (GlobalVariable) getActivity().getApplicationContext();
		String[] status = getResources().getStringArray(R.array.status_arrays);

		BaseAdapter adapter = new SubmitProjectAdapter(getActivity().getApplicationContext(), globalVariable.getData().getSubmitProjects(), status);
		setListAdapter(adapter);

		if(globalVariable.getData().getSubmitProjects().size() == 0 && !isRegistred()) {
			displayDialogWindow();
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// do something with the data
		Intent intent = new Intent(getActivity(),SubmitProjectsActivity.class);
		intent.putExtra("project",globalVariable.getData().getSubmitProjects().get(position));
		startActivity(intent);
	}

	public void displayDialogWindow()
	{
		final AlertDialog.Builder loginDialog = new AlertDialog.Builder(getActivity());
		final View f = getActivity().getLayoutInflater().inflate(R.layout.prompt_user_email, null);
		loginDialog.setTitle(getActivity().getResources().getString(R.string.load_existing_project));
		loginDialog.setView(f);
		
		Spinner emailSpinner = (Spinner)f.findViewById(R.id.email);
		
		Account[] accounts = AccountManager.get(getActivity()).getAccountsByType("com.google");
		final List<String> items =  new ArrayList<String>();

		for (int i = 0; i < accounts.length; i++)
			items.add(accounts[i].name);
		
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,items);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		emailSpinner.setAdapter(dataAdapter);
		emailSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				globalVariable.setUserEmail((String)arg0.getSelectedItem());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});
		
		loginDialog.setPositiveButton(getActivity().getResources().getString(R.string.btn_reload),new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				registration(globalVariable.getUserEmail());
				Intent i2 = new Intent(getActivity(), PreMainActivity.class);
				startActivity(i2);
			}
		});
		
		loginDialog.show();

		
	}
	
	private Boolean isRegistred() {
		// TODO Auto-generated method stub
		id = getActivity().getSharedPreferences("BLAS", getActivity().MODE_PRIVATE).getLong("user_id", Long.valueOf(-1));
		Log.d("Submission", "USER ID "+id);
		return (!(id.equals(Long.valueOf(-1))));
	}
	
	private void registration(String email) {
		// TODO Auto-generated method stub
		Log.d("Submission","go to AddUserTask");
		User user =  new User(email);
		try {
			Log.d("Submission", "registration "+id);
			if(new AddUserTask().execute(user).get())
			{
				Toast.makeText(getActivity().getApplicationContext(), R.string.reg_succed, Toast.LENGTH_SHORT).show();
				getActivity().getSharedPreferences("BLAS", getActivity().MODE_PRIVATE).edit().putLong("user_id", id).commit();
			}
			else
			{
				Toast.makeText(getActivity().getApplicationContext(), R.string.reg_failed, Toast.LENGTH_SHORT).show();
			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class AddUserTask extends AsyncTask<User, Void, Boolean> {

		@Override
		protected Boolean doInBackground(User... params) {
			// TODO Auto-generated method stub
			Log.d("Submission","AddUserTask doInBackground");

			User u = params[0];
			u.setFirstname("");
			u.setName("");
			u.setPhone("");
			u.setAddress("");
			u.setIsHost(false);
			u.setIsPartener(false);

			final UserController c = new UserController();
			try {

				// check user id d'abord...
				id = c.getUserId(u.getEmail());
				if(id.equals(Long.valueOf(-1))) {
					Log.d("Submission", "AddUserTask unknown user");
					c.create(u);
					id = c.getUserId(u.getEmail());
					Log.d("Submission", "AddUserTask user id = "+id);
				}
				else
				{
					Log.d("Submission", "AddUserTask already known user : "+id);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return (!(id.equals(Long.valueOf(-1))));
		}	
	}
	
}
