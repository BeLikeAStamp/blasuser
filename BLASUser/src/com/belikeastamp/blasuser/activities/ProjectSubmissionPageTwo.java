package com.belikeastamp.blasuser.activities;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.belikeastamp.blasuser.db.dao.Datasource;
import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.fragments.ProjectSubmissionPageTwoFragment;
import com.belikeastamp.blasuser.util.CustomMultiPartEntity;
import com.belikeastamp.blasuser.util.EngineConfiguration;
import com.belikeastamp.blasuser.util.GlobalVariable;
import com.belikeastamp.blasuser.util.ProjectController;

public class ProjectSubmissionPageTwo extends Activity {
	private static final int SEND_EMAIL = 0;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private Long projectId;
	private GlobalVariable globalVariable;
	boolean everything_ok = true;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submission_page2);
		globalVariable = (GlobalVariable) getApplicationContext();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.banniere));

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

	public void doSubmissionClick(Project p) {
		// Do stuff here.
		Log.i("FragmentAlertDialog", "SUBMISSION click!");
		remoteSubmission(p);
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

		public static GlobalVariable data;

		public static SubmissionDialogFragment newInstance(int title, GlobalVariable globalVariable) {
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
				colorsBuffer.append(GlobalVariable.colorName.get(color));
			}

			color = data.getColor2();
			if (color != -1) {
				color2.setBackgroundResource(color);
				color2.setLayoutParams(parms);
				colorsBuffer.append(","+GlobalVariable.colorName.get(color));
			}

			color = data.getColor3();
			if (color != -1) {
				color3.setBackgroundResource(color);
				color3.setLayoutParams(parms);
				colorsBuffer.append(","+GlobalVariable.colorName.get(color));
			}

			final Project p = new Project(data.getProjectName(), 
					data.getSubmitDate(), -1, data.getDetails().toString(),
					data.getProjectType(), data.getOrderDate(),
					Integer.valueOf(data.getNumberOfCards()),
					(data.getPerso() == null ? "anonymous" : data.getPerso().toString()));

			p.setColors(colorsBuffer.toString());
			if(data.getTrackFile() != null) p.setTrackFile(data.getTrackFile());

			Log.d("PROJECT BEFORE SAVING ",p.toString());

			projectName.setText(getResources().getString(R.string.project_name)+" : "+data.getProjectName());
			cardType.setText(getResources().getString(R.string.project_type)+" : "+data.getProjectType());
			cardDetail.setText(getResources().getString(R.string.personnalisation)+" : "+data.getPrintableDetails());
			nbrCards.setText(getResources().getString(R.string.how_many_cards)+" : "+data.getNumberOfCards());
			delay.setText(getResources().getString(R.string.for_when)+" : "+data.getOrderDate());
			colors.setText(getResources().getString(R.string.color_set)+" : ");



			layout.addView(projectName, layoutParams);
			layout.addView(cardType, layoutParams);
			layout.addView(cardDetail, layoutParams);

			layout.addView(colors, layoutParams);
			colorlayout.addView(color1);
			colorlayout.addView(color2);
			colorlayout.addView(color3);
			layout.addView(colorlayout, layoutParams);

			layout.addView(nbrCards, layoutParams);
			layout.addView(delay, layoutParams);

			builder.setView(layout);

			builder
			.setTitle(title)
			.setPositiveButton(R.string.alert_dialog_send,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					((ProjectSubmissionPageTwo) getActivity())
					.doSubmissionClick(p);
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
			});

			return builder.create();

		}
	}


	private void localRegistration(Project p) {
		// 0. Check en interne si un projet du meme non n'existe pas
		Datasource datasource = new Datasource(getApplicationContext());
		datasource.open();
		Log.i("PROJECT TO SAVE",p.toString());
		datasource.addProjects(p);
		datasource.close();

		//Intent i = new Intent(ProjectSubmissionPageTwo.this, SavedProjectsActivity.class);
		//startActivity(i);
	}

	private void remoteSubmission(Project p) {

		p.setStatus(0);

		// remote
		AddProjectTask task = new AddProjectTask();
		try {
			projectId = task.execute(p).get();
			p.setRemoteId(projectId);

			if (!projectId.equals(Long.valueOf(-1)))
			{
				sendEmail(p);
			}
			else everything_ok = false;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// local 
		if (everything_ok) {
			Datasource datasource = new Datasource(getApplicationContext());
			datasource.open();
			Log.i("PROJECT TO SUBMIT",p.toString());
			datasource.addProjects(p);
			datasource.close();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Ca a merdé !!!", Toast.LENGTH_SHORT).show();
		}


	}

	private void sendEmail(Project p) {
		// TODO Auto-generated method stub
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,  new String[]{getResources().getString(R.string.contact_email)});

		String body = getResources().getString(R.string.email_body);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject));
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);

		if (p.getTrackFile() != null) {
			emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(p.getTrackFile().toString()));
		}

		startActivityForResult(emailIntent, SEND_EMAIL);
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
				c.create(p,globalVariable.getUserId());
				pid = c.getProjectRemoteId(p.getName(), globalVariable.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}

			return pid;
		}
	}

	public class SendHttpRequestTask extends AsyncTask<File, Integer, String> {

		int serverResponseCode=0;
		//for uploading..// 
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";
		ProgressDialog pd;
		long totalSize;

		StringBuffer buffer=new StringBuffer();


		@Override
		protected void onPreExecute()
		{
			pd = new ProgressDialog(ProjectSubmissionPageTwo.this);
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setMessage("Uploading Picture...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}

		protected String doInBackground(File... params) {

			File file=params[0];

			try { 
				String url = EngineConfiguration.path + "upload?type=project&correspondance="+projectId;
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(url);

				MultipartEntityBuilder builder =MultipartEntityBuilder.create();        
				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				ContentBody cbFile = new FileBody(file);
				builder.addPart("file", cbFile);
				builder.addTextBody("name", "uploadedFile");
				totalSize = builder.build().getContentLength();
				Log.i("totalSize", "totalSize : "+totalSize);

				CustomMultiPartEntity multipartContent = new CustomMultiPartEntity(builder, new CustomMultiPartEntity.ProgressListener() {

					@Override
					public void transferred(long num) {
						// TODO Auto-generated method stub
						publishProgress((int) ((num / (float) totalSize) * 100));
					}
				});

				HttpEntity mpEntity = multipartContent.getEntity();

				post.setEntity(mpEntity);

				HttpResponse response = client.execute(post);

				HttpEntity resEntity = response.getEntity();
				String Response=EntityUtils.toString(resEntity);


				Log.i("uploadFile", "HTTP Response is : "
						+ Response);


			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			} 
			catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 

			return null; 
		} 

		@Override
		protected void onProgressUpdate(Integer... progress)
		{
			pd.setProgress((int) (progress[0]));
		}

		@Override
		protected void onPostExecute(String s)
		{
			pd.dismiss();
		}
	}

	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub 
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {

		case SEND_EMAIL: 
			if(resultCode==Activity.RESULT_CANCELED){
				everything_ok = false;
			} 
			break; 

		default: 
			break; 
		} 
	}

}

