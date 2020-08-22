package org.tensorflow.demo.Search;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static java.lang.Thread.sleep;
import static org.tensorflow.demo.Search.PillDetailActivity.drug_code;


import org.tensorflow.demo.Alarm.data.DatabaseHelper;
import org.tensorflow.demo.Alarm.model.AlarmGroup;
import org.tensorflow.demo.Alarm.service.LoadAlarmsService;
import org.tensorflow.demo.Alarm.ui.AddEditAlarmActivity;
import org.tensorflow.demo.Alarm.ui.AddEditAlarmFragment;
import org.tensorflow.demo.PillDetailVO;
import org.tensorflow.demo.PillInteractionVO;
import org.tensorflow.demo.PillParsing;
import org.tensorflow.demo.R;
import org.tensorflow.demo.bookmark.Bookmark;
import org.tensorflow.demo.bookmark.Database;

class PillDetail{
    String drug_name;
    String drug_enm;
    String upso_name_kfda;
    String cls_name;
    String item_ingr_type;
    String charact;
    String sunb;
    String effect;
    String dosage;
    String caution;
    String mediguide;
    String stmt;

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }

    public String getDrug_enm() {
        return drug_enm;
    }

    public void setDrug_enm(String drug_enm) {
        this.drug_enm = drug_enm;
    }

    public String getUpso_name_kfda() {
        return upso_name_kfda;
    }

    public void setUpso_name_kfda(String upso_name_kfda) {
        this.upso_name_kfda = upso_name_kfda;
    }

    public String getCls_name() {
        return cls_name;
    }

    public void setCls_name(String cls_name) {
        this.cls_name = cls_name;
    }

    public String getItem_ingr_type() {
        return item_ingr_type;
    }

    public void setItem_ingr_type(String item_ingr_type) {
        this.item_ingr_type = item_ingr_type;
    }

    public String getCharact() {
        return charact;
    }

    public void setCharact(String charact) {
        this.charact = charact;
    }

    public String getSunb() {
        return sunb;
    }

    public void setSunb(String sunb) {
        this.sunb = sunb;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public String getMediguide() {
        return mediguide;
    }

    public void setMediguide(String mediguide) {
        this.mediguide = mediguide;
    }

    public String getStmt() {
        return stmt;
    }

    public void setStmt(String stmt) {
        this.stmt = stmt;
    }
}

public class PillDetailActivity extends AppCompatActivity implements DetailFragment1.OnFragmentInteractionListener, DetailFragment2.OnFragmentInteractionListener,DetailFragment3.OnFragmentInteractionListener,DetailFragment4.OnFragmentInteractionListener,DetailFragment5.OnFragmentInteractionListener{
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";
        Button btn1,btn2,btn3,btn4;
    // TODO: Rename and change types of parameters
        public static String mParam1;
        private String mParam2;

        public static String drug_code;
        public static String drug_name;
        public static String img_code;

        public static String inter_name;
        ViewPager pager;

    //private org.tensorflow.demo.Search.DetailFragment.OnFragmentInteractionListener mListener;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pill_detail);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Intent intent = getIntent();
            drug_code = intent.getStringExtra("drug_code");
            drug_name = intent.getStringExtra("drug_name");
            img_code = intent.getStringExtra("img_code");

            btn1 = (Button)findViewById(R.id.button1);
            btn2 = (Button)findViewById(R.id.button2);
            btn3 = (Button)findViewById(R.id.button3);
            btn4 = (Button)findViewById(R.id.button4);

