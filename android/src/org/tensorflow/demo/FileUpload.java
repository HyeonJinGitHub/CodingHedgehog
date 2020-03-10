package org.tensorflow.demo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.net.URLEncoder;
import java.util.Arrays;
import org.tensorflow.demo.DetectorActivity;

import static android.content.ContentValues.TAG;

public class FileUpload extends AsyncTask <byte[], Void, String>{
    Context context;
    ProgressDialog dialog;

    HttpURLConnection con = null;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    final File file = new File(Environment.getExternalStorageDirectory()+"/DCIM", "test1.jpg");
    String drug_color = "";
    boolean cancelled = false;

    public FileUpload(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        dialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("알약을 검색하는중입니다.");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }

    @Override
    protected String doInBackground(byte[]... params) { // params 크기 : 1
        try {
            byte bytes[] = params[0];
            if(DetectorActivity.rect_location == null){ // 인식된 알약이 없으면
                cancelled = true; // 취소
            }

            if(!cancelled){ // 취소되지 않았을 때만 진행
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
            }
        } catch (Exception e) {
            Log.i(TAG, "Upload_Exception : " + e.getMessage());
        } finally {
            Log.i(TAG, "Upload...finish!");
            if (con != null)
                con.disconnect();
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
            if(cancelled){
                builder.setMessage("인식된 알약이 없습니다. 다시 촬영해주세요");
            }
            else{
                builder.setMessage("네트워크 상태를 확인하신 후, 다시 시도해주세요");
            }
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }
    }
}

