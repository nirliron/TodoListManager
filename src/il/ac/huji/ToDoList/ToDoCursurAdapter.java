package il.ac.huji.ToDoList;

import java.util.Date;

import com.parse.ParseObject;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ToDoCursurAdapter extends CursorAdapter {
	private int currId = -1;
	private String currTitle = "";
	private Date currDate;
	@SuppressWarnings("deprecation")
	public ToDoCursurAdapter(Context context, Cursor c) {		
		super(context, c);
		currId = -1;
		currTitle="";
		currDate = new Date();
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public void bindView(View view, Context arg1, Cursor cursor) {
		// TODO Auto-generated method stub
		TextView txtTitle = (TextView) view.findViewById(R.id.txtTodoTitle);
		String title = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1)));
		TextView txtDate = (TextView) view.findViewById(R.id.txtTodoDueDate);
		Date due = new Date(cursor.getLong(cursor.getColumnIndex(cursor.getColumnName(2))));
		TextView txtId = (TextView) view.findViewById(R.id._id);
		String id = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0)));
		txtId.setText(id);						
		if(passedDueDate(due)){
        	txtTitle.setTextColor(Color.RED);
        	txtDate.setTextColor(Color.RED);
        	txtTitle.setText(title);
            txtDate.setText(getDueDateString(due));
        }else{
        	txtTitle.setTextColor(Color.BLACK);
        	txtDate.setTextColor(Color.BLACK);
        	txtTitle.setText(title);
        	txtDate.setText(getDueDateString(due));
        }        
		
	}
	public String getDueDateString(Date dueDate){
		String[] dateList = dueDate.toString().split(" ");
		String retVal = dateList[2]+"/";
		retVal += (dueDate.getMonth()+1)+"/";
		retVal += dueDate.getYear();
		return retVal;		
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
	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(arg2.getContext());
        View retView = inflater.inflate(R.layout.list_item, arg2, false);
        return retView;
	}

}
