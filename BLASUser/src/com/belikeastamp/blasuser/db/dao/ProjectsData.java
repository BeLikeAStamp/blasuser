package com.belikeastamp.blasuser.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.belikeastamp.blasuser.db.DatabaseHandler;
import com.belikeastamp.blasuser.db.model.Project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProjectsData {

	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public ProjectsData(Context context) {
		dbHelper = DatabaseHandler.getInstance(context);
	}  

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}


	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	public void addProjects(Project project) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHandler.P_NAME, project.getName());
		values.put(DatabaseHandler.P_SUBDATE, project.getSubDate());
		values.put(DatabaseHandler.P_STATUS, project.getStatus());
		values.put(DatabaseHandler.P_TYPE, project.getType());
		values.put(DatabaseHandler.P_DETAIL, project.getDetail());
		values.put(DatabaseHandler.P_ORDERDATE, project.getOrderDate());
		values.put(DatabaseHandler.P_NBRCARDS, project.getQuantity());
		values.put(DatabaseHandler.P_COLORS, project.getColors());
		
		// Inserting Row
		database.insert(DatabaseHandler.TABLE_PROJECTS, null, values);

	}

	public Project getProjects(String name) {
		Cursor cursor = database.query(DatabaseHandler.TABLE_PROJECTS, new String[] { 
				DatabaseHandler.P_NAME, 
				DatabaseHandler.P_SUBDATE,
				DatabaseHandler.P_STATUS,
				DatabaseHandler.P_TYPE,
				DatabaseHandler.P_DETAIL,
				DatabaseHandler.P_ORDERDATE,
				DatabaseHandler.P_NBRCARDS,
				DatabaseHandler.P_REMOTEID,
				DatabaseHandler.P_COLORS,
				DatabaseHandler.P_PROTO,
		}, DatabaseHandler.P_NAME + "=?",
		new String[] { name }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Project project = cursorToProject(cursor);
		return project;
	}

	public List<Project> getAllProjects() {
		List<Project> projects = new ArrayList<Project>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_PROJECTS;

		Cursor cursor = database.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Project project = cursorToProject(cursor);
				projects.add(project);
			} while (cursor.moveToNext());
		}

		return projects;
	}

	public List<Project> getAllSubmitProjects() {
		List<Project> projects = new ArrayList<Project>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_PROJECTS +
				" WHERE "+DatabaseHandler.P_STATUS+" = "+DatabaseHandler.PROJ_SUBMIT;

		Cursor cursor = database.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Project project = cursorToProject(cursor);
				projects.add(project);
			} while (cursor.moveToNext());
		}

		return projects;
	}
	
	public List<Project> getAllWaitingProjects() {
		List<Project> projects = new ArrayList<Project>();
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_PROJECTS +
				" WHERE "+DatabaseHandler.P_STATUS+" = "+DatabaseHandler.PROJ_WAIT;

		Cursor cursor = database.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Project project = cursorToProject(cursor);
				projects.add(project);
			} while (cursor.moveToNext());
		}

		return projects;
	}
	
	
	public int getProjectsCount() {
		String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_PROJECTS;
		Cursor cursor = database.rawQuery(selectQuery, null);
		cursor.close();
		return cursor.getCount();
	}

	public int updateProjectRemoteId(Project project, int remoteId) {
		
		ContentValues values = new ContentValues();
	    values.put(DatabaseHandler.P_REMOTEID, remoteId);
		
	    return database.update(DatabaseHandler.TABLE_PROJECTS, values, DatabaseHandler.P_NAME  + " = ?",
	            new String[] { project.getName() });
	}

	public void deleteProject(String name) {
		database.delete(DatabaseHandler.TABLE_PROJECTS, DatabaseHandler.P_NAME + "=?",
				new String[] {name });		
	}

	private Project cursorToProject(Cursor cursor) {
		Project project = new Project();
		project.setName(cursor.getString(0));
		project.setSubDate(cursor.getString(1));
		project.setStatus(Integer.valueOf(cursor.getString(2)));
		project.setType(cursor.getString(3));
		project.setDetail(cursor.getString(4));
		project.setOrderDate(cursor.getString(5));
		project.setStatus(cursor.getInt(6));
		project.setQuantity(cursor.getInt(7));
		project.setRemoteId(cursor.getLong(8));
		project.setColors(cursor.getString(9));
		project.setPath_to_prototype(cursor.getString(10));
		return project;
	}

	public boolean checkUnicity(String name) {
		Cursor cursor = database.query(DatabaseHandler.TABLE_PROJECTS, new String[] { 
				DatabaseHandler.P_NAME, 
		}, DatabaseHandler.P_NAME + "=?",
		new String[] { name }, null, null, null, null);
		
		return (cursor.getCount() == 0);
	}
	
	// TODAO
	public boolean update(String name) {
		Cursor cursor = database.query(DatabaseHandler.TABLE_PROJECTS, new String[] { 
				DatabaseHandler.P_NAME, 
				DatabaseHandler.P_SUBDATE,
				DatabaseHandler.P_STATUS,
				DatabaseHandler.P_DETAIL,
				DatabaseHandler.P_TYPE,
				DatabaseHandler.P_ORDERDATE,
				DatabaseHandler.P_NBRCARDS,
				DatabaseHandler.P_REMOTEID,
				DatabaseHandler.P_COLORS,
		}, DatabaseHandler.P_NAME + "=?",
		new String[] { name }, null, null, null, null);
		
		return (cursor.getCount() == 0);
	}
	
}
