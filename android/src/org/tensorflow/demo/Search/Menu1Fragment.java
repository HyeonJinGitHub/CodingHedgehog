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
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.tensorflow.demo.DetectorActivity;
import org.tensorflow.demo.R;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
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
        Button button1 = (Button)v.findViewById(R.id.button1);      //검색
        Button button2 = (Button)v.findViewById(R.id.button2);      //초기화 버튼
        final EditText drug_name = (EditText)v.findViewById(R.id.drug_name);
        final EditText drug_print = (EditText)v.findViewById(R.id.drug_print);
        // 첫 화면 지정
        //색깔 버튼 선언
        final ToggleButton Cbtn1 = ((ToggleButton)v.findViewById(R.id.Cbtn1));
        final ToggleButton Cbtn2 = ((ToggleButton)v.findViewById(R.id.Cbtn2));
        final ToggleButton Cbtn3 = ((ToggleButton)v.findViewById(R.id.Cbtn3));
        final ToggleButton Cbtn4 = ((ToggleButton)v.findViewById(R.id.Cbtn4));
        final ToggleButton Cbtn5 = ((ToggleButton)v.findViewById(R.id.Cbtn5));
        final ToggleButton Cbtn6 = ((ToggleButton)v.findViewById(R.id.Cbtn6));
        final ToggleButton Cbtn7 = ((ToggleButton)v.findViewById(R.id.Cbtn7));
        final ToggleButton Cbtn8 = ((ToggleButton)v.findViewById(R.id.Cbtn8));
        final ToggleButton Cbtn9 = ((ToggleButton)v.findViewById(R.id.Cbtn9));
        final ToggleButton Cbtn10 = ((ToggleButton)v.findViewById(R.id.Cbtn10));
        final ToggleButton Cbtn11 = ((ToggleButton)v.findViewById(R.id.Cbtn11));
        final ToggleButton Cbtn12 = ((ToggleButton)v.findViewById(R.id.Cbtn12));
        final ToggleButton Cbtn13 = ((ToggleButton)v.findViewById(R.id.Cbtn13));
        final ToggleButton Cbtn14 = ((ToggleButton)v.findViewById(R.id.Cbtn14));
        final ToggleButton Cbtn15 = ((ToggleButton)v.findViewById(R.id.Cbtn15));
        final ToggleButton Cbtn16 = ((ToggleButton)v.findViewById(R.id.Cbtn16));
        //모양 버튼 선언
        final ToggleButton Sbtn1 = ((ToggleButton)v.findViewById(R.id.Cbtn1));
        final ToggleButton Sbtn2 = ((ToggleButton)v.findViewById(R.id.Cbtn2));
        final ToggleButton Sbtn3 = ((ToggleButton)v.findViewById(R.id.Cbtn3));
        final ToggleButton Sbtn4 = ((ToggleButton)v.findViewById(R.id.Cbtn4));
        final ToggleButton Sbtn5 = ((ToggleButton)v.findViewById(R.id.Cbtn5));
        final ToggleButton Sbtn6 = ((ToggleButton)v.findViewById(R.id.Cbtn6));
        final ToggleButton Sbtn7 = ((ToggleButton)v.findViewById(R.id.Cbtn7));
        final ToggleButton Sbtn8 = ((ToggleButton)v.findViewById(R.id.Cbtn8));
        final ToggleButton Sbtn9 = ((ToggleButton)v.findViewById(R.id.Cbtn9));
        final ToggleButton Sbtn10 = ((ToggleButton)v.findViewById(R.id.Cbtn10));


        ToggleButton.OnClickListener onClickColorListener = new ToggleButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleButton btn = (ToggleButton) v.findViewById(v.getId());
                if(btn.isChecked()) {
                    if (drug_color.indexOf(String.valueOf(btn.getText())) < 0) {
                        drug_color = drug_color + btn.getText() + ",";
                    }
                }else{
                    drug_color = drug_color.replace(btn.getText()+",","");
                }
                Log.i("pill", drug_color);

            }
        };
        boolean check = ((ToggleButton)v.findViewById(R.id.Cbtn1)).isChecked();
        String str = (String)((ToggleButton)v.findViewById(R.id.Cbtn1)).getText();
        Log.i("pill", String.valueOf(check));
        Log.i("pill",str);
        Cbtn1.setOnClickListener(onClickColorListener);
        Cbtn2.setOnClickListener(onClickColorListener);
        Cbtn3.setOnClickListener(onClickColorListener);
        Cbtn4.setOnClickListener(onClickColorListener);
        Cbtn5.setOnClickListener(onClickColorListener);
        Cbtn6.setOnClickListener(onClickColorListener);
        Cbtn7.setOnClickListener(onClickColorListener);
        Cbtn8.setOnClickListener(onClickColorListener);
        Cbtn9.setOnClickListener(onClickColorListener);
        Cbtn10.setOnClickListener(onClickColorListener);
        Cbtn11.setOnClickListener(onClickColorListener);
        Cbtn12.setOnClickListener(onClickColorListener);
        Cbtn13.setOnClickListener(onClickColorListener);
        Cbtn14.setOnClickListener(onClickColorListener);
        Cbtn15.setOnClickListener(onClickColorListener);
        Cbtn16.setOnClickListener(onClickColorListener);

        ToggleButton.OnClickListener onClickShapeListener = new ToggleButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleButton btn = (ToggleButton)v.findViewById(v.getId());
                if(btn.isChecked()) {
                    if (drug_shape.indexOf(String.valueOf(btn.getText())) < 0) {
                        drug_shape = drug_shape + btn.getText() + ",";
                    }
                }else{
                    drug_shape = drug_shape.replace(btn.getText()+",","");
                }
                Log.i("pill",drug_shape);
            }
        };

        Sbtn1.setOnClickListener(onClickShapeListener);
        Sbtn2.setOnClickListener(onClickShapeListener);
        Sbtn3.setOnClickListener(onClickShapeListener);
        Sbtn4.setOnClickListener(onClickShapeListener);
        Sbtn5.setOnClickListener(onClickShapeListener);
        Sbtn6.setOnClickListener(onClickShapeListener);
        Sbtn7.setOnClickListener(onClickShapeListener);
        Sbtn8.setOnClickListener(onClickShapeListener);
        Sbtn9.setOnClickListener(onClickShapeListener);
        Sbtn10.setOnClickListener(onClickShapeListener);

        button1.setOnClickListener(new View.OnClickListener(){
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

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cbtn1.setChecked(false);
                Cbtn2.setChecked(false);
                Cbtn3.setChecked(false);
                Cbtn4.setChecked(false);
                Cbtn5.setChecked(false);
                Cbtn6.setChecked(false);
                Cbtn7.setChecked(false);
                Cbtn8.setChecked(false);
                Cbtn9.setChecked(false);
                Cbtn10.setChecked(false);
                Cbtn11.setChecked(false);
                Cbtn12.setChecked(false);
                Cbtn13.setChecked(false);
                Cbtn14.setChecked(false);
                Cbtn15.setChecked(false);
                Cbtn16.setChecked(false);

                Sbtn1.setChecked(false);
                Sbtn2.setChecked(false);
                Sbtn3.setChecked(false);
                Sbtn4.setChecked(false);
                Sbtn5.setChecked(false);
                Sbtn6.setChecked(false);
                Sbtn7.setChecked(false);
                Sbtn8.setChecked(false);
                Sbtn9.setChecked(false);
                Sbtn10.setChecked(false);

                drug_name.setText("");
                drug_print.setText("");

            }
        });
        return v;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}

