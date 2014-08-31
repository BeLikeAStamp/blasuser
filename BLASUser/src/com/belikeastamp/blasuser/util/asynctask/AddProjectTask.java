package com.belikeastamp.blasuser.util.asynctask;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.util.GlobalVariable;
import com.belikeastamp.blasuser.util.ProjectController;

public class AddProjectTask extends MyAbstractAsyncTask {

	Project project;
	Activity activity;
	GlobalVariable globalVariable;
	
	public AddProjectTask(Resources resources) {
		super(resources);
	}

	@Override
	protected Boolean doInBackground(Object... params) {
		Context context = activity.getApplicationContext();
		globalVariable = (GlobalVariable) context;
		
		final ProjectController c = new ProjectController();
		try {
			c.create(project,globalVariable.getUserId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}	

		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		
	}

	public void setProject(Project p) {
		this.project = p;
	}

	public void setActivity(Activity a) {
		this.activity = a;
	}


}
