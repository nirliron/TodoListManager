package il.ac.huji.ToDoList;

import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDBHelper {		
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "todo_db";
	private static final String TABLE_NAME = "todo";
	private static final String ITEM_ID = "_id";
	private static final String ITEM_TITLE = "title";
	private static final String ITEM_DUE = "due";
	
	private DatabaseOpenHelper openHelper;
	private SQLiteDatabase db;
	private ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("todo");
	private class DatabaseOpenHelper extends SQLiteOpenHelper {		        		
		public DatabaseOpenHelper(Context aContext) {
			
            super(aContext, DATABASE_NAME, null, DATABASE_VERSION);
        }
 
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            // Create your tables here
 
            String buildSQL = "CREATE TABLE " + TABLE_NAME + "( " + ITEM_ID + " INTEGER PRIMARY KEY  , " +
                    ITEM_TITLE + " TEXT, " + ITEM_DUE + " LONG )";             
            sqLiteDatabase.execSQL(buildSQL);
        }
 
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            // Database schema upgrade code goes here
 
            String buildSQL = "DROP TABLE IF EXISTS " + TABLE_NAME;            
            sqLiteDatabase.execSQL(buildSQL);       // drop previous table
            onCreate(sqLiteDatabase);               // create the table from the beginning
        }
    }
	public ToDoDBHelper(Context context) {
		// TODO Auto-generated constructor stub		
		openHelper = new DatabaseOpenHelper(context);
		db = openHelper.getWritableDatabase();
	}
	public void insertData (String title, Date due) {
		 
        // we are using ContentValues to avoid sql format errors
 
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_TITLE, title);
        contentValues.put(ITEM_DUE, due.getTime());
        db.insert(TABLE_NAME, null, contentValues);        
//        ParseObject curr = new ParseObject("todo");
//		curr.put("title", title);
//		curr.put("due", due);
//		int iid =  Integer.parseInt(id);
//		curr.put("id",iid);
//		curr.saveInBackground();
    }
	public void deleteData(String _id){
//		String deleteSQL = "DELETE FROM "+TABLE_NAME+" WHERE "+ITEM_TITLE+"="+title;
//		db.rawQuery(deleteSQL, null);
		String[] args = new String[1];
		args[0] = _id;		
		db.delete(TABLE_NAME, ITEM_ID+"=?", args);	
		int iid = Integer.parseInt(_id);
		query.whereEqualTo("id", iid);			
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stub
				for(ParseObject del:objects){
					
					try {
						del.delete();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	public Cursor getAllData () {		 
        String buildSQL = "SELECT * FROM " + TABLE_NAME;          
        return db.rawQuery(buildSQL, null);
    }
}
