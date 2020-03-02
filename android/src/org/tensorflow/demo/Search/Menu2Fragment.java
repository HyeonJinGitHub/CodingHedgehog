package org.tensorflow.demo.Search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.tensorflow.demo.DetectorActivity;
import org.tensorflow.demo.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu2Fragment extends Fragment {
    String[] Q_list;
    String[] A_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_menu2, container, false);

        Q_list = v.getResources().getStringArray(R.array.Q_List); // 텍스트 array 가져오기
        A_list = v.getResources().getStringArray(R.array.A_List);

        ArrayList<FnQ> list = new ArrayList<FnQ>();
        for(int i = 0; i < Q_list.length; i++){
            list.add(new FnQ(Q_list[i], A_list[i]));
        }
        CustomAdapter adapter = new CustomAdapter(getContext(), R.layout.list_fnq_item, list);

        ListView listView = (ListView)v.findViewById(R.id.listview2);
        listView.setAdapter(adapter);

        int[] toggle = new int[Q_list.length]; // 토글버튼을 위한 배열

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               TextView quest = (TextView)view.findViewById(R.id.question_text);
               TextView ans = (TextView)view.findViewById(R.id.answer_text);
               if(toggle[position] == 0) {
                   ans.setVisibility(View.VISIBLE);
                   toggle[position] = 1;
               }
               else{
                   ans.setVisibility(View.GONE);
                   toggle[position] = 0;
               }
            }
        });

        return v;
    }
}

class CustomAdapter extends ArrayAdapter<FnQ>{
    ArrayList<FnQ> list;
    Context context;

    public CustomAdapter(Context context, int viewId, ArrayList<FnQ> list){
        super(context, viewId, list);
        this.list = list;
        this.context = context;
    }

    // position에 위치한 데이터를, 화면에 출력될 view를 리턴하는 함수
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_fnq_item, null);
        }
        TextView quest = (TextView)convertView.findViewById(R.id.question_text);
        TextView ans = (TextView) convertView.findViewById(R.id.answer_text);

        FnQ fnq = list.get(position);
        if( fnq != null){
            quest.setText("Q. " + fnq.getQuestion());
            ans.setText("A. " + fnq.getAnswer());
        }
        return convertView;
    }
    // 아이템 데이터 추가
    public void addItem(String question, String answer){

    }
}
class FnQ{
    String question;
    String answer;

    public FnQ(String question, String answer){
        this.question = question;
        this.answer = answer;
    }
    public void setQuestion(String q){
        this.question = q;
    }
    public String getQuestion(){
        return question;
    }
    public void setAnswer(String a){
        this.answer = a;
    }
    public String getAnswer(){
        return answer;
    }
}





