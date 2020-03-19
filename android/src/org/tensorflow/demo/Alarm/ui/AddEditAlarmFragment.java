package org.tensorflow.demo.Alarm.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.tensorflow.demo.Alarm.data.DatabaseHelper;
import org.tensorflow.demo.Alarm.model.Alarm;
import org.tensorflow.demo.Alarm.model.AlarmGroup;
import org.tensorflow.demo.Alarm.service.AlarmReceiver;
import org.tensorflow.demo.Alarm.service.LoadAlarmsService;
import org.tensorflow.demo.Alarm.util.AlarmUtils;
import org.tensorflow.demo.Alarm.util.ViewUtils;
import org.tensorflow.demo.R;


import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.tensorflow.demo.Alarm.data.DatabaseHelper.COL_MON;
import static org.tensorflow.demo.Alarm.ui.AddEditAlarmFragment.alarmGroup;
import static org.tensorflow.demo.Alarm.ui.AddEditAlarmFragment.time;
import static org.tensorflow.demo.Alarm.ui.AddEditAlarmFragment.items;
import static org.tensorflow.demo.Alarm.ui.AddEditAlarmFragment.pos;
import static org.tensorflow.demo.Alarm.ui.AddEditAlarmFragment.times;
import static org.tensorflow.demo.Alarm.ui.CountList.counts;
import static org.tensorflow.demo.Search.PillListActivity.listCnt;

/*
import com.github.ppartisan.simplealarms.R;
import com.github.ppartisan.simplealarms.data.DatabaseHelper;
import com.github.ppartisan.simplealarms.model.Alarm;
import com.github.ppartisan.simplealarms.service.AlarmReceiver;
import com.github.ppartisan.simplealarms.service.LoadAlarmsService;
import com.github.ppartisan.simplealarms.util.ViewUtils;
*/

public final class AddEditAlarmFragment extends Fragment {

    private TimePicker mTimePicker;
    private EditText mLabel;
    private CheckBox mMon, mTues, mWed, mThurs, mFri, mSat, mSun;
    public static int pos;
    static public ArrayList<String> items = new ArrayList<>();
    static public ArrayList<Calendar> times = new ArrayList<>();
    public static Alarm alarm;
    public static AlarmGroup alarmGroup;
    public static int state;
    public static Calendar time;
    public static List<Alarm> alarms;

    public static AddEditAlarmFragment newInstance(Alarm alarm) {

        Bundle args = new Bundle();
        args.putParcelable(AddEditAlarmActivity.ALARM_EXTRA, alarm);

        AddEditAlarmFragment fragment = new AddEditAlarmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AddEditAlarmFragment newInstance(AlarmGroup alarmGroup,int mode) {

        Bundle args = new Bundle();
        args.putParcelable(AddEditAlarmActivity.ALARM_EXTRA, alarmGroup);
        state = mode;
        AddEditAlarmFragment fragment = new AddEditAlarmFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_add_edit_alarm, container, false);

        setHasOptionsMenu(true);

        //final Alarm alarm = getAlarm();
        alarmGroup = getAlarmGroup();

        times.clear();

        CountList countAdapter = new CountList(getActivity());
        ListView list = (ListView)v.findViewById(R.id.listview);

        list.setAdapter(countAdapter);

        Spinner spinner = (Spinner)v.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),R.array.count_array,android.R.layout.simple_spinner_dropdown_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        alarms = DatabaseHelper.getInstance(getContext()).getAlarms(alarmGroup.getId());
        mLabel = (EditText) v.findViewById(R.id.edit_alarm_label);
        mMon = (CheckBox) v.findViewById(R.id.edit_alarm_mon);
        mTues = (CheckBox) v.findViewById(R.id.edit_alarm_tues);
        mWed = (CheckBox) v.findViewById(R.id.edit_alarm_wed);
        mThurs = (CheckBox) v.findViewById(R.id.edit_alarm_thurs);
        mFri = (CheckBox) v.findViewById(R.id.edit_alarm_fri);
        mSat = (CheckBox) v.findViewById(R.id.edit_alarm_sat);
        mSun = (CheckBox) v.findViewById(R.id.edit_alarm_sun);

