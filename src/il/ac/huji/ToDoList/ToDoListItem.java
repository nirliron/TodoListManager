package il.ac.huji.ToDoList;

import java.util.Calendar;
import java.util.Date;

public class ToDoListItem {
	private String title;
	private Date dueDate;
	
	public ToDoListItem(String title, Date dueDate) {
		super();
		this.title = title;
		this.dueDate = dueDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDueDate() {
		return dueDate;
	}
	@SuppressWarnings("deprecation")
	public String getDueDateString(){
		String[] dateList = dueDate.toString().split(" ");
		String retVal = dateList[2]+"/";
		retVal += (dueDate.getMonth()+1)+"/";
		retVal += dueDate.getYear();
		return retVal;		
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
