package org.tensorflow.demo.Alarm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AlarmGroup implements Parcelable{
    private AlarmGroup(Parcel in) {
        id = in.readLong();
        count = in.readInt();
    }

    public static final Parcelable.Creator<AlarmGroup> CREATOR = new Parcelable.Creator<AlarmGroup>() {
        @Override
        public AlarmGroup createFromParcel(Parcel in) {
            return new AlarmGroup(in);
        }

        @Override
        public AlarmGroup[] newArray(int size) {
            return new AlarmGroup[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeInt(count);
    }

    private long id;
    private int count;

    public AlarmGroup(){ }

    public AlarmGroup(long id) { this.id = id;}

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount(){
        return count;
    }


}
