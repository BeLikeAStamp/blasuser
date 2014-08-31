package com.belikeastamp.blasuser.util.asynctask;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.db.model.Tutorial;
import com.belikeastamp.blasuser.util.GlobalVariable;
import com.belikeastamp.blasuser.util.ProjectController;
import com.belikeastamp.blasuser.util.TutorialController;

public class AddProjectTask extends MyAbstractAsyncTask {

	Project project;
	Activity activity;
	GlobalVariable globalVariable;
	Long pid = null;
	public AddProjectTask(Resources resources) {
		super(resources);
	}

	@Override
	protected Boolean doInBackground(Object... params) {
		// TODO Auto-generated method stub
		Project p = (Project) params[0];
		Context context = activity.getApplicationContext();
		globalVariable = (GlobalVariable) context;
		
		final ProjectController c = new ProjectController();
		try {
			c.create(p,globalVariable.getUserId());
			pid = c.getProjectRemoteId(p.getName(), globalVariable.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}	

		return (pid != null);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		globalVariable.setProjectId(pid);
	}

	public void setProject(Project p) {
		this.project = p;
	}

	public void setActivity(Activity a) {
		this.activity = a;
	}


}
