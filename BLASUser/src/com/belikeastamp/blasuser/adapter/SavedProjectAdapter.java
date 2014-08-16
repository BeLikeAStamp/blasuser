package com.belikeastamp.blasuser.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.db.model.Project;

public class SavedProjectAdapter extends BaseAdapter {

	List<Project> list;
	Context context;
	
	public SavedProjectAdapter (Context context, List<Project> projectList) {
		this.context = context;
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
        ImageView track;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;
		Holder holder = new Holder();
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.listview_saved_project, null);
			TextView name = (TextView) rowView.findViewById(R.id.name);
			TextView type = (TextView) rowView.findViewById(R.id.theme);
			TextView date = (TextView) rowView.findViewById(R.id.date);
			ImageView track = (ImageView) rowView.findViewById(R.id.track);
			
			holder.name = name;
			holder.type = type;
			holder.date = date;
			holder.track = track;
			
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
		
		if (p.getPath_to_track() != null) holder.track.setImageURI(p.getPath_to_track());;
		
		return rowView;
	}

}
