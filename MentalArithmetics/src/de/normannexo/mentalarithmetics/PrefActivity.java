package de.normannexo.mentalarithmetics;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class PrefActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("MentalArithmetic");
		actionBar.setSubtitle("Settings");
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new PrefFragment()).commit();
	}

}
