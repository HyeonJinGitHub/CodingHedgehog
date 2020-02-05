package org.tensorflow.demo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class FileUpload extends AsyncTask <String, Void, Void>{

    @Override
    protected Void doInBackground(String... params) {
        try{
            Log.i(TAG, "length : " + params.length);
            byte[] bytes = params[0].getBytes();
            String str = "hi";

            URL url = new URL("http://192.168.23.245:8080/upload_file/test.jsp");
            Log.i(TAG, "http://localhost");
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            // open Connection
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            Log.i(TAG,"응답코드 : " + con.getResponseCode());
            OutputStream os = con.getOutputStream();
            os.write(str.getBytes("UTF-8"));
            os.flush();
            os.close();

            /*
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

            // write data (이 부분에서 Setting~ 에러  발생?)
            DataOutputStream dos = new DataOutputStream(con.getOutputStream());
            Log.i(TAG, "Open OutputStream");
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            // 파라미터 : file, 파일명 : camera.jpg로 설정하여 전송
            dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"camera.jpg\""
                    +lineEnd);
            dos.writeBytes(lineEnd);
            dos.write(bytes, 0, bytes.length);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            Log.i(TAG, "write All");

            dos.flush(); // 업로드 끝
            dos.close(); // 닫기
            */
        }catch (Exception e){
            Log.i(TAG, "Upload_Exception" + e.getMessage());
        }
        Log.i(TAG,"Upload...finish!");

        return null;
    }

}
