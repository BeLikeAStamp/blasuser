package com.belikeastamp.blasuser.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.db.dao.Datasource;
import com.belikeastamp.blasuser.util.GlobalVariable;
import com.belikeastamp.blasuser.util.asynctask.AsyncTaskManager;
import com.belikeastamp.blasuser.util.asynctask.MyAbstractAsyncTask;
import com.belikeastamp.blasuser.util.asynctask.OnTaskCompleteListener;
import com.belikeastamp.blasuser.util.asynctask.Request4RemoteDataTask;

public class PreMainActivity extends Activity implements OnTaskCompleteListener {
	private GlobalVariable globalVariable;
	private Datasource datasource;
	private AsyncTaskManager mAsyncTaskManager;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pre_activity_main);	

		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setHomeButtonEnabled(false);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.banniere));

		mAsyncTaskManager = new AsyncTaskManager(this, this);
		globalVariable = (GlobalVariable) getApplicationContext();

		if(isOnline()) {

			if(loadRemoteData() && loadLocalData()) {
				Intent i = new Intent(PreMainActivity.this, MainActivity.class);
				startActivity(i);
			}
			else
			{
				Toast.makeText(getApplicationContext(), R.string.alert_loading_data, Toast.LENGTH_LONG).show();
				finish();
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

		Request4RemoteDataTask request = new Request4RemoteDataTask(getResources());
		request.setActivity(PreMainActivity.this);
		mAsyncTaskManager.setupTask(request);
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


	protected void onResume() {
		super.onResume();
	}


	@Override
	public void onTaskComplete(MyAbstractAsyncTask task) {
		
		if (task.isCancelled()) {
			// Report about cancel
			Toast.makeText(getApplicationContext(),  getResources().getString(R.string.task_cancelled), Toast.LENGTH_LONG)
			.show();
		} else {
			// Get result
			Boolean result = null;
			try {
				result = task.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Report about result
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.task_completed, (result != null) ? result.toString() : "null"),
					Toast.LENGTH_LONG).show();
		}
	}

}
