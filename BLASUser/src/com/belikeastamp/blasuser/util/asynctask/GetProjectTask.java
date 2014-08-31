package com.belikeastamp.blasuser.util.asynctask;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.db.model.Tutorial;
import com.belikeastamp.blasuser.util.GlobalVariable;
import com.belikeastamp.blasuser.util.ProjectController;
import com.belikeastamp.blasuser.util.TutorialController;

public class GetProjectTask extends MyAbstractAsyncTask {
	private static final int SEND_EMAIL = 0;
	Project project;
	Activity activity;
	GlobalVariable globalVariable;
	Long pid = null;
	
	public GetProjectTask(Resources resources) {
		super(resources);
	}

	@Override
	protected Boolean doInBackground(Object... params) {
		Context context = activity.getApplicationContext();
		globalVariable = (GlobalVariable) context;
		
		final ProjectController c = new ProjectController();
		try {
			pid = c.getProjectRemoteId(project.getName(), globalVariable.getUserId());	
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		Log.i("doInBackground GetProjectTask","->"+(pid));
		return (pid != null);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		project.setRemoteId(pid);
		sendEmail(project);
	}

	public void setProject(Project p) {
		this.project = p;
	}

	public void setActivity(Activity a) {
		this.activity = a;
	}

	private void sendEmail(Project p) {
		// TODO Auto-generated method stub

		String pj = createTempProjectFile(p);
		String body = activity.getResources().getString(R.string.email_body_submission, globalVariable.getInfos());

		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,  new String[]{activity.getResources().getString(R.string.contact_email)});
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, activity.getResources().getString(R.string.email_subject_submission));
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);

		ArrayList<Uri> uris = new ArrayList<Uri>();
		uris.add(Uri.parse("file://"+pj));
		if (p.getTrackFile() != null) {
			uris.add(p.getTrackFile());
		}

		Log.d("URIS", uris.toString());
		emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
		activity.startActivityForResult(emailIntent, SEND_EMAIL);
	}

	
	private String createTempProjectFile(Project p) {
		String projectFile = Environment.getExternalStorageDirectory().getPath()+"/"+p.getName()+"_project.txt"; 

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(projectFile));
			bw.append(p.toString());
			bw.flush();bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return projectFile;
	}

}
