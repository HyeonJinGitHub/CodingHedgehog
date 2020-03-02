package org.tensorflow.demo.Search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.tensorflow.demo.DetectorActivity;
import org.tensorflow.demo.R;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

public class Menu1Fragment extends Fragment{
    // 4개의 메뉴에 들어갈 Fragment들
    private PillListActivity menu1_list= new PillListActivity();
    String drug_color="";
    String drug_shape="";
    public Menu1Fragment() throws MalformedURLException {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_menu1, container, false);
        ImageButton button1 = (ImageButton)v.findViewById(R.id.button1);
        ImageButton button2 = (ImageButton)v.findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // call data from web URL
                Intent intent = new Intent(getActivity(), TextSearchActivity.class);
                startActivity(intent);

            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), DetectorActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }




}

