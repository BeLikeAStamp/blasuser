package com.belikeastamp.blasuser.fragments;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
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

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.util.DatePickerDialogFragment;
import com.belikeastamp.blasuser.util.ProjectData;

public class ProjectSubmissionFragment extends Fragment {

	private HashMap<String, List<String>> themes;
	private ProjectData globalVariable;
	private List<String>cardThemes;
	private Spinner card_type_spinner;
	private Spinner card_theme_spinner;
	private Spinner card_style_spinner;
	private EditText other_theme;
	private EditText other_style;
	private Button selectDate;
	final static int BACKINTIME = 1;
	final static int IMPOSSIBLE = 2;
	final static int NOINFOS = 3;
	final static int FAISABLE = 4;
	final static long WEEK = 604800000L;
	private long today = System.currentTimeMillis();
	private long delay ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_project_submission, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		globalVariable = new ProjectData();

		setThemeList();


		card_type_spinner = (Spinner) this.getView().findViewById(R.id.card_type_spinner);
		card_theme_spinner = (Spinner) this.getView().findViewById(R.id.card_theme_spinner);
		card_style_spinner = (Spinner) this.getView().findViewById(R.id.card_style_spinner);
		other_style = (EditText) this.getView().findViewById(R.id.other_style);
		other_theme = (EditText) this.getView().findViewById(R.id.other_theme);
		selectDate = (Button) this.getView().findViewById(R.id.select_date);

		setDate(today);
		globalVariable.setSubmitDate(getDate(today));
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
				R.array.type_arrays, R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		card_type_spinner.setAdapter(adapter);

		card_type_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				globalVariable.setProjectType((String)parent.getItemAtPosition(position));
				cardThemes = themes.get(globalVariable.getProjectType());

				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(),
						R.layout.simple_spinner_item, cardThemes);
				adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
				card_theme_spinner.setAdapter(adapter2);


			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub

			}
		});

		other_theme.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub

			}
		});

		other_style.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
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
}
