/**********************************************************************************************************************************************************************
****** AUTO GENERATED FILE BY ANDROID SQLITE HELPER SCRIPT BY FEDERICO PAOLINELLI. ANY CHANGE WILL BE WIPED OUT IF THE SCRIPT IS PROCESSED AGAIN. *******
**********************************************************************************************************************************************************************/
package com.fede;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class DroidContentProvider extends ContentProvider {
    private static final String DATABASE_NAME = "dbFileDb.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TAG = "DroidContentProvider";


    // -------------- URIS ------------

    public static final Uri EVENT_URI = Uri.parse("content://com.fede.dbprovider/event");
    public static final Uri CALL_URI = Uri.parse("content://com.fede.dbprovider/call");


    public static final String ROW_ID = "_id";

    // -------------- EVENT DEFINITIONS ------------

    public static final String EVENT_TABLE = "Event";
    public static final String EVENT_DESCRIPTION_COLUMN = "Description";
    public static final int EVENT_DESCRIPTION_COLUMN_POSITION = 1;
    public static final String EVENT_TIME_COLUMN = "Time";
    public static final int EVENT_TIME_COLUMN_POSITION = 2;
    public static final String EVENT_SHORTDESC_COLUMN = "ShortDesc";
    public static final int EVENT_SHORTDESC_COLUMN_POSITION = 3;
    public static final String EVENT_EVENTTYPE_COLUMN = "EventType";
    public static final int EVENT_EVENTTYPE_COLUMN_POSITION = 4;

    private static final int ALLEVENT= 1;
    private static final int SINGLE_EVENT= 2;



    // -------------- CALL DEFINITIONS ------------

    public static final String CALL_TABLE = "Call";
    public static final String CALL_NUMBER_COLUMN = "Number";
    public static final int CALL_NUMBER_COLUMN_POSITION = 1;
    public static final String CALL_TIME_COLUMN = "Time";
    public static final int CALL_TIME_COLUMN_POSITION = 2;

    private static final int ALLCALL= 3;
    private static final int SINGLE_CALL= 4;



    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.fede.dbprovider", "event", ALLEVENT);
        uriMatcher.addURI("com.fede.dbprovider", "event/#", SINGLE_EVENT);
        uriMatcher.addURI("com.fede.dbprovider", "call", ALLCALL);
        uriMatcher.addURI("com.fede.dbprovider", "call/#", SINGLE_CALL);
    }

    // -------- TABLES CREATION ----------

    // Event CREATION 
    private static final String DATABASE_EVENT_CREATE = "create table " + EVENT_TABLE + " (" + 
				 ROW_ID + " integer primary key autoincrement" + ", " + 
				 EVENT_DESCRIPTION_COLUMN + " text  " + ", " + 
				 EVENT_TIME_COLUMN + " integer  " + ", " + 
				 EVENT_SHORTDESC_COLUMN + " text  " + ", " + 
				 EVENT_EVENTTYPE_COLUMN + " integer  " + ");";


    // Call CREATION 
    private static final String DATABASE_CALL_CREATE = "create table " + CALL_TABLE + " (" + 
				 ROW_ID + " integer primary key autoincrement" + ", " + 
				 CALL_NUMBER_COLUMN + " text  " + ", " + 
				 CALL_TIME_COLUMN + " integer  " + ");";



    private MyDbHelper myOpenHelper;

    @Override
    public boolean onCreate() {
        myOpenHelper = new MyDbHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        return true;
    }

    /**
    * Returns the right table name for the given uri
    * @param uri
    * @return
    */
    private String getTableNameFromUri(Uri uri){
        switch (uriMatcher.match(uri)) {
            case ALLEVENT:
            case SINGLE_EVENT:
                return EVENT_TABLE;
            case ALLCALL:
            case SINGLE_CALL:
                return CALL_TABLE;
            default: break;
        }

           return null;
    }

	/**
    * Returns the parent uri for the given uri
    * @param uri
    * @return
    */
    private Uri getContentUriFromUri(Uri uri){
        switch (uriMatcher.match(uri)) {
            case ALLEVENT:
            case SINGLE_EVENT:
                return EVENT_URI;
            case ALLCALL:
            case SINGLE_CALL:
                return CALL_URI;
            default: break;
        }

        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
        String[] selectionArgs, String sortOrder) {

        // Open thedatabase.
        SQLiteDatabase db;
        try {
            db = myOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = myOpenHelper.getReadableDatabase();
        }

        // Replace these with valid SQL statements if necessary.
        String groupBy = null;
        String having = null;

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // If this is a row query, limit the result set to the passed in row.
        switch (uriMatcher.match(uri)) {
            case SINGLE_EVENT:
            case SINGLE_CALL:
                String rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(ROW_ID + "=" + rowID);
            default: break;
        }

        // Specify the table on which to perform the query. This can
        // be a specific table or a join as required.
        queryBuilder.setTables(getTableNameFromUri(uri));

        // Execute the query.
        Cursor cursor = queryBuilder.query(db, projection, selection,
                    selectionArgs, groupBy, having, sortOrder);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the result Cursor.
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        // Return a string that identifies the MIME type
        // for a Content Provider URI
        switch (uriMatcher.match(uri)) {
            case ALLEVENT:
                return "vnd.android.cursor.dir/vnd.com.fede.event";
            case SINGLE_EVENT:
                return "vnd.android.cursor.dir/vnd.com.fede.event";
            case ALLCALL:
                return "vnd.android.cursor.dir/vnd.com.fede.call";
            case SINGLE_CALL:
                return "vnd.android.cursor.dir/vnd.com.fede.call";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case SINGLE_EVENT:
            case SINGLE_CALL:
                String rowID = uri.getPathSegments().get(1);
                selection = ROW_ID + "=" + rowID + (!TextUtils.isEmpty(selection) ?  " AND (" + selection + ')' : "");
            default: break;
        }


        if (selection == null)
            selection = "1";

        int deleteCount = db.delete(getTableNameFromUri(uri),
                selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return deleteCount;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        String nullColumnHack = null;

        long id = db.insert(getTableNameFromUri(uri), nullColumnHack, values);
        if (id > -1) {
            Uri insertedId = ContentUris.withAppendedId(getContentUriFromUri(uri), id);
                                getContext().getContentResolver().notifyChange(insertedId, null);
            getContext().getContentResolver().notifyChange(insertedId, null);
            return insertedId;
        } else {
            return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        // Open a read / write database to support the transaction.
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();

        // If this is a row URI, limit the deletion to the specified row.
        switch (uriMatcher.match(uri)) {             case SINGLE_EVENT:
            case SINGLE_CALL:
                String rowID = uri.getPathSegments().get(1);
                selection = ROW_ID + "=" + rowID + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
            default: break;
        }

        // Perform the update.
        int updateCount = db.update(getTableNameFromUri(uri), values, selection, selectionArgs);

        // Notify any observers of the change in the data set.
        getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;
    }


    private static class MyDbHelper extends SQLiteOpenHelper {
    
        public MyDbHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // Called when no database exists in disk and the helper class needs
        // to create a new one. 
        @Override
        public void onCreate(SQLiteDatabase db) {      
            db.execSQL(DATABASE_EVENT_CREATE);
			db.execSQL(DATABASE_CALL_CREATE);
			
        }

        // Called when there is a database version mismatch meaning that the version
        // of the database on disk needs to be upgraded to the current version.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Log the version upgrade.
            Log.w(TAG, "Upgrading from version " + 
                        oldVersion + " to " +
                        newVersion + ", which will destroy all old data");
            
            // Upgrade the existing database to conform to the new version. Multiple 
            // previous versions can be handled by comparing _oldVersion and _newVersion
            // values.

            // The simplest case is to drop the old table and create a new one.
            db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE + ";");
			db.execSQL("DROP TABLE IF EXISTS " + CALL_TABLE + ";");
			
            // Create a new one.
            onCreate(db);
        }
    }
     
    /** Dummy object to allow class to compile */
}