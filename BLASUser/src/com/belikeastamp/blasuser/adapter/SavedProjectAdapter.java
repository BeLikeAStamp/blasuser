package com.belikeastamp.blasuser.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.activities.SavedProjectsActivity;
import com.belikeastamp.blasuser.db.dao.Datasource;
import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.util.GlobalVariable;

public class SavedProjectAdapter extends BaseAdapter {

	List<Project> list;
	Activity activity;
	GlobalVariable data;
	Datasource datasource;
	Project projet;
	
	public SavedProjectAdapter (Activity activity, List<Project> projectList) {
		data = (GlobalVariable) activity.getApplicationContext();
		this.activity = activity;
		this.list = projectList;
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
		TextView name;
		TextView type;
		TextView date;
		ImageView resume;
		ImageView delete;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;
		Holder holder = new Holder();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.listview_saved_project, null);
			TextView name = (TextView) rowView.findViewById(R.id.name);
			TextView type = (TextView) rowView.findViewById(R.id.theme);
			TextView date = (TextView) rowView.findViewById(R.id.date);
			ImageView resume = (ImageView) rowView.findViewById(R.id.resume);
			ImageView delete = (ImageView) rowView.findViewById(R.id.delete);

			holder.name = name;
			holder.type = type;
			holder.date = date;
			holder.resume = resume;
			holder.delete = delete;

			rowView.setTag(holder);

		}
		else
			holder = (Holder) rowView.getTag();

		Log.d("BLASUSER", "Position ["+position+"]");
		projet = list.get(position);
		Log.d("BLASUSER", "Project "+projet.toString());
		holder.name.setText(projet.getName());		
		holder.type.setText(projet.getType());
		holder.date.setText(projet.getSubDate());

		holder.delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleteProject();
				Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.demand_return), Toast.LENGTH_SHORT).show();
			}
		});

		holder.resume.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(activity.getApplicationContext(), "Désolé cette fonctionnalité n'est pas encore disponible.", Toast.LENGTH_SHORT).show();;
				displayDialogWindow();
			}
		});


		return rowView;
	}

	private boolean deleteProject() {
		datasource = new Datasource(activity.getApplicationContext());
		datasource.open();
		datasource.deleteProject(projet.getName());
		datasource.close();
		return true;
	}

	public void displayDialogWindow()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

		// set title
		alertDialogBuilder.setTitle(activity.getResources().getString(R.string.modif_prj));

		// set dialog message
		alertDialogBuilder
		.setMessage(activity.getResources().getString(R.string.modif_advice))
		.setCancelable(false)
		.setPositiveButton(activity.getResources().getString(R.string.continu),new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				Intent intent = new Intent(activity,SavedProjectsActivity.class);
				intent.putExtra("project", projet);
				activity.startActivity(intent);
			}
		})
		.setNegativeButton(activity.getResources().getString(R.string.btn_cancel),new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.dismiss();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

}
