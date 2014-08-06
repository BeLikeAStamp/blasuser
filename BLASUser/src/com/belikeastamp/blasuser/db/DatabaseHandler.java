package com.belikeastamp.blasuser.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static DatabaseHandler instance;

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "blas.db";

	// Table project
	public static final String TABLE_PROJECTS = "projects";

	// colonnes
	public static final String P_NAME = "project_name";
	public static final String P_SUBDATE = "sub_date";
	public static final String P_STATUS = "project_status";
	public static final String P_THEME = "theme";
	public static final String P_TYPE = "type";
	public static final String P_COLORS = "colors";
	public static final String P_ORDERDATA = "order_date";
	public static final String P_NBRCARDS = "nbr_cards";
	public static final String P_REMOTEID = "remote_id";
	
	public static final int PROJ_WAIT = -1;
	public static final int PROJ_SUBMIT = 0;
	public static final int PROJ_ACCEPTED = 1;
	public static final int PROJ_DISMISSED = 2;
	public static final int PROTO_INPROGRESS = 3;
	public static final int PROTO_PENDING = 4;
	public static final int PROTO_ACCEPTED = 5;
	public static final int PROTO_DISMISSED = 6;
	public static final int REAL_INPROGRESS = 7;
	public static final int REAL_DONE = 8;
	
	public static DatabaseHandler getInstance(Context context) {

		// Use the application context, which will ensure that you 
		// don't accidentally leak an Activity's context.
		// See this article for more information: http://bit.ly/6LRzfx
		if (instance == null) {
			instance = new DatabaseHandler(context.getApplicationContext());
		}
		return instance;
	}


	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public DatabaseHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d("Handler", "onCreate");
		String CREATE_PROJECTS_TABLE = "CREATE TABLE " + TABLE_PROJECTS 
				+ "("
				+ P_NAME + " TEXT NOT NULL PRIMARY KEY,"
				+ P_SUBDATE + " TEXT NOT NULL,"
				+ P_THEME + " TEXT NOT NULL,"
				+ P_TYPE + " TEXT NOT NULL,"
				+ P_ORDERDATA + " TEXT NOT NULL,"
				+ P_STATUS + " INTEGER NOT NULL,"
				+ P_NBRCARDS + " INTEGER NOT NULL,"
				+ P_REMOTEID + " INTEGER,"
				+ P_COLORS + " TEXT NOT NULL"
				+ ")";
		db.execSQL(CREATE_PROJECTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);

		// Create tables again
		onCreate(db);
	}

}
