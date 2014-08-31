package com.belikeastamp.blasuser.fragments;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.activities.MainActivity;
import com.belikeastamp.blasuser.activities.SubmitProjectsActivity;
import com.belikeastamp.blasuser.db.DatabaseHandler;
import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.util.ProjectController;
import com.belikeastamp.blasuser.util.asynctask.AsyncTaskManager;
import com.belikeastamp.blasuser.util.asynctask.MyAbstractAsyncTask;
import com.belikeastamp.blasuser.util.asynctask.OnTaskCompleteListener;
import com.belikeastamp.blasuser.util.asynctask.Request4PrototypeTask;

public class SubmitProjectsPageTwoFragment extends Fragment implements OnTaskCompleteListener {

	private static final int PROTO = 0;
	private TextView projectName, type, status, progress;
	private EditText message;
	private Button protolink, ok_proto, no_proto, send;
	private LinearLayout valid_proto, send_msg;
	private ImageView image;
	private String proto_path = null;
	private AsyncTaskManager mAsyncTaskManager;
	private UpdateStatusTask updatetask;
	private int selected_status;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_submit_project_details, container, false);
		mAsyncTaskManager = new AsyncTaskManager(getActivity(), this);
		updatetask = new UpdateStatusTask();
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		final Project project = (Project) getActivity().getIntent().getSerializableExtra("project");
		final String[] statusList = getResources().getStringArray(R.array.status_arrays);
		projectName = (TextView) getActivity().findViewById(R.id.name);
		type = (TextView) getActivity().findViewById(R.id.type);
		status = (TextView) getActivity().findViewById(R.id.status);
		progress = (TextView) getActivity().findViewById(R.id.progress);
		protolink = (Button) getActivity().findViewById(R.id.proto_link);
		image = (ImageView) getActivity().findViewById(R.id.image);

		valid_proto = (LinearLayout) getActivity().findViewById(R.id.valid_proto);
		ok_proto = (Button) valid_proto.findViewById(R.id.ok_proto);
		no_proto = (Button) valid_proto.findViewById(R.id.no_proto);

		send_msg = (LinearLayout) getActivity().findViewById(R.id.send_msg);
		send =  (Button) send_msg.findViewById(R.id.send);
		message = (EditText) send_msg.findViewById(R.id.msg);

		projectName.setText(project.getName());
		type.setText(project.getType());
		status.setText(statusList[project.getStatus()]);

		if(project.getStatus() == DatabaseHandler.PROTO_PENDING) { 
			// RECUPERATION DE LIMAGE EN BACKGROUND
			protolink.setVisibility(View.VISIBLE);

		}

		protolink.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				protolink.setVisibility(View.INVISIBLE);
				Log.d("SubmitProjectDetails", "VOIR PROTO");
				//proto_path = task.execute(project).get();
				Request4PrototypeTask request = new Request4PrototypeTask(getResources());
				request.setProject(project);
				request.setActivity(getActivity());
				mAsyncTaskManager.setupTask(request);
				valid_proto.setVisibility(View.VISIBLE);

			}		
		});

		no_proto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ok_proto.setVisibility(View.INVISIBLE);
				no_proto.setVisibility(View.INVISIBLE);
				selected_status = DatabaseHandler.PROTO_DISMISSED;
				send_msg.setVisibility(View.VISIBLE);
				updatetask.execute(project);

			}		
		});

		ok_proto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				no_proto.setVisibility(View.INVISIBLE);
				ok_proto.setVisibility(View.INVISIBLE);
				selected_status = DatabaseHandler.PROTO_ACCEPTED;
				updatetask.execute(project);
				
				Intent i = new Intent(getActivity(), MainActivity.class);
				getActivity().startActivity(i);
			}		
		});

		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendEmail(project);
				Log.d("SubmitProjectDetails", "NO PROTO -  SEND MESSAGE");
				Intent i = new Intent(getActivity(), MainActivity.class);
				getActivity().startActivity(i);
			}		
		});


		progress.setCompoundDrawablesWithIntrinsicBounds(0,0,0,getStatusImg(project.getStatus()));

		progress.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StringBuffer text = new StringBuffer();
				for (int i = 0 ; i < getResources().getStringArray(R.array.status_print_arrays).length ; i++ ) {
					text.append(i+1+":"+statusList[i]+"\n");
				}
				Toast.makeText(getActivity().getApplicationContext(), text.toString(), Toast.LENGTH_LONG).show();
			}
		});

	}

	private int getStatusImg(int statusId) {
		int statut = 0;
		switch (statusId) {
		case DatabaseHandler.PROJ_SUBMIT:
			statut = R.drawable.step1;
			break;
		case DatabaseHandler.PROJ_ACCEPTED:
			statut = R.drawable.step2;
			break;
		case DatabaseHandler.PROTO_INPROGRESS:
			statut = R.drawable.step3;
			break;
		case DatabaseHandler.PROTO_PENDING:
			statut = R.drawable.step4;
			break;
		case DatabaseHandler.PROTO_ACCEPTED:
			statut = R.drawable.step5;
			break;	
		case DatabaseHandler.REAL_INPROGRESS:
			statut = R.drawable.step6;
			break;
		case DatabaseHandler.REAL_DONE:
			statut = R.drawable.step7;
			break;		
		default:
			statut = R.drawable.step0;
			break;
		}

		return statut;
	}

	protected class UpdateStatusTask extends AsyncTask<Project, Void, Void> {

		@Override
		protected Void doInBackground(Project... params) {
			final ProjectController c = new ProjectController();
			Project proj = params[0];
			Log.d("UpdateStatusTask", "Project = "+proj);
			proj.setStatus(selected_status);
			try {
				c.update(proj, proj.getRemoteId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			

			return null;
		}

	}

	private void sendEmail(Project p) {
		// TODO Auto-generated method stub

		if(message.getText().toString().length() == 0) {
			message.setError(getActivity().getResources().getString(R.string.err_no_proto));
		} else {

			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,  new String[]{getResources().getString(R.string.contact_email)});

			String body = getActivity().getResources().getString(R.string.email_body_no_proto, message.getText().toString());
			String msg = getActivity().getResources().getString(R.string.email_subject_no_proto );
			String pj = createTempProjectFile(p);
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, msg);
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
			Log.d("FILE PATH", "=>"+pj);
			emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.parse("file://"+pj));
			startActivity(emailIntent);

		}
	}

	private String createTempProjectFile(Project p) {
		String projectFile = Environment.getExternalStorageDirectory().getPath()+"/project.txt"; 

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

	@Override
	public void onTaskComplete(MyAbstractAsyncTask task) {
		// TODO Auto-generated method stub
		if (task.isCancelled()) {
			// Report about cancel
			Toast.makeText(getActivity().getApplicationContext(),  getActivity().getResources().getString(R.string.task_cancelled), Toast.LENGTH_LONG)
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
			Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.task_completed, (result != null) ? result.toString() : "null"),
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d("RESULT CODE", ""+resultCode);

		if (resultCode == Activity.RESULT_OK) {

			if (requestCode == PROTO) {
				Uri selectedImageUri = data.getData();
				String tempPath = getPath(selectedImageUri, getActivity());
				Log.d("PROTO_PATH", "=> "+proto_path);
				image.setVisibility(View.VISIBLE);
				Bitmap bm;
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
				int width = 600;
				int height = 600;
				LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
				parms.gravity = Gravity.CENTER;
				image.setLayoutParams(parms);
				image.setImageBitmap(bm);

			}
		}
	}

	public String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor =  activity.getContentResolver().query(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}
