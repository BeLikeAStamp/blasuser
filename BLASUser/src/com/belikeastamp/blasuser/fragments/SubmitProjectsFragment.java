package com.belikeastamp.blasuser.fragments;


import java.util.ArrayList;
import java.util.List;
import com.belikeastamp.blasuser.R;
import com.belikeastamp.blasuser.adapter.SubmitProjectAdapter;
import com.belikeastamp.blasuser.db.model.Project;

import android.app.ListFragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.LongSparseArray;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;


public class SubmitProjectsFragment extends ListFragment {
	
	public SubmitProjectsFragment(){}
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    List<Project> projects = new ArrayList<Project>();
	    Project p1 = new Project("name1", "1", 2 , "theme1", "type1", "style1","1", 12, null); p1.setRemoteId(Long.valueOf(1));
	    Project p2 = new Project("name2", "1", 0 , "theme2", "type2", "style2", "1", 1, null); p2.setRemoteId(Long.valueOf(2));
	    Project p3 = new Project("name3", "1", 4 , "theme3", "type3", "style3", "1", 122, null); p3.setRemoteId(Long.valueOf(3));
	    
	    projects.add(p3); projects.add(p1); projects.add(p2);
	   
	    LongSparseArray<Uri> map = new LongSparseArray<Uri>();
	    Uri uri1 = Uri.parse("android.resource://com.belikeastamp.blasuser/drawable/ic_no_image_available");
	    Uri uri2 = Uri.parse("android.resource://com.belikeastamp.blasuser/drawable/ic_home");
	    Uri uri3 = Uri.parse("android.resource://com.belikeastamp.blasuser/drawable/ic_whats_hot");
	    
	    map.put(Long.valueOf(1), uri1);
	    map.put(Long.valueOf(2), uri2);
	    map.put(Long.valueOf(3), uri3);
	    
	  /*  Using Resource Name

	    Syntax : android.resource://[package]/[res type]/[res name]

	    Example :  Uri.parse("android.resource://com.my.package/drawable/icon");

	    Using Resource Id

	    Syntax : android.resource://[package]/[resource_id]

	    Example : Uri.parse("android.resource://com.my.package/" + R.drawable.icon);*/
	    
	    
	    String[] status = getResources().getStringArray(R.array.status_arrays);
	   
	    BaseAdapter adapter = new SubmitProjectAdapter(getActivity().getApplicationContext(), projects, map, status);
	    
	   /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	        android.R.layout.simple_list_item_1, values);*/
	    setListAdapter(adapter);
	  }

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // do something with the data
	  }
	
	/*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_project, container, false);
        
        
        return rootView;
    }*/
}
