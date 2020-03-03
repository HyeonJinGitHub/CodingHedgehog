package org.tensorflow.demo.Search;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.tensorflow.demo.R;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import static org.tensorflow.demo.Search.PillListActivity.once;


public class TextSearchActivity extends AppCompatActivity {
    // 4개의 메뉴에 들어갈 Fragment들
    private PillListActivity menu1_list= new PillListActivity();
    String drug_color="";
    String drug_shape="";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_text_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button button1 = (Button)findViewById(R.id.button1);      //검색
        Button button2 = (Button)findViewById(R.id.button2);      //초기화 버튼
        final EditText drug_name = (EditText)findViewById(R.id.drug_name);
        final EditText drug_print = (EditText)findViewById(R.id.drug_print);
        // 첫 화면 지정
        //색깔 버튼 선언
        final ToggleButton Cbtn1 = ((ToggleButton)findViewById(R.id.Cbtn1));
        final ToggleButton Cbtn2 = ((ToggleButton)findViewById(R.id.Cbtn2));
        final ToggleButton Cbtn3 = ((ToggleButton)findViewById(R.id.Cbtn3));
        final ToggleButton Cbtn4 = ((ToggleButton)findViewById(R.id.Cbtn4));
        final ToggleButton Cbtn5 = ((ToggleButton)findViewById(R.id.Cbtn5));
        final ToggleButton Cbtn6 = ((ToggleButton)findViewById(R.id.Cbtn6));
        final ToggleButton Cbtn7 = ((ToggleButton)findViewById(R.id.Cbtn7));
        final ToggleButton Cbtn8 = ((ToggleButton)findViewById(R.id.Cbtn8));
        final ToggleButton Cbtn9 = ((ToggleButton)findViewById(R.id.Cbtn9));
        final ToggleButton Cbtn10 = ((ToggleButton)findViewById(R.id.Cbtn10));
        final ToggleButton Cbtn11 = ((ToggleButton)findViewById(R.id.Cbtn11));
        final ToggleButton Cbtn12 = ((ToggleButton)findViewById(R.id.Cbtn12));
        final ToggleButton Cbtn13 = ((ToggleButton)findViewById(R.id.Cbtn13));
        final ToggleButton Cbtn14 = ((ToggleButton)findViewById(R.id.Cbtn14));
        final ToggleButton Cbtn15 = ((ToggleButton)findViewById(R.id.Cbtn15));
        final ToggleButton Cbtn16 = ((ToggleButton)findViewById(R.id.Cbtn16));
        //모양 버튼 선언
        final ToggleButton Sbtn1 = ((ToggleButton)findViewById(R.id.Sbtn1));
        final ToggleButton Sbtn2 = ((ToggleButton)findViewById(R.id.Sbtn2));
        final ToggleButton Sbtn3 = ((ToggleButton)findViewById(R.id.Sbtn3));
        final ToggleButton Sbtn4 = ((ToggleButton)findViewById(R.id.Sbtn4));
        final ToggleButton Sbtn5 = ((ToggleButton)findViewById(R.id.Sbtn5));
        final ToggleButton Sbtn6 = ((ToggleButton)findViewById(R.id.Sbtn6));
        final ToggleButton Sbtn7 = ((ToggleButton)findViewById(R.id.Sbtn7));
        final ToggleButton Sbtn8 = ((ToggleButton)findViewById(R.id.Sbtn8));
        final ToggleButton Sbtn9 = ((ToggleButton)findViewById(R.id.Sbtn9));
        final ToggleButton Sbtn10 = ((ToggleButton)findViewById(R.id.Sbtn10));


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
                    once = 0;
                String Dname = null;
                try {
                    Dname = URLEncoder.encode(String.valueOf(drug_name.getText()), "UTF-8");
                    String Dprint = URLEncoder.encode(String.valueOf(drug_print.getText()),"UTF-8");
                    String Dcolor = URLEncoder.encode(String.valueOf(drug_color),"UTF-8");
                    String Dshape = URLEncoder.encode(String.valueOf(drug_shape),"UTF-8");
                    //Dcolor+="%2C";
                    Log.i("pill",Dcolor);
                    Log.i("pill",drug_color);
                    String url = "http://dikweb.health.kr/ajax/idfy_info/idfy_info_ajax.asp?drug_name="+Dname+"&drug_print="+Dprint+"&match=include&mark_code=&drug_color="+Dcolor+"&drug_linef=&drug_lineb=&drug_shape="+Dshape+"&drug_form=&drug_shape_etc=&inner_search=print&inner_keyword=&nsearch=npages";
                    String url2 = "http://dikweb.health.kr/ajax/idfy_info/idfy_info_ajax.asp?drug_name="+Dname+"&drug_print="+Dprint+"&match=include&mark_code=&drug_color="+Dcolor+"&drug_linef=&drug_lineb=&drug_shape="+Dshape+"&drug_form=&drug_shape_etc=&inner_search=print&inner_keyword=&";

                    Intent intent = new Intent(getApplicationContext(),PillListActivity.class);

                    intent.putExtra("mparam1",url);
                    intent.putExtra("mparam2",url2);

                    startActivity(intent);
                } catch (UnsupportedEncodingException e) {
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

                drug_shape="";
                drug_color="";

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}

