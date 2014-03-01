package il.ac.huji.ToDoList;


import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
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

	ArrayList<String> listItems=new ArrayList<String>();
	PairtyArrayAdapter adapter;
	ListView toDoList;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toDoList = (ListView)findViewById(R.id.lstToDoItems);
		registerForContextMenu(toDoList);
		adapter=new PairtyArrayAdapter(this,
	            android.R.layout.simple_list_item_1,
	            listItems);
		toDoList.setAdapter(adapter);					        
	}

	public void addItems() {
		final EditText edtTask = (EditText)findViewById(R.id.edtNewItem);
		String task = edtTask.getText().toString();
		if(!task.equals("")){
			listItems.add(task);
		}
        edtTask.setText("");
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
	            addItems();
	            return true;	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	@Override  
	   public void onCreateContextMenu(ContextMenu menu, View v,  
	             ContextMenuInfo menuInfo) {  
	        // TODO Auto-generated method stub  
	        super.onCreateContextMenu(menu, v, menuInfo);  
	        MenuInflater m = getMenuInflater();  
	        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
	        menu.setHeaderTitle(listItems.get(info.position));
	        m.inflate(R.menu.delete_menue, menu);	        
	   }  
	@Override  
	   public boolean onContextItemSelected(MenuItem item) {  
	        switch(item.getItemId()){  
	             case R.id.menuItemDelete:  
	                  AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();  
	                  int position = (int) info.id;  
	                  listItems.remove(position);  
	                  this.adapter.notifyDataSetChanged();  
	                  return true;  
	        }  
	        return super.onContextItemSelected(item);  
	   }  

}
