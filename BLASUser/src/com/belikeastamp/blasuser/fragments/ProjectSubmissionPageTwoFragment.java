package com.belikeastamp.blasuser.fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.activities.ProjectSubmissionPageTwo.SubmissionDialogFragment;
import com.belikeastamp.blasuser.db.model.User;
import com.belikeastamp.blasuser.util.DatePickerDialogFragment;
import com.belikeastamp.blasuser.util.GlobalVariable;
import com.belikeastamp.blasuser.util.UserController;

public class ProjectSubmissionPageTwoFragment extends Fragment {

	final public static int REQUEST_CAMERA = 0;
	final public static int SELECT_FILE = 1;

	final static int BACKINTIME = 1;
	final static int IMPOSSIBLE = 2;
	final static int NOINFOS = 3;
	final static int FAISABLE = 4;

	private ImageView fileView;
	private Button valider, selectDate;
	private EditText infos, card_num;
	private GlobalVariable globalVariable;
	private Spinner emailSpinner;
	private LinearLayout emailLayout;

	final static long WEEK = 604800000L;
	private long today = System.currentTimeMillis();
	private long delay = today + WEEK;
	private File file = null;
	private Long id;
 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_project_new_project_form2, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		valider = (Button) getView().findViewById(R.id.btn_continue);
		infos = (EditText) getView().findViewById(R.id.infos);
		selectDate = (Button) getView().findViewById(R.id.select_date);
		fileView =(ImageView) getView().findViewById(R.id.file);
		card_num = (EditText) getView().findViewById(R.id.card_num);
		emailSpinner= (Spinner) getView().findViewById(R.id.email);
		emailLayout = (LinearLayout) getView().findViewById(R.id.layout);

		globalVariable = (GlobalVariable) getActivity().getApplicationContext();
		setDate(delay);
		globalVariable.setSubmitDate(getDate(today));

		if(!(isRegistred())) {
			Log.d("Submission", "not reg");
			emailLayout.setVisibility(View.VISIBLE);
			getUserEmail();
		}


