package de.normannexo.calguess;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HiScoreOpenHandler extends SQLiteOpenHelper {

	private static final String TAG = HiScoreOpenHandler.class.getSimpleName();

	private static final String DATABASE_NAME = "cg_hiscore.db";
	private static final int DATABASE_VERSION = 7;

	public static final String TABLE_HI_SCORE_CREATE = "CREATE TABLE tbl_hiscore ("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "SCORE INTEGER, "
			+ "DATE_PLAYED DATE);";

	public HiScoreOpenHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.i(TAG, "constructor");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "onCreate SQLite " + TABLE_HI_SCORE_CREATE);
		db.execSQL(TABLE_HI_SCORE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS tbl_hiscore");
		onCreate(db);

	}

	public void insert(int hiScore) {
		long rowId = -1;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("SCORE", hiScore);
		values.put("DATE_PLAYED", dateFormat.format(date));
		rowId = db.insert("tbl_hiscore", null, values);
	}

}
