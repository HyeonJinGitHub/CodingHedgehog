package org.tensorflow.demo.Search;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.tensorflow.demo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static java.lang.Thread.sleep;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
class pillDetail{
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

public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    public static String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public String getmParam1(){
        return mParam1;
    }
    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
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
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_detail, container, false);

        //TextView text = (TextView)v.findViewById(R.id.textView);
        TextView drug_name = (TextView)v.findViewById(R.id.drug_name);
        TextView upso_name_kfda = (TextView)v.findViewById(R.id.upso_name_kfda);
        TextView cls_name = (TextView)v.findViewById(R.id.cls_name);
        TextView item_ingr_type = (TextView)v.findViewById(R.id.item_ingr_type);
        TextView charact = (TextView)v.findViewById(R.id.charact);
        TextView sunb = (TextView)v.findViewById(R.id.sunb);
        TextView effect = (TextView)v.findViewById(R.id.effect);
        TextView dosage = (TextView)v.findViewById(R.id.dosage);
        TextView caution = (TextView)v.findViewById(R.id.caution);
        TextView mediguide = (TextView)v.findViewById(R.id.mediguide);
        TextView stmt = (TextView)v.findViewById(R.id.stmt);

        new downloadDetail().execute();
        try {
            sleep(1000);    //600이였음
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pillDetail pillDetail =  downloadDetail.getPillDetail();
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

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void onFragmentInteraction(Uri uri){

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

class downloadDetail extends AsyncTask<String,String,String> {
    //데이터를 저장하게 되는 리스트
    static public ArrayList<Pill> list = new ArrayList<>();
    static public pillDetail pillDetail = new pillDetail();

    public static pillDetail getPillDetail() {
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

        URL url = new URL("http://dikweb.health.kr/ajax/drug_info/drug_info_ajax.asp?nsearch=ndetail&drug_code="+DetailFragment.mParam1);

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
        str = str.replace("[","");
        str = str.replace("]","");
        str = str.replace("{","");
        str = str.replace("}","");
        str = str.replace("\\n","");
        str = str.replace("\\","");
        str = str.replace("<br/>","");
        str = str.replace("</br>", "");
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
        Log.i("pill",pillDetail.getDrug_name());


    }
}
