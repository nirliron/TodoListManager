package il.ac.huji.ToDoList;

import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddNewTodoItem extends Activity {
	public static String title = "";
	private AddNewTodoItem that;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		that = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_todo_item);
		Button btnCancel = (Button)findViewById(R.id.btnCancel);		
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {	
				finish();
			}
		});
		
		Button btnOK = (Button)findViewById(R.id.btnOK);		
		btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {	
				EditText edtToDo = (EditText)findViewById(R.id.edtNewItem);
				DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
				Intent result=new Intent(that,TodoListManagerActivity.class);		
				result.putExtra("title", edtToDo.getText().toString());
				@SuppressWarnings("deprecation")
				Date dueDate = new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());;
				result.putExtra("dueDate", dueDate);
				setResult(RESULT_OK, result);				
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_todo_item, menu);
		return true;
	}

}
