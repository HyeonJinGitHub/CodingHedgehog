package org.tensorflow.demo.Alarm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import org.tensorflow.demo.Alarm.data.DatabaseHelper;
import org.tensorflow.demo.Alarm.model.Alarm;
import org.tensorflow.demo.Alarm.model.AlarmGroup;
import org.tensorflow.demo.Alarm.service.AlarmReceiver;
import org.tensorflow.demo.Alarm.ui.AddEditAlarmActivity;
import org.tensorflow.demo.Alarm.util.AlarmUtils;
import org.tensorflow.demo.R;

import java.util.List;

/*
import com.github.ppartisan.simplealarms.R;
import com.github.ppartisan.simplealarms.model.Alarm;
import com.github.ppartisan.simplealarms.ui.AddEditAlarmActivity;
import com.github.ppartisan.simplealarms.util.AlarmUtils;
*/

public final class AlarmsAdapter extends RecyclerView.Adapter<AlarmsAdapter.ViewHolder> {

    private List<Alarm> mAlarms;

    private List<AlarmGroup> mAlarmGroup;

    private Context context;
    private String[] mDays;
    private int mAccentColor = -1;
    boolean enable;

    public AlarmsAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context c = parent.getContext();
        final View v = LayoutInflater.from(c).inflate(R.layout.alarm_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Context c = holder.itemView.getContext();

        if(mAccentColor == -1) {
            mAccentColor = ContextCompat.getColor(c, R.color.accent);
        }

        if(mDays == null){
            mDays = c.getResources().getStringArray(R.array.days_abbreviated);
        }

        //final Alarm alarm = mAlarms.get(position);

        final AlarmGroup alarmGroup = mAlarmGroup.get(position);

        List<Alarm> alarms = DatabaseHelper.getInstance(context).getAlarms(alarmGroup.getId());
        Log.i("pill", String.valueOf(alarms.size()));

        String ampm="";
        if(alarmGroup.getCount()>0) {
            for(int i = 0;i<alarmGroup.getCount();i++){
                Log.i("pill","업데이트"+alarmGroup.getCount());
                ampm = toKorean(alarms.get(i).getTime());
                if(i == 0)  holder.time1.setText(ampm+AlarmUtils.getReadableTime(alarms.get(i).getTime()));
                if(i == 1)  holder.time2.setText(ampm+AlarmUtils.getReadableTime(alarms.get(i).getTime()));
                if(i == 2)  holder.time3.setText(ampm+AlarmUtils.getReadableTime(alarms.get(i).getTime()));
                if(i == 3)  holder.time4.setText(ampm+AlarmUtils.getReadableTime(alarms.get(i).getTime()));
                if(i == 4)  holder.time5.setText(ampm+AlarmUtils.getReadableTime(alarms.get(i).getTime()));
            }
            if(alarmGroup.getCount()==1){
                holder.time2.setText(" ");
                holder.time3.setText(" ");
                holder.time4.setText(" ");
                holder.time5.setText(" ");
            }else if(alarmGroup.getCount()==2){
                holder.time3.setText(" ");
                holder.time4.setText(" ");
                holder.time5.setText(" ");
            }else if(alarmGroup.getCount()==3){
                holder.time4.setText(" ");
                holder.time5.setText(" ");
            }else if(alarmGroup.getCount()==4){
                holder.time5.setText(" ");
            }
            if(alarmGroup.getCount()<4) holder.bottom.setVisibility(View.GONE);
            else holder.bottom.setVisibility(View.VISIBLE);

            holder.count.setText(alarmGroup.getCount()+"회");
            holder.label.setText(alarms.get(0).getLabel());
            holder.days.setText(buildSelectedDays(alarms.get(0)));
            enable = alarms.get(0).isEnabled();
        }

        if(enable) {
            holder.icon.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
        }else{
            holder.icon.clearColorFilter();
        }

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enable = alarms.get(0).isEnabled();
                if(enable){     //취소
                    holder.icon.clearColorFilter();
                    for(int i = 0;i<alarmGroup.getCount();i++) {
                        AlarmReceiver.setReminderAlarm(v.getContext(), alarms.get(i));
                        alarms.get(i).setIsEnabled(false);
                    }
                }else{
                    holder.icon.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
                    for(int i = 0;i<alarmGroup.getCount();i++) {
                        AlarmReceiver.cancelReminderAlarm(v.getContext(), alarms.get(i));
                        alarms.get(i).setIsEnabled(true);
                    }
                }
                for(int i = 0;i<alarmGroup.getCount();i++) {
                    DatabaseHelper.getInstance(v.getContext()).updateAlarm(alarms.get(i));
                }
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context c = view.getContext();
                Log.i("pill","onclick"+view.getId());
                final Intent launchEditAlarmIntent =
                        AddEditAlarmActivity.buildAddEditAlarmActivityIntent(
                                c, AddEditAlarmActivity.EDIT_ALARM
                        );
                launchEditAlarmIntent.putExtra(AddEditAlarmActivity.ALARM_EXTRA, alarmGroup);
                c.startActivity(launchEditAlarmIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        //return (mAlarms == null) ? 0 : mAlarms.size();
        //Log.i("pill","groupsize"+mAlarmGroup.size());
        return (mAlarmGroup == null)? 0 : mAlarmGroup.size();
        //return 1;
    }


    private Spannable buildSelectedDays(Alarm alarm) {

        final int numDays = 7;
        final SparseBooleanArray days = alarm.getDays();

        final SpannableStringBuilder builder = new SpannableStringBuilder();
        ForegroundColorSpan span;

        int startIndex, endIndex;
        for (int i = 0; i < numDays; i++) {

            startIndex = builder.length();

            final String dayText = mDays[i];
            builder.append(dayText);
            builder.append(" ");

            endIndex = startIndex + dayText.length();

            final boolean isSelected = days.valueAt(i);
            if(isSelected) {
                span = new ForegroundColorSpan(mAccentColor);
                builder.setSpan(span, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return builder;

    }

    public void setAlarms(List<Alarm> alarms) {
        mAlarms = alarms;
        notifyDataSetChanged();
    }

    public void setAlarmGroup(List<AlarmGroup> alarmGroup) {
        mAlarmGroup = alarmGroup;
        notifyDataSetChanged();
    }
    static final class ViewHolder extends RecyclerView.ViewHolder {

        final TextView time1,time2,time3,time4,time5, label, days;
        final Button count;
        final ImageView icon;
        final LinearLayout bottom;
        ViewHolder(View itemView) {
            super(itemView);

            time1 = itemView.findViewById(R.id.time1);
            time2 = itemView.findViewById(R.id.time2);
            time3 = itemView.findViewById(R.id.time3);
            time4 = itemView.findViewById(R.id.time4);
            time5 = itemView.findViewById(R.id.time5);

            count = itemView.findViewById(R.id.setcount);
            label = itemView.findViewById(R.id.ar_label);
            days = itemView.findViewById(R.id.ar_days);
            icon = itemView.findViewById(R.id.ar_icon);

            bottom = itemView.findViewById(R.id.bottom);
        }
    }
    public String toKorean(long i){
        if(AlarmUtils.getAmPm(i)=="AM")
            return "오전 ";
        else
            return "오후 ";
    }
}
