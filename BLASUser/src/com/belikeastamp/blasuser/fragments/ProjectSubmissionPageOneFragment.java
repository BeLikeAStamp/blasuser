package com.belikeastamp.blasuser.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.activities.ProjectSubmissionPageTwo;
import com.belikeastamp.blasuser.db.dao.ProjectsData;
import com.belikeastamp.blasuser.util.ColorPicker;
import com.belikeastamp.blasuser.util.ColorPickerAdapter;
import com.belikeastamp.blasuser.util.DatePickerDialogFragment;
import com.belikeastamp.blasuser.util.HourPickerDialogFragment;
import com.belikeastamp.blasuser.util.ProjectData;

public class ProjectSubmissionPageOneFragment extends Fragment {

	private ProjectData globalVariable;
	private Spinner card_type_spinner;
	private EditText project_name;
	private Button continuer;
	private GridView gridView1;
	private ImageView color1, color2, color3;
	private ViewFlipper viewflipper;
	private CheckBox putName, putAge;

	private boolean[] selectedColors = new boolean[3];
	private ArrayList<ImageView> selectedColorsList = new ArrayList<ImageView>();
	private ArrayList<ColorPicker> colorPickerArray1;

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

		View rootView = inflater.inflate(R.layout.fragment_project_new_project_form, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// nom de projet
		project_name = (EditText) this.getView().findViewById(R.id.project_name);

		// type de carte
		card_type_spinner = (Spinner) this.getView().findViewById(R.id.card_type_spinner);

		// personnalisation
		viewflipper = (ViewFlipper)this.getView().findViewById(R.id.vf);
		viewflipper.setMeasureAllChildren(false);

		// A afficher sur la carte
		putName = (CheckBox) this.getView().findViewById(R.id.put_name);
		putAge = (CheckBox) this.getView().findViewById(R.id.put_age);

		// les couleurs
		gridView1 = (GridView)getView().findViewById(R.id.color_grid1);
		color1 = (ImageView) getView().findViewById(R.id.selected_color1);
		color2 = (ImageView) getView().findViewById(R.id.selected_color2);
		color3 = (ImageView) getView().findViewById(R.id.selected_color3);

		// validation
		continuer = (Button) this.getView().findViewById(R.id.btn_continue);

		/*************************** LES CHOSES SERIEUSES COMMENCENT **********************************/

		globalVariable = (ProjectData) getActivity().getApplicationContext();

		globalVariable.setSubmitDate(getDate(today));

		project_name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.setProjectName(project_name.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

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
				switch (position) {
				case 4:
					viewflipper.setDisplayedChild(2);
					prepareLayout3();
					break;
				case 10:
					viewflipper.setDisplayedChild(3);
					prepareLayout4();
					break;
				case 11:
					viewflipper.setDisplayedChild(1);
					prepareLayout2();
					break;
				case 12:
					viewflipper.setDisplayedChild(4);
					prepareLayout6();
					break;
				case 13:
					viewflipper.setDisplayedChild(5);
					prepareLayout7();
					break;
				default:
					viewflipper.setDisplayedChild(0);
					prepareLayout1();
					break;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				//  Auto-generated method stub
				globalVariable.setProjectType((String)parent.getItemAtPosition(0));
			}


		});

		color1.setOnClickListener(new RazOnClickListener());
		color2.setOnClickListener(new RazOnClickListener());
		color3.setOnClickListener(new RazOnClickListener());

		Arrays.fill(selectedColors, false);
		selectedColorsList.add(color1);
		selectedColorsList.add(color2);
		selectedColorsList.add(color3);

		colorPickerArray1 = getColorCat1();

