package il.ac.huji.ToDoList;

import java.util.ArrayList;
import java.util.Date;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;
import android.os.AsyncTask;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.view.ContextMenu;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;

public class TodoListManagerActivity extends Activity {
	private ToDoCursurAdapter customAdapter;
    private ToDoDBHelper databaseHelper;    
	private ArrayList<ToDoListItem> listItems = new ArrayList<ToDoListItem>();
	private ToDoListAdapter adapter;
	private ListView toDoList;
	private ToDoListItem current;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		databaseHelper = new ToDoDBHelper(this);
		toDoList = (ListView) findViewById(R.id.lstToDoItems);		
		toDoList.setOnItemLongClickListener(new OnItemLongClickListener() {			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub							
				String title = ((TextView) view.findViewById(R.id.txtTodoTitle)).getText().toString();
				String dueString = ((TextView) view.findViewById(R.id.txtTodoDueDate)).getText().toString();
				Date due = extractDate(dueString);
				String _id = ((TextView) view.findViewById(R.id._id)).getText().toString();
				current = new ToDoListItem(title, due,_id);
				return false;
			}
		});
		LoadDB loader = new LoadDB();
		loader.execute();
		registerForContextMenu(toDoList);
//		adapter = new ToDoListAdapter(listItems, this);
//		toDoList.setAdapter(adapter);
		
	}
	private Date extractDate(String dateStr){
		String[] dateArr = dateStr.split("/");
		Date date = new Date();
		date.setDate(Integer.parseInt(dateArr[0]));
		date.setMonth(Integer.parseInt(dateArr[1]));
		date.setYear(Integer.parseInt(dateArr[2]));
		return date;
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
			//listItems.add(new ToDoListItem(title, due));
			ContentValues vals = new ContentValues();
			vals.put("title", title);
			vals.put("due", due.toString());
			UpdateDB updater = new UpdateDB();
			updater.execute(new ContentValues[]{vals});
			//databaseHelper.insertData(title,due);			 
            //customAdapter.changeCursor(databaseHelper.getAllData());
			break;

		default:
			break;
		}
		
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
			DeleteTask del = new DeleteTask();
			del.execute();
			//databaseHelper.deleteData(current.getId() );
			//customAdapter.changeCursor(databaseHelper.getAllData());
			//customAdapter.notifyDataSetChanged();
//			listItems.remove(position);
//			this.adapter.notifyDataSetChanged();
			
			return true;
		case R.id.menuItemCall:					
			String tel = listItems.get(position).getTitle().replace("Call ","");
			Intent intent = new Intent(Intent.ACTION_DIAL);
			intent.setData(Uri.parse("tel:"+tel));
			startActivity(intent); 

		}
		return super.onContextItemSelected(item);
	}
		

	private class LoadDB extends AsyncTask<Void, Void, Cursor> {

		@Override
		protected Cursor doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			Cursor ret = databaseHelper.getAllData();
			return ret;
		}
		protected void onPostExecute(Cursor c) {
			customAdapter = new ToDoCursurAdapter(getApplicationContext(), c);
			toDoList.setAdapter(customAdapter);
	    }
	}
	
	private class UpdateDB extends AsyncTask<ContentValues, Void, Void>{

		@Override
		protected Void doInBackground(ContentValues... params) {
			// TODO Auto-generated method stub
			String title = params[0].get("title").toString();
			Date due = new Date(params[0].get("due").toString());
			databaseHelper.insertData(title,due);
			return null;
		}
		@Override
	    protected void onPostExecute(Void v) {			
			customAdapter.changeCursor(databaseHelper.getAllData());			
	    }
	}
	private class DeleteTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			databaseHelper.deleteData(current.getId() );
			return null;
		}
		
		@Override
	    protected void onPostExecute(Void v) {			
			customAdapter.changeCursor(databaseHelper.getAllData());			
	    }
		
	}

}
