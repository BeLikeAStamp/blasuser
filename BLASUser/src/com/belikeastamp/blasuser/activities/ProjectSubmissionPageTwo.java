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
import android.widget.ImageView;
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

	public void doSubmissionClick() {
		// Do stuff here.
		Log.i("FragmentAlertDialog", "SUBMISSION click!");
	}

	public void doCancelClick() {
		// Do stuff here.
		Log.i("FragmentAlertDialog", "CANCEL click!");
	}
	
	public void doNeutralClick() {
		// Do stuff here.
		Log.i("FragmentAlertDialog", "LOCAL REGISTRATION click!");
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

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			
			LinearLayout layout = new LinearLayout(getActivity());
			layout.setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				     LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(30, 20, 30, 30);
			
			TextView projectName = new TextView(getActivity());
			TextView cardTheme = new TextView(getActivity());
			TextView cardType = new TextView(getActivity());
			TextView cardStyle = new TextView(getActivity());
			TextView nbrCards = new TextView(getActivity());
			TextView delay = new TextView(getActivity());
			TextView colors = new TextView(getActivity());
			TextView perso = new TextView(getActivity());
			TextView infos = new TextView(getActivity());
			
			LinearLayout colorlayout = new LinearLayout(getActivity());
			colorlayout.setOrientation(LinearLayout.HORIZONTAL);
			LinearLayout.LayoutParams colorLayoutParams = new LinearLayout.LayoutParams(
				     LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			colorLayoutParams.setMargins(30, 20, 30, 30);
			
			ImageView color1 = new ImageView(getActivity());
			ImageView color2 = new ImageView(getActivity());
			ImageView color3 = new ImageView(getActivity());
			
			int width = 50;
			int height = 50;
			LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
			
			
			int color = data.getColor1();
			if (color != -1) {
				color1.setBackgroundResource(color);
				color1.setLayoutParams(parms);
			}

			color = data.getColor2();
			if (color != -1) {
				color2.setBackgroundResource(color);
				color2.setLayoutParams(parms);
			}

			color = data.getColor3();
			if (color != -1) {
				color3.setBackgroundResource(color);
				color3.setLayoutParams(parms);
			}
			
			projectName.setText(getResources().getString(R.string.project_name)+" : "+data.getProjectName());
			cardTheme.setText(getResources().getString(R.string.card_theme)+" : "+data.getProjectTheme());
			cardType.setText(getResources().getString(R.string.card_type)+" : "+data.getProjectType());
			cardStyle.setText(getResources().getString(R.string.card_style)+" : "+data.getProjectStyle());
			nbrCards.setText(getResources().getString(R.string.how_many_cards)+" : "+data.getNumberOfCards());
			delay.setText(getResources().getString(R.string.for_when)+" : "+data.getOrderDate());
			colors.setText(getResources().getString(R.string.color_set)+" : ");
			perso.setText(getResources().getString(R.string.personnalisation)+" : "+data.getPerso());
			infos.setText(getResources().getString(R.string.infos)+" : "+data.getInfos());
		
			
			layout.addView(projectName, layoutParams);
			layout.addView(cardTheme, layoutParams);
			layout.addView(cardType, layoutParams);
			layout.addView(cardStyle, layoutParams);
			layout.addView(nbrCards, layoutParams);
			layout.addView(delay, layoutParams);
			layout.addView(colors, layoutParams);
			colorlayout.addView(color1);
			colorlayout.addView(color2);
			colorlayout.addView(color3);
			layout.addView(colorlayout, layoutParams);
			layout.addView(infos, layoutParams);

			builder.setView(layout);
			
			builder
			.setTitle(title)
			.setPositiveButton(R.string.alert_dialog_send,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					((ProjectSubmissionPageTwo) getActivity())
					.doSubmissionClick();
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
					.doCancelClick();
				}
			}).setNegativeButton(R.string.alert_dialog_cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					((ProjectSubmissionPageTwo) getActivity())
					.doCancelClick();
				}
			});
			
			return builder.create();
			
		}
	}
}
