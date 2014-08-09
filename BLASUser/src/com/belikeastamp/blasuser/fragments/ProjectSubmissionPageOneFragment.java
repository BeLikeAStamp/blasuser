package com.belikeastamp.blasuser.fragments;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.activities.ProjectSubmissionPageTwo;
import com.belikeastamp.blasuser.db.dao.ProjectsData;
import com.belikeastamp.blasuser.util.DatePickerDialogFragment;
import com.belikeastamp.blasuser.util.ProjectData;

public class ProjectSubmissionPageOneFragment extends Fragment {

	private HashMap<String, List<String>> themes;
	private ProjectData globalVariable;
	private List<String>cardThemes;
	private Spinner card_type_spinner;
	private Spinner card_theme_spinner;
	private Spinner card_style_spinner;
	private EditText other_theme;
	private EditText other_style;
	private EditText project_name;
	private EditText card_num;
	private Button selectDate;
	private Button continuer;

	final static int BACKINTIME = 1;
	final static int IMPOSSIBLE = 2;
	final static int NOINFOS = 3;
	final static int FAISABLE = 4;

	private static final int ENTRY_OK = 0;
	private static final int ILLEGAL_CHAR = 1;
	private static final int EMPTY = 2;
	private static final int NOT_UNIQ = 3;

	final static long WEEK = 604800000L;
	private long today = System.currentTimeMillis();
	private long delay = today + WEEK;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_project_submission, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		globalVariable = (ProjectData) getActivity().getApplicationContext();

		setThemeList();


		card_type_spinner = (Spinner) this.getView().findViewById(R.id.card_type_spinner);
		card_theme_spinner = (Spinner) this.getView().findViewById(R.id.card_theme_spinner);
		card_style_spinner = (Spinner) this.getView().findViewById(R.id.card_style_spinner);
		project_name = (EditText) this.getView().findViewById(R.id.project_name);
		other_style = (EditText) this.getView().findViewById(R.id.other_style);
		other_theme = (EditText) this.getView().findViewById(R.id.other_theme);
		card_num = (EditText) this.getView().findViewById(R.id.card_num);
		selectDate = (Button) this.getView().findViewById(R.id.select_date);
		continuer = (Button) this.getView().findViewById(R.id.btn_continue);

		setDate(delay);
		globalVariable.setSubmitDate(getDate(today));

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
				R.array.type_arrays, R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		card_type_spinner.setAdapter(adapter);

