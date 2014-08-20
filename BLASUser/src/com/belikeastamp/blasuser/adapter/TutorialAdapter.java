package com.belikeastamp.blasuser.adapter;

import java.util.List;

import org.apache.http.conn.routing.RouteInfo.TunnelType;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.db.model.Tutorial;
import com.belikeastamp.blasuser.db.model.Workshop;

public class TutorialAdapter extends BaseAdapter {

	List<Tutorial> list;
	Context context;

	public TutorialAdapter (Context context, List<Tutorial> tutos) {
		this.context = context;
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
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

		holder.title.setText(context.getResources().getString(R.string.title)+" "+t.getTitle());
		holder.dispo.setText(context.getResources().getString(R.string.availability)+" "
		+(t.getAvailable() ? context.getResources().getString(R.string.available)  : context.getResources().getString(R.string.not_available) ));
		holder.action.setText(t.getAvailable() ? context.getResources().getString(R.string.btn_get_tuto)  : context.getResources().getString(R.string.btn_demand));

		/*
		int ratio = 0;
		if(list.get(position).getRegistered() > 0 ) 
			ratio = list.get(position).getCapacity()/list.get(position).getRegistered();

		if (list.get(position).getRegistered() == 0) {
			holder.icon.setImageResource(R.drawable.nobody);
		} else if (list.get(position).getRegistered() > 0 && ratio >= 3){
			holder.icon.setImageResource(R.drawable.tiers);
		} else if (ratio < 3 && ratio >= 2){
			holder.icon.setImageResource(R.drawable.middle);
		} else if (ratio < 2 && list.get(position).getRegistered() < list.get(position).getCapacity()){
			holder.icon.setImageResource(R.drawable.twotiers);
		} else {
			holder.icon.setImageResource(R.drawable.full);
		}*/
		
		return rowView;
	}

}
