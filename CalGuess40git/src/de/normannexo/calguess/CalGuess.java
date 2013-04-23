package de.normannexo.calguess;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalGuess extends Activity {
	private static final String TAG = CalGuess.class.getSimpleName();
	private static final int SPIELDAUER = 60;
	private Button btnDie;
	private Button btnDon;
	private Button btnFre;
	private Button btnMit;
	private Button btnMon;
	private Button btnSam;
	private Button btnSon;
	private Button btnStart;
	private TextView tvTest;
	Calendar currCal;
	FtvCalendarGuess fcg;
	private volatile boolean gameRunning;
	private Handler handler = new Handler();
	private int iDauer = SPIELDAUER;
	private int iGuesses = 0;
	private int iCorrectGuesses = 0;
	private CalGuessTimerTask calTimer;
	private HiScoreOpenHandler hiScoreOpenHandler;

	private class ThrTimer implements Runnable {
		public void run() {
			while (iDauer > 0 && gameRunning) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {

				}
				iDauer--;
				handler.post(new Runnable() {
					public void run() {
						Log.d(TAG, "" + gameRunning);
						tvTime.setText("" + iDauer);
					}
				});

			}

			handler.post(new Runnable() {
				public void run() {
					endGame();
				}
			});
		}
	}

	private TextView tvDate;

	private TextView tvTime;

	public void endGame() {
		reset();
		Context context = getApplicationContext();
		CharSequence text = iGuesses + " Daten: " + iCorrectGuesses
				+ " richtig.";
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		if (iGuesses > 0 && iGuesses == iCorrectGuesses) {
			hiScoreOpenHandler = new HiScoreOpenHandler(this);
			hiScoreOpenHandler.insert(iGuesses);

			hiScoreOpenHandler.close();
		}
	}

	private void reset() {
		gameRunning = false;
		iDauer = SPIELDAUER;
		activateButtons(false);
		if (calTimer != null) {
			calTimer.cancel(true);
		}

		btnStart.setVisibility(View.VISIBLE);
		tvTime.setText("" + 0);
	}

	public void myClickHandler(View view) {
		int guess = 0;
		switch (view.getId()) {
		case R.id.button1:
			guess = 2;
			break;
		case R.id.button2:
			guess = 3;
			break;

		case R.id.button3:
			guess = 4;
			break;
		case R.id.button4:
			guess = 5;
			break;
		case R.id.button5:
			guess = 6;

			break;
		case R.id.button6:
			guess = 7;

			break;
		case R.id.button7:
			guess = 1;

			break;

		}
		iGuesses++;
		if (guessCorrect(currCal, guess)) {
			iCorrectGuesses++;

		}
		nextDate();

	}

	public void handlerBtnStart(View view) {
		startGame();
	}

	public void nextDate() {

		currCal = CalGuess.randomCal();

		String str = calToString(currCal);
		tvDate.setText(str);

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		// fcg = new FtvCalendarGuess();

		setContentView(R.layout.main);
		tvDate = (TextView) findViewById(R.id.textView1);
		tvTime = (TextView) findViewById(R.id.textView2);

		btnMon = (Button) findViewById(R.id.button1);
		btnDie = (Button) findViewById(R.id.button2);
		btnMit = (Button) findViewById(R.id.button3);
		btnDon = (Button) findViewById(R.id.button4);
		btnFre = (Button) findViewById(R.id.button5);
		btnSam = (Button) findViewById(R.id.button6);
		btnSon = (Button) findViewById(R.id.button7);
		btnStart = (Button) findViewById(R.id.btnStart);
		tvTime.setText("" + iDauer);
		tvDate.setText("0.0.0000");

		activateButtons(false);
		// new CalGuessTimerTask().execute(10);

	}

	public void startGame() {
		if (!gameRunning) {
			gameRunning = true;
			iCorrectGuesses = 0;
			iGuesses = 0;
			activateButtons(true);
			btnStart.setVisibility(View.INVISIBLE);
			nextDate();
			calTimer = new CalGuessTimerTask();
			calTimer.execute(SPIELDAUER);

		}
	}

	public void tick(long millisUntilFinished) {

		tvTime.setText("" + millisUntilFinished / 1000);
	}

	public static String calToString(Calendar cal) {

		String str = String.format("%td.%<tm.%<tY", cal);
		return str;
	}

	public static Calendar randomCal() {
		Calendar cal = Calendar.getInstance();

		// random day

		int monthdays;

		int month = (int) ((Math.random() * 12));

		if (month == 6 || month == 9 || month == 11 || month == 4) {
			monthdays = 30;
		} else if (month == 2) {
			monthdays = 28;
		} else {
			monthdays = 31;
		}
		int day = (int) ((Math.random() * monthdays) + 1);
		int year = (int) ((Math.random() * 500) + 1600);
		cal.set(year, month, day);
		return cal;
	}

	public void activateButtons(boolean b) {
		btnMon.setClickable(b);
		btnDie.setClickable(b);
		btnMit.setClickable(b);
		btnDon.setClickable(b);
		btnFre.setClickable(b);
		btnSam.setClickable(b);
		btnSon.setClickable(b);

	}

	boolean guessCorrect(Calendar cal, int guess) {
		Calendar date = Calendar.getInstance();
		date = cal;
		int iDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		return (guess == iDayOfWeek);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.hiscore:
			startActivity(new Intent(this, HiScoreActivity.class));
			break;
		}

		return true;

	}

	@Override
	protected void onPause() {

		reset();
		Log.d(TAG, "onPause");

		super.onPause();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		Log.d(TAG, "onRestart");
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume " + gameRunning);
		reset();
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		Log.d(TAG, "onStart");
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "onStop");
		// TODO Auto-generated method stub
		super.onStop();
	}

	private class CalGuessTimerTask extends AsyncTask<Integer, Integer, Void> {
		/**
		 * The system calls this to perform work in a worker thread and delivers
		 * it the parameters given to AsyncTask.execute()
		 */

		/**
		 * The system calls this to perform work in the UI thread and delivers
		 * the result from doInBackground()
		 */
		protected void onPostExecute(Void result) {
			tvTime.setText("Fertig");
			endGame();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			tvTime.setText("" + values[0]);
			super.onProgressUpdate(values);
		}

		@Override
		protected Void doInBackground(Integer... iSecs) {
			// publishProgress();
			for (int i = iSecs[0]; i > 0; i--) {
				publishProgress(Integer.valueOf(i));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					Log.d(TAG, "interrupted");
				}

			}

			return null;
		}

	}

}