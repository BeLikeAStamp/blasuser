package com.belikeastamp.blasuser.util.asynctask;

import android.app.Activity;
import android.content.res.Resources;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.db.model.Tutorial;
import com.belikeastamp.blasuser.util.TutorialController;

public class Demand4TutoTask extends MyAbstractAsyncTask {

	Tutorial tuto;
	Activity activity;


	public Demand4TutoTask(Resources resources) {
		super(resources);
	}

	@Override
	protected Boolean doInBackground(Object... params) {
		
		Tutorial t = this.tuto;
		final TutorialController c = new TutorialController();

		Integer onDemand = t.getOnDemand()+1;
		t.setOnDemand(onDemand);
		try {
			c.update(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		showToast();

	}

	protected void showToast(){
		Toast.makeText(activity.getApplicationContext(),  activity.getApplicationContext().getResources().getString(R.string.demand_return), Toast.LENGTH_SHORT).show();
	}

	public void setTutorial(Tutorial t) {
		this.tuto = t;
	}

	public void setActivity(Activity a) {
		this.activity = a;
	}


}
