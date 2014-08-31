package com.belikeastamp.blasuser.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.belikeastamp.blasuser.R;


public class NotificationActivity extends Activity {
	
	private String notification;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		notification =  getSharedPreferences("BLAS", Activity.MODE_PRIVATE).getString("notif_content", "Ouuups !");
		displayDialogWindow();
	}
	
	public void displayDialogWindow()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NotificationActivity.this);

		// set title
		alertDialogBuilder.setTitle("Don't Worry, Be Crafty !");

		// set dialog message
		alertDialogBuilder
		.setMessage(notification)
		.setCancelable(false)
		.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				finish();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

}
