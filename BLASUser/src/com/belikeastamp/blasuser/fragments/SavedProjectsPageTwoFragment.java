package com.belikeastamp.blasuser.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.util.GlobalVariable;

public class SavedProjectsPageTwoFragment extends Fragment {

	private TextView projectName;
	private TextView type;
	private TextView detail;
	private TextView nbrCards;
	private TextView orderDate;
	private ImageView color1;
	private ImageView color2;
	private ImageView color3;
	private Project project;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_saved_project_details, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		project = (Project) getActivity().getIntent().getSerializableExtra("project");

		projectName = (TextView) getView().findViewById(R.id.project_name);
		type = (TextView) getView().findViewById(R.id.card_type);
		detail = (TextView) getView().findViewById(R.id.details);
		nbrCards = (TextView) getView().findViewById(R.id.number);
		orderDate = (TextView) getView().findViewById(R.id.date);
		color1 = (ImageView) getView().findViewById(R.id.selected_color1);
		color2 = (ImageView) getView().findViewById(R.id.selected_color2);
		color3 = (ImageView) getView().findViewById(R.id.selected_color3);

		projectName.setText(project.getName());
		type.setText(project.getType());
		detail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity().getApplicationContext(), project.getPrintableDetails(), Toast.LENGTH_LONG).show();
			}
		});
		
		nbrCards.setText(getActivity().getResources().getString(R.string.how_many_cards)+" : "+project.getQuantity());
		orderDate.setText(getActivity().getResources().getString(R.string.save_date)+" : "+project.getOrderDate());

		String[] colors = project.getColors().split(",");

		Log.d("COLOR SET", project.getColors());

		switch (colors.length) {
		case 1:
			color1.setBackgroundResource(GlobalVariable.reverseColorMap.get(colors[0]));
			break;
		case 2:
			color1.setBackgroundResource(GlobalVariable.reverseColorMap.get(colors[0]));
			color2.setBackgroundResource(GlobalVariable.reverseColorMap.get(colors[1]));
			break;
		case 3:
			color1.setBackgroundResource(GlobalVariable.reverseColorMap.get(colors[0]));
			color2.setBackgroundResource(GlobalVariable.reverseColorMap.get(colors[1]));
			color3.setBackgroundResource(GlobalVariable.reverseColorMap.get(colors[2]));
			break;	
		default:
			break;
		}

	}


}
