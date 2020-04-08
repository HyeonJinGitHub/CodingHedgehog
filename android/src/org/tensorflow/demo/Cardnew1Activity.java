package org.tensorflow.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Adapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class Cardnew1Activity extends AppCompatActivity {
   SliderAdapter adapter;
    ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardnews1);

        Intent intent = getIntent();
        int num = intent.getIntExtra("number", 1);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        adapter = new SliderAdapter(getApplicationContext(), num);
        viewPager.setAdapter(adapter);
    }
}
