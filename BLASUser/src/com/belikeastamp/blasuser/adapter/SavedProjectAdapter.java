package com.belikeastamp.blasuser.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.activities.MainActivity;
import com.belikeastamp.blasuser.activities.ProjectSubmissionPageTwo;
import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.util.GlobalVariable;

public class SavedProjectAdapter extends BaseAdapter {

	List<Project> list;
	Activity activity;
	GlobalVariable data;
	
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
        Button resume;
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
			Button resume = (Button) rowView.findViewById(R.id.resume);
			
			holder.name = name;
			holder.type = type;
			holder.date = date;
			holder.resume = resume;
			
			rowView.setTag(holder);

		}
		else
			holder = (Holder) rowView.getTag();
		
		Log.d("BLASUSER", "Position ["+position+"]");
		Project p = list.get(position);
		Log.d("BLASUSER", "Project "+p.toString());
		holder.name.setText(p.getName());		
		holder.type.setText(p.getType());
		holder.date.setText(p.getSubDate());
		
		holder.resume.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(activity.getApplicationContext(), "Désolé cette fonctionnalité n'est pas encore disponible.", Toast.LENGTH_SHORT).show();;
			}
		});
			
		
		return rowView;
	}

}
