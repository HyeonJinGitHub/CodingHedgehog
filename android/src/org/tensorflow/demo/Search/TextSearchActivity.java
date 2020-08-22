package org.tensorflow.demo.Search;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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

import com.google.gson.JsonObject;

import org.tensorflow.demo.PillDetailVO;
import org.tensorflow.demo.PillParsing;
import org.tensorflow.demo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static org.tensorflow.demo.Search.PillDetailActivity.drug_code;
import static org.tensorflow.demo.Search.PillListActivity.once;


public class TextSearchActivity extends AppCompatActivity {
    // 4개의 메뉴에 들어갈 Fragment들
    private PillListActivity menu1_list= new PillListActivity();
    String drug_color="";
    String drug_shape="";
    String get_data = "";


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
                    drug_shape = drug_shape.replace(btn.getText()+",",""); // 원형 - 타원형 수정
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
                try {
                    if(drug_shape.length()>0){
                        drug_shape = drug_shape.substring(0, drug_shape.length()-1);
                    }
                    if(drug_color.length()>0){
                        drug_color = drug_color.substring(0, drug_color.length()-1);
                    }

                    Log.i("drug_name : ", drug_name.getText().toString());
                    Log.i("drug_print : ", drug_print.getText().toString());
                    Log.i("drug_shape : ", drug_shape);
                    Log.i("drug_color : ", drug_color);

                    String[] data = new String[4];
                    data[0] = drug_name.getText().toString();
                    data[1] = drug_print.getText().toString();
                    data[2] = drug_shape;
                    data[3] = drug_color;

                    get_data = new DownloadText().execute(data).get();
                    Log.i("get_data : ", get_data);

                    // 초기화
                    drug_color = drug_color + ",";
                    drug_shape = drug_shape + ",";

                    Intent intent = new Intent(getApplicationContext(),PillListActivity.class);
                    intent.putExtra("mparam1", get_data);
                    startActivity(intent);

                } catch (Exception e) {
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

class DownloadText extends AsyncTask<String,String,String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String get_data = "";
        try {
             get_data = getData(strings[0], strings[1], strings[2], strings[3]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return get_data;
    }

    protected void onPostExecute(String result) {
    }

    private String getData(String drug_name, String searchText, String shape, String color) throws IOException {
        HttpURLConnection con = null;
        String get_data = "";

        URL url = new URL("http://ec2-18-221-12-38.us-east-2.compute.amazonaws.com:3000/textpage");
        // origin : ec2-3-128-160-84.us-east-2.compute.amazonaws.com
        // new : ec2-18-221-12-38.us-east-2.compute.amazonaws.com
        con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);
        con.setRequestMethod("POST");
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        con.connect();

        OutputStream os = con.getOutputStream();
        JsonObject json = new JsonObject();

        json.addProperty("drug_name", drug_name);
        json.addProperty("searchText", searchText);
        json.addProperty("shape", shape);
        json.addProperty("color", color);
        os.write(json.toString().getBytes());

        Log.i(TAG, "write drugName : " + drug_name);

        os.flush(); // 업로드 끝
        os.close(); // 닫기

        int responseCode = con.getResponseCode();
        StringBuffer response = new StringBuffer(); // 받아온 데이터

        Log.i(TAG, "응답코드 : " + responseCode + " 응답메세지 : " + con.getResponseMessage());

        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Log.i(TAG, "받은 데이터 : " + response);
            // 서버로 부터 받아온 알약 데이터값
            if ( response.toString().length() > 0) // 에러 발생하면 "" return
                get_data = response.toString();
        }
        return get_data;
    }
}