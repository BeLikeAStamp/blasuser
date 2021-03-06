package com.belikeastamp.blasuser.adapter;

import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.db.model.Project;

public class SubmitProjectAdapter extends BaseAdapter {

	List<Project> list;
	Context context;
	String[] status;
	
	public SubmitProjectAdapter (Context context, List<Project> projectList, String[] status) {
		this.context = context;
		this.list = projectList;
		this.status = status;
	}
	
	
	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}
	
	public class Holder
    {
        TextView name;
        TextView type;
        TextView status;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View rowView = convertView;
		Holder holder = new Holder();
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.listview_submit_project, null);
			TextView name = (TextView) rowView.findViewById(R.id.name);
			TextView type = (TextView) rowView.findViewById(R.id.theme);
			TextView status = (TextView) rowView.findViewById(R.id.status);
			
			holder.name = name;
			holder.type = type;
			holder.status = status;
			
			rowView.setTag(holder);

		}
		else
			holder = (Holder) rowView.getTag();
		
		Log.d("BLASUSER", "Position ["+position+"]");
		Project p = list.get(position);
		Log.d("BLASUSER", "Project "+p.toString());
		holder.name.setText(p.getName());		
		holder.type.setText(p.getType());
		holder.status.setText(status[p.getStatus()]);
		
		return rowView;
	}

}
