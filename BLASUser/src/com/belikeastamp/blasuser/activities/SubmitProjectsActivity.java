package com.belikeastamp.blasuser.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.fragments.SubmitProjectsFragment;
import com.belikeastamp.blasuser.fragments.SubmitProjectsPageTwoFragment;

public class SubmitProjectsActivity extends Activity {

	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_projects);
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		
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

		mDrawerToggle.setDrawerIndicatorEnabled(false);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			
			Fragment fragment = new SubmitProjectsPageTwoFragment();
	
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.frame_container, fragment).commit();

		}
		

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {   
		// Get item selected and deal with it
		switch (item.getItemId()) {
		case android.R.id.home:
			//called when the up affordance/carat in actionbar is pressed
			this.onBackPressed();
			return true;
		}
		return false;
	}
	
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("XXX", "ICI");
		switch (requestCode) {

		case MainActivity.RELOAD: 
			Log.d("RELOAD", "ben alors ?");
			Intent goodIntent = new Intent(SubmitProjectsActivity.this, MainActivity.class);
			startActivityForResult(goodIntent, MainActivity.RELOAD);
			break; 
		case MainActivity.SEND_MAIL: 
			Log.d("SEND_EMAIL", "ben alors ?");
			Intent backhome = new Intent(SubmitProjectsActivity.this, MainActivity.class);
			startActivity(backhome);
			break; 	
		default: 
			break; 
		} 
	}
}
