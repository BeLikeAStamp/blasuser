package com.belikeastamp.blasuser.activities;


import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.SumPathEffect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.adapter.NavDrawerListAdapter;
import com.belikeastamp.blasuser.db.dao.Datasource;
import com.belikeastamp.blasuser.fragments.HomeFragment;
import com.belikeastamp.blasuser.fragments.JoinMeFragment;
import com.belikeastamp.blasuser.fragments.ProjectSubmissionPageOneFragment;
import com.belikeastamp.blasuser.fragments.SavedProjectsFragment;
import com.belikeastamp.blasuser.fragments.SubmitProjectsFragment;
import com.belikeastamp.blasuser.fragments.TutorialFragment;
import com.belikeastamp.blasuser.fragments.WorkshopFragment;
import com.belikeastamp.blasuser.model.NavDrawerItem;
import com.belikeastamp.blasuser.util.GlobalVariable;
import com.belikeastamp.blasuser.util.asynctask.AsyncTaskManager;
import com.belikeastamp.blasuser.util.asynctask.MyAbstractAsyncTask;
import com.belikeastamp.blasuser.util.asynctask.OnTaskCompleteListener;
import com.belikeastamp.blasuser.util.asynctask.Request4RemoteDataTask;

public class MainActivity extends Activity implements OnTaskCompleteListener {
	public static final int RELOAD = 1;
	public static final int SEND_MAIL = 2;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private GlobalVariable globalVariable;
	private AsyncTaskManager mAsyncTaskManager;
	private Datasource datasource;
	
	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		globalVariable = (GlobalVariable) getApplicationContext();
		
		mAsyncTaskManager = new AsyncTaskManager(this, this);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();


		// Download data
		loadLocalData();
		loadRemoteData();

		int submit_prj = (globalVariable.getData().getSubmitProjects() != null) ? globalVariable.getData().getSubmitProjects().size() : 0;
		int saved_prj = (globalVariable.getData().getSavedProjects() != null) ? globalVariable.getData().getSavedProjects().size() : 0;
		int tutos_prj = (globalVariable.getData().getTutorials() != null) ? globalVariable.getData().getTutorials().size() : 0;
		int works_prj = (globalVariable.getData().getWorkshops() != null) ? globalVariable.getData().getWorkshops().size() : 0;

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// NEW PROJ
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// SUBMIT PROJ
		if (globalVariable.getData().getSubmitProjects().size() == 0) 
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		else
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1), true, ""+submit_prj));
		// SAVED PROJ
		if (globalVariable.getData().getSavedProjects().size() == 0) 
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		else
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, ""+saved_prj));
		// TUTOS
		if (globalVariable.getData().getTutorials().size() == 0) 
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		else
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1), true, ""+tutos_prj));
		// WORKSHOP
		if (globalVariable.getData().getWorkshops().size() == 0) 
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		else
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, ""+works_prj));
		// JOIN ME
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
		// BLOG
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));		
		// ACTUALISER
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));		

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.banniere));


		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_navigation_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
				) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);		
		
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			Log.d("WHERE AM I", "savedInstanceState == null");
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new ProjectSubmissionPageOneFragment();
			break;
		case 2:
			fragment = new SubmitProjectsFragment();
			break;
		case 3:
			fragment = new SavedProjectsFragment();
			break;
		case 4:
			fragment = new TutorialFragment();
			break;
		case 5:
			fragment = new WorkshopFragment();
			break;
		case 6:
			fragment = new JoinMeFragment();
			break;
		case 7:
			String url = getResources().getString(R.string.blog);
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(i);
			break;	
		case 8:
			Intent i2 = new Intent(MainActivity.this, PreMainActivity.class);
			startActivity(i2);
			break;


		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onTaskComplete(MyAbstractAsyncTask task) {
		if (task.isCancelled()) {
			// Report about cancel
			Toast.makeText(getApplicationContext(),  getResources().getString(R.string.task_cancelled), Toast.LENGTH_LONG)
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
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.task_completed, (result != null) ? result.toString() : "null"),
					Toast.LENGTH_LONG).show();
		}
	}

	private boolean loadLocalData() {
		datasource = new Datasource(getApplicationContext());
		datasource.open();
		globalVariable.getData().setSavedProjects(datasource.getAllWaitingProjects());
		datasource.close();
		return true;
	}

	private boolean loadRemoteData() {
		// Projets soumis

		Request4RemoteDataTask request = new Request4RemoteDataTask(getResources());
		request.setActivity(MainActivity.this);
		mAsyncTaskManager.setupTask(request);
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data); 
        System.out.println("REQUEST CODE:" + requestCode);
        switch (requestCode) {
        case RELOAD : 
            if (resultCode == RESULT_OK) {
            	Log.d("WHERE AM I", "RELOAD RESULT_OK");
            	int toOpen = getIntent().getExtras().getInt("reload",0);
				displayView(toOpen);
            } 
            break; 
        default: 
            break; 
        } 
    } 
	
	
	
}
