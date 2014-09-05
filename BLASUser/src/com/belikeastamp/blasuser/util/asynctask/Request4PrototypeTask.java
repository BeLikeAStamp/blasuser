package com.belikeastamp.blasuser.util.asynctask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.util.ProjectController;

public class Request4PrototypeTask extends MyAbstractAsyncTask {

	private static final int PROTO = 0;
	private Project project = null;
	private String filepath = null;
	private ImageView image = null;
	
	Activity activity;
	
	public Request4PrototypeTask(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Boolean doInBackground(Object... params) {
		
		Project p = this.project;
		final ProjectController c = new ProjectController();
		
		InputStream input = null;
		int count = 0;

		try {
			input = c.downloadFile(p.getRemoteId());
			filepath = Environment.getExternalStorageDirectory().getPath()+"/"+
					p.getName()+".jpeg"; 
			Log.d("Request4PrototypeTask", "->"+filepath);
			OutputStream output = new FileOutputStream(filepath);
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
			e.printStackTrace();
		}

		return (filepath != null);
	}

	public void setProject(Project proj) {
		this.project = proj;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		//showPrototype(filepath);
		updateImage(filepath);
	}
	
	protected void showPrototype(String filepath){

		File file = new File(filepath);
		PackageManager packageManager = activity.getPackageManager();
		Intent testIntent = new Intent(Intent.ACTION_VIEW);
		testIntent.setType("image/jpeg");
		packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "image/jpeg");
		activity.startActivityForResult(intent, PROTO);

	}
	
	protected void updateImage(String path) {
	
		image.setVisibility(View.VISIBLE);
		Bitmap bm;
		BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
		bm = BitmapFactory.decodeFile(path, btmapOptions);
		int width = 600;
		int height = 600;
		LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
		parms.gravity = Gravity.CENTER;
		image.setLayoutParams(parms);
		image.setImageBitmap(bm);

	}

	public void setActivity(Activity a) {
		this.activity = a;
	}

	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}
}
