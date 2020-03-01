package org.tensorflow.demo.Search;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import org.tensorflow.demo.R;

import java.util.ArrayList;
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

        ListView listView = (ListView)v.findViewById(R.id.listview);
        List<String> list =new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        /*
            bundle1.putString("upso_name_kfda",pillDetail.getUpso_name_kfda());
            bundle1.putString("cls_name",pillDetail.getCls_name());
            bundle1.putString("item_ingr_type",pillDetail.getItem_ingr_type());
            bundle1.putString("charact",pillDetail.getCharact());
            bundle1.putString("sunb",pillDetail.getSunb());
         */
        Bundle bundle1 = getArguments();
        if(bundle1!=null){
            //text1.setText(bundle1.getString("drug_name"));
            //Log.i("pill",bundle1.getString("drug_name"));
            list.add(bundle1.getString("upso_name_kfda"));
            list.add(bundle1.getString("cls_name"));
            list.add(bundle1.getString("item_ingr_type"));
            list.add(bundle1.getString("charact"));
            list.add(bundle1.getString("sunb"));
        }

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
}