		ColorPickerAdapter colorPickerAdapter1 = new ColorPickerAdapter(getActivity().getApplicationContext(), colorPickerArray1);
		gridView1.setAdapter(colorPickerAdapter1);		
		gridView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if(selectedColors[2]&&selectedColors[1]&&selectedColors[0])
					Toast.makeText(getActivity().getApplicationContext(), R.string.color_warning, Toast.LENGTH_LONG).show();
				else
				{
					ColorPicker picker = (ColorPicker) arg0.getItemAtPosition(arg2);
					int pickerColor = picker.getColorCode();
					for(int i = 0 ; i < 3 ; i++) {
						if(!(selectedColors[i])) {
							selectedColorsList.get(i).setBackgroundResource(pickerColor);
							if(i==0)globalVariable.setColor1(picker.getColorCode());
							if(i==1)globalVariable.setColor2(picker.getColorCode());
							if(i==2)globalVariable.setColor3(picker.getColorCode());
							selectedColors[i] = true;
							break;
						}
					}
				}
			}

		});

		continuer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//  Auto-generated method stub
				Log.i("PROJET","=>"+globalVariable.toString());
				Intent i = new Intent(getActivity(), ProjectSubmissionPageTwo.class);
				startActivity(i);
			}
		});



		/*continuer.setOnClickListener(new View.OnClickListener() {

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
		});*/
	}

	private void prepareLayout1() {
		View myLayout = getView().findViewById(R.id.layout1);
		Spinner gender = (Spinner) myLayout.findViewById(R.id.gender); 
		final EditText name = (EditText) myLayout.findViewById(R.id.firstname);
		Spinner age_type = (Spinner) myLayout.findViewById(R.id.age_type);
		final EditText age = (EditText) myLayout.findViewById(R.id.age);

		gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("1.gender",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		age_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("1.age-type",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("1.name", name.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		age.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("1.age", age.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});


	}


	private void prepareLayout2() {
		View myLayout = getView().findViewById(R.id.layout2);
		Spinner gender1 = (Spinner) myLayout.findViewById(R.id.gender1); 
		final EditText name1 = (EditText) myLayout.findViewById(R.id.firstname1);
		Spinner age_type1 = (Spinner) myLayout.findViewById(R.id.age_type1);
		final EditText age1 = (EditText) myLayout.findViewById(R.id.age1);

		Spinner gender2 = (Spinner) myLayout.findViewById(R.id.gender2); 
		final EditText name2 = (EditText) myLayout.findViewById(R.id.firstname2);
		Spinner age_type2 = (Spinner) myLayout.findViewById(R.id.age_type2);
		final EditText age2 = (EditText) myLayout.findViewById(R.id.age2);

		final Button event_date = (Button) myLayout.findViewById(R.id.event_date);
		final Button event_hour = (Button) myLayout.findViewById(R.id.event_hour);

		final EditText address = (EditText) myLayout.findViewById(R.id.address);
		
		gender1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("1.gender",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		age_type1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("1.age-type",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});



		gender2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("2.gender",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		age_type2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("2.age-type",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		name2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("2.name", name2.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		age2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("2.age", age2.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		name1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("1.name", name1.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		age1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("1.age", age1.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});


		setDate(delay, event_date);
		event_hour.setText("00:00");

		event_date.setOnClickListener(new OnClickListener() {  
			@Override  
			public void onClick(View v) {  
				DialogFragment newFragment = new DatePickerDialogFragment(new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						Calendar c = Calendar.getInstance();  
						c.set(year, monthOfYear, dayOfMonth);
						delay = c.getTime().getTime();
						setDate(c.getTime().getTime(),event_date); 
						globalVariable.addNewDetails("event-date", getDate(c.getTime().getTime()));
					}
				} );  
				newFragment.show(getFragmentManager(), "datePicker");
			}  
		});

		event_hour.setOnClickListener(new OnClickListener() {  
			@Override  
			public void onClick(View v) {  
				DialogFragment newFragment = new HourPickerDialogFragment(new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						// TODO Auto-generated method stub
						final Calendar c = Calendar.getInstance();
						c.set(Calendar.HOUR_OF_DAY, hourOfDay);
						c.set(Calendar.MINUTE, minute);
						event_hour.setText(hourOfDay+":"+minute);
						globalVariable.addNewDetails("event-hour", hourOfDay+":"+minute);
					}

				});

				newFragment.show(getFragmentManager(), "hourPicker");
			}
		});
		
		address.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("address", address.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		
		
	}			

	private void prepareLayout3() {
		// TODO Auto-generated method stub
		View myLayout = getView().findViewById(R.id.layout3);
		Spinner gender1 = (Spinner) myLayout.findViewById(R.id.gender1); 
		final EditText name1 = (EditText) myLayout.findViewById(R.id.firstname1);
		Spinner age_type1 = (Spinner) myLayout.findViewById(R.id.age_type1);
		final EditText age1 = (EditText) myLayout.findViewById(R.id.age1);
		Spinner occaz = (Spinner) myLayout.findViewById(R.id.occaz_type);
		final EditText other_occaz = (EditText) myLayout.findViewById(R.id.other_occaz);

		gender1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("1.gender",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		age_type1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("1.age-type",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		occaz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				if(pos != (getActivity().getResources().getStringArray(R.array.occasion_arrays).length - 1)) { 
					globalVariable.addNewDetails("occasion",(String) parent.getItemAtPosition(pos));
					other_occaz.setVisibility(View.GONE);
				}
				else 
				{
					other_occaz.setVisibility(View.VISIBLE);
				}
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		name1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("1.name", name1.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		age1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("1.age", age1.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});


		other_occaz.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("occasion", other_occaz.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});


	}

	private void prepareLayout4() {
		// TODO Auto-generated method stub
		View myLayout = getView().findViewById(R.id.layout4);
		Spinner gender1 = (Spinner) myLayout.findViewById(R.id.gender1); 
		final EditText name1 = (EditText) myLayout.findViewById(R.id.firstname1);
		Spinner age_type1 = (Spinner) myLayout.findViewById(R.id.age_type1);
		final EditText age1 = (EditText) myLayout.findViewById(R.id.age1);

		Spinner gender2 = (Spinner) myLayout.findViewById(R.id.gender2); 
		final EditText name2 = (EditText) myLayout.findViewById(R.id.firstname2);
		Spinner age_type2 = (Spinner) myLayout.findViewById(R.id.age_type2);
		final EditText age2 = (EditText) myLayout.findViewById(R.id.age2);
		final EditText nbr_years = (EditText) myLayout.findViewById(R.id.nbr_years);

		gender1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("1.gender",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		age_type1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("1.age-type",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});



		gender2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("2.gender",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		age_type2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("2.age-type",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		name2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("2.name", name2.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		age2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("2.age", age2.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		name1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("1.name", name1.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		age1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("1.age", age1.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		nbr_years.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("nbr_years", nbr_years.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void prepareLayout6() {
		// TODO Auto-generated method stub
		View myLayout = getView().findViewById(R.id.layout6);
		Spinner gender1 = (Spinner) myLayout.findViewById(R.id.gender1);
		Spinner gender2 = (Spinner) myLayout.findViewById(R.id.gender2); 
		final EditText name1 = (EditText) myLayout.findViewById(R.id.firstname1);
		final EditText poids1 = (EditText) myLayout.findViewById(R.id.weight);
		final EditText taille1 = (EditText) myLayout.findViewById(R.id.height);
		final EditText name2 = (EditText) myLayout.findViewById(R.id.firstname2);
		final EditText poids2 = (EditText) myLayout.findViewById(R.id.weight2);
		final EditText taille2 = (EditText) myLayout.findViewById(R.id.height2);
		final Button event_date = (Button) myLayout.findViewById(R.id.event_date);
		final Button add_baby = (Button) myLayout.findViewById(R.id.add_baby);
		final LinearLayout perso2 = (LinearLayout) myLayout.findViewById(R.id.perso2);

		setDate(delay, event_date);
		event_date.setOnClickListener(new OnClickListener() {  
			@Override  
			public void onClick(View v) {  
				DialogFragment newFragment = new DatePickerDialogFragment(new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						Calendar c = Calendar.getInstance();  
						c.set(year, monthOfYear, dayOfMonth);
						delay = c.getTime().getTime();
						setDate(c.getTime().getTime(),event_date); 
						globalVariable.addNewDetails("event-date", getDate(c.getTime().getTime()));
					}
				} );  
				newFragment.show(getFragmentManager(), "datePicker");
			}  
		});

		add_baby.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				perso2.setVisibility(View.VISIBLE);
				add_baby.setVisibility(View.GONE);
			}
		});

		gender1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("1.gender",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		name1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("1.name", name1.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});




		gender2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("2.gender",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});


		name2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("2.name", name2.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		taille1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("1.height", taille1.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});


		taille2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("2.height", taille2.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		poids1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("1.weigth", poids1.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		poids2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("2.weigth", poids2.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});


	}

	private void prepareLayout7() {
		// TODO Auto-generated method stub
		View myLayout = getView().findViewById(R.id.layout7);
		Spinner gender = (Spinner) myLayout.findViewById(R.id.gender);
		Spinner occaz = (Spinner) myLayout.findViewById(R.id.occaz_type);
		final EditText name = (EditText) myLayout.findViewById(R.id.firstname);
		Spinner age_type = (Spinner) myLayout.findViewById(R.id.age_type);
		final EditText age = (EditText) myLayout.findViewById(R.id.age);
		final EditText other_occaz = (EditText) myLayout.findViewById(R.id.other_occaz);
		final Button event_date = (Button) myLayout.findViewById(R.id.event_date);
		final Button event_hour = (Button) myLayout.findViewById(R.id.event_hour);
		final EditText address = (EditText) myLayout.findViewById(R.id.address);
		
		
		gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("1.gender",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		age_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				globalVariable.addNewDetails("age-type",(String) parent.getItemAtPosition(pos));
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("1.name", name.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		age.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("1.age", age.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		occaz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				if(pos != (getActivity().getResources().getStringArray(R.array.occasion_arrays).length - 1)) { 
					globalVariable.addNewDetails("occasion",(String) parent.getItemAtPosition(pos));
					other_occaz.setVisibility(View.GONE);
				}
				else 
				{
					other_occaz.setVisibility(View.VISIBLE);
				}
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		other_occaz.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("occasion", other_occaz.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		setDate(delay, event_date);
		event_hour.setText("00:00");

		event_date.setOnClickListener(new OnClickListener() {  
			@Override  
			public void onClick(View v) {  
				DialogFragment newFragment = new DatePickerDialogFragment(new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						Calendar c = Calendar.getInstance();  
						c.set(year, monthOfYear, dayOfMonth);
						delay = c.getTime().getTime();
						setDate(c.getTime().getTime(),event_date); 
						globalVariable.addNewDetails("event-date", getDate(c.getTime().getTime()));
					}
				} );  
				newFragment.show(getFragmentManager(), "datePicker");
			}  
		});

		event_hour.setOnClickListener(new OnClickListener() {  
			@Override  
			public void onClick(View v) {  
				DialogFragment newFragment = new HourPickerDialogFragment(new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						// TODO Auto-generated method stub
						final Calendar c = Calendar.getInstance();
						c.set(Calendar.HOUR_OF_DAY, hourOfDay);
						c.set(Calendar.MINUTE, minute);
						event_hour.setText(hourOfDay+":"+minute);
						globalVariable.addNewDetails("event-hour", hourOfDay+":"+minute);
					}

				});

				newFragment.show(getFragmentManager(), "hourPicker");
			}
		});
		
		address.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				globalVariable.addNewDetails("adresse", address.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		
		
	}



	private String getDate(long millisecond){  
		int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR  
				| DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_MONTH  
				| DateUtils.FORMAT_ABBREV_WEEKDAY;  
		String dateString = DateUtils.formatDateTime(getActivity().getApplicationContext(),millisecond, flags);  
		return dateString;  
	}

	private void setDate(long millisecond, Button v){  
		int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR  
				| DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_MONTH  
				| DateUtils.FORMAT_ABBREV_WEEKDAY;  
		String dateString = DateUtils.formatDateTime(getActivity().getApplicationContext(),millisecond, flags);
		v.setText(dateString);  
	}

	/*private boolean checkEntries() {
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

		// check personnalisation data
		if(putFirstName.isChecked()) {
			if(prenom1.getText().toString().length() == 0) {
				everythin_good = false;
				msg = getActivity().getApplicationContext().getResources().getString(R.string.err_no_firstname);
				prenom1.setError(msg);
			}

			if(person2Layout.isShown()) {
				if(prenom2.getText().toString().length() == 0) {
					everythin_good = false;
					msg = getActivity().getApplicationContext().getResources().getString(R.string.err_no_firstname);
					prenom2.setError(msg);
				}
			}
		}

		if(putAge.isChecked()) {
			if(age1.getText().toString().length() == 0) {
				everythin_good = false;
				msg = getActivity().getApplicationContext().getResources().getString(R.string.err_no_age);
				age1.setError(msg);
			}

			if(person2Layout.isShown()) {
				if(age2.getText().toString().length() == 0) {
					everythin_good = false;
					msg = getActivity().getApplicationContext().getResources().getString(R.string.err_no_age);
					age2.setError(msg);
				}
			}
		}

		// check color set
		if((!selectedColors[2]) && (!selectedColors[1]) && (!selectedColors[0]))
		{
			everythin_good = false;
			Toast.makeText(getActivity().getApplicationContext(), R.string.nocolor_warning, Toast.LENGTH_SHORT).show();
		}


		//check precision
		if (nbrYearsLayout.isShown())
		{
			if(Integer.valueOf(nbrYears.getText().toString()).equals(Integer.valueOf(0))) {
				everythin_good = false;
				msg = getActivity().getApplicationContext().getResources().getString(R.string.err_no_nbr_years);
				nbrYears.setError(msg);
			}
		}


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





		firstname.addTextChangedListener(new TextWatcher() {

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
				perso.setName(s.toString());
			}
		});


		age.addTextChangedListener(new TextWatcher() {

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
				perso.setAge(s.toString());
			}
		});


		gender.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.male) {
					perso.setSexe("M");

				}
				else
				{
					perso.setSexe("F");
				}
			}
		});

		Log.i("checkEntries", "faisable = "+everythin_good);

		return everythin_good;
	}*/


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

	public ArrayList<ColorPicker> getColorCat1() {
		ArrayList<ColorPicker> list = new ArrayList<ColorPicker>();
		// tendances
		list.add(new ColorPicker(R.color.rasberry_ripple));
		list.add(new ColorPicker(R.color.summer_starfruit));
		list.add(new ColorPicker(R.color.midnight_muse));
		list.add(new ColorPicker(R.color.primrose_petals));
		list.add(new ColorPicker(R.color.gumball_green));
		list.add(new ColorPicker(R.color.baked_brown_sugar));
		list.add(new ColorPicker(R.color.coastal_cabana));
		list.add(new ColorPicker(R.color.crisp_cantaloupe));
		list.add(new ColorPicker(R.color.strawberry_slush));
		list.add(new ColorPicker(R.color.pistachio_pudding));
		// eclatantes
		list.add(new ColorPicker(R.color.bermuda_bay));
		list.add(new ColorPicker(R.color.daffodil_delight));
		list.add(new ColorPicker(R.color.melon_mambo));
		list.add(new ColorPicker(R.color.old_olive));
		list.add(new ColorPicker(R.color.pacific_point));
		list.add(new ColorPicker(R.color.pumpkin_pie));
		list.add(new ColorPicker(R.color.real_red));
		list.add(new ColorPicker(R.color.rich_razzleberry));
		list.add(new ColorPicker(R.color.tangerine_tango));
		list.add(new ColorPicker(R.color.tempting_turquoise));
		// subtiles
		list.add(new ColorPicker(R.color.blushing_bride));
		list.add(new ColorPicker(R.color.calypso_coral));
		list.add(new ColorPicker(R.color.marina_mist));
		list.add(new ColorPicker(R.color.pear_pizzazz));
		list.add(new ColorPicker(R.color.pink_pirouette));
		list.add(new ColorPicker(R.color.pool_party));
		list.add(new ColorPicker(R.color.so_saffron));
		list.add(new ColorPicker(R.color.soft_sky));
		list.add(new ColorPicker(R.color.wild_wasabi));
		list.add(new ColorPicker(R.color.wisteria_wonder));
		// gourmandes
		list.add(new ColorPicker(R.color.always_artichoke));
		list.add(new ColorPicker(R.color.cajun_craze));
		list.add(new ColorPicker(R.color.cherry_cobbler));
		list.add(new ColorPicker(R.color.crushed_curry));
		list.add(new ColorPicker(R.color.elegant_eggplant));
		list.add(new ColorPicker(R.color.garden_green));
		list.add(new ColorPicker(R.color.island_indigo));
		list.add(new ColorPicker(R.color.night_of_navy));
		list.add(new ColorPicker(R.color.rose_red));
		list.add(new ColorPicker(R.color.perfect_plum));
		// neutres
		list.add(new ColorPicker(R.color.basic_black));
		list.add(new ColorPicker(R.color.basic_gray));
		list.add(new ColorPicker(R.color.chocolate_chip));
		list.add(new ColorPicker(R.color.crumb_cake));
		list.add(new ColorPicker(R.color.early_espresso));
		list.add(new ColorPicker(R.color.sahara_sand));
		list.add(new ColorPicker(R.color.whisper_white));
		list.add(new ColorPicker(R.color.smoky_slate));
		list.add(new ColorPicker(R.color.soft_suede));
		list.add(new ColorPicker(R.color.very_vanilla));


		return list;
	}

	private class RazOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ImageView i = (ImageView) v;
			i.setBackgroundResource(android.R.color.transparent);
			switch(i.getId()) {
			case R.id.selected_color1:
				selectedColors[0] = false;
				break;
			case R.id.selected_color2:
				selectedColors[1] = false;
				break;
			case R.id.selected_color3:
				selectedColors[2] = false;
				break;
			}
		}
	}
}
