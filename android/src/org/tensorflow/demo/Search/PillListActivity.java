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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.tensorflow.demo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;
import static java.lang.Thread.sleep;
import static org.tensorflow.demo.Search.DownloadList.list;
import static org.tensorflow.demo.Search.DownloadList.totCnt;
import static org.tensorflow.demo.Search.PillListActivity.listCnt;
import static org.tensorflow.demo.Search.PillListActivity.once;


class Pill{
    //String rnum;
    //String idx;
    String drug_code;
    String imgidfy_code;
    String print_front;
    String print_back;
    String drug_name;
    String upso_name_kfda;

    public String getDrug_code() {
        return drug_code;
    }
    public String getDrug_name(){
        return drug_name;
    }
    public String getPrint_front(){
        return print_front;
    }
    public String getPrint_back(){
        return print_back;
    }
    public String getUpso_name_kfda(){
        return upso_name_kfda;
    }
    public String getImgidfy_code(){
        return imgidfy_code;
    }
    public void setDrug_code(String drug_code){
        this.drug_code = drug_code;
    }
    public void setDrug_name(String drug_name){
        this.drug_name=drug_name;
    }
    public void setPrint_front(String print_front){
        this.print_front=print_front;
    }
    public void setPrint_back(String print_back){
        this.print_back=print_back;
    }
    public void setUpso_name_kfda(String upso_name_kfda){
        this.upso_name_kfda=upso_name_kfda;
    }
    public void setImgidfy_code(String imgidfy_code){
        this.imgidfy_code = imgidfy_code;
    }
}

public class PillListActivity extends AppCompatActivity {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static int listCnt;
    private TextView selected_item_textview;
    //static List<String> list = new ArrayList<>();

    // TODO: Rename and change types of parameters
    public static String mParam1;
    public static String mParam2;

    public static int once = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_pill_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mParam1 = intent.getExtras().getString("mparam1");
        mParam2 = intent.getExtras().getString("mparam2");

        final ListView listview = (ListView)findViewById(R.id.listView);
        View footer = getLayoutInflater().inflate(R.layout.listview_footer,null,false);
        //리스트뷰와 리스트를 연결하기 위해 사용되는 어댑터
        final PillList adapter = new PillList(this);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            listview.smoothScrollToPosition(0);
        });

        if(once==0){
            list.clear();
            adapter.notifyDataSetChanged();
        }
        listCnt = 10;

        try {
            new DownloadList().execute();
            sleep(900);        //900이였음
        } catch (Exception e) {
            e.printStackTrace();
        }
        //초기화
        if(valueOf(totCnt)>10){
            listCnt = 10;
        }else{
            listCnt = valueOf(totCnt);
        }

        Button moreBtn = (Button)footer.findViewById(R.id.add);
        moreBtn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Log.i("pill","더보기 클릭"+Integer.parseInt(totCnt));
                try {
                    if(Integer.parseInt(totCnt)>10){
                        totCnt= Integer.toString(Integer.parseInt(totCnt)-10);
                        listCnt = listCnt+10;
                        Log.i("pill","totCnt : "+ totCnt);
                        //count = count-10;
                    }else{
                        listCnt = listCnt+valueOf(totCnt);
                        //count=1;
                    }
                    new DownloadList().execute();
                    sleep(900);        //900이였음
                    Log.i("pill", String.valueOf(listCnt));

                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("pill","exception 발생");
                }
            }
        });

        if(valueOf(totCnt)>10) {
            listview.addFooterView(footer);
        }
        //리스트뷰의 어댑터를 지정해준다.
        listview.setAdapter(adapter);

        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {


                Intent intent = new Intent(getApplicationContext(),PillDetailActivity.class);
                intent.putExtra("drug_code",list.get(position).getDrug_code());
                intent.putExtra("drug_name",list.get(position).getDrug_name());
                startActivity(intent);
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

class PillList extends ArrayAdapter<String>{
    private final Activity context;
    public PillList(Activity context ) {
        super(context, R.layout.listiem);
        Log.i("pill","ok");
        //super(context, R.layout.listiem, download.getList());
        this.context = context;
    }
    public int getCount(){
        Log.i("pill", "listcount"+String.valueOf(listCnt));

        return listCnt;
        //list.size();
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.i("pill","getView넘어감");
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.listiem, null);
        ImageView imageView = rowView.findViewById(R.id.image);
        TextView drug_name = rowView.findViewById(R.id.drug_name);
        TextView upso_name = rowView.findViewById(R.id.upso_name);
        TextView print = rowView.findViewById(R.id.print);
        DownloadList download=new DownloadList();
        List<Pill> list = download.getList();
        if(list.size() !=0) {
            drug_name.setText(list.get(position).getDrug_name());
            upso_name.setText(list.get(position).getUpso_name_kfda());
            print.setText(list.get(position).getPrint_front()+"/"+list.get(position).getPrint_back());
            //Glide.with(view).load(list.get(position).getImgidfy_code()).into(imageView);
        }else{
            Log.i("No pill","none");
        }

        return rowView;
    }
}

