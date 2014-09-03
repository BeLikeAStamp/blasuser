package com.belikeastamp.blasuser.util.asynctask;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.db.model.Tutorial;
import com.belikeastamp.blasuser.db.model.Workshop;
import com.belikeastamp.blasuser.util.GlobalVariable;
import com.belikeastamp.blasuser.util.ProjectController;
import com.belikeastamp.blasuser.util.TutorialController;
import com.belikeastamp.blasuser.util.WorkshopController;

public class Request4RemoteDataTask extends MyAbstractAsyncTask {

	Activity activity;

	public Request4RemoteDataTask(Resources resources) {
		super(resources);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Boolean doInBackground(Object... params) {
		
		ProjectController pc = new ProjectController();
		WorkshopController wc = new WorkshopController();
		TutorialController tc = new TutorialController();
		Context context = activity.getApplicationContext();
		GlobalVariable globalVariable = (GlobalVariable) context;
		
		Long userId = context.getSharedPreferences("BLAS", Context.MODE_PRIVATE).getLong("user_id", Long.valueOf(-1));
		try {
			if(!userId.equals(Long.valueOf(-1))) 
				globalVariable.getData().setSubmitProjects((List<Project>)pc.getAllProjects(userId));
			globalVariable.getData().setWorkshops((List<Workshop>) wc.getAllWorkshops());
			globalVariable.getData().setTutorials((List<Tutorial>) tc.getAllTutorials());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Integer sum = Integer.valueOf(globalVariable.getData().getSubmitProjects().size()
				+globalVariable.getData().getTutorials().size()
				+globalVariable.getData().getWorkshops().size());

		return ( sum > 0 );
	}

	public void setActivity(Activity a) {
		this.activity = a;
	}
}
