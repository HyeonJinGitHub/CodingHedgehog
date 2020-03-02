package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.tensorflow.demo.Search.MainActivity;

public class SplashActivity extends Activity {
    protected void onCreate(Bundle savedIntstanceState){
        super.onCreate(savedIntstanceState);

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
