package com.belikeastamp.blasuser.adapter;

import java.util.List;

import org.apache.http.conn.routing.RouteInfo.TunnelType;

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
		TextView theme;
		TextView town;
		TextView date;
		TextView price;
		//ImageView icon;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;
		Holder holder = new Holder();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.listview_tutorial, null);
			TextView theme = (TextView) rowView.findViewById(R.id.theme);
			TextView town = (TextView) rowView.findViewById(R.id.town);
			TextView date = (TextView) rowView.findViewById(R.id.date);
			TextView price = (TextView) rowView.findViewById(R.id.price);
			//ImageView icon = (ImageView) rowView.findViewById(R.id.icon);

			holder.theme = theme;
			holder.town = town;
			holder.date = date;
			holder.price = price;
			//holder.icon = icon;

			rowView.setTag(holder);

		}

		holder = (Holder) rowView.getTag();

		/*Workshop w = list.get(position);
		Log.d("BLASUSER", "Workshop "+w.toString());

		holder.theme.setText(context.getResources().getString(R.string.theme)+" "+w.getTheme());
		holder.town.setText(context.getResources().getString(R.string.town)+" "+w.getTown());
		holder.date.setText(context.getResources().getString(R.string.date)+" "+w.getDate());
		holder.price.setText(context.getResources().getString(R.string.price)+" "+w.getPrice());*/

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
