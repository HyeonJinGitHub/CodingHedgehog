package org.tensorflow.demo.Search;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import static org.tensorflow.demo.Search.download.list;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link menu1_list.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link menu1_list#newInstance} factory method to
 * create an instance of this fragment.
 */
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

public class menu1_list extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView selected_item_textview;
    //static List<String> list = new ArrayList<>();

    // TODO: Rename and change types of parameters
    public static String mParam1;
    public static String mParam2;

    private OnFragmentInteractionListener mListener;

    public menu1_list() throws MalformedURLException {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment menu1_list.
     */
    // TODO: Rename and change types and number of parameters
    public static menu1_list newInstance(String param1, String param2) throws MalformedURLException {
        menu1_list fragment = new menu1_list();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static menu1_list newInstance() throws MalformedURLException {
        menu1_list fragment = new menu1_list();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            //ConnectivityManager conManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            //NetworkInfo netInfo = conManager.getActiveNetworkInfo();

            /// if (netInfo != null && netInfo.isConnected()) {
            new download().execute();
            sleep(1200);        //900이였음
                    /*} else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Network isn't connected", Toast.LENGTH_LONG);
                        toast.show();
                    }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Inflate the layout for this fragment
        final ConstraintLayout v = (ConstraintLayout)inflater.inflate(R.layout.fragment_menu1_list, container, false);

        final ListView listview = (ListView)v.findViewById(R.id.listView);
        //selected_item_textview = (TextView)v.findViewById(R.id.selected_item_textview);


        //리스트뷰와 리스트를 연결하기 위해 사용되는 어댑터
        final CustomList adapter = new CustomList(getActivity());

        //리스트뷰의 어댑터를 지정해준다.
        listview.setAdapter(adapter);

        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                //클릭한 아이템의 문자열을 가져옴
                //String selected_item = (String)adapterView.getItemAtPosition(position);
                //((MainActivity)getActivity()).replaceFragment(DetailFragment.newInstance(list.get(position).getDrug_code(),list.get(position).getDrug_name()));
                //((MainActivity)getActivity()).replaceFragment(DetailFragment.newInstance());

                //adapter.notifyDataSetChanged();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               fragmentTransaction.replace(R.id.frame_layout,DetailFragment.newInstance(list.get(position).getDrug_code(),list.get(position).getDrug_name()));

               // fragmentTransaction.replace(1,DetailFragment.newInstance(list.get(position).getDrug_code(),list.get(position).getDrug_name()));
                fragmentTransaction.commit();
                //((MainActivity)getActivity()).replaceFragment(DetailFragment.newInstance());

            }
        });
        return v;
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

class CustomList extends ArrayAdapter<String>{
    private final Activity context;
    public CustomList(Activity context ) {
        super(context,R.layout.listiem);
        Log.i("pill","ok");
        //super(context, R.layout.listiem, download.getList());
        this.context = context;
    }
    public int getCount(){
        return list.size();
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
        download download=new download();
        List<Pill> list = download.getList();
        if(list.size() !=0) {
            drug_name.setText(list.get(position).getDrug_name());
            upso_name.setText(list.get(position).getUpso_name_kfda());
            print.setText(list.get(position).getPrint_front()+"/"+list.get(position).getPrint_back());
        }else{
            Log.i("No pill","none");
        }

        return rowView;
    }
}

class download extends AsyncTask<String,String,String> {
    //데이터를 저장하게 되는 리스트
    static public ArrayList<Pill> list = new ArrayList<>();
    int count;
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

        Log.i("pill",menu1_list.mParam1);
        URL url = new URL(menu1_list.mParam1);
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
        int start;
        int j=0;
        str = str.replace("[","");
        str = str.replace("]","");
        str = str.replace("{","");
        str = str.replace("}","");
        if((start = str.indexOf("totCnt"))>-1){
            str = str.substring(start+8,str.length());
            Log.i("pill",str);
        }
        count=1;
        if((valueOf(str)-10)>0){
            count = valueOf(str)-10;
        }

        //http://dikweb.health.kr/ajax/idfy_info/idfy_info_ajax.asp?drug_name=&drug_print=H&match=include&mark_code=&drug_color=&drug_linef=&drug_lineb=&drug_shape=&drug_form=&drug_shape_etc=&inner_search=print&inner_keyword=&strP=3586&endP=1&nsearch=nsearch
        URL url2 = new URL(menu1_list.mParam2+"strP="+str+"&endP="+count +"&nsearch=nsearch");
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
            list.add(i,pill);

        }
        Log.i("pill size", String.valueOf(list.size()));


    }
}

