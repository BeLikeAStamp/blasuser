package com.belikeastamp.blasuser.util;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.belikeastamp.blasuser.R;

public class ColorPickerAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ColorPicker> colorAdapterArray;
	
	public ColorPickerAdapter(Context context, ArrayList<ColorPicker> colorPickerArray) 
	{
		super();
		this.context = context;
		this.colorAdapterArray = colorPickerArray;
	}

	@Override
	public int getCount() {
		return colorAdapterArray.size();
	}

	@Override
	public Object getItem(int arg0) {
		return colorAdapterArray.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;
		ImageView imageView = null;
		ColorPicker colorPicker = null;
		
		if (convertView == null) {

			gridView = new View(context);

			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.color_square, null);

			// set image based on selected text
			imageView = (ImageView) gridView
					.findViewById(R.id.grid_item_image);

			colorPicker = colorAdapterArray.get(position);
			imageView.setBackgroundResource(colorPicker.getColorCode());

		} else {
			gridView = (View) convertView;
		}

		return gridView;
	}

}

