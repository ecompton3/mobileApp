package com.example.wheresmystuff;

import com.example.wheresmystuff.models.Account;
import com.example.wheresmystuff.models.DBHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class HomeActivity extends Activity {
	private Account account;
	private DBHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		db = new DBHelper(this);
		Intent intent = getIntent();
		String user = intent.getStringExtra("user");
		account = db.getAccount(user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
