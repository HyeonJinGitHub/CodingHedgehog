package org.tensorflow.demo.bookmark;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.util.SparseBooleanArray;

import androidx.core.app.ActivityCompat;

import org.tensorflow.demo.Alarm.model.Alarm;
import org.tensorflow.demo.Alarm.model.AlarmGroup;
import org.tensorflow.demo.bookmark.Bookmark;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static org.tensorflow.demo.Alarm.data.DatabaseHelper.COL_FRI;
import static org.tensorflow.demo.Alarm.data.DatabaseHelper.COL_IS_ENABLED;
import static org.tensorflow.demo.Alarm.data.DatabaseHelper.COL_LABEL;
import static org.tensorflow.demo.Alarm.data.DatabaseHelper.COL_MON;
import static org.tensorflow.demo.Alarm.data.DatabaseHelper.COL_SAT;
import static org.tensorflow.demo.Alarm.data.DatabaseHelper.COL_SUN;
import static org.tensorflow.demo.Alarm.data.DatabaseHelper.COL_THURS;
import static org.tensorflow.demo.Alarm.data.DatabaseHelper.COL_TIME;
import static org.tensorflow.demo.Alarm.data.DatabaseHelper.COL_TUES;
import static org.tensorflow.demo.Alarm.data.DatabaseHelper.COL_WED;
import static org.tensorflow.demo.Alarm.data.DatabaseHelper.COUNT;
import static org.tensorflow.demo.Alarm.data.DatabaseHelper.GROUP_ID;
import static org.tensorflow.demo.Alarm.data.DatabaseHelper._ID;
import static org.tensorflow.demo.Alarm.ui.AddEditAlarmFragment.alarm;
import static org.tensorflow.demo.bookmark.Database.DRUG_CODE;
import static org.tensorflow.demo.bookmark.Database.DRUG_NAME;
import static org.tensorflow.demo.bookmark.Database.STATE;


public final class BookmarkUtils {

    private BookmarkUtils() { throw new AssertionError(); }

    //DB에 넣기 전에 내용을 바꿔주는 부분
    public static ContentValues toContentValues(Bookmark bookmark) {

        final ContentValues cv = new ContentValues(3);

        cv.put(DRUG_CODE, bookmark.getCode());
        cv.put(DRUG_NAME, bookmark.getName());
        cv.put(STATE, bookmark.getState());

        return cv;

    }

    //커서로 DB읽어옴
    //리스트에 추가하는 내용
    public static ArrayList<Bookmark> buildBookmarkList(Cursor c) {

        if (c == null) return new ArrayList<>();

        final int size = c.getCount();

        final ArrayList<Bookmark> bookmarks = new ArrayList<>(size);

        if (c.moveToFirst()){
            do {
                final long id = c.getLong(c.getColumnIndex(_ID));
                final String code = c.getString(c.getColumnIndex(DRUG_CODE));
                final String name = c.getString(c.getColumnIndex(DRUG_NAME));

                final Bookmark bookmark = new Bookmark();
                bookmark.setId(id);
                bookmark.setCode(code);
                bookmark.setName(name);

                bookmarks.add(bookmark);
            } while (c.moveToNext());
        }

        return bookmarks;

    }



}
