package com.belikeastamp.blasuser.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.activities.ProjectSubmissionPageTwo.SubmissionDialogFragment;
import com.belikeastamp.blasuser.util.ColorPicker;
import com.belikeastamp.blasuser.util.ColorPickerAdapter;
import com.belikeastamp.blasuser.util.PersoSubject;
import com.belikeastamp.blasuser.util.ProjectData;

public class ProjectSubmissionPageTwoFragment extends Fragment {
	private GridView gridView1;

	private ImageView color1;
	private ImageView color2;
	private ImageView color3;
	private Button valider;
	private EditText firstname;
	private EditText age;
	private RadioGroup gender;
	private RadioGroup namedCard;
	private LinearLayout layout2;
	private EditText infos;
	private boolean anonymous = true;
	private PersoSubject perso = new PersoSubject();
	private ProjectData globalVariable;
	private boolean[] selectedColors = new boolean[3];
	private ArrayList<ImageView> selectedColorsList = new ArrayList<ImageView>();

	private ArrayList<ColorPicker> colorPickerArray1;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_project_submission_2, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		globalVariable = (ProjectData) getActivity().getApplicationContext();

		gridView1 = (GridView)getView().findViewById(R.id.color_grid1);
		color1 = (ImageView) getView().findViewById(R.id.selected_color1);
		color2 = (ImageView) getView().findViewById(R.id.selected_color2);
		color3 = (ImageView) getView().findViewById(R.id.selected_color3);

		firstname = (EditText) getView().findViewById(R.id.firstname);
		age = (EditText) getView().findViewById(R.id.age);
		gender = (RadioGroup) getView().findViewById(R.id.sexechoice);
		namedCard = (RadioGroup) getView().findViewById(R.id.nominalchoice);
		layout2 = (LinearLayout) getView().findViewById(R.id.layout2);
		valider = (Button) getView().findViewById(R.id.btn_continue);
		infos = (EditText) getView().findViewById(R.id.infos);
		
		
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
				// TODO Auto-generated method stub
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


		namedCard.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.not_anonyme) {
					layout2.setVisibility(View.VISIBLE);
					anonymous = false;
				}
				else
				{
					layout2.setVisibility(View.INVISIBLE);
					anonymous = true;
					perso.setAge("");perso.setName("");perso.setSexe("");
				}
			}
		});


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


		valider.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(!(infos.getText().toString().isEmpty()))
					globalVariable.setInfos(infos.getText().toString());
					
				if(selectedColors[2] || selectedColors[1] || selectedColors[0]) { // au moins 1 couleur

					if(selectedColors[0] == false)globalVariable.setColor1(-1);
					if(selectedColors[1] == false)globalVariable.setColor2(-1);
					if(selectedColors[2] == false)globalVariable.setColor3(-1);


					if (!anonymous)
					{
						if (perso.getAge().isEmpty() 
								&& perso.getName().isEmpty() 
								&& perso.getSexe().isEmpty()) {
							Toast.makeText(getActivity().getApplicationContext(), getResources().getText(R.string.infos_manquantes), Toast.LENGTH_LONG).show();
						}
						else
						{
							globalVariable.setPerso(perso);
							//Intent intent = new Intent(PersonnalizationActivity.this,SubmissionActivity.class);
							//startActivity(intent);
							showDialog();
						}
					}
					else {

						//Intent intent = new Intent(PersonnalizationActivity.this,SubmissionActivity.class);
						//startActivity(intent);
						showDialog();
					}

				}
				else
				{
					Toast.makeText(getActivity().getApplicationContext(), R.string.nocolor_warning, Toast.LENGTH_SHORT).show();
				}


				Log.i("PROJET","=>"+globalVariable.toString());
			}
		});


	}

	void showDialog() {
		DialogFragment newFragment = SubmissionDialogFragment
				.newInstance(R.string.alert_check, globalVariable);
		newFragment.show(getFragmentManager(), "dialog");
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

	private int validSubmission() {
		int ret = 0;





		return ret;
	}
}
