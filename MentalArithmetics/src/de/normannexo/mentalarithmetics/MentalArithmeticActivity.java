package de.normannexo.mentalarithmetics;

import de.normannexo.mentalarithmetics.PrefActivity;
import de.normannexo.mentalarithmetics.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MentalArithmeticActivity extends Activity {
	private Button btn0;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;
	private Button btn7;
	private Button btn8;
	private Button btn9;

	private Handler handler;

	private StringBuilder strLoesung = new StringBuilder();

	private TextView tvAufgabe;
	private TextView tvLoesung;

	private Aufgabe aufgabe;
	private MathUnit mathUnit;
	private Mode mode;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main2);
		btn0 = (Button) findViewById(R.id.btn0);
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.btn5);
		btn6 = (Button) findViewById(R.id.btn6);
		btn7 = (Button) findViewById(R.id.btn7);
		btn8 = (Button) findViewById(R.id.btn8);
		btn9 = (Button) findViewById(R.id.btn9);
		MyButtonClickHandler clickHandler = new MyButtonClickHandler();

		btn0.setOnClickListener(clickHandler);
		btn1.setOnClickListener(clickHandler);
		btn2.setOnClickListener(clickHandler);
		btn3.setOnClickListener(clickHandler);
		btn4.setOnClickListener(clickHandler);
		btn5.setOnClickListener(clickHandler);
		btn6.setOnClickListener(clickHandler);
		btn7.setOnClickListener(clickHandler);
		btn8.setOnClickListener(clickHandler);
		btn9.setOnClickListener(clickHandler);

		tvAufgabe = (TextView) findViewById(R.id.tvAufgabe);
		tvLoesung = (TextView) findViewById(R.id.tvLoesung);
	}

	// progresstest

	@Override
	protected void onStart() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		mathUnit = new MathUnit();
		String strMode = sharedPrefs.getString("modus", "Mal");
		String strStellen = sharedPrefs.getString("stellen1", "2");
		mathUnit.setStellen(1, Integer.parseInt(strStellen));
		strStellen = sharedPrefs.getString("stellen2", "2");
		mathUnit.setStellen(2, Integer.parseInt(strStellen));

		if (strMode.equals("Plus")) {
			mode = Mode.Plus;
		} else if (strMode.equals("Minus")) {
			mode = Mode.Minus;

		} else if (strMode.equals("Mal")) {
			mode = Mode.Mal;

		} else if (strMode.equals("Quad")) {
			mode = Mode.Quad;
		} else if (strMode.equals("Mix")) {
			mode = Mode.Mix;
		}

		newAufgabe(mode);
		tvAufgabe.setText(aufgabe.toString());
		super.onStart();
	}

	public class MyButtonClickHandler implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn0:
				strLoesung.append("0");
				break;
			case R.id.btn1:
				strLoesung.append("1");
				break;
			case R.id.btn2:
				strLoesung.append("2");
				break;
			case R.id.btn3:
				strLoesung.append("3");
				break;
			case R.id.btn4:
				strLoesung.append("4");
				break;
			case R.id.btn5:
				strLoesung.append("5");
				break;
			case R.id.btn6:
				strLoesung.append("6");
				break;
			case R.id.btn7:
				strLoesung.append("7");
				break;
			case R.id.btn8:
				strLoesung.append("8");
				break;
			case R.id.btn9:
				strLoesung.append("9");
				break;

			}
			if (checkInput()) {
				tvLoesung.setTextColor(Color.BLUE);
				tvLoesung.setText(strLoesung);
			} else {
				tvLoesung.setTextColor(Color.RED);
				tvLoesung.setText(strLoesung);
				strLoesung.setLength(0);
			}
			int iLoesung = 0;
			try {
				iLoesung = Integer.parseInt(strLoesung.toString());
			} catch (NumberFormatException e) {
				iLoesung = 0;
			}
			if (iLoesung == aufgabe.getLoesung()) {

				newAufgabe(mode);
			}
		}

	}

	private void newAufgabe(Mode mode) {
		strLoesung.setLength(0);
		aufgabe = mathUnit.createAufgabe(mode);
		tvAufgabe.setText(aufgabe.toString());
		tvLoesung.setText("");
	}

	private boolean checkInput() {
		boolean right = true;
		String strSolution = "" + aufgabe.getLoesung();
		for (int i = 0; i < strLoesung.length(); i++) {
			if (strLoesung.charAt(i) != strSolution.charAt(i)) {
				return false;
			}
		}
		return right;

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionsmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.prefs:
			startActivity(new Intent(this, PrefActivity.class));
			break;
		}

		return true;
	}

}