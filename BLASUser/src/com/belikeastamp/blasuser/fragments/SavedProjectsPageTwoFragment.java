package com.belikeastamp.blasuser.fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.activities.MainActivity;
import com.belikeastamp.blasuser.db.dao.Datasource;
import com.belikeastamp.blasuser.db.model.Project;
import com.belikeastamp.blasuser.util.ColorPicker;
import com.belikeastamp.blasuser.util.ColorPickerAdapter;
import com.belikeastamp.blasuser.util.DatePickerDialogFragment;
import com.belikeastamp.blasuser.util.GlobalVariable;
import com.belikeastamp.blasuser.util.asynctask.AddProjectTask;
import com.belikeastamp.blasuser.util.asynctask.AsyncTaskManager;
import com.belikeastamp.blasuser.util.asynctask.GetProjectTask;
import com.belikeastamp.blasuser.util.asynctask.MyAbstractAsyncTask;
import com.belikeastamp.blasuser.util.asynctask.OnTaskCompleteListener;

public class SavedProjectsPageTwoFragment extends Fragment implements OnTaskCompleteListener {

	final private static int REQUEST_CAMERA = 1;
	final static int BACKINTIME = 1;
	final static int IMPOSSIBLE = 2;
	final static int NOINFOS = 3;
	final static int FAISABLE = 4;
	private static final int SEND_EMAIL = 0;
	final private static int SELECT_FILE = 3;

	private TextView projectName;
	private TextView type;
	private TextView detail;
	private TextView nbrCards;
	private TextView orderDate;
	private ImageView color1;
	private ImageView color2;
	private ImageView color3;
	private ImageView fileView;

	private AsyncTaskManager mAsyncTaskManagerAddProj;
	private AsyncTaskManager mAsyncTaskManagerGetProjId;
	private ArrayList<ColorPicker> colorPickerArray1;

	private Project project;

	private EditText infos;

	private Button submit, modifyColors, modifyNbrCards, modifyOrderDate;
	private GlobalVariable globalVariable;
	
	final static long WEEK = 604800000L;
	private long today = System.currentTimeMillis();
	private long delay = today + WEEK;

	private Button newDate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_saved_project_details, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		globalVariable = (GlobalVariable) getActivity().getApplicationContext();
		project = (Project) getActivity().getIntent().getSerializableExtra("project");
		
		Log.i("PROJET RECUPERE", project.toString());
		mAsyncTaskManagerAddProj = new AsyncTaskManager(getActivity(), this);
		mAsyncTaskManagerGetProjId = new AsyncTaskManager(getActivity(), this);

		projectName = (TextView) getView().findViewById(R.id.project_name);
		type = (TextView) getView().findViewById(R.id.card_type);
		detail = (TextView) getView().findViewById(R.id.details);
		nbrCards = (TextView) getView().findViewById(R.id.number);
		orderDate = (TextView) getView().findViewById(R.id.date);
		color1 = (ImageView) getView().findViewById(R.id.selected_color1);
		color2 = (ImageView) getView().findViewById(R.id.selected_color2);
		color3 = (ImageView) getView().findViewById(R.id.selected_color3);
		fileView =(ImageView) getView().findViewById(R.id.file);
		submit = (Button) getView().findViewById(R.id.submit);

		modifyColors = (Button) getView().findViewById(R.id.modifier_color);
		modifyNbrCards = (Button) getView().findViewById(R.id.modifier_number);
		modifyOrderDate = (Button) getView().findViewById(R.id.modifier_date);

		infos = (EditText) getView().findViewById(R.id.infos);

