package com.belikeastamp.blasuser.activities;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.db.dao.ProjectsData;
import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.db.model.User;
import com.belikeastamp.blasuser.fragments.ProjectSubmissionPageTwoFragment;
import com.belikeastamp.blasuser.util.ProjectController;
import com.belikeastamp.blasuser.util.ProjectData;
import com.belikeastamp.blasuser.util.UserController;

public class ProjectSubmissionPageTwo extends Activity {
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private ProjectData globalVariable;
	private Long id;
	private String userEmail;

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

	public void doSaveClick(Project p) {
		// Do stuff here.
		Log.i("FragmentAlertDialog", "LOCAL REGISTRATION click!");
		localRegistration(p);
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
			final StringBuffer colorsBuffer = new StringBuffer();

			LinearLayout layout = new LinearLayout(getActivity());
			layout.setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(30, 20, 30, 30);

			TextView projectName = new TextView(getActivity());
			TextView cardType = new TextView(getActivity());
			TextView cardDetail = new TextView(getActivity());
			TextView nbrCards = new TextView(getActivity());
			TextView delay = new TextView(getActivity());
			TextView colors = new TextView(getActivity());
			TextView perso = new TextView(getActivity());

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
				colorsBuffer.append(ProjectData.colorName.get(color));
			}

			color = data.getColor2();
			if (color != -1) {
				color2.setBackgroundResource(color);
				color2.setLayoutParams(parms);
				colorsBuffer.append(","+ProjectData.colorName.get(color));
			}

			color = data.getColor3();
			if (color != -1) {
				color3.setBackgroundResource(color);
				color3.setLayoutParams(parms);
				colorsBuffer.append(","+ProjectData.colorName.get(color));
			}

			final Project p = new Project(data.getProjectName(), 
					data.getSubmitDate(), -1, data.getProjectDetail(),
					data.getProjectType(), data.getOrderDate(),
					Integer.valueOf(data.getNumberOfCards()),
					(data.getPerso() == null ? "anonymous" : data.getPerso().toString()));

			p.setColors(colorsBuffer.toString());
			p.setPath_to_track(data.getTrackFile());
			
			Log.d("PROJECT BEFORE SAVING ",p.toString());
			
			projectName.setText(getResources().getString(R.string.project_name)+" : "+data.getProjectName());
			cardType.setText(getResources().getString(R.string.card_type)+" : "+data.getProjectType());
			cardDetail.setText(getResources().getString(R.string.card_style)+" : "+data.getProjectDetail());
			nbrCards.setText(getResources().getString(R.string.how_many_cards)+" : "+data.getNumberOfCards());
			delay.setText(getResources().getString(R.string.for_when)+" : "+data.getOrderDate());
			colors.setText(getResources().getString(R.string.color_set)+" : ");
			perso.setText(getResources().getString(R.string.personnalisation)+" : "+(data.getPerso() == null ? "anonyme" : data.getPerso()));


			layout.addView(projectName, layoutParams);
			layout.addView(cardType, layoutParams);
			layout.addView(cardDetail, layoutParams);
			layout.addView(nbrCards, layoutParams);
			layout.addView(delay, layoutParams);
			layout.addView(colors, layoutParams);
			colorlayout.addView(color1);
			colorlayout.addView(color2);
			colorlayout.addView(color3);
			layout.addView(colorlayout, layoutParams);
			layout.addView(perso, layoutParams);
			
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
					.doSaveClick(p);
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


	private void localRegistration(Project p) {
		// 0. Check en interne si un projet du meme non n'existe pas
		ProjectsData datasource = new ProjectsData(getApplicationContext());
		datasource.open();
		datasource.addProjects(p);
		datasource.close();
	}

	private void remoteSubmission(Project p) {
		p.setStatus(1);
		
		if(isRegistred()) {
			AddProjectTask task = new AddProjectTask();
			
			
			
			
			
			
		}
		
	}
	
	
	private Boolean isRegistred() {
		// TODO Auto-generated method stub
		id = getSharedPreferences("BLAS", MODE_PRIVATE).getLong("user_id", Long.valueOf(-1));
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
				Toast.makeText(getApplicationContext(), R.string.reg_succed, Toast.LENGTH_SHORT).show();
				getSharedPreferences("BLAS", MODE_PRIVATE).edit().putLong("user_id", id).commit();
			}
			else
			{
				Toast.makeText(getApplicationContext(), R.string.reg_failed, Toast.LENGTH_SHORT).show();
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


	/*private class WaitTask extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			Log.i("Submission", "WaitTask onPreExecute");
			pd = new ProgressDialog(SubmissionActivity.this);
			pd.setTitle("Processing...");
			pd.setMessage("Please wait.");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		} 

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void unused) {
			super.onPostExecute(unused);
			Log.i("Submission", "WaitTask onPostExecute");
			if (pd!=null) {
				pd.dismiss();
				envoyer.setEnabled(true);
			}
		} 

	}*/

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

	private class AddProjectTask extends AsyncTask<Project, Void, Long> {


		@Override
		protected Long doInBackground(Project... params) {
			// TODO Auto-generated method stub
			Log.d("Submission","AddProjectTask doInBackground");
			Project p = params[0];
			Long pid = null;
			final ProjectController c = new ProjectController();
			try {
				c.create(p,id);
				pid = c.getProjectRemoteId(p.getName(), id);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return pid;
		}
	}
}