		card_type_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				//  Auto-generated method stub
				globalVariable.setProjectType((String)parent.getItemAtPosition(position));
				cardThemes = themes.get(globalVariable.getProjectType());

				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(),
						R.layout.simple_spinner_item, cardThemes);
				adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
				card_theme_spinner.setAdapter(adapter2);


			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				//  Auto-generated method stub
				globalVariable.setProjectType((String)parent.getItemAtPosition(0));
			}


		});

		card_theme_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position != parent.getCount() - 1) {
					globalVariable.setProjectTheme(parent.getItemAtPosition(position).toString());
					other_theme.setVisibility(View.GONE);
				}
				else
				{
					other_theme.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				//  Auto-generated method stub

			}
		});

		other_theme.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//  Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				//  Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				//  Auto-generated method stub
				globalVariable.setProjectTheme(s.toString());
			}
		});

		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
				R.array.style_arrays, R.layout.simple_spinner_item);
		adapter3.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		card_style_spinner.setAdapter(adapter3);

		card_style_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position != parent.getCount() - 1) {
					globalVariable.setProjectStyle(parent.getItemAtPosition(position).toString());
					other_style.setVisibility(View.GONE);
				}
				else
				{
					other_style.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				//  Auto-generated method stub

			}
		});

		other_style.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//  Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				//  Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				//  Auto-generated method stub
				globalVariable.setProjectStyle(s.toString());
			}
		});

		selectDate.setOnClickListener(new OnClickListener() {  
			@Override  
			public void onClick(View v) {  
				DialogFragment newFragment = new DatePickerDialogFragment(callback);  
				newFragment.show(getFragmentManager(), "datePicker");
			}  
		});

		continuer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//  Auto-generated method stub
				if(checkEntries()) {
					Log.i("PROJET","=>"+globalVariable.toString());
					Intent i = new Intent(getActivity(), ProjectSubmissionPageTwo.class);
					startActivity(i);
				}
				else
				{
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
					alertDialogBuilder.setTitle(getActivity().getResources().getString(R.string.alertMsg));
					alertDialogBuilder
					.setMessage(getActivity().getResources().getString(R.string.alertBox))
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							//MainActivity.this.finish();
						}
					});
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();
				}
			}
		});



	}



	protected int isFaisable() {
		int faisable = -1;

		if(delay == today) faisable = NOINFOS;
		else if (delay < today) {
			faisable = BACKINTIME;
		}
		else if (delay - today < WEEK) {
			faisable = IMPOSSIBLE;
		}
		else faisable = FAISABLE;

		return faisable;
	}

	private void setDate(long millisecond){  
		int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR  
				| DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_MONTH  
				| DateUtils.FORMAT_ABBREV_WEEKDAY;  
		String dateString = DateUtils.formatDateTime(getActivity().getApplicationContext(),millisecond, flags);
		selectDate.setText(dateString);  

	}

	private String getDate(long millisecond){  
		int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR  
				| DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_MONTH  
				| DateUtils.FORMAT_ABBREV_WEEKDAY;  
		String dateString = DateUtils.formatDateTime(getActivity().getApplicationContext(),millisecond, flags);  
		return dateString;  
	}

	OnDateSetListener callback = new OnDateSetListener() {  

		@Override  
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {  
			Calendar c = Calendar.getInstance();  
			c.set(year, monthOfYear, dayOfMonth);
			delay = c.getTime().getTime();
			setDate(c.getTime().getTime());  

		}  
	}; 

	private void setThemeList() {
		themes = new HashMap<String, List<String>>();
		themes.put("Save The Date", Arrays.asList("std1","std2","std3","std4","Autre"));
		themes.put("Remerciements", Arrays.asList("rem1","rem2","rem3","rem4","rem5", "Autre"));
		themes.put("Fêtes", Arrays.asList("fet1","fet2","fet3","fet4","Autre"));
		themes.put("Anniversaires", Arrays.asList("ann1","ann2","ann3","ann4","Autre"));
		themes.put("Voeux", Arrays.asList("voe1","voe2","voe3","voe4","Autre"));
		themes.put("Saint Valentin", Arrays.asList("stv1","stv2","Autre"));
		themes.put("Félicitations", Arrays.asList("fel1","fel2","Autre"));
	}

	private boolean checkEntries() {
		boolean everythin_good = true;
		String msg = "";
		int ret = 0;

		// check project name
		ret = checkEntry(project_name.getText().toString());
		if(ret != ENTRY_OK) everythin_good = false;

		switch (ret) {
		case EMPTY:
			msg = getActivity().getApplicationContext().getResources().getString(R.string.err_no_project);
			project_name.setError(msg);
			break;
		case ILLEGAL_CHAR:
			msg = getActivity().getApplicationContext().getResources().getString(R.string.err_illegal_char);
			project_name.setError(msg);
			break;
		case NOT_UNIQ:
			msg = getActivity().getApplicationContext().getResources().getString(R.string.err_not_uniq);
			project_name.setError(msg);
			break;	
		default:
			globalVariable.setProjectName(project_name.getText().toString());
			break;
		}

		Log.i("checkEntries", "project_name = "+everythin_good);

		if(other_theme.isShown()) {
			// check other theme
			ret = checkEntry(other_theme.getText().toString());
			if(ret != ENTRY_OK) everythin_good = false;

			Log.i("checkEntries", "other_theme = "+everythin_good);

			switch (ret) {
			case EMPTY:
				msg = getActivity().getApplicationContext().getResources().getString(R.string.err_no_theme);
				other_theme.setError(msg);
				break;
			case ILLEGAL_CHAR:
				msg = getActivity().getApplicationContext().getResources().getString(R.string.err_illegal_char);
				other_theme.setError(msg);
				break;
			default:
				globalVariable.setProjectTheme(other_theme.getText().toString());
				break;
			}
		}

		// check other style

		if(other_style.isShown()) {
			ret = checkEntry(other_style.getText().toString());
			if(ret != ENTRY_OK) everythin_good = false;

			Log.i("checkEntries", "other_style = "+everythin_good);

			switch (ret) {
			case EMPTY:
				msg = getActivity().getApplicationContext().getResources().getString(R.string.err_no_style);
				other_style.setError(msg);
				break;
			case ILLEGAL_CHAR:
				msg = getActivity().getApplicationContext().getResources().getString(R.string.err_illegal_char);
				other_style.setError(msg);
				break;
			default:
				globalVariable.setProjectStyle(other_style.getText().toString());
				break;
			}
		}

		Log.i("checkEntries", "card num = "+everythin_good);

		if(Integer.valueOf(card_num.getText().toString()) == 0)
		{
			everythin_good = false;
			msg = getActivity().getApplicationContext().getResources().getString(R.string.zero_card);
			card_num.setError(msg);
		}
		else
		{
			globalVariable.setNumberOfCards(card_num.getText().toString());
		}



		switch (isFaisable()) {

		case FAISABLE:
			globalVariable.setOrderDate(selectDate.getText().toString());
			break;
		case IMPOSSIBLE:
			Toast.makeText(getActivity().getApplicationContext(), 
					getActivity().getApplicationContext().getResources().getString(R.string.mission_impossible), Toast.LENGTH_LONG).show() ;
			everythin_good = false;
			break;
		case NOINFOS:
			Toast.makeText(getActivity().getApplicationContext(),
					getActivity().getApplicationContext().getResources().getString(R.string.infos_manquantes), Toast.LENGTH_LONG).show();
			everythin_good = false;
			break;
		case BACKINTIME:
			Toast.makeText(getActivity().getApplicationContext(),
					getActivity().getApplicationContext().getResources().getString(R.string.back_in_time), Toast.LENGTH_LONG).show();
			everythin_good = false;
			break;
		default:
			break;
		}

		Log.i("checkEntries", "faisable = "+everythin_good);

		return everythin_good;
	}


	private int checkEntry(String s) {
		int ret = ENTRY_OK;
		if((ret = checkUnicity(s)) == ENTRY_OK) {
			if(!(s.matches("[a-zA-Z0-9_]*"))) ret = ILLEGAL_CHAR;
			if (s.length() == 0) ret = EMPTY;
		}

		return ret;		
	}

	private int checkUnicity(String s) {
		int ret = ENTRY_OK;
		ProjectsData datasource = new ProjectsData(getActivity().getApplicationContext());

		datasource.open();

		if (!(datasource.checkUnicity(s))) {			
			ret = NOT_UNIQ;
		}

		return ret;
	}
}
