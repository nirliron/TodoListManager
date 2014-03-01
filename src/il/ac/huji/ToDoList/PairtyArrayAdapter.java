package il.ac.huji.ToDoList;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PairtyArrayAdapter extends ArrayAdapter<String> {

	public PairtyArrayAdapter(Context context, int resource,
			List<String> objects) {
		super(context, resource, objects);	
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {  
	TextView view = (TextView) super.getView(position, convertView, parent);  
	if (position % 2 == 1) {
	    view.setTextColor(Color.BLUE);  
	} else {
	    view.setTextColor(Color.RED);  
	}

	return view;  
	}
}
