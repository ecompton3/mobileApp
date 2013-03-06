package com.example.wheresmystuff;

import java.util.List;

import com.example.wheresmystuff.models.Account;
import com.example.wheresmystuff.models.DBHelper;
import com.example.wheresmystuff.models.Item;
import com.example.wheresmystuff.models.ItemDBHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends Activity {
	private Account account;
	private DBHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		db = new DBHelper(this);
		
		Bundle extras = getIntent().getExtras();
		String user = "";
		if (extras != null) {
		    user = extras.getString("user");
		    Log.w("users", user + "");
		}
		//Log.w("user",user);
		
		account = db.getAccount(user);
		//Log.i("user",db.getAccount(user).getUsername());
		findViewById(R.id.LaunchAdd).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent myIntent = new Intent(HomeActivity.this, AddItemActivity.class);

						myIntent.putExtra("user", account.getUsername());
						HomeActivity.this.startActivity(myIntent);
					}
				});
		findViewById(R.id.LaunchView).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						//Intent myIntent = new Intent(HomeActivity.this, AddItemActivity.class);
						//HomeActivity.this.startActivity(myIntent);
						//myIntent.putExtra("user", account.getUsername());
						makeToast();
					}
				});
	}
	public void makeToast() {
		String name = account.getUsername();
		List<Item> items = db.getUserItems(name);
		if(items == null) {
			Toast.makeText(this, "-1", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, items.size() + "", Toast.LENGTH_SHORT).show();
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
