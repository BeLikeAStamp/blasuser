package com.belikeastamp.blasuser.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.fragments.ProjectSubmissionPageOneFragment;
import com.belikeastamp.blasuser.fragments.ProjectSubmissionPageTwoFragment;
import com.belikeastamp.blasuser.util.ProjectData;

public class ProjectSubmissionPageTwo extends Activity {
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	ProjectData globalVariable;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submission_page2);
		globalVariable = (ProjectData) getApplicationContext();

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_navigation_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
				) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}


			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};

		mDrawerToggle.setDrawerIndicatorEnabled(false);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			Fragment fragment = new ProjectSubmissionPageTwoFragment();

			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.frame_container, fragment).commit();

		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {   
		// Get item selected and deal with it
		switch (item.getItemId()) {
		case android.R.id.home:
			//called when the up affordance/carat in actionbar is pressed
			this.onBackPressed();
			return true;
		}
		return false;
	}

	public void doPositiveClick() {
		// Do stuff here.
		Log.i("FragmentAlertDialog", "Positive click!");
	}

	public void doNegativeClick() {
		// Do stuff here.
		Log.i("FragmentAlertDialog", "Negative click!");
	}
	
	public void doNeutralClick() {
		// Do stuff here.
		Log.i("FragmentAlertDialog", "Positive click!");
	}


	public static class SubmissionDialogFragment extends DialogFragment {
		
		public static ProjectData data;
		
		public static SubmissionDialogFragment newInstance(int title, ProjectData globalVariable) {
			SubmissionDialogFragment frag = new SubmissionDialogFragment();
			Bundle args = new Bundle();
			SubmissionDialogFragment.data = globalVariable;
			args.putInt("title", title);
			frag.setArguments(args);
			return frag;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			int title = getArguments().getInt("title");

			/*final Dialog dialog = new Dialog(getActivity());  
			dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);  
			dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
					WindowManager.LayoutParams.FLAG_FULLSCREEN);  
			dialog.setContentView(R.layout.custom_dialogfragment);
			
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.show();
			
			return dialog;*/
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			
			LinearLayout layout = new LinearLayout(getActivity());
			layout.setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				     LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(30, 20, 30, 0);
			
			TextView projectName = new TextView(getActivity());
			projectName.setText(getResources().getString(R.string.project_name)+" : "+data.getProjectName());
			layout.addView(projectName, layoutParams);
			builder.setView(layout);
			
			builder
			.setTitle(title)
			.setPositiveButton(R.string.alert_dialog_send,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					((ProjectSubmissionPageTwo) getActivity())
					.doPositiveClick();
				}
			})
			.setNeutralButton(R.string.alert_dialog_rec,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					((ProjectSubmissionPageTwo) getActivity())
					.doNeutralClick();
				}
			})
			.setNegativeButton(R.string.alert_dialog_cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					((ProjectSubmissionPageTwo) getActivity())
					.doNegativeClick();
				}
			}).setNegativeButton(R.string.alert_dialog_cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					((ProjectSubmissionPageTwo) getActivity())
					.doNegativeClick();
				}
			});
			
			return builder.create();
			
		}
	}
}
