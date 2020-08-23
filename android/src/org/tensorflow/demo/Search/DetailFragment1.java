package org.tensorflow.demo.Search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.tensorflow.demo.R;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    HashMap<String, Bitmap> mMap = new HashMap<String,Bitmap>();
    Handler handler;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetailFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment1 newInstance(String param1, String param2) {
        DetailFragment1 fragment = new DetailFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        View v= inflater.inflate(R.layout.fragment_detail1, container, false);

        ImageView pill_img = (ImageView)v.findViewById(R.id.pill_img);
        ImageView picto1= (ImageView)v.findViewById(R.id.picto1);
        ImageView picto2= (ImageView)v.findViewById(R.id.picto2);
        ImageView picto3= (ImageView)v.findViewById(R.id.picto3);

        TextView medititle = (TextView)v.findViewById(R.id.medititle);
        TextView mediguide = (TextView)v.findViewById(R.id.mediguide);

        Bundle bundle1 = getArguments();
        if(bundle1!=null){
            loadImage("http://www.pharm.or.kr/images/sb_photo/small/" + bundle1.getString("img_code") + "_s.jpg", pill_img);
            String temp = bundle1.getString("picto_img");
            System.out.println("temp :" + temp);

            if(temp != null) {
                String[] pictos = temp.split("\\|");

                for (int i = 0; i < pictos.length; i++) {
                    System.out.println("pictos[" + i + "] : " + pictos[i]);
                    if (i == 0) loadImage(pictos[i], picto1);
                    else if (i == 1) loadImage(pictos[i], picto2);
                    else if (i == 2) loadImage(pictos[i], picto3);

                }
            }
            medititle.setText(bundle1.getString("medititle"));
            mediguide.setText(bundle1.getString("mediguide"));
        }

        handler = new Handler(){
            @Override
            public void handleMessage(final Message msg) {
            }
        };


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
            bm = BitmapFactory.decodeStream(new PillList.FlushedInputStream(is));
            mMap.put(url, bm);
            bis.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return bm;
    }
}
