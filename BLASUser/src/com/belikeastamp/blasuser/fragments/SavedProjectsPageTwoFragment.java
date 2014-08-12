package com.belikeastamp.blasuser.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.util.ProjectData;

public class SavedProjectsPageTwoFragment extends Fragment {

	private TextView projectName;
	private TextView type;
	private TextView detail;
	private TextView nbrCards;
	private TextView orderDate;
	private TextView perso;
	private ImageView color1;
	private ImageView color2;
	private ImageView color3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_saved_project_details, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Project project = (Project) getActivity().getIntent().getSerializableExtra("project");

		projectName = (TextView) getView().findViewById(R.id.project_name);
		type = (TextView) getView().findViewById(R.id.card_type);
		detail = (TextView) getView().findViewById(R.id.card_theme);
		nbrCards = (TextView) getView().findViewById(R.id.number);
		orderDate = (TextView) getView().findViewById(R.id.date);
		perso = (TextView) getView().findViewById(R.id.perso);
		color1 = (ImageView) getView().findViewById(R.id.selected_color1);
		color2 = (ImageView) getView().findViewById(R.id.selected_color2);
		color3 = (ImageView) getView().findViewById(R.id.selected_color3);

		projectName.setText(project.getName());
		type.setText(project.getType());
		detail.setText(project.getDetail());
		perso.setText(project.getPerso() != null ? project.getPerso() : "anonyme");
		nbrCards.setText(""+project.getQuantity());
		orderDate.setText(project.getOrderDate());

		String[] colors = project.getColors().split(",");

		Log.d("COLOR SET", project.getColors());

		switch (colors.length) {
		case 1:
			color1.setBackgroundResource(ProjectData.reverseColorMap.get(colors[0]));
			break;
		case 2:
			color1.setBackgroundResource(ProjectData.reverseColorMap.get(colors[0]));
			color2.setBackgroundResource(ProjectData.reverseColorMap.get(colors[1]));
			break;
		case 3:
			color1.setBackgroundResource(ProjectData.reverseColorMap.get(colors[0]));
			color2.setBackgroundResource(ProjectData.reverseColorMap.get(colors[1]));
			color3.setBackgroundResource(ProjectData.reverseColorMap.get(colors[2]));
			break;	
		default:
			break;
		}

	}


}
