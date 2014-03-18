package il.ac.huji.ToDoList;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ToDoListAdapter extends BaseAdapter {
	private ArrayList<ToDoListItem> toDoListItems;
	private Context context;
	
	public ToDoListAdapter(ArrayList<ToDoListItem> toDoListItems,
			Context context) {
		super();
		this.toDoListItems = toDoListItems;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return toDoListItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return toDoListItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ToDoListItem entry = toDoListItems.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }        
        TextView title = (TextView) convertView.findViewById(R.id.txtTodoTitle);        
        TextView dueDate = (TextView) convertView.findViewById(R.id.txtTodoDueDate);
        if(passedDueDate(entry.getDueDate())){
        	title.setTextColor(Color.RED);
        	dueDate.setTextColor(Color.RED);
        	title.setText(entry.getTitle());
            dueDate.setText(entry.getDueDateString());
        }else{
        	title.setTextColor(Color.BLACK);
        	dueDate.setTextColor(Color.BLACK);
        	title.setText(entry.getTitle());
            dueDate.setText(entry.getDueDateString());
        }        
        
		return convertView;
	}
	public void addItem(ToDoListItem item){
		toDoListItems.add(item);
	}
	@SuppressWarnings("unused")
	private boolean passedDueDate(Date due){
		Date today = new Date();	
		if(due.getYear()>today.getYear()+1900){
			return false;
		}else if(due.getYear()<today.getYear()+1900){
			return true;
		}
		if(due.getMonth()>today.getMonth()){
			return false;
		}else if(due.getMonth()<today.getMonth()){
			return true;
		}
		String[] dateList = due.toString().split(" ");
		int dueDay = Integer.parseInt(dateList[2]);
		String[] todayDateList = today.toString().split(" ");
		int todayDay = Integer.parseInt(todayDateList[2]);
		return dueDay<todayDay;					
	}

}
