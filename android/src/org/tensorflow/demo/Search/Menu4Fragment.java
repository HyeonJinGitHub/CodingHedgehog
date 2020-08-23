package org.tensorflow.demo.Search;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.tensorflow.demo.Alarm.adapter.AlarmsAdapter;
import org.tensorflow.demo.Alarm.model.Alarm;
import org.tensorflow.demo.Alarm.model.AlarmGroup;
import org.tensorflow.demo.Alarm.service.LoadAlarmGroupReceiver;
import org.tensorflow.demo.Alarm.service.LoadAlarmsReceiver;
import org.tensorflow.demo.Alarm.service.LoadAlarmsService;
import org.tensorflow.demo.Alarm.view.DividerItemDecoration;
import org.tensorflow.demo.Alarm.view.EmptyRecyclerView;
import org.tensorflow.demo.Alarm.util.AlarmUtils;
import org.tensorflow.demo.PillInteractionVO;
import org.tensorflow.demo.PillParsing;
import org.tensorflow.demo.R;
import org.tensorflow.demo.bookmark.Bookmark;
import org.tensorflow.demo.bookmark.BookmarksAdapter;
import org.tensorflow.demo.bookmark.Database;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static org.tensorflow.demo.Alarm.ui.AddEditAlarmActivity.ADD_ALARM;
import static org.tensorflow.demo.Alarm.ui.AddEditAlarmActivity.buildAddEditAlarmActivityIntent;
import static org.tensorflow.demo.Search.PillDetailActivity.drug_code;


public final class Menu4Fragment extends Fragment{

    private BookmarksAdapter mAdapter;
    public String drug_list = "";
    private ProgressDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        dialog = new ProgressDialog(getActivity());
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_menu4, container, false);
        final List<Bookmark> bookmark = Database.getInstance(getActivity()).getBookmarks();

        for(int i = 0; i <bookmark.size(); i++){
            drug_list += bookmark.get(i).getName() + ",";
        }
        if(drug_list.length() > 0)
            drug_list = drug_list.substring(0, drug_list.length() - 1); // 마지막은 쉼표 제거

        Button button = (Button)v.findViewById(R.id.inter_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("상호작용을 검색중입니다.");
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                final Thread t = new Thread(){
                    public void run(){
                        ArrayList<PillInteractionVO> inter_list = new ArrayList<PillInteractionVO>();
                        try {
                            inter_list = new DownloadInteraction().execute(drug_list).get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for(int i = 0; i < inter_list.size(); i++){
                            System.out.println("drug_name1 : " + inter_list.get(i).getDrug_name1());
                            System.out.println("drug_name2 : " + inter_list.get(i).getDrug_name2());
                            System.out.println("effect : " + inter_list.get(i).getEffect());
                        }

                        Intent intent = new Intent(getActivity(), PillInterationActivity.class);
                        intent.putExtra("inter_list", inter_list);
                        startActivity(intent);

                        if(dialog != null)
                            dialog.dismiss();
                        drug_list = ""; // 초기화 필수!!
                    }
                };
                t.start();
            }
        });

        final EmptyRecyclerView rv = v.findViewById(R.id.recycler);
        mAdapter = new BookmarksAdapter(getContext());
        mAdapter.setBookmarks(bookmark);
        rv.setEmptyView(v.findViewById(R.id.empty_view));
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new DividerItemDecoration(getContext()));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());
        Thread.currentThread();

        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override public void onResume(){
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onStop() {
        super.onStop();
    }

    public void onBookmarksLoaded(ArrayList<Bookmark> bookmarks) {
        mAdapter.setBookmarks(bookmarks);
        mAdapter.notifyDataSetChanged();
    }

}


// 상호작용
class DownloadInteraction extends AsyncTask<String,String,ArrayList<PillInteractionVO>> {
    //데이터를 저장하게 되는 리스트
    static public PillInteractionVO pillInteractionVO = new PillInteractionVO();
    static public ArrayList<PillInteractionVO> inter_list = new ArrayList<PillInteractionVO>();
    public static ArrayList<PillInteractionVO> getInterList(){
        return inter_list;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<PillInteractionVO> doInBackground(String... strings) {
        try {
            String drug_list = strings[0];
            String json = getData(drug_list);
            Log.i(TAG, "Json : " + json);
            PillParsing parsing = new PillParsing();
            if (json != "") {
                inter_list = parsing.getPillInteraction(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inter_list;
    }

    @Override
    protected void onPostExecute(ArrayList<PillInteractionVO> result) {
        super.onPostExecute(result);
    }


    private String getData(String drug_name) throws IOException {
        HttpURLConnection con = null;
        String get_data = "";

        URL url = new URL("http://ec2-18-221-12-38.us-east-2.compute.amazonaws.com:3000/interactionpage");
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
        os.write(json.toString().getBytes());

        Log.i(TAG, "write drugCode : " + drug_name);

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
            if (response.toString().length() > 5) // 에러 발생하면 "" return
                get_data = response.toString();
        }
        return get_data;
    }
}


