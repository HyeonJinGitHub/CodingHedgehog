package org.tensorflow.demo.bookmark;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import org.tensorflow.demo.bookmark.BookmarkUtils;

import java.util.List;

public final class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bookmarks.db";
    private static final int SCHEMA = 1;

    private static final String TABLE_NAME = "bookmark";

    public static final String _ID = "_id";
    public static final String DRUG_CODE = "code";
    public static final String DRUG_NAME = "name";
    public static final String STATE = "state";


    private static Database sInstance = null;

    public static synchronized Database getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Database(context.getApplicationContext());
        }
        return sInstance;
    }

    private Database(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.i(getClass().getSimpleName(), "Creating database...");

        final String CREATE_BOOKMARK_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DRUG_CODE + " TEXT, "+
                DRUG_NAME + " TEXT, "+
                STATE + " INTEGER "+
                ");";

        sqLiteDatabase.execSQL(CREATE_BOOKMARK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        throw new UnsupportedOperationException("This shouldn't happen yet!");
    }

    public long addBookmark() {
        return addBookmark(new Bookmark());
    }

    long addBookmark(Bookmark bookmark) {
        return getWritableDatabase().insert(TABLE_NAME, null, BookmarkUtils.toContentValues(bookmark));
    }

    public int updateBookmark(Bookmark bookmark) {
        final String where = _ID + "=?";
        final String[] whereArgs = new String[]{Long.toString(bookmark.getId())};
        return getWritableDatabase()
                .update(TABLE_NAME, BookmarkUtils.toContentValues(bookmark), where, whereArgs);
    }
    /*
    public int updateAlarm(Alarm alarm) {
        final String where = _ID + "=?";
        final String[] whereArgs = new String[] { Long.toString(alarm.getId()) };
        return getWritableDatabase()
                .update(TABLE_NAME, AlarmUtils.toContentValues(alarm), where, whereArgs);
    }*/

    /*public int deleteBookmark(Bookmark bookmark) {
        return deleteBookmark(bookmark.getId());
    }

    int deleteBookmark(long id) {
        final String where = _ID + "=?";
        final String[] whereArgs = new String[] { Long.toString(id) };
        return getWritableDatabase().delete(TABLE_NAME, where, whereArgs);
    }

     */

    public int deleteBookmark(String code) {
        final String where = DRUG_CODE + "=?";
        final String[] whereArgs = new String[] { code };
        return getWritableDatabase().delete(TABLE_NAME, where, whereArgs);
    }

    public List<Bookmark> getBookmarks() {
        Cursor c = null;

        try{
            c = getReadableDatabase().query(TABLE_NAME, null, null, null, null, null, null);
            return BookmarkUtils.buildBookmarkList(c);
        } finally {
            if (c != null && !c.isClosed()) c.close();
        }
    }
    public int readBookmark(String code) {
        final String where = DRUG_CODE+ "=";
        String read = "SELECT * FROM "+TABLE_NAME+" WHERE "+where+"\""+code+"\"";
        Cursor c = getReadableDatabase().rawQuery(read,null);
        int state = 0;
        while(c.moveToNext()){
            state =  c.getInt(3);
        }
        return state;
    }



}