        if(alarmGroup.getCount()>0){
            mLabel.setText(alarms.get(0).getLabel());
            setDayCheckboxes(alarms.get(0));
            spinner.setSelection(alarmGroup.getCount()-1);
            for(int i = 0 ;i<alarmGroup.getCount();i++){
                time = Calendar.getInstance();
                time.setTimeInMillis(alarms.get(i).getTime());
                Log.i("pill",time.HOUR_OF_DAY+":"+time.MINUTE);
                Log.i("pill", String.valueOf(time.getTime()));
                times.add(time);
            }
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(parent.getContext(),"횟수:"+parent.getItemAtPosition(position).toString()+"pos"+position,Toast.LENGTH_SHORT).show();
                pos = position+1;
                items.clear();
                for(int i=0;i<position+1;i++) {
                    items.add(counts[position]);
                    Log.i("pill",position+" "+counts[position]);
                }
                countAdapter.notifyDataSetChanged();
            }
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });




        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_alarm_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                save();
                Log.i("pill",times.toString());
                break;
            case R.id.action_delete:
                delete();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    private Alarm getAlarm() {
        return getArguments().getParcelable(AddEditAlarmActivity.ALARM_EXTRA);
    }*/


    private AlarmGroup getAlarmGroup(){
        return getArguments().getParcelable(AddEditAlarmActivity.ALARM_EXTRA);
    }

    private void setDayCheckboxes(Alarm alarm) {
        mMon.setChecked(alarm.getDay(Alarm.MON));
        mTues.setChecked(alarm.getDay(Alarm.TUES));
        mWed.setChecked(alarm.getDay(Alarm.WED));
        mThurs.setChecked(alarm.getDay(Alarm.THURS));
        mFri.setChecked(alarm.getDay(Alarm.FRI));
        mSat.setChecked(alarm.getDay(Alarm.SAT));
        mSun.setChecked(alarm.getDay(Alarm.SUN));
    }

    private void save() {
        long group_id = alarmGroup.getId();
        Log.i("pill", "groupid"+String.valueOf(group_id));
        Log.i("pill", String.valueOf(alarmGroup.getCount())+" "+String.valueOf(pos));

        if(alarmGroup.getCount() == pos) {
            alarmGroup.setCount(pos);
            for (int i = 0; i < pos; i++) {
                Log.i("pill", "save");
                Log.i("pill", String.valueOf(state));
                //이부분 수정
                if (state == 1) {
                    alarm = alarms.get(i);
                }
                if (state == 2) {
                    final long id = DatabaseHelper.getInstance(getActivity()).addAlarm();
                    LoadAlarmsService.launchLoadAlarmsService(getActivity());
                    alarm = new Alarm(id);
                }
        /*
        final Calendar time = Calendar.getInstance();
        time.set(Calendar.MINUTE, ViewUtils.getTimePickerMinute(mTimePicker));
        time.set(Calendar.HOUR_OF_DAY, ViewUtils.getTimePickerHour(mTimePicker));
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);

        alarm.setTime(time.getTimeInMillis());
*/
                alarm.setTime(times.get(i).getTimeInMillis());

                Log.i("pill", AlarmUtils.getReadableTime(times.get(i).getTimeInMillis()));

                alarm.setLabel(mLabel.getText().toString());

                alarm.setDay(Alarm.MON, mMon.isChecked());
                alarm.setDay(Alarm.TUES, mTues.isChecked());
                alarm.setDay(Alarm.WED, mWed.isChecked());
                alarm.setDay(Alarm.THURS, mThurs.isChecked());
                alarm.setDay(Alarm.FRI, mFri.isChecked());
                alarm.setDay(Alarm.SAT, mSat.isChecked());
                alarm.setDay(Alarm.SUN, mSun.isChecked());

                alarm.setGroup_id(group_id);

                final int rowsUpdated = DatabaseHelper.getInstance(getContext()).updateAlarm(alarm);
                final int messageId = (rowsUpdated == 1) ? R.string.update_complete : R.string.update_failed;
                Toast.makeText(getContext(), messageId, Toast.LENGTH_SHORT).show();
                AlarmReceiver.setReminderAlarm(getContext(), alarm);
            }
        }else{
            //times.clear();
            //기존 알람 삭제
            for(int j = 0;j<alarmGroup.getCount();j++){
                AlarmReceiver.cancelReminderAlarm(getContext(), alarms.get(j));
                DatabaseHelper.getInstance(getContext()).deleteAlarm(alarms.get(j));
            }
            alarmGroup.setCount(pos);

            //아예 새로 만들어줌
            for (int i = 0; i < pos; i++) {
                final long id = DatabaseHelper.getInstance(getActivity()).addAlarm();
                LoadAlarmsService.launchLoadAlarmsService(getActivity());
                alarm = new Alarm(id);


                alarm.setTime(times.get(i).getTimeInMillis());

                Log.i("pill", AlarmUtils.getReadableTime(times.get(i).getTimeInMillis()));

                alarm.setLabel(mLabel.getText().toString());

                alarm.setDay(Alarm.MON, mMon.isChecked());
                alarm.setDay(Alarm.TUES, mTues.isChecked());
                alarm.setDay(Alarm.WED, mWed.isChecked());
                alarm.setDay(Alarm.THURS, mThurs.isChecked());
                alarm.setDay(Alarm.FRI, mFri.isChecked());
                alarm.setDay(Alarm.SAT, mSat.isChecked());
                alarm.setDay(Alarm.SUN, mSun.isChecked());

                alarm.setGroup_id(group_id);

                final int rowsUpdated = DatabaseHelper.getInstance(getContext()).updateAlarm(alarm);
                final int messageId = (rowsUpdated == 1) ? R.string.update_complete : R.string.update_failed;
                Toast.makeText(getContext(), messageId, Toast.LENGTH_SHORT).show();
                AlarmReceiver.setReminderAlarm(getContext(), alarm);
            }
        }
        final int growsUpdated = DatabaseHelper.getInstance(getContext()).updateAlarmGroup(alarmGroup);
        final int gmessageId = (growsUpdated == 1) ? R.string.update_complete : R.string.update_failed;

        //Toast.makeText(getContext(), gmessageId, Toast.LENGTH_SHORT).show();
        Log.i("pill","addedit"+alarmGroup.getCount());

        getActivity().finish();

    }

    private void delete() {

        //final Alarm alarm = getAlarm();

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext(), R.style.DeleteAlarmDialogTheme);
        builder.setTitle(R.string.delete_dialog_title);
        builder.setMessage(R.string.delete_dialog_content);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int growsDeleted = DatabaseHelper.getInstance(getContext()).deleteAlarmGroup(alarmGroup);
                //Cancel any pending notifications for this alarm
                for(int j = 0;j < alarmGroup.getCount();j++){
                    AlarmReceiver.cancelReminderAlarm(getContext(), alarms.get(j));

                    final int rowsDeleted = DatabaseHelper.getInstance(getContext()).deleteAlarm(alarms.get(j));
                    Log.i("pill",String.valueOf("id "+alarms.get(j).getId()));
                    int messageId;
                    if(rowsDeleted == 1) {
                        messageId = R.string.delete_complete;
                        Toast.makeText(getContext(), messageId, Toast.LENGTH_SHORT).show();
                        LoadAlarmsService.launchLoadAlarmsService(getContext());
                        getActivity().finish();
                    } else {
                        messageId = R.string.delete_failed;
                        Toast.makeText(getContext(), messageId, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        builder.setNegativeButton(R.string.no, null);
        builder.show();

    }

}

class CountList extends ArrayAdapter<String>{
    private final Activity context;
    public CountList(Activity context ) {
        super(context, R.layout.count_list_custom);
        this.context = context;
    }
    public int getCount(){
        return pos;
    }
    public static String[] counts={
            "1회",
            "2회",
            "3회",
            "4회",
            "5회"
    };
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.i("pill","getView넘어감");
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.count_list_custom, null);
        TextView count = rowView.findViewById(R.id.count);
        //TextView time = rowView.findViewById(R.id.time);

        //count.setText(counts[position]);
        count.setText(counts[position]);

        Button timeBtn = (Button)rowView.findViewById(R.id.time);
        DateFormat format = new SimpleDateFormat("H:mm");
        if(position<times.size()) {
            timeBtn.setText(format.format(times.get(position).getTime()));
        }
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c= Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeBtn.setText(hourOfDay+":"+minute);
                        time = Calendar.getInstance();
                        time.set(Calendar.MINUTE, minute);
                        time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        time.set(Calendar.SECOND, 0);
                        time.set(Calendar.MILLISECOND, 0);
                        if(times.size()<alarmGroup.getCount()) {
                            times.remove(position);
                        }
                        times.add(position,time);
                    }
                },mHour,mMinute,false);
                timePickerDialog.show();
            }
        });

        return rowView;
    }
}