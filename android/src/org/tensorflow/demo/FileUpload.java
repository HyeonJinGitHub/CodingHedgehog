package org.tensorflow.demo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class FileUpload extends AsyncTask <byte[], Void, String> {
    Context context;
    ProgressDialog dialog;

    HttpURLConnection con = null;
    HttpURLConnection con2 = null;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";


    final File file = new File(Environment.getExternalStorageDirectory() + "/DCIM", "test1.jpg");
    String drug_color = "";
    String drug_color2 = "";
    String locations[] = new String[2];

    public FileUpload(Context context, String[] strings) {
        this.context = context;
        this.locations = strings;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("알약을 검색하는중입니다.");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }

    @Override
    protected String doInBackground(byte[]... params) { // params 크기 : 2
        try {
            Log.i("TAG", "params 크기 : " + params.length);

            byte bytes[] = params[0]; // 앞면 이미지
            byte bytes2[] = params[1]; // 뒷면 이미지

            String location = locations[0]; // 앞면 좌표
            String location2 = locations[1]; // 뒷면 좌표

            Log.i("TAG", "byte1 : " + bytes + "  /  bytes2 : " + bytes2);
            Log.i("TAG", "location1 : " + location + "  /  location2 : " + location2);

            URL url = new URL("http://39.123.153.90:3000/listpage");
            con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
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
            }
            // 서버로 부터 받아온 알약 데이터값
            drug_color = response.toString();

            // 뒷면 이미지
            URL url2 = new URL("http://39.123.153.90:3000/imgback");
            con2 = (HttpURLConnection) url2.openConnection();
            con2.setDoInput(true);
            con2.setDoOutput(true);
            con2.setUseCaches(false);
            con2.setRequestMethod("POST");
            con2.setRequestProperty("Connection", "Keep-Alive");
            con2.setRequestProperty("ENCTYPE", "multipart/form-data");
            con2.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            con2.connect();

            DataOutputStream dos2 = new DataOutputStream(con2.getOutputStream());

            // 좌표값 전송
            dos2.writeBytes(twoHyphens + boundary + lineEnd);
            dos2.writeBytes("Content-Disposition: form-data; name=\"locations\"\r\n\r\n"
                    + location2 + lineEnd);

            // 이미지 전송
            dos2.writeBytes(twoHyphens + boundary + lineEnd);
            dos2.writeBytes("Content-Disposition: form-data; name=\"files\";filename=\"camera2.jpg\""
                    + lineEnd);
            dos2.writeBytes(lineEnd);
            dos2.write(bytes2, 0, bytes2.length); // 이미지 byte 전송
            dos2.writeBytes(lineEnd);
            dos2.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            Log.i(TAG, "write All");

            dos2.flush(); // 업로드 끝
            dos2.close(); // 닫기

            int responseCode2 = con2.getResponseCode();
            StringBuffer response2 = new StringBuffer(); // 받아온 데이터

            Log.i(TAG, "응답코드 : " + responseCode2 + " 응답메세지 : " + con2.getResponseMessage());

            if (responseCode2 == HttpURLConnection.HTTP_OK || responseCode2 == HttpURLConnection.HTTP_CREATED) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con2.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response2.append(inputLine);
                }
                in.close();
                Log.i(TAG, "받은 데이터 : " + response2);
            }
            // 서버로 부터 받아온 알약 데이터값
            drug_color2 = response2.toString();
        } catch (Exception e) {
            Log.i(TAG, "Upload_Exception" + e.getMessage());
        } finally {
            Log.i(TAG, "Upload...finish!");
            if (con != null)
                con.disconnect();
            if(con2 != null)
                con2.disconnect();
        }

        Log.i(TAG, "마지막 리턴값" + drug_color);

        return drug_color;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();

        if(drug_color.equals("")){ // 받아온 데이터가 없음 --> 서버연결에 문제 발생
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setMessage("네트워크 상태를 확인하신 후, 다시 시도해주세요");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }
    }
}

