package org.tensorflow.demo.Search;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.tensorflow.demo.Cardnew1Activity;
import org.tensorflow.demo.DetectorActivity;
import org.tensorflow.demo.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu2Fragment extends Fragment {
    String[] Q_list;
    String[] A_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_menu2, container, false);

        ImageButton c1 = (ImageButton)v.findViewById(R.id.cardnews1);
        ImageButton c2 = (ImageButton)v.findViewById(R.id.cardnews2);
        ImageButton c3 = (ImageButton)v.findViewById(R.id.cardnews3);
        ImageButton c4 = (ImageButton)v.findViewById(R.id.cardnews4);
        ImageButton c5 = (ImageButton)v.findViewById(R.id.cardnews5);
        ImageButton c6 = (ImageButton)v.findViewById(R.id.cardnews6);

        Intent intent = new Intent(getActivity(), Cardnew1Activity.class);

        ImageButton.OnClickListener onClickListener = new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch(v.getId()){

                    case R.id.cardnews1:
                        intent.putExtra("number", 1);
                        startActivity(intent);
                        break;
                    case R.id.cardnews2:
                        intent.putExtra("number", 2);
                        startActivity(intent);
                        break;
                    case R.id.cardnews3:
                        intent.putExtra("number", 3);
                        startActivity(intent);
                        break;
                    case R.id.cardnews4:
                        intent.putExtra("number", 4);
                        startActivity(intent);
                        break;
                    case R.id.cardnews5:
                        intent.putExtra("number", 5);
                        startActivity(intent);
                        break;
                    case R.id.cardnews6:
                        intent.putExtra("number", 6);
                        startActivity(intent);
                        break;
                }
            }
        };
        c1.setOnClickListener(onClickListener); c2.setOnClickListener(onClickListener);
        c3.setOnClickListener(onClickListener); c4.setOnClickListener(onClickListener);
        c5.setOnClickListener(onClickListener); c6.setOnClickListener(onClickListener);

        return v;
    }
}


