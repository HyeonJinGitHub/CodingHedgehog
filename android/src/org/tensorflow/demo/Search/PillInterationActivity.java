package org.tensorflow.demo.Search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.demo.PillInteractionVO;
import org.tensorflow.demo.PillListVO;
import org.tensorflow.demo.R;
import org.tensorflow.demo.bookmark.Bookmark;
import org.tensorflow.demo.bookmark.BookmarkUtils;
import org.tensorflow.demo.bookmark.Database;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class PillInterationActivity extends AppCompatActivity {

    public static ArrayList<PillInteractionVO> inter_list = new ArrayList<PillInteractionVO>();
    TextToSpeech tts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_interaction);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        inter_list = (ArrayList<PillInteractionVO>) intent.getSerializableExtra("inter_list");

        ListView listView = (ListView)findViewById(R.id.listView);
        PillAdapter adapter = new PillAdapter(this);

        //리스트뷰의 어댑터를 지정해준다.
        listView.setAdapter(adapter);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });

        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                String text = inter_list.get(position).getDrug_name1() + "과" + inter_list.get(position).getDrug_name2() + "을 함께 복용하시면 "
                        + inter_list.get(position).getEffect() + "의 위험이 있습니다.";
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }
    @Override
    public void onBackPressed(){
        tts.stop();
        tts.shutdown();
        inter_list.clear();
        super.onBackPressed();
}
}

class PillAdapter extends ArrayAdapter<String> {
    private final Activity context;
    ArrayList<PillInteractionVO> list = PillInterationActivity.inter_list;
    HashMap<String, Bitmap> mMap = new HashMap<String,Bitmap>();


    public PillAdapter(Activity context) {
        super(context, R.layout.list_inter_item);
        this.context = context;
    }
    public int getCount(){
        return list.size();
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_inter_item, null);

        TextView drug_name1 = rowView.findViewById(R.id.drug_name1);
        TextView drug_effect = rowView.findViewById(R.id.drug_effect);
        ImageView drug_image1 = rowView.findViewById(R.id.drug_image1);
        ImageView drug_image2 = rowView.findViewById(R.id.drug_image2);

        if(list.size() !=0) {
            String name1 = list.get(position).getDrug_name1();
            String name2 = list.get(position).getDrug_name2();
            /*
            if(name1.indexOf("(") > 0)
                name1 = name1.substring(0, name1.indexOf("("));
            if(name2.indexOf("(") > 0)
                name2 = name2.substring(0, name2.indexOf("("));

            PillInterationActivity.inter_list.get(position).setDrug_name1(name1);
            PillInterationActivity.inter_list.get(position).setDrug_name2(name2);
            */
            System.out.println("name1 : " + name1 + "name2 : " + name2);
            drug_name1.setText(name1 + " & " + name2);
            drug_effect.setText(list.get(position).getEffect());

            String img_code1 = Database.getInstance(getContext()).readBookmarkImage(name1);
            String img_code2 = Database.getInstance(getContext()).readBookmarkImage(name2);

            System.out.println("img_code1 : " + img_code1 + "img_code2 : " + img_code2);

            loadImage("http://www.pharm.or.kr/images/sb_photo/small/" + img_code1 + "_s.jpg", drug_image1);
            loadImage("http://www.pharm.or.kr/images/sb_photo/small/" + img_code2 + "_s.jpg", drug_image2);
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