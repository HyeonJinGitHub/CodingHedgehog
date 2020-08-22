package org.tensorflow.demo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

public class PillParsing {

    public static void main(String[] args) {

    }

    public ArrayList<PillListVO> getPillList(String json) {
        ArrayList<PillListVO> list = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = (JsonArray) parser.parse(json);

            int index = 0;
            while (index < jsonArray.size()) {
                PillListVO PillListVO = gson.fromJson(jsonArray.get(index).toString(), PillListVO.class);
                list.add(PillListVO);
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public PillDetailVO getPillDetail(String json) {
        PillDetailVO pillDetailVO = new PillDetailVO();

        Gson gson = new Gson();
        try {
            JsonElement jelement = new JsonParser().parse(json);
            JsonObject jobject = jelement.getAsJsonObject();
            JsonObject jobject1 = (JsonObject)jobject.getAsJsonObject("detail");
            JsonObject jobject2 = (JsonObject)jobject.getAsJsonObject("guide");
            JsonElement picto = jobject2.get("picto_img");
            JsonElement medititle = jobject2.get("medititle");

            pillDetailVO = gson.fromJson(jobject1.toString(), PillDetailVO.class);
            pillDetailVO.setPicto_img(picto.toString().replace("\"",""));
            pillDetailVO.setMedititle(medititle.toString().replace("\"",""));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pillDetailVO;
    }

    public ArrayList<PillInteractionVO> getPillInteraction(String json) {
        PillInteractionVO pillInteractionVO = new PillInteractionVO();
        ArrayList<PillInteractionVO> list = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = (JsonArray) parser.parse(json);

            for(int i = 0; i < jsonArray.size(); i++) {
                JsonObject jobject = (JsonObject) jsonArray.get(i);
                JsonArray jsonArray1 = (JsonArray)jobject.getAsJsonArray("data");
                for(int j = 0 ; j < jsonArray1.size(); j++){
                    PillInteractionVO vo = gson.fromJson(jsonArray1.get(j).toString(), PillInteractionVO.class);
                    System.out.println("data : " + jsonArray1.get(j).toString());
                    list.add(vo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
