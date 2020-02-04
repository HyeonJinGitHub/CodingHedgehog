package com.example.pharm;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Menu1Fragment extends Fragment implements menu1_list.OnFragmentInteractionListener {
    // 4개의 메뉴에 들어갈 Fragment들
    private menu1_list menu1_list= new menu1_list();
    String drug_color="";
    String drug_shape="";
    public Menu1Fragment() throws MalformedURLException {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_menu1, container, false);
        Button button2 = (Button)v.findViewById(R.id.button2);
        final EditText drug_name = (EditText)v.findViewById(R.id.drug_name);
        final EditText drug_print = (EditText)v.findViewById(R.id.drug_print);
        // 첫 화면 지정

        Button.OnClickListener onClickColorListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v.findViewById(v.getId());
                if(drug_color.indexOf(String.valueOf(btn.getText())) < 0){
                    drug_color = drug_color+btn.getText()+",";
                }
            }
        };
        ((Button)v.findViewById(R.id.Cbtn1)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn2)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn3)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn4)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn5)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn6)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn7)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn8)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn9)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn10)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn11)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn12)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn13)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn14)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn15)).setOnClickListener(onClickColorListener);
        ((Button)v.findViewById(R.id.Cbtn16)).setOnClickListener(onClickColorListener);

        Button.OnClickListener onClickShapeListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v.findViewById(v.getId());
                if(drug_shape.indexOf(String.valueOf(btn.getText())) < 0){
                    drug_shape = drug_shape+btn.getText()+",";
                }
            }
        };
        ((Button)v.findViewById(R.id.Sbtn1)).setOnClickListener(onClickShapeListener);
        ((Button)v.findViewById(R.id.Sbtn2)).setOnClickListener(onClickShapeListener);
        ((Button)v.findViewById(R.id.Sbtn3)).setOnClickListener(onClickShapeListener);
        ((Button)v.findViewById(R.id.Sbtn4)).setOnClickListener(onClickShapeListener);
        ((Button)v.findViewById(R.id.Sbtn5)).setOnClickListener(onClickShapeListener);
        ((Button)v.findViewById(R.id.Sbtn6)).setOnClickListener(onClickShapeListener);
        ((Button)v.findViewById(R.id.Sbtn7)).setOnClickListener(onClickShapeListener);
        ((Button)v.findViewById(R.id.Sbtn8)).setOnClickListener(onClickShapeListener);
        ((Button)v.findViewById(R.id.Sbtn9)).setOnClickListener(onClickShapeListener);
        ((Button)v.findViewById(R.id.Sbtn10)).setOnClickListener(onClickShapeListener);
        ((Button)v.findViewById(R.id.Sbtn11)).setOnClickListener(onClickShapeListener);

        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // call data from web URL
                try {
                    String Dname = URLEncoder.encode(String.valueOf(drug_name.getText()), "UTF-8");
                    String Dprint = URLEncoder.encode(String.valueOf(drug_print.getText()),"UTF-8");
                    String Dcolor = URLEncoder.encode(String.valueOf(drug_color),"UTF-8");
                    String Dshape = URLEncoder.encode(String.valueOf(drug_shape),"UTF-8");
                    //Dcolor+="%2C";
                    Log.i("pill",Dcolor);
                    Log.i("pill",drug_color);
                    String url = "http://dikweb.health.kr/ajax/idfy_info/idfy_info_ajax.asp?drug_name="+Dname+"&drug_print="+Dprint+"&match=include&mark_code=&drug_color="+Dcolor+"&drug_linef=&drug_lineb=&drug_shape="+Dshape+"&drug_form=&drug_shape_etc=&inner_search=print&inner_keyword=&nsearch=npages";
                    String url2 = "http://dikweb.health.kr/ajax/idfy_info/idfy_info_ajax.asp?drug_name="+Dname+"&drug_print="+Dprint+"&match=include&mark_code=&drug_color="+Dcolor+"&drug_linef=&drug_lineb=&drug_shape="+Dshape+"&drug_form=&drug_shape_etc=&inner_search=print&inner_keyword=&";
                    ((MainActivity)getActivity()).replaceFragment(menu1_list.newInstance(url,url2));
                    //((MainActivity)getActivity()).replaceFragment(menu1_list.newInstance());
                } catch (MalformedURLException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        return v;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}

