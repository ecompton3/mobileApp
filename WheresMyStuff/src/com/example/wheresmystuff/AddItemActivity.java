package com.example.wheresmystuff;

import com.example.wheresmystuff.models.Account;
import com.example.wheresmystuff.models.DBHelper;
import com.example.wheresmystuff.models.FoundItem;
import com.example.wheresmystuff.models.ItemDBHelper;
import com.example.wheresmystuff.models.LostItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.support.v4.app.NavUtils;

public class AddItemActivity extends Activity {

	private final String ANTIQUE = "antique", PERSONAL = "personal", MISC = "misc", LOST = "lost", FOUND = "found";
	private String name, desc, city, state, category, type;
	private int reward, month, day, year;
	private Account account;
	private DBHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
		// Show the Up button in the action bar.
		setupActionBar();
		db = new DBHelper(this);
		Bundle extras = getIntent().getExtras();
		String user = "";
		if (extras != null) {
		    user = extras.getString("user");
		    Log.w("users", user + "");
		}
		account = db.getAccount(user);
		
		findViewById(R.id.addItem).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						addItem();
					}
				});
		findViewById(R.id.cancel_add).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						cancelAdd();
					}
				});
	}
	
	private void addItem() {
		EditText mName = (EditText)findViewById(R.id.itemName);
		EditText mDesc = (EditText)findViewById(R.id.itemDesc);
		EditText mCity = (EditText)findViewById(R.id.itemCity);
		EditText mState = (EditText)findViewById(R.id.itemState);
		EditText mReward = (EditText)findViewById(R.id.itemReward);
		
		// Reset errors.
				mName.setError(null);
				mDesc.setError(null);
				mCity.setError(null);
				mState.setError(null);
				mReward.setError(null);
				

				// Store values at the time of the login attempt.
		
		
		name   = mName.getText().toString();
		desc   = mDesc.getText().toString();
		city   = mCity.getText().toString();
		state   = mState.getText().toString();
		String rewardString = mReward.getText().toString();
		
		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(name)) {
			mName.setError(getString(R.string.error_field_required));
			focusView = mName;
			cancel = true;
		} else if(db.getItem(name) == null) {
			mName.setError("Name Already Exists");
			focusView = mName;
			cancel = true;
		}
		
		if (TextUtils.isEmpty(desc)) {
			mDesc.setError(getString(R.string.error_field_required));
			focusView = mDesc;
			cancel = true;
		}
		
		if (TextUtils.isEmpty(city)) {
			mCity.setError(getString(R.string.error_field_required));
			focusView = mCity;
			cancel = true;
		}
		
		if (TextUtils.isEmpty(state)) {
			mState.setError(getString(R.string.error_field_required));
			focusView = mState;
			cancel = true;
		} else if(state.length() > 2) {
			
				mState.setError("Max Two Characters");
				focusView = mState;
				cancel = true;
			
		}
		
		if (TextUtils.isEmpty(rewardString)) {
			mReward.setError(getString(R.string.error_field_required));
			focusView = mReward;
			cancel = true;
		}
		
		if(cancel) {
			return;
		}
		
		reward = Integer.parseInt(rewardString);
		day = ((DatePicker)findViewById(R.id.datePickerItem)).getDayOfMonth();
		month = ((DatePicker)findViewById(R.id.datePickerItem)).getMonth();
		year = ((DatePicker)findViewById(R.id.datePickerItem)).getYear();
		
		RadioGroup cat = (RadioGroup)findViewById(R.id.radioGroupCat);
		RadioGroup typeGroup = (RadioGroup)findViewById(R.id.radioGroupType);
		
		if(cat.getCheckedRadioButtonId() == R.id.radioAnt) {
			category = ANTIQUE;
		} else if(cat.getCheckedRadioButtonId() == R.id.radioPersonal) {
			category = PERSONAL;
		} else {
			category = MISC;
		}
		
		if(typeGroup.getCheckedRadioButtonId() == R.id.radioLost) {
			db.addItem(new LostItem(name, account.getUsername(), desc, category, city, state, reward, day, month, year));
		} else {
			db.addItem(new FoundItem(name, account.getUsername(), desc, category, city, state, reward, day, month, year));

		}
		
		Intent myIntent = new Intent(AddItemActivity.this, HomeActivity.class);

		myIntent.putExtra("user", account.getUsername());
		AddItemActivity.this.startActivity(myIntent);
	}
	
	private void cancelAdd() {
		Intent myIntent = new Intent(AddItemActivity.this, HomeActivity.class);

		myIntent.putExtra("user", account.getUsername());
		AddItemActivity.this.startActivity(myIntent);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
