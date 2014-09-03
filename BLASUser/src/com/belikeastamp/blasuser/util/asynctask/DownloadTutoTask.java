package com.belikeastamp.blasuser.util.asynctask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;

import com.belikeastamp.blasuser.db.model.Tutorial;
import com.belikeastamp.blasuser.util.TutorialController;

public class DownloadTutoTask extends MyAbstractAsyncTask {

	String filepath = null;
	Tutorial tuto;
	Activity activity;


	public DownloadTutoTask(Resources resources) {
		super(resources);
	}

	@Override
	protected Boolean doInBackground(Object... params) {
		
		Tutorial t = this.tuto;
		final TutorialController c = new TutorialController();
		InputStream input = null;
		int count = 0;


		try {
			input = c.downloadFile(t.getId());

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

		return (filepath != null);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		showTutorial(filepath);

	}

	protected void showTutorial(String filepath){

		File file = new File(filepath);
		PackageManager packageManager = activity.getPackageManager();
		Intent testIntent = new Intent(Intent.ACTION_VIEW);
		testIntent.setType("image/jpeg");
		packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "image/jpeg");
		activity.startActivity(intent);

	}


	public String getFilePath() {
		return this.filepath;
	}

	public void setTutorial(Tutorial t) {
		this.tuto = t;
	}

	public void setActivity(Activity a) {
		this.activity = a;
	}

}
