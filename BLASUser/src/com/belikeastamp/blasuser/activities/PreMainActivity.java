package com.belikeastamp.blasuser.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.belikeastamp.blasuser.R;

public class PreMainActivity extends Activity {
	public static final String CLOSE = "close";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pre_activity_main);
		
		if(getIntent().getExtras() != null) {
			Log.d("BALSUSER", "!!!!");
			if(getIntent().getExtras().getBoolean(CLOSE)) {
				Log.d("BALSUSER", "?????");
				finish();
			}
			
		}
				
		if(isOnline()) {
			Intent i = new Intent(PreMainActivity.this, MainActivity.class);
			startActivity(i);
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


	public boolean isOnline() {
		ConnectivityManager cm =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
	
	
}
