package com.belikeastamp.blasuser.adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
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
import com.belikeastamp.blasuser.util.TutorialController;

public class TutorialAdapter extends BaseAdapter {

	private List<Tutorial> list;
	private Activity activity;
	private ProgressDialog prgDialog;
	public static final int progress_bar_type = 0;

	public TutorialAdapter (Activity activity, List<Tutorial> tutos) {
		this.activity = activity;
		this.list = tutos;
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
				Long tutorialId = t.getId();

				if(b.getText().toString().equals(activity.getApplicationContext().getResources().getString(R.string.btn_get_tuto))) {
					// TELECH
					new DownloadTutoTask().execute(t);
				}
				else
				{
					// PLUSOYER
					new Demand4TutoTask().execute(t);
					Toast.makeText(activity.getApplicationContext(),  activity.getApplicationContext().getResources().getString(R.string.demand_return), Toast.LENGTH_SHORT).show();
				}

				b.setEnabled(false);
			}
		});

		return rowView;
	}


	protected class Demand4TutoTask extends AsyncTask<Tutorial, Void, Void> {

		@Override
		protected Void doInBackground(Tutorial... params) {
			final TutorialController c = new TutorialController();

			Tutorial t = params[0];
			Integer onDemand = t.getOnDemand()+1;
			t.setOnDemand(onDemand);
			try {
				c.update(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			

			return null;
		}

	}

	protected class DownloadTutoTask extends AsyncTask<Tutorial, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			prgDialog = new ProgressDialog(activity);
			prgDialog.setMessage("Downloading file. Please wait...");
			prgDialog.setIndeterminate(true);
			prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			prgDialog.setCancelable(false);
			prgDialog.show();


		}


		@Override
		protected String doInBackground(Tutorial... params) {
			// TODO Auto-generated method stub
			Tutorial t = params[0];
			final TutorialController c = new TutorialController();
			InputStream input = null;
			int count = 0;
			String filepath = null;

			try {
				input = c.downloadFile(t.getId());
				/*PackageManager m = activity.getPackageManager();
				String s = activity.getPackageName();
				PackageInfo p = m.getPackageInfo(s, 0);
				s = p.applicationInfo.dataDir;
				String filepath = s+"/"+t.getFile()+".png" */

				filepath = Environment.getExternalStorageDirectory().getPath()+"/"+t.getFile()+".jpeg"; 
				OutputStream output = new FileOutputStream(filepath);
				//OutputStream output = new FileOutputStream();
				byte data[] = new byte[1024];

				while ((count = input.read(data)) != -1) {
					// Write data to file
					output.write(data, 0, count);
				}
				// Flush output
				output.flush();
				// Close streams
				output.close();
				input.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return filepath;
		}

		@Override
		protected void onPostExecute(String file) {
			// Dismiss the dialog after the Music file was downloaded
			super.onPostExecute(file);
			prgDialog.dismiss();
			showTutorial(file);
		}
	}

	protected void showTutorial(String filepath){

		File file = new File(filepath);
		PackageManager packageManager = activity.getPackageManager();
		Intent testIntent = new Intent(Intent.ACTION_VIEW);
		testIntent.setType("application/pdf");
		packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "image/jpeg");
		activity.startActivity(intent);


	}

}