            TextView drug_name = (TextView)findViewById(R.id.drug_name);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                new DownloadDetail().execute(); // .get()
            }
            try {
                sleep(2000);    //600이였음
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            pager = findViewById(R.id.pager);
            pager.setOffscreenPageLimit(2);
            MoviePagerAdapter adapter = new MoviePagerAdapter(getSupportFragmentManager());
            pager.setCurrentItem(0);

            btn1.setOnClickListener(movePageListener);
            btn1.setTag(0);
            btn2.setOnClickListener(movePageListener);
            btn2.setTag(1);
            btn3.setOnClickListener(movePageListener);
            btn3.setTag(2);
            btn4.setOnClickListener(movePageListener);
            btn4.setTag(3);
            DetailFragment1 fragment1 = new DetailFragment1();
            adapter.addItem(fragment1);
            DetailFragment2 fragment2 = new DetailFragment2();
            adapter.addItem(fragment2);
            DetailFragment3 fragment3 = new DetailFragment3();
            adapter.addItem(fragment3);
            DetailFragment4 fragment4 = new DetailFragment4();
            adapter.addItem(fragment4);

            pager.setAdapter(adapter);

            PillDetailVO pillDetail =  DownloadDetail.getPillDetailVO();
            System.out.println("drug_name: "+pillDetail.getDrug_name());
            drug_name.setText(pillDetail.getDrug_name());

            Bundle bundle1 = new Bundle(4);
            bundle1.putString("img_code",img_code);
            bundle1.putString("mediguide",pillDetail.getMediguide());
            bundle1.putString("picto_img",pillDetail.getPicto_img());
            bundle1.putString("medititle",pillDetail.getMedititle());
            fragment1.setArguments(bundle1);

            Bundle bundle2 =new Bundle(1);
            bundle2.putString("effect",pillDetail.getEffect());
            fragment2.setArguments(bundle2);

            Bundle bundle3 =new Bundle(1);
            bundle3.putString("dosage",pillDetail.getDosage());
            fragment3.setArguments(bundle3);

            Bundle bundle4 =new Bundle(1);
            bundle4.putString("caution",pillDetail.getCaution());
            fragment4.setArguments(bundle4);

            /*
            drug_name.setText("약 이름 : " +pillDetail.getDrug_name());
            upso_name_kfda.setText("제조사 : " + pillDetail.getUpso_name_kfda());
            cls_name.setText("식약처 분류 : " +pillDetail.getCls_name());
            item_ingr_type.setText("약 분류 : "+ pillDetail.getItem_ingr_type());
            charact.setText("charact : "+pillDetail.getCharact());
            sunb.setText("sunb : "+pillDetail.getSunb());
            effect.setText("효능 : " +pillDetail.getEffect());
            dosage.setText("용법 : "+pillDetail.getDosage());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      caution.setText("주의 : "+pillDetail.getCaution());
            mediguide.setText("복약 : " +pillDetail.getMediguide());
            stmt.setText("stmt : "+pillDetail.stmt);
*/
        }
    @Override public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.detail_alarm_menu , menu);
            int result = Database.getInstance(this).readBookmark(drug_code);
            if(result==1){
                menu.findItem(R.id.action_add_star).setIcon(R.drawable.ic_yellow_star_24dp);
            }
            return true;
        }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            case R.id.action_add_star:
                int result = Database.getInstance(this).readBookmark(drug_code);
                Log.i("pill", String.valueOf(result));
                if(result == 0) {
                    long id = Database.getInstance(this).addBookmark();
                    Bookmark bookmark = new Bookmark();
                    bookmark.setId(id);
                    bookmark.setName(drug_name);
                    bookmark.setCode(drug_code);
                    bookmark.setImgidfy_code(img_code);
                    bookmark.setState(1);
                    Database.getInstance(this).updateBookmark(bookmark);
                    item.setIcon(R.drawable.ic_yellow_star_24dp);
                }if(result == 1){
                    Database.getInstance(this).deleteBookmark(drug_code);
                    item.setIcon(R.drawable.ic_star_border_black_24dp);
                    Log.i("pill","delete");

            }
                break;
            case R.id.action_add_alarm:
                Intent intent = new Intent(this, AddEditAlarmActivity.class);
                intent.putExtra("mode_extra", 2);
                intent.putExtra("label",drug_name);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();
            pager.setCurrentItem(tag);
            switch (v.getId()){
                case R.id.button1:
                    btn1.setTextColor(Color.parseColor("#0191D8"));
                    btn2.setTextColor(Color.BLACK);
                    btn3.setTextColor(Color.BLACK);
                    btn4.setTextColor(Color.BLACK);
                    break;
                case R.id.button2:
                    btn2.setTextColor(Color.parseColor("#0191D8"));
                    btn1.setTextColor(Color.BLACK);
                    btn3.setTextColor(Color.BLACK);
                    btn4.setTextColor(Color.BLACK);
                    break;
                case R.id.button3:
                    btn3.setTextColor(Color.parseColor("#0191D8"));
                    btn2.setTextColor(Color.BLACK);
                    btn1.setTextColor(Color.BLACK);
                    btn4.setTextColor(Color.BLACK);
                    break;
                case R.id.button4:
                    btn4.setTextColor(Color.parseColor("#0191D8"));
                    btn2.setTextColor(Color.BLACK);
                    btn3.setTextColor(Color.BLACK);
                    btn1.setTextColor(Color.BLACK);
                    break;


            }
        }
    };


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class MoviePagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> items = new ArrayList<Fragment>();

        public MoviePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment item){
            items.add(item);
        }

        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }

        public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            void onFragmentInteraction(Uri uri);
        }
    }

    class DownloadDetail extends AsyncTask<String,String,String> {
        //데이터를 저장하게 되는 리스트
        static public PillDetailVO pillDetailVO = new PillDetailVO();
        static public ArrayList<PillDetailVO> list = new ArrayList<PillDetailVO>();

        public static PillDetailVO getPillDetailVO() {
            // list.get(0)
            return pillDetailVO;
        }

        public static ArrayList<PillDetailVO> getList() {
            return list;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        /*try {
            getData();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                PillParsing parsing = new PillParsing();
                String json = getData(drug_code);
                if(json != ""){
                    pillDetailVO = parsing.getPillDetail(json); // Array형태의 json Parsing
                    System.out.println("pillDetail(2)" + pillDetailVO.getEffect());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            //mTextView.setText(result);
        }

        private static String unicodeConvert(String str) {
            StringBuilder sb = new StringBuilder();
            char ch;
            int len = str.length();
            for (int i = 0; i < len; i++) {
                ch = str.charAt(i);
                if (ch == '\\' && str.charAt(i+1) == 'u') {
                    sb.append((char) Integer.parseInt(str.substring(i+2, i+6), 16));
                    i+=5;
                    continue;
                }
                sb.append(ch);
            }
            return sb.toString();
        }

        private String getData(String drug_code) throws IOException {
            HttpURLConnection con = null;
            String get_data = "";

            URL url = new URL("http://ec2-18-221-12-38.us-east-2.compute.amazonaws.com:3000/detailpage");
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

            json.addProperty("drug_code", drug_code);
            os.write(json.toString().getBytes());

            Log.i(TAG, "write drugCode : " + drug_code);

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
                if(response.toString().substring(0, 5) != "Error") // 에러 발생하면 "" return
                    get_data = response.toString();
            }
            return get_data;
        }
    }
