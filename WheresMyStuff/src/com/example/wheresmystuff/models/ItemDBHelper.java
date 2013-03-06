package com.example.wheresmystuff.models;

import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ItemDBHelper extends SQLiteOpenHelper{

	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "acountManager";
 
    // Contacts table name
    private static final String TABLE_ITEMS = "items";
 
    // Contacts Table Columns names
    private static final String KEY_USER = "user";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "description";
    private static final String KEY_CITY = "city";
    private static final String KEY_STATE = "state";
    private static final String KEY_REWARD = "reward";
    private static final String KEY_MONTH = "month";
    private static final String KEY_DAY = "day";
    private static final String KEY_YEAR = "year";
    private static final String KEY_CAT = "category";
    private static final String KEY_STATUS = "status";
    private static final String KEY_TYPE = "type";
	
    
 
    public ItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_NAME + " TEXT PRIMARY KEY,"
                + KEY_USER + " TEXT," + KEY_DESC + " TEXT," + KEY_CITY + " TEXT," + KEY_STATE + " TEXT," + 
                KEY_REWARD + " INTEGER," + KEY_MONTH + " INTEGER," + KEY_DAY + " INTEGER," + KEY_YEAR + " INTEGER," + 
                KEY_CAT + " TEXT," + KEY_STATUS + " INTEGER" + KEY_TYPE + " TEXT" + ")";
        db.execSQL(CREATE_ITEMS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
 
        // Create tables again
        onCreate(db);
    }
    
 // Adding new contact
    public void addItem(Item item) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName()); 
        values.put(KEY_USER, item.getOwner());
        values.put(KEY_DESC, item.getDescription());
        values.put(KEY_CITY, item.getCity());
        values.put(KEY_STATE, item.getState());
        values.put(KEY_REWARD, item.getReward());
        values.put(KEY_MONTH, item.getMonth());
        values.put(KEY_DAY, item.getDay());
        values.put(KEY_YEAR, item.getYear());
        values.put(KEY_CAT, item.getCategory());
        if(item.isStatus()) {
        	values.put(KEY_STATUS, 1);
        } else {
        	values.put(KEY_STATUS, 0);
        }
        
        values.put(KEY_TYPE, item.getType());
     
        // Inserting Row
        db.insert(TABLE_ITEMS, null, values);
        db.close(); // Closing database connection
    }
     
    // Getting single contact
    public Item getItem(String itemName) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(TABLE_ITEMS, new String[] {
                KEY_NAME, KEY_USER, KEY_DESC, KEY_CITY, KEY_STATE, KEY_REWARD, KEY_MONTH, KEY_DAY, KEY_YEAR, KEY_CAT, KEY_STATUS, KEY_TYPE,}, KEY_NAME + "=?",
                new String[] { String.valueOf(itemName) }, null, null, null, null);
        if (cursor != null){
        	 cursor.moveToFirst();
        }
        if(cursor == null || cursor.getCount() < 1 ){
        	return null;
        }
        Log.w("cursor", "" + cursor.getColumnCount());
        Item item = new Item(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(9),cursor.getString(3),
        		cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8));
        item.setType(cursor.getString(11));
        if(cursor.getInt(10) == 1) {
        	 item.setStatus(true);
        } else {
        	 item.setStatus(false);
        }
       
        // return contact
        return item;
    }
     
    // Getting All Contacts
    public List<Item> getAllItems() {
    	List<Item> itemList = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(9),cursor.getString(3),
                		cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8));
                item.setType(cursor.getString(11));
                if(cursor.getInt(10) == 1) {
                	 item.setStatus(true);
                } else {
                	 item.setStatus(false);
                }
               
               
                // Adding contact to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return itemList;
    }
    
 // Getting All Contacts
    public List<Item> getUserItems(String owner) {
    	List<Item> itemList = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS + " WHERE " + KEY_USER + " = '" + owner + "'";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(9),cursor.getString(3),
                		cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8));
                item.setType(cursor.getString(11));
                if(cursor.getInt(10) == 1) {
                	 item.setStatus(true);
                } else {
                	 item.setStatus(false);
                }
               
               
                // Adding contact to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return itemList;
    }
     
    // Getting contacts Count
    public int getItemssCount() {
    	String countQuery = "SELECT  * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
    // Updating single contact
    public int updateItem(Item item) {
    	 SQLiteDatabase db = this.getWritableDatabase();
    	 
    	    ContentValues values = new ContentValues();
    	    values.put(KEY_NAME, item.getName()); 
            values.put(KEY_USER, item.getOwner());
            values.put(KEY_DESC, item.getDescription());
            values.put(KEY_CITY, item.getCity());
            values.put(KEY_STATE, item.getState());
            values.put(KEY_REWARD, item.getReward());
            values.put(KEY_MONTH, item.getMonth());
            values.put(KEY_DAY, item.getDay());
            values.put(KEY_YEAR, item.getYear());
            values.put(KEY_CAT, item.getCategory());
            if(item.isStatus()) {
            	values.put(KEY_STATUS, 1);
            } else {
            	values.put(KEY_STATUS, 0);
            }
            values.put(KEY_TYPE, item.getType());
    	    // updating row
    	    return db.update(TABLE_ITEMS, values, KEY_NAME + " = ?",
    	            new String[] { String.valueOf(item.getName()) });
    }
     
    // Deleting single contact
    public void deleteItem(Item item) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_NAME + " = ?",
                new String[] { String.valueOf(item.getName()) });
        db.close();
    }
}
