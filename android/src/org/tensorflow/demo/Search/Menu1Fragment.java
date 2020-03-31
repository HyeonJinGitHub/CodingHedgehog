package org.tensorflow.demo.Search;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.ContentView;
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

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                // builder.setMessage("중앙에 맞춰 알약 앞면과 뒷면을 한번씩 촬영해주세요.");

                View view2 = inflater.inflate(R.layout.custom_dialog, container, false);
                TextView text = (TextView) view2.findViewById(R.id.textview);
                ImageView image = (ImageView)view2.findViewById(R.id.imageview);
                text.setText("중앙에 맞춰 알약 앞면과 뒷면을 한번씩 촬영해주세요.");
                image.setImageResource(R.drawable.dialog);

                builder.setView(view2);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), DetectorActivity.class);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
        return v;
    }




}

