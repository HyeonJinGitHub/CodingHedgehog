package org.tensorflow.demo.Alarm.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.tensorflow.demo.Alarm.model.Alarm;
import org.tensorflow.demo.Alarm.model.AlarmGroup;

import java.util.ArrayList;

public final class LoadAlarmGroupReceiver extends BroadcastReceiver {

    private OnAlarmGroupLoadedListener gListener;

    @SuppressWarnings("unused")
    public LoadAlarmGroupReceiver(){}

    public LoadAlarmGroupReceiver(OnAlarmGroupLoadedListener listener){
        gListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final ArrayList<AlarmGroup> alarmGroup =
                intent.getParcelableArrayListExtra(LoadAlarmsService.GROUP_EXTRA);
        gListener.onAlarmGroupLoaded(alarmGroup);
    }


    public void setOnAlarmGroupLoadedListener(OnAlarmGroupLoadedListener listener) {
        gListener = listener;
    }


    public interface OnAlarmGroupLoadedListener {
        void onAlarmGroupLoaded(ArrayList<AlarmGroup> alarmGroup);
    }
}