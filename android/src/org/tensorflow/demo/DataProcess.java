package org.tensorflow.demo;

import android.content.Context;
import android.content.Intent;

import org.tensorflow.demo.Search.PillListActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DataProcess {

    public DataProcess(){
    }

    // 색상 처리 함수
    public String getColor(String drug_color) throws UnsupportedEncodingException {

        String Dcolor;

        if(drug_color.equals("White"))
            drug_color = "하양";
        else if(drug_color.equals("Yellow"))
            drug_color = "노랑";
        else if(drug_color.equals("Orange"))
            drug_color = "주황";
        else if(drug_color.equals("Pink"))
            drug_color = "분홍";
        else if(drug_color.equals("Red"))
            drug_color = "빨강";
        else if(drug_color.equals("Brown"))
            drug_color = "갈색";
        else if(drug_color.equals("Light green"))
            drug_color = "연두";
        else if(drug_color.equals("Green"))
            drug_color = "초록";
        else if(drug_color.equals("Turquoise"))
            drug_color = "청록";
        else if(drug_color.equals("Blue"))
            drug_color = "파랑";
        else if(drug_color.equals("Indigo"))
            drug_color = "남색";
        else if(drug_color.equals("Light purple"))
            drug_color = "자주";
        else if(drug_color.equals("Purple"))
            drug_color = "보라";
        else if(drug_color.equals("Gray"))
            drug_color = "회색";
        else if(drug_color.equals("Black"))
            drug_color = "검정";
        else
            drug_color = "";

        drug_color += ","; // 쉼표 꼭 추가해야 함
        Dcolor = URLEncoder.encode(String.valueOf(drug_color), "UTF-8"); // 한글을 인코딩한 값

        return Dcolor;
    }
}