		fileView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectImage();
			}
		});

		selectDate.setOnClickListener(new OnClickListener() {  
			@Override  
			public void onClick(View v) {  
				DialogFragment newFragment = new DatePickerDialogFragment(callback);  
				newFragment.show(getFragmentManager(), "datePicker");
			}  
		});


		valider.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(file != null)
					globalVariable.setTrackFile(file);

				if(!(infos.getText().toString().isEmpty()))
					globalVariable.setInfos(infos.getText().toString());

				if(checkEntries()) {
					globalVariable.setUserId(id);
					showDialog();
				}
				else {
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

				Log.i("PROJET","=>"+globalVariable.toString());
			}
		});


	}

	void showDialog() {
		DialogFragment newFragment = SubmissionDialogFragment
				.newInstance(R.string.alert_check, globalVariable);
		newFragment.show(getFragmentManager(), "dialog");
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


	private void selectImage() {
		/*final CharSequence[] items = { "Take Photo", "Choose from Library",
		"Cancel" };*/
		
		final CharSequence[] items = { "Choose from Library",
		"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				/*if (items[item].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					startActivityForResult(intent, REQUEST_CAMERA);
				} else */if (items[item].equals("Choose from Library")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(
							Intent.createChooser(intent, "Select File"),
							SELECT_FILE);
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d("RESULT CODE", ""+resultCode);

		if (resultCode == Activity.RESULT_OK) {
			
			if (requestCode == ProjectSubmissionPageTwoFragment.REQUEST_CAMERA) {
				File f = new File(Environment.getExternalStorageDirectory()
						.toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}
				try {
					Bitmap bm;
					BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

					bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
							btmapOptions);

					int width = 300;
					int height = 300;
					LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
					parms.gravity = Gravity.CENTER;
					fileView.setLayoutParams(parms);
					fileView.setImageBitmap(bm);


					String path = android.os.Environment
							.getExternalStorageDirectory()
							+ File.separator
							+ "Phoenix" + File.separator + "default";
					f.delete();
					OutputStream fOut = null;
					File selectedFile = new File(path, String.valueOf(System
							.currentTimeMillis()) + ".jpg");

					file = f;

					try {
						fOut = new FileOutputStream(selectedFile);
						bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);

						fOut.flush();
						fOut.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (requestCode == ProjectSubmissionPageTwoFragment.SELECT_FILE) {
				Uri selectedImageUri = data.getData();
				String tempPath = getPath(selectedImageUri, getActivity());
				Bitmap bm;
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
				int width = 300;
				int height = 300;
				LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
				parms.gravity = Gravity.CENTER;
				fileView.setLayoutParams(parms);
				fileView.setImageBitmap(bm);
			    Uri selUri = Uri.withAppendedPath(selectedImageUri, "test");
				Toast.makeText(getActivity().getApplicationContext(), 
				     selUri.getPath(), 
				     Toast.LENGTH_LONG).show();
				//file = new File(data.getData().getPath());
				file = new File(selectedImageUri.getPath());
			}
		}
	}

	public String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor =  activity.getContentResolver().query(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
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


	private boolean checkEntries() {
		boolean everythin_good = true;
		String msg = "";

		Log.i("checkEntries", "card num = "+everythin_good);

		if(Integer.valueOf(card_num.getText().toString()) == 0)
		{
			everythin_good = false;
			msg = getActivity().getApplicationContext().getResources().getString(R.string.zero_card);
			card_num.setError(msg);
		}
		else if(Integer.valueOf(card_num.getText().toString()) > 200) {
			everythin_good = false;
			String contact = getActivity().getApplicationContext().getResources().getString(R.string.err_too_big, 
					getActivity().getApplicationContext().getResources().getString(R.string.contact_email));
			Toast.makeText(getActivity().getApplicationContext(),contact, Toast.LENGTH_LONG).show();
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

		
		if(!isRegistred()) {
			registration(globalVariable.getUserEmail());
		}

		if(id.equals(Long.valueOf(-1))) everythin_good = false;
		
		return everythin_good;	
	}

	private void getUserEmail() {

		Account[] accounts = AccountManager.get(getActivity()).getAccountsByType("com.google");
		final List<String> items =  new ArrayList<String>();

		for (int i = 0; i < accounts.length; i++)
			items.add(accounts[i].name);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,items);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		emailSpinner.setAdapter(dataAdapter);
		emailSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				globalVariable.setUserEmail((String)arg0.getSelectedItem());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});
	}


	private Boolean isRegistred() {
		// TODO Auto-generated method stub
		id = getActivity().getSharedPreferences("BLAS", getActivity().MODE_PRIVATE).getLong("user_id", Long.valueOf(-1));
		Log.d("Submission", "USER ID "+id);
		return (!(id.equals(Long.valueOf(-1))));
	}

	private void registration(String email) {
		// TODO Auto-generated method stub
		Log.d("Submission","go to AddUserTask");
		User user =  new User(email);
		try {
			Log.d("Submission", "registration "+id);
			if(new AddUserTask().execute(user).get())
			{
				Toast.makeText(getActivity().getApplicationContext(), R.string.reg_succed, Toast.LENGTH_SHORT).show();
				getActivity().getSharedPreferences("BLAS", getActivity().MODE_PRIVATE).edit().putLong("user_id", id).commit();
			}
			else
			{
				Toast.makeText(getActivity().getApplicationContext(), R.string.reg_failed, Toast.LENGTH_SHORT).show();
			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class AddUserTask extends AsyncTask<User, Void, Boolean> {

		@Override
		protected Boolean doInBackground(User... params) {
			// TODO Auto-generated method stub
			Log.d("Submission","AddUserTask doInBackground");

			User u = params[0];
			u.setFirstname("");
			u.setName("");
			u.setPhone("");
			u.setAddress("");
			u.setIsHost(false);
			u.setIsPartener(false);

			final UserController c = new UserController();
			try {

				// check user id d'abord...
				id = c.getUserId(u.getEmail());
				if(id.equals(Long.valueOf(-1))) {
					Log.d("Submission", "AddUserTask unknown user");
					c.create(u);
					id = c.getUserId(u.getEmail());
					Log.d("Submission", "AddUserTask user id = "+id);
				}
				else
				{
					Log.d("Submission", "AddUserTask already known user : "+id);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return (!(id.equals(Long.valueOf(-1))));
		}	
	}


}
