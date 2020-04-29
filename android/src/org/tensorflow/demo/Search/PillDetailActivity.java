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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static org.tensorflow.demo.Search.PillDetailActivity.drug_code;


import org.tensorflow.demo.Alarm.data.DatabaseHelper;
import org.tensorflow.demo.Alarm.model.AlarmGroup;
import org.tensorflow.demo.Alarm.service.LoadAlarmsService;
import org.tensorflow.demo.Alarm.ui.AddEditAlarmActivity;
import org.tensorflow.demo.Alarm.ui.AddEditAlarmFragment;
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
        Button btn1,btn2,btn3,btn4,btn5;
    // TODO: Rename and change types of parameters
        public static String mParam1;
        private String mParam2;

        public static String drug_code;
        public static String drug_name;
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

            btn1 = (Button)findViewById(R.id.button1);
            btn2 = (Button)findViewById(R.id.button2);
            btn3 = (Button)findViewById(R.id.button3);
            btn4 = (Button)findViewById(R.id.button4);
            btn5 = (Button)findViewById(R.id.button5);

            TextView drug_name = (TextView)findViewById(R.id.drug_name);

            /*
            //TextView text = (TextView)v.findViewById(R.id.textView);
            TextView drug_name = (TextView)findViewById(R.id.drug_name);
            TextView upso_name_kfda = (TextView)findViewById(R.id.upso_name_kfda);
            TextView cls_name = (TextView)findViewById(R.id.cls_name);
            TextView item_ingr_type = (TextView)findViewById(R.id.item_ingr_type);
            TextView charact = (TextView)findViewById(R.id.charact);
            TextView sunb = (TextView)findViewById(R.id.sunb);
            TextView effect = (TextView)findViewById(R.id.effecttㅑ);
            TextView dosage = (TextView)findViewById(R.id.dosage);
            TextView caution = (TextView)findViewById(R.id.caution);
            TextView mediguide = (TextView)findViewById(R.id.mediguide);
            TextView stmt = (TextView)findViewById(R.id.stmt);
*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                new DownloadDetail().execute();
            }
            try {
                sleep(600);    //600이였음
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
            btn5.setOnClickListener(movePageListener);
            btn5.setTag(4);
            DetailFragment1 fragment1 = new DetailFragment1();
            adapter.addItem(fragment1);
            DetailFragment2 fragment2 = new DetailFragment2();
            adapter.addItem(fragment2);
            DetailFragment3 fragment3 = new DetailFragment3();
            adapter.addItem(fragment3);
            DetailFragment4 fragment4 = new DetailFragment4();
            adapter.addItem(fragment4);
            DetailFragment5 fragment5 = new DetailFragment5();
            adapter.addItem(fragment5);

            pager.setAdapter(adapter);

            PillDetail pillDetail =  DownloadDetail.getPillDetail();

            drug_name.setText(pillDetail.getDrug_name());

            Bundle bundle1 = new Bundle(5);
            bundle1.putString("upso_name_kfda",pillDetail.getUpso_name_kfda());
            bundle1.putString("cls_name",pillDetail.getCls_name());
            bundle1.putString("item_ingr_type",pillDetail.getItem_ingr_type());
            bundle1.putString("charact",pillDetail.getCharact());
            bundle1.putString("sunb",pillDetail.getSunb());
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

            Bundle bundle5 =new Bundle(1);
            bundle5.putString("mediguide",pillDetail.getMediguide());
            fragment5.setArguments(bundle5);
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
                    btn5.setTextColor(Color.BLACK);
                    break;
                case R.id.button2:
                    btn2.setTextColor(Color.parseColor("#0191D8"));
                    btn1.setTextColor(Color.BLACK);
                    btn3.setTextColor(Color.BLACK);
                    btn4.setTextColor(Color.BLACK);
                    btn5.setTextColor(Color.BLACK);
                    break;
                case R.id.button3:
                    btn3.setTextColor(Color.parseColor("#0191D8"));
                    btn2.setTextColor(Color.BLACK);
                    btn1.setTextColor(Color.BLACK);
                    btn4.setTextColor(Color.BLACK);
                    btn5.setTextColor(Color.BLACK);
                    break;
                case R.id.button4:
                    btn4.setTextColor(Color.parseColor("#0191D8"));
                    btn2.setTextColor(Color.BLACK);
                    btn3.setTextColor(Color.BLACK);
                    btn1.setTextColor(Color.BLACK);
                    btn5.setTextColor(Color.BLACK);
                    break;
                case R.id.button5:
                    btn5.setTextColor(Color.parseColor("#0191D8"));
                    btn2.setTextColor(Color.BLACK);
                    btn3.setTextColor(Color.BLACK);
                    btn4.setTextColor(Color.BLACK);
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


        /**
         * This interface must be implemented by activities that contain this
         * fragment to allow an interaction in this fragment to be communicated
         * to the activity and potentially other fragments contained in that
         * activity.
         * <p>
         * See the Android Training lesson <a href=
         * "http://developer.android.com/training/basics/fragments/communicating.html"
         * >Communicating with Other Fragments</a> for more information.
         */
        public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            void onFragmentInteraction(Uri uri);
        }
    }

    class DownloadDetail extends AsyncTask<String,String,String> {
        //데이터를 저장하게 되는 리스트
        static public ArrayList<Pill> list = new ArrayList<>();
        static public PillDetail pillDetail = new PillDetail();

        public static PillDetail getPillDetail() {
            return pillDetail;
        }

        public static ArrayList<Pill> getList() {
            //Log.i("pill","데이터"+list.get(0).getRnum());
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
                getData();
            } catch (MalformedURLException e) {
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
        private void getData() throws MalformedURLException {
            Log.i("pill","url입력됨");
            String str="";
            String line="";
            // Pill pill = new Pill();

            URL url = new URL("http://dikweb.health.kr/ajax/drug_info/drug_info_ajax.asp?nsearch=ndetail&drug_code="+ drug_code);

            Log.i("pill","http://dikweb.health.kr/ajax/drug_info/drug_info_ajax.asp?nsearch=ndetail&drug_code="+ drug_code);
            try {
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                Log.i("pill","http연결");
                while((line = rd.readLine()) != null){
                    str += line;
                }

            } catch (IOException e) {
                Log.i("pill","http연결실패");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            str = unicodeConvert(str);
            //str = str.replace("[","");
            //str = str.replace("]","");
            str = str.replace("{","");
            str = str.replace("}","");
            str = str.replace("\\n","");
            str = str.replace("\\","");
            str = str.replace("<br/>","\r\n");
            str = str.replace("</br>", "");
            str = str.replace("<P></P>  ","\r\n");
            str = str.replace("<P></P>","\r\n");

            Log.i("pill",str);

            String[] array = str.split("\",\"");
            Log.i("string", String.valueOf(array.length));
            int start;
            Log.i("pill length", String.valueOf(array.length));

            for(int j=0;j<array.length;j++) {
                //pillDetail pillDetail = new pillDetail();
                if ((start = array[j].indexOf("drug_name")) > -1) {
                    pillDetail.setDrug_name(array[j].substring(start + 12, array[j].length()));
                } if ((start = array[j].indexOf("drug_enm")) > -1) {
                    pillDetail.setDrug_enm(array[j].substring(start + 11, array[j].length()));
                }if ((start = array[j].indexOf("upso_name_kfda")) > -1) {
                    pillDetail.setUpso_name_kfda(array[j].substring(start + 17, array[j].length()));
                }if ((start = array[j].indexOf("cls_name")) > -1) {
                    pillDetail.setCls_name(array[j].substring(start + 11, array[j].length()));
                }if ((start = array[j].indexOf("item_ingr_type")) > -1) {
                    pillDetail.setItem_ingr_type(array[j].substring(start + 17, array[j].length() ));
                }  if ((start = array[j].indexOf("charact")) > -1) {
                    pillDetail.setCharact(array[j].substring(start + 10, array[j].length()));
                }if ((start = array[j].indexOf("sunb")) > -1) {
                    pillDetail.setSunb(array[j].substring(start + 7, array[j].length()));
                }if ((start = array[j].indexOf("effect")) > -1) {
                    pillDetail.setEffect(array[j].substring(start + 9, array[j].length()));
                }if ((start = array[j].indexOf("dosage")) > -1) {
                    pillDetail.setDosage (array[j].substring(start + 9, array[j].length()));
                }if ((start = array[j].indexOf("caution")) > -1) {
                    pillDetail.setCaution(array[j].substring(start + 10, array[j].length()));
                }if ((start = array[j].indexOf("mediguide")) > -1) {
                    pillDetail.setMediguide (array[j].substring(start + 12, array[j].length()));
                }if ((start = array[j].indexOf("stmt")) > -1) {
                    pillDetail.setStmt(array[j].substring(start + 7, array[j].length()));
                }
            }
            //Log.i("pill",pillDetail.getDrug_name());


        }

    }

