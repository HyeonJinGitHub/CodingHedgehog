package org.tensorflow.demo.Alarm.ui;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static org.tensorflow.demo.Alarm.ui.AddEditAlarmActivity.ADD_ALARM;
import static org.tensorflow.demo.Alarm.ui.AddEditAlarmActivity.buildAddEditAlarmActivityIntent;


public final class MainFragment extends Fragment
        implements LoadAlarmsReceiver.OnAlarmsLoadedListener, LoadAlarmGroupReceiver.OnAlarmGroupLoadedListener {

    private LoadAlarmsReceiver mReceiver;
    private LoadAlarmGroupReceiver gReceiver;

    private AlarmsAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceiver = new LoadAlarmsReceiver(this);
        gReceiver = new LoadAlarmGroupReceiver(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_menu3, container, false);

        final EmptyRecyclerView rv = v.findViewById(R.id.recycler);
        mAdapter = new AlarmsAdapter(getContext());
        rv.setEmptyView(v.findViewById(R.id.empty_view));
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new DividerItemDecoration(getContext()));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());

        final FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlarmUtils.checkAlarmPermissions(getActivity());
            final Intent i = buildAddEditAlarmActivityIntent(getContext(), ADD_ALARM);
            startActivity(i);
        });

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        final IntentFilter filter = new IntentFilter(LoadAlarmsService.ACTION_COMPLETE);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, filter);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(gReceiver, filter);
        LoadAlarmsService.launchLoadAlarmsService(getContext());
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(gReceiver);
    }


    @Override
    public void onAlarmsLoaded(ArrayList<Alarm> alarms) {
        mAdapter.setAlarms(alarms);
    }

    public void onAlarmGroupLoaded(ArrayList<AlarmGroup> alarmGroups) {
        mAdapter.setAlarmGroup(alarmGroups);
        mAdapter.notifyDataSetChanged();

    }

}

