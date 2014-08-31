package com.belikeastamp.blasuser.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.db.model.Tutorial;
import com.belikeastamp.blasuser.util.asynctask.AsyncTaskManager;
import com.belikeastamp.blasuser.util.asynctask.Demand4TutoTask;
import com.belikeastamp.blasuser.util.asynctask.DownloadTutoTask;
import com.belikeastamp.blasuser.util.asynctask.MyAbstractAsyncTask;
import com.belikeastamp.blasuser.util.asynctask.OnTaskCompleteListener;

public class TutorialAdapter extends BaseAdapter implements OnTaskCompleteListener {

	private List<Tutorial> list;
	private Activity activity;
	//private ProgressDialog prgDialog;
	//public static final int progress_bar_type = 0;
	private AsyncTaskManager mAsyncTaskManager;
	
	
	public TutorialAdapter (Activity activity, List<Tutorial> tutos) {
		this.activity = activity;
		this.list = tutos;
		// Create manager and set this activity as context and listener
		mAsyncTaskManager = new AsyncTaskManager(activity, this);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public class Holder
	{
		TextView title;
		TextView dispo;
		Button action;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;
		Holder holder = new Holder();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.listview_tutorial, null);
			TextView title = (TextView) rowView.findViewById(R.id.title);
			TextView dispo = (TextView) rowView.findViewById(R.id.dispo);
			Button action = (Button) rowView.findViewById(R.id.action);

			holder.title = title;
			holder.dispo = dispo;
			holder.action = action;

			rowView.setTag(holder);

		}

		holder = (Holder) rowView.getTag();

		Tutorial t = list.get(position);
		Log.d("BLASUSER", "Tutorial "+t.toString());

		holder.title.setText(activity.getApplicationContext().getResources().getString(R.string.title)+" "+t.getTitle());
		holder.dispo.setText(activity.getApplicationContext().getResources().getString(R.string.availability)+" "
				+(t.getAvailable() ? activity.getApplicationContext().getResources().getString(R.string.available)  : activity.getApplicationContext().getResources().getString(R.string.not_available) ));
		holder.action.setText(t.getAvailable() ? activity.getApplicationContext().getResources().getString(R.string.btn_get_tuto)  : activity.getApplicationContext().getResources().getString(R.string.btn_demand));
		holder.action.setTag(t);

		holder.action.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				Button b = (Button)v ;
				Tutorial t = (Tutorial)b.getTag();

				if(b.getText().toString().equals(activity.getApplicationContext().getResources().getString(R.string.btn_get_tuto))) {
					// TELECH
					DownloadTutoTask downloadTask = new DownloadTutoTask(activity.getResources());
					downloadTask.setTutorial(t);
					downloadTask.setActivity(activity);
					mAsyncTaskManager.setupTask(downloadTask);
				}
				else
				{
					// PLUSOYER
					Demand4TutoTask deamndeTuto = new Demand4TutoTask(activity.getResources());
					deamndeTuto.setTutorial(t);
					deamndeTuto.setActivity(activity);
					mAsyncTaskManager.setupTask(deamndeTuto);
				}

				b.setEnabled(false);
			}
		});

		return rowView;
	}

	@Override
	public void onTaskComplete(MyAbstractAsyncTask task) {
		// TODO Auto-generated method stub
		if (task.isCancelled()) {
		    // Report about cancel
		    Toast.makeText(activity.getApplicationContext(),  activity.getResources().getString(R.string.task_cancelled), Toast.LENGTH_LONG)
			    .show();
		} else {
		    // Get result
		    Boolean result = null;
		    try {
			result = task.get();
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		    // Report about result
		    Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.task_completed, (result != null) ? result.toString() : "null"),
			    Toast.LENGTH_LONG).show();
		}
	}

}
