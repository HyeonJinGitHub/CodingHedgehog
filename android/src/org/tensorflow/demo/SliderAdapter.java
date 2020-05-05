package org.tensorflow.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    private int[][] images = {
            {R.drawable.c1_1, R.drawable.c1_2, R.drawable.c1_3, R.drawable.c1_4, R.drawable.c1_5, R.drawable.c1_6,
                    R.drawable.c1_7, R.drawable.c1_8 ,R.drawable.c1_9, R.drawable.c1_10},
            {R.drawable.c2_1, R.drawable.c2_2, R.drawable.c2_3, R.drawable.c2_4},
            {R.drawable.c3_1, R.drawable.c3_2, R.drawable.c3_4, R.drawable.c3_5, R.drawable.c3_6, R.drawable.c3_7, R.drawable.c3_8},
            {R.drawable.c4_1, R.drawable.c4_2, R.drawable.c4_3,  R.drawable.c4_4,  R.drawable.c4_5},
            {R.drawable.c5_1, R.drawable.c5_2, R.drawable.c5_3},
            {R.drawable.c6_1, R.drawable.c6_2, R.drawable.c6_3}};

    private LayoutInflater inflater;
    private Context context;
    private int num; // 몇번째 카드뉴스인지

    public SliderAdapter(Context context, int num){
        this.context = context;
        this.num = num;
    }

    @Override
    public int getCount() {
        return images[num-1].length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View)object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageview);
        imageView.setImageResource(images[num-1][position]);
        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // super.destroyItem(container, position, object);
    }
}
