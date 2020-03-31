package org.tensorflow.demo.bookmark;

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
import org.tensorflow.demo.Search.PillDetailActivity;

import java.util.List;


public final class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder> {

    private List<Bookmark> mBookmarks;

    private Context context;
    private String[] mDays;
    private int mAccentColor = -1;

    public BookmarksAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context c = parent.getContext();
        final View v = LayoutInflater.from(c).inflate(R.layout.bookmark_row, parent, false);

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

        final Bookmark bookmark = mBookmarks.get(position);

        Log.i("pill",bookmark.getName());
        holder.drug_name.setText(bookmark.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context c = view.getContext();
                Intent intent = new Intent(view.getContext(), PillDetailActivity.class);
                intent.putExtra("drug_code",bookmark.getCode());
                intent.putExtra("drug_name",bookmark.getName());
                c.startActivity(intent);


            }
        });

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.getInstance(v.getContext()).deleteBookmark(bookmark.getCode());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (mBookmarks == null)? 0 : mBookmarks.size();
    }


    public void setBookmarks(List<Bookmark> bookmarks) {
        mBookmarks = bookmarks;
        notifyDataSetChanged();
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

        final TextView drug_name;
        final ImageView delete_btn;
        ViewHolder(View itemView) {
            super(itemView);

            drug_name= itemView.findViewById(R.id.drug_name);
            delete_btn = itemView.findViewById(R.id.delete_btn);
        }
    }

}
