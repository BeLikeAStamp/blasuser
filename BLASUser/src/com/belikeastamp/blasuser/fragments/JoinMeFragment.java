package com.belikeastamp.blasuser.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;

public class JoinMeFragment extends Fragment {
	private static final int ENTRY_OK = 0;
	private static final int EMPTY = 1;

	private EditText name;
	private EditText email;
	private EditText message;
	private Button envoyer;

	public JoinMeFragment(){}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_join_me, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		name = (EditText) this.getView().findViewById(R.id.name);
		email = (EditText) this.getView().findViewById(R.id.email);
		message = (EditText) this.getView().findViewById(R.id.message);
		envoyer = (Button) this.getView().findViewById(R.id.envoyer);

		envoyer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(checkEntries()) {
					sendContact(v);
				}
				else
				{
					Toast.makeText(getActivity().getApplicationContext(), R.string.infos_manquantes, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}


	private boolean checkEntries() {
		// TODO Auto-generated method stub
		boolean everythin_good = true;
		String msg = "";
		int ret = 0;

		ret = checkEntry(name.getText().toString());
		if(ret != ENTRY_OK) everythin_good = false;

		if (ret == EMPTY) {
			msg = getActivity().getApplicationContext().getResources().getString(R.string.empty_field);
			name.setError(msg);
		}


		ret = checkEntry(email.getText().toString());
		if(ret != ENTRY_OK) everythin_good = false;
		if (ret == EMPTY) {
			msg = getActivity().getApplicationContext().getResources().getString(R.string.empty_field);
			email.setError(msg);
		}

		ret = checkEntry(message.getText().toString());
		if(ret != ENTRY_OK) everythin_good = false;
		if (ret == EMPTY) {
			msg = getActivity().getApplicationContext().getResources().getString(R.string.empty_field);
			message.setError(msg);
		}



		return everythin_good;
	}

	private int checkEntry(String s) {
		int ret = ENTRY_OK;

		if (s.length() == 0) ret = EMPTY;
		return ret;		
	}

	public void sendContact(View button) {


		// Take the fields and format the message contents
		String subject = "Join your team";

		String myMessage = formatMessage(name.getText().toString(), email.getText().toString(), message.getText().toString());

		// Create the message
		sendMessage(subject, myMessage);
	}

	protected String formatMessage(String n, String e, String m) {

		String strFeedbackFormatMsg = getResources().getString(
				R.string.messagebody_format);

		String strFeedbackMsg = String.format(strFeedbackFormatMsg, m, n, e);

		return strFeedbackMsg;

	}

	public void sendMessage(String subject, String message) {

		Intent messageIntent = new Intent(android.content.Intent.ACTION_SEND);

		String aEmailList[] = { getActivity().getResources().getString(R.string.contact_email) };
		messageIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);

		messageIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);

		messageIntent.setType("plain/text");
		messageIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);

		startActivity(messageIntent);
	}
}
