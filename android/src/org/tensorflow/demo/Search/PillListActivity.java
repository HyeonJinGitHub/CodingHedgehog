package org.tensorflow.demo.Search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.tensorflow.demo.PillListVO;
import org.tensorflow.demo.PillParsing;
import org.tensorflow.demo.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;
import static android.speech.tts.TextToSpeech.QUEUE_ADD;
import static android.speech.tts.TextToSpeech.QUEUE_FLUSH;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;
import static java.lang.Thread.sleep;
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
    //private static final String ARG_PARAM2 = "param2";
    public static int listCnt; // 리스트에 보여주는 알약 개수
    private TextView selected_item_textview;
    private TextToSpeech tts;

    public static ArrayList<PillListVO> list;

    // TODO: Rename and change types of parameters
    public static String mParam1;
    //public static String mParam2;

    public static int once = 0;
    public static int cnt = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_pill_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mParam1 = intent.getExtras().getString("mparam1");
        // mParam2 = intent.getExtras().getString("mparam2");
        once = 0;

        // TTS 초기화
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR)
                    tts.setLanguage(Locale.KOREAN);
            }
        });

        final ListView listview = (ListView)findViewById(R.id.listView);
        View footer = getLayoutInflater().inflate(R.layout.listview_footer,null,false);
        //리스트뷰와 리스트를 연결하기 위해 사용되는 어댑터
        final PillList adapter = new PillList(this);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            listview.smoothScrollToPosition(0);
        });

        if(once==0){
            // list.clear(); // 이 부분 주석 처리함
            adapter.notifyDataSetChanged();
        }
        listCnt = 10;

        try {
            PillParsing pillParsing = new PillParsing();
            if(mParam1.length() > 5) { // 데이터 1개 이상
                list = pillParsing.getPillList(mParam1);
                cnt = list.size();
            }
            else {
                String text = "[알림] 검색 결과가 없습니다.";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
            Log.i("TAG", "count 개수 : " + cnt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //초기화
        if(valueOf(cnt)>10){
            listCnt = 10;
        }else{
            listCnt = valueOf(cnt);
        }

        Button moreBtn = (Button)footer.findViewById(R.id.add);
        moreBtn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Log.i("pill","더보기 클릭"+ cnt);
                try {
                    if(cnt >10){
                        cnt = cnt - 10;
                        listCnt = listCnt+10;
                        Log.i("pill","Cnt : "+ cnt);
                        //count = count-10;
                    }else{
                        listCnt = listCnt+cnt;
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("pill","exception 발생");
                }
            }
        });

        if(valueOf(cnt)>10) {
            listview.addFooterView(footer);
        }

        String text = "[알림] 검색결과가 10개이상입니다.\n 정확한 알약을 선택해주세요";
        if(cnt>10) {
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
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
                intent.putExtra("img_code",list.get(position).getImgidfy_code());

                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed(){
        // list 초기화
        list.clear();
        listCnt = 0;
        cnt = 0;
        super.onBackPressed();
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
    HashMap<String, Bitmap> mMap = new HashMap<String,Bitmap>();

    public PillList(Activity context) {
        super(context, R.layout.listiem);
        this.context = context;
    }
    public int getCount(){
        Log.i("pill", "listcount"+String.valueOf(listCnt));

        return listCnt;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.i("pill","getView넘어감");
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.listiem, null);
        ImageView imageView = rowView.findViewById(R.id.imageView);
        TextView drug_name = rowView.findViewById(R.id.drug_name);
        TextView print = rowView.findViewById(R.id.print);
        ArrayList<PillListVO> list = PillListActivity.list;

        if(list.size() !=0) {
            loadImage("http://www.pharm.or.kr/images/sb_photo/small/" + list.get(position).getImgidfy_code() + "_s.jpg", imageView);
            drug_name.setText(list.get(position).getDrug_name());
            print.setText(list.get(position).getPrint_front());

        }else{
            Log.i("No pill","none");
        }

        return rowView;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(final Message msg) {
        }
    };

    private void loadImage(final String url, final ImageView imageview){
        Bitmap bitmap = mMap.get(url);
        if(bitmap == null){
            Thread t = new Thread(){
                public void run(){
                    getBitmap(url);
                    handler.post(new Runnable(){
                        @Override
                        public void run() {
                            imageview.setImageBitmap(mMap.get(url));
                        }
                    });
                }
            };
            t.start();
        }else{
            imageview.setImageBitmap(bitmap);
        }
    }

    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(new FlushedInputStream(is));
            mMap.put(url, bm);
            bis.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return bm;
    }

    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break; // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }
}

