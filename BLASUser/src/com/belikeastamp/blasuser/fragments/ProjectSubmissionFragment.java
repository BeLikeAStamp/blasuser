package com.belikeastamp.blasuser.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.util.ProjectData;

public class ProjectSubmissionFragment extends Fragment {

	private HashMap<String, List<String>> themes;
	private ProjectData globalVariable;
	private List<String>cardThemes;
	Spinner card_type_spinner;
	Spinner card_theme_spinner;


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


		cardThemes.add(getActivity().getResources().getString(R.string.theme_prompt));

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
				R.array.cardtypes, R.layout.simple_spinner_item);
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
				card_theme_spinner.setVisibility(View.VISIBLE);
				Log.d("BLASUSER", "type = "+globalVariable.getProjectType());
				Log.d("BLASUSER", "theme = "+cardThemes);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				globalVariable.setProjectType((String)parent.getItemAtPosition(0));
			}


		});


	}






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
