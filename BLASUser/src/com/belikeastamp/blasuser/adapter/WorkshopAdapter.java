package com.belikeastamp.blasuser.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.sax.StartElementListener;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.activities.MainActivity;
import com.belikeastamp.blasuser.db.model.Inscription;
import com.belikeastamp.blasuser.db.model.Workshop;
import com.belikeastamp.blasuser.util.InscriptionController;

public class WorkshopAdapter extends BaseAdapter {

	List<Workshop> list;
	Activity activity;
	Inscription inscription;
	//AddInscriprionTask task;
	private long today = System.currentTimeMillis();
	
	
	public WorkshopAdapter (Activity activity, List<Workshop> workshops) {
		this.activity = activity;
		this.list = workshops;
		this.inscription = new Inscription();
		//task = new AddInscriprionTask();
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
		TextView theme;
		TextView town;
		TextView date;
		TextView price;
		Button action;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View rowView = convertView;
		Holder holder = new Holder();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

		final Workshop w = list.get(position);

		holder.theme.setText(activity.getResources().getString(R.string.theme)+" "+w.getTheme());
		holder.town.setText(activity.getResources().getString(R.string.town)+" "+w.getTown());
		holder.date.setText(activity.getResources().getString(R.string.date)+" "+w.getDate());
		holder.price.setText(activity.getResources().getString(R.string.price)+" "+w.getPrice());
		holder.action.setText(w.getRegistered() >= w.getCapacity() ? activity.getResources().getString(R.string.btn_complet)  : activity.getResources().getString(R.string.btn_signin));
		if (w.getRegistered() >= w.getCapacity()) {
			holder.action.setEnabled(false);
			holder.action.setBackgroundResource(R.drawable.btn_action_turquoise1);
		}

		holder.action.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				displayDialogWindow(w);
			}
		});

		return rowView;
	}


	public void displayDialogWindow(final Workshop w)
	{
		final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		final View f = activity.getLayoutInflater().inflate(R.layout.prompt_workshop, null);
		builder.setTitle(activity.getResources().getString(R.string.inscription, w.getTheme()));
		builder.setView(f);

		final AlertDialog dialog = builder.create();
		
		Button submit = (Button)f.findViewById(R.id.submit);
		final EditText name = (EditText) f.findViewById(R.id.name);
		final EditText number = (EditText) f.findViewById(R.id.number);
		final EditText email = (EditText) f.findViewById(R.id.email);
		final EditText participant = (EditText) f.findViewById(R.id.participant);
		Spinner expertise = (Spinner)f.findViewById(R.id.expertise);

		ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(
				activity, R.array.expertise, android.R.layout.simple_spinner_item);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		expertise.setAdapter(dataAdapter);
		expertise.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {				
				inscription.setExpertise((String)arg0.getSelectedItem());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
				inscription.setExpertise((String)arg0.getSelectedItem());
			}

		});

		
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(checkEntries()) {
					inscription.setWorkshopId(w.getId());
					inscription.setName(name.getText().toString());
					inscription.setPhoneNumber(number.getText().toString());
					inscription.setEmail(email.getText().toString());
					inscription.setPartcipants(Integer.valueOf(participant.getText().toString()));
					inscription.setInscriptionDate(getDate(today));
					new AddInscriprionTask().execute(inscription);
					Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.demand_return), Toast.LENGTH_SHORT).show();
					dialog.dismiss();
					
					
				}
				else
					Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.alertBox),Toast.LENGTH_SHORT).show();
			}

			private boolean checkEntries() {
				
				boolean everything_ok = true;

				if (name.getText().length() == 0) {
					name.setError(activity.getResources().getString(R.string.err_missing_info));
					everything_ok = false;
				}

				if (number.getText().length() == 0) {
					number.setError(activity.getResources().getString(R.string.err_missing_info));
					everything_ok = false;
				}

				if (email.getText().length() == 0) {
					email.setError(activity.getResources().getString(R.string.err_missing_info));
					everything_ok = false;
				}

				if (participant.getText().length() == 0) {
					participant.setError(activity.getResources().getString(R.string.err_missing_info));
					everything_ok = false;
				}

				return everything_ok;
			}

		});


		dialog.show();
	}

	
	private String getDate(long millisecond){  
		int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR  
				| DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_MONTH  
				| DateUtils.FORMAT_ABBREV_WEEKDAY;  
		String dateString = DateUtils.formatDateTime(activity.getApplicationContext(),millisecond, flags);  
		return dateString;  
	}

	public class AddInscriprionTask extends AsyncTask<Inscription, Void, Void> {

		@Override
		protected Void doInBackground(Inscription... params) {

			Inscription insc = params[0];
			final InscriptionController c = new InscriptionController();
			try {
				c.create(insc);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}
