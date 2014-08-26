package com.belikeastamp.blasuser.activities;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.db.dao.Datasource;
import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.db.model.Tutorial;
import com.belikeastamp.blasuser.db.model.Workshop;
import com.belikeastamp.blasuser.util.GlobalVariable;
import com.belikeastamp.blasuser.util.ProjectController;
import com.belikeastamp.blasuser.util.TutorialController;
import com.belikeastamp.blasuser.util.WorkshopController;

public class PreMainActivity extends Activity {
	public static final String CLOSE = "close";
	private GlobalVariable globalVariable;
	private Datasource datasource;
	private Long userId = Long.valueOf(-1);
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pre_activity_main);
		globalVariable = (GlobalVariable) getApplicationContext();
		userId = getSharedPreferences("BLAS", MODE_PRIVATE).getLong("user_id", Long.valueOf(-1));
		
		if(isOnline()) {
						
			if(loadRemoteData() && loadLocalData()) {
				Intent i = new Intent(PreMainActivity.this, MainActivity.class);
				startActivity(i);
			}
			else
			{
				Toast.makeText(getApplicationContext(), R.string.alert_loading_data, Toast.LENGTH_LONG).show();
				//finish();
			}
			
		}
		else
		{

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PreMainActivity.this);
			alertDialogBuilder.setTitle(getResources().getString(R.string.cxn_alert));
			alertDialogBuilder
			.setMessage(getResources().getString(R.string.no_connection))
			.setCancelable(false)
			.setPositiveButton("OK",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {

					PreMainActivity.this.finish();
				}
			});
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}
	}


	private boolean loadLocalData() {
		datasource = new Datasource(getApplicationContext());
		datasource.open();
		globalVariable.getData().setSavedProjects(datasource.getAllWaitingProjects());
		datasource.close();
		return true;
	}


	private boolean loadRemoteData() {
		// Projets soumis
		
		if(!userId.equals(Long.valueOf(-1))) new Request4Project().execute();
		new Request4Tuto().execute();
		new Request4Workshop().execute();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(globalVariable.getData().getWorkshops().size() == 0 
				&& globalVariable.getData().getTutorials().size() == 0)
			return false;
		
		return true;
	}


	public boolean isOnline() {
		ConnectivityManager cm =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
	
	private class Request4Tuto extends AsyncTask<Void, Void, Void> {

		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground(Void... params) {
			TutorialController c = new TutorialController();

			try {
				globalVariable.getData().setTutorials((List<Tutorial>) c.getAllTutorials());
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

	}
	
	private class Request4Workshop extends AsyncTask<Void, Void, Void> {

		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground(Void... params) {
			WorkshopController c = new WorkshopController();
			try {
				globalVariable.getData().setWorkshops((List<Workshop>) c.getAllWorkshops());
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

	}
	

	private class Request4Project extends AsyncTask<Void, Void, Void> {

		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground(Void... params) {
			
			ProjectController c = new ProjectController();

			try {
				globalVariable.getData().setSubmitProjects((List<Project>)c.getAllProjects(userId));
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

	}
	
	protected void onResume() {
		super.onResume();
	}
	
}
