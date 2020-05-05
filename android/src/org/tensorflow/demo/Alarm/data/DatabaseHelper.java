package org.tensorflow.demo.Alarm.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import org.tensorflow.demo.Alarm.model.Alarm;
import org.tensorflow.demo.Alarm.model.AlarmGroup;
import org.tensorflow.demo.Alarm.util.AlarmUtils;

import java.util.List;

import static java.nio.file.attribute.AclEntryType.ALARM;

/*
import com.github.ppartisan.simplealarms.model.Alarm;
import com.github.ppartisan.simplealarms.util.AlarmUtils;
*/

public final class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "alarms.db";
    private static final int SCHEMA = 1;

    private static final String TABLE_NAME = "alarms";

    private static final String GROUP_NAME = "alarmGroup";

    public static final String _ID = "_id";
    public static final String COL_TIME = "time";
    public static final String COL_LABEL = "label";
    public static final String COL_MON = "mon";
    public static final String COL_TUES = "tues";
    public static final String COL_WED = "wed";
    public static final String COL_THURS = "thurs";
    public static final String COL_FRI = "fri";
    public static final String COL_SAT = "sat";
    public static final String COL_SUN = "sun";
    public static final String COL_IS_ENABLED = "is_enabled";

    public static String COUNT="count";
    public static final String GROUP_ID = "group_id";

    private static DatabaseHelper sInstance = null;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.i(getClass().getSimpleName(), "Creating database...");

        final String CREATE_ALARMS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TIME + " INTEGER NOT NULL, " +
                COL_LABEL + " TEXT, " +
                COL_MON + " INTEGER NOT NULL, " +
                COL_TUES + " INTEGER NOT NULL, " +
                COL_WED + " INTEGER NOT NULL, " +
                COL_THURS + " INTEGER NOT NULL, " +
                COL_FRI + " INTEGER NOT NULL, " +
                COL_SAT + " INTEGER NOT NULL, " +
                COL_SUN + " INTEGER NOT NULL, " +
                GROUP_ID + " INTEGER NOT NULL, "+
                COL_IS_ENABLED + " INTEGER NOT NULL" +
                ");";

        sqLiteDatabase.execSQL(CREATE_ALARMS_TABLE);

        final String CREATE_GROUP_TABLE = "CREATE TABLE " + GROUP_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COUNT + " INTEGER NOT NULL "+
                ");";

        sqLiteDatabase.execSQL(CREATE_GROUP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        throw new UnsupportedOperationException("This shouldn't happen yet!");
    }

    public long addAlarm() {
        return addAlarm(new Alarm());
    }

    public long addAlarmGroup() {return addAlarmGroup(new AlarmGroup());}

    long addAlarm(Alarm alarm) {
        Log.i("pill","addalarm");
        return getWritableDatabase().insert(TABLE_NAME, null, AlarmUtils.toContentValues(alarm));
    }

    long addAlarmGroup(AlarmGroup alarmGroup) {
        return getWritableDatabase().insert(GROUP_NAME, null, AlarmUtils.toContentValues(alarmGroup));
    }
    public int updateAlarm(Alarm alarm) {
        final String where = _ID + "=?";
        final String[] whereArgs = new String[] { Long.toString(alarm.getId()) };
        return getWritableDatabase()
                .update(TABLE_NAME, AlarmUtils.toContentValues(alarm), where, whereArgs);
    }

    public int updateAlarmGroup(AlarmGroup alarmGroup) {
        final String where = _ID + "=?";
        final String[] whereArgs = new String[] { Long.toString(alarmGroup.getId()) };
        return getWritableDatabase()
                .update(GROUP_NAME, AlarmUtils.toContentValues(alarmGroup), where, whereArgs);
    }
    public int deleteAlarm(Alarm alarm) {
        return deleteAlarm(alarm.getId());
    }

    public int deleteAlarmGroup(AlarmGroup alarmGroup) {
        return deleteAlarmGroup(alarmGroup.getId());
    }

    int deleteAlarm(long id) {
        final String where = _ID + "=?";
        final String[] whereArgs = new String[] { Long.toString(id) };
        return getWritableDatabase().delete(TABLE_NAME, where, whereArgs);
    }

    int deleteAlarmGroup(long id) {
        final String where = _ID + "=?";
        final String[] whereArgs = new String[] { Long.toString(id) };
        return getWritableDatabase().delete(GROUP_NAME, where, whereArgs);
    }

    public List<Alarm> getAlarms() {

        Cursor c = null;

        try{
            c = getReadableDatabase().query(TABLE_NAME, null, null, null, null, null, null);
            return AlarmUtils.buildAlarmList(c);
        } finally {
            if (c != null && !c.isClosed()) c.close();
        }
    }

    public List<Alarm> getAlarms(long group_id) {

        Cursor c = null;

        try{
            //c = getReadableDatabase().query(TABLE_NAME, null, null, null, null, null, null);
            c = getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE GROUP_ID = "+group_id+";",null);
            return AlarmUtils.buildAlarmList(c);
        } finally {
            if (c != null && !c.isClosed()) c.close();
        }
    }

    public List<AlarmGroup> getAlarmGroup() {

        Cursor c = null;

        try{
            c = getReadableDatabase().query(GROUP_NAME, null, null, null, null, null, null);
            return AlarmUtils.buildAlarmGroupList(c);
        } finally {
            if (c != null && !c.isClosed()) c.close();
        }

    }

}
