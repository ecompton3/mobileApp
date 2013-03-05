package com.example.wheresmystuff.models;

import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "acountManager";
 
    // Contacts table name
    private static final String TABLE_ACCOUNTS = "accounts";
 
    // Contacts Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "password";
    private static final String KEY_LOCKED = "locked";
    private static final String KEY_ATTEMPTS = "attempts";
 
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ACCOUNTS + "("
                + KEY_NAME + " TEXT PRIMARY KEY,"
                + KEY_PASS + " TEXT," + KEY_LOCKED + " INTEGER," + KEY_ATTEMPTS + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
 
        // Create tables again
        onCreate(db);
    }
    
 // Adding new contact
    public void addAccount(Account account) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, account.getUsername()); // account Name
        values.put(KEY_PASS, account.getPassword()); // Account Password
        if(account.isLocked()) {
        	values.put(KEY_LOCKED, 1); // account lock
        } else {
        	values.put(KEY_LOCKED, 0); // account lock
        }
        values.put(KEY_ATTEMPTS, account.getLoginAttempts());
        
     
        // Inserting Row
        db.insert(TABLE_ACCOUNTS, null, values);
        db.close(); // Closing database connection
    }
     
    // Getting single contact
    public Account getAccount(String username) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(TABLE_ACCOUNTS, new String[] {
                KEY_NAME, KEY_PASS, KEY_LOCKED, KEY_ATTEMPTS }, KEY_NAME + "=?",
                new String[] { String.valueOf(username) }, null, null, null, null);
        if (cursor != null){
        	 cursor.moveToFirst();
        }
        if(cursor == null || cursor.getCount() < 1 ){
        	return null;
        }
        Log.w("cursor", "" + cursor.getColumnCount());
        Account account = new Account(
                cursor.getString(0), cursor.getString(1));
     
        if(cursor.getInt(2) == 1) {
        	 account.setLocked(true);
        } else {
        	 account.setLocked(false);
        }
        account.setLoginAttempts(cursor.getInt(3));
       
        // return contact
        return account;
    }
     
    // Getting All Contacts
    public List<Account> getAllAccounts() {
    	List<Account> accountList = new ArrayList<Account>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNTS;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Account account = new Account(cursor.getString(0),cursor.getString(1));
               
                // Adding contact to list
                accountList.add(account);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return accountList;
    }
     
    // Getting contacts Count
    public int getAccountsCount() {
    	String countQuery = "SELECT  * FROM " + TABLE_ACCOUNTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
    // Updating single contact
    public int updateAccount(Account account) {
    	 SQLiteDatabase db = this.getWritableDatabase();
    	 
    	    ContentValues values = new ContentValues();
    	    values.put(KEY_NAME, account.getUsername());
    	    values.put(KEY_PASS, account.getPassword());
    	    if(account.isLocked()) {
    	    	 values.put(KEY_LOCKED, 1);
    	    } else {
    	    	 values.put(KEY_LOCKED, 0);
    	    }
    	    values.put(KEY_ATTEMPTS, account.getLoginAttempts());
    	    // updating row
    	    return db.update(TABLE_ACCOUNTS, values, KEY_NAME + " = ?",
    	            new String[] { String.valueOf(account.getUsername()) });
    }
     
    // Deleting single contact
    public void deleteContact(Account account) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACCOUNTS, KEY_NAME + " = ?",
                new String[] { String.valueOf(account.getUsername()) });
        db.close();
    }
}
