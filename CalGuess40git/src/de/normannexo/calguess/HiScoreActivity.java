package de.normannexo.calguess;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class HiScoreActivity extends Activity {
	private static final String TAG = HiScoreActivity.class.getSimpleName();
	private ListView lvList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hi_score_layout);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Highscore");
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    lvList = (ListView) findViewById(R.id.listView1);
	    HiScoreOpenHandler hiScoreOpenHandler = new HiScoreOpenHandler(this);
	    
	    Cursor cursor = hiScoreOpenHandler.getReadableDatabase().query("tbl_hiscore",new String[]{"_id","SCORE", "DATE_PLAYED"},null, null, null,null, "SCORE DESC", "10");
	    
	    SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.hiscore_row, cursor, new String[]{"SCORE", "DATE_PLAYED"}, new int[]{R.id.tv_hsscore, R.id.tv_hsdate});
	    Log.d(TAG, ""+ cursor.getCount());
	    lvList.setAdapter(mAdapter);
	    
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(this, CalGuess.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
