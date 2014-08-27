package com.belikeastamp.blasuser.fragments;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import com.belikeastamp.blasuser.db.DatabaseHandler;
import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.util.ProjectController;

public class SubmitProjectsPageTwoFragment extends Fragment {

	private TextView projectName, type, status, progress;
	private EditText message;
	private Button protolink, ok_proto, no_proto, send;
	private LinearLayout valid_proto, send_msg;
	private ImageView image;
	private ProgressDialog prgDialog;
	public static final int progress_bar_type = 0;
	private String proto_path = null;
	private Request4Prototype task;
	private UpdateStatusTask updatetask;
	private int selected_status;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_submit_project_details, container, false);
		task = new Request4Prototype();
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
		message = (EditText) send_msg.findViewById(R.id.message);

		projectName.setText(project.getName());
		type.setText(project.getType());
		status.setText(statusList[project.getStatus()]);

		if(project.getStatus() == DatabaseHandler.PROJ_SUBMIT) { 
			// RECUPERATION DE LIMAGE EN BACKGROUND
			protolink.setVisibility(View.VISIBLE);

		}



		protolink.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				protolink.setEnabled(false);
				Log.d("SubmitProjectDetails", "VOIR PROTO");
				try {
					proto_path = task.execute(project).get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				valid_proto.setVisibility(View.VISIBLE);
				if(proto_path != null) {
					image.setVisibility(View.VISIBLE);
					Bitmap bm;
					BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

					bm = BitmapFactory.decodeFile(proto_path,btmapOptions);

					int width = 600;
					int height = 600;
					LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
					parms.gravity = Gravity.CENTER;
					image.setLayoutParams(parms);
					image.setImageBitmap(bm);
				}

			}		
		});

		no_proto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ok_proto.setEnabled(false);
				no_proto.setEnabled(false);
				selected_status = DatabaseHandler.PROTO_DISMISSED;
				send_msg.setVisibility(View.VISIBLE);
				updatetask.execute(project);
				
			}		
		});

		ok_proto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				no_proto.setEnabled(false);
				ok_proto.setEnabled(false);
				selected_status = DatabaseHandler.PROTO_ACCEPTED;
				updatetask.execute(project);
			}		
		});

		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("SubmitProjectDetails", "NO PROTO -  SEND MESSAGE");
			}		
		});


		progress.setCompoundDrawablesWithIntrinsicBounds(0,0,0,getStatusImg(project.getStatus()));

		progress.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StringBuffer text = new StringBuffer();
				for (int i = 0 ; i < statusList.length ; i++ ) {
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
		case DatabaseHandler.REAL_INPROGRESS:
			statut = R.drawable.step5;
			break;
		case DatabaseHandler.REAL_DONE:
			statut = R.drawable.step6;
			break;		
		default:
			break;
		}

		return statut;
	}


	private class Request4Prototype extends AsyncTask<Project, Void, String> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			prgDialog = new ProgressDialog(getActivity());
			prgDialog.setMessage("Downloading file. Please wait...");
			prgDialog.setIndeterminate(true);
			prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			prgDialog.setCancelable(false);
			prgDialog.show();


		}

		@Override
		protected String doInBackground(Project... params) {
			// TODO Auto-generated method stub
			Project p = params[0];
			final ProjectController c = new ProjectController();

			InputStream input = null;
			int count = 0;
			String filepath = null;


			try {
				input = c.downloadFile(p.getRemoteId());
				filepath = Environment.getExternalStorageDirectory().getPath()+"/"+
						p.getName()+".jpeg"; 
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
				e.printStackTrace();
			}

			return filepath;
		}

		@Override
		protected void onPostExecute(String file) {
			// Dismiss the dialog after the Music file was downloaded
			super.onPostExecute(file);
			proto_path = file;
			prgDialog.dismiss();
		}


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
}
