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
import com.belikeastamp.blasuser.model.Project;

public class ProjectAdapter extends BaseAdapter {

	List<Project> list;
	Context context;
	LongSparseArray<Uri> prototypes;
	String[] status;
	
	public ProjectAdapter (Context context, List<Project> projectList, LongSparseArray<Uri> map, String[] status) {
		this.context = context;
		this.prototypes = map;
		this.list = projectList;
		this.status = status;
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
        TextView theme;
        TextView status;
        ImageView img;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;
		Holder holder = new Holder();
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.listview_project, null);
			TextView name = (TextView) rowView.findViewById(R.id.name);
			TextView theme = (TextView) rowView.findViewById(R.id.theme);
			TextView status = (TextView) rowView.findViewById(R.id.status);
			ImageView proto = (ImageView) rowView.findViewById(R.id.proto);
			
			holder.name = name;
			holder.theme = theme;
			holder.status = status;
			holder.img = proto;
			
			rowView.setTag(holder);

		}
		else
			holder = (Holder) rowView.getTag();
		
		Log.d("BLASUSER", "Position ["+position+"]");
		Project p = list.get(position);
		Log.d("BLASUSER", "Project "+p.toString());
		holder.name.setText(p.getName());		
		holder.theme.setText(p.getTheme());
		holder.status.setText(status[p.getStatus()]);
		holder.img.setImageURI(prototypes.get(p.getRemoteId()));
		
		return rowView;
	}

}
