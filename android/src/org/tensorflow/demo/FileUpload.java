package org.tensorflow.demo;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import org.tensorflow.demo.DetectorActivity;

import static android.content.ContentValues.TAG;

public class FileUpload extends AsyncTask <byte[], Void, Integer>{
    HttpURLConnection con = null;

    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    final File file = new File(Environment.getExternalStorageDirectory()+"/DCIM", "test1.jpg");

    @Override
    protected Integer doInBackground(byte[]... params) { // params 크기 : 1
        try {
            byte bytes[] = params[0];
            String str = DetectorActivity.rect_location.toString(); // 확률 0.6 이상의 좌표값
            str = str.substring(6, str.length()-1);

            String[] xy = str.split(",");

            for(int i = 0; i < xy.length; i++){
               xy[i] = xy[i].substring(0, xy[i].indexOf("."));
            }
             String location = xy[0] + "," + xy[1] + "," + xy[2] + "," + xy[1] + ","
                     + xy[2] + "," + xy[3] + ", " + xy[0] + "," + xy[3];

            Log.i("TAG", "바이트2 : " + bytes);
            Log.i("TAG", "로케이션2 : " + location);

            URL url = new URL("http://39.123.153.90:3000/listpage");
            con = (HttpURLConnection) url.openConnection();

            /*
            con.setRequestProperty("content-type", "application/json");
            con.setRequestMethod("GET");         // 통신방식
            con.setDoInput(true);                // 읽기모드 지정
            con.setDoOutput(true);                // 쓰기모드 지정
            con.setUseCaches(false);             // 캐싱데이터를 받을지 안받을지
            con.setConnectTimeout(15000);        // 통신 타임아웃

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
            */
            /*
            String str = "";
            JSONObject json = new JSONObject();
            json.accumulate("hi", "hello");
            str = json.toString();

            con.setDoInput(true);
            con.setDoOutput(true);
            //con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json");

            // Log.i(TAG,"Method : " + con.getRequestMethod());
            // con.connect();

            OutputStream os = con.getOutputStream();
            os.write(str.getBytes());
            os.flush();
            os.close();
            Log.i(TAG,"응답코드 : " + con.getResponseCode());
            */

            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            //con.setRequestProperty("Content-Encoding", "gzip");
            con.setRequestProperty("ENCTYPE", "multipart/form-data");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            con.connect();

            // StringBuffer buffer = new StringBuffer();
            // buffer.append(twoHyphens + boundary + lineEnd);
            // buffer.append("Content-Disposition: form-data; name=\"file\";filename=\"test.jpg\""
            //               +lineEnd);

            DataOutputStream dos = new DataOutputStream(con.getOutputStream());

            // 좌표값 전송
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"locations\"\r\n\r\n"
                    + location + lineEnd);

            // 이미지 전송
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"files\";filename=\"camera.jpg\""
                    + lineEnd);
            dos.writeBytes(lineEnd);
            dos.write(bytes, 0, bytes.length); // 이미지 byte 전송
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            Log.i(TAG, "write All");

            dos.flush(); // 업로드 끝
            dos.close(); // 닫기

            int responseCode = con.getResponseCode();
            Log.i(TAG, "응답코드 : " + responseCode + " 응답메세지 : " + con.getResponseMessage());

        } catch (Exception e) {
            Log.i(TAG, "Upload_Exception" + e.getMessage());
        } finally {
            Log.i(TAG, "Upload...finish!");
            if (con != null)
                con.disconnect();
        }
        return 0;
    }
}

