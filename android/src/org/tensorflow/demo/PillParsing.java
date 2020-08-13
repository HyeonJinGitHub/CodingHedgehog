package org.tensorflow.demo;

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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PillParsing {

    public static void main(String[] args) {

    }

    public ArrayList<PillListVO> getPillList(String json) {
        ArrayList<PillListVO> list = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JsonObject jsonObject = new JsonObject();
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
}