class DownloadList extends AsyncTask<String,String,String> {
    //데이터를 저장하게 되는 리스트
    static public ArrayList<Pill> list = new ArrayList<>();
    static public String totCnt;
    static public int count;
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
                sb.append((char) parseInt(str.substring(i+2, i+6), 16));
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
        int start;
        int j = 0;

        if(once==0) {
            //Intent로 변경
            URL url = new URL(PillListActivity.mParam1);
            try {
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                Log.i("pill", "http연결");
                while ((line = rd.readLine()) != null) {
                    str += line;
                }

            } catch (IOException e) {
                Log.i("pill", "http연결실패");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            str = unicodeConvert(str);
            str = str.replace("[", "");
            str = str.replace("]", "");
            str = str.replace("{", "");
            str = str.replace("}", "");
            if ((start = str.indexOf("totCnt")) > -1) {
                totCnt = str.substring(start + 8, str.length());
                Log.i("pill", str);
            }
            once = 1;
        }
        count = Integer.parseInt(totCnt)-10;

        //http://dikweb.health.kr/ajax/idfy_info/idfy_info_ajax.asp?drug_name=&drug_print=H&match=include&mark_code=&drug_color=&drug_linef=&drug_lineb=&drug_shape=&drug_form=&drug_shape_etc=&inner_search=print&inner_keyword=&strP=3586&endP=1&nsearch=nsearch
        URL url2 = new URL(PillListActivity.mParam2+"strP="+totCnt+"&endP="+count+"&nsearch=nsearch");
        Log.i("pill", PillListActivity.mParam2+"strP="+totCnt+"&endP="+count +"&nsearch=nsearch");
        try {
            HttpURLConnection con = (HttpURLConnection) url2.openConnection();
            con.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Log.i("pill","http연결");
            str="";
            while((line = rd.readLine()) != null){
                str += line;
            }

        } catch (IOException e) {
            Log.i("pill","http연결실패");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        str = unicodeConvert(str);
        str = str.replace("[","");
        str = str.replace("]","");
        str = str.replace("{","");
        str = str.replace("}","");
        str = str.replace("/","");
        str = str.replace("\\","");
        Log.i("pill","데이터 받아옴");
        Log.i("pill",str);
        start = 0;
        String[] array = str.split(",");
        Log.i("string", String.valueOf(array.length));
        for(int i=0;i<array.length/8;i++) {
            Pill pill = new Pill();
            for(int k=0;k<8;k++) {
                /*if ((start = array[j].indexOf("rnum")) > -1) {
                    pill.setRnum(array[j].substring(start + 6, array[j].length()));
                    Log.i("pill", j + "" + pill.getRnum());
                } if ((start = array[j].indexOf("idx")) > -1) {
                    pill.idx = array[j].substring(start + 5, array[j].length());
                }*/
                if ((start = array[j].indexOf("drug_code")) > -1) {
                    pill.setDrug_code(array[j].substring(start + 12, array[j].length() - 1));
                }if ((start = array[j].indexOf("imgidfy_code")) > -1) {
                    pill.setImgidfy_code(array[j].substring(start + 15, array[j].length() - 1));
                }if ((start = array[j].indexOf("print_front")) > -1) {
                    pill.setPrint_front(array[j].substring(start + 14, array[j].length() - 1));
                }if ((start = array[j].indexOf("print_back")) > -1) {
                    pill.setPrint_back (array[j].substring(start + 13, array[j].length() - 1));
                }if ((start = array[j].indexOf("drug_name")) > -1) {
                    pill.setDrug_name (array[j].substring(start + 12, array[j].length() - 1));
                }if ((start = array[j].indexOf("upso_name_kfda")) > -1) {
                    pill.setUpso_name_kfda(array[j].substring(start + 17, array[j].length() - 1));
                }
                j++;
            }
            //Log.i("pill","데이터 num "+pill.rnum);
            //list.set(i,pill);
            Log.i("pill", "add num" + String.valueOf(listCnt - 10 + i));
            //list.add(listCnt -10 + i, pill);

            if(listCnt < 10) {
                Log.i("pill", "add num" + String.valueOf(listCnt + i));
                list.add(listCnt + i, pill);
            }else{
                Log.i("pill", "add num" + String.valueOf(listCnt - 10 + i));
                list.add(listCnt -10 + i, pill);
            }

        }
        Log.i("pill size", String.valueOf(list.size()));


    }
}