		projectName.setText(project.getName());
		type.setText(project.getType());
		detail.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(getActivity().getApplicationContext(), project.getPrintableDetails(), Toast.LENGTH_LONG).show();
			}
		});

		nbrCards.setText(getActivity().getResources().getString(R.string.how_many_cards)+" : "+project.getQuantity());
		orderDate.setText(getActivity().getResources().getString(R.string.for_when)+" : "+project.getOrderDate());

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



		fileView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				selectImage();
			}
		});

		modifyColors.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				ArrayList<ImageView> selectedColorsList2 = new ArrayList<ImageView>();
				selectedColorsList2.add(color1);
				selectedColorsList2.add(color2);
				selectedColorsList2.add(color3);
				lauchDialogBoxColor(selectedColorsList2);

			}
		});

		modifyNbrCards.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				launchDialogCardNumber(nbrCards);
			}
		});

		modifyOrderDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				launchDialogBoxDate(orderDate);
			}
		});

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Project newProject = buildNewProject();
				remoteSubmission(newProject);
				deleteLocalCopy(newProject.getName());
			}

		});

	}

	private Project buildNewProject() {

		StringBuffer colorsBuffer = new StringBuffer();
		Project newProject = new Project(project);

		int color = globalVariable.getColor1();
		if (color != -1) {
			colorsBuffer.append(GlobalVariable.colorName.get(color));
		}

		color = globalVariable.getColor2();
		if (color != -1) {
			colorsBuffer.append(","+GlobalVariable.colorName.get(color));
		}

		color = globalVariable.getColor3();
		if (color != -1) {
			colorsBuffer.append(","+GlobalVariable.colorName.get(color));
		}

		newProject.setColors(colorsBuffer.toString());

		if(globalVariable.getTrackFile() != null) newProject.setTrackFile(globalVariable.getTrackFile());


		if(!(infos.getText().toString().isEmpty()))
			globalVariable.setInfos(infos.getText().toString());



		if (globalVariable.getOrderDate() != null && globalVariable.getOrderDate().length() > 0)
			newProject.setOrderDate(globalVariable.getOrderDate());
		if(globalVariable.getNumberOfCards() != null && globalVariable.getNumberOfCards().length() > 0) 
			newProject.setQuantity(Integer.valueOf(globalVariable.getNumberOfCards()));

		return newProject;
	}

	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d("RESULT CODE", ""+resultCode);

		if (resultCode == Activity.RESULT_OK) {
			
			if (requestCode == REQUEST_CAMERA) {
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
			} else if (requestCode == SELECT_FILE) {
				Uri selectedImageUri = data.getData();
				globalVariable.setTrackFile(selectedImageUri);
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
			}
		}
	}


	public void remoteSubmission(Project newProject) {

		newProject.setStatus(0);

		// remote
		AddProjectTask addproj = new AddProjectTask(getResources());
		addproj.setActivity(getActivity());
		addproj.setProject(newProject);
		mAsyncTaskManagerAddProj.setupTask(addproj);

		GetProjectTask getProjId = new GetProjectTask(getResources());
		getProjId.setActivity(getActivity());
		getProjId.setProject(newProject);
		mAsyncTaskManagerGetProjId.setupTask(getProjId);

	}

	@Override
	public void onTaskComplete(MyAbstractAsyncTask task) {
		// TODO Auto-generated method stub
		if (task.isCancelled()) {
			// Report about cancel
			Toast.makeText(getActivity().getApplicationContext(),  getResources().getString(R.string.task_cancelled), Toast.LENGTH_LONG)
			.show();
		} else {
			// Get result
			Boolean result = null;
			try {
				result = task.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Report about result
			Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.task_completed, (result != null) ? result.toString() : "null"),
					Toast.LENGTH_LONG).show();
		}
	}


	public void deleteLocalCopy(String name) {

		Datasource datasource = new Datasource(getActivity().getApplicationContext());
		datasource.open();
		datasource.deleteProject(name);
		datasource.close();
	}

	private void launchDialogCardNumber(final TextView tv) {

		final EditText newCardNumber;
		final LayoutInflater li = LayoutInflater.from(getActivity());
		View layout = li.inflate(R.layout.prompt_cards, null);
		newCardNumber = (EditText) layout.findViewById(R.id.new_card_num);
		final AlertDialog d = new AlertDialog.Builder(getActivity())
		.setPositiveButton("OK", null)
		.setView(layout)
		.setCancelable(false)
		.setPositiveButton("OK", null)
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		}).create();

		// This is handy for re-adjusting the size of the dialog when a keyboard is shown
		d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

		// MUST call show before using AlertDialog.getButton(), otherwise you'll get null returned
		d.show();

		// Override the button's on-click so it doesn't close by default
		d.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// This prevents the dialog from closing
				if(Integer.valueOf(newCardNumber.getText().toString()) == 0) {
					Log.d("--------++++-------", "ZERO");
					Toast.makeText(getActivity().getApplicationContext(), R.string.zero_card, Toast.LENGTH_LONG).show();
					return;
				} else if(Integer.valueOf(newCardNumber.getText().toString()) > 200) {
					String contact = getActivity().getApplicationContext().getResources().getString(R.string.err_too_big, 
							getActivity().getApplicationContext().getResources().getString(R.string.contact_email));
					Toast.makeText(getActivity().getApplicationContext(),contact, Toast.LENGTH_LONG).show();
					return;
				}
				else
				{
					globalVariable.setNumberOfCards(newCardNumber.getText().toString());
					tv.setText(getActivity().getResources().getString(R.string.how_many_cards)+" : "+Integer.valueOf(newCardNumber.getText().toString()));
				}

				// On success, the dialog must be closed manually
				d.dismiss();
			}
		});

		// show it
		d.show();
	}

	private void launchDialogBoxDate(final TextView tv) {

		final LayoutInflater li = LayoutInflater.from(getActivity());
		View layout = li.inflate(R.layout.prompt_date, null);

		newDate = (Button) layout.findViewById(R.id.new_select_date);
		newDate.setText(getDate(delay));
		newDate.setOnClickListener(new OnClickListener() {  
			@Override  
			public void onClick(View v) {  
				DialogFragment newFragment = new DatePickerDialogFragment(callback);  
				newFragment.show(getFragmentManager(), "datePicker");
			}  
		});


		final AlertDialog d = new AlertDialog.Builder(getActivity())
		.setPositiveButton("OK", null)
		.setView(layout)
		.setCancelable(false)
		.setPositiveButton("OK", null)
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		}).create();

		// This is handy for re-adjusting the size of the dialog when a keyboard is shown
		d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

		// MUST call show before using AlertDialog.getButton(), otherwise you'll get null returned
		d.show();

		// Override the button's on-click so it doesn't close by default
		d.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (isFaisable()) {

				case FAISABLE:
					globalVariable.setOrderDate(newDate.getText().toString());
					break;
				case IMPOSSIBLE:
					Toast.makeText(getActivity().getApplicationContext(), 
							getActivity().getApplicationContext().getResources().getString(R.string.mission_impossible), Toast.LENGTH_LONG).show() ;
					return;
				case NOINFOS:
					Toast.makeText(getActivity().getApplicationContext(),
							getActivity().getApplicationContext().getResources().getString(R.string.infos_manquantes), Toast.LENGTH_LONG).show();
					return;
				case BACKINTIME:
					Toast.makeText(getActivity().getApplicationContext(),
							getActivity().getApplicationContext().getResources().getString(R.string.back_in_time), Toast.LENGTH_LONG).show();
					return;
				default:
					break;
				}
				// This prevents the dialog from closing

				tv.setText(getActivity().getResources().getString(R.string.for_when)+" : "+newDate.getText());

				// On success, the dialog must be closed manually
				d.dismiss();
			}
		});

		// show it
		d.show();

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

	void setDate(long millisecond){  
		int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR  
				| DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_MONTH  
				| DateUtils.FORMAT_ABBREV_WEEKDAY;  
		String dateString = DateUtils.formatDateTime(getActivity().getApplicationContext(),millisecond, flags);
		newDate.setText(dateString);  
	}

	String getDate(long millisecond){ 
		int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR  
				| DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_MONTH  
				| DateUtils.FORMAT_ABBREV_WEEKDAY;  
		return DateUtils.formatDateTime(getActivity().getApplicationContext(),millisecond, flags); 
	}

	private void lauchDialogBoxColor(final ArrayList<ImageView> initialImageView) {

		GridView mgridView1;
		final ImageView mcolor1;
		final ImageView mcolor2;
		final ImageView mcolor3;
		final LayoutInflater li = LayoutInflater.from(getActivity());
		View layout = li.inflate(R.layout.prompt_colors, null);
		final boolean[] selectedColors = new boolean[3];

		final ArrayList<ImageView> selectedColorsList = new ArrayList<ImageView>();

		for (int i = 0 ; i < 3 ; i++)
			initialImageView.get(i).setBackgroundColor(getResources().getColor(R.color.bgcol));

		mgridView1 = (GridView) layout.findViewById(R.id.color_grid1);
		LinearLayout l = (LinearLayout) layout.findViewById(R.id.color_layout);
		mcolor1 = (ImageView) l.findViewById(R.id.mselected_color1);
		mcolor2 = (ImageView) l.findViewById(R.id.mselected_color2);
		mcolor3 = (ImageView) l.findViewById(R.id.mselected_color3);

		final AlertDialog d = new AlertDialog.Builder(getActivity())
		.setPositiveButton("OK", null)
		.setView(layout)
		.setCancelable(false)
		.setPositiveButton("OK", null)
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		}).create();

		// This is handy for re-adjusting the size of the dialog when a keyboard is shown
		d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

		// MUST call show before using AlertDialog.getButton(), otherwise you'll get null returned
		d.show();

		// Override the button's on-click so it doesn't close by default
		d.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// This prevents the dialog from closing
				if((!selectedColors[2]) && (!selectedColors[1]) && (!selectedColors[0])) {
					Toast.makeText(getActivity().getApplicationContext(), R.string.nocolor_warning, Toast.LENGTH_SHORT).show();
					return;
				}

				// On success, the dialog must be closed manually
				d.dismiss();
			}
		});

		// show it
		d.show();


		Arrays.fill(selectedColors, false);
		selectedColorsList.add(mcolor1);
		selectedColorsList.add(mcolor2);
		selectedColorsList.add(mcolor3);

		colorPickerArray1 = getColorCat1();

		ColorPickerAdapter colorPickerAdapter1 = new ColorPickerAdapter(getActivity().getApplicationContext(), colorPickerArray1);
		mgridView1.setAdapter(colorPickerAdapter1);		
		mgridView1.setOnItemClickListener(new OnItemClickListener() {

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
							initialImageView.get(i).setBackgroundResource(pickerColor);
							if(i==0) globalVariable.setColor1(picker.getColorCode()); 
							if(i==1) globalVariable.setColor2(picker.getColorCode());
							if(i==2) globalVariable.setColor3(picker.getColorCode());
							selectedColors[i] = true;
							break;
						}
					}
				}
			}

		});


		class RazOnClickListener implements View.OnClickListener {

			@Override
			public void onClick(View v) {

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

		mcolor1.setOnClickListener(new RazOnClickListener());
		mcolor2.setOnClickListener(new RazOnClickListener());
		mcolor3.setOnClickListener(new RazOnClickListener());
	}

	private void selectImage() {

		final CharSequence[] items = { "Choose from Library",
		"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Choose from Library")) {
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


	public String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor =  activity.getContentResolver().query(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
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

}
