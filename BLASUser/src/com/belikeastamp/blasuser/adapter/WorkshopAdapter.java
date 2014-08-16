package com.belikeastamp.blasuser.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.db.model.Workshop;

public class WorkshopAdapter extends BaseAdapter {

	List<Workshop> list;
	Context context;

	public WorkshopAdapter (Context context, List<Workshop> workshops) {
		this.context = context;
		this.list = workshops;
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
		Button action;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;
		Holder holder = new Holder();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.listview_workshop, null);
			TextView theme = (TextView) rowView.findViewById(R.id.theme);
			TextView town = (TextView) rowView.findViewById(R.id.town);
			TextView date = (TextView) rowView.findViewById(R.id.date);
			TextView price = (TextView) rowView.findViewById(R.id.price);
			Button action = (Button) rowView.findViewById(R.id.action);

			holder.theme = theme;
			holder.town = town;
			holder.date = date;
			holder.price = price;
			holder.action = action;

			rowView.setTag(holder);

		}

		holder = (Holder) rowView.getTag();

		Workshop w = list.get(position);
		Log.d("BLASUSER", "Workshop "+w.toString());

		holder.theme.setText(context.getResources().getString(R.string.theme)+" "+w.getTheme());
		holder.town.setText(context.getResources().getString(R.string.town)+" "+w.getTown());
		holder.date.setText(context.getResources().getString(R.string.date)+" "+w.getDate());
		holder.price.setText(context.getResources().getString(R.string.price)+" "+w.getPrice());
		holder.action.setText(w.getRegistered() >= w.getCapacity() ? context.getResources().getString(R.string.btn_complet)  : context.getResources().getString(R.string.btn_signin));
		if (w.getRegistered() >= w.getCapacity()) {
			holder.action.setEnabled(false);
			holder.action.setBackgroundResource(R.drawable.btn_action_turquoise1);
		}
		
		return rowView;
	}

}
