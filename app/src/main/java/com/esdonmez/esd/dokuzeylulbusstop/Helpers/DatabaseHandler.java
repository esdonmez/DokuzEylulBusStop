package com.esdonmez.esd.dokuzeylulbusstop.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.esdonmez.esd.dokuzeylulbusstop.Models.StopModel;
import com.esdonmez.esd.dokuzeylulbusstop.Models.SurveyModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DeuBusStop.db";

    // Contacts table name
    private static final String TABLE_STOPS = "stops";
    private static final String TABLE_SURVEYS = "surveys";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    private static final String KEY_LIKE = "like";
    private static final String KEY_VOTE = "vote";
    private static final String KEY_ADVICE = "advice";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_STOP = "stop";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STOP_TABLE = "CREATE TABLE " + TABLE_STOPS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_LAT + " TEXT," + KEY_LNG + " TEXT," + KEY_LIKE + " INTEGER," + KEY_VOTE + " INTEGER" + ")";
        db.execSQL(CREATE_STOP_TABLE);

        String CREATE_SURVEY_TABLE = "CREATE TABLE " + TABLE_SURVEYS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ADVICE + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_LIKE + " TEXT," + KEY_STOP + " TEXT" + ")";
        db.execSQL(CREATE_SURVEY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEYS);
        // Create tables again
        onCreate(db);
    }

    public void addStop(StopModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, model.getName());
        values.put(KEY_LAT, model.getLatitude());
        values.put(KEY_LNG, model.getLongitude());
        values.put(KEY_LIKE, model.getLike());
        values.put(KEY_VOTE, model.getVote());

        // Inserting Row
        db.insert(TABLE_STOPS, null, values);
        db.close(); // Closing database connection
    }

    public StopModel getStop(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STOPS, new String[] { KEY_ID,
                        KEY_NAME, KEY_LAT, KEY_LNG, KEY_LIKE, KEY_VOTE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        StopModel stop = new StopModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)));

        return stop;
    }

    public List<StopModel> getAllStops() {
        List<StopModel> stopList = new ArrayList<StopModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STOPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StopModel model = new StopModel();
                model.setId(Integer.parseInt(cursor.getString(0)));
                model.setName(cursor.getString(1));
                model.setLatitude(cursor.getString(2));
                model.setLongitude(cursor.getString(3));
                model.setLike(Integer.parseInt(cursor.getString(4)));
                model.setVote(Integer.parseInt(cursor.getString(5)));

                stopList.add(model);
            } while (cursor.moveToNext());
        }

        // return contact list
        return stopList;
    }

    public int updateStop(StopModel model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, model.getName());
        values.put(KEY_LAT, model.getLatitude());
        values.put(KEY_LNG, model.getLongitude());
        values.put(KEY_LIKE, model.getLike());
        values.put(KEY_VOTE, model.getVote());

        // updating row
        return db.update(TABLE_STOPS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(model.getId()) });
    }

    public void addSurvey(SurveyModel model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ADVICE, model.getAdvice());
        values.put(KEY_STOP, model.getBusStop());
        values.put(KEY_EMAIL, model.getEmail());
        values.put(KEY_LIKE, model.getLikeDegree());

        // Inserting Row
        db.insert(TABLE_SURVEYS, null, values);
        db.close(); // Closing database connection
    }
}
