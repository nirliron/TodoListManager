package il.ac.huji.ToDoList;

import java.util.ArrayList;
import java.util.Date;


import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.view.ContextMenu;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;

public class TodoListManagerActivity extends Activity {

	ArrayList<ToDoListItem> listItems = new ArrayList<ToDoListItem>();
	ToDoListAdapter adapter;
	ListView toDoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toDoList = (ListView) findViewById(R.id.lstToDoItems);
		registerForContextMenu(toDoList);
		adapter = new ToDoListAdapter(listItems, this);
		toDoList.setAdapter(adapter);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==0){
			return;
		}
		switch (requestCode) {
		case 555:
			String title = data.getStringExtra("title");
			Date due = (Date)data.getExtras().get("dueDate");
			addItems(title, due);
			break;

		default:
			break;
		}
		
	}
	/*
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		try{
			String title = intent.getStringExtra("title");
			System.out.println(title);
			addItems(title);
		}catch (Exception e) {
			// TODO: handle exception
			super.onResume();
		}
	}
*/
	public void addItems(String title,Date due) {
		//final EditText edtTask = (EditText) findViewById(R.id.edtNewItem);
		//String task = edtTask.getText().toString();
		if (!title.equals("")) {
			//listItems.add(task);
			ToDoListItem item = new ToDoListItem(title, due);
			listItems.add(item);
		}
		//edtTask.setText("");
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menuItemAdd:
			//addItems();
			Intent intent=new Intent(this,AddNewTodoItem.class);
			startActivityForResult(intent, 555);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater m = getMenuInflater();
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		ToDoListItem current= listItems.get(info.position);
		String title = current.getTitle();
		menu.setHeaderTitle(title);
		m.inflate(R.menu.delete_menue, menu);
		MenuItem callItem = menu.findItem(R.id.menuItemCall);
		if (!title.startsWith("Call ")) {
			callItem.setVisible(false);
			this.invalidateOptionsMenu();
		} else {
			callItem.setTitle(title);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = (int) info.id;
		switch (item.getItemId()) {
		case R.id.menuItemDelete:
			listItems.remove(position);
			this.adapter.notifyDataSetChanged();
			return true;
		case R.id.menuItemCall:					
			String tel = listItems.get(position).getTitle().replace("Call ","");
			Intent intent = new Intent(Intent.ACTION_DIAL);
			intent.setData(Uri.parse("tel:"+tel));
			startActivity(intent); 

		}
		return super.onContextItemSelected(item);
	}

}
