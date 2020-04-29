package org.tensorflow.demo.Search;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.tensorflow.demo.Alarm.adapter.AlarmsAdapter;
import org.tensorflow.demo.Alarm.model.Alarm;
import org.tensorflow.demo.Alarm.model.AlarmGroup;
import org.tensorflow.demo.Alarm.service.LoadAlarmGroupReceiver;
import org.tensorflow.demo.Alarm.service.LoadAlarmsReceiver;
import org.tensorflow.demo.Alarm.service.LoadAlarmsService;
import org.tensorflow.demo.Alarm.view.DividerItemDecoration;
import org.tensorflow.demo.Alarm.view.EmptyRecyclerView;
import org.tensorflow.demo.Alarm.util.AlarmUtils;
import org.tensorflow.demo.R;
import org.tensorflow.demo.bookmark.Bookmark;
import org.tensorflow.demo.bookmark.BookmarksAdapter;
import org.tensorflow.demo.bookmark.Database;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static org.tensorflow.demo.Alarm.ui.AddEditAlarmActivity.ADD_ALARM;
import static org.tensorflow.demo.Alarm.ui.AddEditAlarmActivity.buildAddEditAlarmActivityIntent;


public final class Menu4Fragment extends Fragment{

    private BookmarksAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_menu4, container, false);
        final List<Bookmark> bookmark = Database.getInstance(getContext()).getBookmarks();

        final EmptyRecyclerView rv = v.findViewById(R.id.recycler);
        mAdapter = new BookmarksAdapter(getContext());
        mAdapter.setBookmarks(bookmark);
        rv.setEmptyView(v.findViewById(R.id.empty_view));
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new DividerItemDecoration(getContext()));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());
        Thread.currentThread();

        return v;

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override public void onResume(){
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onStop() {
        super.onStop();
    }

    public void onBookmarksLoaded(ArrayList<Bookmark> bookmarks) {
        mAdapter.setBookmarks(bookmarks);
        mAdapter.notifyDataSetChanged();
    }

}

