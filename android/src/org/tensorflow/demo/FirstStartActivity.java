package org.tensorflow.demo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class FirstStartActivity extends Activity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fisrt_start);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapterClass(getApplicationContext()));
    }
    // 닫기 버튼 시, 다시 보이지 않기위한 처리
    private View.OnClickListener mCloseButtonClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int count = 1;
            SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("count", count);
            editor.commit();
            Toast.makeText(getApplicationContext(), "저장", Toast.LENGTH_LONG).show();
            finish();
        }
    };


    // Pager Adapter 정의
    private class PagerAdapterClass extends androidx.viewpager.widget.PagerAdapter{
        private LayoutInflater inflater;
        public PagerAdapterClass(Context c) {
            super();
            inflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return 5; // 페이지 수 설정
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View v = null;

            // 페이지에 따른 view 설정
            if(position == 0){
                v = inflater.inflate(R.layout.activity_first_help1, null);
                v.findViewById(R.id.help1);
            }
            else if(position == 1){
                v = inflater.inflate(R.layout.activity_first_help2, null);
                v.findViewById(R.id.help2);
            }
            else if(position == 2){
                v = inflater.inflate(R.layout.activity_first_help3, null);
                v.findViewById(R.id.help3);
            }
            else if(position == 3){
                v = inflater.inflate(R.layout.activity_first_help4, null);
                v.findViewById(R.id.help4);
            }
            else{
                v = inflater.inflate(R.layout.activity_first_help5, null);
                v.findViewById(R.id.help5);
                v.findViewById(R.id.close_btn).setOnClickListener(mCloseButtonClick);
            }

            ((ViewPager)viewPager).addView(v, 0);
            return v;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ((ViewPager)viewPager).removeView((View)object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == (View)object;
        }
    }
}

